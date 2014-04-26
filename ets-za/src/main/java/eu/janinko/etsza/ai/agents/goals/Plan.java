
package eu.janinko.etsza.ai.agents.goals;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Plan implements Comparable<Plan>{
    private final List<Step> steps = new ArrayList<>();
    private double liking;

    public void add(Step step){
        steps.add(step);
    }

    public void setLinking(double liking) {
        this.liking = liking;
    }

    public double getLiking() {
        return liking;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public Plan() {
    }

    @Override
    public int compareTo(Plan o) {
        if(liking == o.liking){
            return Double.compare(steps.get(0).getLiking(), o.steps.get(0).getLiking());
        }
        return Double.compare(liking, o.liking);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Liking: ");
        sb.append(liking);
        for(Step step : steps){
            sb.append("\n  ");
            sb.append(step);
        }
        return sb.toString();
    }

    public interface Step{
        double getLiking();
    }
    public static class Move implements Step{
        private final double tx, ty;
        private final double distance;

        public Move(double tx, double ty, double distance) {
            this.tx = tx;
            this.ty = ty;
            this.distance = distance;
        }

        public double getTx() {
            return tx;
        }

        public double getTy() {
            return ty;
        }

        @Override
        public String toString() {
            return "Move{" + "tx=" + tx + ", ty=" + ty + ", distance=" + distance + '}';
        }

        @Override
        public double getLiking() {
            return 1 / (distance + 1);
        }
    }
    public static class Attack implements Step{
        private final long target;
        private final boolean isHuman;

        public Attack(long target, boolean isHuman) {
            this.target = target;
            this.isHuman = isHuman;
        }

        public boolean isTargetHuman() {
            return isHuman;
        }

        public long getTarget() {
            return target;
        }

        @Override
        public String toString() {
            return "Attack{" + "target=" + target + ", isHuman=" + isHuman + '}';
        }

        @Override
        public double getLiking() {
            return 1;
        }
    }
}
