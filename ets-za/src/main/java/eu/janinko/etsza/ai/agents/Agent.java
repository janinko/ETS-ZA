
package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.agents.goals.Goal;
import eu.janinko.etsza.ai.agents.goals.Utility;
import eu.janinko.etsza.ai.agents.memory.Memories;
import eu.janinko.etsza.wrapper.Turtle;
import java.util.List;
import org.nlogo.api.Context;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public interface Agent {
    void perform(Context ctx);
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
