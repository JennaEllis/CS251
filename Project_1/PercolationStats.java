import java.util.*;

public class PercolationStats{


        public static void main(String args[]){
            //parameters N, T, and type from cmd line
            int N = Integer.parseInt(args[0]);        //NxN grid
            int T = Integer.parseInt(args[1]);        //T number of repitions
            String type = args[2];  //"slow" of "fast" union quickU is slow and weightedU is fast
                                    //fast is already implemented here
           
            Percolation perc_fast = null;
            PercolationQuick perc_slow = null;
            Random randomGenerator = new Random();

            //array that holds percolation threshhold for T repititions
            int open_counts[] = new int[T];
            int count = 0;
            int total_count=0;
            double count_mean, count_std_dev;


            //time array that keeps track of times for each T experiments
            double times[] = new double[T];
            double time_mean, time_std_dev, time_total;
            time_total = 0;

            //percolation.java is implemented using weightedquickunionUF
            if(type.equals("fast")){
                perc_fast = new Percolation(N);
                
            }
            else{
               
                perc_slow = new PercolationQuick(N);
               
            }

            for(int i = 0; i < T; i++){
                boolean percolates = false;
                double start_time =(double) System.currentTimeMillis();

                while(!percolates){
                    //randomly pick a blocked cell and open it
                    int x = randomGenerator.nextInt(N); //generates rand int from 0 to N-1
                    int y = randomGenerator.nextInt(N);

            
                    if(type.equals("fast")){
                        
                        if(!perc_fast.isOpen(x,y)){ //avoid opening the same cell twice 
                            perc_fast.open(x,y);
                            count++;
                           
                            percolates = perc_fast.percolates(); //loop control
                            
                        }
                        
                    }else{
                        
                        if(!perc_slow.isOpen(x,y)){ //check coords unique 
                        
                            perc_slow.open(x,y);
                            count++;
                            percolates = perc_slow.percolates();//loop
                           
                        }
                        
                    }


                }
               
                double end_time = (double)System.currentTimeMillis();

                double time_in_seconds =  (end_time - start_time)/1000; //conversion factor

                time_total += time_in_seconds;
                //put count into array, reset to zero
                open_counts[i] = count;
               
                total_count+=count; 

                count = 0;

                times[i] = time_in_seconds;

                

                perc_fast = new Percolation(N);
                perc_slow = new PercolationQuick(N);

                        
            }

                  
            count_mean = total_count / T;
            time_mean = time_total / T;

            double dev_count = 0;
            double dev_time = 0;
            //standard deviations
            for(int k = 0; k < T; k++){
                dev_count += (count_mean - open_counts[k] )* (count_mean - open_counts[k]);
                dev_time += ( time_mean -times[k] ) * ( time_mean- times[k] );
            }
            double thing1 =   dev_count * 1/(T-1);
            double thing2 = dev_time * 1/(T-1);

            count_std_dev =  Math.sqrt(thing1);
            time_std_dev =  Math.sqrt(thing2);

            //print statements
            StdOut.println("\n**OUTPUT BELOW**");
            StdOut.println("mean threshhold="+count_mean);
            StdOut.println("std dev="+count_std_dev);
            StdOut.println("time="+time_total);
            StdOut.println("mean time="+time_mean);
            StdOut.println("stddev time="+time_std_dev);

        }



        // public static void printer(Percolation p){
        //     for(int i = 0; i < p.size; i++){
        //         for(int k = 0; k < p.size; k++){
        //             StdOut.print(p.grid[i][k]+" ");
        //         }
        //         StdOut.println();
        //     }
        // }
}
        