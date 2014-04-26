
package eu.janinko.etsza.wrapper;

import org.nlogo.api.AgentVariableNumbers;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Patch implements AgentWrapper{
    org.nlogo.api.Patch patch;

    public Patch(org.nlogo.api.Patch patch) {
        this.patch = patch;
    }

    public long getId() {
        return patch.id();
    }

    @Override
    public double getPosX() {
        return (double) patch.getVariable(AgentVariableNumbers.VAR_PXCOR);
    }

    @Override
    public double getPosY() {
        return (double) patch.getVariable(AgentVariableNumbers.VAR_PYCOR);
    }

    public double getHeading() {
        return (double) patch.getVariable(AgentVariableNumbers.VAR_HEADING);
    }

    public double getZFood(){
        return (double) patch.getVariable(AgentVariableNumbers.VAR_PLABELCOLOR + 1);
    }

    public double getHFood(){
        return (double) patch.getVariable(AgentVariableNumbers.VAR_PLABELCOLOR + 2);
    }
}
