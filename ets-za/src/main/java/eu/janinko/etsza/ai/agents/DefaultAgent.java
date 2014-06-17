
package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.Callbacks;
import eu.janinko.etsza.ai.Callbacks.Actuators;
import eu.janinko.etsza.ai.brains.Brain;
import eu.janinko.etsza.ai.goals.Goal;
import eu.janinko.etsza.ai.goals.Utility;
import eu.janinko.etsza.ai.memory.Memories;
import eu.janinko.etsza.ai.memory.MemoryOfHuman;
import eu.janinko.etsza.ai.memory.MemoryOfZombie;
import eu.janinko.etsza.wrapper.Turtle;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public abstract class DefaultAgent implements Agent{
    protected AI ai;

    protected long id;
    protected double posX, posY;
    protected double heading;

    protected Brain brain;
    protected Memories memories = new Memories();
    protected List<Utility> utilities = new ArrayList<>();
    protected List<Goal> goals = new ArrayList<>();

    protected Actions.Action thinkResult;

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

    protected void act(Callbacks.Actuators a) {
        switch (thinkResult.getType()) {
            case Move: {
                a.move();
                return;
            }
            case Rotate: {
                Actions.Rotate rotate = (Actions.Rotate) thinkResult;
                a.rotate((double) rotate.getDegree());
                return;
            }
            case Idle: {
                return;
            }
            case RotateAndMove: {
                Actions.RotateAndMove rotateAndMove = (Actions.RotateAndMove) thinkResult;
                a.rotate(rotateAndMove.getDegree());
                a.move();
                return;
            }
            case Attack: {
                Actions.Attack attack = (Actions.Attack) thinkResult;
                a.attack(attack.getId());
                return;
            }
            case Eat: {
                a.eat();
                return;
            }
            default: {
                throw new UnsupportedOperationException("Unknown action: " + thinkResult);
            }

        }
    }

    @Override
    public void think() {
        thinkResult = brain.perform();
    }

    @Override
    public void perform(Actuators a) {
        if(thinkResult == null) throw new IllegalStateException("Method perform called before method think.");
        act(a);
        thinkResult = null;
    }

    @Override
    public void inform(String info, Turtle turtle) {
        switch (info) {
            case "die": die(turtle); return;
            case "zombifie": zombifie(turtle); return;
            default:
                throw new IllegalArgumentException("Unknown info " + info);
        }
    }

    protected void die(Turtle turtle){
        if (turtle.isHuman()){
            memories.forget(MemoryOfHuman.class, turtle.getId());
        }else{
            memories.forget(MemoryOfZombie.class, turtle.getId());
        }
    }

    protected void zombifie(Turtle turtle) {
        memories.forget(MemoryOfHuman.class, turtle.getId());
        memories.put(new MemoryOfZombie(turtle, ai), turtle.getId());
    }

    @Override
    public List<Utility> getUtilities() {
        return utilities;
    }

    @Override
    public List<Goal> getGoals() {
        return goals;
    }
}
