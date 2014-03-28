
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
