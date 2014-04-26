
package eu.janinko.etsza.ai.memory;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.wrapper.Turtle;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class ZombieMemory implements Memory{
    private long id;
    private double posx;
    private double posy;
    private long date;

    public ZombieMemory(Turtle human, AI ai) {
        if(human.isHuman()){
            throw new IllegalArgumentException("Provided turtle isn't zombie.");
        }
        id = human.getId();
        posx = human.getPosX();
        posy = human.getPosY();
        date = ai.getTime();
    }
    
    final public void update(Turtle human, AI ai){
        if(human.isHuman()){
            throw new IllegalArgumentException("Provided turtle isn't zombie.");
        }
        if(human.getId() != id){
            throw new IllegalArgumentException("Provided turtle doesn't have same id.");
        }
        posx = human.getPosX();
        posy = human.getPosY();
        date = ai.getTime();
    }

    public long getId() {
        return id;
    }

    public double getPosx() {
        return posx;
    }

    public double getPosy() {
        return posy;
    }

    public long getDate() {
        return date;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ZombieMemory other = (ZombieMemory) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
