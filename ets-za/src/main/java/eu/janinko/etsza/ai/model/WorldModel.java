
package eu.janinko.etsza.ai.model;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.WorldConfig;
import eu.janinko.etsza.ai.agents.Agent;
import eu.janinko.etsza.ai.agents.memory.Memories;
import eu.janinko.etsza.util.Vector;
import eu.janinko.etsza.util.WorldMath;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class WorldModel {
    private final long tick;
    private final Memories memories;
    private final double posx;
    private final double posy;
    private final double heading;
    private final WorldConfig config;

    public WorldModel(Agent a, AI ai) {
        this.tick = ai.getTime();
        this.memories = a.getMemories();
        this.posx = a.getPosX();
        this.posy = a.getPosY();
        this.heading = a.getHeading();
        this.config = ai.getConfig();
    }

    public WorldModel(long tick, Memories memories, double posx, double posy, double heading, WorldConfig config) {
        this.tick = tick;
        this.memories = memories;
        this.posx = posx;
        this.posy = posy;
        this.heading = heading;
        this.config = config;
    }
    
    public WorldModel idle(){
        return new WorldModel(tick + 1, memories, posx, posy, heading, config);
    }
    
    public WorldModel move(){
        Vector v = new Vector(heading, config.getHumanSpeed());
        return new WorldModel(tick + 1, memories, posx + v.dx(), posy + v.dy(), heading, config);
    }
    
    public WorldModel rotate(double degree){
        double angle = WorldMath.angleSum(heading, degree);
        return new WorldModel(tick + 1, memories, posx, posy, angle, config);
    }

    public long getTick() {
        return tick;
    }

    public Memories getMemories() {
        return memories;
    }

    public double getPosx() {
        return posx;
    }

    public double getPosy() {
        return posy;
    }

    public double getHeading() {
        return heading;
    }
    
}
