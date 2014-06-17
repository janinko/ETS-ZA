package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.Callbacks.Actuators;
import eu.janinko.etsza.ai.Callbacks.Sensors;
import eu.janinko.etsza.ai.agents.Actions.Shoot;
import eu.janinko.etsza.ai.brains.HumanGoalBasedBrain;
import eu.janinko.etsza.ai.brains.HumanMemoryBrain;
import eu.janinko.etsza.ai.brains.HumanPathfindingBrain;
import eu.janinko.etsza.ai.brains.basic.HumanBasicBrain;
import eu.janinko.etsza.ai.goals.humans.AvoidZombie;
import eu.janinko.etsza.ai.goals.humans.FearUtility;
import eu.janinko.etsza.ai.goals.humans.HumanAttack;
import eu.janinko.etsza.ai.goals.humans.HumanEat;
import eu.janinko.etsza.ai.goals.humans.KillInfected;
import eu.janinko.etsza.ai.goals.humans.PickupAmmo;
import eu.janinko.etsza.ai.goals.humans.SaveAmmoUtility;
import eu.janinko.etsza.ai.goals.humans.ShootZombie;
import eu.janinko.etsza.ai.goals.humans.StayUtility;
import eu.janinko.etsza.ai.memory.MemoryOfAmmo;
import eu.janinko.etsza.ai.memory.MemoryOfFood;
import eu.janinko.etsza.ai.memory.MemoryOfHuman;
import eu.janinko.etsza.ai.memory.MemoryOfZombie;
import eu.janinko.etsza.ai.goals.GoalConfig;
import eu.janinko.etsza.util.Vector;
import eu.janinko.etsza.util.WorldMath;
import eu.janinko.etsza.wrapper.Patch;
import eu.janinko.etsza.wrapper.Turtle;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Human extends DefaultAgent {
    private int aroundZ;

    private double ttl;
    private int ammo;

    public Human(Turtle turtle, AI ai) {
        super(turtle, ai);
        ttl = turtle.getTTL();
        ammo = turtle.getAmmo();

        brain = new HumanMemoryBrain(this, ai);
        Random random = ai.getRandom();
        GoalConfig gc = ai.getAgents().getGoalConfig();

        //utilities.add(new DangerUtility(0, ai));
        utilities.add(new StayUtility(gc.getStay(), ai));// + random.nextDouble()*10, ai));
        //utilities.add(new AvoidDangerUtility(ai, 0.4));
        utilities.add(new SaveAmmoUtility(ai, gc.getSaveAmmo()));
        utilities.add(new FearUtility(ai, gc.getFear()));

        goals.add(new HumanAttack(ai, gc.getHumanAttack()));// + random.nextDouble()*0.3));
        goals.add(new ShootZombie(ai, gc.getShootZombie()));// + random.nextDouble()*0.3));
        goals.add(new KillInfected(ai, gc.getKillInfected()));// + random.nextDouble()*0.3));
        goals.add(new HumanEat(ai, gc.getHumanEat()));// + random.nextDouble()*0.2));
        goals.add(new AvoidZombie(ai, gc.getAvoidZombie()));// + random.nextDouble()*0.5));
        goals.add(new PickupAmmo(ai, gc.getPickupAmmo()));// + random.nextDouble()*0.5));

        memories.addMemoryClass(MemoryOfZombie.class);
        memories.addMemoryClass(MemoryOfHuman.class);
        memories.addMemoryClass(MemoryOfFood.class);
        memories.addMemoryClass(MemoryOfAmmo.class);
    }

    @Override
    public void updateAgent(Turtle turtle) {
        super.updateAgent(turtle);
        ttl = turtle.getTTL();
        ammo = turtle.getAmmo();
    }

    @Override
    public void setBrain(String brainId) {
        switch(brainId){
            case "BasicBrain": brain = new HumanBasicBrain(this, ai); return;
            case "MemoryBrain": brain = new HumanMemoryBrain(this, ai); return;
            case "PathfindingBrain": brain = new HumanPathfindingBrain(this, ai); return;
            case "GoalBrain": brain = new HumanGoalBasedBrain(this, ai); return;
            default: throw new IllegalArgumentException("Unknown brain id");
        }
    }
    
    @Override
    protected void act(Actuators a) {
        switch (thinkResult.getType()) {
            case Shoot:{
                Shoot s = (Shoot) thinkResult;
                a.shoot(s.getId());
                break;
            }
            default: {
                super.act(a);
            }
        }
    }
    
    @Override
    public void sense(Sensors s){
        for(Turtle t : s.see(id)){
            Long tid = t.getId();
            if(t.isHuman()){
                if(id == tid) continue;
                if(memories.contains(MemoryOfHuman.class, tid)){
                    memories.get(MemoryOfHuman.class, tid).update(t, ai);
                }else{
                    memories.put(new MemoryOfHuman(t, ai), tid);
                }
            }else{
                if(memories.contains(MemoryOfZombie.class, tid)){
                    memories.get(MemoryOfZombie.class, tid).update(t, ai);
                }else{
                    memories.put(new MemoryOfZombie(t, ai), tid);
                }
            }
        }
        for(Patch p : s.seePatches(id)){
            Long pid = p.getId();
            if(p.getHFood() > 0){
                if(memories.contains(MemoryOfFood.class, pid)){
                    memories.get(MemoryOfFood.class, pid).update(p, ai);
                }else{
                    memories.put(new MemoryOfFood(p, ai), pid);
                }
            }else{
                memories.forget(MemoryOfFood.class, pid);
            }
            if(p.getAmmoBoxes() > 0){
              if(memories.contains(MemoryOfAmmo.class, pid)){
                    memories.get(MemoryOfAmmo.class, pid).update(p, ai);
                }else{
                    memories.put(new MemoryOfAmmo(p, ai), pid);
                }
            }else{
                memories.forget(MemoryOfAmmo.class, pid);
            }
        }
        aroundZ = s.zombiesAround(id);
    }

    public int getAroundZ() {
        return aroundZ;
    }

    public int countZombiesAhead(double cone, double distance) {
        int count = 0;
        for(MemoryOfZombie z : memories.getAll(MemoryOfZombie.class).values()){
            if(ai.getTime() != z.getDate()) continue;
            Vector v = new Vector(posX, posY, z.getPosX(), z.getPosY());
            
            if(v.size() > distance) continue;
            if(WorldMath.angleDiff(v.angle(), heading) > cone) continue;
            count++;
        }
        return count;
    }

    public Set<MemoryOfZombie> getZombiesAhead(double cone, double distance) {
        Set<MemoryOfZombie> ret = new HashSet<>();
        for(MemoryOfZombie h : memories.getAll(MemoryOfZombie.class).values()){
            if(ai.getTime() != h.getDate()) continue;
            Vector v = new Vector(posX, posY, h.getPosX(), h.getPosY());

            if(v.size() > distance) continue;
            if(WorldMath.angleDiff(v.angle(), heading) > cone) continue;
            ret.add(h);
        }
        return ret;
    }

    public double getTTL() {
        return ttl;
    }

    public int getAmmo() {
        return ammo;
    }
}
