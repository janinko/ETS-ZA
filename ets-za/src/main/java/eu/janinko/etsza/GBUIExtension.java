
package eu.janinko.etsza;

import eu.janinko.etsza.primitives.PrintAgentInfo;
import eu.janinko.etsza.primitives.SetSensors;
import eu.janinko.etsza.primitives.SetActuators;
import eu.janinko.etsza.primitives.AIPerform;
import eu.janinko.etsza.primitives.SetSettings;
import eu.janinko.etsza.primitives.Tick;
import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.primitives.SelectBrains;
import org.nlogo.api.DefaultClassManager;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.PrimitiveManager;

/**
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class GBUIExtension extends DefaultClassManager {
    AI ai = new AI();

    @Override
    public void load(PrimitiveManager pm) throws ExtensionException {
        pm.addPrimitive("print-agent-info", new PrintAgentInfo(ai));
        pm.addPrimitive("ai-perform", new AIPerform(ai));
        pm.addPrimitive("set-actuators", new SetActuators(ai));
        pm.addPrimitive("set-sensors", new SetSensors(ai));
        pm.addPrimitive("tick", new Tick(ai));
        pm.addPrimitive("select-brains", new SelectBrains(ai));
        pm.addPrimitive("set-settings", new SetSettings(ai));
    }

    @Override
    public void clearAll() {
        ai.clear();
    }

}
