/**
 * <h1>k legkisebb elem</h1>
 * 
 * <p>Bináris keresőfa.</p>
 */
public class KthSmallest {

    /**
     * <p>Gyökér csomó (a keresőfa).</p>
     */
    Node root;


    /**
     * <p>Számláló.</p>
     */
    static int count = 0;

    /**
     * <p>Bináris keresőfa létrehozása a gyökér csomóban.</p>
     * 
     * @param x Csomó (gyerek) eleme
     */
    public void add(int x) {
        root = insert(root, x);
    }

    /**
     * <p>k legkisebb elem megjelenítése.</p>
     * 
     * @param root bináris keresőfa gyökér csomója
     * @param k
     */
    public static void printKthSmallest(Node root, int k) {
        Node result = kthSmallest(root, k);

        if (result == null) {
            System.out.println("k-n\u00E1l kevesebb \"gyerek\" van a bin\u00E1ris f\u00E1ban");
        } else {
            System.out.println("k. legkisebb elem: " + result.data);
        }
    }

    /**
     * <p>Csomó létrehozása (x elemként) 
     * vagy a csomohóz (gyerekhez) gyerek beszúrása (x elemként).</p>
     * 
     * @param current csomó vagy jelenlegi gyerek
     * @param x létrehozandó csomó vagy beszúrandó gyerek eleme
     * @return a csomó (keresőfa)
     */
    private static Node insert(Node current, int x) {
        if (current == null) {
            // nincs csomó (gyerek)

            return new Node(x); // létrehozás, x lesz az eleme
        }

        // x kisebb-e mint a csomó (gyerek) eleme
        if (x < current.data) {
            // baloldali gyerekként beszúrás, x lesz az eleme
            current.left = insert(current.left, x);
        } else if (x > current.data) {
            // jobboldalli gyerekként beszúrás, x lesz az eleme
            current.right = insert(current.right, x);
        } else {
            return current;
        }

        // módosult csomó (gyerek) "visszaadása"
        return current;
    }

    /**
     * <p>k legkisebb elem keresése.</p>
     * 
     * @param current csomó vagy jelenlegi gyerek
     * @param k
     * @return találat esetén a csomó (gyerek), ha nincs találat akkor "null" érték
     */
    private static Node kthSmallest(Node current, int k) {
        if (current == null) {
            // nincs gyerek (vagy baloldalon, vagy jobboldalon)
            return null;
        }

        // keresés a baloldali gyerek(ek)nél
        Node left = kthSmallest(current.left, k);

        if (left != null) {
            // k legkisebb elem
            return left;
        }

        // nincs baloldalon gyerek

        // számláló növelése
        count++;
        if (count == k) {
            // k legkisebb elem
            return current;
        }

        // keresés a jobboldali gyerek(ek)nél
        return kthSmallest(current.right, k);
    }

    public static void main(String[] args) {
        KthSmallest ks = new KthSmallest();

        // elemek
        int[] keys = new int[] {20, 8, 22, 4, 12, 10, 14};

        for (int x : keys) {
            ks.add(x);
        }

        //    20
        //    /\
        //   8 22
        //  /\
        // 4 12
        //   /\
        // 10  14

        int k = 5;
        printKthSmallest(ks.root, k);
    }
}