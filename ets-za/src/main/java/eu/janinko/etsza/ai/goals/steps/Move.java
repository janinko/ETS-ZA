
package eu.janinko.etsza.ai.goals.steps;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Move implements Step {
    private final double tx;
    private final double ty;
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

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "Move{" + "tx=" + tx + ", ty=" + ty + ", distance=" + distance + '}';
    }

    @Override
    public double getLiking() {
        return 1 / (distance + 1);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.tx) ^ (Double.doubleToLongBits(this.tx) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.ty) ^ (Double.doubleToLongBits(this.ty) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.distance) ^ (Double.doubleToLongBits(this.distance) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Move other = (Move) obj;
        if (Double.doubleToLongBits(this.tx) != Double.doubleToLongBits(other.tx)) return false;
        if (Double.doubleToLongBits(this.ty) != Double.doubleToLongBits(other.ty)) return false;
        if (Double.doubleToLongBits(this.distance) != Double.doubleToLongBits(other.distance))
            return false;
        return true;
    }

}
