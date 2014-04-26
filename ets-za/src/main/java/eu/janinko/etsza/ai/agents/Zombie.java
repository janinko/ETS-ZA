
package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.Callbacks.Actuators;
import eu.janinko.etsza.ai.Callbacks.Sensors;
import eu.janinko.etsza.ai.agents.Actions.Action;
import eu.janinko.etsza.ai.agents.Actions.RotateAndMove;
import eu.janinko.etsza.ai.agents.brains.ZombieBasicBrain;
import eu.janinko.etsza.ai.agents.brains.ZombieChaseBrain;
import eu.janinko.etsza.ai.agents.brains.ZombieGoalBasedBrain;
import eu.janinko.etsza.ai.agents.goals.Canibalism;
import eu.janinko.etsza.ai.agents.goals.Eat;
import eu.janinko.etsza.ai.agents.memory.HumanMemory;
import eu.janinko.etsza.ai.agents.memory.ZombieMemory;
import eu.janinko.etsza.util.Vector;
import eu.janinko.etsza.util.WorldMath;
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
    
    private Eat gEat;
    private Canibalism uCanibalism;

    private double ttl;

    public Zombie(Turtle turtle, AI ai) {
        super(turtle, ai);

        memories.addMemoryClass(HumanMemory.class);
        memories.addMemoryClass(ZombieMemory.class);
        gEat = new Eat(ai, 1);
        uCanibalism = new Canibalism(0, ai);
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
                if(memories.contains(HumanMemory.class, tid)){
                    memories.get(HumanMemory.class, tid).update(t, ai);
                }else{
                    memories.put(new HumanMemory(t, ai), tid);
                }
            }else{
                if(tid == id) continue;
                if(memories.contains(ZombieMemory.class, tid)){
                    memories.get(ZombieMemory.class, tid).update(t, ai);
                }else{
                    memories.put(new ZombieMemory(t, ai), tid);
                }
            }
        }
        aroundH = s.humansAround();
    }

    public int getAroundH() {
        return aroundH;
    }

    public int countHumansAhead(double cone, double distance) {
        int count = 0;
        for(HumanMemory h : memories.getAll(HumanMemory.class).values()){
            if(ai.getTime() != h.getDate()) continue;
            Vector v = new Vector(posX, posY, h.getPosx(), h.getPosy());
            
            if(v.size() > distance) continue;
            if(WorldMath.angleDiff(v.angle(), heading) > cone) continue;
            count++;
        }
        return count;
    }
	

    public Set<HumanMemory> getHumansAhead(double cone, double distance) {
		Set<HumanMemory> ret = new HashSet<>();
        for(HumanMemory h : memories.getAll(HumanMemory.class).values()){
            if(ai.getTime() != h.getDate()) continue;
            Vector v = new Vector(posX, posY, h.getPosx(), h.getPosy());
            
            if(v.size() > distance) continue;
            if(WorldMath.angleDiff(v.angle(), heading) > cone) continue;
			ret.add(h);
        }
        return ret;
    }

    @Override
    protected void die(Turtle turtle) {
        if (!turtle.isHuman())
            return;
        memories.forget(HumanMemory.class, turtle.getId());
    }

    public double getTTL() {
        return ttl;
    }

    public Eat getGoalEat() {
        return gEat;
    }

    public Canibalism getCanibalism() {
        return uCanibalism;
    }
}
