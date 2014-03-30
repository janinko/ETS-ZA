
package eu.janinko.etsza.ai.agents.brains;

import eu.janinko.etsza.ai.Callbacks.Actuators;
import eu.janinko.etsza.ai.Callbacks.Sensors;
import eu.janinko.etsza.wrapper.Turtle;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class HumanBasicBrain implements Brain{

    @Override
    public void perform(Sensors s, Actuators a) {
        if(s.zombiesAround() == 0){
            return;
        }
        for(Turtle t : s.see()){
            if(!t.isHuman()){
                a.rotate(20.0);
                return;
            }
        }
        a.move();
    }
    
}
