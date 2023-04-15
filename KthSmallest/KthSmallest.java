public class KthSmallest {
    Node root;

    static int count = 0;

    public void add(int x) {
        root = insert(root, x);
    }

    public static void printKthSmallest(Node root, int k) {
        Node result = kthSmallest(root, k);

        if (result == null) {
            System.out.println("k-nál kevesebb \"gyerek\" van a bináris fában");
        } else {
            System.out.println("k. legkisebb elem: " + result.data);
        }
    }

    private static Node insert(Node current, int x) {
        if (current == null) {
            return new Node(x);
        }
    
        if (x < current.data) {
            current.left = insert(current.left, x);
        } else if (x > current.data) {
            current.right = insert(current.right, x);
        } else {
            return current;
        }
    
        return current;
    }

    private static Node kthSmallest(Node current, int k) {
        if (current == null) {
            return null;
        }

        Node left = kthSmallest(current.left, k);

        if (left != null) {
            return left;
        }

        count++;
        if (count == k) {
            return current;
        }

        return kthSmallest(current.right, k);
    }

    public static void main(String[] args) {
        KthSmallest ks = new KthSmallest();

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