
package eu.janinko.etsza.ai.brains;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.WorldConfig;
import eu.janinko.etsza.ai.agents.Actions;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.goals.DangerUtility;
import eu.janinko.etsza.ai.goals.KillZombie;
import eu.janinko.etsza.ai.goals.Plan;
import eu.janinko.etsza.ai.goals.Plan.Step;
import eu.janinko.etsza.ai.model.WorldModel;
import eu.janinko.etsza.util.WorldMath;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

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
        if(owner.getZombiesAhead(20,6) > 0){
            return Actions.rotate(20);
        }
        return Actions.move();
    }
	
	
}
