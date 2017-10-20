/*************************************************************************
 * Compilation:  javac Point.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point>{

    public final static Comparator<Point> BY_SLOPE_ORDER = new Comparator<Point>(){
      
        public int compare(Point a, Point b){
            
            double rad1 = a.getAngle();
            double rad2 = b.getAngle();
            //3) do comparison. if a > b, return 1, a < b return -1, else 0
            if(rad1 > rad2){
                return 1;
            }
            if(rad1 < rad2){
                return -1;
            }else{
                return 0;
            }
            //they are equal 
            
        
        }

       
    };
     
    private final int x;                              // x coordinate
    private final int y; 
    private double angle;                              // y coordinate

    // constructor
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
       
    }
        
     public int getX(){
         return this.x;
     }

     public int getY(){
         return this.y;
     }

     public void setAngle(double angle){
         this.angle = angle;
     }

     public double getAngle(){
         return this.angle;
     }

    //  public static double getAngle(Point p){
    //         double op_1 = (double) p.getY();
    //         double adj_1 = (double)p.getX();
    //         double ratio_1 = op_1/adj_1;
    //        // double rad1 = Math.atan(ratio_1);
    //        double rad1 = Math.atan2(op_1,adj_1);
    //         return rad1;
    //  }

     
    // are the 3 points p, q, and r collinear?
    public static boolean areCollinear(Point p, Point q, Point r) {
        //do these points fall on the same line?
        //1) compute slope between first two points
        //2) slope between first and 3rd point, if == 1, then return true

        //error check for divide by zero, verticle line
        if((q.x-p.x) == 0 ){
                if(r.x-p.x == 0){
                    return true; 
                }
                return false; 
        }
        if((r.x-p.x)==0){
            if(q.x-p.x == 0){
                return true;
            }
            return false;
        }
        
        double slope1 = ((double)q.y-p.y)/((double)q.x-p.x);
        double slope2 = ((double)r.y-p.y)/((double)r.x-p.x);
        //take absolute value or no??
        return (slope1 == slope2);
    }

     public static boolean areEqual(Point p, Point q, Point r){
        if(p.x != q.x || p.y !=q.y){
            return false; 
        }
        if(p.x !=r.x || p.y != r.y){
            return false; 
        }
       return true; 
    }

    public static boolean areEqual(Point p, Point q, Point r, Point s){
        if(p.x != q.x || p.y !=q.y){
            return false; 
        }
        if(p.x !=r.x || p.y != r.y){
            return false; 
        }
        if(p.x != s.x || p.y !=s.y){
            return false;
        }

        return true; 
    }
    // are the 4 points p, q, r, and s collinear?
    public static boolean areCollinear(Point p, Point q, Point r, Point s) {
        //check first three, if no, return false there
        //if ye, check slope between s and p, and compare to slope of r and plane
        
        //testing
        // if(p.x==1234 || q.x ==1234 || r.x == 1234 || s.x==1234){
        //     StdOut.println("**TESTING ERROR**");
        //     double slope_1 = ((double) s.y-p.y)/((double) s.x-p.x);
        //     double slope_2 = ((double) r.y-p.y)/((double) r.x-p.x);
        //     double slope_3 = ((double) q.y - p.y)/((double) q.x-p.x);
        //     StdOut.println("SLOPE_1: "+ slope_1);
        //     StdOut.println("SLOPE_2: "+ slope_2);
        //     StdOut.println("SLOPE_3: "+ slope_3);
        //     System.exit(0);
        // }

        //check if all the points are the same
        boolean equal = areEqual(p,q,r,s);

        if(equal){
            return false;
        }

        if(p.x==q.x && p.y == q.y){
            return false;
        }

        if(p.x == r.x && p.y == r.y){
            return false;
        }

        if(p.x == s.x && p.y == s.y){
            return false;
        }

        if( q.x == r.x && q.y == r.y){
            return false;
        }
        if(q.x == s.x && q.y == s.y){
            return false;
        }

        if(s.x == r.x && s.y == r.y){
            return false;
        }

        if(!areCollinear(p,q,r)){
            return false;
        }

        if((s.x-p.x) ==0 && (q.x-p.x)==0 && (r.x-p.x ==0)){
            return true;
        }

        if((s.x-p.x) ==0 || (q.x-p.x)==0 || (r.x-p.x ==0)){
            return false;
        }
        double slope_s = ((double) s.y - p.y)/((double) s.x-p.x);
        double other = ((double) r.y - p.y)/((double) r.x-p.x);

        return (slope_s == other);
    }

    // is this point lexicographically smaller than that one?
    //by x value first, then y value
    public int compareTo(Point that) {
    
        if(this.x < that.x){//check x first
            return -1;
        }
        if(this.x > that.x){//x first
            return 1;
        }
        if( this.y < that.y){//x vals are = so check y
            return -1;
        }
        if(this. y > that.y){ //more y
            return 1;
        }


        //points are the same, return 0
        return 0;
    }

    
   

}
    