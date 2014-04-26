
package eu.janinko.etsza.ai.agents.brains;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.WorldConfig;
import eu.janinko.etsza.ai.agents.Actions;
import eu.janinko.etsza.ai.agents.Zombie;
import eu.janinko.etsza.ai.agents.goals.Canibalism;
import eu.janinko.etsza.ai.agents.goals.Eat;
import eu.janinko.etsza.ai.agents.goals.Plan;
import eu.janinko.etsza.ai.agents.goals.Plan.Step;
import eu.janinko.etsza.util.WorldMath;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class ZombieGoalBasedBrain extends DefaultBrain<Zombie>{
    private Random r;

	public ZombieGoalBasedBrain(Zombie owner, AI ai) {
		super(owner, ai);
        r = ai.getRandom();
	}

	@Override
	public Actions.Action perform() {
        Eat eat = owner.getGoalEat();
        Canibalism canibalism = owner.getCanibalism();
        Set<Plan> plans = eat.getPlans(owner);

		if(plans.isEmpty()){ // Idont't have any plan.
            return noGoal();
		}

        TreeSet<Plan> sortedPlans = new TreeSet<>();
        for(Plan plan : plans){
            for(Step step : plan.getSteps()){
                if(step instanceof Plan.Attack){
                    Plan.Attack a = (Plan.Attack) step;
                    if(!a.isTargetHuman()){
                        plan.setLinking(plan.getLiking() * canibalism.getCurrentUtility(owner));
                    }
                }
            }
            sortedPlans.add(plan);
        }

        Plan plan = sortedPlans.last();
        Step step = plan.getSteps().get(0);

        if(plan.getLiking() <= 0){
            return noGoal();
        }
        if(owner.getId() == 99) System.out.println(owner.getId() + ": Wining plan: " + plan);


		WorldMath wm = ai.getWorldMath();
        
        if(step instanceof Plan.Attack){
            Plan.Attack a = (Plan.Attack) step;
            return Actions.attack(a.getTarget());
        }else if( step instanceof Plan.Move){
            Plan.Move m = (Plan.Move) step;
            double x = owner.getPosX();
            double y = owner.getPosY();
            double heading = owner.getHeading();

            double angle = wm.angle(x, y, m.getTx(), m.getTy());
            double turn = angle - heading;

            // if (owner.getId() == 99) System.out.println(owner.getId() + ": chasing in " + turn);
            return Actions.rotateAndMove((int)turn);
        }else{
            throw new IllegalArgumentException("Unknown plan step " + step);
        }
	}

    private Actions.Action noGoal(){
		WorldConfig cfg = ai.getConfig();
        //if(owner.getId() == 99) System.out.println(owner.getId() + ": I don't see anything.");
        if (owner.getAroundH() > 0) { // Around me is a human.
            // I'l try to look around for him.
            return Actions.rotate((int) cfg.getSeeCone());
        } else { // Around me isn't any human, I'll walk randomly.
            if (r.nextInt(4) == 0) {
                return Actions.rotateAndMove(WorldMath.normalizeAngle((r.nextDouble() - 0.5) * 60));
            } else {
                return Actions.move();
            }
        }
    }
	
	
}