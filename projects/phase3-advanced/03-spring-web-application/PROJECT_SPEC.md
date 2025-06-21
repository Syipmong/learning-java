# Spring Web Application

## 🎯 Project Objectives
Master Spring Framework, MVC architecture, and dependency injection by building a modern web application with RESTful APIs and database integration.

## 📋 Requirements

### Core Features
1. **RESTful API**: Complete CRUD operations with REST endpoints
2. **MVC Architecture**: Proper separation of concerns
3. **Database Integration**: JPA/Hibernate with Spring Data
4. **Security**: Authentication and authorization
5. **Validation**: Input validation and error handling
6. **Testing**: Unit and integration tests
7. **Documentation**: API documentation with Swagger

### Technical Requirements
- Use Spring Boot for rapid development
- Implement dependency injection throughout
- Use Spring Data JPA for data persistence
- Implement Spring Security for authentication
- Create RESTful web services
- Use proper HTTP status codes and error handling
- Implement pagination and sorting

## 🏗️ System Architecture

### Spring MVC Architecture
```
Web Layer (Controllers)
├── REST Controllers (@RestController)
├── Web Controllers (@Controller)
├── Exception Handlers (@ControllerAdvice)
└── Request/Response DTOs

Service Layer
├── Business Logic (@Service)
├── Transaction Management (@Transactional)
├── Validation Logic
└── External Service Integration

Data Layer
├── JPA Repositories (@Repository)
├── Entity Classes (@Entity)
├── Database Configuration
└── Data Transfer Objects
```

### Project Structure
```
src/main/java/com/example/webapp/
├── WebAppApplication.java (Main class)
├── controller/ (REST Controllers)
├── service/ (Business logic)
├── repository/ (Data access)
├── entity/ (JPA entities)
├── dto/ (Data transfer objects)
├── config/ (Configuration classes)
├── security/ (Security configuration)
└── exception/ (Exception handling)
```

## 📊 Core Components

### Entity Classes
```java
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    // Constructors, getters, setters
}
```

### Repository Layer
```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    Page<Product> findByPriceRange(@Param("minPrice") BigDecimal minPrice, 
                                  @Param("maxPrice") BigDecimal maxPrice, 
                                  Pageable pageable);
    
    @Modifying
    @Query("UPDATE Product p SET p.price = p.price * :multiplier WHERE p.category.id = :categoryId")
    int updatePricesByCategory(@Param("categoryId") Long categoryId, 
                              @Param("multiplier") BigDecimal multiplier);
}
```

### Service Layer
```java
@Service
@Transactional
public class ProductService {
    
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    
    public ProductService(ProductRepository productRepository, 
                         CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    
    public ProductDTO createProduct(CreateProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(category);
        
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }
}
```

### REST Controller
```java
@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {
    
    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<ProductDTO> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }
    
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @Valid @RequestBody CreateProductRequest request) {
        ProductDTO product = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
}
```

## 🎨 Sample Usage

### Application Configuration
```java
@SpringBootApplication
public class WebAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebAppApplication.class, args);
    }
}
```

### API Testing Examples
```bash
# Get all products with pagination
curl -X GET "http://localhost:8080/api/products?page=0&size=5&sortBy=name"

# Create a new product
curl -X POST "http://localhost:8080/api/products" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop",
    "description": "High-performance laptop",
    "price": 999.99,
    "categoryId": 1
  }'

# Get a specific product
curl -X GET "http://localhost:8080/api/products/1"

# Update a product
curl -X PUT "http://localhost:8080/api/products/1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Laptop",
    "description": "Updated description",
    "price": 1099.99,
    "categoryId": 1
  }'
```

## 🚀 Implementation Steps

### Phase 1: Project Setup
1. Initialize Spring Boot project with dependencies
2. Configure database connection
3. Set up basic project structure
4. Create entity classes and repositories

### Phase 2: Core Functionality
1. Implement service layer with business logic
2. Create REST controllers with CRUD operations
3. Add input validation and error handling
4. Implement pagination and sorting

