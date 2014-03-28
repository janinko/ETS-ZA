
package eu.janinko.etsza;

import java.util.Random;
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
class AIRotate extends DefaultReporter {
    Random r = new Random();

    public AIRotate() {
    }

    /**
     * command: ai-rotate
     * arguments: turtle number number turtleset
     * returns: number
     */
    @Override
    public Syntax getSyntax() {
        return Syntax.reporterSyntax(new int[]{Syntax.TurtleType(),
                                               Syntax.NumberType(),
                                               Syntax.NumberType(),
                                               Syntax.TurtlesetType()},
                                     Syntax.NumberType());
    }

    @Override
    public Object report(Argument[] argmnts, Context cntxt) throws ExtensionException, LogoException {
        return r.nextInt(360);
    }
    
}
