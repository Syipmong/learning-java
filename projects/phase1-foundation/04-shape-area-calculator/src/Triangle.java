/**
 * Triangle implementation demonstrating inheritance and geometric calculations
 */
public class Triangle extends Shape implements Drawable, Scalable {
    protected double sideA;
    protected double sideB;
    protected double sideC;
    private double scaleFactor;
    
    public Triangle(double sideA, double sideB, double sideC) {
        super("Triangle");
        validateTriangle(sideA, sideB, sideC);
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
        this.scaleFactor = 1.0;
    }
    
    public Triangle(double sideA, double sideB, double sideC, String color) {
        super("Triangle", color);
        validateTriangle(sideA, sideB, sideC);
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
        this.scaleFactor = 1.0;
    }
    
    // Getters
    public double getSideA() { return sideA; }
    public double getSideB() { return sideB; }
    public double getSideC() { return sideC; }
    
    // Validation method for triangle inequality
    private void validateTriangle(double a, double b, double c) {
        validatePositive(a, "Side A");
        validatePositive(b, "Side B");
        validatePositive(c, "Side C");
        
        if (a + b <= c || a + c <= b || b + c <= a) {
            throw new IllegalArgumentException("Invalid triangle: sides do not satisfy triangle inequality");
        }
    }
    
    // Abstract method implementations
    @Override
    public double calculateArea() {
        // Using Heron's formula
        double s = calculatePerimeter() / 2.0; // semi-perimeter
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }
    
    @Override
    public double calculatePerimeter() {
        return sideA + sideB + sideC;
    }
    
    @Override
    public void displayInfo() {
        System.out.println("=== Triangle Information ===");
        displayBasicInfo();
        System.out.printf("Side A: %.2f units%n", sideA);
        System.out.printf("Side B: %.2f units%n", sideB);
        System.out.printf("Side C: %.2f units%n", sideC);
        System.out.printf("Triangle Type: %s%n", getTriangleType());
        System.out.printf("Largest Angle: %.2f degrees%n", Math.toDegrees(getLargestAngle()));
        System.out.println("============================");
    }
    
    // Drawable interface implementation
    @Override
    public void draw() {
        System.out.println("Drawing a triangle:");
        System.out.println(getAsciiArt());
    }
    
    @Override
    public void drawWithDimensions() {
        draw();
        System.out.printf("Sides: %.2f, %.2f, %.2f units%n", sideA, sideB, sideC);
    }
    
    @Override
    public String getAsciiArt() {
        return "    /\\\n" +
               "   /  \\\n" +
               "  /    \\\n" +
               " /      \\\n" +
               "/________\\";
    }
    
    // Scalable interface implementation
    @Override
    public void scale(double factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("Scale factor must be positive");
        }
        this.sideA *= factor;
        this.sideB *= factor;
        this.sideC *= factor;
        this.scaleFactor *= factor;
    }
    
    @Override
    public Shape createScaledCopy(double factor) {
        Triangle scaledTriangle = new Triangle(this.sideA * factor, this.sideB * factor, this.sideC * factor, this.color);
        scaledTriangle.scaleFactor = this.scaleFactor * factor;
        return scaledTriangle;
    }
    
    @Override
    public double getScaleFactor() {
        return scaleFactor;
    }
    
    // Additional triangle-specific methods
    public String getTriangleType() {
        // Check for equilateral
        if (Math.abs(sideA - sideB) < 0.001 && Math.abs(sideB - sideC) < 0.001) {
            return "Equilateral";
        }
        // Check for isosceles
        else if (Math.abs(sideA - sideB) < 0.001 || 
                 Math.abs(sideB - sideC) < 0.001 || 
                 Math.abs(sideA - sideC) < 0.001) {
            return "Isosceles";
        }
        // Check for right triangle (Pythagorean theorem)
        else if (isRightTriangle()) {
            return "Right";
        }
        else {
            return "Scalene";
        }
    }
    
    public boolean isRightTriangle() {
        double[] sides = {sideA, sideB, sideC};
        java.util.Arrays.sort(sides);
        
        // Check if a² + b² = c² (allowing for floating point precision)
        double sumOfSquares = sides[0] * sides[0] + sides[1] * sides[1];
        double hypotenuseSquared = sides[2] * sides[2];
        
        return Math.abs(sumOfSquares - hypotenuseSquared) < 0.001;
    }
    
    public double getLargestAngle() {
        // Using law of cosines: c² = a² + b² - 2ab*cos(C)
        // Therefore: cos(C) = (a² + b² - c²) / (2ab)
        
        double[] sides = {sideA, sideB, sideC};
        java.util.Arrays.sort(sides);
        
        // Largest angle is opposite to largest side
        double a = sides[0];
        double b = sides[1];
        double c = sides[2];
        
        double cosC = (a * a + b * b - c * c) / (2 * a * b);
        return Math.acos(cosC);
    }
    
    public double calculateHeight(double base) {
        // Height = 2 * Area / base
        return 2 * calculateArea() / base;
    }
    
    public double calculateInradius() {
        // Inradius = Area / semi-perimeter
        return calculateArea() / (calculatePerimeter() / 2.0);
    }
    
    public double calculateCircumradius() {
        // Circumradius = (a * b * c) / (4 * Area)
        return (sideA * sideB * sideC) / (4 * calculateArea());
    }
}
