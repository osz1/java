/**
 * <h1>Csomó (gyerek)</h1>
 */
public class Node {

    /**
     * <p>Elem.</p>
     */
    int data;

    /**
     * <p>Baloldali gyerek.</p>
     */
    Node left;

    /**
     * <p>Jobboldali gyerek.</p>
     */
    Node right;

    public Node(int data, Node left, Node right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
}