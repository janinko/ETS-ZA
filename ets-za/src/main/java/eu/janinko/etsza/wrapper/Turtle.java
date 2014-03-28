
package eu.janinko.etsza.wrapper;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Turtle {
    org.nlogo.api.Turtle turtle;

    public Turtle(org.nlogo.api.Turtle turtle) {
        this.turtle = turtle;
    }

    public boolean isHuman() {
        return "HUMANS".equals(turtle.getBreed().printName());
    }

    public long getId() {
        return turtle.id();
    }
    
}
