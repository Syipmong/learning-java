# Project 1: Contact Management System

## 🎯 Project Overview
Build a sophisticated contact management system that demonstrates mastery of Java Collections Framework, exception handling, file I/O operations, and generic programming concepts.

## 📋 System Requirements

### Core Functionality
1. **Contact Management**
   - Create, read, update, delete (CRUD) operations
   - Contact validation and duplicate prevention
   - Bulk import/export capabilities
   - Advanced search and filtering

2. **Data Organization**
   - Multiple storage strategies using different Collections
   - Efficient lookup and retrieval mechanisms
   - Sorted views and custom ordering
   - Category-based organization with tags

3. **Persistence Layer**
   - File-based storage with multiple formats (JSON, CSV, XML)
   - Automatic backup and recovery
   - Data integrity validation
   - Migration between format versions

4. **Advanced Features**
   - Contact merging and duplicate detection
   - Relationship mapping (family, work, friends)
   - Contact history and interaction tracking
   - Statistics and analytics dashboard

## 🏗️ System Architecture

### Class Structure
```
ContactManagementSystem/
├── model/
│   ├── Contact.java                 // Contact entity
│   ├── ContactGroup.java           // Contact categorization
│   ├── Address.java                // Address components
│   └── ContactHistory.java         // Interaction tracking
├── repository/
│   ├── ContactRepository.java      // Data access interface
│   ├── FileContactRepository.java  // File-based implementation
│   └── MemoryContactRepository.java // In-memory implementation
├── service/
│   ├── ContactService.java         // Business logic
│   ├── SearchService.java          // Search operations
│   ├── ImportExportService.java    // Data transfer
│   └── ValidationService.java     // Data validation
├── util/
│   ├── FileManager.java           // File operations
│   ├── DataValidator.java         // Input validation
│   └── CollectionUtils.java      // Collection utilities
├── exception/
│   ├── ContactException.java      // Custom exception hierarchy
│   ├── DuplicateContactException.java
│   └── InvalidContactDataException.java
└── ContactManagementApp.java      // Main application
```

## 📊 Collections Strategy

### Storage Optimization
```java
public class ContactRepository {
    // Primary storage - insertion order preservation
    private List<Contact> contacts = new ArrayList<>();
    
    // Fast lookup by phone - O(1) retrieval
    private Map<String, Contact> phoneIndex = new HashMap<>();
    
    // Fast lookup by email - duplicate prevention
    private Map<String, Contact> emailIndex = new HashMap<>();
    
    // Sorted view by name - O(log n) insertion
    private NavigableSet<Contact> sortedContacts = new TreeSet<>();
    
    // Category-based grouping
    private Map<String, Set<Contact>> categoryIndex = new HashMap<>();
    
    // Recent contacts - LRU cache behavior
    private LinkedHashSet<Contact> recentContacts = new LinkedHashSet<>();
}
```

### Advanced Search Implementation
```java
public class SearchService {
    public List<Contact> searchContacts(SearchCriteria criteria) {
        return contacts.stream()
            .filter(contact -> matchesCriteria(contact, criteria))
            .sorted(getComparator(criteria.getSortBy()))
            .limit(criteria.getLimit())
            .collect(Collectors.toList());
    }
    
    public Map<String, List<Contact>> groupByCategory() {
        return contacts.stream()
            .collect(Collectors.groupingBy(Contact::getCategory));
    }
    
    public List<Contact> findSimilarContacts(Contact contact) {
        return contacts.stream()
            .filter(c -> calculateSimilarity(c, contact) > 0.8)
            .collect(Collectors.toList());
    }
}
```

## 💾 Data Persistence Architecture

### Multi-Format Support
```java
public interface DataFormatter<T> {
    void write(List<T> data, OutputStream output) throws IOException;
    List<T> read(InputStream input) throws IOException;
}

public class JsonContactFormatter implements DataFormatter<Contact> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void write(List<Contact> contacts, OutputStream output) throws IOException {
        objectMapper.writeValue(output, contacts);
    }
    
    @Override
    public List<Contact> read(InputStream input) throws IOException {
        return objectMapper.readValue(input, 
            new TypeReference<List<Contact>>() {});
    }
}
```

