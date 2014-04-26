
package eu.janinko.etsza.ai.brains;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.WorldConfig;
import eu.janinko.etsza.ai.agents.Actions;
import eu.janinko.etsza.ai.agents.Human;

import java.util.Random;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class HumanGoalBasedBrain extends DefaultGoalBasedBrain<Human>{
    private Random r;

	public HumanGoalBasedBrain(Human owner, AI ai) {
		super(owner, ai);
        r = ai.getRandom();
	}

    @Override
    protected Actions.Action noGoal(){
		WorldConfig cfg = ai.getConfig();

        if(owner.getAroundZ() == 0){
            return Actions.rotate((int) cfg.getSeeCone());
        }
        if(owner.countZombiesAhead(20, 6) > 0){
            return Actions.rotate(r.nextBoolean() ? 20 : -20);
        }
        return Actions.rotateAndMove((r.nextDouble()-0.5)*20);
    }
	
	
}
