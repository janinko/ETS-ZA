
package eu.janinko.etsza.ai.agents.brains;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Actions;
import eu.janinko.etsza.ai.agents.Agent;
import eu.janinko.etsza.ai.agents.goals.Goal;
import eu.janinko.etsza.ai.agents.goals.Plan;
import eu.janinko.etsza.ai.agents.goals.Utility;
import eu.janinko.etsza.util.WorldMath;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public abstract class DefaultGoalBasedBrain<T extends Agent> extends DefaultBrain<T>{

	public DefaultGoalBasedBrain(T owner, AI ai) {
		super(owner, ai);
	}

    @Override
    public Actions.Action perform() {
        Set<Plan> plans = new HashSet<>();
        for(Goal g : owner.getGoals()){
            plans.addAll(g.getPlans(owner));
        }
        if(plans.isEmpty()){ // Idont't have any plan.
            return noGoal();
		}
        
        TreeSet<Plan> sortedPlans = new TreeSet<>();
        for(Plan plan : plans){
            for(Utility u : owner.getUtilities()){
                u.updatePlan(plan, owner);
            }
            sortedPlans.add(plan);
        }

        Plan plan = sortedPlans.last();

        if(plan.getLiking() <= 0){
            return noGoal();
        }
        if(owner.getId() == 1 || owner.getId() == 99) System.out.println(owner.getId() + ": Wining plan: " + plan);
        Plan.Step step = plan.getSteps().get(0);

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

    protected abstract Actions.Action noGoal();
}
