import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Complete solution for Task Priority Queue Exercise
 * Demonstrates PriorityQueue, Comparator, and advanced Collections usage
 */

// Task class with all required properties
class Task {
    private static int idCounter = 1;
    
    private final int id;
    private String description;
    private int priority; // 1-10, where 1 is highest priority
    private LocalDate dueDate;
    private LocalDateTime creationTime;
    private boolean completed;
    
    public Task(String description, int priority, LocalDate dueDate) {
        this.id = idCounter++;
        this.description = description;
        setPriority(priority);
        this.dueDate = dueDate;
        this.creationTime = LocalDateTime.now();
        this.completed = false;
    }
    
    // Getters
    public int getId() { return id; }
    public String getDescription() { return description; }
    public int getPriority() { return priority; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDateTime getCreationTime() { return creationTime; }
    public boolean isCompleted() { return completed; }
    
    // Setters with validation
    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        this.description = description;
    }
    
    public void setPriority(int priority) {
        if (priority < 1 || priority > 10) {
            throw new IllegalArgumentException("Priority must be between 1 and 10");
        }
        this.priority = priority;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    // Utility methods
    public boolean isOverdue() {
        return dueDate != null && dueDate.isBefore(LocalDate.now());
    }
    
    public long getDaysUntilDue() {
        return dueDate != null ? 
            java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), dueDate) : 
            Long.MAX_VALUE;
    }
    
    @Override
    public String toString() {
        return String.format("Task{id=%d, desc='%s', priority=%d, due=%s, overdue=%s}", 
                           id, description, priority, dueDate, isOverdue());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return id == task.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

// Enum for different sorting strategies
enum SortingStrategy {
    PRIORITY,           // Sort by priority (1 = highest)
    DUE_DATE,          // Sort by due date (earliest first)
    CREATION_TIME,     // Sort by creation time (oldest first)
    COMPOSITE          // Sort by priority, then due date, then creation time
}

// Comparator implementations
class TaskComparators {
    public static final Comparator<Task> BY_PRIORITY = 
        Comparator.comparingInt(Task::getPriority);
    
    public static final Comparator<Task> BY_DUE_DATE = 
        Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()));
    
    public static final Comparator<Task> BY_CREATION_TIME = 
        Comparator.comparing(Task::getCreationTime);
    
    public static final Comparator<Task> COMPOSITE = 
        BY_PRIORITY
        .thenComparing(BY_DUE_DATE)
        .thenComparing(BY_CREATION_TIME);
    
    public static Comparator<Task> getComparator(SortingStrategy strategy) {
        switch (strategy) {
            case PRIORITY: return BY_PRIORITY;
            case DUE_DATE: return BY_DUE_DATE;
            case CREATION_TIME: return BY_CREATION_TIME;
            case COMPOSITE: return COMPOSITE;
            default: throw new IllegalArgumentException("Unknown strategy: " + strategy);
        }
    }
}

// Main TaskManager class
class TaskManager {
    private PriorityQueue<Task> tasks;
    private SortingStrategy currentStrategy;
    
    public TaskManager() {
        this(SortingStrategy.PRIORITY);
    }
    
    public TaskManager(SortingStrategy strategy) {
        this.currentStrategy = strategy;
        this.tasks = new PriorityQueue<>(TaskComparators.getComparator(strategy));
    }
    
    public void addTask(String description, int priority, LocalDate dueDate) {
        Task task = new Task(description, priority, dueDate);
        tasks.offer(task);
        System.out.println("Added task: " + task);
    }
    
    public Task getNextTask() {
        return tasks.peek();
    }
    
    public Task completeNextTask() {
        Task task = tasks.poll();
        if (task != null) {
            task.setCompleted(true);
            System.out.println("Completed task: " + task);
        }
        return task;
    }
    
    public void setSortingStrategy(SortingStrategy strategy) {
        if (this.currentStrategy == strategy) {
            return; // No change needed
        }
        
        // Convert to list, change strategy, and rebuild queue
        List<Task> taskList = new ArrayList<>(tasks);
        this.currentStrategy = strategy;
        this.tasks = new PriorityQueue<>(TaskComparators.getComparator(strategy));
        this.tasks.addAll(taskList);
        
        System.out.println("Changed sorting strategy to: " + strategy);
    }
    
