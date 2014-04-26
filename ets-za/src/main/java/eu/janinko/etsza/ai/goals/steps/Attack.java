
package eu.janinko.etsza.ai.goals.steps;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Attack implements Step {
    final long target;
    final boolean isHuman;

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
