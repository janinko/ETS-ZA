
package eu.janinko.etsza.ai.memory;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.wrapper.Patch;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class MemoryOfFood implements Memory{
    private long id;
    private double posx;
    private double posy;
    private double amountZ;
    private double amountH;
    private long date;

    public MemoryOfFood(Patch patch, AI ai) {
        id = patch.getId();
        posx = patch.getPosX();
        posy = patch.getPosY();
        amountZ = patch.getZFood();
        amountH = patch.getHFood();
        date = ai.getTime();
    }
    
    final public void update(Patch patch, AI ai){
        if(patch.getId() != id){
            throw new IllegalArgumentException("Provided patch doesn't have same id.");
        }
        posx = patch.getPosX();
        posy = patch.getPosY();
        amountZ = patch.getZFood();
        amountH = patch.getHFood();
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

    public double getAmountZ() {
        return amountZ;
    }

    public double getAmountH() {
        return amountH;
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
        final MemoryOfFood other = (MemoryOfFood) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
