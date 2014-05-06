
package eu.janinko.etsza.ai.goals.humans;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.WorldConfig;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.goals.Goal;
import eu.janinko.etsza.ai.goals.Plan;
import eu.janinko.etsza.ai.memory.MemoryOfZombie;
import eu.janinko.etsza.ai.goals.steps.Move;
import eu.janinko.etsza.util.WorldMath;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class AvoidZombie implements Goal<Human>{
    // coplexity depends on depth: (depth*2+1)^2
    // 0 -> 1; 1 -> 9; 2 -> 25; 3 -> 49; 4 -> 81; 5 -> 121; 6 -> 169; 7 -> 225; 8-> 289; 9 -> 361
    private static final int depth = 4;
    private AI ai;
    private double priority;

    public AvoidZombie(AI ai, double priority) {
        this.ai = ai;
        this.priority = priority;
    }

    @Override
    public double getPriority() {
        return priority;
    }
    
    @Override
    public Set<Plan> getPlans(Human human){
		Collection<MemoryOfZombie> zombies = human.getMemories().getAll(MemoryOfZombie.class).values();
		WorldConfig cfg = ai.getConfig();
		WorldMath wm = ai.getWorldMath();

		double x = human.getPosX();
		double y = human.getPosY();
        double step = cfg.getHumanSpeed() * 5;

        double minDDx = 0;
        double minDDy = 0;
        double minDanger = DangerUtility.getDanger(x, y, zombies, ai);
        for(int dx = -depth; dx <= depth; dx++){
            for(int dy = -depth; dy <= depth; dy++){
                if(dx == 0 && dy == 0) continue; // Already computed
                double danger = DangerUtility.getDanger(x + dx, y + dy, zombies, ai);
                if(danger < minDanger){
                    minDDx = dx;
                    minDDy = dy;
                    minDanger = danger;
                }
            }
        }

        Set<Plan> plans = new HashSet<>();
        if(minDDx == 0 && minDDy == 0){
            return plans;
        }
        double tx = x + minDDx * step;
        double ty = y + minDDy * step;
        Plan plan = new Plan();
        plan.add(new Move(tx, ty, wm.distance(x, y, tx, ty)));
        plan.setLinking(priority);
        plans.add(plan);
        return plans;
    }
}
