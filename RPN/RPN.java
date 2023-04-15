import java.util.Stack;

public class RPN {
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
            default:
                return -1;
        }
    }

    public static Stack<String> expressionToStack(String expression) {
        Stack<String> strStack = new Stack<>();
        for (String element : expression.split(" ")) {
            strStack.push(element);
        }

        return strStack;
    }

    public static String infixToPostfix(Stack<String> infixExpression) {
        Stack<String> stack1 = new Stack<>();
        Stack<String> stack2 = new Stack<>();
        int i = 0;
        int parenthesesLevel = 0;
        int maxPrecedence = 0;
        int maxPrecedenceIndex = 0;
        for (String element : infixExpression) {
            if (element.matches("[0-9]") || (element.length() > 1)) {
                stack1.push(element);
            } else if (element.equals("(")) {
                stack1.push(element);
                parenthesesLevel += 3;
            } else if (element.equals(")")) {
                stack1.push(element);
                parenthesesLevel -= 3;
            } else {
                int precedence = parenthesesLevel + precedenceValue(element);
                if (i <= 1) {
                    maxPrecedence = precedence;
                    maxPrecedenceIndex = i;
                }
                if (precedence > maxPrecedence) {
                    maxPrecedence = precedence;
                    maxPrecedenceIndex = i;
                }

                stack1.push(element);
            }

            i++;
        }

        i = 0;
        String maxPrecedenceNum1 = "";
        String maxPrecedenceOperator = "";
        for (String element : stack1) {
            if (i == (maxPrecedenceIndex - 2)) {
                if (!element.equals("(")) {
                    stack2.push(element);
                }
            } else if (i == (maxPrecedenceIndex - 1)) {
                maxPrecedenceNum1 = element;
            } else if (i == maxPrecedenceIndex) {
                maxPrecedenceOperator = element;
            } else if (i == (maxPrecedenceIndex + 1)) {
                stack2.push(maxPrecedenceNum1 + " " + element + " " + maxPrecedenceOperator);
            } else if (i == (maxPrecedenceIndex + 2)) {
                if (!element.equals(")")) {
                    stack2.push(element);
                }
            } else {
                stack2.push(element);
            }

            i++;
        }

        stack1.clear();
        for (String element : stack2) {
            stack1.push(element);
        }

        stack2.clear();
        if (stack1.size() == 1) {
            return stack1.peek();
        } else {
            return infixToPostfix(stack1);
        }
    }

    public static void main(String[] args) {
        System.out.println(infixToPostfix(expressionToStack("1 + 2 - 3 ^ 4")));
        System.out.println(infixToPostfix(expressionToStack("1 ^ 2 - 3 * 4")));
        System.out.println(infixToPostfix(expressionToStack("1 + 2 * 3 - 4 ^ 5 + 6")));
        System.out.println(infixToPostfix(expressionToStack("( 5 + 4 ) * 3 + ( 4 ^ ( 5 - 6 ) )")));
        System.out.println(infixToPostfix(expressionToStack("9 + 8 + 7 / 6 + 5 + 4 * ( 7 + 8 )")));
        System.out.println(infixToPostfix(expressionToStack("9 - 1 - 2 - 3 * 2 - 10")));
    }
}