
package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.AI;
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
        ai.getCallbacks().rotate(turtle, ctx, ai.getRandom().nextInt(41)-20);
        ai.getCallbacks().move(turtle, ctx);
    }
    
    
}
