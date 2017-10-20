import java.util.*;
public class BurrowsWheeler {
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    //change all dis shit

    static class CircularString implements Comparable<CircularString>{
                public int rotation_number;
                public String original_string;
                public char last_char;
                
                public CircularString(String value, int rotation){
                    rotation_number = rotation;
                    original_string = value;
                    last_char = value.charAt((value.length()-1+rotation)%value.length());
                }

                public int compareTo(CircularString other){
                        for(int i = 0; i < this.original_string.length(); i++){
                            if(this.getChar(i) > other.getChar(i)){
                                return 1;
                            }
                            else if(this.getChar(i) < other.getChar(i)){
                                return -1;
                            }
                            
                        }
                        return 0;
                }

                public char getChar(int loc){
                    return this.original_string.charAt((loc+this.rotation_number)%this.original_string.length());
                }
    }

   
    public static void encode()
    {
    
       String str = BinaryStdIn.readString();
        //basic steps
		//given a string of length N, 
		//consider the N by N matrix in which each row is a different cyclic rotation of
		//the original text string
		//sort the rows lexicographically 
		//the BWT is the right most column in the sorted matrix
        CircularString[] matrix = new CircularString[str.length()];
        
        for(int i = 0; i < matrix.length; i++){
            matrix[i] = new CircularString(str, i);
          
        }

        Arrays.sort(matrix);

        String compressed = "";
        for(int i = 0; i< matrix.length; i++){
            compressed+=matrix[i].last_char;
        }

        int index = 0;
        for(int i = 0; i < matrix.length; i++){
            if(matrix[i].rotation_number == 0){
                index = i;
                break;
            }
        }
        //probably wrong, fix file stuff later
        BinaryStdOut.write(index);
        //BinaryStdOut.write('\n');
       
        BinaryStdOut.write(compressed);
        BinaryStdOut.close();
    }

        // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode()
    {
        //use frequency of the letters in the string given to construct original
        //use next array 
        int first_char = BinaryStdIn.readInt(); //original val location
    	String str = BinaryStdIn.readString(); //read the string 
    	
    	int N = str.length();
    	int R = 256;//radix value

    	int[] freq = new int[R+1];
    	char[] aux_array = new char[N]; //put values in original order in this array 
    	int[] next = new int[N];    //the next array numbers in example 

        //initialize frequency
    	for(int i = 0;i < N;i++){
    		freq[str.charAt(i)+1]++;
        }

        //summation prop
    	for(int r = 0;r < R;r++){
    		freq[r+1] += freq[r];
        }

        //actual reconstruction of word
    	for(int i = 0;i < N;i++) {
    		int tmp = freq[str.charAt(i)]++;
    		aux_array[tmp] = str.charAt(i);
    		next[tmp] = i;
    	}

    	//print
    	for(int i = 0;i < N;i++)
    	{
    		BinaryStdOut.write(aux_array[first_char]);
    		first_char = next[first_char];
    	}

    	BinaryStdOut.close();


    }
    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args)
    {
        if(args[0].equals("-")){ encode();}
        else if(args[0].equals("+")){ decode();}
        else throw new IllegalArgumentException("Bad command line argument!");

    }
}