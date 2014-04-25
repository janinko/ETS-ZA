
package eu.janinko.etsza.ai.agents.brains;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.WorldConfig;
import eu.janinko.etsza.ai.agents.Actions;
import eu.janinko.etsza.ai.agents.Zombie;
import eu.janinko.etsza.ai.agents.memory.HumanMemory;
import eu.janinko.etsza.util.Vector;
import eu.janinko.etsza.util.WorldMath;
import java.util.Collection;
import java.util.Random;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class ZombieChaseBrain extends DefaultBrain<Zombie>{
    private Random r;

	public ZombieChaseBrain(Zombie owner, AI ai) {
		super(owner, ai);
        r = ai.getRandom();
	}

	@Override
	public Actions.Action perform() {
		Collection<HumanMemory> humans = owner.getMemories().getAll(HumanMemory.class).values();
		WorldConfig cfg = ai.getConfig();
		WorldMath wm = ai.getWorldMath();
		
		double x = owner.getPosX();
		double y = owner.getPosY();
		
		HumanMemory nearest = null;
		double dist = Double.MAX_VALUE;
		for(HumanMemory h : humans){ // Select the nearest human I know about.
			long age = ai.getTime() - h.getDate();
			if(age > 50) continue;
			double d = wm.distance(x, y, h.getPosx(), h.getPosy()) + cfg.getHumanSpeed() * age;
			if(d < dist){
				nearest = h;
				dist = d;
			}
		}
		
		if(nearest == null){ // Idont't know abou any human or all the memories are old.
			if(owner.getAroundH() > 0){ // Around me is a human.
				// I'l try to look around for him.
                return Actions.rotate((int) cfg.getSeeCone());
			}else{ // Around me isn't any human, I'll walk randomly.
				if(r.nextInt(4)==0){
					return Actions.rotateAndMove(WorldMath.normalizeAngle(r.nextInt(41)-20));
				}else{
					return Actions.move();
				}
			}
		}
		
		// I can attack the neareast human.
		if(dist <= cfg.getAttackDistance()){
			return Actions.attack(nearest.getId());
		}
		
		// Around me is a human and the nearest human I know about isn't around me.
		if(owner.getAroundH() > 0 && dist > cfg.getSenseDistance() + cfg.getZombieSpeed()*360/cfg.getSeeCone()){
			// I'll try to look around for the neearer one.
            return Actions.rotate((int) cfg.getSeeCone());
		}
		
		Vector v = new Vector(x, y, nearest.getPosx(), nearest.getPosy());
		double heading = owner.getHeading();
		double turn = WorldMath.normalizeAngle(v.angle() - heading);
		return Actions.rotateAndMove((int)turn);
	}
	
	
}
