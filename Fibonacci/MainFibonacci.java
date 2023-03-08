public class MainFibonacci {
    public static boolean fibonacci(long n) {
        long s1 = 0;
        long s2 = 1;
        while (s2 < n) {
            long x = s2;
            s2 += s1;
            s1 = x;
        }
        return (n == s1) || (n == s2);
    }

    public static boolean fibonacci(long n, long s1, long s2) {
        if (s2 >= n) {
            return (n == s1) || (n == s2);
        } else {
            return fibonacci(n, s2, (s1 + s2));
        }
    }
    
    public static void main(String[] args) {
        long n = 144;
        System.out.println(n + " " + fibonacci(n));
        System.out.println(n + " " + fibonacci(n, 0, 1));
    }
}