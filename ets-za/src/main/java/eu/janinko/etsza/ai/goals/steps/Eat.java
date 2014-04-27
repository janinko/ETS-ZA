
package eu.janinko.etsza.ai.goals.steps;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Eat implements Step {

    public Eat() {
    }

    @Override
    public String toString() {
        return "Eat{}";
    }

    @Override
    public double getLiking() {
        return 1;
    }

}
