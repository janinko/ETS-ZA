
package eu.janinko.etsza.ai.goals;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.WorldConfig;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.memory.MemoryOfZombie;
import eu.janinko.etsza.ai.goals.steps.Attack;
import eu.janinko.etsza.ai.goals.steps.Move;
import eu.janinko.etsza.ai.memory.MemoryOfHuman;
import eu.janinko.etsza.util.WorldMath;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class HumanAttack implements Goal<Human>{
    private AI ai;
    private double priority;

    private static final double maxTTL = 1000;

    public HumanAttack(AI ai, double priority) {
        this.ai = ai;
        this.priority = priority;
    }

    @Override
    public double getPriority() {
        return priority;
    }

    @Override
    public double getSatisfaction(Human human) {
        return getStatisfaction(human.getTTL());
    }

    private double getStatisfaction(double ttl){
        if(ttl > maxTTL) return 1;
        return ttl / maxTTL;
    }

    @Override
    public Set<Plan> getPlans(Human human){
		Collection<MemoryOfZombie> zombies = human.getMemories().getAll(MemoryOfZombie.class).values();
		Collection<MemoryOfHuman> humans = human.getMemories().getAll(MemoryOfHuman.class).values();
		WorldConfig cfg = ai.getConfig();
		WorldMath wm = ai.getWorldMath();

		double x = human.getPosX();
		double y = human.getPosY();
        double speed = cfg.getHumanSpeed();

        Set<Plan> plans = new HashSet<>();
		for(MemoryOfZombie z : zombies){
			long age = ai.getTime() - z.getDate();
			if(age > 30) continue;
			double d = wm.distance(x, y, z.getPosX(), z.getPosY());
            Plan p = new Plan();
            if(d <= cfg.getAttackDistance()){
                p.add(new Attack(z.getId(), false));
                p.setLinking(priority);
            }else{
                Move m = new Move(z.getPosX(), z.getPosY(), d);
                p.add(m);
                p.add(new Attack(z.getId(), false));
                double steps = 1 / (d / speed + 1);
                p.setLinking(steps * priority);
            }
            plans.add(p);
		}
        for (MemoryOfHuman h : humans) {
            if (!h.isInfected()) continue;
            long age = ai.getTime() - h.getDate();
            if (age > 30) continue;
            double d = wm.distance(x, y, h.getPosX(), h.getPosY());
            Plan p = new Plan();
            if (d <= cfg.getAttackDistance()) {
                p.add(new Attack(h.getId(), false));
                p.setLinking(priority);
            } else {
                Move m = new Move(h.getPosX(), h.getPosY(), d);
                p.add(m);
                p.add(new Attack(h.getId(), false));
                double steps = 1 / (d / speed + 1);
                p.setLinking(steps * priority);
            }
            plans.add(p);
        }
        return plans;
    }
}
