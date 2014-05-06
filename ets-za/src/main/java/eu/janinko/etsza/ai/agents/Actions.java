
package eu.janinko.etsza.ai.agents;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Actions {
    public enum Type{
        Move,
        Rotate,
        RotateAndMove,
		Attack,
		Eat,
        Idle;
    }

    private static final Move move = new Move();
    public static Move move(){
        return move;
    }

    private static final Eat eat = new Eat();
    public static Eat eat(){
        return eat;
    }
    
    private static final Idle idle = new Idle();
    public static Idle idle(){
        return idle;
    }
    
    public static Rotate rotate(int degree){
        return new Rotate(degree);
    }
    
    public static RotateAndMove rotateAndMove(double degree){
        return new RotateAndMove(degree);
    }

    public static Attack attack(long id){
        return new Attack(id);
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
    public static class Eat implements Action{
        private Eat(){};

        @Override
        public Type getType() {
            return Type.Eat;
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
    public static class RotateAndMove implements Action{
        private final double degree;
        private RotateAndMove(double degree){
            this.degree = degree;
        };

        @Override
        public Type getType() {
            return Type.RotateAndMove;
        }

        public double getDegree() {
            return degree;
        }
    }
    public static class Attack implements Action{
        private final long id;
        private Attack(long id){
            this.id = id;
        }

        @Override
        public Type getType() {
            return Type.Attack;
        }

        public long getId() {
            return id;
        }
    }
}
