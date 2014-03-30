
package eu.janinko.etsza.ai;

import eu.janinko.etsza.ai.agents.Agent;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.agents.Zombie;
import eu.janinko.etsza.wrapper.Turtle;
import java.util.HashMap;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Agents {
    HashMap<Long, Zombie> zombies = new HashMap<>();
    HashMap<Long, Human> humans = new HashMap<>();
    AI ai;

    Agents(AI ai) {
        this.ai = ai;
    }
    
    public Agent getAgent(Turtle t){
        Agent a;
        if(t.isHuman()){
            a = humans.get(t.getId());
            if(a == null){
                Human h = new Human(t, ai);
                humans.put(t.getId(), h);
                a = h;
            }
        }else{
            a = zombies.get(t.getId());
            if(a == null){
                Zombie z = new Zombie(t, ai);
                zombies.put(t.getId(), z);
                a = z;
            }
        }
        a.updateAgent(t);
        return a;
    }
}
