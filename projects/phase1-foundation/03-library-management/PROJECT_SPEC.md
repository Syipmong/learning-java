# Library Management System

## 🎯 Project Objectives
Learn and implement abstract classes, interfaces, and inheritance concepts through a practical library management system.

## 📋 Requirements

### Core Features
1. **Book Management**: Add, remove, search, and display books
2. **Member Management**: Register members and track their borrowing history
3. **Borrowing System**: Implement book checkout and return functionality
4. **Fine Calculation**: Calculate late return fees

### Technical Requirements
- Use abstract classes for common book properties
- Implement interfaces for borrowable items
- Apply inheritance for different types of books
- Handle edge cases (book not available, member not found, etc.)

## 🏗️ System Architecture

### Class Hierarchy
```
Item (Abstract Class)
├── Book (Abstract Class)
│   ├── Fiction
│   ├── NonFiction
│   └── Reference
├── Magazine
└── DVD
```

### Interfaces
- `Borrowable`: Items that can be borrowed
- `Searchable`: Items that can be searched
- `Renewable`: Items that can have their due date extended

## 📊 Class Specifications

### Abstract Classes

#### `Item`
```java
public abstract class Item {
    protected String id;
    protected String title;
    protected String author;
    protected boolean isAvailable;
    
    // Abstract methods
    public abstract void displayInfo();
    public abstract double calculateFine(int daysOverdue);
}
```

#### `Book`
```java
public abstract class Book extends Item implements Borrowable {
    protected String isbn;
    protected String genre;
    protected int pages;
    
    // Common book functionality
}
```

### Concrete Classes

#### `Fiction`, `NonFiction`, `Reference`
Extend `Book` with specific behaviors:
- Fiction: Standard borrowing period (14 days)
- NonFiction: Extended borrowing period (21 days)  
- Reference: Not borrowable (library use only)

#### `Library`
Main class managing all operations:
- Collection of items
- Member management
- Borrowing/returning logic
- Search functionality

#### `Member`
User class with:
- Personal information
- Borrowing history
- Current borrowed items
- Fine calculation

## 🧪 Test Cases

### Unit Tests
1. **Item Creation**: Verify proper initialization
2. **Borrowing Logic**: Test checkout/return operations
3. **Search Functionality**: Test by title, author, genre
4. **Fine Calculation**: Test overdue scenarios
5. **Edge Cases**: Unavailable books, invalid members

### Integration Tests
1. **End-to-End Workflow**: Complete borrowing cycle
2. **Multiple Members**: Concurrent borrowing scenarios
3. **Mixed Item Types**: Books, magazines, DVDs

## 🎨 Sample Usage

```java
public class LibraryDemo {
    public static void main(String[] args) {
        Library library = new Library();
        
        // Add books
        library.addItem(new Fiction("F001", "1984", "George Orwell", "978-0451524935"));
        library.addItem(new NonFiction("NF001", "Sapiens", "Yuval Harari", "978-0062316097"));
        
        // Register member
        Member member = new Member("M001", "John Doe", "john@example.com");
        library.registerMember(member);
        
        // Borrow book
        library.borrowItem("F001", "M001");
        
        // Display member's borrowed items
        member.displayBorrowedItems();
    }
}
```

## 🚀 Implementation Steps

### Phase 1: Core Structure
1. Create abstract `Item` class
2. Create `Book` abstract class extending `Item`
3. Implement `Borrowable` interface
4. Create concrete book classes

### Phase 2: Library Management
1. Implement `Library` class
2. Add item management methods
3. Create member registration system
4. Implement basic search functionality

### Phase 3: Borrowing System
1. Implement checkout logic
2. Add return functionality
3. Track borrowing history
4. Handle availability checking

### Phase 4: Advanced Features
1. Fine calculation system
2. Overdue notification system
3. Advanced search filters
4. Reporting and statistics

## 🏆 Success Criteria

### Functional Requirements ✅
- [ ] All book types can be created and managed
- [ ] Members can borrow and return items
- [ ] Search functionality works across all criteria
- [ ] Fine calculation is accurate
- [ ] Error handling for edge cases

### Technical Requirements ✅
- [ ] Proper use of abstract classes
- [ ] Interface implementation
- [ ] Inheritance hierarchy
- [ ] Polymorphism demonstration
- [ ] Clean code practices

## 📈 Extension Ideas

### Advanced Features
1. **Database Integration**: Store data persistently
2. **GUI Interface**: JavaFX or Swing interface
3. **Reservation System**: Hold books for members
4. **Multi-branch Support**: Manage multiple library locations
5. **Digital Resources**: E-books and audiobooks
6. **Recommendation Engine**: Suggest books based on history

### Technical Enhancements
1. **Design Patterns**: Observer for notifications, Strategy for fine calculation
2. **Concurrency**: Thread-safe operations for multi-user access
3. **Serialization**: Save/load library state
4. **API Integration**: External book information services
5. **Reporting**: Generate usage statistics and reports

## 🔍 Learning Outcomes

By completing this project, you will understand:
- Abstract classes vs concrete classes
- Interface implementation and multiple inheritance
- Polymorphism in practice
- Method overriding and inheritance
- Object-oriented design principles
- Error handling and validation
- Collections and data management

Happy coding! 📚
