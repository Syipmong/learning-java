# Exercise 2: Basic Calculator

## 🎯 Objective
Create a simple calculator program that performs basic arithmetic operations and demonstrates method creation, conditional statements, and user interaction.

## 📋 Requirements

### Part A: Basic Operations
Create methods for the following operations:
1. Addition (`add`)
2. Subtraction (`subtract`)
3. Multiplication (`multiply`)
4. Division (`divide`)
5. Modulus (`remainder`)

### Part B: Calculator Menu
1. Display a menu with operation choices
2. Allow user to select an operation
3. Get two numbers from the user
4. Perform the calculation and display result
5. Handle division by zero

### Part C: Enhanced Features
1. Add a loop to perform multiple calculations
2. Add option to exit the program
3. Implement input validation
4. Add a history feature (store last 5 calculations)

### Part D: Advanced Operations
1. Power calculation (x^y)
2. Square root
3. Percentage calculation
4. Average of multiple numbers

## 🔧 Technical Requirements
- Use methods for each operation
- Implement proper error handling
- Use switch statement for menu selection
- Use loops for program continuation
- Follow Java naming conventions

## 📊 Expected Output
```
=== Basic Calculator ===

Choose an operation:
1. Addition (+)
2. Subtraction (-)
3. Multiplication (*)
4. Division (/)
5. Modulus (%)
6. Power (^)
7. Square Root (√)
8. Percentage (%)
9. View History
0. Exit

Enter your choice: 1
Enter first number: 15.5
Enter second number: 8.3
Result: 15.5 + 8.3 = 23.8

Do you want to perform another calculation? (y/n): y

Choose an operation:
...
```

## 🧪 Test Cases
Your calculator should handle:
1. Positive and negative numbers
2. Decimal numbers
3. Division by zero (should show error message)
4. Invalid menu choices
5. Invalid number inputs
6. Large numbers

## 💡 Hints
- Use `double` for all numbers to handle decimals
- Create separate methods for each operation
- Use `try-catch` blocks for input validation
- Use `ArrayList` or array for history storage
- Use `Math.pow()` for power calculations
- Use `Math.sqrt()` for square root

## 🎓 Learning Goals
After completing this exercise, you should understand:
- Method creation and parameters
- Return types and return statements
- Switch statements
- While loops and do-while loops
- Basic exception handling
- Input validation techniques
- Working with collections (ArrayList)
- Mathematical operations in Java
