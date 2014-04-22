
package eu.janinko.etsza.ai.agents.brains;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Actions;
import eu.janinko.etsza.ai.agents.Actions.Action;
import eu.janinko.etsza.ai.agents.Human;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class HumanBasicBrain extends DefaultBrain<Human>{

    public HumanBasicBrain(Human owner, AI ai) {
		super(owner, ai);
    }

    @Override
    public Action perform() {
        if(owner.getAroundZ() == 0){
            return Actions.idle();
        }
        if(owner.getZombiesAhead(20,6) > 0){
            return Actions.rotate(20);
        }
        return Actions.move();
    }
    
}
