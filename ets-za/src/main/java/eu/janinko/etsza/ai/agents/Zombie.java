
package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.Callbacks.Actuators;
import eu.janinko.etsza.ai.Callbacks.Sensors;
import eu.janinko.etsza.ai.agents.Actions.Action;
import eu.janinko.etsza.ai.brains.basic.ZombieBasicBrain;
import eu.janinko.etsza.ai.brains.ZombieChaseBrain;
import eu.janinko.etsza.ai.brains.ZombieGoalBasedBrain;
import eu.janinko.etsza.ai.goals.Canibalism;
import eu.janinko.etsza.ai.goals.DontStarveUtility;
import eu.janinko.etsza.ai.memory.MemoryOfHuman;
import eu.janinko.etsza.ai.memory.MemoryOfZombie;
import eu.janinko.etsza.ai.goals.ZombieAttack;
import eu.janinko.etsza.ai.goals.ZombieEat;
import eu.janinko.etsza.ai.memory.MemoryOfFood;
import eu.janinko.etsza.util.Vector;
import eu.janinko.etsza.util.WorldMath;
import eu.janinko.etsza.wrapper.Patch;
import eu.janinko.etsza.wrapper.Turtle;
import java.util.HashSet;
import java.util.Set;
import org.nlogo.api.Context;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Zombie extends DefaultAgent{
    private int aroundH;

    private double ttl;

    public Zombie(Turtle turtle, AI ai) {
        super(turtle, ai);

        memories.addMemoryClass(MemoryOfHuman.class);
        memories.addMemoryClass(MemoryOfZombie.class);
        memories.addMemoryClass(MemoryOfFood.class);

        utilities.add(new Canibalism(0, ai));
        utilities.add(new DontStarveUtility(0.8, ai));

        goals.add(new ZombieAttack(ai, 0.9));
        goals.add(new ZombieEat(ai, 1));
    }

    @Override
    public void updateAgent(Turtle turtle) {
        super.updateAgent(turtle);
        ttl = turtle.getTTL();
    }

    @Override
    public void setBrain(String brainId) {
        switch(brainId){
            case "BasicBrain": brain = new ZombieBasicBrain(this, ai); return;
            case "ChaseBrain": brain = new ZombieChaseBrain(this, ai); return;
            case "GoalBrain": brain = new ZombieGoalBasedBrain(this, ai); return;
            default: throw new IllegalArgumentException("Unknown brain id");
        }
    }
    
    @Override
    public void perform(Context ctx) {
        Sensors s = ai.getCallbacks().getSensors(ctx);
        Actuators a = ai.getCallbacks().getActuators(ctx);
        
        sense(s);
        Action action = brain.perform();
        act(action, a);
    }
    
    @Override
    protected void act(Action action, Actuators a) {
        switch (action.getType()) {
            default: {
                super.act(action, a);
            }
        }
    }
    
    private void sense(Sensors s){
        for(Turtle t : s.see()){
            Long tid = t.getId();
            if(t.isHuman()){
                if(memories.contains(MemoryOfHuman.class, tid)){
                    memories.get(MemoryOfHuman.class, tid).update(t, ai);
                }else{
                    memories.put(new MemoryOfHuman(t, ai), tid);
                }
            }else{
                if(tid == id) continue;
                if(memories.contains(MemoryOfZombie.class, tid)){
                    memories.get(MemoryOfZombie.class, tid).update(t, ai);
                }else{
                    memories.put(new MemoryOfZombie(t, ai), tid);
                }
            }
        }
        for(Patch p : s.seePatches()){
            Long pid = p.getId();
            if(p.getZFood() > 0){
                if(memories.contains(MemoryOfFood.class, pid)){
                    memories.get(MemoryOfFood.class, pid).update(p, ai);
                }else{
                    memories.put(new MemoryOfFood(p, ai), pid);
                }
            }else{
                memories.forget(MemoryOfFood.class, pid);
            }
        }
        aroundH = s.humansAround();
    }

    public int getAroundH() {
        return aroundH;
    }

    public int countHumansAhead(double cone, double distance) {
        int count = 0;
        for(MemoryOfHuman h : memories.getAll(MemoryOfHuman.class).values()){
            if(ai.getTime() != h.getDate()) continue;
            Vector v = new Vector(posX, posY, h.getPosX(), h.getPosY());
            
            if(v.size() > distance) continue;
            if(WorldMath.angleDiff(v.angle(), heading) > cone) continue;
            count++;
        }
        return count;
    }
	

    public Set<MemoryOfHuman> getHumansAhead(double cone, double distance) {
		Set<MemoryOfHuman> ret = new HashSet<>();
        for(MemoryOfHuman h : memories.getAll(MemoryOfHuman.class).values()){
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
}
