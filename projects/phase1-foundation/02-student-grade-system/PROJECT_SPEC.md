# Project 2: Student Grade Management System

## 🎯 Project Overview
Develop a comprehensive student grade management system that demonstrates object-oriented programming, inheritance, polymorphism, and data management concepts.

## 📋 Requirements

### Core Features
1. **Student Management**
   - Add, edit, and remove students
   - Student information: ID, name, email, major
   - Grade tracking across multiple subjects

2. **Grade Management**
   - Add grades for different subjects
   - Calculate GPA and weighted averages
   - Letter grade conversion
   - Grade validation and constraints

3. **Reporting System**
   - Individual student reports
   - Class statistics and analytics
   - Grade distribution analysis
   - Performance trending

4. **Data Persistence**
   - Save/load student data to/from files
   - Export reports to CSV format
   - Backup and restore functionality

## 🏗️ System Architecture

### Class Hierarchy
```
StudentGradeSystem
├── Person.java                    // Base class
├── Student.java                   // Extends Person
├── Course.java                    // Course information
├── Grade.java                     // Individual grade
├── GradeBook.java                 // Manages all grades
├── GradeCalculator.java           // Calculation logic
├── ReportGenerator.java           // Report creation
├── FileManager.java               // Data persistence
├── SystemManager.java             // Main system controller
└── StudentGradeSystemApp.java     // Application entry point
```

### Key Classes Design

#### Person (Base Class)
```java
public abstract class Person {
    protected String id;
    protected String name;
    protected String email;
    
    // Constructor, getters, setters
    public abstract void displayInfo();
}
```

#### Student (Inherits from Person)
```java
public class Student extends Person {
    private String major;
    private List<Grade> grades;
    private double gpa;
    
    // Student-specific methods
    public void addGrade(Grade grade);
    public double calculateGPA();
    public String getLetterGrade();
}
```

## 📊 Expected Functionality

### Main Menu System
```
=== Student Grade Management System ===

1. Student Management
   1.1 Add New Student
   1.2 Edit Student Information
   1.3 Remove Student
   1.4 Search Students

2. Grade Management
   2.1 Add Grade
   2.2 Edit Grade
   2.3 Remove Grade
   2.4 View Student Grades

3. Reports & Analytics
   3.1 Individual Student Report
   3.2 Class Statistics
   3.3 Grade Distribution
   3.4 Performance Trends

4. Data Management
   4.1 Save Data
   4.2 Load Data
   4.3 Export Reports
   4.4 Backup System

0. Exit
```

### Sample Output
```
=== Student Report ===
Student ID: ST001
Name: Alice Johnson
Email: alice.johnson@email.com
Major: Computer Science
Enrollment Date: 2024-09-01

=== Grade Summary ===
Course              Grade    Credit Hours    Quality Points
-----------------------------------------------------------
Java Programming       A         3              12.0
Data Structures       B+         4              14.0
Calculus I            B         4              12.0
English Composition   A-         3              11.1
-----------------------------------------------------------
Total Credit Hours: 14
Total Quality Points: 49.1
GPA: 3.51
Class Rank: 15/45

=== Performance Analysis ===
Strongest Subject: Java Programming (A)
Needs Improvement: Calculus I (B)
Trend: Improving (GPA increased 0.2 points)
```

## 🧪 Testing Requirements

### Unit Tests
- Student creation and validation
- Grade calculation accuracy
- GPA computation correctness
- File I/O operations
- Data integrity checks

### Integration Tests
- Complete workflow testing
- Data persistence verification
- Report generation accuracy
- System performance under load

### Test Cases
```java
@Test
public void testGPACalculation() {
    Student student = new Student("ST001", "John Doe", "john@example.com", "CS");
    student.addGrade(new Grade("Math", 85, 3));
    student.addGrade(new Grade("Science", 90, 4));
    
    double expectedGPA = 3.29; // Based on 4.0 scale
    assertEquals(expectedGPA, student.calculateGPA(), 0.01);
}
```

## 🎯 Learning Objectives

### OOP Concepts
- **Inheritance**: Person → Student hierarchy
- **Polymorphism**: Different student types (undergraduate, graduate)
- **Encapsulation**: Private data with public methods
- **Abstraction**: Abstract base classes and interfaces

### Data Structures
- **ArrayList**: Dynamic student and grade lists
- **HashMap**: Quick student lookup by ID
- **TreeMap**: Sorted grade storage
- **Enums**: Grade levels, majors, status

### File Operations
- **File I/O**: Reading and writing student data
- **CSV Processing**: Export functionality
- **Serialization**: Object persistence
- **Error Handling**: File operation exceptions

## 🚀 Implementation Phases

### Phase 1: Core Classes (Week 1)
- Implement Person and Student classes
- Create Grade and Course classes
- Basic CRUD operations
- Simple console interface

### Phase 2: Advanced Features (Week 2)
- Add GradeBook and calculator classes
- Implement reporting system
- Add data validation
- Error handling and edge cases

### Phase 3: Data Persistence (Week 3)
- File I/O implementation
- CSV export functionality
- Backup and restore features
- Performance optimization

### Phase 4: Polish and Testing (Week 4)
- Comprehensive testing
- Code documentation
- User interface improvements
- Final integration testing

## 💡 Advanced Features

### Grade Weighting System
```java
public class WeightedGrade extends Grade {
    private double weight;
    private GradeType type; // QUIZ, MIDTERM, FINAL, PROJECT
    
    @Override
    public double getEffectiveGrade() {
        return grade * weight;
    }
}
```

### Multiple Grading Scales
- Standard 4.0 scale
- Percentage-based grading
- Pass/Fail system
- Honors/Regular distinction

### Statistical Analysis
- Class averages and medians
- Standard deviation calculations
- Grade distribution histograms
- Performance trend analysis

## 🔧 Technical Implementation

### File Format (JSON)
```json
{
  "students": [
    {
      "id": "ST001",
      "name": "Alice Johnson",
      "email": "alice@example.com",
      "major": "Computer Science",
      "grades": [
        {
          "course": "Java Programming",
          "grade": 92.5,
          "creditHours": 3,
          "semester": "Fall 2024"
        }
      ]
    }
  ]
}
```

### Error Handling Strategy
```java
public class GradeValidationException extends Exception {
    public GradeValidationException(String message) {
        super(message);
    }
}

public void validateGrade(double grade) throws GradeValidationException {
    if (grade < 0 || grade > 100) {
        throw new GradeValidationException("Grade must be between 0 and 100");
    }
}
```

## 📚 Key Technologies
- **Java SE**: Core development platform
- **JUnit 5**: Testing framework
- **JSON**: Data format for persistence
- **Apache Commons**: Utility libraries
- **SLF4J**: Logging framework

## 🎓 Assessment Criteria
- **Code Quality**: Clean, readable, well-documented code
- **OOP Implementation**: Proper use of inheritance and polymorphism
- **Functionality**: All requirements implemented correctly
- **Testing**: Comprehensive test coverage
- **User Experience**: Intuitive interface and error handling

This project will solidify your understanding of object-oriented programming and prepare you for more complex system design challenges.
