
package eu.janinko.etsza.ai.goals.humans;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.goals.Goal;
import eu.janinko.etsza.ai.goals.Plan;
import eu.janinko.etsza.ai.goals.steps.Move;
import eu.janinko.etsza.ai.memory.MemoryOfAmmo;
import eu.janinko.etsza.util.WorldMath;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class PickupAmmo implements Goal<Human>{
    private AI ai;
    private double priority;

    public PickupAmmo(AI ai, double priority) {
        this.ai = ai;
        this.priority = priority;
    }

    @Override
    public double getPriority() {
        return priority;
    }

    @Override
    public Set<Plan> getPlans(Human human){
		Collection<MemoryOfAmmo> ammo = human.getMemories().getAll(MemoryOfAmmo.class).values();
		WorldMath wm = ai.getWorldMath();

		double x = human.getPosX();
		double y = human.getPosY();

        double ttl = human.getTTL();

        Set<Plan> plans = new HashSet<>();
        for (MemoryOfAmmo a : ammo) {
			long age = ai.getTime() - a.getDate();
            if (age > 30) continue;
			double d = wm.distance(x, y, a.getPosx(), a.getPosy());
            Plan p = new Plan();
            if (d < 0.5) continue;
            p.add(new Move(a.getPosx(), a.getPosy(), d));
            p.setLinking(priority);
            plans.add(p);
		}
        return plans;
    }
}
