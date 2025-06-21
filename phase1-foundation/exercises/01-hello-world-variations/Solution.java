import java.util.Scanner;

public class HelloWorldVariations {
    public static void main(String[] args) {
        System.out.println("=== Hello World Variations ===\n");
        
        // Part A: Basic Hello World
        System.out.println("Part A: Basic Greeting");
        System.out.println("Hello, World! My name is John Doe\n");
        
        // Part B: Variables and Data Types
        System.out.println("Part B: Personal Information");
        String name = "John Doe";
        int age = 25;
        double height = 1.75;
        boolean likesProgramming = true;
        
        System.out.println("Name: " + name);
        System.out.println("Age: " + age + " years old");
        System.out.println("Height: " + height + " meters");
        System.out.println("Likes programming: " + likesProgramming + "\n");
        
        // Part C: User Input
        System.out.println("Part C: Interactive Greeting");
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("What's your name? ");
        String userName = scanner.nextLine();
        
        System.out.print("How old are you? ");
        int userAge = scanner.nextInt();
        
        System.out.println("Hello " + userName + "! Nice to meet you. You are " + userAge + " years old.\n");
        
        // Part D: Age Calculator
        System.out.println("Part D: Age Calculator");
        System.out.print("What year were you born? ");
        int birthYear = scanner.nextInt();
        
        int currentYear = 2025;
        int calculatedAge = currentYear - birthYear;
        
        System.out.println("Based on " + currentYear + ", you are approximately " + calculatedAge + " years old.");
        
        // Close the scanner
        scanner.close();
        
        System.out.println("\nProgram completed successfully!");
    }
}
