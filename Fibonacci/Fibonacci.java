/**
 * <h1>Fibonacci</h1>
 */
public class Fibonacci {

    /**
     * <p>Adott szám tagja-e a Fibonacci sorozatnak.</p>
     * 
     * @param n a szám
     * @return igaz, ha tagja a Fibonacci sorozatnak
     */
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

    /**
     * <p>Adott szám tagja-e a Fibonacci sorozatnak.</p>
     * 
     * @param n a szám
     * @param s1 sorozat "első" tagja
     * @param s2 sorozat "második" tagja
     * @return igaz, ha tagja a Fibonacci sorozatnak
     */
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