
package eu.janinko.etsza;

import org.nlogo.api.DefaultClassManager;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.PrimitiveManager;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class GBUIExtension extends DefaultClassManager {

    @Override
    public void load(PrimitiveManager pm) throws ExtensionException {
        pm.addPrimitive("print-agent-info", new PrintAgentInfo());
        pm.addPrimitive("ai-rotate", new AIRotate());
        pm.addPrimitive("ai-move", new AIMove());
    }
    
}
