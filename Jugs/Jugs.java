/**
 * <h1>Kancsók</h1>
 * 
 * <p>Adott vízmennyiség (k) kimérése két kancsó (m és n) segítségével.</p>
 * 
 * <p>k < m, k < n</p>
 */
public class Jugs {
    private static void jug1(int m, int n, int k) {
        if(!((k < m) && (k < n))) {
            return;
        }

        int contentM = m;
        int contentN = 0;

        System.out.println("(" + contentM + "," + contentN + ")");

        contentM -= n;
        contentN += n;

        System.out.println("(" + contentM + "," + contentN + ")");

        if ((k == contentM) || (k == contentN)) {
            return;
        } else {
            jug1(contentM, contentN, k);
        }
    }

    private static void jug2(int m, int n, int k, int contentM) {
        if(!((k < m) && (k < n) && (contentM < m))) {
            return;
        }

        int contentN = n;

        System.out.println("(" + contentM + "," + contentN + ")");

        if ((m - contentM) >= n) {
            contentM += n;
            contentN -= n;
        } else {
            contentM -= (m - contentM);
            contentN += n;
        }

        System.out.println("(" + contentM + "," + contentN + ")");

        if ((k == contentM) || (k == contentN) || (contentN > n)) {
            return;
        } else {
            jug2(m, n, k, contentM);
        }
    }

    public static void main(String[] args) {
        int m = 5;
        int n = 3;
        int k = 2;
        if((k < m) && (k < n) && (n != m)) {
            System.out.println(m + " " + n + " " + k);

            jug1(m, n, k);
        }
        
        k = 1;
        if((k < m) && (k < n) && (n != m)) {
            System.out.println(m + " " + n + " " + k);

            jug2(m, n, k, 0);
        }
    }
}