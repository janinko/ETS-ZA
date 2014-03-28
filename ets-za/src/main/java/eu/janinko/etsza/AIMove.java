
package eu.janinko.etsza;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
class AIMove extends DefaultReporter {

    public AIMove() {
    }
    
    /**
     * command: ai-move
     * arguments: turtle number number turtleset
     * returns: boolean
     */
    @Override
    public Syntax getSyntax() {
        return Syntax.reporterSyntax(new int[]{Syntax.TurtleType(),
                                               Syntax.NumberType(),
                                               Syntax.NumberType(),
                                               Syntax.TurtlesetType()},
                                     Syntax.BooleanType());
    }

    @Override
    public Object report(Argument[] argmnts, Context cntxt) throws ExtensionException, LogoException {
        return true;
    }
    
}
