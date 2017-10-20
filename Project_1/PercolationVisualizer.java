import java.io.*;


public class PercolationVisualizer{
    public Percolation perc = null;
    public int size;

    public PercolationVisualizer(int n){
        //create percolation object of size n 
        perc = new Percolation(n);
        size = n;

     }
     
    public static void main(String args[]){
       
        try{
       
        File out = new File("visualMatrix.txt");
        PrintWriter pWriter = new PrintWriter(out);
      
        
        int size = StdIn.readInt();

        int x_arr[] = new int[size*size];
        int y_arr[] = new int[size*size];
        int xCount=0;
        int yCount = 0;

        //print size to terminal and to .txt file
        StdOut.println(size+"\n");
        pWriter.println(size+"\n");

        PercolationVisualizer vis = new PercolationVisualizer(size);
        vis.size = size;

        while(!StdIn.isEmpty()){
            int x = StdIn.readInt();
            int y = StdIn.readInt();

            x_arr[xCount] = x;
            y_arr[yCount] = y;
            xCount++;
            yCount++;

            vis.perc.open(x,y);

            if(x == vis.perc.size - 1){//if accessing the top row
                for(int num = 0; num < xCount && num < yCount; num++){
                    //check various cells for connections 
                    int index_thing = vis.perc.size*(vis.perc.size - x -1)  + y;
                    int current_place = vis.perc.size*(vis.size - x_arr[num] -1) + y_arr[num];

                    if(vis.perc.union_find.connected(index_thing,current_place)){//is connected -> percolation
                        vis.perc.grid[vis.size - x_arr[num]-1][y_arr[num]] = 2;//visualize
                    }
               }
            }          
    
            //each time a value is opened, print out the matrix to terminal and .txt file
            vis.printIt(pWriter);

        }
            pWriter.close();
         }catch(IOException ioe){ioe.printStackTrace(); };   
    }

   


    public void printIt(PrintWriter pWriter){
        //prints the current state of the associated grid to terminal 
        for(int i = 0; i < this.size; i++ ){
            for(int j = 0; j < this.size; j++){
                StdOut.print(this.perc.grid[i][j]+" ");
                pWriter.print(this.perc.grid[i][j]+" ");
            }
            StdOut.println();
            pWriter.println();
        }

        StdOut.println();
        pWriter.println();
    }



}