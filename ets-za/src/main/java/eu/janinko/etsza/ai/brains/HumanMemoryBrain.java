
package eu.janinko.etsza.ai.brains;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Actions;
import eu.janinko.etsza.ai.agents.Actions.Action;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.memory.MemoryOfZombie;
import eu.janinko.etsza.util.Vector;
import eu.janinko.etsza.util.WorldMath;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class HumanMemoryBrain extends DefaultBrain<Human>{

    public HumanMemoryBrain(Human owner, AI ai) {
		super(owner, ai);
    }

    @Override
    public Action perform() {
        Map<Long, MemoryOfZombie> zombies = owner.getMemories().getAll(MemoryOfZombie.class);
        
        if(owner.getAroundZ() == 0){
            //if(owner.getId() == 1) System.out.println(owner.getId() + ": I sense nobody, turning right.");
            return Actions.rotate((int) ai.getConfig().getSeeCone());
        }
        
        double desired = getAngle();
        double heading = owner.getHeading();

        return Actions.rotateAndMove(desired - heading);
    }
    
    public double getAngle(){
        double ox = owner.getPosX();
        double oy = owner.getPosY();
        double turnSpeed = 5;
        double hspeed = ai.getConfig().getHumanSpeed();
        double heading = owner.getHeading();
        
        
        /*for(MemoryOfZombie z : zombies.values()){
            long age = ai.getTime() - z.getDate();
            if(owner.getId() == 1) System.out.println(owner.getId() + ": Zombie " +z.getId()+ " at "+ z.getPosX() + ", " + z.getPosY() + " was there in " + age + " ticks.");
        }*/
        
        if(owner.getId() == 1) printDanger();
            
        
        Vector toAhead = new Vector(heading, hspeed);
        double dangerAhead = getDanger(ox + toAhead.dx(), oy + toAhead.dy());
        Vector toLeft = new Vector(heading - turnSpeed, hspeed);
        double dangerLeft = getDanger(ox + toLeft.dx(), oy + toLeft.dy());
        Vector toRight = new Vector(heading + turnSpeed, hspeed);
        double dangerRight = getDanger(ox + toRight.dx(), oy + toRight.dy());
        
        double angle = heading;
        double danger = dangerAhead;
        if(dangerAhead > dangerLeft || dangerAhead > dangerRight){
            double diff;
            double newDanger;
            if(dangerLeft < dangerRight){
                diff = -turnSpeed;
                newDanger = dangerLeft;
            }else{
                diff = +turnSpeed;
                newDanger = dangerRight;
            }
            while(newDanger < danger){
                angle += diff;
                danger = newDanger;
                
                double newAngle = angle + diff;
                Vector to = new Vector(newAngle, hspeed);
                newDanger = getDanger(ox + to.dx(), oy + to.dy());
            }
        }
        
        /*if(owner.getId() == 1) System.out.println(owner.getId() + ": I am at "
                + ox + ", " + oy + ", heading " + owner.getHeading()
                + ". Danger ahead: " + dangerAhead
                + ", danger at angle: " + angle + ": " + danger);*/
        return angle;
    }
    
    public double getDanger(double x, double y){
        Map<Long, MemoryOfZombie> zombies = owner.getMemories().getAll(MemoryOfZombie.class);
        double distanceParam = 1 / ai.getConfig().getZombieSpeed();
        double agingParam = 20;
        long time = ai.getTime();
        WorldMath wm = new WorldMath(ai.getConfig().getWidth(), ai.getConfig().getHeight());
        
        double ret = 0;
        for(MemoryOfZombie z : zombies.values()){
            long age = time - z.getDate();
            if(age > 50) continue;
            double timemodif = agingParam / (agingParam + age);
            double danger = distanceParam / (distanceParam + wm.distance(x, y, z.getPosX(), z.getPosY()));
            ret += danger * timemodif;
        }
        return ret;
    }
    
    private void printDanger(){
        try(BufferedWriter fw = new BufferedWriter(new FileWriter("/tmp/data_" + ai.getTime()))){
            for(double x=0; x < 30; x+=0.5){
                for(double y=0; y < 30; y+=0.5){
                    fw.write(x + "\t" + y + "\t" + getDanger(x, y));
                    fw.newLine();
                }
                fw.newLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(HumanMemoryBrain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}