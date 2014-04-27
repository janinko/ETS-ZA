
package eu.janinko.etsza.primitives;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.Callbacks;
import org.nlogo.api.*;

/**
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class SetActuators extends DefaultCommand {
    private AI ai;

    public SetActuators(AI ai) {
        this.ai = ai;
    }

    /**
     * gbui:set-actuators move rotate eat
     */
    @Override
    public Syntax getSyntax() {
        return Syntax.commandSyntax(new int[]{Syntax.CommandTaskType(),
                Syntax.CommandTaskType(),
                Syntax.CommandTaskType(),
                Syntax.CommandTaskType()});
    }

    @Override
    public void perform(Argument[] args, Context ctx) throws ExtensionException, LogoException {
        Callbacks cb = ai.getCallbacks();
        cb.setMove(args[0].getCommandTask());
        cb.setRotate(args[1].getCommandTask());
        cb.setAttack(args[2].getCommandTask());
        cb.setEat(args[3].getCommandTask());
    }

}
