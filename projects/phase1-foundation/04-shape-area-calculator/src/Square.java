/**
 * Square implementation extending Rectangle
 * Demonstrates inheritance and method specialization
 */
public class Square extends Rectangle {
    
    public Square(double side) {
        super(side, side);
        this.name = "Square";
    }
    
    public Square(double side, String color) {
        super(side, side, color);
        this.name = "Square";
    }
    
    // Getter for side (more intuitive for squares)
    public double getSide() {
        return width; // width and height are the same for squares
    }
    
    // Override display to show square-specific information
    @Override
    public void displayInfo() {
        System.out.println("=== Square Information ===");
        displayBasicInfo();
        System.out.printf("Side Length: %.2f units%n", getSide());
        System.out.printf("Diagonal: %.2f units%n", calculateDiagonal());
        System.out.println("==========================");
    }
    
    // Override ASCII art for square
    @Override
    public String getAsciiArt() {
        return "┌─────────┐\n" +
               "│         │\n" +
               "│    ■    │\n" +
               "│         │\n" +
               "└─────────┘";
    }
    
    // Override scaling to maintain square properties
    @Override
    public void scale(double factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("Scale factor must be positive");
        }
        double newSide = width * factor;
        this.width = newSide;
        this.height = newSide;
    }
    
    @Override
    public Shape createScaledCopy(double factor) {
        return new Square(this.width * factor, this.color);
    }
    
    // Override isSquare to always return true
    @Override
    public boolean isSquare() {
        return true;
    }
    
    // Additional square-specific methods
    public double calculateInscribedCircleRadius() {
        return getSide() / 2.0;
    }
    
    public double calculateCircumscribedCircleRadius() {
        return getSide() * Math.sqrt(2) / 2.0;
    }
    
    public double calculateInscribedCircleArea() {
        double radius = calculateInscribedCircleRadius();
        return Math.PI * radius * radius;
    }
    
    public double calculateCircumscribedCircleArea() {
        double radius = calculateCircumscribedCircleRadius();
        return Math.PI * radius * radius;
    }
}
