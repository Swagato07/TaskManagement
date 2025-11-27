import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskManagementApp {
    
    private Scanner scanner;
    private TaskManager taskManager;
    private TaskStatistics statistics;
    private ReportGenerator reportGenerator;
    
    public TaskManagementApp() {
        scanner = new Scanner(System.in);
        taskManager = new TaskManager();
        statistics = new TaskStatistics(taskManager);
        reportGenerator = new ReportGenerator(taskManager);
    }
    
    public static void main(String[] args) {
        TaskManagementApp app = new TaskManagementApp();
        app.loadSampleData();
        app.run();
    }
    
    public void run() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║    TASK MANAGEMENT SYSTEM v1.0         ║");
        System.out.println("║    Organize your work & life!          ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        boolean running = true;
        
        while (running) {
            displayMainMenu();
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        createNewTask();
                        break;
                    case 2:
                        viewAllTasks();
                        break;
                    case 3:
                        viewTasksByStatus();
                        break;
                    case 4:
                        viewTasksByCategory();
                        break;
                    case 5:
                        searchTasks();
                        break;
                    case 6:
                        updateTask();
                        break;
                    case 7:
                        completeTask();
                        break;
                    case 8:
                        deleteTask();
                        break;
                    case 9:
                        viewStatistics();
                        break;
                    case 10:
                        generateReports();
                        break;
                    case 11:
                        viewOverdueAndUpcoming();
                        break;
                    case 0:
                        running = false;
                        System.out.println("\n✓ Thank you for using Task Management System!");
                        System.out.println("Your productivity matters. See you next time!\n");
                        break;
                    default:
                        System.out.println("\n⚠ Invalid choice! Please try again.");
                }
                
                if (running && choice != 0) {
                    waitForUser();
                }
                
            } catch (NumberFormatException e) {
                System.out.println("\n⚠ Error: Please enter a valid number!");
                waitForUser();
            } catch (Exception e) {
                System.out.println("\n⚠ An error occurred: " + e.getMessage());
                waitForUser();
            }
        }
        
        scanner.close();
    }
    
    private void displayMainMenu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           MAIN MENU                    ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║  1. Create New Task                    ║");
        System.out.println("║  2. View All Tasks                     ║");
        System.out.println("║  3. Filter by Status                   ║");
        System.out.println("║  4. Filter by Category                 ║");
        System.out.println("║  5. Search Tasks                       ║");
        System.out.println("║  6. Update Task                        ║");
        System.out.println("║  7. Mark Task Complete                 ║");
        System.out.println("║  8. Delete Task                        ║");
        System.out.println("║  9. View Statistics                    ║");
        System.out.println("║ 10. Generate Reports                   ║");
        System.out.println("║ 11. Overdue & Upcoming Tasks           ║");
        System.out.println("║  0. Exit                               ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Enter your choice: ");
    }
    
    private void createNewTask() {
        System.out.println("\n=== CREATE NEW TASK ===");
        
        try {
            System.out.print("Enter task title: ");
            String title = scanner.nextLine().trim();
            
            System.out.print("Enter description: ");
            String description = scanner.nextLine().trim();
            
            System.out.println("\nSelect Priority:");
            int priorityIndex = 1;
            for (Priority p : Priority.values()) {
                System.out.println(priorityIndex++ + ". " + p.getDescription());
            }
            System.out.print("Choice: ");
            int priorityChoice = Integer.parseInt(scanner.nextLine().trim()) - 1;
            Priority priority = Priority.values()[priorityChoice];
            
            System.out.println("\nSelect Task Type:");
            System.out.println("1. Work Task");
            System.out.println("2. Personal Task");
            System.out.println("3. Shopping Task");
            System.out.print("Choice: ");
            int typeChoice = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("\nDays until due (0 for no due date): ");
            int daysUntilDue = Integer.parseInt(scanner.nextLine().trim());
            LocalDateTime dueDate = daysUntilDue > 0 ? LocalDateTime.now().plusDays(daysUntilDue) : null;
            
            Task newTask = null;
            
            switch (typeChoice) {
                case 1:
                    System.out.print("Project name: ");
                    String project = scanner.nextLine().trim();
                    System.out.print("Assigned to: ");
                    String assignedTo = scanner.nextLine().trim();
                    System.out.print("Estimated hours: ");
                    int hours = Integer.parseInt(scanner.nextLine().trim());
                    newTask = new WorkTask(title, description, priority, dueDate, project, assignedTo, hours);
                    break;
                    
                case 2:
                    System.out.print("Location (optional): ");
                    String location = scanner.nextLine().trim();
                    System.out.print("Is recurring? (yes/no): ");
                    boolean recurring = scanner.nextLine().trim().equalsIgnoreCase("yes");
                    int recurDays = 0;
                    if (recurring) {
                        System.out.print("Repeat every X days: ");
                        recurDays = Integer.parseInt(scanner.nextLine().trim());
                    }
                    newTask = new PersonalTask(title, description, priority, dueDate, location, recurring, recurDays);
                    break;
                    
                case 3:
                    System.out.print("Estimated budget: $");
                    double budget = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Store: ");
                    String store = scanner.nextLine().trim();
                    ShoppingTask shoppingTask = new ShoppingTask(title, description, priority, dueDate, budget, store);
                    
                    System.out.print("\nAdd items to shopping list? (yes/no): ");
                    if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                        boolean addingItems = true;
                        while (addingItems) {
                            System.out.print("Enter item (or 'done' to finish): ");
                            String item = scanner.nextLine().trim();
                            if (item.equalsIgnoreCase("done")) {
                                addingItems = false;
                            } else if (!item.isEmpty()) {
                                shoppingTask.addItem(item);
                            }
                        }
                    }
                    newTask = shoppingTask;
                    break;
                    
                default:
                    throw new InvalidTaskException("Invalid task type!");
            }
            
            if (newTask != null) {
                taskManager.addTask(newTask);
                System.out.println("\n✓ Task created successfully!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("⚠ Invalid number format!");
        } catch (InvalidTaskException e) {
            System.out.println("⚠ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("⚠ Error creating task: " + e.getMessage());
        }
    }
    
    private void viewAllTasks() {
        taskManager.listAllTasks();
    }
    
    private void viewTasksByStatus() {
        System.out.println("\n=== FILTER BY STATUS ===");
        System.out.println("Select status:");
        
        int index = 1;
        for (TaskStatus status : TaskStatus.values()) {
            System.out.println(index++ + ". " + status.getDisplayName());
        }
        
        try {
            System.out.print("Choice: ");
            int choice = Integer.parseInt(scanner.nextLine().trim()) - 1;
            TaskStatus selectedStatus = TaskStatus.values()[choice];
            
            ArrayList<Task> filtered = taskManager.filterByStatus(selectedStatus);
            taskManager.listTasks(filtered, selectedStatus.getDisplayName() + " Tasks");
        } catch (Exception e) {
            System.out.println("⚠ Invalid selection!");
        }
    }
    
    private void viewTasksByCategory() {
        System.out.println("\n=== FILTER BY CATEGORY ===");
        System.out.println("Select category:");
        
        int index = 1;
        for (TaskCategory category : TaskCategory.values()) {
            System.out.println(index++ + ". " + category);
        }
        
        try {
            System.out.print("Choice: ");
            int choice = Integer.parseInt(scanner.nextLine().trim()) - 1;
            TaskCategory selectedCategory = TaskCategory.values()[choice];
            
            ArrayList<Task> filtered = taskManager.filterByCategory(selectedCategory);
            taskManager.listTasks(filtered, selectedCategory.toString() + " Tasks");
        } catch (Exception e) {
            System.out.println("⚠ Invalid selection!");
        }
    }
    
    private void searchTasks() {
        System.out.println("\n=== SEARCH TASKS ===");
        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine().trim();
        
        if (searchTerm.isEmpty()) {
            System.out.println("⚠ Search term cannot be empty!");
            return;
        }
        
        ArrayList<Task> results = taskManager.findTasksByTitle(searchTerm);
        taskManager.listTasks(results, "Search Results for '" + searchTerm + "'");
    }
    
    private void updateTask() {
        System.out.println("\n=== UPDATE TASK ===");
        System.out.print("Enter task ID: ");
        
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Task task = taskManager.findTaskById(id);
            
            if (task == null) {
                System.out.println("⚠ Task not found!");
                return;
            }
            
            System.out.println("\nCurrent task:");
            task.displayDetails();
            
            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Status");
            System.out.println("2. Priority");
            System.out.println("3. Title");
            System.out.print("Choice: ");
            
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    System.out.println("\nSelect new status:");
                    int idx = 1;
                    for (TaskStatus status : TaskStatus.values()) {
                        System.out.println(idx++ + ". " + status.getDisplayName());
                    }
                    System.out.print("Choice: ");
                    int statusChoice = Integer.parseInt(scanner.nextLine().trim()) - 1;
                    taskManager.updateTaskStatus(id, TaskStatus.values()[statusChoice]);
                    break;
                    
                case 2:
                    System.out.println("\nSelect new priority:");
                    idx = 1;
                    for (Priority p : Priority.values()) {
                        System.out.println(idx++ + ". " + p.getDescription());
                    }
                    System.out.print("Choice: ");
                    int priorityChoice = Integer.parseInt(scanner.nextLine().trim()) - 1;
                    task.setPriority(Priority.values()[priorityChoice]);
                    System.out.println("✓ Priority updated!");
                    break;
                    
                case 3:
                    System.out.print("Enter new title: ");
                    String newTitle = scanner.nextLine().trim();
                    task.setTitle(newTitle);
                    System.out.println("✓ Title updated!");
                    break;
                    
                default:
                    System.out.println("⚠ Invalid choice!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("⚠ Invalid input!");
        } catch (Exception e) {
            System.out.println("⚠ Error: " + e.getMessage());
        }
    }
    
    private void completeTask() {
        System.out.println("\n=== MARK TASK COMPLETE ===");
        System.out.print("Enter task ID: ");
        
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            taskManager.completeTask(id);
            System.out.println("✓ Task marked as complete!");
        } catch (NumberFormatException e) {
            System.out.println("⚠ Invalid ID!");
        } catch (TaskException e) {
            System.out.println("⚠ " + e.getMessage());
        }
    }
    
    private void deleteTask() {
        System.out.println("\n=== DELETE TASK ===");
        System.out.print("Enter task ID: ");
        
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            Task task = taskManager.findTaskById(id);
            
            if (task == null) {
                System.out.println("⚠ Task not found!");
                return;
            }
            
            System.out.println("\nAre you sure you want to delete:");
            System.out.println(task.getTitle());
            System.out.print("Type 'yes' to confirm: ");
            
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                taskManager.removeTask(id);
                System.out.println("✓ Task deleted!");
            } else {
                System.out.println("Deletion cancelled.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("⚠ Invalid ID!");
        } catch (TaskException e) {
            System.out.println("⚠ " + e.getMessage());
        }
    }
    
    private void viewStatistics() {
        statistics.displayStatistics();
    }
    
    private void generateReports() {
        System.out.println("\n=== GENERATE REPORTS ===");
        System.out.println("1. Summary Report");
        System.out.println("2. Detailed Report");
        System.out.println("3. Productivity Analysis");
        System.out.print("Choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    System.out.println(reportGenerator.generateSummaryReport());
                    break;
                case 2:
                    System.out.println(reportGenerator.generateDetailedReport());
                    break;
                case 3:
                    reportGenerator.displayProductivityAnalysis();
                    break;
                default:
                    System.out.println("⚠ Invalid choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠ Invalid input!");
        }
    }
    
    private void viewOverdueAndUpcoming() {
        System.out.println("\n=== OVERDUE & UPCOMING TASKS ===");
        
        ArrayList<Task> overdue = taskManager.getOverdueTasks();
        if (!overdue.isEmpty()) {
            taskManager.listTasks(overdue, "⚠ OVERDUE Tasks");
        } else {
            System.out.println("\n✓ No overdue tasks!");
        }
        
        ArrayList<Task> upcoming = taskManager.getUpcomingTasks(7);
        if (!upcoming.isEmpty()) {
            taskManager.listTasks(upcoming, "Upcoming Tasks (Next 7 Days)");
        } else {
            System.out.println("\n✓ No upcoming tasks in the next 7 days.");
        }
        
        taskManager.listRecentlyCompleted();
    }
    
    private void loadSampleData() {
        try {
            taskManager.addTask(new WorkTask(
                "Complete Project Proposal",
                "Draft and finalize Q1 project proposal for management review",
                Priority.HIGH,
                LocalDateTime.now().plusDays(2),
                "Q1 Initiative",
                "John Doe",
                16
            ));
            
            taskManager.addTask(new WorkTask(
                "Code Review",
                "Review pull requests from team members",
                Priority.MEDIUM,
                LocalDateTime.now().plusDays(1),
                "Development",
                "Team",
                4
            ));
            
            taskManager.addTask(new PersonalTask(
                "Dentist Appointment",
                "Annual dental checkup",
                Priority.MEDIUM,
                LocalDateTime.now().plusDays(5),
                "Downtown Dental Clinic",
                false,
                0
            ));
            
            ShoppingTask groceries = new ShoppingTask(
                "Weekly Groceries",
                "Buy groceries for the week",
                Priority.LOW,
                LocalDateTime.now().plusDays(1),
                150.00,
                "Whole Foods"
            );
            groceries.addItem("Milk");
            groceries.addItem("Bread");
            groceries.addItem("Eggs");
            groceries.addItem("Vegetables");
            taskManager.addTask(groceries);
            
            Task completedTask = new PersonalTask(
                "Morning Workout",
                "30 minute cardio session",
                Priority.MEDIUM,
                LocalDateTime.now().minusDays(1),
                "Gym",
                true,
                1
            );
            taskManager.addTask(completedTask);
            taskManager.completeTask(completedTask.getId());
            
        } catch (Exception e) {
            System.out.println("Error loading sample data: " + e.getMessage());
        }
    }
    
    private void waitForUser() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
