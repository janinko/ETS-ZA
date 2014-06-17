
package eu.janinko.etsza.ai.goals.humans;

import eu.janinko.etsza.ai.goals.steps.Move;
import eu.janinko.etsza.ai.goals.steps.Step;
import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.goals.Plan;
import eu.janinko.etsza.ai.goals.Utility;
import eu.janinko.etsza.ai.memory.MemoryOfZombie;
import eu.janinko.etsza.util.Vector;
import java.util.Collection;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class AvoidDangerUtility implements Utility<Human>{
    private static final int maxSteps = 50;
    private final double acceptedDanger;
    private final AI ai;

    public AvoidDangerUtility(AI ai, double acceptedDanger) {
        this.acceptedDanger = acceptedDanger;
        this.ai = ai;
    }

    @Override
    public void updatePlan(Plan plan, Human agent) {
        double speed = ai.getConfig().getHumanSpeed();
        Collection<MemoryOfZombie> zombies = agent.getMemories().getAll(MemoryOfZombie.class).values();
        for (Step step : plan.getSteps()) {
            if (step instanceof Move) {
                Move m = (Move) step;

                int steps = maxSteps;
                int targetSteps = (int) (m.getDistance() / speed);
                if (steps > targetSteps) {
                    steps = targetSteps;
                }
                double x = agent.getPosX();
                double y = agent.getPosY();
                Vector v = new Vector(ai.getWorldMath().angle(x, y, m.getTx(), m.getTy()), speed);
                double dx = v.dx();
                double dy = v.dy();

                double maxDanger = DangerUtility.getDanger(x, y, zombies, ai);
                for (double i = 0; i < steps; i++) {
                    x += dx;
                    y += dy;
                    double danger = DangerUtility.getDanger(x, y, zombies, ai);;
                    if (danger > maxDanger) {
                        maxDanger = danger;
                    }
                }
                if (maxDanger < acceptedDanger) return;
                plan.setLinking(plan.getLiking() * (1 - maxDanger + acceptedDanger));
                return;
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.acceptedDanger) ^ (Double.doubleToLongBits(this.acceptedDanger) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final AvoidDangerUtility other = (AvoidDangerUtility) obj;
        if (Double.doubleToLongBits(this.acceptedDanger) != Double.doubleToLongBits(other.acceptedDanger))
            return false;
        return true;
    }
}
