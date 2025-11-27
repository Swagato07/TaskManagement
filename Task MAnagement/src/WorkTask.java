import java.time.LocalDateTime;

public class WorkTask extends Task {
    private String project;
    private String assignedTo;
    private int estimatedHours;
    
    public WorkTask(String title, String description, Priority priority, LocalDateTime dueDate, 
                    String project, String assignedTo, int estimatedHours) throws InvalidTaskException {
        super(title, description, priority, TaskCategory.WORK, dueDate);
        this.project = project;
        this.assignedTo = assignedTo;
        this.estimatedHours = estimatedHours;
    }
    
    @Override
    public String getTaskType() {
        return "Work Task";
    }
    
    @Override
    public void displayDetails() {
        displayBasicInfo();
        System.out.println("Project: " + project);
        System.out.println("Assigned to: " + assignedTo);
        System.out.println("Estimated hours: " + estimatedHours);
        
        if (status == TaskStatus.COMPLETED && completedDate != null) {
            double actualHours = calculateCompletionTime();
            double variance = actualHours - estimatedHours;
            System.out.println("Actual hours: " + actualHours);
            System.out.println("Variance: " + (variance > 0 ? "+" : "") + variance + " hours");
        }
    }
    
    public String getProject() {
        return project;
    }
    
    public int getEstimatedHours() {
        return estimatedHours;
    }
}
