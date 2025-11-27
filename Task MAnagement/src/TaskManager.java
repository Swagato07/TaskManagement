import java.util.ArrayList;
import java.util.LinkedList;
import java.time.LocalDateTime;

public class TaskManager {
    private ArrayList<Task> allTasks;
    private LinkedList<Task> recentlyCompleted;
    private int maxRecentTasks = 10;
    
    public TaskManager() {
        this.allTasks = new ArrayList<>();
        this.recentlyCompleted = new LinkedList<>();
    }
    
    public void addTask(Task task) {
        allTasks.add(task);
        System.out.println("Task added successfully! ID: " + task.getId());
    }
    
    public void removeTask(int id) throws TaskException {
        Task task = findTaskById(id);
        if (task == null) {
            throw new TaskException("Task with ID " + id + " not found!");
        }
        allTasks.remove(task);
        System.out.println("Task removed successfully!");
    }
    
    public void completeTask(int id) throws TaskException {
        Task task = findTaskById(id);
        if (task == null) {
            throw new TaskException("Task with ID " + id + " not found!");
        }
        task.markComplete();
        addToRecentlyCompleted(task);
        System.out.println("Task marked as complete!");
    }
    
    public void updateTaskStatus(int id, TaskStatus newStatus) throws TaskException {
        Task task = findTaskById(id);
        if (task == null) {
            throw new TaskException("Task with ID " + id + " not found!");
        }
        task.updateStatus(newStatus);
        if (newStatus == TaskStatus.COMPLETED) {
            addToRecentlyCompleted(task);
        }
        System.out.println("Task status updated to: " + newStatus.getDisplayName());
    }
    
    private void addToRecentlyCompleted(Task task) {
        recentlyCompleted.addFirst(task);
        while (recentlyCompleted.size() > maxRecentTasks) {
            recentlyCompleted.removeLast();
        }
    }
    
    public Task findTaskById(int id) {
        for (Task task : allTasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }
    
    public ArrayList<Task> findTasksByTitle(String searchTerm) {
        ArrayList<Task> results = new ArrayList<>();
        String lowerSearch = searchTerm.toLowerCase();
        
        for (Task task : allTasks) {
            if (task.getTitle().toLowerCase().contains(lowerSearch)) {
                results.add(task);
            }
        }
        return results;
    }
    
    public ArrayList<Task> filterByStatus(TaskStatus status) {
        ArrayList<Task> filtered = new ArrayList<>();
        for (Task task : allTasks) {
            if (task.getStatus() == status) {
                filtered.add(task);
            }
        }
        return filtered;
    }
    
    public ArrayList<Task> filterByCategory(TaskCategory category) {
        ArrayList<Task> filtered = new ArrayList<>();
        for (Task task : allTasks) {
            if (task.getCategory() == category) {
                filtered.add(task);
            }
        }
        return filtered;
    }
    
    public ArrayList<Task> filterByPriority(Priority priority) {
        ArrayList<Task> filtered = new ArrayList<>();
        for (Task task : allTasks) {
            if (task.getPriority() == priority) {
                filtered.add(task);
            }
        }
        return filtered;
    }
    
    public ArrayList<Task> getOverdueTasks() {
        ArrayList<Task> overdue = new ArrayList<>();
        for (Task task : allTasks) {
            if (task.isOverdue()) {
                overdue.add(task);
            }
        }
        return overdue;
    }
    
    public ArrayList<Task> getUpcomingTasks(int days) {
        ArrayList<Task> upcoming = new ArrayList<>();
        LocalDateTime futureDate = LocalDateTime.now().plusDays(days);
        
        for (Task task : allTasks) {
            if (!task.getStatus().isComplete() && task.getDueDate() != null) {
                if (task.getDueDate().isBefore(futureDate) && task.getDueDate().isAfter(LocalDateTime.now())) {
                    upcoming.add(task);
                }
            }
        }
        return upcoming;
    }
    
    public void listAllTasks() {
        if (allTasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        
        System.out.println("\n=== All Tasks (" + allTasks.size() + " total) ===");
        int index = 1;
        for (Task task : allTasks) {
            System.out.println("\n" + index++ + ".");
            task.displayDetails();
        }
    }
    
    public void listTasks(ArrayList<Task> tasks, String title) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        
        System.out.println("\n=== " + title + " (" + tasks.size() + " tasks) ===");
        int index = 1;
        for (Task task : tasks) {
            System.out.println("\n" + index++ + ".");
            task.displayDetails();
        }
    }
    
    public void listRecentlyCompleted() {
        if (recentlyCompleted.isEmpty()) {
            System.out.println("No recently completed tasks.");
            return;
        }
        
        System.out.println("\n=== Recently Completed Tasks ===");
        int index = 1;
        for (Task task : recentlyCompleted) {
            System.out.println("\n" + index++ + ".");
            task.displayBasicInfo();
        }
    }
    
    public int getTotalTaskCount() {
        return allTasks.size();
    }
    
    public int getTaskCount(TaskStatus status) {
        int count = 0;
        for (Task task : allTasks) {
            if (task.getStatus() == status) {
                count++;
            }
        }
        return count;
    }
    
    public ArrayList<Task> getAllTasks() {
        return allTasks;
    }
}