### Backup Strategy
```java
public class BackupManager {
    private final Path backupDirectory;
    private final int maxBackups = 10;
    
    public void createBackup(List<Contact> contacts) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Path backupFile = backupDirectory.resolve("contacts_backup_" + timestamp + ".json");
        
        try (OutputStream output = Files.newOutputStream(backupFile)) {
            new JsonContactFormatter().write(contacts, output);
        }
        
        cleanupOldBackups();
    }
    
    public List<Contact> restoreFromBackup(String backupName) throws IOException {
        Path backupFile = backupDirectory.resolve(backupName);
        try (InputStream input = Files.newInputStream(backupFile)) {
            return new JsonContactFormatter().read(input);
        }
    }
}
```

## 🧪 Exception Handling Strategy

### Custom Exception Hierarchy
```java
public class ContactException extends Exception {
    public ContactException(String message) {
        super(message);
    }
    
    public ContactException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class DuplicateContactException extends ContactException {
    private final Contact existingContact;
    
    public DuplicateContactException(Contact existing, Contact duplicate) {
        super("Contact already exists: " + existing.getName());
        this.existingContact = existing;
    }
    
    public Contact getExistingContact() {
        return existingContact;
    }
}
```

### Error Recovery Mechanisms
```java
public class ContactService {
    public void saveContact(Contact contact) throws ContactException {
        try {
            validateContact(contact);
            
            if (repository.existsByEmail(contact.getEmail())) {
                throw new DuplicateContactException(
                    repository.findByEmail(contact.getEmail()), contact);
            }
            
            repository.save(contact);
            
        } catch (DuplicateContactException e) {
            // Offer merge option
            handleDuplicateContact(e.getExistingContact(), contact);
        } catch (IOException e) {
            // Attempt recovery
            throw new ContactException("Failed to save contact", e);
        }
    }
}
```

## 📈 Performance Optimization

### Caching Strategy
```java
public class CachedContactService {
    private final Map<String, Contact> cache = new LRU<>(1000);
    private final ContactRepository repository;
    
    public Contact findByPhone(String phone) {
        return cache.computeIfAbsent(phone, 
            p -> repository.findByPhone(p).orElse(null));
    }
    
    public void invalidateCache(String phone) {
        cache.remove(phone);
    }
}
```

### Batch Operations
```java
public class BatchContactProcessor {
    public BatchResult importContacts(List<Contact> contacts) {
        List<Contact> successful = new ArrayList<>();
        List<ContactError> errors = new ArrayList<>();
        
        contacts.parallelStream().forEach(contact -> {
            try {
                contactService.saveContact(contact);
                synchronized (successful) {
                    successful.add(contact);
                }
            } catch (ContactException e) {
                synchronized (errors) {
                    errors.add(new ContactError(contact, e.getMessage()));
                }
            }
        });
        
        return new BatchResult(successful, errors);
    }
}
```

## 🎯 Advanced Features Implementation

### Fuzzy Search Capability
```java
public class FuzzySearchService {
    public List<Contact> fuzzySearch(String query, double threshold) {
        return contacts.stream()
            .map(contact -> new ScoredContact(contact, calculateScore(contact, query)))
            .filter(scored -> scored.getScore() >= threshold)
            .sorted(Comparator.comparing(ScoredContact::getScore).reversed())
            .map(ScoredContact::getContact)
            .collect(Collectors.toList());
    }
    
    private double calculateScore(Contact contact, String query) {
        // Implement Levenshtein distance or similar algorithm
        return FuzzyMatchingAlgorithm.similarity(contact.getName(), query);
    }
}
```

### Contact Analytics
```java
public class ContactAnalytics {
    public ContactStatistics generateStatistics(List<Contact> contacts) {
        Map<String, Long> byCategory = contacts.stream()
            .collect(Collectors.groupingBy(Contact::getCategory, Collectors.counting()));
            
        Map<String, Long> byDomain = contacts.stream()
            .map(Contact::getEmail)
            .filter(Objects::nonNull)
            .map(email -> email.substring(email.indexOf('@') + 1))
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            
        return new ContactStatistics(
            contacts.size(),
            byCategory,
            byDomain,
            calculateGrowthTrend(contacts)
        );
    }
}
```

