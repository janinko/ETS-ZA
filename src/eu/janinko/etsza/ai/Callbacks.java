package eu.janinko.etsza.ai;

import eu.janinko.etsza.wrapper.Turtle;
import org.nlogo.api.CommandTask;
import org.nlogo.api.Context;
import org.nlogo.api.ReporterTask;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Callbacks {

    CommandTask move;
    CommandTask rotate;
    ReporterTask aroundZ;
    ReporterTask aroundH;
    ReporterTask see;

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

    public void rotate(Turtle turtle, Context ctx, Integer i) {
        rotate.perform(ctx, new Object[] {i});
    }

    public void move(Turtle turtle, Context ctx) {
        move.perform(ctx, new Object[] {});
    }

}
