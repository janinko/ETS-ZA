
package eu.janinko.etsza.primitives;

import eu.janinko.etsza.ai.AI;
import org.nlogo.api.AgentVariableNumbers;
import org.nlogo.api.AgentVariables;
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
    private AI ai;

    public PrintAgentInfo(AI ai) {
        this.ai = ai;
    }

    @Override
    public Syntax getSyntax() {
        return Syntax.commandSyntax(new int[] { Syntax.TurtleType()});
    }

    @Override
    public void perform(Argument[] argmnts, Context cntxt) throws ExtensionException, LogoException {
        AgentVariables.getImplicitLinkVariables();
        for(Argument arg : argmnts){
            System.out.println("Argument: " + arg);
            Turtle t = arg.getTurtle();
            System.out.println("  Agent: " + t);
            Object[] vars = t.variables();
            System.out.println("  variables:");
            System.out.println("    VAR_WHO: " + vars[AgentVariableNumbers.VAR_WHO]);
            System.out.println("    VAR_COLOR: " + vars[AgentVariableNumbers.VAR_COLOR]);
            System.out.println("    VAR_HEADING: " + vars[AgentVariableNumbers.VAR_HEADING]);
            System.out.println("    VAR_XCOR: " + vars[AgentVariableNumbers.VAR_XCOR]);
            System.out.println("    VAR_YCOR: " + vars[AgentVariableNumbers.VAR_YCOR]);
            System.out.println("    VAR_SHAPE: " + vars[AgentVariableNumbers.VAR_SHAPE]);
            System.out.println("    VAR_LABEL: " + vars[AgentVariableNumbers.VAR_LABEL]);
            System.out.println("    VAR_LABELCOLOR: " + vars[AgentVariableNumbers.VAR_LABELCOLOR]);
            System.out.println("    VAR_BREED: " + vars[AgentVariableNumbers.VAR_BREED]);
            System.out.println("    VAR_HIDDEN: " + vars[AgentVariableNumbers.VAR_HIDDEN]);
            System.out.println("    VAR_SIZE: " + vars[AgentVariableNumbers.VAR_SIZE]);
            System.out.println("    VAR_PENSIZE: " + vars[AgentVariableNumbers.VAR_PENSIZE]);
            System.out.println("    VAR_PENMODE: " + vars[AgentVariableNumbers.VAR_PENMODE]);
            for(int i=13; i < vars.length; i++){
                System.out.println("    CUSTOM_VAR_"+i+": " + vars[i]);
            }
            for(Object v : t.variables()){
                System.out.println("  variable: " + v);
            }
            System.out.println();
        }
        
    }
    
}
