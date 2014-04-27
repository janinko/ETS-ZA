
package eu.janinko.etsza.ai.goals.zombies;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.WorldConfig;
import eu.janinko.etsza.ai.agents.Zombie;
import eu.janinko.etsza.ai.goals.Goal;
import eu.janinko.etsza.ai.goals.Plan;
import eu.janinko.etsza.ai.goals.steps.Attack;
import eu.janinko.etsza.ai.goals.steps.Move;
import eu.janinko.etsza.ai.goals.steps.Rotate;
import eu.janinko.etsza.ai.memory.MemoryOfHuman;
import eu.janinko.etsza.ai.memory.MemoryOfZombie;
import eu.janinko.etsza.util.WorldMath;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class ZombieAttack implements Goal<Zombie>{
    private AI ai;
    private double priority;

    public ZombieAttack(AI ai, double priority) {
        this.ai = ai;
        this.priority = priority;
    }

    @Override
    public double getPriority() {
        return priority;
    }

    @Override
    public Set<Plan> getPlans(Zombie zombie){

		Collection<MemoryOfHuman> humans = zombie.getMemories().getAll(MemoryOfHuman.class).values();
		Collection<MemoryOfZombie> zombies = zombie.getMemories().getAll(MemoryOfZombie.class).values();
		WorldConfig cfg = ai.getConfig();
		WorldMath wm = ai.getWorldMath();

		double x = zombie.getPosX();
		double y = zombie.getPosY();
        double hspeed =  cfg.getHumanSpeed();
        double attackDist = cfg.getAttackDistance();
        double senseDist = cfg.getSenseDistance();
        long time = ai.getTime();

        double ttl = zombie.getTTL();

        int aheadInSenseArea = 0;
        Set<Plan> plans = new HashSet<>();
		for(MemoryOfHuman h : humans){
			//if(h.isInfected() && ttl > 500) continue;
			long age = time - h.getDate();
			if(age > 50) continue;
			double d = wm.distance(x, y, h.getPosX(), h.getPosY()) + hspeed * age;
            if(d <= senseDist){
                aheadInSenseArea++;
            }
            Plan p = new Plan();
            if(d > attackDist){
                p.add(new Move(h.getPosX(), h.getPosY(), d));
            }
            p.add(new Attack(h.getId(), true));
            p.setLinking(priority);
            plans.add(p);
		}
        if(zombie.getAroundH() > aheadInSenseArea){
            Plan p = new Plan();
            p.add(new Rotate(cfg.getSeeCone()));
            p.setLinking(priority);
            plans.add(p);
        }
		/*for(MemoryOfZombie z : zombies){
			long age = ai.getTime() - z.getDate();
			if(age > 30) continue;
			double d = wm.distance(x, y, z.getPosX(), z.getPosY());
            Plan p = new Plan();
            if(d <= cfg.getAttackDistance()){
                p.add(new Attack(z.getId(), false));
                p.setLinking(getStatisfaction(afterTTL) * priority);
            }else{
                p.add(new Move(z.getPosX(), z.getPosY(), d));
                p.add(new Attack(z.getId(), false));
                double steps = d / speed;
                if(steps >= ttl){
                    p.setLinking(0);
                }else{
                    p.setLinking(getStatisfaction(afterTTL - steps) * priority);
                }
            }
            plans.add(p);
		}*/
        return plans;
    }
}
