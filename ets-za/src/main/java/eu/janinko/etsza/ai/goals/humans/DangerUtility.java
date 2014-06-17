
package eu.janinko.etsza.ai.goals.humans;

import eu.janinko.etsza.ai.goals.steps.Move;
import eu.janinko.etsza.ai.goals.steps.Step;
import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.goals.Plan;
import eu.janinko.etsza.ai.goals.Utility;
import eu.janinko.etsza.ai.memory.MemoryOfZombie;
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
    
    public double getCurrentUtility(Human h){
        Collection<MemoryOfZombie> zombies = h.getMemories().getAll(MemoryOfZombie.class).values();
        return dangerToUtility(getDanger(h.getPosX(), h.getPosY(), zombies, ai));
    }
    
    private double dangerToUtility(double danger){
		if (danger <= acceptedDanger) return 0;
		return (acceptedDanger - danger) / (acceptedDanger - 1);
    }

    public double getDanger(double x, double y, Collection<MemoryOfZombie> zombies){
        return getDanger(x, y, zombies, ai);
    }

    public static double getDanger(double x, double y, Collection<MemoryOfZombie> zombies, AI ai){
        double maxDistance = ai.getConfig().getZombieSpeed() * 100;
        double agingParam = 50;
        long time = ai.getTime();
        WorldMath wm = new WorldMath(ai.getConfig().getWidth(), ai.getConfig().getHeight());
        
        double ret = 1;
        for(MemoryOfZombie z : zombies){
            long age = time - z.getDate();
            if(age > agingParam) continue;
			double distance = wm.distance(x, y, z.getPosX(), z.getPosY());
			if(distance > maxDistance) continue;
            double timemodif = 1 - Math.pow(age / agingParam, 2);
            double distmodif = distance / maxDistance;

            ret *= timemodif * distmodif;
        }
        return 1 - ret;
    }

    //@Override
    public double getUtilityWhen(WorldModel model) {
        Collection<MemoryOfZombie> zombies = model.getMemories().getAll(MemoryOfZombie.class).values();
        return dangerToUtility(getDanger(model.getPosx(), model.getPosy(), zombies, ai));
    }

	public double getAcceptedDanger() {
		return acceptedDanger;
	}

    @Override
    public void updatePlan(Plan plan, Human agent) {
        for (Step step : plan.getSteps()) {
            if (step instanceof Move) {
                Move m = (Move) step;
                double danger = getDanger(m.getTx(), m.getTy(), agent.getMemories().getAll(MemoryOfZombie.class).values(), ai);
                plan.setLinking(plan.getLiking() * dangerToUtility(danger));
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.acceptedDanger) ^ (Double.doubleToLongBits(this.acceptedDanger) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final DangerUtility other = (DangerUtility) obj;
        if (Double.doubleToLongBits(this.acceptedDanger) != Double.doubleToLongBits(other.acceptedDanger))
            return false;
        return true;
    }
}
