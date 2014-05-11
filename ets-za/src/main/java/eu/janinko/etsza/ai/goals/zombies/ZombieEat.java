
package eu.janinko.etsza.ai.goals.zombies;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Zombie;
import eu.janinko.etsza.ai.goals.Goal;
import eu.janinko.etsza.ai.goals.Plan;
import eu.janinko.etsza.ai.goals.steps.Eat;
import eu.janinko.etsza.ai.goals.steps.Move;
import eu.janinko.etsza.ai.memory.MemoryOfFood;
import eu.janinko.etsza.util.WorldMath;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class ZombieEat implements Goal<Zombie>{
    private final AI ai;
    private final double priority;

    private static final double maxTTL = 1000;

    public ZombieEat(AI ai, double priority) {
        this.ai = ai;
        this.priority = priority;
    }

    @Override
    public double getPriority() {
        return priority;
    }

    @Override
    public Set<Plan> getPlans(Zombie zombie){

		Collection<MemoryOfFood> foods = zombie.getMemories().getAll(MemoryOfFood.class).values();
		WorldMath wm = ai.getWorldMath();

		double x = zombie.getPosX();
		double y = zombie.getPosY();

        double ttl = zombie.getTTL();

        Set<Plan> plans = new HashSet<>();
        for (MemoryOfFood h : foods) {
			long age = ai.getTime() - h.getDate();
            if (age > 50) continue;
			double d = wm.distance(x, y, h.getPosx(), h.getPosy());
            Plan p = new Plan();
            if (d > 0.5) {
                p.add(new Move(h.getPosx(), h.getPosy(), d));
            }
            p.add(new Eat());
            p.setLinking((1 - 0.5*ttl / maxTTL) * priority);
            plans.add(p);
		}
        return plans;
    }
}
