
package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.Callbacks.Actuators;
import eu.janinko.etsza.ai.Callbacks.Sensors;
import eu.janinko.etsza.ai.agents.Actions.Action;
import static eu.janinko.etsza.ai.agents.Actions.Type.Move;
import eu.janinko.etsza.ai.agents.brains.ZombieBasicBrain;
import eu.janinko.etsza.ai.agents.memory.HumanMemory;
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

    public Zombie(Turtle turtle, AI ai) {
        super(turtle, ai);
        
        memories.addMemoryClass(HumanMemory.class);
    }

    @Override
    public void setBrain(String brainId) {
        switch(brainId){
            case "BasicBrain": brain = new ZombieBasicBrain(this, ai); return;
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
    
    private void act(Action action, Actuators a){
        switch(action.getType()){
            case Move:{
                a.move();
                return;
            }
            case Rotate:{
                Actions.Rotate rotate = (Actions.Rotate) action;
                a.rotate((double) rotate.getDegree());
                return;
            }
			case Idle:{
				return;
			}
			case Attack:{
				Actions.Attack attack = (Actions.Attack) action;
				a.attack(attack.getId());
				return;
			}
            default:{	
                throw new UnsupportedOperationException("Unknown action: " + action);
            }
                
        }
    }
    
    private void sense(Sensors s){
        for(Turtle t : s.see()){
            if(t.isHuman()){
                Long hid = t.getId();
                if(memories.contains(HumanMemory.class, hid)){
                    memories.get(HumanMemory.class, hid).update(t, ai);
                }else{
                    memories.put(new HumanMemory(t, ai), hid);
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
}
