# Project 1: E-commerce Microservices Platform

## 🎯 Project Overview
Build a production-ready, cloud-native e-commerce platform using microservices architecture, Spring Boot ecosystem, and modern DevOps practices. This capstone project demonstrates enterprise-level development skills.

## 📋 System Requirements

### Business Requirements
1. **Customer Management**
   - User registration, authentication, and profile management
   - Customer preferences and shopping behavior tracking
   - Loyalty program and rewards system
   - Customer service and support ticket system

2. **Product Catalog**
   - Product information management with rich media
   - Category hierarchy and advanced filtering
   - Inventory tracking with real-time updates
   - Price management with dynamic pricing rules

3. **Order Processing**
   - Shopping cart with persistent sessions
   - Order lifecycle management (placed, confirmed, shipped, delivered)
   - Payment processing with multiple payment methods
   - Order history and tracking capabilities

4. **Analytics & Reporting**
   - Real-time sales analytics and dashboards
   - Customer behavior analysis
   - Inventory reports and forecasting
   - Performance metrics and KPIs

### Technical Requirements
- **Scalability**: Handle 10,000+ concurrent users
- **Availability**: 99.99% uptime with disaster recovery
- **Performance**: Sub-200ms API response times
- **Security**: GDPR compliance and PCI DSS standards
- **Observability**: Comprehensive monitoring and alerting

## 🏗️ Microservices Architecture

### Service Decomposition
```
E-commerce Platform/
├── user-service/              // User management and authentication
├── product-service/           // Product catalog and inventory
├── order-service/             // Order processing and management
├── payment-service/           // Payment processing and billing
├── notification-service/      // Email, SMS, and push notifications
├── analytics-service/         // Data analytics and reporting
├── recommendation-service/    // AI-powered product recommendations
├── inventory-service/         // Stock management and tracking
├── shipping-service/          // Logistics and delivery management
├── review-service/           // Product reviews and ratings
├── api-gateway/              // Entry point and request routing
├── config-server/            // Centralized configuration management
├── discovery-server/         // Service discovery and registration
└── monitoring-stack/         // Observability and health monitoring
```

### Service Communication Patterns
```java
// Synchronous Communication (REST)
@FeignClient(name = "product-service")
public interface ProductServiceClient {
    @GetMapping("/api/products/{id}")
    ProductDto getProduct(@PathVariable("id") Long productId);
    
    @PostMapping("/api/products/{id}/reserve")
    ReservationDto reserveProduct(@PathVariable("id") Long productId, 
                                 @RequestBody ReservationRequest request);
}

// Asynchronous Communication (Events)
@EventListener
@Async
public void handleOrderPlaced(OrderPlacedEvent event) {
    // Update inventory
    inventoryService.reduceStock(event.getOrderItems());
    
    // Send confirmation email
    notificationService.sendOrderConfirmation(event.getCustomerId(), event.getOrderId());
    
    // Update analytics
    analyticsService.recordSale(event);
}

// Saga Pattern for Distributed Transactions
@Component
public class OrderSaga {
    @SagaOrchestrationStart
    public void processOrder(OrderCreatedEvent event) {
        sagaManager.choreography()
            .step("reserve-inventory")
                .compensate("release-inventory")
            .step("process-payment")
                .compensate("refund-payment")
            .step("confirm-order")
                .compensate("cancel-order")
            .execute(event);
    }
}
```

## 🛡️ Security Architecture

### OAuth2 + JWT Implementation
```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/orders/**").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/orders/**").hasRole("USER")
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            .build();
    }
    
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }
}

// API Gateway Security
@Component
public class AuthenticationFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = extractToken(exchange.getRequest());
        
        return validateToken(token)
            .flatMap(claims -> {
                exchange.getRequest().mutate()
                    .header("X-User-Id", claims.getSubject())
                    .header("X-User-Roles", String.join(",", claims.getRoles()))
                    .build();
                return chain.filter(exchange);
            })
            .onErrorResume(error -> handleAuthError(exchange, error));
    }
}
```

### Data Encryption and Compliance
```java
@Service
public class PersonalDataService {
    private final AESEncryption encryption;
    private final AuditLogger auditLogger;
    
    @GDPRCompliant
    public CustomerProfile getCustomerProfile(String customerId, String requesterId) {
        // Log access for GDPR compliance
        auditLogger.logDataAccess(customerId, requesterId, "CUSTOMER_PROFILE_ACCESS");
        
        CustomerProfile encrypted = customerRepository.findById(customerId);
        return decryptSensitiveData(encrypted);
    }
    
    @DataRetention(period = "7_YEARS")
    public void deleteCustomerData(String customerId) {
        // Anonymize instead of delete for compliance
        customerRepository.anonymizeCustomer(customerId);
        auditLogger.logDataDeletion(customerId, "GDPR_RIGHT_TO_BE_FORGOTTEN");
    }
    
    private CustomerProfile decryptSensitiveData(CustomerProfile profile) {
        profile.setEmail(encryption.decrypt(profile.getEncryptedEmail()));
        profile.setPhoneNumber(encryption.decrypt(profile.getEncryptedPhone()));
        profile.setAddress(encryption.decrypt(profile.getEncryptedAddress()));
        return profile;
    }
}
```

