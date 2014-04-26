
package eu.janinko.etsza.ai.goals;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.memory.ZombieMemory;
import eu.janinko.etsza.ai.model.WorldModel;
import eu.janinko.etsza.util.WorldMath;
import java.util.Collection;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class DangerUtility implements Utility<Human>{
    private final double acceptedDanger;
    private final AI ai;

    public DangerUtility(double acceptedDanger, AI ai) {
        this.acceptedDanger = acceptedDanger;
        this.ai = ai;
    }
    
    @Override
    public double getCurrentUtility(Human h){
        Collection<ZombieMemory> zombies = h.getMemories().getAll(ZombieMemory.class).values();
        return dangerToUtility(getDanger(h.getPosX(), h.getPosY(), zombies));
    }
    
    private double dangerToUtility(double danger){
		if (danger <= acceptedDanger) return 0;
		return (acceptedDanger - danger) / (acceptedDanger - 1);
    }
    
    public double getDanger(double x, double y, Collection<ZombieMemory> zombies){
        double maxDistance = ai.getConfig().getZombieSpeed() * 100;
        double agingParam = 50;
        long time = ai.getTime();
        WorldMath wm = new WorldMath(ai.getConfig().getWidth(), ai.getConfig().getHeight());
        
        double ret = 1;
        for(ZombieMemory z : zombies){
            long age = time - z.getDate();
            if(age > agingParam) continue;
			double distance = wm.distance(x, y, z.getPosx(), z.getPosy());
			if(distance > maxDistance) continue;
            double timemodif = 1 - Math.pow(age / agingParam, 2);
            double distmodif = distance / maxDistance;

            ret *= timemodif * distmodif;
        }
        return 1 - ret;
    }

    //@Override
    public double getUtilityWhen(WorldModel model) {
        Collection<ZombieMemory> zombies = model.getMemories().getAll(ZombieMemory.class).values();
        return dangerToUtility(getDanger(model.getPosx(), model.getPosy(), zombies));
    }

	public double getAcceptedDanger() {
		return acceptedDanger;
	}

    @Override
    public void updatePlan(Plan plan, Human agent) {
        for (Plan.Step step : plan.getSteps()) {
            if (step instanceof Plan.Move) {
                Plan.Move m = (Plan.Move) step;
                double danger = getDanger(m.getTx(), m.getTy(), agent.getMemories().getAll(ZombieMemory.class).values());
                plan.setLinking(plan.getLiking() * dangerToUtility(danger));
            }
        }
    }
}
