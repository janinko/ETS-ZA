
package eu.janinko.etsza.ai.memory;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.wrapper.Patch;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class MemoryOfAmmo implements Memory{
    private long id;
    private double posx;
    private double posy;
    private double amount;
    private long date;

    public MemoryOfAmmo(Patch patch, AI ai) {
        id = patch.getId();
        posx = patch.getPosX();
        posy = patch.getPosY();
        amount = patch.getAmmoBoxes();
        date = ai.getTime();
    }
    
    final public void update(Patch patch, AI ai){
        if(patch.getId() != id){
            throw new IllegalArgumentException("Provided patch doesn't have same id.");
        }
        posx = patch.getPosX();
        posy = patch.getPosY();
        amount = patch.getAmmoBoxes();
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

    public double getAmount() {
        return amount;
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
        final MemoryOfAmmo other = (MemoryOfAmmo) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
