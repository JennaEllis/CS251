import java.util.*;

public final class WordNet{
    private SAP hypernyms_sap; 
    private Hashtable<Integer, ArrayList<String>> synset_table;
    private Hashtable<String, ArrayList<Integer>> noun_num_table;
    private ArrayList<String> nouns_list;

    public WordNet(String synsets, String hypernyms){
           
            int num_nouns = parse_synsets(synsets);
            Digraph G = parse_hypernyms(hypernyms, num_nouns);

            hypernyms_sap = new SAP(G);

    }

    //read synset file to fill the Hashtable for later word access
    public int parse_synsets(String synsets){
            synset_table = new Hashtable<Integer,ArrayList<String>>(); 
            noun_num_table = new Hashtable<String, ArrayList<Integer>>();
            nouns_list = new ArrayList<String>();
            int noun_count = 0;
            In syn = new In(synsets);
           
            while(!syn.isEmpty()){
                String[] line = syn.readLine().split(",");
                int line_num = Integer.parseInt(line[0]);

                String[] nouns = line[1].split(" ");
                for(int i = 0; i < nouns.length; i++ ){
                    nouns_list.add(nouns[i]);
                    ArrayList<String> temp_string = new ArrayList<String>();
                    temp_string.add(nouns[i]);
                    ArrayList<String> ret = synset_table.put(line_num, temp_string);

                    if(!(ret == null)){//duplicate
                        for(String val: ret){
                            temp_string.add(val);
                        }
                        synset_table.put(line_num, temp_string);
                    }
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    temp.add(line_num);
                    ArrayList<Integer> s = noun_num_table.put(nouns[i], temp);
                    if(!(s == null)){ //if it returns a value that was a duplicate
                        for(int babu : s){
                            temp.add(babu);
                        }
                        noun_num_table.put(nouns[i], temp);
                    }
                    noun_count++;
                   
                }
            }
            return noun_count;
    }

    //read hypernyms file to build a Digraph that will be used as the SAP basis
    public Digraph parse_hypernyms(String hypernyms, int num_nouns){
        Digraph G = new Digraph(num_nouns);
        In hyp = new In(hypernyms);
        
        while(!hyp.isEmpty()){
            
            String[] line = hyp.readLine().split(",");
            int v = Integer.parseInt(line[0]);

            for(int j = 1; j < line.length; j++){
                int w = Integer.parseInt(line[j]);
                G.addEdge(v, w);
            }
        }
        
        hyp.close();

        return G;
    }

     // is the word a WordNet noun? This can be used to search for existing
    // nouns at the beginning of the printSap method
    public boolean isNoun(String word){
    
        return this.nouns_list.contains(word);
    }

 // print the synset (second field of synsets.txt) that is the common ancestor
 // of nounA and nounB in a shortest ancestral path as well as the length of the path,
 // following this format: "sap<space>=<space><number>,<space>ancestor<space>=<space><synsettext>"
 // If no such path exists the sap should contain -1 and ancestor should say "null"
 // This method should use the previously defined SAP datatype
    public void printSap(String nounA, String nounB){
       //if(!this.isNoun(nounB) && this.isNoun(nounA)){
           // print statments
           if(!this.isNoun(nounB) || !this.isNoun(nounA)){
            StdOut.println("sap = -1, ancestor = null");
           return;
        }

        ArrayList<Integer> A = this.noun_num_table.get(nounA);
        ArrayList<Integer> B = this.noun_num_table.get(nounB);
       
       int minCestor = Integer.MAX_VALUE;
       int minLength = Integer.MAX_VALUE; 
       for(int a : A){
           for(int b : B){
            int ancestor_val = this.hypernyms_sap.ancestor(a, b);
            int length = this.hypernyms_sap.length(a,b);

            if(length < minLength){
                minLength = length;
                minCestor = ancestor_val;
            }
           }
       }

        if(minLength == -1){
            StdOut.printf("sap = -1, ancestor = null\n");
            return;
        }

        //begin print statement
        StdOut.printf("sap = %d, ancestor = ", minLength);
        ArrayList<String> values = this.synset_table.get(minCestor);
        StdOut.printf("%s\n", values.get(values.size()-1));
        // for(int i = values.size()-1; i >= 0;i--){
        //     StdOut.printf("%s",values.get(i) );
        //     if(i != 0){
        //         StdOut.printf(" ");
        //     }
        //     else{
        //         StdOut.println();
        //     }
        // }
      

    }
    
    public static void main(String args[]){
        String hypernyms = args[1];
        String synsets = args[0];
        String test_input = args[2];

        WordNet word = new WordNet(synsets, hypernyms);

        In input = new In(test_input);

        while(!input.isEmpty()){
            String nounA = input.readString();
            String nounB = input.readString();

            word.printSap(nounA, nounB);
        }


    }

}