## 🔄 Event-Driven Architecture

### Event Sourcing Implementation
```java
@Entity
@Table(name = "event_store")
public class EventStore {
    @Id
    private String eventId;
    private String aggregateId;
    private String eventType;
    private String eventData;
    private LocalDateTime timestamp;
    private Integer version;
    
    // Event sourcing for order aggregate
    @EventSourcingHandler
    public void handle(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.customerId = event.getCustomerId();
        this.status = OrderStatus.CREATED;
        this.totalAmount = event.getTotalAmount();
        this.createdAt = event.getTimestamp();
    }
    
    @EventSourcingHandler
    public void handle(OrderConfirmedEvent event) {
        this.status = OrderStatus.CONFIRMED;
        this.confirmedAt = event.getTimestamp();
    }
    
    @EventSourcingHandler
    public void handle(OrderShippedEvent event) {
        this.status = OrderStatus.SHIPPED;
        this.trackingNumber = event.getTrackingNumber();
        this.shippedAt = event.getTimestamp();
    }
}

@Component
public class OrderProjection {
    @EventHandler
    public void on(OrderCreatedEvent event) {
        OrderView orderView = new OrderView(
            event.getOrderId(),
            event.getCustomerId(),
            event.getTotalAmount(),
            OrderStatus.CREATED
        );
        orderViewRepository.save(orderView);
    }
    
    @EventHandler
    public void on(OrderConfirmedEvent event) {
        orderViewRepository.updateStatus(event.getOrderId(), OrderStatus.CONFIRMED);
    }
}
```

### CQRS Pattern Implementation
```java
// Command Side
@RestController
@RequestMapping("/api/orders")
public class OrderCommandController {
    private final CommandGateway commandGateway;
    
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderCommand command) {
        String orderId = commandGateway.sendAndWait(command);
        return ResponseEntity.accepted().body(orderId);
    }
    
    @PutMapping("/{orderId}/confirm")
    public ResponseEntity<Void> confirmOrder(@PathVariable String orderId) {
        commandGateway.send(new ConfirmOrderCommand(orderId));
        return ResponseEntity.ok().build();
    }
}

// Query Side
@RestController
@RequestMapping("/api/orders")
public class OrderQueryController {
    private final OrderQueryService queryService;
    
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderView> getOrder(@PathVariable String orderId) {
        OrderView order = queryService.findOrderById(orderId);
        return ResponseEntity.ok(order);
    }
    
    @GetMapping
    public ResponseEntity<PagedResult<OrderSummary>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String customerId) {
        
        PagedResult<OrderSummary> orders = queryService.findOrders(customerId, page, size);
        return ResponseEntity.ok(orders);
    }
}
```

## 📊 Observability and Monitoring

### Distributed Tracing
```java
@RestController
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final MeterRegistry meterRegistry;
    
    @GetMapping("/api/products/{id}")
    @Timed(name = "product.get", description = "Time taken to get product")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        Span span = tracer.nextSpan().name("get-product").start();
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(span)) {
            span.tag("product.id", String.valueOf(id));
            
            ProductDto product = productService.getProductById(id);
            
            // Custom metrics
            meterRegistry.counter("product.views", "category", product.getCategory())
                .increment();
            
            span.tag("product.category", product.getCategory());
            return ResponseEntity.ok(product);
            
        } catch (ProductNotFoundException e) {
            span.tag("error", true);
            span.tag("error.message", e.getMessage());
            throw e;
        } finally {
            span.end();
        }
    }
}

// Custom Health Indicators
@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    private final DataSource dataSource;
    
    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(1)) {
                return Health.up()
                    .withDetail("database", "Available")
                    .withDetail("connection.pool.active", getActiveConnections())
                    .build();
            }
        } catch (SQLException e) {
            return Health.down()
                .withDetail("database", "Unavailable")
                .withDetail("error", e.getMessage())
                .build();
        }
        return Health.down().withDetail("database", "Connection validation failed").build();
    }
}
```

