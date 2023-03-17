class Node {
    int data;
    Node left;
    Node right;

    public Node(int data, Node left, Node right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
}

public class MainMergeTwoBinaryTrees {
    public static Node newNode(int x) {
        return new Node(x, null, null);
    }

    public static void inorder(Node current) {
        if (current == null) {
            return;
        }
        inorder(current.left);
        System.out.printf(current.data + " ");
        inorder(current.right);
    }

    public static Node mergeTrees(Node tree1, Node tree2) {
        if (tree1 == null) {
            return tree2;
        }
        if (tree2 == null) {
            return tree1;
        }
        tree1.data += tree2.data;
        tree1.left = mergeTrees(tree1.left, tree2.left);
        tree1.right = mergeTrees(tree1.right, tree2.right);
        return tree1;
    }

    public static void main(String[] args) {
        // Első bináris fa létrehozása
        //      1
        //     / \
        //    2   3
        //   / \   \
        //  4   5   6
        Node root1 = newNode(1);
        root1.left = newNode(2);
        root1.right = newNode(3);
        root1.left.left = newNode(4);
        root1.left.right = newNode(5);
        root1.right.right = newNode(6);

        // Második bináris fa létrehozása
        //        4
        //       / \
        //     1     7
        //    /     / \
        //   3     2   6
        Node root2 = newNode(4);
        root2.left = newNode(1);
        root2.right = newNode(7);
        root2.left.left = newNode(3);
        root2.right.left = newNode(2);
        root2.right.right = newNode(6);

        Node root3 = mergeTrees(root1, root2);
        System.out.println("Az összefésült bináris fa:");
        inorder(root3);
    }
}