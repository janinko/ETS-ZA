
package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.ai.agents.memory.Memories;
import eu.janinko.etsza.wrapper.Turtle;
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

    double getPosX();

    double getPosY();

    double getHeading();

    public void inform(String info, Turtle turtle);
}
