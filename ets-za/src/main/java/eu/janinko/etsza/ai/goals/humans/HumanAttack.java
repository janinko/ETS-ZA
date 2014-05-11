
package eu.janinko.etsza.ai.goals.humans;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.WorldConfig;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.goals.Goal;
import eu.janinko.etsza.ai.goals.Plan;
import eu.janinko.etsza.ai.memory.MemoryOfZombie;
import eu.janinko.etsza.ai.goals.steps.Attack;
import eu.janinko.etsza.ai.goals.steps.Move;
import eu.janinko.etsza.ai.memory.MemoryOfHuman;
import eu.janinko.etsza.util.Vector;
import eu.janinko.etsza.util.WorldMath;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class HumanAttack implements Goal<Human>{
    private final AI ai;
    private final double priority;

    public HumanAttack(AI ai, double priority) {
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
        double attackDist = cfg.getAttackDistance();
        long time = ai.getTime();

        Set<Plan> plans = new HashSet<>();
		for(MemoryOfZombie z : zombies){
			long age = time - z.getDate();
			if(age > 30) continue;
			double d = wm.distance(x, y, z.getPosX(), z.getPosY());
            Plan p = new Plan();
            if(d > attackDist){
                Vector v = new Vector(wm.angle(x, y, z.getPosX(), z.getPosY()), attackDist);
                p.add(new Move(z.getPosX() - v.dx(), z.getPosY() - v.dy(), d - attackDist));
            }
            p.add(new Attack(z.getId(), d, false));
            p.setLinking(priority);
            plans.add(p);
		}
        return plans;
    }
}
