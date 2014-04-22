
package eu.janinko.etsza.ai.agents.brains;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Agent;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public abstract class DefaultBrain<T extends Agent> implements Brain {
    protected final T owner;
    protected final AI ai;

    protected DefaultBrain(T owner, AI ai){
		if(owner == null) throw new NullPointerException("owner must not be null");
		if(ai == null) throw new NullPointerException("ai must not be null");
		this.owner = owner;
		this.ai = ai;
    }
	
}
