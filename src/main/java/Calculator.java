import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class Calculator {

    private static Logger logger = LogManager.getLogger(Calculator.class);

    private static final Stack<String> operations = new Stack<>();
    private static final Stack<BigInteger> numbers = new Stack<>();


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
            logger.error("Something went wrong: Please enter your expression!");
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
                numbers.push(BigInteger.valueOf(Long.parseLong(value.toString())));
            }
            index++;
        }

        while (!operations.empty() && !numbers.empty()) {
            doOperation();
        }
    }

    private static void doOperation() {
        String op = operations.pop();
        BigInteger lastElement = numbers.pop();
        BigInteger previousElement = numbers.pop();
        switch (op) {
            case "*":
                numbers.push(previousElement.multiply(lastElement));
                break;
            case "/":
                try {
                    numbers.push(previousElement.divide(lastElement));
                } catch (ArithmeticException ex) {
                    logger.error("Your expression failed, you " + ex.getMessage());
                }
                break;
            case "+":
                numbers.push(previousElement.add(lastElement));
                break;
            case "-":
                numbers.push(previousElement.subtract(lastElement));
                break;
            default:
                logger.error("You entered a wrong expression");
        }
    }
}
