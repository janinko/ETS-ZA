
package eu.janinko.etsza.primitives;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.Callbacks;
import eu.janinko.etsza.wrapper.Turtle;
import org.nlogo.api.*;

/**
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class AIPerform extends DefaultCommand {
    private AI ai;

    public AIPerform(AI ai) {
        this.ai = ai;
    }

    /**
     * gbui:ai-perform
     */
    @Override
    public Syntax getSyntax() {
        return Syntax.commandSyntax();
    }

    @Override
    public void perform(Argument[] args, Context ctx) throws LogoException {
        Turtle turtle = new Turtle((org.nlogo.api.Turtle) ctx.getAgent());

        Callbacks.Actuators a = ai.getCallbacks().getActuators(ctx);
        ai.getAgents().getAgent(turtle).perform(a);
    }

}
