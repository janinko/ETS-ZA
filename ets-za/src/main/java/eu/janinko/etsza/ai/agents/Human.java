package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.Callbacks.Actuators;
import eu.janinko.etsza.ai.Callbacks.Sensors;
import eu.janinko.etsza.ai.agents.Actions.Action;
import eu.janinko.etsza.ai.brains.basic.HumanBasicBrain;
import eu.janinko.etsza.ai.brains.HumanGoalBasedBrain;
import eu.janinko.etsza.ai.brains.HumanMemoryBrain;
import eu.janinko.etsza.ai.brains.HumanPathfindingBrain;
import eu.janinko.etsza.ai.goals.DangerUtility;
import eu.janinko.etsza.ai.goals.KillZombie;
import eu.janinko.etsza.ai.memory.MemoryOfZombie;
import eu.janinko.etsza.util.Vector;
import eu.janinko.etsza.util.WorldMath;
import eu.janinko.etsza.wrapper.Turtle;
import org.nlogo.api.Context;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Human extends DefaultAgent {
    private int aroundZ;

    private double ttl;

    public Human(Turtle turtle, AI ai) {
        super(turtle, ai);
        brain = new HumanMemoryBrain(this, ai);

        utilities.add(new DangerUtility(0, ai));
        
        goals.add(new KillZombie(ai, 0.8));
        
        memories.addMemoryClass(MemoryOfZombie.class);
    }

    @Override
    public void updateAgent(Turtle turtle) {
        super.updateAgent(turtle);
        ttl = turtle.getTTL();
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
            if(!t.isHuman()){
                Long zid = t.getId();
                if(memories.contains(MemoryOfZombie.class, zid)){
                    memories.get(MemoryOfZombie.class, zid).update(t, ai);
                }else{
                    memories.put(new MemoryOfZombie(t, ai), zid);
                }
            }
        }
        aroundZ = s.zombiesAround();
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

    @Override
    protected void die(Turtle turtle) {
        if (turtle.isHuman())
            return;

        memories.forget(MemoryOfZombie.class, turtle.getId());
    }

    public double getTTL() {
        return ttl;
    }
}
