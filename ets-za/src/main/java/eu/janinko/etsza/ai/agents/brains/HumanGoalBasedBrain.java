
package eu.janinko.etsza.ai.agents.brains;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.WorldConfig;
import eu.janinko.etsza.ai.agents.Actions;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.agents.goals.DangerUtility;
import eu.janinko.etsza.ai.agents.goals.KillZombie;
import eu.janinko.etsza.ai.agents.goals.Plan;
import eu.janinko.etsza.ai.agents.goals.Plan.Step;
import eu.janinko.etsza.ai.model.WorldModel;
import eu.janinko.etsza.util.WorldMath;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class HumanGoalBasedBrain extends DefaultBrain<Human>{
    private Random r;

	public HumanGoalBasedBrain(Human owner, AI ai) {
		super(owner, ai);
        r = ai.getRandom();
	}

	@Override
	public Actions.Action perform() {
        KillZombie kill = owner.getGoalKill();
        DangerUtility danger = owner.getDangerUtility();
        Set<Plan> plans = kill.getPlans(owner);

		if(plans.isEmpty()){ // Idont't have any plan.
            return noGoal();
		}

        TreeSet<Plan> sortedPlans = new TreeSet<>();
        for(Plan plan : plans){
            for(Step step : plan.getSteps()){
                if(step instanceof Plan.Move){
                    Plan.Move m = (Plan.Move) step;
                    WorldModel wm = new WorldModel(ai.getTime(), owner.getMemories(), m.getTx(), m.getTy(), owner.getHeading(), ai.getConfig());
                    plan.setLinking(plan.getLiking() * danger.getUtilityWhen(wm));
                }
            }
            sortedPlans.add(plan);
        }

        Plan plan = sortedPlans.last();
        Step step = plan.getSteps().get(0);

        if(plan.getLiking() <= 0){
            return noGoal();
        }
        if(owner.getId() == 1) System.out.println(owner.getId() + ": Wining plan: " + plan);


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

        if(owner.getAroundZ() == 0){
            return Actions.rotate((int) cfg.getSeeCone());
        }
        if(owner.getZombiesAhead(20,6) > 0){
            return Actions.rotate(20);
        }
        return Actions.move();
    }
	
	
}
