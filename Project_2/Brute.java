import java.io.*;
import java.util.*; 

public class Brute{
    public static void main(String args[]){
        //file location piped in, use stdin
        
        int num_points = StdIn.readInt();
        double start = System.currentTimeMillis();
        //create an array of points 
        Point[] points = new Point[num_points];
        
        //read in coordinates
        for(int i = 0; i < num_points; i++){
            int x = StdIn.readInt();
            int y = StdIn.readInt();

            Point new_point = new Point(x,y);
            
            points[i] = new_point;
        }

        //4 for loops to check all point combinations 
        try{
            File out = new File("visualPoints.txt");
            PrintWriter pWriter = new PrintWriter(out);
           
            //outstanding case: array of size 4 
            if(num_points==4){
                boolean collinear = Point.areCollinear(points[0],points[1],points[2],points[3]);
                if(collinear){
                    //sort the points using compareTo lexicographic order 
                    sort(points);

                    //print to PrintWriter and exit
                    StdOut.printf("4:(%d, %d) -> (%d, %d) -> (%d, %d) -> (%d, %d) \n", points[0].getX(), points[0].getY(), points[1].getX(), points[1].getY(), points[2].getX(), points[2].getY(), points[3].getX(), points[3].getY());
                   pWriter.printf("4:(%d, %d) -> (%d, %d) -> (%d, %d) -> (%d, %d) \n", points[0].getX(), points[0].getY(), points[1].getX(), points[1].getY(), points[2].getX(), points[2].getY(), points[3].getX(), points[3].getY());

                }
                return; 
            }
            ArrayList<Point[]> sets = new ArrayList<Point[]>();
            //for loop traversal, all possible combinations of 4 points in the list
            for(int i = 0; i < num_points; i++){
                for(int j =i+1; j < num_points; j++){
                    for(int k = j+1; k < num_points; k++){
                        for(int l = k+1; l < num_points; l++){
                            
                            if(Point.areCollinear(points[i],points[j],points[k],points[l])){
                                //put them into an array size 4
                               
                                Point[] collinear = new Point[4];
                                collinear[0] = points[i];
                                collinear[1] = points[j];
                                collinear[2] = points[k];
                                collinear[3] = points[l];
                               
                                //sort lexicographic
                                sort(collinear);

                                sets.add(collinear);

                                //print to pWriter and continue
                               // StdOut.printf("4: (%d, %d) -> (%d, %d) -> (%d, %d) -> (%d, %d)\n", collinear[0].getX(), collinear[0].getY(), collinear[1].getX(), collinear[1].getY(), collinear[2].getX(), collinear[2].getY(), collinear[3].getX(), collinear[3].getY());
                                //pWriter.printf("4: (%d, %d) -> (%d, %d) -> (%d, %d) -> (%d, %d)\n", collinear[0].getX(), collinear[0].getY(), collinear[1].getX(), collinear[1].getY(), collinear[2].getX(), collinear[2].getY(), collinear[3].getX(), collinear[3].getY());
                            }
                        }
                    }
                }
            }
            if(sets.size()!=0){
                print_sort(sets,pWriter);
            }
            
            pWriter.close();
        }catch(IOException ioe){ ioe.printStackTrace();}
        double end = System.currentTimeMillis();

       double elapsed_mill = (end - start);

       double seconds = elapsed_mill/1000;

       //StdOut.println("Time elapsed Brute: " + seconds);

        
    }

    public static void print_sort(ArrayList<Point[]> sets, PrintWriter p){
        //sort lexicographic by first points 
        Point[][] double_thing = new Point[sets.size()][sets.get(0).length];
        for(int i = 0; i < double_thing.length; i++){
            double_thing[i][0] = sets.get(i)[0];
            double_thing[i][1] = sets.get(i)[1];
            double_thing[i][2] = sets.get(i)[2];
            double_thing[i][3] = sets.get(i)[3];
        }

        //sort by first point in each set 
        for(int i = 0; i < double_thing.length; i++){
            for(int j = 0; j < double_thing.length-1; j++){
                if(double_thing[j][0].compareTo(double_thing[j+1][0]) > 0){
                    //swap
                    Point[] temp = double_thing[j];
                    double_thing[j] = double_thing[j+1];
                    double_thing[j+1] = temp;
                }
            }
        }

        for(int i = 0; i < double_thing.length; i++){
            StdOut.printf("4:(%d, %d) -> (%d, %d) -> (%d, %d) -> (%d, %d) \n", double_thing[i][0].getX(), double_thing[i][0].getY(), double_thing[i][1].getX(), double_thing[i][1].getY(), double_thing[i][2].getX(), double_thing[i][2].getY(), double_thing[i][3].getX(), double_thing[i][3].getY());
            p.printf("4:(%d, %d) -> (%d, %d) -> (%d, %d) -> (%d, %d) \n", double_thing[i][0].getX(), double_thing[i][0].getY(), double_thing[i][1].getX(), double_thing[i][1].getY(), double_thing[i][2].getX(), double_thing[i][2].getY(), double_thing[i][3].getX(), double_thing[i][3].getY());
        }
    }

    public static void sort(Point[] points){
        //sort in lexicographic order; using bubble sort
        for(int i = 0; i < points.length; i++){
            for(int j = 0; j < points.length -1; j++){
                if(points[j].compareTo(points[j+1]) > 0){
                    //swap points
                    Point temp = points[j];
                    points[j] = points[j+1];
                    points[j+1] = temp;
                }
            }
        }
    }
}