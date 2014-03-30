
package eu.janinko.etsza.ai.agents.brains;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.Callbacks.Actuators;
import eu.janinko.etsza.ai.Callbacks.Sensors;
import eu.janinko.etsza.wrapper.Turtle;
import java.util.Random;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class ZombieBasicBrain implements Brain{
    Random r;

    public ZombieBasicBrain(AI ai){
        r = ai.getRandom();
    }

    @Override
    public void perform(Sensors s, Actuators a) {
        for(Turtle t : s.see()){
            if(t.isHuman()){
                a.move();
                return;
            }
        }
        if(s.humansAround() > 0){
            a.rotate(20.0);
        }else{
            if(r.nextInt(3)==0){
                a.rotate(r.nextDouble()*40-20);
            }else{
                a.move();
            }
        }
    }
    
}
