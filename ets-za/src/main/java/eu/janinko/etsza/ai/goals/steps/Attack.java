
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (int) (this.target ^ (this.target >>> 32));
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.dist) ^ (Double.doubleToLongBits(this.dist) >>> 32));
        hash = 53 * hash + (this.isHuman ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Attack other = (Attack) obj;
        if (this.target != other.target) return false;
        if (Double.doubleToLongBits(this.dist) != Double.doubleToLongBits(other.dist)) return false;
        if (this.isHuman != other.isHuman) return false;
        return true;
    }

}
