# Task Management System

A simple and user-friendly Task Management System built with Java Swing. This application allows you to manage your tasks efficiently with features like adding, updating, deleting, and tracking task completion status.

## Features

- Add new tasks with title, description, and due date
- Update existing tasks
- Delete tasks
- Toggle task completion status
- Filter tasks (All/Pending/Completed)
- Modern and responsive user interface
- Color-coded task status (Green for completed, Red for pending)

## Requirements

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (Eclipse, IntelliJ IDEA, NetBeans, etc.)
- Git (for version control)

## Installation

### Using Command Line

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/task-management-system.git
   cd task-management-system
   ```

2. Compile the Java files:
   ```bash
   javac *.java
   ```

3. Run the application:
   ```bash
   java MainGUI
   ```

### Using Eclipse

1. Open Eclipse IDE
2. Go to File → Import → Git → Projects from Git
3. Select "Clone URI"
4. Enter the repository URL: `https://github.com/yourusername/task-management-system.git`
5. Follow the wizard to complete the import
6. Right-click on the project → Run As → Java Application
7. Select `MainGUI` as the main class

### Using IntelliJ IDEA

1. Open IntelliJ IDEA
2. Go to File → New → Project from Version Control
3. Enter the repository URL: `https://github.com/yourusername/task-management-system.git`
4. Click "Clone"
5. Wait for the project to be imported
6. Right-click on `MainGUI.java` → Run 'MainGUI.main()'

### Using NetBeans

1. Open NetBeans IDE
2. Go to Team → Git → Clone
3. Enter the repository URL: `https://github.com/yourusername/task-management-system.git`
4. Follow the wizard to complete the clone
5. Right-click on the project → Run

## Usage Instructions

1. **Adding a Task**
   - Enter the task title (required)
   - Enter the task description (optional)
   - Enter the due date in the format: yyyy-MM-dd HH:mm (e.g., 2024-03-20 14:30)
   - Click "Add Task"

2. **Updating a Task**
   - Select a task from the list
   - Modify the fields as needed
   - Click "Update Task"

3. **Deleting a Task**
   - Select a task from the list
   - Click "Delete Task"
   - Confirm the deletion

4. **Toggling Task Completion**
   - Select a task from the list
   - Click "Toggle Completion"

5. **Filtering Tasks**
   - Use the dropdown menu to filter tasks:
     - All Tasks: Shows all tasks
     - Pending Tasks: Shows only incomplete tasks
     - Completed Tasks: Shows only completed tasks

## Project Structure

```
task-management-system/
├── Task.java              # Task model class
├── TaskManager.java       # Task management logic
├── MainGUI.java          # User interface
└── README.md             # Documentation
```

## Notes

- Task titles are required fields
- Due dates must be in the format: yyyy-MM-dd HH:mm
- Completed tasks are shown in green, pending tasks in red
- The application uses the system's default look and feel for better integration with your operating system

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 