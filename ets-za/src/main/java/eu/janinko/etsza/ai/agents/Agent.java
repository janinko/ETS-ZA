
package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.Callbacks.Actuators;
import eu.janinko.etsza.ai.Callbacks.Sensors;
import eu.janinko.etsza.ai.goals.Goal;
import eu.janinko.etsza.ai.goals.Utility;
import eu.janinko.etsza.ai.memory.Memories;
import eu.janinko.etsza.wrapper.Turtle;
import java.util.List;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public interface Agent {

    /**
     * This method use sensors for gathering informations about the world.
     * @param sensors Sensors that will be used for gathering informations about the world.
     */
    void sense(Sensors sensors);

    /**
     * This method use brain for computaion of agents's next step.
     */
    void think();

    /**
     * This method performs action computed by think method.
     * @param actuators Actuators that will be used for altering the world.
     * @throws IllegalStateException When {@link #think think} method wasn't invoked prior to this method.
     */
    void perform(Actuators actuators);

    void updateAgent(Turtle turtle);
    
    void setBrain(String brainId);

    Memories getMemories();

    long getId();

    double getPosX();

    double getPosY();

    double getHeading();

    public void inform(String info, Turtle turtle);

    public List<Utility> getUtilities();
    
    public List<Goal> getGoals();
}
