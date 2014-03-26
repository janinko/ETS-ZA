
package eu.janinko.etsza;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;
import org.nlogo.api.Turtle;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class PrintAgentInfo extends DefaultCommand{

    @Override
    public Syntax getSyntax() {
        return Syntax.commandSyntax(new int[] { Syntax.TurtleType()});
    }

    
    
    @Override
    public void perform(Argument[] argmnts, Context cntxt) throws ExtensionException, LogoException {
        for(Argument arg : argmnts){
            System.out.println("Argument: " + arg);
            Turtle t = arg.getTurtle();
            System.out.println("Agent: " + t);
            for(Object v : t.variables()){
                System.out.println("  variable: " + v);
            }
            System.out.println();
        }
    }
    
}
