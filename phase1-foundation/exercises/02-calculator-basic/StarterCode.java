import java.util.ArrayList;
import java.util.Scanner;

public class BasicCalculator {
    // TODO: Create instance variables for scanner and history
    
    
    public static void main(String[] args) {
        BasicCalculator calculator = new BasicCalculator();
        calculator.runCalculator();
    }
    
    // TODO: Implement runCalculator method
    // This should contain the main program loop
    public void runCalculator() {
        
    }
    
    // TODO: Implement displayMenu method
    // Show all available operations
    public void displayMenu() {
        
    }
    
    // TODO: Implement basic arithmetic methods
    // All methods should take two double parameters and return double
    
    public double add(double a, double b) {
        // TODO: Implement addition
        return 0.0;
    }
    
    public double subtract(double a, double b) {
        // TODO: Implement subtraction
        return 0.0;
    }
    
    public double multiply(double a, double b) {
        // TODO: Implement multiplication
        return 0.0;
    }
    
    public double divide(double a, double b) {
        // TODO: Implement division with zero-check
        return 0.0;
    }
    
    public double remainder(double a, double b) {
        // TODO: Implement modulus operation
        return 0.0;
    }
    
    // TODO: Implement advanced operations
    public double power(double base, double exponent) {
        // TODO: Use Math.pow()
        return 0.0;
    }
    
    public double squareRoot(double number) {
        // TODO: Use Math.sqrt()
        return 0.0;
    }
    
    public double percentage(double number, double percent) {
        // TODO: Calculate percentage
        return 0.0;
    }
    
    // TODO: Implement helper methods
    public void addToHistory(String calculation) {
        // TODO: Add calculation to history (limit to 5)
        
    }
    
    public void displayHistory() {
        // TODO: Show calculation history
        
    }
    
    // TODO: Implement input validation methods
    public double getValidNumber(String prompt) {
        // TODO: Get valid number input with error handling
        return 0.0;
    }
    
    public int getValidChoice() {
        // TODO: Get valid menu choice
        return 0;
    }
}
