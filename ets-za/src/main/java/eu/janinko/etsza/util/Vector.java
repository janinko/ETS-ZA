
package eu.janinko.etsza.util;

import java.util.Collection;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class Vector {
    
    private final double dx;
    private final double dy;

    public Vector(double fx, double fy, double tx, double ty) {
        dx = tx - fx;
        dy = ty - fy;
    }
    
    public Vector(double angle, double size){
        dy = Math.cos(angle / 180 * Math.PI) * size;
        double ldx = Math.sqrt(size*size - dy*dy);
        while(angle > 360){
            angle -= 360;
        }
        while(angle < 0){
            angle += 360;
        }
        if(angle > 180){
            ldx = -ldx;
        }
        dx = ldx;
    }
    
    public double size(){
        double in = dx*dx + dy*dy;
        return Math.sqrt(in);
    }
    
    public double angle(){
        double a = Math.acos(dy / size())*180/Math.PI;
        if(dx < 0){
            a = 360 - a;
        }
        return a;
    }

    public double dx() {
        return dx;
    }

    public double dy() {
        return dy;
    }
    
    public Vector multiply(double s){
        return new Vector(0, 0, dx * s, dy * s);
    }
    
    public static Vector sum(Collection<Vector> vectors){
        double dx = 0;
        double dy = 0;
        for(Vector v : vectors){
            dx += v.dx;
            dy += v.dy;
        }
        return new Vector(0, 0, dx, dy);
    }
    
    public static double angle(Vector v1, Vector v2){
        return Math.acos((v1.dx * v2.dx + v1.dy * v2.dy) / (v1.size() * v2.size())) * 180 / Math.PI;
    }
    
}
