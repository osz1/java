/**
 * <h1>Két bináris fa összefésülése</h1>
 */
public class MergeTwoBinaryTrees {

    /**
     * <p>Csomó (gyerek) létrehozása.</p>
     * 
     * @param x elem
     * @return új csomó (gyerek) x elemmel
     */
    public static Node newNode(int x) {
        return new Node(x, null, null);
    }

    /**
     * <p>Bináris fa megjelenítése.</p>
     * 
     * <p>Először a baloldali gyerek, a csomó, majd  a jobboldali gyerek jelenik meg.</p>
     * 
     * <p>(Mintha "összepréselve" lenne a fa.)</p>
     * 
     * @param current jelenlegi csomó (gyerek)
     */
    public static void inorder(Node current) {
        // (vége)
        if (current == null) {
            return;
        }

        // baloldali gyerek megjelenítése
        inorder(current.left);

        // csomó (gyerek) megjelenítése
        System.out.printf(current.data + " ");

        // jobboldali gyerek megjelenítése
        inorder(current.right);
    }

    /**
     * <p>Két bináris fa (csomó, gyerek) összefésülése (adott szinten).</p>
     * 
     * <p>(Első fa módosítása.)</p>
     * 
     * @param tree1 első fa
     * @param tree2 második fa
     * @return az összefésült fa (módosult első fa)
     */
    public static Node mergeTrees(Node tree1, Node tree2) {
        if (tree1 == null) {
            // nincs első csomó (gyerek)
            return tree2;
        }

        if (tree2 == null) {
            // nincs második csomó (gyerek)
            return tree1;
        }

        // azonos szinten a két fa elemének összeadása
        tree1.data += tree2.data;

        // következő szint gyerekeinek összefésülése
        tree1.left = mergeTrees(tree1.left, tree2.left);
        tree1.right = mergeTrees(tree1.right, tree2.right);

        // az összefésült fa
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
        //     1    7
        //    /    / \
        //   3    2   6
        Node root2 = newNode(4);
        root2.left = newNode(1);
        root2.right = newNode(7);
        root2.left.left = newNode(3);
        root2.right.left = newNode(2);
        root2.right.right = newNode(6);

        // A két bináris fa összefésülése
        //       5
        //     /   \
        //    3    10
        //   / \   / \
        //  7   5 2  12
        Node root3 = mergeTrees(root1, root2);

        System.out.println("Az \u00F6sszef\u00E9s\u00FClt bin\u00E1ris fa:");

        inorder(root3);
    }
}