
package eu.janinko.etsza.wrapper;

import org.nlogo.api.AgentVariableNumbers;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Patch implements AgentWrapper{
    private final org.nlogo.api.Patch patch;

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
        return getCustomVariable(1);
    }

    public double getHFood(){
        return getCustomVariable(2);
    }

    public double getAmmoBoxes(){
        return getCustomVariable(3);
    }

    private double getCustomVariable(int i){
        return (double) patch.getVariable(AgentVariableNumbers.VAR_PLABELCOLOR + i);
    }
}
