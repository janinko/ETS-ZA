package eu.janinko.etsza.ai;

import eu.janinko.etsza.wrapper.Turtle;
import java.util.HashSet;
import java.util.Set;
import org.nlogo.api.Agent;
import org.nlogo.api.AgentSet;
import org.nlogo.api.CommandTask;
import org.nlogo.api.Context;
import org.nlogo.api.ReporterTask;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 * @author Jakub Senko <373902@mail.muni.cz>
 */
public class Callbacks {

    CommandTask move;
    CommandTask rotate;
    CommandTask attack;

    ReporterTask aroundZ;
    ReporterTask aroundH;
    ReporterTask see;
    ReporterTask canAttack;


    public void setMove(CommandTask commandTask) {
        move = commandTask;
    }

    public void setRotate(CommandTask commandTask) {
        rotate = commandTask;
    }

    public void setAroundZ(ReporterTask reporterTask) {
        aroundZ = reporterTask;
    }

    public void setAroundH(ReporterTask reporterTask) {
        aroundH = reporterTask;
    }

    public void setSee(ReporterTask reporterTask) {
        see = reporterTask;
    }

    public void setAttack(CommandTask commandTask) {
        attack = commandTask;
    }

    public void setCanAttack(ReporterTask canAttack) {
        this.canAttack = canAttack;
    }

    public Sensors getSensors(Context ctx){
        return new Sensors(ctx);
    }
    
    public Actuators getActuators(Context ctx){
        return new Actuators(ctx);
    }
    
    public class Sensors {
        private Context ctx;
        
        private Sensors(){};

        private Sensors(Context ctx) {
            this.ctx = ctx;
        }
        
        public int zombiesAround(){
            return ((Double) aroundZ.report(ctx, new Object[0])).intValue();
        }
        
        public int humansAround(){
            return ((Double) aroundH.report(ctx, new Object[0])).intValue();
        }
        
        public Set<Turtle> see(){
            Set<Turtle> ret = new HashSet<>();
            AgentSet agents = (AgentSet) see.report(ctx, new Object[0]);
            for(Agent agent : agents.agents()){
                ret.add(new Turtle((org.nlogo.api.Turtle) agent));
            }
            return ret;
        }

        public boolean canAttack(Turtle turtle) {
            return (Boolean) canAttack.report(ctx, new Object[] {turtle.getNLTurtle()});
        }
    }
    
    public class Actuators {
        private Context ctx;
        
        private Actuators(){};

        private Actuators(Context ctx) {
            this.ctx = ctx;
        }
        
        public void rotate(Double i){
            rotate.perform(ctx, new Object[] {i});
        }
        
        
        public void move(){
            move.perform(ctx, new Object[] {});
        }

        public void attack(Turtle agent) {
            attack.perform(ctx, new Object[] {agent.getNLTurtle()});
        }
    }
}
