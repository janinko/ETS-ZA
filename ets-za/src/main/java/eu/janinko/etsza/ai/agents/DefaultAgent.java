
package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.brains.Brain;
import eu.janinko.etsza.wrapper.Turtle;
import org.nlogo.api.Context;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public abstract class DefaultAgent implements Agent{
    protected AI ai;
    protected Brain brain;
    protected long id;
    protected double posX, posY;
    protected double heading;

    public DefaultAgent(Turtle turtle, AI ai) {
        this.ai = ai;
        posX = turtle.getPosX();
        posY = turtle.getPosY();
        heading = turtle.getHeading();
        id = turtle.getId();
    }
    
    @Override
    public void updateAgent(Turtle turtle){
        posX = turtle.getPosX();
        posY = turtle.getPosY();
        heading = turtle.getHeading();
    }

    public long getId() {
        return id;
    }
    
    @Override
    public void perform(Context ctx) {
        brain.perform(ai.getCallbacks().getSensors(ctx), ai.getCallbacks().getActuators(ctx));
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getHeading() {
        return heading;
    }
}
