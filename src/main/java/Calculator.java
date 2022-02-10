import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {

    private static final Stack<String> operations = new Stack<>();
    private static final Stack<Integer> numbers = new Stack<>();


    public static void main(String[] args) {
        System.out.println("Please put an Integer number!");
        System.out.println("For example : 1+1*(2-3)");
        System.out.println("Enter your expression to get a solution: ");

        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextLine()) {
                String expression = sc.nextLine().replaceAll("\\s", "");
                calculate(expression);
                System.out.println(expression + "=" + numbers.pop());
            }
        } catch (NoSuchElementException ex) {
            System.err.println("Something went wrong: Please enter your expression!");
        }
    }

    private static void calculate(String expression) {
        int index = 0;

        while (index < expression.length()) {
            if (expression.charAt(index) == '(') {

            } else if (expression.charAt(index) == '*' || expression.charAt(index) == '/' ||
                    expression.charAt(index) == '+' || expression.charAt(index) == '-') {
                operations.push(String.valueOf(expression.charAt(index)));
            } else if (expression.charAt(index) == ')') {
                doOperation();
            } else {
                StringBuilder value = new StringBuilder(String.valueOf(expression.charAt(index)));

                while ((index + 1) < expression.length() &&
                        Character.isDigit(expression.charAt(index + 1))) {
                    value.append(expression.charAt(++index));
                }
                numbers.push(Integer.parseInt(value.toString()));
            }
            index++;
        }

        while (!operations.empty() && !numbers.empty()) {
            doOperation();
        }
    }

    private static void doOperation() {
        String op = operations.pop();
        switch (op) {
            case "*":
                numbers.push(numbers.pop() * numbers.pop());
                break;
            case "/":
                try {
                    int first = numbers.pop();
                    int second = numbers.pop();
                    numbers.push(second / first);
                } catch (ArithmeticException ex) {
                    System.err.println("Your expression failed, you " + ex.getMessage());
                }
                break;
            case "+":
                numbers.push(numbers.pop() + numbers.pop());
                break;
            case "-":
                numbers.push(numbers.pop() - numbers.pop());
                break;
            default:
                System.out.println("You entered a wrong expression");
        }
    }
}
