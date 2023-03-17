class Node {
    int data;
    Node left;
    Node right;

    Node(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}

public class MainKthSmallest {
    Node root;

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

    public void add(int x) {
        root = insert(root, x);
    }

    static int count = 0;

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

    public static void printKthSmallest(Node root, int k) {
        Node result = kthSmallest(root, k);
        if (result == null) {
            System.out.println("k-nál kevesebb \"gyerek\" van a bináris fában");
        } else {
            System.out.println("k. legkisebb elem: " + result.data);
        }
    }

    public static void main(String[] args) {
        MainKthSmallest mks = new MainKthSmallest();

        int[] keys = new int[] {20, 8, 22, 4, 12, 10, 14};
        for (int x : keys) {
            mks.add(x);
        }

        int k = 5;
        printKthSmallest(mks.root, k);
    }
}