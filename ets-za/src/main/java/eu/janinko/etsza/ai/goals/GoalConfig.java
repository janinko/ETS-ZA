
package eu.janinko.etsza.ai.goals;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class GoalConfig {

    private double humanAttack;
    private double humanEat;
    private double killInfected;
    private double pickupAmmo;
    private double shootZombie;
    private double avoidZombie;
    private double fear;
    private double saveAmmo;
    private double stay;

    public GoalConfig(double humanAttack, double humanEat, double killInfected, double pickupAmmo, double shootZombie, double avoidZombie, double fear, double saveAmmo, double stay) {
        this.humanAttack = humanAttack;
        this.humanEat = humanEat;
        this.killInfected = killInfected;
        this.pickupAmmo = pickupAmmo;
        this.shootZombie = shootZombie;
        this.avoidZombie = avoidZombie;
        this.fear = fear;
        this.saveAmmo = saveAmmo;
        this.stay = stay;
    }

    public double getHumanAttack() {
        return humanAttack;
    }

    public double getHumanEat() {
        return humanEat;
    }

    public double getKillInfected() {
        return killInfected;
    }

    public double getPickupAmmo() {
        return pickupAmmo;
    }

    public double getShootZombie() {
        return shootZombie;
    }

    public double getAvoidZombie() {
        return avoidZombie;
    }

    public double getFear() {
        return fear;
    }

    public int getSaveAmmo() {
        return (int) saveAmmo;
    }

    public double getStay() {
        return stay;
    }

    @Override
    public String toString() {
        return "GoalConfig{" + "humanAttack=" + humanAttack + ", humanEat=" + humanEat + ", killInfected=" + killInfected + ", pickupAmmo=" + pickupAmmo + ", shootZombie=" + shootZombie + ", avoidZombie=" + avoidZombie + ", fear=" + fear + ", saveAmmo=" + saveAmmo + ", stay=" + stay + '}';
    }
}
