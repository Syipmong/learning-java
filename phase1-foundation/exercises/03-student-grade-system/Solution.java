import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private String name;
    private String studentId;
    private ArrayList<Double> grades;
    
    public Student(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
        this.grades = new ArrayList<>();
    }
    
    public void addGrade(double grade) {
        if (grade >= 0 && grade <= 100) {
            grades.add(grade);
        } else {
            System.out.println("Invalid grade! Grade must be between 0 and 100.");
        }
    }
    
    public double calculateAverage() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        
        double sum = 0.0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.size();
    }
    
    public char getLetterGrade() {
        double average = calculateAverage();
        
        if (average >= 90) return 'A';
        else if (average >= 80) return 'B';
        else if (average >= 70) return 'C';
        else if (average >= 60) return 'D';
        else return 'F';
    }
    
    public boolean isPassing() {
        return calculateAverage() >= 60.0;
    }
    
    public double getHighestGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        
        double highest = grades.get(0);
        for (double grade : grades) {
            if (grade > highest) {
                highest = grade;
            }
        }
        return highest;
    }
    
    public double getLowestGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        
        double lowest = grades.get(0);
        for (double grade : grades) {
            if (grade < lowest) {
                lowest = grade;
            }
        }
        return lowest;
    }
    
    // Getter methods
    public String getName() {
        return name;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public ArrayList<Double> getGrades() {
        return grades;
    }
    
    public void displayInfo() {
        System.out.println("\n=== Student Report ===");
        System.out.println("Name: " + name);
        System.out.println("Student ID: " + studentId);
        System.out.println("Grades: " + grades);
        System.out.printf("Average Grade: %.2f%n", calculateAverage());
        System.out.println("Letter Grade: " + getLetterGrade());
        System.out.println("Status: " + (isPassing() ? "PASSING" : "FAILING"));
        
        if (!grades.isEmpty()) {
            System.out.printf("Highest Grade: %.1f%n", getHighestGrade());
            System.out.printf("Lowest Grade: %.1f%n", getLowestGrade());
        }
    }
}

class GradeManager {
    private ArrayList<Student> students;
    private Scanner scanner;
    
    public GradeManager() {
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    
    public void addStudent() {
        System.out.println("\nAdding new student...");
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        
        // Check if student ID already exists
        if (findStudent(studentId) != null) {
            System.out.println("Error: Student ID already exists!");
            return;
        }
        
        Student student = new Student(name, studentId);
        students.add(student);
        System.out.println("Student " + name + " (ID: " + studentId + ") created successfully.");
    }
    
    public void addGradesToStudent() {
        System.out.print("\nEnter student ID: ");
        String studentId = scanner.nextLine();
        
        Student student = findStudent(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        
        System.out.println("Adding grades for " + student.getName() + ":");
        System.out.print("How many grades do you want to add? ");
        int numGrades = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        for (int i = 1; i <= numGrades; i++) {
            boolean validGrade = false;
            while (!validGrade) {
                System.out.print("Enter grade " + i + " (0-100): ");
                try {
                    double grade = scanner.nextDouble();
                    if (isValidGrade(grade)) {
                        student.addGrade(grade);
                        validGrade = true;
                    } else {
                        System.out.println("Please enter a grade between 0 and 100.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input! Please enter a number.");
                    scanner.nextLine(); // Clear invalid input
                }
            }
        }
        scanner.nextLine(); // Consume newline after last nextDouble()
        
        System.out.println("Grades added successfully!");
    }
    
    private Student findStudent(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }
    
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("\nNo students in the system.");
            return;
        }
        
        System.out.println("\n=== All Students ===");
        for (Student student : students) {
            student.displayInfo();
            System.out.println();
        }
    }
    
    public double calculateClassAverage() {
        if (students.isEmpty()) {
            return 0.0;
        }
        
        double totalAverage = 0.0;
        int studentsWithGrades = 0;
        
        for (Student student : students) {
            if (!student.getGrades().isEmpty()) {
                totalAverage += student.calculateAverage();
                studentsWithGrades++;
            }
        }
        
        return studentsWithGrades > 0 ? totalAverage / studentsWithGrades : 0.0;
    }
    
    public Student findBestStudent() {
        if (students.isEmpty()) {
            return null;
        }
        
        Student bestStudent = null;
        double highestAverage = -1.0;
        
        for (Student student : students) {
            if (!student.getGrades().isEmpty()) {
                double average = student.calculateAverage();
                if (average > highestAverage) {
                    highestAverage = average;
                    bestStudent = student;
                }
            }
        }
        
        return bestStudent;
    }
    
    public void displayClassSummary() {
        System.out.println("\n=== Class Summary ===");
        System.out.println("Total Students: " + students.size());
        
        if (!students.isEmpty()) {
            System.out.printf("Class Average: %.1f%n", calculateClassAverage());
            
            Student bestStudent = findBestStudent();
            if (bestStudent != null) {
                System.out.printf("Best Student: %s (Average: %.1f)%n", 
                    bestStudent.getName(), bestStudent.calculateAverage());
            }
            
            // Additional statistics
            int passingStudents = 0;
            for (Student student : students) {
                if (!student.getGrades().isEmpty() && student.isPassing()) {
                    passingStudents++;
                }
            }
            
            System.out.println("Passing Students: " + passingStudents + "/" + students.size());
        }
    }
    
    private boolean isValidGrade(double grade) {
        return grade >= 0 && grade <= 100;
    }
    
    public void displayMenu() {
        System.out.println("\n=== Student Grade Management System ===");
        System.out.println("1. Add New Student");
        System.out.println("2. Add Grades to Student");
        System.out.println("3. Display All Students");
        System.out.println("4. Display Class Summary");
        System.out.println("5. Find Student by ID");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }
    
    public void findAndDisplayStudent() {
        System.out.print("\nEnter student ID: ");
        String studentId = scanner.nextLine();
        
        Student student = findStudent(studentId);
        if (student != null) {
            student.displayInfo();
        } else {
            System.out.println("Student not found!");
        }
    }
    
    public void run() {
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    addGradesToStudent();
                    break;
                case 3:
                    displayAllStudents();
                    break;
                case 4:
                    displayClassSummary();
                    break;
                case 5:
                    findAndDisplayStudent();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using the Student Grade Management System!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        
        scanner.close();
    }
}

public class StudentGradeSystem {
    public static void main(String[] args) {
        GradeManager manager = new GradeManager();
        manager.run();
    }
}
