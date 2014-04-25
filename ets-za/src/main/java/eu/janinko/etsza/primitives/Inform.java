package eu.janinko.etsza.primitives;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.wrapper.Turtle;
import org.nlogo.api.Agent;
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
public class Inform extends DefaultCommand {
    private AI ai;


    public Inform(AI ai) {
        this.ai = ai;
    }

    /**
     * gbui:select-brains HumanBrain ZombieBrain
     */
    @Override
    public Syntax getSyntax() {
        return Syntax.commandSyntax(new int[]{Syntax.StringType(), Syntax.TurtleType()});
    }

    @Override
    public void perform(Argument[] args, Context ctx) throws ExtensionException, LogoException {
        String info = args[0].getString();
        Agent a = ctx.getAgent();
		
        Turtle turtle = new Turtle((org.nlogo.api.Turtle) ctx.getAgent());
        ai.getAgents().getAgent(turtle).inform(info, new Turtle(args[1].getTurtle()));
				
    }
    
}
