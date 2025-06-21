# Java Quick Reference Guide

A comprehensive quick reference for Java concepts, syntax, and best practices across all learning phases.

## 📚 Table of Contents

- [Basic Syntax](#basic-syntax)
- [Data Types](#data-types)
- [Control Structures](#control-structures)
- [Object-Oriented Programming](#object-oriented-programming)
- [Collections Framework](#collections-framework)
- [Exception Handling](#exception-handling)
- [File I/O](#file-io)
- [Multithreading](#multithreading)
- [Functional Programming](#functional-programming)
- [Spring Framework](#spring-framework)
- [Best Practices](#best-practices)

## Basic Syntax

### Hello World
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

### Variable Declaration
```java
// Primitive types
int age = 25;
double height = 5.9;
boolean isStudent = true;
char grade = 'A';

// Reference types
String name = "John Doe";
int[] numbers = {1, 2, 3, 4, 5};
```

### Constants
```java
public static final double PI = 3.14159;
public static final String APP_NAME = "MyApp";
```

## Data Types

### Primitive Types
| Type | Size | Range | Default |
|------|------|--------|---------|
| byte | 8 bits | -128 to 127 | 0 |
| short | 16 bits | -32,768 to 32,767 | 0 |
| int | 32 bits | -2³¹ to 2³¹-1 | 0 |
| long | 64 bits | -2⁶³ to 2⁶³-1 | 0L |
| float | 32 bits | IEEE 754 | 0.0f |
| double | 64 bits | IEEE 754 | 0.0d |
| char | 16 bits | 0 to 65,535 | '\u0000' |
| boolean | 1 bit | true/false | false |

### Wrapper Classes
```java
Integer intObj = 42;
Double doubleObj = 3.14;
Boolean boolObj = true;
Character charObj = 'A';

// Autoboxing and Unboxing
int primitive = intObj;        // Unboxing
Integer wrapper = primitive;   // Autoboxing
```

## Control Structures

### Conditional Statements
```java
// if-else
if (age >= 18) {
    System.out.println("Adult");
} else if (age >= 13) {
    System.out.println("Teenager");
} else {
    System.out.println("Child");
}

// switch statement
switch (grade) {
    case 'A':
        System.out.println("Excellent");
        break;
    case 'B':
        System.out.println("Good");
        break;
    default:
        System.out.println("Need improvement");
}

// switch expression (Java 14+)
String result = switch (grade) {
    case 'A' -> "Excellent";
    case 'B' -> "Good";
    default -> "Need improvement";
};
```

### Loops
```java
// for loop
for (int i = 0; i < 10; i++) {
    System.out.println(i);
}

// enhanced for loop (for-each)
int[] numbers = {1, 2, 3, 4, 5};
for (int num : numbers) {
    System.out.println(num);
}

// while loop
int i = 0;
while (i < 10) {
    System.out.println(i);
    i++;
}

// do-while loop
int j = 0;
do {
    System.out.println(j);
    j++;
} while (j < 10);
```

## Object-Oriented Programming

### Class Definition
```java
public class Person {
    // Instance variables
    private String name;
    private int age;
    
    // Constructor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Getter methods
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    // Setter methods
    public void setName(String name) {
        this.name = name;
    }
    
    public void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        }
    }
    
    // Method
    public void introduce() {
        System.out.println("Hi, I'm " + name + " and I'm " + age + " years old.");
    }
    
    // Override toString
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
    
    // Override equals and hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return age == person.age && Objects.equals(name, person.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
```

### Inheritance
```java
// Base class
public class Animal {
    protected String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void makeSound() {
        System.out.println("Animal makes a sound");
    }
}

// Derived class
public class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " barks");
    }
    
    public void wagTail() {
        System.out.println(name + " wags tail");
    }
}
```

### Abstract Classes and Interfaces
```java
// Abstract class
public abstract class Shape {
    protected String color;
    
    public Shape(String color) {
        this.color = color;
    }
    
    public abstract double getArea();
    
    public void displayColor() {
        System.out.println("Color: " + color);
    }
}

// Interface
public interface Drawable {
    void draw();
    
    default void print() {
        System.out.println("Printing shape");
    }
    
    static void info() {
        System.out.println("This is a drawable shape");
    }
}

// Implementation
public class Circle extends Shape implements Drawable {
    private double radius;
    
    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }
    
    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public void draw() {
        System.out.println("Drawing a circle");
    }
}
```

## Collections Framework

### List Interface
```java
// ArrayList
List<String> arrayList = new ArrayList<>();
arrayList.add("apple");
arrayList.add("banana");
arrayList.add("cherry");

// LinkedList
List<String> linkedList = new LinkedList<>();
linkedList.add("one");
linkedList.add("two");

// Vector (thread-safe)
List<String> vector = new Vector<>();
```

### Set Interface
```java
// HashSet (no ordering)
Set<String> hashSet = new HashSet<>();
hashSet.add("apple");
hashSet.add("banana");
hashSet.add("apple"); // Duplicate, won't be added

// LinkedHashSet (insertion order)
Set<String> linkedHashSet = new LinkedHashSet<>();

// TreeSet (sorted order)
Set<String> treeSet = new TreeSet<>();
```

### Map Interface
```java
// HashMap
Map<String, Integer> hashMap = new HashMap<>();
hashMap.put("apple", 5);
hashMap.put("banana", 3);
hashMap.put("cherry", 8);

// TreeMap (sorted by keys)
Map<String, Integer> treeMap = new TreeMap<>();

// LinkedHashMap (insertion order)
Map<String, Integer> linkedHashMap = new LinkedHashMap<>();

// Iterate through map
for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}
```

### Queue Interface
```java
// ArrayDeque
Queue<String> queue = new ArrayDeque<>();
queue.offer("first");
queue.offer("second");
String first = queue.poll();

// PriorityQueue
Queue<Integer> priorityQueue = new PriorityQueue<>();
priorityQueue.offer(5);
priorityQueue.offer(2);
priorityQueue.offer(8);
```

## Exception Handling

### Try-Catch-Finally
```java
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Division by zero!");
} catch (Exception e) {
    System.out.println("General exception: " + e.getMessage());
} finally {
    System.out.println("This always executes");
}
```

### Try-with-Resources
```java
try (FileReader file = new FileReader("file.txt");
     BufferedReader reader = new BufferedReader(file)) {
    
    String line = reader.readLine();
    System.out.println(line);
    
} catch (IOException e) {
    e.printStackTrace();
}
```

### Custom Exceptions
```java
public class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
    
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}

// Usage
public void validateAge(int age) throws CustomException {
    if (age < 0) {
        throw new CustomException("Age cannot be negative");
    }
}
```

## File I/O

### Reading Files
```java
// Reading with Scanner
try (Scanner scanner = new Scanner(new File("input.txt"))) {
    while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        System.out.println(line);
    }
}

// Reading with BufferedReader
try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
}

// Reading all lines
List<String> lines = Files.readAllLines(Paths.get("input.txt"));
```

### Writing Files
```java
// Writing with PrintWriter
try (PrintWriter writer = new PrintWriter("output.txt")) {
    writer.println("Hello, World!");
    writer.printf("Number: %d%n", 42);
}

// Writing with Files
String content = "Hello, World!";
Files.write(Paths.get("output.txt"), content.getBytes());

// Appending to file
Files.write(Paths.get("output.txt"), content.getBytes(), StandardOpenOption.APPEND);
```

## Multithreading

### Creating Threads
```java
// Extending Thread class
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread is running");
    }
}

// Implementing Runnable interface
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Runnable is running");
    }
}

// Usage
Thread thread1 = new MyThread();
Thread thread2 = new Thread(new MyRunnable());
thread1.start();
thread2.start();
```

### Synchronization
```java
public class Counter {
    private int count = 0;
    
    public synchronized void increment() {
        count++;
    }
    
    public synchronized int getCount() {
        return count;
    }
}

// Synchronized block
public void method() {
    synchronized (this) {
        // Critical section
    }
}
```

### ExecutorService
```java
ExecutorService executor = Executors.newFixedThreadPool(5);

// Submit tasks
for (int i = 0; i < 10; i++) {
    final int taskId = i;
    executor.submit(() -> {
        System.out.println("Task " + taskId + " executed by " + 
                          Thread.currentThread().getName());
    });
}

executor.shutdown();
```

## Functional Programming

### Lambda Expressions
```java
// Basic lambda
Runnable task = () -> System.out.println("Hello from lambda");

// Lambda with parameters
Comparator<String> comparator = (s1, s2) -> s1.compareTo(s2);

// Lambda with block body
Consumer<String> printer = (s) -> {
    System.out.println("Processing: " + s);
    System.out.println("Length: " + s.length());
};
```

### Stream API
```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

// Filter and collect
List<String> longNames = names.stream()
    .filter(name -> name.length() > 4)
    .collect(Collectors.toList());

// Map and reduce
int totalLength = names.stream()
    .mapToInt(String::length)
    .sum();

// Complex stream operations
Map<Integer, List<String>> namesByLength = names.stream()
    .collect(Collectors.groupingBy(String::length));
```

### Method References
```java
// Static method reference
Function<String, Integer> parseInt = Integer::parseInt;

// Instance method reference
List<String> names = Arrays.asList("alice", "bob", "charlie");
names.stream()
    .map(String::toUpperCase)
    .forEach(System.out::println);

// Constructor reference
Supplier<ArrayList<String>> listFactory = ArrayList::new;
```

## Spring Framework

### Dependency Injection
```java
// Component
@Component
public class UserService {
    private final UserRepository userRepository;
    
    // Constructor injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

// Configuration
@Configuration
public class AppConfig {
    @Bean
    public UserService userService() {
        return new UserService(userRepository());
    }
    
    @Bean
    public UserRepository userRepository() {
        return new DatabaseUserRepository();
    }
}
```

### Spring Boot
```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
```

## Best Practices

### Naming Conventions
```java
// Classes: PascalCase
public class UserManager { }

// Methods and variables: camelCase
public void calculateTotalPrice() { }
private int itemCount;

// Constants: UPPER_SNAKE_CASE
public static final int MAX_RETRY_COUNT = 3;

// Packages: lowercase
package com.example.util;
```

### Code Organization
```java
public class BankAccount {
    // 1. Static variables
    private static final double INTEREST_RATE = 0.05;
    
    // 2. Instance variables
    private String accountNumber;
    private double balance;
    
    // 3. Constructors
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    
    // 4. Public methods
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
    
    // 5. Private methods
    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
    
    // 6. Getters and setters
    public double getBalance() {
        return balance;
    }
}
```

### Error Handling
```java
// Use specific exceptions
public void processFile(String filename) throws FileNotFoundException, IOException {
    if (filename == null || filename.trim().isEmpty()) {
        throw new IllegalArgumentException("Filename cannot be null or empty");
    }
    
    // Process file
}

// Validate inputs
public void setAge(int age) {
    if (age < 0 || age > 150) {
        throw new IllegalArgumentException("Age must be between 0 and 150");
    }
    this.age = age;
}
```

### Resource Management
```java
// Always close resources
try (FileInputStream fis = new FileInputStream("file.txt");
     ObjectInputStream ois = new ObjectInputStream(fis)) {
    
    Object obj = ois.readObject();
    // Process object
    
} catch (IOException | ClassNotFoundException e) {
    logger.error("Error reading file", e);
}
```

This quick reference guide covers the essential Java concepts you'll encounter throughout your learning journey. Keep this handy as you progress through the phases!
