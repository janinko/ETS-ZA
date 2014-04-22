
package eu.janinko.etsza.util;

/**
 *
 * @author Honza Brázdil <janinko.g@gmail.com>
 */
public class WorldMath {
    private double width;
    private double height;
    private double widthH;
    private double heightH;

    public WorldMath(double width, double height) {
        this.width = width;
        this.height = height;
        widthH = width / 2;
        heightH = height / 2;
    }
    
    public static double angleDiff(double a1, double a2){
        while(a1 > 360) a1 -= 360;
        while(a1 < 0) a1 += 360;
        while(a2 > 360) a2 -= 360;
        while(a2 < 0) a2 += 360;
        double diff = Math.abs(a1 - a2);
        while(diff > 180){
            diff = 360 - diff;
        }
        return diff;
    }
	
	public static int normalizeAngle(int angle){
		while(angle >= 360){
			angle -= 360;
		}
		while(angle < 0){
			angle += 360;
		}
		return angle;
	}
	
	public static double normalizeAngle(double angle){
		while(angle >= 360){
			angle -= 360;
		}
		while(angle < 0){
			angle += 360;
		}
		return angle;
	}
    
    public static double angleSum(double a1, double a2){
        double r = a1 + a2;
        while(r > 360) r -= 360;
        while(r < 0) r += 360;
        return r;
    }

    public double distance(double fx, double fy, double tx, double ty){
        double dx = Math.abs(tx - fx);
        double dy = Math.abs(ty - fy);
        if(dx > widthH){
            dx = width - dx;
        }
        if(dy > heightH){
            dy = height - dy;
        }
        return Math.sqrt(dx*dx+dy*dy);
    }
    
    public double angle(double fx, double fy, double tx, double ty){
        double dx = tx - fx;
        double dy = ty - fy;
        
        if(dx < -widthH){
            dx = dx + width;
        }else if(dx > widthH){
            dx = dx - width;
        }
        if(dy < -heightH){
            dy = dy + height;
        }else if(dy > heightH){
            dy = dx - height;
        }
        return Math.acos(dy / Math.sqrt(dx*dx + dy*dy))*180/Math.PI;
    }
    
    public Vector vector(double fx, double fy, double tx, double ty){
        double dx = tx - fx;
        double dy = ty - fy;
        
        if(dx < -widthH){
            dx = dx + width;
        }else if(dx > widthH){
            dx = dx - width;
        }
        if(dy < -heightH){
            dy = dy + height;
        }else if(dy > heightH){
            dy = dx - height;
        }
        return new Vector(0, 0, dx, dy);
    }
}
