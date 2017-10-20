public class test{

    public static String rotate(String s, int offset){
        int i = offset % s.length();

        return s.substring(i) + s.substring(0,1);
    }

    public static void main(String args[]){
        String val = "abcdefg";
        for(int i = 0; i < 7; i++){
            StdOut.println(val);
            val = rotate(val, 1);
        }
    }

}