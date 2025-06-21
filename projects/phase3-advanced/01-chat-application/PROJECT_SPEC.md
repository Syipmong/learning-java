# Project 1: Multi-threaded Chat Application

## 🎯 Project Overview
Build a real-time, multi-threaded chat application that demonstrates advanced concurrency concepts, network programming, design patterns, and system architecture principles.

## 📋 System Requirements

### Core Features
1. **Multi-Client Server Architecture**
   - Concurrent client handling using thread pools
   - Scalable server design supporting 100+ simultaneous users
   - Non-blocking I/O with NIO selectors
   - Graceful connection management and cleanup

2. **Real-Time Messaging**
   - Instant message delivery across all connected clients
   - Message broadcasting and private messaging
   - Message history persistence and retrieval
   - Typing indicators and user presence status

3. **Advanced Chat Features**
   - Multiple chat rooms with dynamic creation
   - User authentication and session management
   - Message encryption for security
   - File sharing capabilities
   - Emoji and rich text support

4. **Administration & Monitoring**
   - Server administration panel
   - Real-time server statistics and monitoring
   - User management (ban, kick, promote to moderator)
   - Chat room moderation tools

## 🏗️ System Architecture

### Server Architecture
```
ChatServer/
├── server/
│   ├── ChatServer.java              // Main server class
│   ├── ClientHandler.java           // Individual client management
│   ├── MessageBroadcaster.java      // Message distribution
│   ├── ConnectionManager.java       // Connection lifecycle
│   └── ServerMonitor.java           // Performance monitoring
├── messaging/
│   ├── Message.java                 // Message entity
│   ├── MessageQueue.java            // Thread-safe message queue
│   ├── MessageProcessor.java        // Message handling logic
│   └── MessageEncryption.java       // Security layer
├── room/
│   ├── ChatRoom.java               // Chat room management
│   ├── RoomManager.java            // Multiple room coordination
│   └── RoomStatistics.java         // Room analytics
├── user/
│   ├── User.java                   // User entity
│   ├── UserSession.java            // Session management
│   ├── UserManager.java            // User operations
│   └── AuthenticationService.java  // User authentication
├── security/
│   ├── EncryptionService.java      // Message encryption
│   ├── SecurityManager.java        // Access control
│   └── AuditLogger.java           // Security auditing
└── util/
    ├── ThreadPoolManager.java      // Thread management
    ├── ConfigurationManager.java   // Server configuration
    └── PerformanceMetrics.java     // Performance tracking
```

### Client Architecture
```
ChatClient/
├── client/
│   ├── ChatClient.java             // Main client class
│   ├── MessageSender.java          // Outgoing messages
│   ├── MessageReceiver.java        // Incoming messages
│   └── ConnectionManager.java      // Server connection
├── ui/
│   ├── ChatUI.java                // User interface
│   ├── LoginDialog.java           // Authentication UI
│   ├── ChatWindow.java            // Main chat interface
│   └── SettingsPanel.java         // Client settings
├── model/
│   ├── ChatModel.java             // Application state
│   ├── MessageModel.java          // Message representation
│   └── UserModel.java             // User information
└── util/
    ├── MessageFormatter.java       // Message display formatting
    ├── FileTransferManager.java    // File sharing
    └── NotificationManager.java    // System notifications
```

## 🔧 Advanced Concurrency Implementation

### Thread-Safe Message Broadcasting
```java
public class MessageBroadcaster {
    private final ConcurrentHashMap<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private final BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();
    private final ExecutorService broadcastExecutor = Executors.newFixedThreadPool(4);
    private volatile boolean running = true;
    
    public void startBroadcasting() {
        // Dedicated thread for message processing
        Thread messageProcessor = new Thread(this::processMessages);
        messageProcessor.setDaemon(true);
        messageProcessor.start();
    }
    
    private void processMessages() {
        while (running) {
            try {
                Message message = messageQueue.take();
                broadcastMessage(message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    private void broadcastMessage(Message message) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        
        clients.values().forEach(client -> {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    client.sendMessage(message);
                } catch (IOException e) {
                    handleClientDisconnection(client);
                }
            }, broadcastExecutor);
            futures.add(future);
        });
        
        // Wait for all broadcasts to complete or timeout
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
            .orTimeout(5, TimeUnit.SECONDS)
            .exceptionally(throwable -> {
                logger.warn("Message broadcast timeout or failure", throwable);
                return null;
            });
    }
}
```

