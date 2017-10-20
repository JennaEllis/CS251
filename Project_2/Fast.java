import java.util.*;
import java.io.*;
public class Fast{

    public static void main(String args[]){
        int num_points = StdIn.readInt();

        Point[] points = new Point[num_points];
        double start = System.currentTimeMillis();
        //load the points from input
        for(int i = 0; i < num_points; i++){
            int x = StdIn.readInt();
            int y = StdIn.readInt();

            Point new_point = new Point(x,y);
            points[i] = new_point;
        }

        //make copy of points[i]
        Point[] temp_copy = new Point[points.length];
        for(int i = 0; i < points.length; i++){
            temp_copy[i] = new Point(points[i].getX(), points[i].getY());
        }
       
        try{
            File print_to = new File("visualPoints.txt");
            PrintWriter p = new PrintWriter(print_to);

            for(int i = 0; i < points.length; i++){
                Point compare = temp_copy[i];
                //StdOut.println(compare.getX()+"  "+compare.getY());
                for(int j = 0; j < points.length; j++){

                    if(compare.getX() == points[j].getX() && compare.getY() == points[j].getY()){
                        points[j].setAngle(-33000);
                        continue;
                    }
                    else{
                        Point [] pts = new Point[2];
                        pts[0] = compare; 
                        pts[1] = points[j];
                    
                        Arrays.sort(pts);
                        double angle = (Math.atan2( ( pts[1].getY() - pts[0].getY()),(pts[1].getX() - pts[0].getX()))); //mabs
                        points[j].setAngle(angle);
                    }
                }
            
            Arrays.sort(points, Point.BY_SLOPE_ORDER);
            
            print_points(points, compare, p);  
        }
        p.close();
        }catch(IOException ioe){ ioe.printStackTrace();};
        
       double end = System.currentTimeMillis();

       double elapsed_mill = (end - start);

       double seconds = elapsed_mill/1000;

       //StdOut.println("Time elapsed Fast: " + seconds);

    }


    public static void print_points(Point[] points, Point ref, PrintWriter p){
        ArrayList<Point> to_print = new ArrayList<Point>();
        
        double angle_to_compare=0;

        for(int i = 0; i < points.length; i++){
            if(i==0){
                to_print.add(points[0]); //adds 
                angle_to_compare = points[i].getAngle();
                continue;
            }
            if(points[i].getAngle() == angle_to_compare){
                to_print.add(points[i]);
            }else{
                //iter++;
                angle_to_compare = points[i].getAngle();
                
                if(to_print.size() > 3){
                   break;
                }
                
                to_print.clear();
                to_print.add(points[0]);
                to_print.add(points[i]);
            }
          //  StdOut.println("ANGLE COMPARISON ANGLE YEE " + angle_to_compare);
        }
       
        //put into array, sort lex, check print points [0] to see if == ref
        Point print_dese[] = new Point[to_print.size()];
        for(int i = 0; i < to_print.size(); i++){
            print_dese[i] = to_print.get(i);
        }
       
        //sort
        sort(print_dese);

        if(print_dese[0].getX() == ref.getX() && print_dese[0].getY() == ref.getY() && print_dese.length >=4){
            actually_print(print_dese, p);

        }

    }



    public static void actually_print(Point[] points, PrintWriter p){
        
       // try{
           // File print_to = new File("visualPoints.txt");
            //PrintWriter p = new PrintWriter(print_to);
            StdOut.print(points.length+":");
            p.print(points.length+":");

            for(int i = 0; i < points.length; i++){
                StdOut.printf("(%d, %d) ", points[i].getX(),points[i].getY());
                p.printf("(%d, %d) ", points[i].getX(),points[i].getY());
                if(i != points.length -1){
                    StdOut.printf("-> ");
                    p.printf("-> ");
                }
            }
            StdOut.println();
            p.println();
            
      //  }//catch (IOException ioe){ };
    }

    public static void sort(Point[] points){
        for(int i = 0; i < points.length; i++){
            for(int j = 0; j < points.length-1; j++){
                if(points[j].compareTo(points[j+1]) > 0 ){
                    Point temp = points[j];
                    points[j] = points[j+1];
                    points[j+1] = temp;
                }
            }
        }
    }
    
}
