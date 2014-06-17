
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

    /**
     * Penalize attacking when low TTL.
     * @param ai
     */
    public FearUtility(AI ai, double ttlPerc) {
        this.ttl = ttlPerc * ai.getConfig().getMaxTTL();
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.ttl) ^ (Double.doubleToLongBits(this.ttl) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final FearUtility other = (FearUtility) obj;
        if (Double.doubleToLongBits(this.ttl) != Double.doubleToLongBits(other.ttl)) return false;
        return true;
    }
}
