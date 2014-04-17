
package eu.janinko.etsza.primitives;

import eu.janinko.etsza.ai.AI;
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
public class Tick extends DefaultCommand {
    private AI ai;


    public Tick(AI ai) {
        this.ai = ai;
    }

    /**
     * gbui:tick
     */
    @Override
    public Syntax getSyntax() {
        return Syntax.commandSyntax();
    }

    @Override
    public void perform(Argument[] args, Context ctx) throws ExtensionException, LogoException {
        ai.tick();
    }
    
}