### Performance Monitoring
```java
@Configuration
public class MetricsConfiguration {
    
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
    
    @Bean
    public CountedAspect countedAspect(MeterRegistry registry) {
        return new CountedAspect(registry);
    }
    
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        Metrics.counter("orders.created", 
                "customer.type", event.getCustomerType(),
                "payment.method", event.getPaymentMethod())
            .increment();
            
        Metrics.gauge("order.value", event.getTotalAmount());
    }
    
    @Scheduled(fixedRate = 30000)
    public void recordBusinessMetrics() {
        // Revenue metrics
        BigDecimal todayRevenue = orderService.getTodayRevenue();
        Metrics.gauge("business.revenue.today", todayRevenue.doubleValue());
        
        // Inventory metrics
        long lowStockItems = inventoryService.getLowStockCount();
        Metrics.gauge("inventory.low_stock.count", lowStockItems);
        
        // User activity metrics
        long activeUsers = userService.getActiveUsersCount();
        Metrics.gauge("users.active.count", activeUsers);
    }
}
```

## 🚀 Cloud-Native Deployment

### Kubernetes Deployment
```yaml
# product-service-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: product-service
  labels:
    app: product-service
    version: v1
spec:
  replicas: 3
  selector:
    matchLabels:
      app: product-service
  template:
    metadata:
      labels:
        app: product-service
        version: v1
    spec:
      containers:
      - name: product-service
        image: ecommerce/product-service:latest
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "kubernetes"
        - name: DB_HOST
          valueFrom:
            secretKeyRef:
              name: db-secret
              key: host
        resources:
          requests:
            memory: "512Mi"
            cpu: "250m"
          limits:
            memory: "1Gi"
            cpu: "500m"
        readinessProbe:
          httpGet:
            path: /actuator/health/readiness
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 10
        livenessProbe:
          httpGet:
            path: /actuator/health/liveness
            port: 8080
          initialDelaySeconds: 60
          periodSeconds: 30
---
apiVersion: v1
kind: Service
metadata:
  name: product-service
spec:
  selector:
    app: product-service
  ports:
  - port: 80
    targetPort: 8080
  type: ClusterIP
```

### Istio Service Mesh Configuration
```yaml
# Virtual Service for A/B Testing
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: product-service-vs
spec:
  hosts:
  - product-service
  http:
  - match:
    - headers:
        x-user-group:
          exact: beta
    route:
    - destination:
        host: product-service
        subset: v2
      weight: 100
  - route:
    - destination:
        host: product-service
        subset: v1
      weight: 90
    - destination:
        host: product-service
        subset: v2
      weight: 10
---
# Destination Rule
apiVersion: networking.istio.io/v1alpha3
kind: DestinationRule
metadata:
  name: product-service-dr
spec:
  host: product-service
  trafficPolicy:
    circuitBreaker:
      consecutiveErrors: 3
      interval: 30s
      baseEjectionTime: 30s
      maxEjectionPercent: 50
  subsets:
  - name: v1
    labels:
      version: v1
  - name: v2
    labels:
      version: v2
```

## 🧪 Comprehensive Testing Strategy

### Integration Testing with TestContainers
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class OrderServiceIntegrationTest {
    
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");
    
    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Test
    void shouldCreateOrderSuccessfully() {
        // Given
        CreateOrderRequest request = CreateOrderRequest.builder()
            .customerId("customer-123")
            .items(List.of(
                OrderItem.builder().productId("product-1").quantity(2).price(new BigDecimal("29.99")).build(),
                OrderItem.builder().productId("product-2").quantity(1).price(new BigDecimal("49.99")).build()
            ))
            .build();
        
        // When
        ResponseEntity<OrderResponse> response = restTemplate.postForEntity(
            "/api/orders", request, OrderResponse.class);
        
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getOrderId()).isNotNull();
        
        // Verify database state
        Optional<Order> savedOrder = orderRepository.findById(response.getBody().getOrderId());
        assertThat(savedOrder).isPresent();
        assertThat(savedOrder.get().getStatus()).isEqualTo(OrderStatus.CREATED);
        assertThat(savedOrder.get().getTotalAmount()).isEqualByComparingTo(new BigDecimal("109.97"));
    }
    
    @Test
    void shouldHandleInventoryShortage() {
        // Simulate inventory service returning insufficient stock
        mockInventoryService.whenRequested("/api/inventory/check")
            .thenReturn(InsufficientStockResponse.of("product-1", 1, 2));
        
        CreateOrderRequest request = CreateOrderRequest.builder()
            .customerId("customer-123")
            .items(List.of(OrderItem.builder().productId("product-1").quantity(2).build()))
            .build();
        
        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(
            "/api/orders", request, ErrorResponse.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getErrorCode()).isEqualTo("INSUFFICIENT_STOCK");
    }
}
```

### Contract Testing with Pact
```java
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "product-service")
class ProductServiceContractTest {
    
