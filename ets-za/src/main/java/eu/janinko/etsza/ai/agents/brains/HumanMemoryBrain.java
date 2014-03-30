
package eu.janinko.etsza.ai.agents.brains;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.Callbacks;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.agents.memory.ZombieMemory;
import eu.janinko.etsza.util.Vector;
import eu.janinko.etsza.util.WorldMath;
import eu.janinko.etsza.wrapper.Turtle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class HumanMemoryBrain implements Brain{
    private AI ai;
    private Human owner;
    
    private HashMap<Long, ZombieMemory> zombies = new HashMap<>();

    public HumanMemoryBrain(Human owner, AI ai) {
        this.ai = ai;
        this.owner = owner;
    }

    @Override
    public void perform(Callbacks.Sensors s, Callbacks.Actuators a) {
        for(Turtle t : s.see()){
            if(!t.isHuman()){
                if(zombies.containsKey(t.getId())){
                    zombies.get(t.getId()).update(t, ai);
                }else{
                    zombies.put(t.getId(), new ZombieMemory(t, ai));
                }
            }
        }
        
        if(s.zombiesAround() == 0){
            if(owner.getId() == 1) System.out.println(owner.getId() + ": I sense nobody, turning right.");
            a.rotate(ai.getConfig().getSeeCone());
            return;
        }
        
        double desired = getAngle();
        if(Double.isNaN(desired)){
            if(owner.getId() == 1) System.out.println(owner.getId() + ": I didn't seen anybody, turning right.");
            a.rotate(ai.getConfig().getSeeCone());
            return;
        }
        
        double heading = owner.getHeading();
        double diff = Vector.angle(new Vector(desired, 1), new Vector(heading, 1));
        double margin = ai.getConfig().getSeeCone() / 6;
        if(diff < margin){ // heading probably the right way
            if(owner.getId() == 1) System.out.println(owner.getId() + ": I want to face "+ desired + ", facing " + heading + ", moving forward.");
            a.move();
            return;
        }
        
        double r = desired - heading;
        if(r < 0){
            r += 360;
        }
        if(owner.getId() == 1) System.out.println(owner.getId() + ": I want to face "+ desired + ", facing " + heading + ", turning right " + r + "°");
        a.rotate(r);
    }
    
    public double getAngle(){
        double timeParam = 1 / ai.getConfig().getZombieSpeed();
        double distanceParam = 1 / ai.getConfig().getHumanSpeed();
        double ox = owner.getPosX();
        double oy = owner.getPosY();
        long time = ai.getTime();
        
        WorldMath wm = new WorldMath(ai.getConfig().getWidth(), ai.getConfig().getHeight());
        
        List<Vector> vectors = new ArrayList<>(zombies.size());
        for(ZombieMemory z : zombies.values()){
            if(time - z.getDate() > 50) continue;
            Vector v = wm.vector(ox, oy, z.getPosx(), z.getPosy());
            if(owner.getId() == 1) System.out.println(owner.getId() + ": Zombie " +z.getId()+ " at "+ v.angle() + "°, " + v.size() + " far");
            double timeModif = timeParam / (timeParam + time - z.getDate());
            double size = distanceParam / (distanceParam + v.size());
            vectors.add(new Vector(v.angle() + 180, size * timeModif));
        }
        
        return Vector.sum(vectors).angle();
    }
    
}