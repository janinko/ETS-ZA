
package eu.janinko.etsza.ai.goals.steps;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Move implements Step {
    final double tx;
    private final double ty;
    final double distance;

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

}
