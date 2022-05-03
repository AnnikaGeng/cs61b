public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int res = 0;
        while (x < 10) {
            res = res + x;
            System.out.println(res);
            x = x + 1;
        }
    }
}