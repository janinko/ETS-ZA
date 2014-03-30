
package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.brains.HumanMemoryBrain;
import eu.janinko.etsza.wrapper.Turtle;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Human extends DefaultAgent {

    public Human(Turtle turtle, AI ai) {
        super(turtle, ai);
        brain = new HumanMemoryBrain(this, ai);
    }    
}
