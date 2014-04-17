
package eu.janinko.etsza.ai.agents.goals;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.agents.memory.ZombieMemory;
import eu.janinko.etsza.ai.model.WorldModel;
import eu.janinko.etsza.util.WorldMath;
import java.util.Collection;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class DangerUtility implements Utility{
    private static final double sigma = 1;
    private final double desiredDanger;
    private final AI ai;

    public DangerUtility(double desiredDanger, AI ai) {
        this.desiredDanger = desiredDanger;
        this.ai = ai;
    }
    
    @Override
    public double getCurrentUtility(Human h){
        Collection<ZombieMemory> zombies = h.getMemories().getAll(ZombieMemory.class).values();
        return dangerToUtility(getDanger(h.getPosX(), h.getPosY(), zombies));
    }
    
    private double dangerToUtility(double danger){
        double diff = Math.abs(desiredDanger - danger);
        return Math.pow(Math.E, -(diff*diff)/(2*sigma*sigma));
    }
    
    public double getDanger(double x, double y, Collection<ZombieMemory> zombies){
        double distanceParam = 1 / ai.getConfig().getZombieSpeed();
        double agingParam = 20;
        long time = ai.getTime();
        WorldMath wm = new WorldMath(ai.getConfig().getWidth(), ai.getConfig().getHeight());
        
        double ret = 0;
        for(ZombieMemory z : zombies){
            long age = time - z.getDate();
            if(age > 50) continue;
            double timemodif = agingParam / (agingParam + age);
            double danger = distanceParam / (distanceParam + wm.distance(x, y, z.getPosx(), z.getPosy()));
            ret += danger * timemodif;
        }
        return ret;
    }

    @Override
    public double getUtilityWhen(WorldModel model) {
        Collection<ZombieMemory> zombies = model.getMemories().getAll(ZombieMemory.class).values();
        return dangerToUtility(getDanger(model.getPosx(), model.getPosy(), zombies));
    }
}
