/**
 * Circle implementation demonstrating inheritance and method overriding
 */
public class Circle extends Shape implements Drawable, Scalable {
    private double radius;
    private double scaleFactor;
    
    public Circle(double radius) {
        super("Circle");
        validatePositive(radius, "Radius");
        this.radius = radius;
        this.scaleFactor = 1.0;
    }
    
    public Circle(double radius, String color) {
        super("Circle", color);
        validatePositive(radius, "Radius");
        this.radius = radius;
        this.scaleFactor = 1.0;
    }
    
    // Getters
    public double getRadius() { return radius; }
    public double getDiameter() { return 2 * radius; }
    
    // Abstract method implementations
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("=== Circle Information ===");
        displayBasicInfo();
        System.out.printf("Radius: %.2f units%n", radius);
        System.out.printf("Diameter: %.2f units%n", getDiameter());
        System.out.printf("Circumference: %.2f units%n", calculatePerimeter());
        System.out.println("==========================");
    }
    
    // Drawable interface implementation
    @Override
    public void draw() {
        System.out.println("Drawing a circle:");
        System.out.println(getAsciiArt());
    }
    
    @Override
    public void drawWithDimensions() {
        draw();
        System.out.println("Radius: " + radius + " units");
    }
    
    @Override
    public String getAsciiArt() {
        // Simple ASCII circle representation
        return "    ***    \n" +
               "  *     *  \n" +
               " *       * \n" +
               "*    O    *\n" +
               " *       * \n" +
               "  *     *  \n" +
               "    ***    ";
    }
    
    // Scalable interface implementation
    @Override
    public void scale(double factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("Scale factor must be positive");
        }
        this.radius *= factor;
        this.scaleFactor *= factor;
    }
    
    @Override
    public Shape createScaledCopy(double factor) {
        Circle scaledCircle = new Circle(this.radius * factor, this.color);
        scaledCircle.scaleFactor = this.scaleFactor * factor;
        return scaledCircle;
    }
    
    @Override
    public double getScaleFactor() {
        return scaleFactor;
    }
    
    // Additional circle-specific methods
    public double calculateCircumference() {
        return calculatePerimeter();
    }
    
    public boolean isPointInside(double x, double y) {
        // Assuming circle is centered at origin
        return (x * x + y * y) <= (radius * radius);
    }
}
