
package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.brains.ZombieBasicBrain;
import eu.janinko.etsza.wrapper.Turtle;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Zombie extends DefaultAgent{

    public Zombie(Turtle turtle, AI ai) {
        super(turtle, ai);
        brain = new ZombieBasicBrain(ai);
    }
}
