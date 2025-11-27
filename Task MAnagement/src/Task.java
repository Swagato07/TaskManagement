import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public abstract class Task {
    protected static int nextId = 1;
    protected int id;
    protected String title;
    protected String description;
    protected Priority priority;
    protected TaskStatus status;
    protected TaskCategory category;
    protected LocalDateTime createdDate;
    protected LocalDateTime dueDate;
    protected LocalDateTime completedDate;
    
    public Task(String title, String description, Priority priority, TaskCategory category, LocalDateTime dueDate) throws InvalidTaskException {
        validateTask(title, description);
        this.id = nextId++;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.category = category;
        this.status = TaskStatus.TODO;
        this.createdDate = LocalDateTime.now();
        this.dueDate = dueDate;
    }
    
    protected void validateTask(String title, String description) throws InvalidTaskException {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidTaskException("Task title cannot be empty!");
        }
        if (title.length() > 100) {
            throw new InvalidTaskException("Task title too long (max 100 characters)!");
        }
    }
    
    public abstract String getTaskType();
    
    public abstract void displayDetails();
    
    public void markComplete() {
        this.status = TaskStatus.COMPLETED;
        this.completedDate = LocalDateTime.now();
    }
    
    public void updateStatus(TaskStatus newStatus) {
        this.status = newStatus;
        if (newStatus == TaskStatus.COMPLETED && completedDate == null) {
            completedDate = LocalDateTime.now();
        }
    }
    
    public boolean isOverdue() {
        if (status.isComplete() || dueDate == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(dueDate);
    }
    
    public long getDaysUntilDue() {
        if (dueDate == null) {
            return -1;
        }
        return ChronoUnit.DAYS.between(LocalDateTime.now(), dueDate);
    }
    
    public double calculateCompletionTime() {
        if (completedDate == null) {
            return -1;
        }
        return ChronoUnit.HOURS.between(createdDate, completedDate);
    }
    
    public void displayBasicInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        System.out.println("\nID: " + id + " | " + category.getIcon() + " " + title);
        System.out.println("Type: " + getTaskType());
        System.out.println("Description: " + description);
        System.out.println("Priority: " + priority.getDescription());
        System.out.println("Status: " + status.getDisplayName());
        System.out.println("Created: " + createdDate.format(formatter));
        
        if (dueDate != null) {
            System.out.println("Due: " + dueDate.format(formatter));
            if (isOverdue()) {
                System.out.println(">>> OVERDUE! <<<");
            } else if (!status.isComplete()) {
                long daysLeft = getDaysUntilDue();
                System.out.println("Days until due: " + daysLeft);
            }
        }
        
        if (completedDate != null) {
            System.out.println("Completed: " + completedDate.format(formatter));
            System.out.println("Completion time: " + calculateCompletionTime() + " hours");
        }
    }
    
    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) throws InvalidTaskException {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidTaskException("Task title cannot be empty!");
        }
        this.title = title;
    }
    
    public Priority getPriority() {
        return priority;
    }
    
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    public TaskStatus getStatus() {
        return status;
    }
    
    public TaskCategory getCategory() {
        return category;
    }
    
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}
