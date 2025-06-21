# Task Priority Queue Exercise

## 🎯 Objective
Master PriorityQueue, Comparator interface, and priority-based data structures by implementing a task management system with priority queues.

## 📋 Problem Description

Create a task management system that uses priority queues to manage tasks based on their priority, due date, and creation time. The system should support different priority schemes and demonstrate proper use of Comparator.

## 🔧 Requirements

### Core Features
1. **Task Class**: Create a Task class with priority, description, due date, and creation time
2. **Priority Queue**: Use PriorityQueue to manage tasks by different criteria
3. **Multiple Comparators**: Implement different sorting strategies
4. **Task Operations**: Add, remove, peek, and display tasks
5. **Priority Schemes**: Support different priority calculation methods

### Task Properties
- **id**: Unique identifier (auto-generated)
- **description**: Task description
- **priority**: Task priority (1-10, where 1 is highest priority)
- **dueDate**: When the task is due
- **creationTime**: When the task was created
- **completed**: Whether the task is completed

### Comparator Types
1. **Priority Comparator**: Sort by priority (ascending)
2. **Due Date Comparator**: Sort by due date (earliest first)
3. **Creation Time Comparator**: Sort by creation time (oldest first)
4. **Composite Comparator**: Combine multiple criteria

## 🎨 Sample Usage

```java
TaskManager manager = new TaskManager();

// Add tasks
manager.addTask("Complete project report", 1, LocalDate.now().plusDays(2));
manager.addTask("Buy groceries", 3, LocalDate.now().plusDays(1));
manager.addTask("Call dentist", 2, LocalDate.now().plusDays(3));

// Get next task by priority
Task nextTask = manager.getNextTask();
System.out.println("Next task: " + nextTask.getDescription());

// Switch to due date priority
manager.setSortingStrategy(SortingStrategy.DUE_DATE);
Task urgentTask = manager.getNextTask();
System.out.println("Most urgent task: " + urgentTask.getDescription());

// Display all tasks
manager.displayAllTasks();
```

## 🏆 Success Criteria

1. **Correct Priority Queue Usage**: Proper implementation of PriorityQueue
2. **Multiple Comparators**: Different sorting strategies work correctly
3. **Dynamic Sorting**: Can change sorting strategy at runtime
4. **Task Management**: All CRUD operations work properly
5. **Edge Cases**: Handles empty queue, duplicate priorities, etc.

## 📈 Bonus Features

1. **Task Categories**: Group tasks by category
2. **Recurring Tasks**: Support for repeating tasks
3. **Task Dependencies**: Handle task prerequisites
4. **Statistics**: Task completion statistics
5. **Persistence**: Save/load tasks from file

## 🔍 Key Concepts to Demonstrate

- PriorityQueue usage and behavior
- Comparator interface and lambda expressions
- Comparable vs Comparator
- Priority-based algorithms
- Collections framework integration
