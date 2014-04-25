
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
    private double width = 43;
    private double height = 26;
	private double attackDistance = 1;

	public WorldConfig() {
	}

	public WorldConfig(
			double seeDistance,
			double seeCone,
			double senseDistance,
			double zombieSpeed,
			double humanSpeed,
			double width,
			double height,
			double attackDistance) {
		this.seeDistance = seeDistance;
		this.seeCone = seeCone;
		this.senseDistance = senseDistance;
		this.zombieSpeed = zombieSpeed;
		this.humanSpeed = humanSpeed;
		this.width = width;
		this.height = height;
		this.attackDistance = attackDistance;
	}

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

	public double getAttackDistance() {
		return attackDistance;
	}
    
}
