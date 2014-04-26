
package eu.janinko.etsza.ai.brains;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Actions;
import eu.janinko.etsza.ai.agents.Actions.Action;
import eu.janinko.etsza.ai.agents.Zombie;
import eu.janinko.etsza.ai.memory.HumanMemory;
import eu.janinko.etsza.util.WorldMath;
import java.util.Random;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class ZombieBasicBrain extends DefaultBrain<Zombie>{
    private Random r;

    public ZombieBasicBrain(Zombie owner, AI ai){
		super(owner, ai);
        r = ai.getRandom();
    }

    @Override
    public Action perform() {
		for(HumanMemory m : owner.getHumansAhead(360, 1)){
			System.out.println("attacking: " + m.getId());
			return Actions.attack(m.getId());
		}
        if(owner.countHumansAhead(20, 5) > 0){
            return Actions.move();
        }
        if(owner.getAroundH() > 0){
            return Actions.rotate(20);
        }else{
            if(r.nextInt(3)==0){
                return Actions.rotateAndMove(r.nextInt(41)-20);
            }else{
                return Actions.move();
            }
        }
    }

}
