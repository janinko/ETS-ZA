
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
        return "Move{angle=" + angle + '}';
    }

    @Override
    public double getLiking() {
        return 1;
    }

}
