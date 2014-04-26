
package eu.janinko.etsza.ai.brains.basic;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Actions;
import eu.janinko.etsza.ai.agents.Actions.Action;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.brains.DefaultBrain;
import eu.janinko.etsza.ai.memory.MemoryOfZombie;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class HumanBasicBrain extends DefaultBrain<Human> {

    public HumanBasicBrain(Human owner, AI ai) {
		super(owner, ai);
    }

    @Override
    public Action perform() {

        for(MemoryOfZombie m : owner.getZombiesAhead(360, 1)){
            System.out.println("attacking: " + m.getId());
            return Actions.attack(m.getId());
        }

        if(owner.getAroundZ() == 0){
            return Actions.idle();
        }
        if(owner.countZombiesAhead(20, 6) > 0){
            return Actions.rotate(20);
        }
        return Actions.move();
    }
    
}
