
package eu.janinko.etsza.util;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class VectorTest {
    
    @Test
    public void vectorFromAngleSizeTest(){
        Vector v;
        double angle, size;
               
        for(double i = 0; i <= 400; i += 0.1234){
            compareVectorFromAngleSize(i, 1);
        }    
        for(double i = -4000; i <= 4000; i += 1.234){
            compareVectorFromAngleSize(i, 1);
        }
        for(double i = 0; i <= 360; i += 12.345){
            for(double j = 0.001; j <= 100; j+= 0.832){
                compareVectorFromAngleSize(i, j);
            }
        }
    }

    public static double delta = 0.00000001;
    private void compareVectorFromAngleSize(double angle, double size) {
        Vector v = new Vector(angle, size);
        while(angle < 0){
            angle += 360;
        }
        while(angle > 360){
            angle -= 360;
        }
        if(Math.abs(v.angle() - angle) > delta){
            fail("Angle " + v.angle() + " doesn't match " + angle + "; size: " + size);
        }
        if(Math.abs(v.size()- size) > delta){
            fail("Size " + v.size() + " doesn't match " + size + "; angle: " + angle);
        }
    }
    
    @Test
    public void vectorSumTest(){
        List<Vector> vs = new ArrayList<>();
        vs.add(new Vector(0, 1));
        vs.add(new Vector(90, 1));
        compareVectorSum(vs, 45, Math.sqrt(2));
        
        vs.clear();
        vs.add(new Vector(90, 1));
        vs.add(new Vector(180, 1));
        compareVectorSum(vs, 135, Math.sqrt(2));
        
        vs.clear();
        vs.add(new Vector(180, 1));
        vs.add(new Vector(270, 1));
        compareVectorSum(vs, 225, Math.sqrt(2));
        
        vs.clear();
        vs.add(new Vector(270, 1));
        vs.add(new Vector(360, 1));
        compareVectorSum(vs, 315, Math.sqrt(2));
        
        vs.clear();
        vs.add(new Vector(13, 0.5));
        vs.add(new Vector(13, 1));
        vs.add(new Vector(13, 1.5));
        vs.add(new Vector(13, 2));
        vs.add(new Vector(13, 3));
        compareVectorSum(vs, 13, 8);
        
        vs.clear();
        vs.add(new Vector(113, 0.5));
        vs.add(new Vector(113, 1));
        vs.add(new Vector(113, 1.5));
        vs.add(new Vector(113, 2));
        vs.add(new Vector(113, 3));
        compareVectorSum(vs, 113, 8);
        
        vs.clear();
        vs.add(new Vector(263, 0.5));
        vs.add(new Vector(263, 1));
        vs.add(new Vector(263, 1.5));
        vs.add(new Vector(263, 2));
        vs.add(new Vector(263, 3));
        compareVectorSum(vs, 263, 8);
        
        vs.clear();
        vs.add(new Vector(333, 0.5));
        vs.add(new Vector(333, 1));
        vs.add(new Vector(333, 1.5));
        vs.add(new Vector(333, 2));
        vs.add(new Vector(333, 3));
        compareVectorSum(vs, 333, 8);
    }
    
    private void compareVectorSum(List<Vector> vs, double angle, double size){
        Vector v = Vector.sum(vs);
        if(Math.abs(v.angle() - angle) > delta){
            fail("Angle " + v.angle() + " doesn't match " + angle + "; size: " + size);
        }
        if(Math.abs(v.size() - size) > delta){
            fail("Size " + v.size() + " doesn't match " + size + "; angle: " + angle);
        }
    }
}
