
package eu.janinko.etsza.ai;

import eu.janinko.etsza.util.WorldMath;
import java.util.Random;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class AI {
    private Callbacks cb;
    private Random random;
    private Agents agents;
    private WorldConfig wc;
    private WorldMath wm;
    
    private long time;

    public AI() {
        init();
    }

    public void clear() {
        init();
    }
    
    private void init(){
        cb = new Callbacks();
        random = new Random();
        wc = new WorldConfig();
        wm = new WorldMath(wc.getWidth(), wc.getHeight());
        agents = new Agents(this);
        time = 0;
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

    public void tick() {
        time++;
    }

    public long getTime() {
        return time;
    }

    public WorldConfig getConfig() {
        return wc;
    }

    public WorldMath getWorldMath() {
        return wm;
    }

	public void setConfig(WorldConfig wc) {
		this.wc = wc;
        wm = new WorldMath(wc.getWidth(), wc.getHeight());
	}
    
}
