
package eu.janinko.etsza.ai.agents.goals;

import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.model.WorldModel;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public interface Utility {

    double getCurrentUtility(Human h);
    double getUtilityWhen(WorldModel model);
}
