
package eu.janinko.etsza.ai;

import eu.janinko.etsza.ai.Callbacks.Sensors;
import eu.janinko.etsza.ai.agents.Agent;
import eu.janinko.etsza.util.WorldMath;
import eu.janinko.etsza.wrapper.Turtle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.nlogo.api.Context;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class AI {
    private static final ExecutorService es = Executors.newFixedThreadPool(10);

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

    public void think(Set<Turtle> turtles, Context ctx) {
        Sensors s = cb.getSensors(ctx);
        List<Future<?>> futures = new ArrayList<>(turtles.size());

        for(Turtle turtle : turtles){
            Agent a = agents.getAgent(turtle);
            a.sense(s);
            AgentThinker at = new AgentThinker(a);
            futures.add(es.submit(at));
        }
        for(Future<?> future : futures){
            try {
                future.get(); // Wait for completition
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(AI.class.getName()).log(Level.SEVERE, "Thinking interupted.", ex);
            }
        }
    }

    private static class AgentThinker implements Runnable{
        private final Agent a;

        public AgentThinker(Agent a) {
            this.a = a;
        }

        @Override
        public void run() {
            a.think();
        }
    }
}