### Non-Blocking Server Implementation
```java
public class NIOChatServer {
    private final Selector selector;
    private final ServerSocketChannel serverChannel;
    private final Map<SocketChannel, ClientHandler> clientHandlers = new ConcurrentHashMap<>();
    private final ExecutorService workerPool = Executors.newCachedThreadPool();
    
    public void start() throws IOException {
        selector = Selector.open();
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(PORT));
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        while (!Thread.currentThread().isInterrupted()) {
            int readyChannels = selector.select(1000);
            if (readyChannels == 0) continue;
            
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();
                
                if (key.isAcceptable()) {
                    acceptConnection();
                } else if (key.isReadable()) {
                    handleRead(key);
                }
            }
        }
    }
    
    private void handleRead(SelectionKey key) {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ClientHandler handler = clientHandlers.get(clientChannel);
        
        // Process in thread pool to avoid blocking the selector
        workerPool.submit(() -> {
            try {
                handler.processIncomingData();
            } catch (IOException e) {
                handleClientDisconnection(clientChannel);
            }
        });
    }
}
```

## 🛡️ Security Implementation

### Message Encryption
```java
public class MessageEncryption {
    private final SecretKey secretKey;
    private final Cipher cipher;
    
    public MessageEncryption() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        this.secretKey = keyGen.generateKey();
        this.cipher = Cipher.getInstance("AES/GCM/NoPadding");
    }
    
    public EncryptedMessage encrypt(String message) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] iv = cipher.getIV();
        byte[] encryptedData = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        
        return new EncryptedMessage(encryptedData, iv);
    }
    
    public String decrypt(EncryptedMessage encryptedMessage) throws Exception {
        GCMParameterSpec spec = new GCMParameterSpec(128, encryptedMessage.getIv());
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        byte[] decryptedData = cipher.doFinal(encryptedMessage.getData());
        
        return new String(decryptedData, StandardCharsets.UTF_8);
    }
}
```

### Authentication System
```java
public class AuthenticationService {
    private final Map<String, HashedPassword> userCredentials = new ConcurrentHashMap<>();
    private final Map<String, UserSession> activeSessions = new ConcurrentHashMap<>();
    private final SecureRandom secureRandom = new SecureRandom();
    
    public AuthenticationResult authenticate(String username, String password) {
        HashedPassword storedPassword = userCredentials.get(username);
        if (storedPassword == null) {
            return AuthenticationResult.failure("User not found");
        }
        
        if (verifyPassword(password, storedPassword)) {
            String sessionId = generateSessionId();
            UserSession session = new UserSession(username, sessionId, Instant.now());
            activeSessions.put(sessionId, session);
            
            return AuthenticationResult.success(sessionId);
        }
        
        return AuthenticationResult.failure("Invalid password");
    }
    
    private String generateSessionId() {
        byte[] sessionBytes = new byte[32];
        secureRandom.nextBytes(sessionBytes);
        return Base64.getEncoder().encodeToString(sessionBytes);
    }
    
    private boolean verifyPassword(String password, HashedPassword hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword.getHash());
    }
}
```

## 📊 Performance Monitoring

### Real-Time Metrics Collection
```java
public class PerformanceMetrics {
    private final AtomicLong messagesProcessed = new AtomicLong(0);
    private final AtomicLong bytesTransferred = new AtomicLong(0);
    private final AtomicInteger activeConnections = new AtomicInteger(0);
    private final Timer messageProcessingTime = Timer.createTimer();
    
    private final ScheduledExecutorService metricsReporter = 
        Executors.newScheduledThreadPool(1);
    
    public void startMetricsReporting() {
        metricsReporter.scheduleAtFixedRate(this::reportMetrics, 
            0, 30, TimeUnit.SECONDS);
    }
    
    public void recordMessageProcessed(long processingTimeMs, int messageSize) {
        messagesProcessed.incrementAndGet();
        bytesTransferred.addAndGet(messageSize);
        messageProcessingTime.recordTime(processingTimeMs);
    }
    
    private void reportMetrics() {
        long totalMessages = messagesProcessed.get();
        long totalBytes = bytesTransferred.get();
        int connections = activeConnections.get();
        double avgProcessingTime = messageProcessingTime.getAverageTime();
        
        logger.info("Performance Metrics - Messages: {}, Bytes: {}, " +
                   "Connections: {}, Avg Processing Time: {}ms",
                   totalMessages, totalBytes, connections, avgProcessingTime);
        
        // Send metrics to monitoring system
        sendToMonitoringSystem(createMetricsSnapshot());
    }
}
```

