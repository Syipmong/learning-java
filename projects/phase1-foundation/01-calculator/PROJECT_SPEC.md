# Project 1: Calculator Application

## 🎯 Project Overview
Build a comprehensive calculator application that demonstrates basic Java programming concepts, object-oriented design, and user interaction. This project serves as your first complete Java application.

## 📋 Requirements

### Functional Requirements
1. **Basic Arithmetic Operations**
   - Addition, subtraction, multiplication, division
   - Modulus operation
   - Power calculation

2. **Advanced Features**
   - Memory functions (store, recall, clear)
   - History of calculations
   - Scientific functions (sqrt, factorial)
   - Input validation and error handling

3. **User Interface**
   - Console-based menu system
   - Clear and intuitive user prompts
   - Formatted output display

### Technical Requirements
- Use object-oriented programming principles
- Implement proper error handling
- Include input validation
- Follow Java coding conventions
- Write clean, documented code

## 🏗️ Architecture Design

### Class Structure
```
Calculator
├── CalculatorEngine.java      // Core calculation logic
├── MemoryManager.java         // Memory operations
├── HistoryManager.java        // Calculation history
├── InputValidator.java        // Input validation
├── Calculator.java            // Main application class
└── CalculatorTest.java        // Unit tests
```

### Class Responsibilities
- **CalculatorEngine**: Performs all mathematical operations
- **MemoryManager**: Handles memory store/recall operations
- **HistoryManager**: Manages calculation history
- **InputValidator**: Validates user inputs
- **Calculator**: Main class with user interface logic

## 📊 Expected Output
```
=== Advanced Calculator ===

Main Menu:
1. Basic Operations
2. Scientific Functions
3. Memory Operations
4. View History
5. Settings
0. Exit

Choose option: 1

Basic Operations Menu:
1. Addition (+)
2. Subtraction (-)
3. Multiplication (*)
4. Division (/)
5. Modulus (%)
6. Power (^)
0. Back to Main Menu

Choose operation: 1

Enter first number: 15.5
Enter second number: 8.3
Result: 15.5 + 8.3 = 23.8

Save to memory? (y/n): y
Result saved to memory.

Perform another calculation? (y/n): y
```

## 🧪 Test Cases

### Basic Operations
- Addition with positive numbers
- Addition with negative numbers
- Division by zero handling
- Large number calculations
- Decimal precision handling

### Edge Cases
- Invalid input handling
- Overflow scenarios
- Memory operations with empty memory
- History operations with empty history

### User Interface
- Menu navigation
- Input validation messages
- Error message clarity
- Exit functionality

## 🎯 Learning Objectives
By completing this project, you will:
- Master basic Java syntax and control structures
- Understand object-oriented programming principles
- Learn proper error handling techniques
- Practice user input validation
- Implement clean code practices
- Write and run unit tests

## 🚀 Implementation Steps

### Phase 1: Basic Structure (Week 1)
1. Create basic class structure
2. Implement main menu system
3. Add basic arithmetic operations
4. Test basic functionality

### Phase 2: Enhanced Features (Week 2)
1. Add input validation
2. Implement error handling
3. Add memory functions
4. Create history management

### Phase 3: Polish and Testing (Week 3)
1. Improve user interface
2. Add scientific functions
3. Write comprehensive tests
4. Code cleanup and documentation

## 💡 Extension Ideas
After completing the basic requirements, consider adding:
- Graphical user interface (Swing/JavaFX)
- Configuration file for settings
- Unit conversion features
- Statistical functions
- Expression parsing (e.g., "2+3*4")
- Multiple number bases (binary, hexadecimal)

## 📚 Key Concepts Applied
- **OOP Principles**: Encapsulation, abstraction
- **Exception Handling**: Try-catch blocks, custom exceptions
- **Data Structures**: Arrays, ArrayLists for history
- **Input/Output**: Scanner class, formatted output
- **Control Structures**: Loops, conditional statements
- **Method Design**: Parameters, return values, overloading

## 🔧 Tools and Technologies
- **Java SE**: Core Java development
- **JUnit**: Unit testing framework
- **Scanner**: User input handling
- **IDE**: IntelliJ IDEA or Eclipse

## 📖 Documentation Requirements
- Class and method documentation (Javadoc)
- README with usage instructions
- Code comments for complex logic
- Test documentation

This project will give you a solid foundation in Java programming and prepare you for more complex projects in the intermediate phase.
