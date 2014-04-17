
package eu.janinko.etsza.primitives;

import eu.janinko.etsza.ai.Callbacks;
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
public class SetSensors extends DefaultCommand {
    private AI ai;

    public SetSensors(AI ai) {
        this.ai = ai;
    }

    /**
     * gbui:set-sensors arond-z around-h see
     */
    @Override
    public Syntax getSyntax() {
        return Syntax.commandSyntax(new int[] { Syntax.ReporterTaskType(),
                                                Syntax.ReporterTaskType(),
                                                Syntax.ReporterTaskType(),
                                                Syntax.ReporterTaskType()});
    }

    @Override
    public void perform(Argument[] args, Context ctx) throws ExtensionException, LogoException {
        Callbacks cb = ai.getCallbacks();
        cb.setAroundZ(args[0].getReporterTask());
        cb.setAroundH(args[1].getReporterTask());
        cb.setSee(args[2].getReporterTask());
        cb.setCanAttack(args[3].getReporterTask());
    }
    
}
