import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainGUI extends JFrame {
    private TaskManager taskManager;
    private JList<Task> taskList;
    private DefaultListModel<Task> listModel;
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextField dueDateField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton toggleButton;
    private JComboBox<String> filterComboBox;

    public MainGUI() {
        taskManager = new TaskManager();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Task Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create task list panel
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setCellRenderer(new TaskListCellRenderer());
        JScrollPane listScrollPane = new JScrollPane(taskList);
        listScrollPane.setPreferredSize(new Dimension(400, 0));

        // Create input panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title field
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        titleField = new JTextField(20);
        inputPanel.add(titleField, gbc);

        // Description area
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        inputPanel.add(descScrollPane, gbc);

        // Due date field
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Due Date:"), gbc);
        gbc.gridx = 1;
        dueDateField = new JTextField(20);
        dueDateField.setToolTipText("Format: yyyy-MM-dd HH:mm");
        inputPanel.add(dueDateField, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButton = new JButton("Add Task");
        updateButton = new JButton("Update Task");
        deleteButton = new JButton("Delete Task");
        toggleButton = new JButton("Toggle Completion");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(toggleButton);

        // Filter combo box
        String[] filterOptions = {"All Tasks", "Pending Tasks", "Completed Tasks"};
        filterComboBox = new JComboBox<>(filterOptions);
        filterComboBox.addActionListener(e -> refreshTaskList());

        // Add components to main panel
        mainPanel.add(listScrollPane, BorderLayout.WEST);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(inputPanel, BorderLayout.NORTH);
        rightPanel.add(buttonPanel, BorderLayout.CENTER);
        rightPanel.add(filterComboBox, BorderLayout.SOUTH);
        
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Add action listeners
        addButton.addActionListener(e -> addTask());
        updateButton.addActionListener(e -> updateTask());
        deleteButton.addActionListener(e -> deleteTask());
        toggleButton.addActionListener(e -> toggleTaskCompletion());

        // Add main panel to frame
        add(mainPanel);

        // Initial refresh
        refreshTaskList();
    }

    private void addTask() {
        try {
            String title = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            LocalDateTime dueDate = LocalDateTime.parse(dueDateField.getText().trim(), 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a title");
                return;
            }

            taskManager.addTask(title, description, dueDate);
            clearInputFields();
            refreshTaskList();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-MM-dd HH:mm");
        }
    }

    private void updateTask() {
        Task selectedTask = taskList.getSelectedValue();
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(this, "Please select a task to update");
            return;
        }

        try {
            String title = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            LocalDateTime dueDate = LocalDateTime.parse(dueDateField.getText().trim(), 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a title");
                return;
            }

            taskManager.updateTask(selectedTask.getId(), title, description, dueDate);
            clearInputFields();
            refreshTaskList();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-MM-dd HH:mm");
        }
    }

    private void deleteTask() {
        Task selectedTask = taskList.getSelectedValue();
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(this, "Please select a task to delete");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this task?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            taskManager.deleteTask(selectedTask.getId());
            clearInputFields();
            refreshTaskList();
        }
    }

    private void toggleTaskCompletion() {
        Task selectedTask = taskList.getSelectedValue();
        if (selectedTask == null) {
            JOptionPane.showMessageDialog(this, "Please select a task to toggle");
            return;
        }

        taskManager.toggleTaskCompletion(selectedTask.getId());
        refreshTaskList();
    }

    private void clearInputFields() {
        titleField.setText("");
        descriptionArea.setText("");
        dueDateField.setText("");
    }

    private void refreshTaskList() {
        listModel.clear();
        List<Task> tasks;
        
        switch (filterComboBox.getSelectedIndex()) {
            case 1:
                tasks = taskManager.getPendingTasks();
                break;
            case 2:
                tasks = taskManager.getCompletedTasks();
                break;
            default:
                tasks = taskManager.getAllTasks();
        }
        
        for (Task task : tasks) {
            listModel.addElement(task);
        }
    }

    private class TaskListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, 
                int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof Task) {
                Task task = (Task) value;
                setText(String.format("<html><b>%s</b><br>Due: %s<br>Status: %s</html>",
                    task.getTitle(),
                    task.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    task.isCompleted() ? "Completed" : "Pending"));
                
                if (task.isCompleted()) {
                    setForeground(new Color(0, 128, 0)); // Dark green for completed tasks
                } else {
                    setForeground(new Color(128, 0, 0)); // Dark red for pending tasks
                }
            }
            
            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MainGUI().setVisible(true);
        });
    }
} 