package eu.janinko.etsza.ai;

import eu.janinko.etsza.wrapper.Patch;
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
    CommandTask shoot;
    CommandTask eat;

    ReporterTask aroundZ;
    ReporterTask aroundH;
    ReporterTask see;
    ReporterTask seePatches;

    public void setMove(CommandTask commandTask) {
        move = commandTask;
    }

    public void setRotate(CommandTask commandTask) {
        rotate = commandTask;
    }

    public void setEat(CommandTask eat) {
        this.eat = eat;
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

    public void setSeePatches(ReporterTask seePatches) {
        this.seePatches = seePatches;
    }

    public void setShoot(CommandTask commandTask) {
        this.shoot = commandTask;
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

        public int zombiesAround(long id) {
            return ((Double) aroundZ.report(ctx, new Object[]{(double) id})).intValue();
        }

        public int humansAround(long id) {
            return ((Double) aroundH.report(ctx, new Object[]{(double) id})).intValue();
        }

        public Set<Turtle> see(long id) {
            Set<Turtle> ret = new HashSet<>();
            AgentSet agents = (AgentSet) see.report(ctx, new Object[]{(double) id});
            for (Agent agent : agents.agents()) {
                ret.add(new Turtle((org.nlogo.api.Turtle) agent));
            }
            return ret;
        }

        public Set<Patch> seePatches(long id) {
            Set<Patch> ret = new HashSet<>();
            AgentSet agents = (AgentSet) seePatches.report(ctx, new Object[]{(double) id});
            for (Agent agent : agents.agents()) {
                ret.add(new Patch((org.nlogo.api.Patch) agent));
            }
            return ret;
        }
    }
    
    public class Actuators {
        private Context ctx;
        
        private Actuators(){};

        private Actuators(Context ctx) {
            this.ctx = ctx;
        }
        
        public synchronized void rotate(Double i){
            rotate.perform(ctx, new Object[] {i});
        }

        public synchronized void move(){
            move.perform(ctx, new Object[] {});
        }

        public synchronized void eat(){
            eat.perform(ctx, new Object[] {});
        }

        public synchronized void attack(long id) {
            attack.perform(ctx, new Object[] {(double) id});
        }

        public synchronized void shoot(long id) {
            attack.perform(ctx, new Object[] {(double) id});
        }
    }
}
