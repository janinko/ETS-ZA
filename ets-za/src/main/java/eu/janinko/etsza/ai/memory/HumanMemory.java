
package eu.janinko.etsza.ai.memory;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.wrapper.Turtle;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class HumanMemory implements Memory{
    private long id;
    private double posx;
    private double posy;
    private long date;

    public HumanMemory(Turtle human, AI ai) {
        if(!human.isHuman()){
            throw new IllegalArgumentException("Provided turtle isn't human.");
        }
        id = human.getId();
        posx = human.getPosX();
        posy = human.getPosY();
        date = ai.getTime();
    }
    
    final public void update(Turtle human, AI ai){
        if(!human.isHuman()){
            throw new IllegalArgumentException("Provided turtle isn't human.");
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
        int hash = 7;
        hash = 61 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final HumanMemory other = (HumanMemory) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
