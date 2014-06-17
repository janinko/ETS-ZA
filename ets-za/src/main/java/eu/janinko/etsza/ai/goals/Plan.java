
package eu.janinko.etsza.ai.goals;

import eu.janinko.etsza.ai.goals.steps.Step;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Plan implements Comparable<Plan>{
    private final List<Step> steps = new ArrayList<>();
    private double liking;

    public void add(Step step){
        steps.add(step);
    }

    public void setLinking(double liking) {
        this.liking = liking;
    }

    public double getLiking() {
        return liking;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public Plan() {
    }

    @Override
    public int compareTo(Plan o) {
        if(liking == o.liking){
            return Double.compare(steps.get(0).getLiking(), o.steps.get(0).getLiking());
        }
        return Double.compare(liking, o.liking);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Liking: ");
        sb.append(liking);
        for(Step step : steps){
            sb.append("\n  ");
            sb.append(step);
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.steps);
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.liking) ^ (Double.doubleToLongBits(this.liking) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Plan other = (Plan) obj;
        if (!Objects.equals(this.steps, other.steps)) return false;
        if (Double.doubleToLongBits(this.liking) != Double.doubleToLongBits(other.liking))
            return false;
        return true;
    }

}
