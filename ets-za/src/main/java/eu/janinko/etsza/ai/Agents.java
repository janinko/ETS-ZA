
package eu.janinko.etsza.ai;

import eu.janinko.etsza.ai.agents.Agent;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.agents.Zombie;
import eu.janinko.etsza.ai.goals.GoalConfig;
import eu.janinko.etsza.wrapper.Turtle;
import java.util.HashMap;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Agents {
    private AI ai;
    private HashMap<Long, Zombie> zombies = new HashMap<>();
    private HashMap<Long, Human> humans = new HashMap<>();
    private String hbrain = "BasicBrain";
    private String zbrain = "BasicBrain";
    private GoalConfig gc;

    Agents(AI ai) {
        this.ai = ai;
    }
    
    public Agent getAgent(Turtle t){
        Agent a;
        if(t.isHuman()){
            a = humans.get(t.getId());
            if(a == null){
                Human h = new Human(t, ai);
				h.setBrain(hbrain);
                humans.put(t.getId(), h);
                a = h;
            }
        }else{
            a = zombies.get(t.getId());
            if(a == null){
                Zombie z = new Zombie(t, ai);
				z.setBrain(zbrain);
                zombies.put(t.getId(), z);
                a = z;
            }
        }
        a.updateAgent(t);
        return a;
    }
	
	public Human getHuman(long id){
		return humans.get(id);
	}

	public Zombie getZombie(long id){
		return zombies.get(id);
	}

	public Agent getAgent(long id){
        if(zombies.containsKey(id)){
            return zombies.get(id);
        }else{
            return humans.get(id);
        }
	}

    public void setDefaultBrains(String hbrain, String zbrain) {
        this.hbrain = hbrain;
        this.zbrain = zbrain;
    }

    public void setGoalConfig(GoalConfig gc) {
        this.gc = gc;
    }

    public GoalConfig getGoalConfig() {
        return gc;
    }
}
