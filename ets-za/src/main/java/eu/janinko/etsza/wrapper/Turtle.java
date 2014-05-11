
package eu.janinko.etsza.wrapper;

import org.nlogo.api.AgentVariableNumbers;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Turtle implements AgentWrapper{
    private final org.nlogo.api.Turtle turtle;

    public Turtle(org.nlogo.api.Turtle turtle) {
        this.turtle = turtle;
    }

    public boolean isHuman() {
        return "HUMANS".equals(turtle.getBreed().printName());
    }

    public long getId() {
        return turtle.id();
    }

    @Override
    public double getPosX() {
        return (double) turtle.getVariable(AgentVariableNumbers.VAR_XCOR);
    }

    @Override
    public double getPosY() {
        return (double) turtle.getVariable(AgentVariableNumbers.VAR_YCOR);
    }

    public double getHeading() {
        return (double) turtle.getVariable(AgentVariableNumbers.VAR_HEADING);
    }

    public double getTTL(){
        return getCustomVariable(1);
    }

    public boolean isInfected(){
        return isHuman() && getCustomVariable(2) >= 0;
    }

    public int getAmmo(){
        if(!isHuman()) return 0;
        return (int) getCustomVariable(3);
    }

    private double getCustomVariable(int i){
        return (double) turtle.getVariable(AgentVariableNumbers.VAR_PENMODE + i);
    }
}
