
package eu.janinko.etsza.ai.goals.steps;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Attack implements Step {
    private final long target;
    private final double dist;
    private final boolean isHuman;

    public Attack(long target, double distance, boolean isHuman) {
        this.target = target;
        this.dist = distance;
        this.isHuman = isHuman;
    }

    public boolean isTargetHuman() {
        return isHuman;
    }

    public long getTarget() {
        return target;
    }

    public double getDistance() {
        return dist;
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
