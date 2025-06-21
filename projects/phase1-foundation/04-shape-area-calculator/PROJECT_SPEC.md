# Shape Area Calculator

## 🎯 Project Objectives
Master polymorphism, method overriding, and inheritance through a geometric shape calculator system.

## 📋 Requirements

### Core Features
1. **Shape Management**: Create and manage different geometric shapes
2. **Area Calculation**: Calculate areas for various shapes using polymorphism
3. **Perimeter Calculation**: Calculate perimeters with method overriding
4. **Shape Comparison**: Compare shapes by area and perimeter
5. **Shape Display**: Visualize shape information and properties

### Technical Requirements
- Implement abstract base class for shapes
- Use method overriding for specific calculations
- Demonstrate polymorphism with shape arrays
- Handle invalid shape parameters
- Implement comparable interface for shape sorting

## 🏗️ System Architecture

### Class Hierarchy
```
Shape (Abstract Class)
├── Circle
├── Rectangle
│   └── Square
├── Triangle
│   ├── EquilateralTriangle
│   ├── IsoscelesTriangle
│   └── RightTriangle
└── Polygon
    ├── Pentagon
    ├── Hexagon
    └── Octagon
```

### Interfaces
- `Drawable`: Shapes that can be visually represented
- `Scalable`: Shapes that can be resized
- `Comparable<Shape>`: For sorting shapes

## 📊 Class Specifications

### Abstract Base Class

#### `Shape`
```java
public abstract class Shape implements Comparable<Shape> {
    protected String name;
    protected String color;
    
    // Abstract methods
    public abstract double calculateArea();
    public abstract double calculatePerimeter();
    public abstract void displayInfo();
    
    // Common methods
    public String getName() { return name; }
    public String getColor() { return color; }
}
```

### Concrete Shape Classes

#### `Circle`
- Properties: radius
- Area: π × r²
- Perimeter: 2 × π × r

#### `Rectangle`
- Properties: width, height
- Area: width × height
- Perimeter: 2 × (width + height)

#### `Square` (extends Rectangle)
- Properties: side length
- Inherits area/perimeter from Rectangle

#### `Triangle`
- Properties: base, height, side lengths
- Area: (base × height) / 2
- Perimeter: sum of all sides

## 🧪 Test Cases

### Unit Tests
1. **Shape Creation**: Valid and invalid parameters
2. **Area Calculations**: Accuracy for all shape types
3. **Perimeter Calculations**: Boundary cases
4. **Polymorphism**: Method calls through base class references
5. **Comparison**: Sorting shapes by area/perimeter

### Integration Tests
1. **Shape Calculator**: Multiple shapes operations
2. **Shape Collection**: Array/list operations
3. **User Input**: Interactive shape creation

## 🎨 Sample Usage

```java
public class ShapeDemo {
    public static void main(String[] args) {
        // Create different shapes
        Shape[] shapes = {
            new Circle(5.0),
            new Rectangle(4.0, 6.0),
            new Square(4.0),
            new Triangle(3.0, 4.0, 5.0)
        };
        
        // Calculate total area (polymorphism)
        double totalArea = 0;
        for (Shape shape : shapes) {
            totalArea += shape.calculateArea();
            shape.displayInfo();
        }
        
        System.out.println("Total Area: " + totalArea);
    }
}
```

## 🚀 Implementation Steps

### Phase 1: Base Architecture
1. Create abstract `Shape` class
2. Define abstract methods for area and perimeter
3. Implement common properties and methods
4. Create `Drawable` interface

### Phase 2: Basic Shapes
1. Implement `Circle` class
2. Implement `Rectangle` class
3. Implement `Square` as Rectangle subclass
4. Add validation for parameters

### Phase 3: Complex Shapes
1. Implement `Triangle` base class
2. Create specialized triangle types
3. Add polygon support
4. Implement comparison logic

### Phase 4: Advanced Features
1. Add scaling functionality
2. Implement shape drawing (ASCII art)
3. Create shape calculator utility
4. Add statistical analysis

## 🏆 Success Criteria

### Functional Requirements ✅
- [ ] All shapes calculate area correctly
- [ ] Perimeter calculations are accurate
- [ ] Polymorphism works with shape arrays
- [ ] Shape comparison and sorting works
- [ ] Invalid parameters are handled

### Technical Requirements ✅
- [ ] Proper inheritance hierarchy
- [ ] Method overriding implementation
- [ ] Interface implementation
- [ ] Polymorphic behavior demonstration
- [ ] Clean code and documentation

## 📈 Extension Ideas

### Advanced Features
1. **3D Shapes**: Extend to 3D with volume calculations
2. **Shape Builder**: Factory pattern for shape creation
3. **Graphics Integration**: JavaFX/Swing visualization
4. **Shape Transformation**: Rotation, translation, scaling
5. **Shape Intersection**: Calculate overlapping areas
6. **File I/O**: Save/load shape collections

### Mathematical Extensions
1. **Advanced Calculations**: Centroid, moment of inertia
2. **Geometric Algorithms**: Collision detection
3. **Optimization**: Find largest/smallest shapes
4. **Statistical Analysis**: Shape distribution analysis
5. **Unit Conversion**: Multiple measurement units

## 🔍 Learning Outcomes

By completing this project, you will understand:
- Abstract classes and concrete implementations
- Method overriding and polymorphism
- Inheritance hierarchies and specialization
- Interface implementation and contracts
- Comparable interface and natural ordering
- Mathematical programming concepts
- Object-oriented design patterns

## 📚 Mathematical Formulas

### Basic Shapes
- **Circle**: Area = πr², Perimeter = 2πr
- **Rectangle**: Area = w×h, Perimeter = 2(w+h)
- **Square**: Area = s², Perimeter = 4s
- **Triangle**: Area = ½bh, Perimeter = a+b+c

### Advanced Shapes
- **Regular Polygon**: Area = ½pa (p=perimeter, a=apothem)
- **Ellipse**: Area = πab (a,b = semi-axes)
- **Trapezoid**: Area = ½(b₁+b₂)h

Happy calculating! 📐
