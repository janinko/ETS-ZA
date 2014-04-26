
package eu.janinko.etsza.primitives;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.WorldConfig;
import org.nlogo.api.*;

/**
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class SetSettings extends DefaultCommand {
    private AI ai;

    public SetSettings(AI ai) {
        this.ai = ai;
    }

    /**
     * gbui:set-settings see-distance see-cone sense-distance zombie-speed human-speed width height attackDistance
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
                Syntax.NumberType()});
    }

    @Override
    public void perform(Argument[] args, Context ctx) throws ExtensionException, LogoException {
        WorldConfig wc = new WorldConfig(args[0].getDoubleValue(),
                args[1].getDoubleValue(),
                args[2].getDoubleValue(),
                args[3].getDoubleValue(),
                args[4].getDoubleValue(),
                args[5].getDoubleValue(),
                args[6].getDoubleValue(),
                args[7].getDoubleValue());
        ai.setConfig(wc);
    }

}
