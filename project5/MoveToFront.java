import java.util.*;

public class MoveToFront {
	// apply move-to-front encoding, reading from standard input and writing to standard output
	public static void encode()
	{	
		String binary_string = BinaryStdIn.readString();
		
		int R = 256;
		ArrayList<Character> alphabet = new ArrayList<Character>(R);
		for(int i = 0; i < R; i++){
			alphabet.add( (char)i );
		}


		int[] output = new int[binary_string.length()];

		for(int i = 0; i < binary_string.length(); i++){
			char current = binary_string.charAt(i); //get first character
			int index = alphabet.indexOf(current);

			output[i] = index;

			char letter = alphabet.remove(index);
			alphabet.add(0, binary_string.charAt(i));


		}

		for(int i: output){
			BinaryStdOut.write((char) i);
		}

			BinaryStdOut.close();
	}
	// apply move-to-front decoding, reading from standard input and writing to standard output
	public static void decode()
	{
		int R = 256;
    	char[] alphabet = new char[R];
    	for(int i = 0;i < R;i++)
    		alphabet[i] = (char) i;
    	
    	while (!BinaryStdIn.isEmpty())
        {
    		char c = BinaryStdIn.readChar();
    		int index = (int)c;
    		BinaryStdOut.write(alphabet[index]);
    		
    		char tmp = alphabet[0];
    		for(int i = 0;i < index;i++)
    		{
    			char t_char = alphabet[i+1];
    			alphabet[i+1] = tmp;
    			tmp = t_char;
    		}
    		alphabet[0] = tmp;
        }
    	
    	BinaryStdOut.close();
	}
	// if args[0] is '-', apply move-to-front encoding
	// if args[0] is '+', apply move-to-front decoding
	public static void main(String[] args)
	{
		if(args[0].equals("-")){
			//String str = BinaryStdIn.readString();
			encode();
		}
        else if(args[0].equals("+")){

			 decode();
		}
        else throw new IllegalArgumentException("Bad command line argument!");
	}
}