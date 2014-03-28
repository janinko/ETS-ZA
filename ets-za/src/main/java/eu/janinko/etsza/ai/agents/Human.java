
package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.Callbacks;
import eu.janinko.etsza.wrapper.Turtle;
import org.nlogo.api.Context;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Human implements Agent {
    private Turtle turtle;
    private AI ai;

    public Human(Turtle t, AI ai) {
        turtle = t;
        this.ai = ai;
    }

    @Override
    public void perform(Context ctx) {
        Callbacks.Actuators a = ai.getCallbacks().getActuators(ctx);
        Callbacks.Sensors s = ai.getCallbacks().getSensors(ctx);
        
        if(s.zombiesAround() == 0){
            return;
        }
        for(Turtle t : s.see()){
            if(!t.isHuman()){
                a.rotate(20.0);
                return;
            }
        }
        a.move();
    }
    
}
