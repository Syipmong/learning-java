# Library System Enhanced

## 🎯 Project Objectives
Master exception handling, generics, and advanced Java features by building an enhanced library management system with robust error handling and type safety.

## 📋 Requirements

### Core Features
1. **Enhanced Book Management**: Generic collections with type safety
2. **Advanced Member System**: Different membership types with benefits
3. **Reservation System**: Book reservations and waiting lists
4. **Fine Management**: Automatic fine calculation and payment tracking
5. **Data Persistence**: Save/load library data to/from files
6. **Robust Error Handling**: Comprehensive exception handling

### Technical Requirements
- Use generics for type-safe collections
- Implement custom exceptions for library-specific errors
- Handle file I/O operations with proper exception handling
- Use try-with-resources for resource management
- Implement logging for operations and errors
- Create utility classes with static methods

## 🏗️ System Architecture

### Enhanced Class Hierarchy
```
LibrarySystem
├── ItemManager<T extends LibraryItem>
├── MemberManager<T extends Member>
├── ReservationManager
├── FineManager
├── DataPersistenceManager
└── LibraryLogger
```

### Custom Exceptions
- `LibraryException` (base exception)
- `ItemNotFoundException`
- `MemberNotFoundException`
- `ItemUnavailableException`
- `InvalidMembershipException`
- `DataPersistenceException`

### Generic Collections
- `Repository<T>`: Generic repository pattern
- `SearchableCollection<T>`: Generic searchable collection
- `SortedLibraryCollection<T>`: Generic sorted collection

## 📊 Class Specifications

### Generic Repository Pattern
```java
public interface Repository<T> {
    void add(T item) throws LibraryException;
    void remove(String id) throws LibraryException;
    T findById(String id) throws ItemNotFoundException;
    List<T> findAll();
    List<T> search(Predicate<T> criteria);
}
```

### Enhanced Member Types
```java
public enum MembershipType {
    BASIC(3, 14, 0.50, 5.00),
    PREMIUM(5, 21, 0.25, 10.00),
    VIP(10, 30, 0.10, 25.00);
    
    private final int maxBooks;
    private final int borrowPeriod;
    private final double fineRate;
    private final double fineLimit;
}
```

## 🧪 Exception Handling Scenarios

### File I/O Operations
1. **File Not Found**: Handle missing data files gracefully
2. **Corrupted Data**: Validate and recover from corrupted files
3. **Permission Errors**: Handle file access permissions
4. **Disk Space**: Handle insufficient disk space

### Business Logic Exceptions
1. **Invalid Operations**: Prevent borrowing unavailable books
2. **Membership Violations**: Enforce borrowing limits
3. **Data Integrity**: Ensure consistent system state
4. **Concurrent Access**: Handle multi-user scenarios

## 🎨 Sample Usage

```java
public class LibrarySystemDemo {
    public static void main(String[] args) {
        try {
            LibrarySystem library = new LibrarySystem();
            
            // Type-safe operations with generics
            ItemManager<Book> bookManager = library.getBookManager();
            MemberManager<PremiumMember> memberManager = library.getMemberManager();
            
            // Exception handling
            Book book = bookManager.findById("B001");
            library.borrowBook(book.getId(), "M001");
            
        } catch (LibraryException e) {
            System.err.println("Library error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}
```

## 🚀 Implementation Steps

### Phase 1: Exception Framework
1. Create custom exception hierarchy
2. Implement error codes and messages
3. Add exception handling to existing methods
4. Create error logging system

### Phase 2: Generic Collections
1. Implement generic repository pattern
2. Create type-safe collection classes
3. Add generic search and filter capabilities
4. Implement comparable and comparator interfaces

### Phase 3: Advanced Features
1. Add reservation system with waiting lists
2. Implement automatic fine calculation
3. Create membership tier system
4. Add data validation and sanitization

### Phase 4: Data Persistence
1. Implement file-based data storage
2. Add JSON/XML serialization
3. Create backup and restore functionality
4. Handle concurrent file access

## 🏆 Success Criteria

### Functional Requirements ✅
- [ ] All operations handle exceptions gracefully
- [ ] Generic collections work with type safety
- [ ] Reservation system manages waiting lists
- [ ] Fine system calculates and tracks payments
- [ ] Data persists between application runs

### Technical Requirements ✅
- [ ] Custom exceptions with meaningful messages
- [ ] Proper use of generics throughout
- [ ] Try-with-resources for file operations
- [ ] Logging for all major operations
- [ ] Thread-safe operations where needed

## 📈 Extension Ideas

### Advanced Error Handling
1. **Error Recovery**: Automatic recovery from common errors
2. **Circuit Breaker**: Prevent cascading failures
3. **Retry Logic**: Automatic retry for transient failures
4. **Error Metrics**: Track and analyze error patterns

### Advanced Generics
1. **Bounded Type Parameters**: Constraint generic types
2. **Wildcard Types**: Flexible method parameters
3. **Generic Methods**: Method-level generics
4. **Type Erasure**: Understanding runtime behavior

### Enterprise Features
1. **Audit Trail**: Track all system changes
2. **Configuration Management**: External configuration
3. **Performance Monitoring**: System performance metrics
4. **Security**: User authentication and authorization

## 🔍 Learning Outcomes

By completing this project, you will understand:
- Custom exception design and implementation
- Generic programming concepts and best practices
- File I/O operations with proper error handling
- Resource management with try-with-resources
- Collection framework and type safety
- Logging and debugging techniques
- Design patterns (Repository, Strategy, Observer)

## 📚 Key Java Concepts

### Exception Handling
- Checked vs unchecked exceptions
- Exception hierarchy design
- Exception propagation
- Resource management

### Generics
- Type parameters and bounds
- Generic methods and constructors
- Wildcards and type erasure
- Generic collections

### File I/O
- NIO.2 file operations
- Serialization and deserialization
- File watching and monitoring
- Stream processing

Happy coding with enhanced error handling! 🚀
