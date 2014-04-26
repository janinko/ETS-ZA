
package eu.janinko.etsza.ai.brains;

import eu.janinko.etsza.ai.AI;
import eu.janinko.etsza.ai.agents.Actions;
import eu.janinko.etsza.ai.agents.Actions.Action;
import eu.janinko.etsza.ai.agents.Human;
import eu.janinko.etsza.ai.goals.DangerUtility;
import eu.janinko.etsza.ai.memory.MemoryOfZombie;
import eu.janinko.etsza.util.Vector;
import eu.janinko.etsza.util.WorldMath;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class HumanPathfindingBrain extends DefaultBrain<Human>{
	private static List<Vector> guessVectors = new  ArrayList<>();
	private static List<Vector> searchVectors = new  ArrayList<>();
	private Step lastTarget = null;

    public HumanPathfindingBrain(Human owner, AI ai) {
		super(owner, ai);
		
		double speed = ai.getConfig().getHumanSpeed();
		
		for(int i=0; i<360; i+=60){
			guessVectors.add(new Vector(i, speed*10));
		}
		for(int i=0; i<360; i+=5){
			searchVectors.add(new Vector(i, speed));
		}
    }

    @Override
    public Action perform() {
        return Actions.idle();/*
		DangerUtility du = owner.getDangerUtility();

		if(du == null){
			return Actions.idle();
		}
		double seeCone = ai.getConfig().getSeeCone();
		if(du.getCurrentUtility(owner) == 0){
			return Actions.rotate((int) seeCone);
		}
		
		
		double sx = owner.getPosX();
		double sy = owner.getPosY();
		Collection<MemoryOfZombie> zombies = owner.getMemories().getAll(MemoryOfZombie.class).values();
		
		Step start = new Step(sx, sy, du.getDanger(sx, sy, zombies));
		Step target = guessTarget(start, du, zombies);
		//Step star = aStar(start, target, du, zombies);
		if(owner.getId() == 1){
			System.out.println("Last target: " + lastTarget);
			System.out.println("New target: " + target);
		}
		lastTarget = target;
		
		Step next = target;
		while(next.previous != start){
			next = next.previous;
		}
		
		Vector v = new Vector(sx, sy, next.x, next.y);
		double heading = owner.getHeading();
		
		return Actions.rotateAndMove(v.angle() - heading);
    }
	
	private Step guessTarget(Step start, DangerUtility du, Collection<MemoryOfZombie> zombies){
		PriorityQueue<Step> queue = new PriorityQueue<>(100, new GuessComparator());
		queue.add(start);
		
		int counter = 0;
		Step last = queue.poll();
		long st = System.nanoTime();
		while(last.danger > du.getAcceptedDanger() && counter < 60){
			for(Vector v : guessVectors){
				queue.add(start.move(v, du, zombies));
			}
			last = queue.poll();
			counter++;
		}
		long et = System.nanoTime();
		System.out.println("Computed " + counter + " steps in " + ((et - st)/ 1000000) + " ms.");
		return last;
        */
	}
	
	
	private Step aStar(Step start, Step target, DangerUtility du, Collection<MemoryOfZombie> zombies){
		double width = ai.getConfig().getWidth();
		double height = ai.getConfig().getHeight();
		
		AStarComparator comp = new AStarComparator(target.x, target.y, new WorldMath(width, height));
		PriorityQueue<Step> queue = new PriorityQueue<>(1000, comp);
		queue.add(start);
		
		int counter = 0;
		Step last = queue.poll();
		long st = System.nanoTime();
		while(last.danger > du.getAcceptedDanger() && counter < 1000){
			for(Vector v : searchVectors){
				queue.add(start.move(v, du, zombies));
			}
			last = queue.poll();
			counter++;
		}
		long et = System.nanoTime();
		System.out.println("Computed " + counter + " steps in " + ((et - st)/ 1000000) + " ms.");
		return last;
	}

	private static class Step{
		private final double x, y;
		private final double danger;
		private final int depth;
		private final Step previous;

		public Step(double x, double y, double danger, int depth, Step previous) {
			this.x = x;
			this.y = y;
			this.danger = danger;
			this.depth = depth;
			this.previous = previous;
		}
		
		public Step(double x, double y, double danger) {
			this(x, y, danger, 0, null);
		}

		private Step move(Vector v, DangerUtility du, Collection<MemoryOfZombie> zombies) {
			return new Step(x + v.dx(), y + v.dy(), du.getDanger(x, y, zombies), depth + 1, this );
		}
		
		private double getLiking(){
			return depth * danger;
		}

		@Override
		public String toString() {
			return "Step{" + "x=" + x + ", y=" + y + ", danger=" + danger + ", depth=" + depth + '}';
		}
		
	}
	
	private static class GuessComparator implements Comparator<Step>{

		@Override
		public int compare(Step o1, Step o2) {
			return Double.compare(o1.getLiking(), o2.getLiking());
		}
		
	}
	
	private static class AStarComparator implements Comparator<Step>{
		private final double tx, ty;
		private final WorldMath wm;

		public AStarComparator(double tx, double ty, WorldMath wm) {
			this.tx = tx;
			this.ty = ty;
			this.wm = wm;
		}

		@Override
		public int compare(Step o1, Step o2) {
			double d1 = wm.distance(o1.x, o1.y, tx, ty);
			double d2 = wm.distance(o2.x, o2.y, tx, ty);
			return Double.compare(o1.getLiking() * d1, o2.getLiking() * d2);
		}
		
	}
    
}
