package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.Callbacks;
import eu.janinko.etsza.ai.Callbacks.Actuators;
import eu.janinko.etsza.ai.Callbacks.Sensors;
import eu.janinko.etsza.ai.agents.Actions.Action;
import eu.janinko.etsza.ai.agents.Actions.Rotate;
import static eu.janinko.etsza.ai.agents.Actions.Type.Rotate;
import eu.janinko.etsza.ai.agents.brains.HumanBasicBrain;
import eu.janinko.etsza.ai.agents.brains.HumanMemoryBrain;
import eu.janinko.etsza.ai.agents.goals.DangerUtility;
import eu.janinko.etsza.ai.agents.goals.Utility;
import eu.janinko.etsza.ai.agents.memory.ZombieMemory;
import eu.janinko.etsza.util.Vector;
import eu.janinko.etsza.util.WorldMath;
import eu.janinko.etsza.wrapper.Turtle;
import javax.naming.OperationNotSupportedException;
import org.nlogo.api.Context;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Human extends DefaultAgent {
    private int aroundZ;

    public Human(Turtle turtle, AI ai) {
        super(turtle, ai);
        brain = new HumanMemoryBrain(this, ai);
        utilities.add(new DangerUtility(0, ai));
        
        memories.addMemoryClass(ZombieMemory.class);
    }

    @Override
    public void setBrain(String brainId) {
        switch(brainId){
            case "BasicBrain": brain = new HumanBasicBrain(this, ai); return;
            case "MemoryBrain": brain = new HumanMemoryBrain(this, ai); return;
            default: throw new IllegalArgumentException("Unknown brain id");
        }
    }
    
    @Override
    public void perform(Context ctx) {
        if(id == 1){
            System.out.println("Utilities:");
            for(Utility u : utilities){
                System.out.println(u.getClass().getName() + ": " + u.getCurrentUtility(this));
            }
        }
        Sensors s = ai.getCallbacks().getSensors(ctx);
        Actuators a = ai.getCallbacks().getActuators(ctx);
        sense(s);
        Action action = brain.perform();
        act(action, a);
    }
    
    private void act(Action action, Actuators a){
        switch(action.getType()){
            case Move:{
                a.move();
                return;
            }
            case Rotate:{
                Rotate rotate = (Rotate) action;
                a.rotate((double) rotate.getDegree());
                return;
            }
            default:{
                throw new UnsupportedOperationException("Unknown action: " + action);
            }
                
        }
    }
    
    private void sense(Sensors s){
        for(Turtle t : s.see()){
            if(!t.isHuman()){
                Long zid = t.getId();
                if(memories.contains(ZombieMemory.class, zid)){
                    memories.get(ZombieMemory.class, zid).update(t, ai);
                }else{
                    memories.put(new ZombieMemory(t, ai), zid);
                }
            }
        }
        aroundZ = s.zombiesAround();
    }

    public int getAroundZ() {
        return aroundZ;
    }

    public int getZombiesAhead(double cone, double distance) {
        int count = 0;
        for(ZombieMemory z : memories.getAll(ZombieMemory.class).values()){
            if(ai.getTime() != z.getDate()) continue;
            Vector v = new Vector(posX, posY, z.getPosx(), z.getPosy());
            
            if(v.size() > distance) continue;
            if(WorldMath.angleDiff(v.angle(), heading) > cone) continue;
            count++;
        }
        return count;
    }
}
