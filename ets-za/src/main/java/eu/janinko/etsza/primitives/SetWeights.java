
package eu.janinko.etsza.primitives;

import eu.janinko.etsza.ai.goals.GoalConfig;
import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.WorldConfig;
import org.nlogo.api.*;

/**
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class SetWeights extends DefaultCommand {
    private AI ai;

    public SetWeights(AI ai) {
        this.ai = ai;
    }

    /**
     * gbui:set-settings HumanAttack HumanEat KillInfected PickupAmmo ShootZombie AvoidZombie Fear SaveAmmo Stay
     */
    @Override
    public Syntax getSyntax() {
        return Syntax.commandSyntax(new int[]{Syntax.NumberType(),
                Syntax.NumberType(),
                Syntax.NumberType(),
                Syntax.NumberType(),
                Syntax.NumberType(),
                Syntax.NumberType(),
                Syntax.NumberType(),
                Syntax.NumberType(),
                Syntax.NumberType()});
    }

    @Override
    public void perform(Argument[] args, Context ctx) throws ExtensionException, LogoException {
        GoalConfig gc = new GoalConfig(args[0].getDoubleValue(),
                args[1].getDoubleValue(),
                args[2].getDoubleValue(),
                args[3].getDoubleValue(),
                args[4].getDoubleValue(),
                args[5].getDoubleValue(),
                args[6].getDoubleValue(),
                args[7].getDoubleValue(),
                args[8].getDoubleValue());
        ai.getAgents().setGoalConfig(gc);
    }

}
