import java.util.*; 

public class SAP{
    private Digraph d_graph;
    public SAP(Digraph G){
            this.d_graph = G;
    }

    public int length(int v, int w){
        int anc = this.ancestor(v,w);
        if(anc == -1) return -1;
        BreadthFirstDirectedPaths one = new BreadthFirstDirectedPaths(this.d_graph, v);
        BreadthFirstDirectedPaths two = new BreadthFirstDirectedPaths(this.d_graph, w);

        return (one.distTo(anc)+two.distTo(anc));
        //not neccessary 
        //return 0; 
    }

    public int ancestor(int v, int w){
        //do breadth first search on v 
        //do breadth first search on w 
        //intersection of the 2 
        //return one with smallest length--> use distTo method in digrah.java 
        BreadthFirstDirectedPaths set_one = new BreadthFirstDirectedPaths(this.d_graph, v);
        BreadthFirstDirectedPaths set_two = new BreadthFirstDirectedPaths(this.d_graph,  w);

        ArrayList<Integer> ancestor = new ArrayList<Integer>();

        int i = 0;
        while(i < this.d_graph.V()){
          

            if((set_one.hasPathTo(i) ) && (set_two.hasPathTo(i) )){
                ancestor.add(i);
            }
            i++;
        }

        if(ancestor.size() == 0){ return -1;}


        int min_length_anc = -1;
        int min_length = set_one.distTo(ancestor.get(0)) + set_two.distTo(ancestor.get(0));
        for(int j = 0; j < ancestor.size(); j++){
            int sum_length = set_one.distTo(ancestor.get(j)) + set_two.distTo(ancestor.get(j));

            if(sum_length <= min_length){
                min_length = sum_length;
                min_length_anc = ancestor.get(j);
            }
        }

        return min_length_anc; 
    }

    //take name of two files as command line arguments: 
    //digraph input file to be used by digraph
    //and a digraph test input file 
    //should construct a digraph  and read in vertex pairs from test input file
    //prints out length of shortest ancestral path b/w two vertices and a common ancestor 
    //in the path. can be multiple paths 
    public static void main(String[] args){
        String digraph_file = args[0];
        String digraph_input = args[1]; //nodes to find ancestral path between 

        Digraph G = new Digraph(new In(digraph_file));

        SAP sap = new SAP(G);

        In points = new In(digraph_input);

        while(!points.isEmpty()){
            int x = points.readInt();
            int y = points.readInt();

            //do something
            int ancestor = sap.ancestor(x, y);

            int length = sap.length(x,y);

            StdOut.printf("sap = %d, ancestor = %d\n", length, ancestor);


        }





    }
}