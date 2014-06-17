
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
public class KillInfected implements Goal<Human>{
    private final AI ai;
    private final double priority;

    public KillInfected(AI ai, double priority) {
        this.ai = ai;
        this.priority = priority;
    }

    @Override
    public double getPriority() {
        return priority;
    }

    @Override
    public Set<Plan> getPlans(Human human){
		Collection<MemoryOfHuman> humans = human.getMemories().getAll(MemoryOfHuman.class).values();
		WorldConfig cfg = ai.getConfig();
		WorldMath wm = ai.getWorldMath();

		double x = human.getPosX();
		double y = human.getPosY();
        double attackDist = cfg.getAttackDistance();
        if(human.getAmmo() > 0){
            attackDist = cfg.getShootDistance();
        }
        long time = ai.getTime();

        Set<Plan> plans = new HashSet<>();
        for (MemoryOfHuman h : humans) {
            if (!h.isInfected()) continue;
            long age = time - h.getDate();
            if (age > 30) continue;
            double d = wm.distance(x, y, h.getPosX(), h.getPosY());
            Plan p = new Plan();
            if (d > attackDist) {
                p.add(new Move(h.getPosX(), h.getPosY(), d));
            }
            p.add(new Attack(h.getId(), d, false));
            p.setLinking(priority);
            plans.add(p);
        }
        return plans;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.priority) ^ (Double.doubleToLongBits(this.priority) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final KillInfected other = (KillInfected) obj;
        if (Double.doubleToLongBits(this.priority) != Double.doubleToLongBits(other.priority))
            return false;
        return true;
    }
}
