
package eu.janinko.etsza.ai.brains;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.WorldConfig;
import eu.janinko.etsza.ai.agents.Actions;
import eu.janinko.etsza.ai.agents.Zombie;
import eu.janinko.etsza.ai.goals.Canibalism;
import eu.janinko.etsza.ai.goals.Eat;
import eu.janinko.etsza.ai.goals.Plan;
import eu.janinko.etsza.ai.goals.Plan.Step;
import eu.janinko.etsza.util.WorldMath;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class ZombieGoalBasedBrain extends DefaultGoalBasedBrain<Zombie>{
    private Random r;

	public ZombieGoalBasedBrain(Zombie owner, AI ai) {
		super(owner, ai);
        r = ai.getRandom();
	}

    @Override
    protected Actions.Action noGoal(){
		WorldConfig cfg = ai.getConfig();
        //if(owner.getId() == 99) System.out.println(owner.getId() + ": I don't see anything.");
        if (owner.getAroundH() > 0) { // Around me is a human.
            // I'l try to look around for him.
            return Actions.rotate((int) cfg.getSeeCone());
        } else { // Around me isn't any human, I'll walk randomly.
            if (r.nextInt(4) == 0) {
                return Actions.rotateAndMove(WorldMath.normalizeAngle((r.nextDouble() - 0.5) * 60));
            } else {
                return Actions.move();
            }
        }
    }
	
	
}
