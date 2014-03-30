
package eu.janinko.etsza.wrapper;

import org.nlogo.api.AgentVariableNumbers;

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

    public double getPosX() {
        return (double) turtle.getVariable(AgentVariableNumbers.VAR_XCOR);
    }

    public double getPosY() {
        return (double) turtle.getVariable(AgentVariableNumbers.VAR_YCOR);
    }

    public double getHeading() {
        return (double) turtle.getVariable(AgentVariableNumbers.VAR_HEADING);
    }
    
}