
package eu.janinko.etsza.ai.agents.goals;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Zombie;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Canibalism implements Utility<Zombie>{
    private AI ai;

    private double canibalismRate;

    public Canibalism(double canibalismRate, AI ai) {
        this.canibalismRate = canibalismRate;
        this.ai = ai;
    }

    @Override
    public double getCurrentUtility(Zombie h) {
        return canibalismRate;
    }

    @Override
    public void updatePlan(Plan plan, Zombie agent) {
        for (Plan.Step step : plan.getSteps()) {
            if (step instanceof Plan.Attack) {
                Plan.Attack a = (Plan.Attack) step;
                if (!a.isTargetHuman()) {
                    plan.setLinking(plan.getLiking() * canibalismRate);
                }
            }
        }
    }

}