    public void displayAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks in queue.");
            return;
        }
        
        System.out.println("\n=== All Tasks (in priority order) ===");
        System.out.println("Current strategy: " + currentStrategy);
        
        // Create a copy to avoid modifying original queue
        PriorityQueue<Task> copy = new PriorityQueue<>(tasks);
        int index = 1;
        while (!copy.isEmpty()) {
            Task task = copy.poll();
            System.out.printf("%d. %s%n", index++, task);
        }
        System.out.println("=====================================\n");
    }
    
    public List<Task> getTasksByPriority(int priority) {
        return tasks.stream()
                   .filter(task -> task.getPriority() == priority)
                   .collect(Collectors.toList());
    }
    
    public List<Task> getOverdueTasks() {
        return tasks.stream()
                   .filter(Task::isOverdue)
                   .collect(Collectors.toList());
    }
    
    public List<Task> getTasksDueWithin(int days) {
        return tasks.stream()
                   .filter(task -> task.getDaysUntilDue() <= days && task.getDaysUntilDue() >= 0)
                   .collect(Collectors.toList());
    }
    
    public Map<Integer, List<Task>> getTasksByPriorityMap() {
        return tasks.stream()
                   .collect(Collectors.groupingBy(Task::getPriority));
    }
    
    public int getTaskCount() {
        return tasks.size();
    }
    
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
    
    public void displayStatistics() {
        System.out.println("\n=== Task Statistics ===");
        System.out.println("Total tasks: " + getTaskCount());
        System.out.println("Overdue tasks: " + getOverdueTasks().size());
        System.out.println("Tasks due within 3 days: " + getTasksDueWithin(3).size());
        
        Map<Integer, List<Task>> byPriority = getTasksByPriorityMap();
        System.out.println("Tasks by priority:");
        for (int i = 1; i <= 10; i++) {
            List<Task> priorityTasks = byPriority.getOrDefault(i, Collections.emptyList());
            if (!priorityTasks.isEmpty()) {
                System.out.printf("  Priority %d: %d tasks%n", i, priorityTasks.size());
            }
        }
        System.out.println("=====================\n");
    }
}

// Main class demonstrating the solution
public class TaskPriorityQueueSolution {
    public static void main(String[] args) {
        System.out.println("=== Task Priority Queue Demo ===\n");
        
        // Create TaskManager
        TaskManager manager = new TaskManager();
        
        // Add sample tasks
        System.out.println("1. Adding tasks...");
        manager.addTask("Complete project report", 1, LocalDate.now().plusDays(2));
        manager.addTask("Buy groceries", 3, LocalDate.now().plusDays(1));
        manager.addTask("Call dentist", 2, LocalDate.now().plusDays(3));
        manager.addTask("Fix car", 4, LocalDate.now().minusDays(1)); // Overdue
        manager.addTask("Plan vacation", 5, LocalDate.now().plusDays(7));
        manager.addTask("Submit tax documents", 1, LocalDate.now().plusDays(1)); // High priority
        
        System.out.println("\n2. Display all tasks (Priority order):");
        manager.displayAllTasks();
        
        System.out.println("3. Next task to work on:");
        Task nextTask = manager.getNextTask();
        System.out.println("Next: " + nextTask);
        
        System.out.println("\n4. Complete next task:");
        manager.completeNextTask();
        
        System.out.println("\n5. Switch to Due Date priority:");
        manager.setSortingStrategy(SortingStrategy.DUE_DATE);
        manager.displayAllTasks();
        
        System.out.println("6. Most urgent by due date:");
        Task urgentTask = manager.getNextTask();
        System.out.println("Most urgent: " + urgentTask);
        
        System.out.println("\n7. Switch to Composite priority:");
        manager.setSortingStrategy(SortingStrategy.COMPOSITE);
        manager.displayAllTasks();
        
        System.out.println("8. Task statistics:");
        manager.displayStatistics();
        
        System.out.println("9. High priority tasks (priority 1-2):");
        List<Task> highPriorityTasks = new ArrayList<>();
        highPriorityTasks.addAll(manager.getTasksByPriority(1));
        highPriorityTasks.addAll(manager.getTasksByPriority(2));
        highPriorityTasks.forEach(System.out::println);
        
        System.out.println("\n10. Overdue tasks:");
        List<Task> overdueTasks = manager.getOverdueTasks();
        if (overdueTasks.isEmpty()) {
            System.out.println("No overdue tasks!");
        } else {
            overdueTasks.forEach(System.out::println);
        }
        
        System.out.println("\n11. Complete all remaining tasks:");
        while (!manager.isEmpty()) {
            manager.completeNextTask();
        }
        
        System.out.println("\nFinal task count: " + manager.getTaskCount());
        System.out.println("\n=== Demo Complete ===");
    }
}
