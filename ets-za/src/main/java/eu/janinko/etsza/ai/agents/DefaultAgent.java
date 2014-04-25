
package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.brains.Brain;
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
    protected HashSet<Utility> utilities = new HashSet<>();
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

	public HashSet<Utility> getUtilities() {
		return utilities;
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
