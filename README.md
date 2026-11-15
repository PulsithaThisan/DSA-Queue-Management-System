# DSA Queue Management System

A console-based **player queue management system** written in Java, built entirely on **custom data structures** (linked list, queue and stack) implemented from scratch without using Java's built-in collections. This project was developed as coursework for the **Data Structures and Algorithms** module.

## Overview

The system manages players waiting to be assigned to stations. Players are registered with a skill level, placed into a priority queue based on that level, and assigned to stations in priority order. Every assignment and removal is recorded so the last action can be undone.

## Features

- **Register players** with an ID, name and skill level (Low / Medium / High)
- **Remove players** from the system
- **Search players** by ID or name
- **Assign the next player to a station**, taking players from the High, then Medium, then Low priority queue
- **View players by priority** queue
- **Undo the last action** (reverts the most recent assignment or removal)
- Input validation for menu choices, skill levels and station numbers

## Data Structures

All core structures are generic (`<T>`) and implemented by hand in the `DataStructures` package:

| Class | Description | Used for |
|-------|-------------|----------|
| `Node<T>` | Singly linked node holding data and a reference to the next node | Building block for all structures |
| `MyLinkedList<T>` | Singly linked list with insert, search by ID/name, remove, duplicate check and display | Master list of registered players |
| `MyQueue<T>` | FIFO queue with enqueue, dequeue, display and removal of inactive players | Low, Medium and High priority waiting queues |
| `MyStack<T>` | LIFO stack backed by linked nodes with push and pop | Action history for the undo feature |

## Project Structure

```
DSA-Queue-Management-System/
├── pom.xml
└── src/main/java/
    ├── DataStructures/
    │   ├── Node.java
    │   ├── MyLinkedList.java
    │   ├── MyQueue.java
    │   └── MyStack.java
    ├── Model/
    │   ├── Player.java          # Player details: ID, name, level, station, active status
    │   └── ActionRecord.java    # A recorded action (assign/remove) used for undo
    └── Services/
        └── QueueManagementSystem.java   # Main class with the menu and system logic
```

## How It Works

1. A new player is added to the linked list of all players and to the queue that matches their skill level.
2. When a station is assigned, the system takes the next player from the **High** priority queue first, then **Medium**, then **Low**.
3. Each assignment or removal is pushed onto the action stack as an `ActionRecord`.
4. **Undo** pops the latest record and reverses it: an assigned player is returned to the waiting state, and a removed player is restored.

## Main Menu

```
---- Main Menu ----
1. Register Player
2. Remove Player
3. Search Player
4. Assign Player Station
5. View Players by Priority
6. Undo Last Action
7. Exit System
```

## Getting Started

### Prerequisites
- **JDK 26** (as set in `pom.xml`; lower the `maven.compiler.source`/`target` values to match your installed JDK if needed)
- **Maven**, or an IDE such as IntelliJ IDEA

### Run with IntelliJ IDEA
1. Clone the repository:
   ```bash
   git clone https://github.com/PulsithaThisan/DSA-Queue-Management-System.git
   ```
2. Open the folder in IntelliJ IDEA (it will be detected as a Maven project).
3. Run `Services.QueueManagementSystem`.

### Run from the command line
```bash
mvn compile
java -cp target/classes Services.QueueManagementSystem
```

## Author

**Pulsitha Thisan Peiris**
BSc (Hons) Artificial Intelligence and Data Science, Informatics Institute of Technology (IIT), Sri Lanka

- GitHub: [@PulsithaThisan](https://github.com/PulsithaThisan)
- LinkedIn: [pulsitha-thisan](https://www.linkedin.com/in/pulsitha-thisan)
