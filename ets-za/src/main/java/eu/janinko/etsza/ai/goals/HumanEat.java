
package eu.janinko.etsza.ai.goals;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.WorldConfig;
import eu.janinko.etsza.ai.agents.Human;
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
    public double getSatisfaction(Human human) {
        return getStatisfaction(human.getTTL());
    }

    private double getStatisfaction(double ttl){
        if(ttl > maxTTL) return 1;
        return ttl / maxTTL;
    }

    @Override
    public Set<Plan> getPlans(Human human){

		Collection<MemoryOfFood> foods = human.getMemories().getAll(MemoryOfFood.class).values();
		WorldConfig cfg = ai.getConfig();
		WorldMath wm = ai.getWorldMath();

		double x = human.getPosX();
		double y = human.getPosY();
        double speed = cfg.getZombieSpeed();

        double ttl = human.getTTL();

        Set<Plan> plans = new HashSet<>();
        for (MemoryOfFood h : foods) {
			long age = ai.getTime() - h.getDate();
            if (age > 50) continue;
			double d = wm.distance(x, y, h.getPosx(), h.getPosy());
            Plan p = new Plan();
            //if( d < 0.5){
            //    p.add(new Eat());
            //    p.setLinking(getStatisfaction(ttl + 333 * h.getAmountZ()) * priority);
            //}else{
            p.add(new Move(h.getPosx(), h.getPosy(), d));
            //p.add(new Eat());
            double steps = d / speed;
            if (steps >= ttl) {
                p.setLinking(0);
            } else {
                p.setLinking(getStatisfaction(ttl + 1000 - steps) * priority);
            }
            //}
            plans.add(p);
		}
        return plans;
    }
}