### Connection Pool Management
```java
public class ConnectionPoolManager {
    private final BlockingQueue<Connection> availableConnections;
    private final Set<Connection> usedConnections = ConcurrentHashMap.newKeySet();
    private final AtomicInteger totalConnections = new AtomicInteger(0);
    private final int maxConnections;
    
    public ConnectionPoolManager(int maxConnections) {
        this.maxConnections = maxConnections;
        this.availableConnections = new LinkedBlockingQueue<>();
        initializePool();
    }
    
    public Connection acquireConnection() throws InterruptedException {
        Connection connection = availableConnections.poll(10, TimeUnit.SECONDS);
        if (connection == null) {
            throw new RuntimeException("Connection pool exhausted");
        }
        
        usedConnections.add(connection);
        return connection;
    }
    
    public void releaseConnection(Connection connection) {
        if (usedConnections.remove(connection)) {
            if (connection.isValid()) {
                availableConnections.offer(connection);
            } else {
                replaceInvalidConnection();
            }
        }
    }
    
    private void replaceInvalidConnection() {
        if (totalConnections.get() < maxConnections) {
            try {
                Connection newConnection = createNewConnection();
                availableConnections.offer(newConnection);
                totalConnections.incrementAndGet();
            } catch (Exception e) {
                logger.error("Failed to create replacement connection", e);
            }
        }
    }
}
```

## 🎮 User Interface Design

### Console-Based Chat Interface
```
================================================================================
                            NEXUS CHAT v2.0
================================================================================
Server: chat.nexus.com:8080                      Status: Connected ●
User: alice_johnson                               Room: #general
================================================================================

[14:32:15] <bob_smith> Hey everyone, how's the project going?
[14:32:28] <charlie_dev> @bob_smith Making good progress! Just finished the
                        authentication module.
[14:32:45] <alice_johnson> Nice work @charlie_dev! I'm working on the message
                          encryption layer.
[14:33:02] *** diana_admin joined the room ***
[14:33:05] <diana_admin> Hi team! 👋 Quick standup in 5 minutes
[14:33:12] <bob_smith> Roger that! 
[14:33:18] *** charlie_dev is typing... ***

================================================================================
Active Users (12): alice_johnson●, bob_smith●, charlie_dev⌨, diana_admin👑,
                   eve_tester💤, frank_qa●, grace_ui●, henry_ops●...

Rooms: #general (12), #development (8), #random (15), #announcements (45)
================================================================================
Type your message: |

Commands: /help /join <room> /whisper <user> <msg> /quit /users /rooms
         /upload <file> /status <message> /clear
================================================================================
```

### Rich Text and Emoji Support
```java
public class MessageFormatter {
    private static final Map<String, String> EMOJI_MAP = Map.of(
        ":smile:", "😊", ":heart:", "❤️", ":thumbsup:", "👍",
        ":wave:", "👋", ":fire:", "🔥", ":rocket:", "🚀"
    );
    
    public FormattedMessage formatMessage(String rawMessage, User sender) {
        String formatted = rawMessage;
        
        // Process emojis
        for (Map.Entry<String, String> emoji : EMOJI_MAP.entrySet()) {
            formatted = formatted.replace(emoji.getKey(), emoji.getValue());
        }
        
        // Process mentions
        formatted = processMentions(formatted);
        
        // Process URLs
        formatted = processUrls(formatted);
        
        // Apply user-specific formatting (colors, styles)
        formatted = applyUserStyles(formatted, sender);
        
        return new FormattedMessage(formatted, extractMetadata(rawMessage));
    }
    
    private String processMentions(String message) {
        return message.replaceAll("@(\\w+)", 
            match -> colorize(match, Color.BLUE) + "🔔");
    }
}
```

