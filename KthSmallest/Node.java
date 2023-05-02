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

    Node(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}