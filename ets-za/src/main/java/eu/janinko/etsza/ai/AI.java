
package eu.janinko.etsza.ai;

import java.util.Random;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class AI {
    Callbacks cb;
    Random random;
    Agents agents;

    public AI() {
        init();
    }

    public void clear() {
        init();
    }
    
    private void init(){
        cb = new Callbacks();
        random = new Random();
        agents = new Agents(this);
    }

    public Callbacks getCallbacks() {
        return cb;
    }

    public Agents getAgents() {
        return agents;
    }

    public Random getRandom() {
        return random;
    }
    
}