## 🧪 Comprehensive Testing Strategy

### Load Testing
```java
@Test
public void testConcurrentClientConnections() throws Exception {
    int numberOfClients = 100;
    CountDownLatch startLatch = new CountDownLatch(1);
    CountDownLatch finishLatch = new CountDownLatch(numberOfClients);
    
    List<ChatClient> clients = new ArrayList<>();
    List<Exception> exceptions = Collections.synchronizedList(new ArrayList<>());
    
    // Create clients
    for (int i = 0; i < numberOfClients; i++) {
        ChatClient client = new ChatClient("user" + i, "localhost", 8080);
        clients.add(client);
        
        new Thread(() -> {
            try {
                startLatch.await(); // Wait for all threads to be ready
                
                client.connect();
                client.sendMessage("Hello from " + client.getUsername());
                
                // Simulate some chat activity
                for (int j = 0; j < 10; j++) {
                    client.sendMessage("Message " + j);
                    Thread.sleep(100);
                }
                
            } catch (Exception e) {
                exceptions.add(e);
            } finally {
                finishLatch.countDown();
            }
        }).start();
    }
    
    // Start all clients simultaneously
    startLatch.countDown();
    
    // Wait for all clients to finish
    assertTrue(finishLatch.await(30, TimeUnit.SECONDS));
    
    // Verify no exceptions occurred
    assertTrue("Exceptions occurred: " + exceptions, exceptions.isEmpty());
    
    // Verify server metrics
    PerformanceMetrics metrics = server.getMetrics();
    assertEquals(numberOfClients, metrics.getPeakConnections());
    assertTrue(metrics.getMessagesProcessed() >= numberOfClients * 11); // 1 + 10 messages per client
}
```

### Message Ordering Test
```java
@Test
public void testMessageOrderingUnderLoad() throws Exception {
    ChatClient sender = new ChatClient("sender", "localhost", 8080);
    ChatClient receiver = new ChatClient("receiver", "localhost", 8080);
    
    sender.connect();
    receiver.connect();
    
    List<String> sentMessages = new ArrayList<>();
    List<String> receivedMessages = Collections.synchronizedList(new ArrayList<>());
    
    // Set up message listener
    receiver.setMessageListener(message -> 
        receivedMessages.add(message.getContent()));
    
    // Send messages rapidly
    for (int i = 0; i < 1000; i++) {
        String message = "Message " + i;
        sentMessages.add(message);
        sender.sendMessage(message);
    }
    
    // Wait for all messages to be received
    Thread.sleep(5000);
    
    // Verify message ordering
    assertEquals(sentMessages.size(), receivedMessages.size());
    assertEquals(sentMessages, receivedMessages);
}
```

## 📚 Learning Outcomes

### Advanced Concurrency Mastery
- **Thread Pools**: Optimal configuration and management
- **Lock-Free Programming**: Atomic operations and CAS algorithms
- **Producer-Consumer**: Complex scenarios with multiple producers/consumers
- **Non-Blocking I/O**: Selector-based server architecture

### Network Programming Excellence
- **Socket Programming**: TCP/IP communication protocols
- **Protocol Design**: Custom message protocols and serialization
- **Connection Management**: Handling connection lifecycle and failures
- **Performance Optimization**: Throughput and latency optimization

### Design Patterns Application
- **Observer Pattern**: Event-driven message broadcasting
- **Command Pattern**: Message processing and queuing
- **State Pattern**: User and connection state management
- **Factory Pattern**: Client and message creation

### Security Implementation
- **Encryption**: Symmetric and asymmetric encryption techniques
- **Authentication**: Secure user authentication and session management
- **Authorization**: Role-based access control
- **Audit Logging**: Security event tracking and compliance

## 🎯 Success Criteria
- **Scalability**: Support 100+ concurrent users without performance degradation
- **Reliability**: 99.9% uptime with proper error handling and recovery
- **Security**: All messages encrypted, secure authentication implemented
- **Performance**: Sub-100ms message delivery latency
- **Maintainability**: Clean architecture with comprehensive documentation

This advanced project will demonstrate your mastery of concurrent programming, network communications, and enterprise-level system design principles.
