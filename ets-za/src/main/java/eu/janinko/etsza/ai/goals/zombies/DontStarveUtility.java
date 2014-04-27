
package eu.janinko.etsza.ai.goals.zombies;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Zombie;
import eu.janinko.etsza.ai.goals.Plan;
import eu.janinko.etsza.ai.goals.Utility;
import eu.janinko.etsza.ai.goals.steps.Eat;
import eu.janinko.etsza.ai.goals.steps.Move;
import eu.janinko.etsza.ai.goals.steps.Step;

/**
 * Utility that penalize plans with movement with respect to TTL.
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class DontStarveUtility implements Utility<Zombie>{
    private final double treshold;
    private final double speed;
    private final double maxTTL;
    private final double foodTTL = 500;

    /**
     * Penalize movement. When ttl would fall under TTL treshold after movement, plan is penalized.
     * @param ttlTreshold TTL treshold [0,1]
     * @param ai
     */
    public DontStarveUtility(double ttlTreshold, AI ai) {
        this.maxTTL = ai.getConfig().getMaxTTL();
        this.treshold = maxTTL / ttlTreshold;
        this.speed = ai.getConfig().getZombieSpeed();
    }

    public double getStepsToHalf() {
        return 1 / treshold;
    }

    @Override
    public void updatePlan(Plan plan, Zombie agent) {
        double afterTTL = agent.getTTL();
        for (Step step : plan.getSteps()) {
            if (step instanceof Move) {
                Move m = (Move) step;
                double steps = m.getDistance() / speed;
                afterTTL -= steps;
                if(afterTTL < 0){
                    plan.setLinking(0);
                    return;
                }
            }else if(step instanceof Eat){
                afterTTL += foodTTL;
                if(afterTTL > maxTTL){
                    afterTTL = maxTTL;
                }
            }
        }
        if(afterTTL < treshold){
            plan.setLinking(plan.getLiking() * afterTTL / treshold);
        }
    }
}
