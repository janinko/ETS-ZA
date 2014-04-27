
package eu.janinko.etsza.ai.goals;

import eu.janinko.etsza.ai.goals.steps.Move;
import eu.janinko.etsza.ai.goals.steps.Step;
import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Human;

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
}
