
package eu.janinko.etsza.ai.goals.humans;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.goals.Plan;
import eu.janinko.etsza.ai.goals.Utility;
import eu.janinko.etsza.ai.goals.steps.Attack;
import eu.janinko.etsza.ai.goals.steps.Step;

/**
 * Utility that penalize plans with movement.
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class FearUtility implements Utility<Human>{
    private final double ttl;
    private final AI ai;

    /**
     * Penalize attacking when low TTL.
     * @param ai
     */
    public FearUtility(AI ai, double ttlPerc) {
        this.ttl = ttlPerc * ai.getConfig().getMaxTTL();
        this.ai = ai;
    }

    @Override
    public void updatePlan(Plan plan, Human agent) {
        if(agent.getTTL() > ttl) return;
        for (Step step : plan.getSteps()) {
            if (step instanceof Attack) {
                double modif = agent.getTTL() / ttl;
                plan.setLinking(plan.getLiking() * modif);
            }
        }
    }
}
