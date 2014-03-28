
package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.Callbacks;
import eu.janinko.etsza.wrapper.Turtle;
import org.nlogo.api.Context;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Zombie implements Agent{
    private Turtle turtle;
    private AI ai;

    public Zombie(Turtle t, AI ai) {
        turtle = t;
        this.ai = ai;
    }

    @Override
    public void perform(Context ctx) {
        Callbacks.Actuators a = ai.getCallbacks().getActuators(ctx);
        Callbacks.Sensors s = ai.getCallbacks().getSensors(ctx);
        
        for(Turtle t : s.see()){
            if(t.isHuman()){
                a.move();
                return;
            }
        }
        if(s.humansAround() > 0){
            a.rotate(20.0);
        }else{
            if(ai.getRandom().nextInt(3)==0){
                a.rotate(ai.getRandom().nextDouble()*40-20);
            }else{
                a.move();
            }
        }
    }
    
    
}
