
package eu.janinko.etsza.primitives;

import eu.janinko.etsza.ai.AI;
import org.nlogo.api.*;

/**
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class SelectBrains extends DefaultCommand {
    private AI ai;


    public SelectBrains(AI ai) {
        this.ai = ai;
    }

    /**
     * gbui:select-brains HumanBrain ZombieBrain
     */
    @Override
    public Syntax getSyntax() {
        return Syntax.commandSyntax(new int[]{Syntax.StringType(), Syntax.StringType()});
    }

    @Override
    public void perform(Argument[] args, Context ctx) throws ExtensionException, LogoException {
        String hbrain = args[0].getString();
        String zbrain = args[1].getString();
        ai.getAgents().setDefaultBrains(hbrain, zbrain);
    }

}
