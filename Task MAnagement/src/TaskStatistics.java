import java.util.ArrayList;

public class TaskStatistics {
    private TaskManager taskManager;
    
    public TaskStatistics(TaskManager taskManager) {
        this.taskManager = taskManager;
    }
    
    public int calculateTotal() {
        return taskManager.getTotalTaskCount();
    }
    
    public int calculateTotal(TaskStatus status) {
        return taskManager.getTaskCount(status);
    }
    
    public int calculateTotal(TaskCategory category) {
        return taskManager.filterByCategory(category).size();
    }
    
    public double calculateCompletionRate() {
        int total = calculateTotal();
        if (total == 0) {
            return 0.0;
        }
        int completed = calculateTotal(TaskStatus.COMPLETED);
        return (completed * 100.0) / total;
    }
    
    public double calculateAverageCompletionTime() {
        ArrayList<Task> allTasks = taskManager.getAllTasks();
        double totalHours = 0;
        int completedCount = 0;
        
        for (Task task : allTasks) {
            if (task.getStatus() == TaskStatus.COMPLETED) {
                double hours = task.calculateCompletionTime();
                if (hours > 0) {
                    totalHours += hours;
                    completedCount++;
                }
            }
        }
        
        if (completedCount == 0) {
            return 0.0;
        }
        return totalHours / completedCount;
    }
    
    public Priority getMostCommonPriority() {
        int[] priorityCounts = new int[Priority.values().length];
        
        for (Task task : taskManager.getAllTasks()) {
            priorityCounts[task.getPriority().ordinal()]++;
        }
        
        int maxIndex = 0;
        int maxCount = priorityCounts[0];
        
        for (int i = 1; i < priorityCounts.length; i++) {
            if (priorityCounts[i] > maxCount) {
                maxCount = priorityCounts[i];
                maxIndex = i;
            }
        }
        
        return Priority.values()[maxIndex];
    }
    
    public TaskCategory getMostActiveCategory() {
        int maxCount = 0;
        TaskCategory mostActive = TaskCategory.OTHER;
        
        for (TaskCategory category : TaskCategory.values()) {
            int count = calculateTotal(category);
            if (count > maxCount) {
                maxCount = count;
                mostActive = category;
            }
        }
        
        return mostActive;
    }
    
    public int countOverdueTasks() {
        return taskManager.getOverdueTasks().size();
    }
    
    public int countHighPriorityTasks() {
        int count = 0;
        for (Task task : taskManager.getAllTasks()) {
            if (task.getPriority().isUrgent() && !task.getStatus().isComplete()) {
                count++;
            }
        }
        return count;
    }
    
    public void displayStatistics() {
        System.out.println("\n========================================");
        System.out.println("         TASK STATISTICS");
        System.out.println("========================================");
        
        System.out.println("\n--- Overall Statistics ---");
        System.out.println("Total tasks: " + calculateTotal());
        System.out.println("Completed: " + calculateTotal(TaskStatus.COMPLETED));
        System.out.println("In Progress: " + calculateTotal(TaskStatus.IN_PROGRESS));
        System.out.println("To Do: " + calculateTotal(TaskStatus.TODO));
        System.out.println("Blocked: " + calculateTotal(TaskStatus.BLOCKED));
        System.out.println("Cancelled: " + calculateTotal(TaskStatus.CANCELLED));
        
        double completionRate = calculateCompletionRate();
        System.out.println("\nCompletion rate: " + String.format("%.1f", completionRate) + "%");
        
        double avgTime = calculateAverageCompletionTime();
        if (avgTime > 0) {
            System.out.println("Average completion time: " + String.format("%.1f", avgTime) + " hours");
        }
        
        System.out.println("\n--- Priority Breakdown ---");
        for (Priority priority : Priority.values()) {
            int count = taskManager.filterByPriority(priority).size();
            System.out.println(priority + ": " + count);
        }
        
        System.out.println("\n--- Category Breakdown ---");
        for (TaskCategory category : TaskCategory.values()) {
            int count = calculateTotal(category);
            System.out.println(category + ": " + count);
        }
        
        System.out.println("\n--- Alerts ---");
        int overdueCount = countOverdueTasks();
        if (overdueCount > 0) {
            System.out.println("⚠ OVERDUE tasks: " + overdueCount);
        }
        
        int urgentCount = countHighPriorityTasks();
        if (urgentCount > 0) {
            System.out.println("⚠ High priority pending: " + urgentCount);
        }
        
        if (overdueCount == 0 && urgentCount == 0) {
            System.out.println("✓ No urgent issues!");
        }
    }
}