### Phase 3: Advanced Features
1. Add Spring Security for authentication
2. Implement role-based authorization
3. Create custom exception handling
4. Add API documentation with Swagger

### Phase 4: Testing & Deployment
1. Write unit tests for all layers
2. Create integration tests
3. Add application monitoring
4. Prepare for deployment

## 🏆 Success Criteria

### Functional Requirements ✅
- [ ] All CRUD operations work correctly
- [ ] API endpoints return proper HTTP status codes
- [ ] Input validation prevents invalid data
- [ ] Authentication and authorization work
- [ ] Database transactions are handled properly

### Technical Requirements ✅
- [ ] Proper dependency injection usage
- [ ] Clean separation of concerns (MVC)
- [ ] RESTful API design principles
- [ ] Comprehensive error handling
- [ ] Unit and integration test coverage

## 📈 Extension Ideas

### Advanced Spring Features
1. **Spring Data Specifications**: Dynamic queries
2. **Spring Boot Actuator**: Application monitoring
3. **Spring Profiles**: Environment-specific configuration
4. **Spring Caching**: Performance optimization
5. **Spring Events**: Asynchronous processing

### Web Development
1. **Frontend Integration**: React/Angular SPA
2. **WebSocket Support**: Real-time features
3. **File Upload**: Multipart file handling
4. **Rate Limiting**: API throttling
5. **CORS Configuration**: Cross-origin requests

### Production Features
1. **Docker Integration**: Containerization
2. **Database Migrations**: Flyway/Liquibase
3. **Logging**: Structured logging with Logback
4. **Configuration Management**: External properties
5. **Health Checks**: Custom health indicators

## 🔍 Key Spring Concepts

### Dependency Injection
```java
// Constructor injection (recommended)
@Service
public class ProductService {
    private final ProductRepository repository;
    
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
}

// Field injection (not recommended)
@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
}
```

### Configuration Classes
```java
@Configuration
public class DatabaseConfig {
    
    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.primary")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }
    
    @Bean
    @Primary
    public DataSource primaryDataSource() {
        return primaryDataSourceProperties()
            .initializeDataSourceBuilder()
            .build();
    }
}
```

### Exception Handling
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("ENTITY_NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        
        ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR", "Invalid input", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
```

## 📚 Learning Outcomes

By completing this project, you will understand:
- Spring Framework core concepts and IoC container
- Dependency injection patterns and best practices
- Spring MVC architecture and request handling
- Spring Data JPA and database integration
- REST API design and implementation
- Spring Security for authentication/authorization
- Spring Boot auto-configuration and starters
- Testing strategies for Spring applications

## 🛠️ Technology Stack

### Core Spring Technologies
- **Spring Boot**: Auto-configuration and starter dependencies
- **Spring MVC**: Web framework
- **Spring Data JPA**: Data access layer
- **Spring Security**: Security framework
- **Spring Test**: Testing support

### Additional Dependencies
- **Hibernate**: JPA implementation
- **H2/MySQL/PostgreSQL**: Database options
- **Jackson**: JSON serialization
- **Bean Validation**: Input validation
- **Swagger/OpenAPI**: API documentation

### Development Tools
- **Spring Boot DevTools**: Development productivity
- **Spring Boot Actuator**: Application monitoring
- **TestContainers**: Integration testing
- **MockMvc**: Web layer testing

## ⚠️ Best Practices

### Spring Development
1. **Constructor Injection**: Prefer constructor over field injection
2. **Single Responsibility**: Keep controllers thin
3. **Exception Handling**: Use @ControllerAdvice for global handling
4. **Configuration**: Use @ConfigurationProperties for complex properties

### API Design
1. **RESTful Principles**: Proper HTTP methods and status codes
2. **Versioning**: API versioning strategy
3. **Documentation**: Comprehensive API documentation
4. **Error Responses**: Consistent error response format

### Security
1. **Input Validation**: Validate all input data
2. **HTTPS**: Use HTTPS in production
3. **CORS**: Configure CORS properly
4. **Authentication**: Secure API endpoints

Happy Spring development! 🌱
