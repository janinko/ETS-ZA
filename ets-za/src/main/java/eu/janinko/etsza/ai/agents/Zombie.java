
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
        
        a.rotate(ai.getRandom().nextDouble()*40-20);
        a.move();
    }
    
    
}
