import java.util.Arrays;
import java.util.Scanner;

/**
 * Shape Calculator Demo - Comprehensive demonstration of polymorphism,
 * inheritance, and method overriding with geometric shapes
 */
public class ShapeCalculatorDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Shape Area Calculator Demo ===\n");
        
        // Demonstrate polymorphism with shape array
        demonstratePolymorphism();
        
        // Demonstrate inheritance and method overriding
        demonstrateInheritance();
        
        // Demonstrate interfaces
        demonstrateInterfaces();
        
        // Demonstrate comparison and sorting
        demonstrateComparison();
        
        // Interactive calculator
        // runInteractiveCalculator();
        
        System.out.println("\n=== Demo Complete ===");
    }
    
    /**
     * Demonstrates polymorphism by treating different shapes uniformly
     */
    private static void demonstratePolymorphism() {
        System.out.println("1. POLYMORPHISM DEMONSTRATION");
        System.out.println("Creating array of different shapes...\n");
        
        // Create array of shapes (polymorphism)
        Shape[] shapes = {
            new Circle(5.0, "Red"),
            new Rectangle(4.0, 6.0, "Blue"),
            new Square(4.0, "Green"),
            new Triangle(3.0, 4.0, 5.0, "Yellow")
        };
        
        double totalArea = 0;
        double totalPerimeter = 0;
        
        // Process each shape polymorphically
        for (Shape shape : shapes) {
            shape.displayInfo();
            totalArea += shape.calculateArea();
            totalPerimeter += shape.calculatePerimeter();
            System.out.println();
        }
        
        System.out.printf("Total Area of all shapes: %.2f square units%n", totalArea);
        System.out.printf("Total Perimeter of all shapes: %.2f units%n", totalPerimeter);
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
    
    /**
     * Demonstrates inheritance relationships and method overriding
     */
    private static void demonstrateInheritance() {
        System.out.println("2. INHERITANCE DEMONSTRATION");
        System.out.println("Showing inheritance relationships...\n");
        
        // Rectangle and Square relationship
        Rectangle rect = new Rectangle(5.0, 3.0);
        Square square = new Square(4.0);
        
        System.out.println("Rectangle:");
        rect.displayInfo();
        System.out.println("Is square? " + rect.isSquare());
        
        System.out.println("\nSquare (extends Rectangle):");
        square.displayInfo();
        System.out.println("Is square? " + square.isSquare());
        
        // Show that Square IS-A Rectangle
        Rectangle rectRef = square; // Polymorphism
        System.out.println("\nSquare referenced as Rectangle:");
        System.out.println("Area: " + rectRef.calculateArea());
        System.out.println("Type: " + rectRef.getClass().getSimpleName());
        
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
    
    /**
     * Demonstrates interface implementation
     */
    private static void demonstrateInterfaces() {
        System.out.println("3. INTERFACE DEMONSTRATION");
        System.out.println("Showing Drawable and Scalable interfaces...\n");
        
        Circle circle = new Circle(3.0);
        
        // Drawable interface
        System.out.println("Drawing capabilities:");
        circle.draw();
        circle.drawWithDimensions();
        
        // Scalable interface
        System.out.println("\nScaling capabilities:");
        System.out.println("Original circle area: " + circle.calculateArea());
        
        Shape scaledCopy = circle.createScaledCopy(2.0);
        System.out.println("Scaled copy (2x) area: " + scaledCopy.calculateArea());
        
        circle.scale(1.5);
        System.out.println("Original circle after scaling (1.5x): " + circle.calculateArea());
        
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
    
    /**
     * Demonstrates comparison and sorting of shapes
     */
    private static void demonstrateComparison() {
        System.out.println("4. COMPARISON AND SORTING DEMONSTRATION");
        System.out.println("Sorting shapes by area...\n");
        
        Shape[] shapes = {
            new Circle(2.0),
            new Rectangle(3.0, 4.0),
            new Square(3.0),
            new Triangle(6.0, 8.0, 10.0),
            new Circle(4.0)
        };
        
        System.out.println("Before sorting:");
        for (Shape shape : shapes) {
            System.out.printf("%-12s - Area: %6.2f%n", 
                            shape.getName(), shape.calculateArea());
        }
        
        // Sort by area (using Comparable interface)
        Arrays.sort(shapes);
        
        System.out.println("\nAfter sorting by area:");
        for (Shape shape : shapes) {
            System.out.printf("%-12s - Area: %6.2f%n", 
                            shape.getName(), shape.calculateArea());
        }
        
        // Find largest and smallest
        Shape smallest = shapes[0];
        Shape largest = shapes[shapes.length - 1];
        
        System.out.println("\nSmallest shape: " + smallest);
        System.out.println("Largest shape: " + largest);
        
        System.out.println("\n" + "=".repeat(50) + "\n");
    }
    
    /**
     * Interactive calculator for user to create and compare shapes
     */
    private static void runInteractiveCalculator() {
        System.out.println("5. INTERACTIVE SHAPE CALCULATOR");
        System.out.println("Create your own shapes and compare them!\n");
        
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            System.out.println("Choose a shape to create:");
            System.out.println("1. Circle");
            System.out.println("2. Rectangle");
            System.out.println("3. Square");
            System.out.println("4. Triangle");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            
            int choice = scanner.nextInt();
            
            try {
                Shape shape = null;
                
                switch (choice) {
                    case 1:
                        System.out.print("Enter radius: ");
                        double radius = scanner.nextDouble();
                        shape = new Circle(radius);
                        break;
                        
                    case 2:
                        System.out.print("Enter width: ");
                        double width = scanner.nextDouble();
                        System.out.print("Enter height: ");
                        double height = scanner.nextDouble();
                        shape = new Rectangle(width, height);
                        break;
                        
                    case 3:
                        System.out.print("Enter side length: ");
                        double side = scanner.nextDouble();
                        shape = new Square(side);
                        break;
                        
                    case 4:
                        System.out.print("Enter side A: ");
                        double sideA = scanner.nextDouble();
                        System.out.print("Enter side B: ");
                        double sideB = scanner.nextDouble();
                        System.out.print("Enter side C: ");
                        double sideC = scanner.nextDouble();
                        shape = new Triangle(sideA, sideB, sideC);
                        break;
                        
                    case 0:
                        running = false;
                        continue;
                        
                    default:
                        System.out.println("Invalid choice!");
                        continue;
                }
                
                if (shape != null) {
                    System.out.println("\nShape created successfully!");
                    shape.displayInfo();
                    
                    if (shape instanceof Drawable) {
                        ((Drawable) shape).draw();
                    }
                    
                    System.out.println();
                }
                
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        scanner.close();
        System.out.println("Thank you for using the Shape Calculator!");
    }
    
    /**
     * Utility method to demonstrate advanced shape properties
     */
    private static void demonstrateAdvancedProperties() {
        System.out.println("ADVANCED SHAPE PROPERTIES");
        
        // Circle properties
        Circle circle = new Circle(5.0);
        System.out.println("Circle with radius 5:");
        System.out.println("- Circumference: " + circle.calculateCircumference());
        System.out.println("- Point (3,4) inside? " + circle.isPointInside(3, 4));
        
        // Square properties
        Square square = new Square(6.0);
        System.out.println("\nSquare with side 6:");
        System.out.println("- Inscribed circle radius: " + square.calculateInscribedCircleRadius());
        System.out.println("- Circumscribed circle radius: " + square.calculateCircumscribedCircleRadius());
        
        // Triangle properties
        Triangle triangle = new Triangle(3, 4, 5);
        System.out.println("\nTriangle (3,4,5):");
        System.out.println("- Type: " + triangle.getTriangleType());
        System.out.println("- Is right triangle? " + triangle.isRightTriangle());
        System.out.println("- Inradius: " + triangle.calculateInradius());
        System.out.println("- Circumradius: " + triangle.calculateCircumradius());
    }
}
