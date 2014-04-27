
package eu.janinko.etsza.ai.goals;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Human;
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
public class HumanEat implements Goal<Human>{
    private AI ai;
    private double priority;

    private static final double maxTTL = 1000;

    public HumanEat(AI ai, double priority) {
        this.ai = ai;
        this.priority = priority;
    }

    @Override
    public double getPriority() {
        return priority;
    }

    @Override
    public Set<Plan> getPlans(Human human){
		Collection<MemoryOfFood> foods = human.getMemories().getAll(MemoryOfFood.class).values();
		WorldMath wm = ai.getWorldMath();

		double x = human.getPosX();
		double y = human.getPosY();

        double ttl = human.getTTL();

        Set<Plan> plans = new HashSet<>();
        for (MemoryOfFood h : foods) {
			long age = ai.getTime() - h.getDate();
            if (age > 50) continue;
			double d = wm.distance(x, y, h.getPosx(), h.getPosy());
            Plan p = new Plan();
            if (d >= 0.5) {
                p.add(new Move(h.getPosx(), h.getPosy(), d));
            }
            p.add(new Eat());
            p.setLinking((1 - 0.5*ttl / maxTTL) * priority);
            plans.add(p);
		}
        return plans;
    }
}
