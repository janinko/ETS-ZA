
package eu.janinko.etsza.ai.agents.goals;

import eu.janinko.etsza.ai.agents.Agent;
import java.util.Set;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public interface Goal<K extends Agent> {
    /**
     * Returns priority of this goal.
     * @return Priority of this goal.
     */
    double getPriority();

    /**
     * Return satisfaction of the goal for given agent.
     * 1 is satisfied, 0 is unsatisfied.
     * @param agnet Agent to compute it's satisfaction.
     * @return Satisfaction of the goal in range [0,1].
     */
    double getSatisfaction(K agnet);

    Set<Plan> getPlans(K agent);
}
