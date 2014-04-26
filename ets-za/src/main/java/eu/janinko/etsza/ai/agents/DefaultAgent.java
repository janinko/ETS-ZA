
package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.Callbacks;
import eu.janinko.etsza.ai.agents.brains.Brain;
import eu.janinko.etsza.ai.agents.goals.Goal;
import eu.janinko.etsza.ai.agents.goals.Utility;
import eu.janinko.etsza.ai.agents.memory.Memories;
import eu.janinko.etsza.wrapper.Turtle;
import java.util.HashSet;

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
    protected Memories memories = new Memories();

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
    public double getPosX() {
        return posX;
    }

    @Override
    public double getPosY() {
        return posY;
    }

    public double getHeading() {
        return heading;
    }

    @Override
    public Memories getMemories() {
        return memories;
    }

    protected void act(Actions.Action action, Callbacks.Actuators a) {
        switch (action.getType()) {
            case Move: {
                a.move();
                return;
            }
            case Rotate: {
                Actions.Rotate rotate = (Actions.Rotate) action;
                a.rotate((double) rotate.getDegree());
                return;
            }
            case Idle: {
                return;
            }
            case RotateAndMove: {
                Actions.RotateAndMove rotateAndMove = (Actions.RotateAndMove) action;
                a.rotate(rotateAndMove.getDegree());
                a.move();
                return;
            }
            case Attack: {
                Actions.Attack attack = (Actions.Attack) action;
                a.attack(attack.getId());
                return;
            }
            default: {
                throw new UnsupportedOperationException("Unknown action: " + action);
            }

        }
    }

    @Override
    public void inform(String info, Turtle turtle) {
        switch (info) {
            case "die": die(turtle); return;
            default:
                throw new IllegalArgumentException("Unknown info " + info);
        }
    }

    protected abstract void die(Turtle turtle);
}
