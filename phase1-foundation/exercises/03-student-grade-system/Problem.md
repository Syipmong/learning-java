# Exercise 3: Student Grade System

## 🎯 Objective
Create a comprehensive student grade management system that demonstrates object-oriented programming concepts, arrays, methods, and conditional logic.

## 📋 Requirements

### Part A: Student Class
Create a `Student` class with:
1. Instance variables: name, studentId, grades array
2. Constructor to initialize student data
3. Methods to add grades, calculate average, and determine letter grade
4. Method to display student information

### Part B: Grade Management
Implement the following functionality:
1. Add individual grades (0-100 range)
2. Calculate numerical average
3. Convert numerical grade to letter grade (A, B, C, D, F)
4. Determine if student is passing (>= 60%)
5. Find highest and lowest grades

### Part C: Multiple Students
1. Create an array or list of students
2. Add multiple students to the system
3. Display all student information
4. Find the best performing student
5. Calculate class average

### Part D: Enhanced Features
1. Grade validation (ensure grades are 0-100)
2. Different grade scales (standard, honors, etc.)
3. Subject-based grading (Math, Science, English, etc.)
4. Generate grade report

## 🔧 Technical Requirements
- Use proper encapsulation (private variables, public methods)
- Implement input validation
- Use arrays for storing grades
- Use static methods where appropriate
- Follow Java naming conventions

## 📊 Expected Output
```
=== Student Grade Management System ===

Adding new student...
Enter student name: Alice Johnson
Enter student ID: ST001
Student Alice Johnson (ID: ST001) created successfully.

Adding grades for Alice Johnson:
Enter grade for subject 1 (0-100): 85
Enter grade for subject 2 (0-100): 92
Enter grade for subject 3 (0-100): 78
Enter grade for subject 4 (0-100): 88

=== Student Report ===
Name: Alice Johnson
Student ID: ST001
Grades: [85.0, 92.0, 78.0, 88.0]
Average Grade: 85.75
Letter Grade: B
Status: PASSING
Highest Grade: 92.0
Lowest Grade: 78.0

=== Class Summary ===
Total Students: 3
Class Average: 82.5
Best Student: Bob Smith (Average: 89.2)
```

## 🧪 Test Cases
Your system should handle:
1. Valid grade inputs (0-100)
2. Invalid grade inputs (negative, > 100)
3. Empty grade arrays
4. Students with different numbers of grades
5. Duplicate student IDs
6. Edge cases (perfect scores, failing grades)

## 💡 Hints
- Use `ArrayList<Double>` for dynamic grade storage
- Create constants for grade thresholds
- Use enhanced for-loops to iterate through grades
- Implement `toString()` method for easy display
- Consider using enums for letter grades
- Use `String.format()` for decimal formatting

## 🎓 Learning Goals
After completing this exercise, you should understand:
- Class design and object creation
- Encapsulation principles
- Constructor usage
- Instance vs static methods
- Array and ArrayList operations
- Conditional statements and grade logic
- Object interactions and composition
- Input validation techniques
