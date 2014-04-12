
package eu.janinko.etsza.command;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.wrapper.Turtle;
import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

/**
 *
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
    public void perform(Argument[] args, Context ctx) throws ExtensionException, LogoException {
        Turtle turtle = new Turtle((org.nlogo.api.Turtle) ctx.getAgent());
        ai.getAgents().getAgent(turtle).perform(ctx);
    }
    
}
