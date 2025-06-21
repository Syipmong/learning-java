/**
 * Rectangle implementation demonstrating inheritance and polymorphism
 */
public class Rectangle extends Shape implements Drawable, Scalable {
    protected double width;
    protected double height;
    private double scaleFactor;
    
    public Rectangle(double width, double height) {
        super("Rectangle");
        validatePositive(width, "Width");
        validatePositive(height, "Height");
        this.width = width;
        this.height = height;
        this.scaleFactor = 1.0;
    }
    
    public Rectangle(double width, double height, String color) {
        super("Rectangle", color);
        validatePositive(width, "Width");
        validatePositive(height, "Height");
        this.width = width;
        this.height = height;
        this.scaleFactor = 1.0;
    }
    
    // Getters
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    
    // Abstract method implementations
    @Override
    public double calculateArea() {
        return width * height;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * (width + height);
    }
    
    @Override
    public void displayInfo() {
        System.out.println("=== Rectangle Information ===");
        displayBasicInfo();
        System.out.printf("Width: %.2f units%n", width);
        System.out.printf("Height: %.2f units%n", height);
        System.out.printf("Diagonal: %.2f units%n", calculateDiagonal());
        System.out.println("=============================");
    }
    
    // Drawable interface implementation
    @Override
    public void draw() {
        System.out.println("Drawing a rectangle:");
        System.out.println(getAsciiArt());
    }
    
    @Override
    public void drawWithDimensions() {
        draw();
        System.out.println("Width: " + width + " units, Height: " + height + " units");
    }
    
    @Override
    public String getAsciiArt() {
        return "┌─────────────┐\n" +
               "│             │\n" +
               "│      ▣      │\n" +
               "│             │\n" +
               "└─────────────┘";
    }
    
    // Scalable interface implementation
    @Override
    public void scale(double factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("Scale factor must be positive");
        }
        this.width *= factor;
        this.height *= factor;
        this.scaleFactor *= factor;
    }
    
    @Override
    public Shape createScaledCopy(double factor) {
        Rectangle scaledRect = new Rectangle(this.width * factor, this.height * factor, this.color);
        scaledRect.scaleFactor = this.scaleFactor * factor;
        return scaledRect;
    }
    
    @Override
    public double getScaleFactor() {
        return scaleFactor;
    }
    
    // Additional rectangle-specific methods
    public double calculateDiagonal() {
        return Math.sqrt(width * width + height * height);
    }
    
    public boolean isSquare() {
        return Math.abs(width - height) < 0.001; // Account for floating point precision
    }
    
    public boolean isPointInside(double x, double y) {
        // Assuming rectangle is positioned at origin
        return x >= 0 && x <= width && y >= 0 && y <= height;
    }
    
    public double getAspectRatio() {
        return width / height;
    }
}
