
package eu.janinko.etsza.ai.goals.humans;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Agent;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.goals.Plan;
import eu.janinko.etsza.ai.goals.Utility;
import eu.janinko.etsza.ai.goals.steps.Attack;
import eu.janinko.etsza.ai.goals.steps.Shoot;
import eu.janinko.etsza.ai.goals.steps.Step;

/**
 * Utility that penalize plans with movement.
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class SaveAmmoUtility implements Utility<Human>{
    private final double mod;
    private final AI ai;

    /**
     * Penalize using ammo. When shooting with optimumAmo, priority is halved.
     * @param optimumAmo Number of ammo when if shooting, the priorty is halved.
     * @param ai
     */
    public SaveAmmoUtility(AI ai, int optimumAmo) {
        this.mod = 0.25 / optimumAmo;
        this.ai = ai;
    }

    public double getStepsToHalf() {
        return 1 / mod;
    }

    @Override
    public void updatePlan(Plan plan, Human agent) {
        for (Step step : plan.getSteps()) {
            if (step instanceof Shoot) {
                Shoot a = (Shoot) step;
                double modif = Math.sqrt(agent.getAmmo() * mod);
                if(modif > 1) return;
                plan.setLinking(plan.getLiking() * modif);
            }
        }
    }
}
