
package eu.janinko.etsza.ai.agents.brains;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Actions;
import eu.janinko.etsza.ai.agents.Actions.Action;
import eu.janinko.etsza.ai.agents.Actions.Rotate;
import eu.janinko.etsza.ai.agents.Actions.RotateAndMove;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.model.WorldModel;
import java.util.ArrayDeque;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class HumanSpaceSearchBrain extends DefaultBrain<Human>{

    public HumanSpaceSearchBrain(Human owner, AI ai) {
		super(owner, ai);
    }

    @Override
    public Actions.Action perform() {
        WorldModel wm = new WorldModel(owner, ai);
        PathStep start = new PathStep(wm);
        return computeBFS(start);
    }

    private Action computeBFS(PathStep start) {
        ArrayDeque<PathStep> queue = new ArrayDeque<>();
        queue.add(start);
        while(!queue.isEmpty()){
            PathStep step = queue.poll();
            if(step.getDepth() > 5){
                continue;
            }
            
            queue.add(step.next(Actions.idle()));
            for(int i=0; i<360; i+=10){
                queue.add(step.next(Actions.rotateAndMove(i)));
            }
        }
		return null;
    }
    
    
    private static class PathStep {
        private final PathStep previous;
        private final WorldModel wm;
        private final Action action;
        private final int depth;
        
        public PathStep(WorldModel wm){
            this.wm = wm;
            this.depth = 0;
            this.previous = null;
            this.action = null;
        }

        private PathStep(PathStep previous, WorldModel wm, Action action) {
            this.previous = previous;
            this.wm = wm;
            this.action = action;
            this.depth = previous.depth + 1;
        }
        
        public PathStep next(Action action){
            WorldModel nwm;
            switch(action.getType()){
                case Idle: nwm = wm.idle(); break;
                case RotateAndMove: nwm = wm.rotate(((RotateAndMove) action).getDegree()); wm.move(); break;
                case Rotate: nwm = wm.rotate(((Rotate) action).getDegree()); break;
                default: throw new IllegalArgumentException("Unexpected action type: " + action);
            }
            return new PathStep(this, wm, action);
        }

        public int getDepth() {
            return depth;
        }
    }
}
