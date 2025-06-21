import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BasicCalculator {
    private Scanner scanner;
    private ArrayList<String> history;
    private static final int MAX_HISTORY = 5;
    
    public BasicCalculator() {
        scanner = new Scanner(System.in);
        history = new ArrayList<>();
    }
    
    public static void main(String[] args) {
        BasicCalculator calculator = new BasicCalculator();
        calculator.runCalculator();
    }
    
    public void runCalculator() {
        System.out.println("=== Basic Calculator ===\n");
        
        boolean continueCalculating = true;
        
        while (continueCalculating) {
            displayMenu();
            int choice = getValidChoice();
            
            switch (choice) {
                case 1: // Addition
                    performBasicOperation("+", 1);
                    break;
                case 2: // Subtraction
                    performBasicOperation("-", 2);
                    break;
                case 3: // Multiplication
                    performBasicOperation("*", 3);
                    break;
                case 4: // Division
                    performBasicOperation("/", 4);
                    break;
                case 5: // Modulus
                    performBasicOperation("%", 5);
                    break;
                case 6: // Power
                    performPowerOperation();
                    break;
                case 7: // Square Root
                    performSquareRootOperation();
                    break;
                case 8: // Percentage
                    performPercentageOperation();
                    break;
                case 9: // View History
                    displayHistory();
                    break;
                case 0: // Exit
                    continueCalculating = false;
                    System.out.println("Thank you for using the calculator!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            if (continueCalculating && choice != 9) {
                System.out.print("\nDo you want to perform another calculation? (y/n): ");
                String response = scanner.nextLine().toLowerCase();
                if (!response.equals("y") && !response.equals("yes")) {
                    continueCalculating = false;
                    System.out.println("Thank you for using the calculator!");
                }
            }
            System.out.println();
        }
        
        scanner.close();
    }
    
    public void displayMenu() {
        System.out.println("Choose an operation:");
        System.out.println("1. Addition (+)");
        System.out.println("2. Subtraction (-)");
        System.out.println("3. Multiplication (*)");
        System.out.println("4. Division (/)");
        System.out.println("5. Modulus (%)");
        System.out.println("6. Power (^)");
        System.out.println("7. Square Root (√)");
        System.out.println("8. Percentage (%)");
        System.out.println("9. View History");
        System.out.println("0. Exit");
        System.out.print("\nEnter your choice: ");
    }
    
    private void performBasicOperation(String operator, int operation) {
        double num1 = getValidNumber("Enter first number: ");
        double num2 = getValidNumber("Enter second number: ");
        
        double result = 0;
        String calculation = "";
        boolean validOperation = true;
        
        switch (operation) {
            case 1:
                result = add(num1, num2);
                calculation = num1 + " + " + num2 + " = " + result;
                break;
            case 2:
                result = subtract(num1, num2);
                calculation = num1 + " - " + num2 + " = " + result;
                break;
            case 3:
                result = multiply(num1, num2);
                calculation = num1 + " * " + num2 + " = " + result;
                break;
            case 4:
                if (num2 == 0) {
                    System.out.println("Error: Division by zero is not allowed!");
                    validOperation = false;
                } else {
                    result = divide(num1, num2);
                    calculation = num1 + " / " + num2 + " = " + result;
                }
                break;
            case 5:
                if (num2 == 0) {
                    System.out.println("Error: Modulus by zero is not allowed!");
                    validOperation = false;
                } else {
                    result = remainder(num1, num2);
                    calculation = num1 + " % " + num2 + " = " + result;
                }
                break;
        }
        
        if (validOperation) {
            System.out.println("Result: " + calculation);
            addToHistory(calculation);
        }
    }
    
    private void performPowerOperation() {
        double base = getValidNumber("Enter base number: ");
        double exponent = getValidNumber("Enter exponent: ");
        
        double result = power(base, exponent);
        String calculation = base + " ^ " + exponent + " = " + result;
        
        System.out.println("Result: " + calculation);
        addToHistory(calculation);
    }
    
    private void performSquareRootOperation() {
        double number = getValidNumber("Enter number: ");
        
        if (number < 0) {
            System.out.println("Error: Cannot calculate square root of negative number!");
            return;
        }
        
        double result = squareRoot(number);
        String calculation = "√" + number + " = " + result;
        
        System.out.println("Result: " + calculation);
        addToHistory(calculation);
    }
    
    private void performPercentageOperation() {
        double number = getValidNumber("Enter the number: ");
        double percent = getValidNumber("Enter the percentage: ");
        
        double result = percentage(number, percent);
        String calculation = percent + "% of " + number + " = " + result;
        
        System.out.println("Result: " + calculation);
        addToHistory(calculation);
    }
    
    // Basic arithmetic methods
    public double add(double a, double b) {
        return a + b;
    }
    
    public double subtract(double a, double b) {
        return a - b;
    }
    
    public double multiply(double a, double b) {
        return a * b;
    }
    
    public double divide(double a, double b) {
        return a / b;
    }
    
    public double remainder(double a, double b) {
        return a % b;
    }
    
    // Advanced operations
    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }
    
    public double squareRoot(double number) {
        return Math.sqrt(number);
    }
    
    public double percentage(double number, double percent) {
        return (number * percent) / 100.0;
    }
    
    // Helper methods
    public void addToHistory(String calculation) {
        if (history.size() >= MAX_HISTORY) {
            history.remove(0); // Remove oldest calculation
        }
        history.add(calculation);
    }
    
    public void displayHistory() {
        System.out.println("=== Calculation History ===");
        if (history.isEmpty()) {
            System.out.println("No calculations performed yet.");
        } else {
            for (int i = 0; i < history.size(); i++) {
                System.out.println((i + 1) + ". " + history.get(i));
            }
        }
        System.out.println();
    }
    
    // Input validation methods
    public double getValidNumber(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
    
    public int getValidChoice() {
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // Clear invalid input
                System.out.print("Enter your choice: ");
            }
        }
    }
}
