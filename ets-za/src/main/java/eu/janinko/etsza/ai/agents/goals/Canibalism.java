
package eu.janinko.etsza.ai.agents.goals;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Zombie;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Canibalism implements Utility<Zombie>{
    private AI ai;

    private double canibalismRate;

    public Canibalism(double canibalismRate, AI ai) {
        this.canibalismRate = canibalismRate;
        this.ai = ai;
    }

    @Override
    public double getCurrentUtility(Zombie h) {
        return canibalismRate;
    }

}
