# Exercise 1: Contact Manager System

## 🎯 Objective
Build a comprehensive contact management system that demonstrates the effective use of Java Collections Framework, including ArrayList, HashMap, HashSet, and various collection operations.

## 📋 Requirements

### Part A: Contact Class Design
Create a `Contact` class with:
1. Properties: name, phone, email, address, tags
2. Proper encapsulation with getters and setters
3. Override `equals()` and `hashCode()` methods
4. Implement `Comparable` interface for natural ordering
5. Override `toString()` method for display

### Part B: ContactManager Implementation
Create a `ContactManager` class using different collections:
1. **ArrayList** - Store all contacts in insertion order
2. **HashMap** - Quick lookup by phone number
3. **TreeSet** - Maintain contacts in sorted order by name
4. **HashSet** - Store unique email addresses to prevent duplicates

### Part C: Core Operations
Implement the following methods:
1. `addContact(Contact contact)` - Add new contact with validation
2. `removeContact(String phone)` - Remove contact by phone number
3. `updateContact(String phone, Contact newContact)` - Update existing contact
4. `findByPhone(String phone)` - Find contact by phone number
5. `findByName(String name)` - Find contacts by name (partial match)
6. `findByEmail(String email)` - Find contact by email address

### Part D: Advanced Features
1. **Search Operations**:
   - Find contacts by tag
   - Search contacts containing specific keywords
   - Get contacts within a specific city/area

2. **Bulk Operations**:
   - Import contacts from CSV format
   - Export contacts to different formats
   - Merge duplicate contacts

3. **Statistics**:
   - Total number of contacts
   - Most common email domains
   - Contacts grouped by tags

### Part E: Collection Performance Analysis
1. Compare performance of different search methods
2. Analyze memory usage of different collection types
3. Implement caching for frequently accessed contacts

## 🔧 Technical Requirements
- Use appropriate collection types for each operation
- Implement proper error handling and validation
- Use generics for type safety
- Follow object-oriented design principles
- Include comprehensive input validation

## 📊 Expected Output
```
=== Contact Management System ===

1. Add Contact
2. Remove Contact
3. Update Contact
4. Find by Phone
5. Find by Name
6. Find by Email
7. Find by Tag
8. Display All Contacts
9. Display Statistics
10. Import/Export
0. Exit

Choose an option: 1

Enter contact details:
Name: John Doe
Phone: +1-555-123-4567
Email: john.doe@email.com
Address: 123 Main St, New York, NY
Tags (comma-separated): work, client, important

Contact added successfully!

Choose an option: 8

=== All Contacts (Sorted by Name) ===
1. Alice Smith
   Phone: +1-555-987-6543
   Email: alice.smith@email.com
   Address: 456 Oak Ave, Boston, MA
   Tags: [friend, college]

2. John Doe
   Phone: +1-555-123-4567
   Email: john.doe@email.com
   Address: 123 Main St, New York, NY
   Tags: [work, client, important]

Total Contacts: 2
```

## 🧪 Test Cases
Your implementation should handle:
1. **Valid Operations**:
   - Adding contacts with all required fields
   - Searching by various criteria
   - Updating existing contacts

2. **Edge Cases**:
   - Duplicate phone numbers (should prevent)
   - Duplicate email addresses (should warn)
   - Empty or null input values
   - Special characters in names/addresses

3. **Performance Tests**:
   - Adding 1000+ contacts
   - Searching in large datasets
   - Memory usage with different collection sizes

## 💡 Hints
- Use `HashMap<String, Contact>` for O(1) phone number lookups
- Use `TreeSet<Contact>` for automatic sorting by name
- Use `HashSet<String>` to track unique email addresses
- Implement `Comparator` for different sorting options
- Use `Iterator` for safe collection traversal and modification
- Consider using `LinkedHashMap` to preserve insertion order
- Use `Collections.unmodifiableList()` for read-only views

## 🎓 Learning Goals
After completing this exercise, you should understand:
- When to use different collection types (List, Set, Map)
- Performance characteristics of different collections
- Proper implementation of `equals()` and `hashCode()`
- Using `Comparable` and `Comparator` for sorting
- Iterator pattern for safe collection modification
- Generic type parameters and wildcards
- Collection utility methods and operations
