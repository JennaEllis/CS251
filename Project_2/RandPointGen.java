import java.io.*;
import java.util.*;
public class RandPointGen{
	public static void genPoints(int num) throws IOException{
		Random r = new Random();
		BufferedWriter bw = new BufferedWriter(new FileWriter("./points/points"+num+".txt"));
		String s = num+ "\n";
		bw.write(s, 0, s.length());
		bw.flush();
		boolean[][] used = new boolean[300][300];
		s = "";
		for(int i = 0; i < num; i++){
				int x = r.nextInt(300);
				int y = r.nextInt(300);
			while(used[x][y]){
				x = r.nextInt(300);
				y = r.nextInt(300);
			}
			used[x][y] = true;

			s =  x*100 + " " + y*100 + "\n";
			bw.write(s, 0, s.length());
			bw.flush();
		}
	}
	public static void main(String[] args) throws IOException{
		RandPointGen.genPoints(Integer.parseInt(args[0]));
	}
}