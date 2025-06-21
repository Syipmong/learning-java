/**
 * Abstract base class for all geometric shapes
 * Demonstrates abstract classes, polymorphism, and method overriding
 */
public abstract class Shape implements Comparable<Shape> {
    protected String name;
    protected String color;
    
    public Shape(String name) {
        this.name = name;
        this.color = "Default";
    }
    
    public Shape(String name, String color) {
        this.name = name;
        this.color = color;
    }
    
    // Abstract methods to be implemented by subclasses
    public abstract double calculateArea();
    public abstract double calculatePerimeter();
    public abstract void displayInfo();
    
    // Common methods available to all shapes
    public String getName() { return name; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    // Default comparison by area (can be overridden)
    @Override
    public int compareTo(Shape other) {
        return Double.compare(this.calculateArea(), other.calculateArea());
    }
    
    // Common display format
    protected void displayBasicInfo() {
        System.out.println("Shape: " + name);
        System.out.println("Color: " + color);
        System.out.printf("Area: %.2f square units%n", calculateArea());
        System.out.printf("Perimeter: %.2f units%n", calculatePerimeter());
    }
    
    // Utility method for validation
    protected void validatePositive(double value, String paramName) {
        if (value <= 0) {
            throw new IllegalArgumentException(paramName + " must be positive, got: " + value);
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s (Area: %.2f, Perimeter: %.2f)", 
                           name, calculateArea(), calculatePerimeter());
    }
}
