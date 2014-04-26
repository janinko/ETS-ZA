
package eu.janinko.etsza.ai.memory;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.wrapper.Turtle;

/**
 * @author Honza Brázdil <janinko.g@gmail.com>
 * @author Jakub Senko <jsenko@mail.muni.cz>
 */
public class MemoryOfZombie extends MemoryOfTurtle implements Memory {

    public MemoryOfZombie(Turtle human, AI ai) {
        super(human, ai.getTime());

        if (human.isHuman()) {
            throw new IllegalArgumentException("Provided turtle isn't zombie.");
        }
    }

    final public void update(Turtle human, AI ai) {
        if (human.isHuman()) {
            throw new IllegalArgumentException("Provided turtle isn't zombie.");
        }
        if (human.getId() != id) {
            throw new IllegalArgumentException("Provided turtle doesn't have same id.");
        }

        update(human, ai.getTime());
    }
}
