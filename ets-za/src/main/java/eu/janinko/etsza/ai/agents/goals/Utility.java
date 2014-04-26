
package eu.janinko.etsza.ai.agents.goals;

import eu.janinko.etsza.ai.agents.Agent;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public interface Utility<K extends Agent> {

    double getCurrentUtility(K h);
    //double getUtilityWhen(WorldModel model);
}
