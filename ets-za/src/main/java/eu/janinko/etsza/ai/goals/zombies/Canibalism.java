
package eu.janinko.etsza.ai.goals.zombies;

import eu.janinko.etsza.ai.goals.steps.Attack;
import eu.janinko.etsza.ai.goals.steps.Step;
import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Zombie;
import eu.janinko.etsza.ai.goals.Plan;
import eu.janinko.etsza.ai.goals.Utility;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Canibalism implements Utility<Zombie>{

    private double canibalismRate;

    public Canibalism(double canibalismRate, AI ai) {
        this.canibalismRate = canibalismRate;
    }

    public double getCurrentUtility(Zombie h) {
        return canibalismRate;
    }

    @Override
    public void updatePlan(Plan plan, Zombie agent) {
        for (Step step : plan.getSteps()) {
            if (step instanceof Attack) {
                Attack a = (Attack) step;
                if (!a.isTargetHuman()) {
                    plan.setLinking(plan.getLiking() * canibalismRate);
                }
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.canibalismRate) ^ (Double.doubleToLongBits(this.canibalismRate) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Canibalism other = (Canibalism) obj;
        if (Double.doubleToLongBits(this.canibalismRate) != Double.doubleToLongBits(other.canibalismRate))
            return false;
        return true;
    }

}
