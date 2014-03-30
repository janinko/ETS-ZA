
package eu.janinko.etsza.ai;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class WorldConfig {
    private double seeDistance = 6;
    private double seeCone = 60;
    private double senseDistance = 3;
    private double zombieSpeed = 0.05;
    private double humanSpeed = 0.1;
    private double width = 30;
    private double height = 30;

    public double getSeeDistance() {
        return seeDistance;
    }

    public double getSeeCone() {
        return seeCone;
    }

    public double getSenseDistance() {
        return senseDistance;
    }

    public double getZombieSpeed() {
        return zombieSpeed;
    }

    public double getHumanSpeed() {
        return humanSpeed;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
    
}
