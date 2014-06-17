
package eu.janinko.etsza.primitives;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.wrapper.Turtle;
import java.util.HashSet;
import java.util.Set;
import org.nlogo.api.Agent;
import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

/**
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class AIThink extends DefaultCommand {
    private AI ai;

    public AIThink(AI ai) {
        this.ai = ai;
    }

    /**
     * gbui:ai-perform
     */
    @Override
    public Syntax getSyntax() {
        return Syntax.commandSyntax(new int[] {Syntax.TurtlesetType()});
    }

    @Override
    public void perform(Argument[] args, Context ctx) throws LogoException, ExtensionException {
        Set<Turtle> turtles = new HashSet<>();
        for( Agent agent: args[0].getAgentSet().agents()){
            turtles.add(new Turtle((org.nlogo.api.Turtle) agent));
        }
        ai.think(turtles, ctx);
    }

}
