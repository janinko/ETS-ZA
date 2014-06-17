
package eu.janinko.etsza.ai.goals.steps;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Rotate implements Step {
    private final double angle;

    public Rotate(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    @Override
    public String toString() {
        return "Rotate{angle=" + angle + '}';
    }

    @Override
    public double getLiking() {
        return 1;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.angle) ^ (Double.doubleToLongBits(this.angle) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Rotate other = (Rotate) obj;
        if (Double.doubleToLongBits(this.angle) != Double.doubleToLongBits(other.angle))
            return false;
        return true;
    }

}
