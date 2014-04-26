
package eu.janinko.etsza.ai.goals;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.WorldConfig;
import eu.janinko.etsza.ai.agents.Zombie;
import eu.janinko.etsza.ai.goals.Plan.Attack;
import eu.janinko.etsza.ai.goals.Plan.Move;
import eu.janinko.etsza.ai.memory.HumanMemory;
import eu.janinko.etsza.ai.memory.ZombieMemory;
import eu.janinko.etsza.util.WorldMath;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Eat implements Goal<Zombie>{
    private AI ai;
    private double priority;

    private static final double maxTTL = 1000;

    public Eat(AI ai, double priority) {
        this.ai = ai;
        this.priority = priority;
    }

    @Override
    public double getPriority() {
        return priority;
    }

    @Override
    public double getSatisfaction(Zombie zombie) {
        return getStatisfaction(zombie.getTTL());
    }

    private double getStatisfaction(double ttl){
        if(ttl > maxTTL) return 1;
        return ttl / maxTTL;
    }

    @Override
    public Set<Plan> getPlans(Zombie zombie){

		Collection<HumanMemory> humans = zombie.getMemories().getAll(HumanMemory.class).values();
		Collection<ZombieMemory> zombies = zombie.getMemories().getAll(ZombieMemory.class).values();
		WorldConfig cfg = ai.getConfig();
		WorldMath wm = ai.getWorldMath();

		double x = zombie.getPosX();
		double y = zombie.getPosY();
        double speed = cfg.getZombieSpeed();

        double ttl = zombie.getTTL();
        double afterTTL = ttl * 2;
        if(ttl > maxTTL/2){
            afterTTL = ttl + maxTTL / 2;
        }

        Set<Plan> plans = new HashSet<>();
		for(HumanMemory h : humans){
			long age = ai.getTime() - h.getDate();
			if(age > 50) continue;
			double d = wm.distance(x, y, h.getPosx(), h.getPosy()) + cfg.getHumanSpeed() * age;
            Plan p = new Plan();
            if(d <= cfg.getAttackDistance()){
                p.add(new Attack(h.getId(), true));
                p.setLinking(getStatisfaction(afterTTL) * priority);
            }else{
                p.add(new Move(h.getPosx(), h.getPosy(), d));
                p.add(new Attack(h.getId(), true));
                double steps = d / speed;
                if(steps >= ttl){
                    p.setLinking(0);
                }else{
                    p.setLinking(getStatisfaction(afterTTL - steps) * priority);
                }
            }
            plans.add(p);
		}
		for(ZombieMemory z : zombies){
			long age = ai.getTime() - z.getDate();
			if(age > 30) continue;
			double d = wm.distance(x, y, z.getPosx(), z.getPosy());
            Plan p = new Plan();
            if(d <= cfg.getAttackDistance()){
                p.add(new Attack(z.getId(), false));
                p.setLinking(getStatisfaction(afterTTL) * priority);
            }else{
                p.add(new Move(z.getPosx(), z.getPosy(), d));
                p.add(new Attack(z.getId(), false));
                double steps = d / speed;
                if(steps >= ttl){
                    p.setLinking(0);
                }else{
                    p.setLinking(getStatisfaction(afterTTL - steps) * priority);
                }
            }
            plans.add(p);
		}
        return plans;
    }
}
