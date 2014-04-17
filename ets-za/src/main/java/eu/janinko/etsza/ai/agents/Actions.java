
package eu.janinko.etsza.ai.agents;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Actions {
    public enum Type{
        Move,
        Rotate,
        Idle;
    }
    
    private static final Move move = new Move();
    public static Move move(){
        return move;
    }
    
    private static final Idle idle = new Idle();
    public static Idle idle(){
        return idle;
    }
    
    private static final Rotate[] rotates = new Rotate[360];
    public static Rotate rotate(int degree){
        if(rotates[degree] == null){
            rotates[degree] = new Rotate(degree);
        }
        return rotates[degree];
    }
    
    public interface Action{
        Type getType();
    }
    public static class Move implements Action{
        private Move(){};

        @Override
        public Type getType() {
            return Type.Move;
        }
    }
    public static class Idle implements Action{
        private Idle(){};

        @Override
        public Type getType() {
            return Type.Idle;
        }
    }
    public static class Rotate implements Action{
        private final int degree;
        private Rotate(int degree){
            this.degree = degree;
        };

        @Override
        public Type getType() {
            return Type.Rotate;
        }

        public int getDegree() {
            return degree;
        }
    }
}
