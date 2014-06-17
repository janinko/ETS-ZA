
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

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        return true;
    }

}
