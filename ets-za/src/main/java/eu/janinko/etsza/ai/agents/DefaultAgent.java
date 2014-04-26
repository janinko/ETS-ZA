
package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.Callbacks;
import eu.janinko.etsza.ai.agents.brains.Brain;
import eu.janinko.etsza.ai.agents.goals.Goal;
import eu.janinko.etsza.ai.agents.goals.Utility;
import eu.janinko.etsza.ai.agents.memory.Memories;
import eu.janinko.etsza.wrapper.Turtle;
import java.util.ArrayList;
import java.util.List;

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
    protected List<Utility> utilities = new ArrayList<>();
    protected List<Goal> goals = new ArrayList<>();

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

    @Override
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

    @Override
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

    @Override
    public List<Utility> getUtilities() {
        return utilities;
    }

    @Override
    public List<Goal> getGoals() {
        return goals;
    }
}
