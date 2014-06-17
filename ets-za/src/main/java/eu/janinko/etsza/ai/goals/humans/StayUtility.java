
package eu.janinko.etsza.ai.goals.humans;

import eu.janinko.etsza.ai.goals.steps.Move;
import eu.janinko.etsza.ai.goals.steps.Step;
import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.goals.Plan;
import eu.janinko.etsza.ai.goals.Utility;

/**
 * Utility that penalize plans with movement.
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class StayUtility implements Utility<Human>{
    private final double mod;
    private final double speed;

    /**
     * Penalize movement. After stepsToHalf steps, the priority is halved.
     * @param stepsToHalf Number of steps to halve the priorty.
     * @param ai
     */
    public StayUtility(double stepsToHalf, AI ai) {
        this.mod = 1 / stepsToHalf;
        this.speed = ai.getConfig().getHumanSpeed();
    }

    public double getStepsToHalf() {
        return 1 / mod;
    }

    @Override
    public void updatePlan(Plan plan, Human agent) {
        for (Step step : plan.getSteps()) {
            if (step instanceof Move) {
                Move m = (Move) step;
                double dist = m.getDistance();
                double distModif = speed / (dist*mod + speed);
                plan.setLinking(plan.getLiking() * distModif);
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.mod) ^ (Double.doubleToLongBits(this.mod) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final StayUtility other = (StayUtility) obj;
        if (Double.doubleToLongBits(this.mod) != Double.doubleToLongBits(other.mod)) return false;
        return true;
    }
}
