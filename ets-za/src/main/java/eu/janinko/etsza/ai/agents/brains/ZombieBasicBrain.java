
package eu.janinko.etsza.ai.agents.brains;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Actions;
import eu.janinko.etsza.ai.agents.Actions.Action;
import eu.janinko.etsza.ai.agents.Zombie;
import java.util.Random;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class ZombieBasicBrain implements Brain{
    private Zombie owner;
    private Random r;

    public ZombieBasicBrain(Zombie owner, AI ai){
        r = ai.getRandom();
    }

    @Override
    public Action perform() {
        if(owner.getHumansAhead(20, 5) > 0){
            return Actions.move();
        }
        if(owner.getAroundH() > 0){
            return Actions.rotate(20);
        }else{
            if(r.nextInt(3)==0){
                return Actions.rotate(r.nextInt(41)-20);
            }else{
                // if(s.canAttack(t))
                //     a.attack(t);
                // else
                return Actions.move();
            }
        }
    }

}
