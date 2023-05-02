import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

/**
 * <h1>Fordított lengyel jelölés</h1>
 */
public class RPN {

    /**
     * <p>Művelet precedens értéke.</p>
     * 
     * @param operator művelet
     * @return precedens érték
     */
    public static int precedenceValue(String operator) {
        switch (operator) {
            case "^":
                return 2;
            case "*":
            case "/":
                return 1;
            case "+":
            case "-":
                return 0;
            default: // nem művelet (szám)
                return -1;
        }
    }

    /**
     * <p>Kifejezés (String) felbontása.</p>
     * 
     * @param expression kifejezés szövege
     * @return kifejezés szöveghalmazként
     */
    public static String[] expressionToArray(String expression) {
        return expression.split(" ");
    }

    /**
     * <p>"Hagyományos" kifejezés átalakítása "fordított lengyel jelölésűvé".</p>
     * 
     * @param infixExpression "hagyományos" kifejezés szöveghalmaza
     * @return kifejezés "fordított lengyel jelölésűvé" átalakítva "Stack" típusként
     */
    public static Stack<String> infixToPostfix(String[] infixExpression) {
        // kezdeti tároló
        Queue<String> quene = new LinkedList<>(); // elemek
        Queue<Integer> precedenceQuene = new LinkedList<>(); // precedensek

        // a megoldás
        Stack<String> stack = new Stack<>(); 
        Stack<Integer> precedenceStack = new Stack<>();

        // ideiglenes tároló
        Queue<String> tmpQuene = new LinkedList<>();
        Queue<Integer> tmpPrecedenceQuene = new LinkedList<>();

        int parenthesesLevel = 0; // zárójel szintje

        // kifejezés vizsgálata
        for (String element : infixExpression) {
            if (element.matches("[0-9]") || (element.length() > 1)) { // szám
                quene.add(element);
                precedenceQuene.add(precedenceValue(element)); // -1
            } else if (element.equals("(")) {
                parenthesesLevel += 3;
            } else if (element.equals(")")) {
                parenthesesLevel -= 3;
            } else { // művelet
                quene.add(element);
                precedenceQuene.add(parenthesesLevel + precedenceValue(element));
            }
        }

        // kezdés
        stack.push(quene.poll()); // szám hozzáadása a megoldáshoz
        precedenceStack.push(precedenceQuene.poll());

        // elemek rendezése (a megoldásba)
        while (!quene.isEmpty()) {
            if (precedenceQuene.peek() == -1) { // szám
                // szám hozzáadása a megoldáshoz
                stack.push(quene.poll());
                precedenceStack.push(precedenceQuene.poll());

                // ideiglenes tárolóban lévő műveletek hozzáadása a megoldáshoz
                while (!tmpQuene.isEmpty()) {
                    stack.push(tmpQuene.poll());
                    precedenceStack.push(tmpPrecedenceQuene.poll());

                    // megoldásban lévő, kisebb precedensű művelet visszahelyezése
                    if (!tmpPrecedenceQuene.isEmpty() && (tmpPrecedenceQuene.peek() > precedenceStack.peek())) {
                        tmpQuene.add(stack.pop());
                        tmpPrecedenceQuene.add(precedenceStack.pop());
                    }
                }
            } else { // művelet
                // művelet hozzáadása az ideiglenes tárolóhoz
                tmpQuene.add(quene.poll());
                tmpPrecedenceQuene.add(precedenceQuene.poll());

                if (tmpPrecedenceQuene.peek() > precedenceStack.peek()) {
                    // nagyobb az előző művelet precedencsénél
                    // (kezdetben szám)

                    // megoldásban lévő kisebb precedensű műveletek áthelyezése
                    // az ideiglenes tárolóba
                    while (precedenceStack.peek() != -1) {
                        tmpQuene.add(stack.pop());
                        tmpPrecedenceQuene.add(precedenceStack.pop());
                    }
                }
            }
        }

        return stack;
    }

    /**
     * <p>"Stack" típus megjelenítése.</p>
     * 
     * @param stack (kifejezés)
     */
    public static void printStack(Stack<String> stack) {
        for (String element : stack) {
            System.out.printf(element + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        printStack(infixToPostfix(expressionToArray("1 + 2 - 3 ^ 4")));
        printStack(infixToPostfix(expressionToArray("1 ^ 2 - 3 * 4")));
        printStack(infixToPostfix(expressionToArray("1 + 2 * 3 - 4 ^ 5 + 6")));
        printStack(infixToPostfix(expressionToArray("( 5 + 4 ) * 3 + ( 4 ^ ( 5 - 6 ) )")));
        printStack(infixToPostfix(expressionToArray("9 + 8 + 7 / 6 + 5 + 4 * ( 7 + 8 )")));
        printStack(infixToPostfix(expressionToArray("9 - 1 - 2 - 3 * 2 - 10")));
    }
}