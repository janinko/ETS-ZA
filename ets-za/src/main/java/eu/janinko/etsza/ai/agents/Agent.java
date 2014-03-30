
package eu.janinko.etsza.ai.agents;

import eu.janinko.etsza.wrapper.Turtle;
import org.nlogo.api.Context;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public interface Agent {
    void perform(Context ctx);
    void updateAgent(Turtle turtle);
}
