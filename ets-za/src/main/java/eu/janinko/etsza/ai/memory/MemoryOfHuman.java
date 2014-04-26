
package eu.janinko.etsza.ai.memory;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.wrapper.Turtle;

/**
 * @author Honza Brázdil <janinko.g@gmail.com>
 * @author Jakub Senko <jsenko@mail.muni.cz>
 */
public class MemoryOfHuman extends MemoryOfTurtle implements Memory {
    boolean infected;

    public MemoryOfHuman(Turtle human, AI ai) {
        super(human, ai.getTime());

        if (!human.isHuman()) {
            throw new IllegalArgumentException("Provided turtle isn't human.");
        }
    }


    public void update(Turtle human, AI ai) {
        if (!human.isHuman()) {
            throw new IllegalArgumentException("Provided turtle isn't human.");
        }
        if (human.getId() != id) {
            throw new IllegalArgumentException("Provided turtle doesn't have same id.");
        }
        infected = human.isInfected();
        super.update(human, ai.getTime());
    }

    public boolean isInfected() {
        return infected;
    }

}
