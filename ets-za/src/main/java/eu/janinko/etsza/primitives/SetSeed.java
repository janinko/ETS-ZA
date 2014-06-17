
package eu.janinko.etsza.primitives;

import eu.janinko.etsza.ai.AI;
import org.nlogo.api.*;

/**
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class SetSeed extends DefaultCommand {
    private AI ai;

    public SetSeed(AI ai) {
        this.ai = ai;
    }

    /**
     * gbui:set-settings HumanAttack HumanEat KillInfected PickupAmmo ShootZombie AvoidZombie Fear SaveAmmo Stay
     */
    @Override
    public Syntax getSyntax() {
        return Syntax.commandSyntax(new int[]{Syntax.NumberType()});
    }

    @Override
    public void perform(Argument[] args, Context ctx) throws ExtensionException, LogoException {
        long seed = (int) args[0].getDoubleValue();
        ai.setSeed(seed);
    }

}