    @Pact(consumer = "order-service")
    public RequestResponsePact productExistsPact(PactDslWithProvider builder) {
        return builder
            .given("product with id 123 exists")
            .uponReceiving("a request for product 123")
                .path("/api/products/123")
                .method("GET")
            .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body(LambdaDsl.newJsonBody(body -> body
                    .stringType("id", "123")
                    .stringType("name", "Test Product")
                    .numberType("price", 29.99)
                    .booleanType("available", true)
                ).build())
            .toPact();
    }
    
    @Test
    @PactTestFor(pactMethod = "productExistsPact")
    void shouldGetProductSuccessfully(MockServer mockServer) {
        ProductServiceClient client = new ProductServiceClient(mockServer.getUrl());
        
        ProductDto product = client.getProduct("123");
        
        assertThat(product.getId()).isEqualTo("123");
        assertThat(product.getName()).isEqualTo("Test Product");
        assertThat(product.getPrice()).isEqualByComparingTo(new BigDecimal("29.99"));
        assertThat(product.isAvailable()).isTrue();
    }
}
```

## 📈 Performance Optimization

### Caching Strategy
```java
@Configuration
@EnableCaching
public class CacheConfig {
    
    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager.Builder builder = RedisCacheManager
            .RedisCacheManagerBuilder
            .fromConnectionFactory(redisConnectionFactory())
            .cacheDefaults(cacheConfiguration());
        
        return builder.build();
    }
    
    private RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(30))
            .serializeKeysWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
}

@Service
public class ProductService {
    
    @Cacheable(value = "products", key = "#productId")
    public ProductDto getProduct(String productId) {
        return productRepository.findById(productId)
            .map(this::toDto)
            .orElseThrow(() -> new ProductNotFoundException(productId));
    }
    
    @CacheEvict(value = "products", key = "#product.id")
    public ProductDto updateProduct(ProductDto product) {
        Product saved = productRepository.save(toEntity(product));
        return toDto(saved);
    }
    
    // Cache warming strategy
    @EventListener(ApplicationReadyEvent.class)
    public void warmupCache() {
        List<String> popularProducts = analyticsService.getPopularProductIds();
        popularProducts.parallelStream()
            .forEach(this::getProduct); // This will populate the cache
    }
}
```

### Database Optimization
```java
@Entity
@Table(name = "products", indexes = {
    @Index(name = "idx_product_category", columnList = "category"),
    @Index(name = "idx_product_price", columnList = "price"),
    @Index(name = "idx_product_created", columnList = "created_at")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 500)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal price;
    
    // Optimistic locking for concurrent updates
    @Version
    private Long version;
    
    // Audit fields
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
}

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Custom query with pagination and sorting
    @Query(value = """
        SELECT p FROM Product p 
        WHERE (:category IS NULL OR p.category = :category)
          AND (:minPrice IS NULL OR p.price >= :minPrice)
          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
          AND p.available = true
        """)
    Page<Product> findProducts(
        @Param("category") String category,
        @Param("minPrice") BigDecimal minPrice,
        @Param("maxPrice") BigDecimal maxPrice,
        Pageable pageable
    );
    
    // Batch update for performance
    @Modifying
    @Query("UPDATE Product p SET p.available = false WHERE p.id IN :productIds")
    void markProductsUnavailable(@Param("productIds") List<Long> productIds);
}
```

## 🎯 Success Metrics

### Business KPIs
- **Revenue Growth**: 15% month-over-month increase
- **Customer Acquisition**: 1000+ new customers per month
- **Order Conversion**: >3% cart-to-order conversion rate
- **Customer Satisfaction**: >4.5/5 average rating

### Technical KPIs
- **Availability**: 99.99% uptime (4.32 minutes downtime/month)
- **Performance**: <200ms P95 API response time
- **Scalability**: Support 10,000 concurrent users
- **Security**: Zero data breaches, GDPR compliant

### Development KPIs
- **Code Quality**: >90% test coverage, <5% technical debt
- **Deployment**: <15 minutes deployment time
- **Time to Market**: <2 weeks feature delivery cycle
- **Reliability**: <0.1% error rate across all services

## 📚 Learning Outcomes

By completing this comprehensive project, you will have mastered:
- **Microservices Architecture**: Service decomposition, communication patterns
- **Spring Boot Ecosystem**: Comprehensive framework utilization
- **Cloud-Native Development**: Kubernetes, Docker, service mesh
- **Event-Driven Architecture**: Event sourcing, CQRS, saga patterns
- **Security Implementation**: OAuth2, JWT, data encryption, compliance
- **Observability**: Monitoring, tracing, metrics, alerting
- **Performance Optimization**: Caching, database tuning, async processing
- **DevOps Practices**: CI/CD, infrastructure as code, automated testing

This capstone project represents professional-level e-commerce platform development and demonstrates your readiness for senior engineering roles in enterprise environments.