## 🔧 User Interface Design

### Console-Based Interface
```
=== Contact Management System ===

Main Menu:
1. Contact Operations
   1.1 Add New Contact          1.2 Search Contacts
   1.3 Edit Contact            1.4 Delete Contact
   1.5 Merge Contacts          1.6 View All Contacts

2. Import/Export
   2.1 Import from CSV         2.2 Import from JSON
   2.3 Export to CSV           2.4 Export to JSON
   2.5 Bulk Import             2.6 Create Backup

3. Advanced Features
   3.1 Fuzzy Search           3.2 Duplicate Detection
   3.3 Contact Analytics      3.4 Category Management
   3.5 Relationship Mapping   3.6 History Tracking

4. System Operations
   4.1 View Statistics        4.2 System Settings
   4.3 Data Validation        4.4 Restore Backup

0. Exit

Total Contacts: 1,247 | Categories: 8 | Last Backup: 2 hours ago
```

## 📊 Testing Strategy

### Unit Testing
```java
@ExtendWith(MockitoExtension.class)
class ContactServiceTest {
    @Mock
    private ContactRepository repository;
    
    @InjectMocks
    private ContactService contactService;
    
    @Test
    void shouldSaveValidContact() throws ContactException {
        // Given
        Contact contact = createValidContact();
        when(repository.existsByEmail(contact.getEmail())).thenReturn(false);
        
        // When
        contactService.saveContact(contact);
        
        // Then
        verify(repository).save(contact);
    }
    
    @Test
    void shouldThrowExceptionForDuplicateEmail() {
        // Given
        Contact existing = createValidContact();
        Contact duplicate = createContactWithSameEmail(existing);
        when(repository.existsByEmail(duplicate.getEmail())).thenReturn(true);
        when(repository.findByEmail(duplicate.getEmail())).thenReturn(existing);
        
        // When & Then
        assertThrows(DuplicateContactException.class, 
            () -> contactService.saveContact(duplicate));
    }
}
```

### Integration Testing
```java
@TestMethodOrder(OrderAnnotation.class)
class ContactManagementIntegrationTest {
    private ContactManagementSystem system;
    private Path testDataDir;
    
    @Test
    @Order(1)
    void shouldCreateAndSaveContact() throws Exception {
        Contact contact = new Contact("John Doe", "john@example.com", "+1234567890");
        system.addContact(contact);
        
        assertTrue(system.findByEmail("john@example.com").isPresent());
    }
    
    @Test
    @Order(2)
    void shouldPersistDataBetweenSessions() throws Exception {
        system.shutdown();
        system = new ContactManagementSystem(testDataDir);
        
        assertTrue(system.findByEmail("john@example.com").isPresent());
    }
}
```

## 📚 Learning Outcomes

### Collections Mastery
- **ArrayList vs LinkedList**: Performance characteristics understanding
- **HashMap vs TreeMap**: Trade-offs between speed and ordering
- **HashSet vs TreeSet**: When to use each collection type
- **LinkedHashMap**: Maintaining insertion order while providing fast access

### Exception Handling Excellence
- **Custom exception hierarchies**: Creating meaningful exception types
- **Recovery strategies**: Graceful error handling and user feedback
- **Resource management**: Proper cleanup with try-with-resources

### File I/O Proficiency
- **Multiple formats**: JSON, CSV, XML processing
- **Stream processing**: Efficient handling of large datasets
- **NIO.2 usage**: Modern file operations with Path API

### Generic Programming
- **Type safety**: Eliminating ClassCastException risks
- **Bounded wildcards**: Understanding ? extends and ? super
- **Generic methods**: Creating reusable, type-safe utilities

## 🎯 Success Metrics
- **Functionality**: All CRUD operations working correctly
- **Performance**: Sub-second response for searches under 10K contacts
- **Reliability**: Zero data loss with proper exception handling
- **Maintainability**: Clean code with comprehensive documentation
- **Testability**: >90% code coverage with meaningful tests

This comprehensive project will establish your expertise in intermediate Java concepts and prepare you for advanced enterprise development challenges.
