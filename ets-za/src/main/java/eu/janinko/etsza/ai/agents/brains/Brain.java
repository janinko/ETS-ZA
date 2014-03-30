package eu.janinko.etsza.ai.agents.brains;

import eu.janinko.etsza.ai.Callbacks.Actuators;
import eu.janinko.etsza.ai.Callbacks.Sensors;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public interface Brain {
    void perform(Sensors s, Actuators a);
}
