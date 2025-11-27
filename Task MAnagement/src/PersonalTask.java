import java.time.LocalDateTime;

public class PersonalTask extends Task {
    private String location;
    private boolean recurring;
    private int recurDays;
    
    public PersonalTask(String title, String description, Priority priority, LocalDateTime dueDate,
                       String location, boolean recurring, int recurDays) throws InvalidTaskException {
        super(title, description, priority, TaskCategory.PERSONAL, dueDate);
        this.location = location;
        this.recurring = recurring;
        this.recurDays = recurDays;
    }
    
    @Override
    public String getTaskType() {
        return "Personal Task";
    }
    
    @Override
    public void displayDetails() {
        displayBasicInfo();
        if (location != null && !location.isEmpty()) {
            System.out.println("Location: " + location);
        }
        if (recurring) {
            System.out.println("Recurring: Every " + recurDays + " days");
        }
    }
    
    public LocalDateTime getNextOccurrence() {
        if (!recurring || dueDate == null) {
            return null;
        }
        return dueDate.plusDays(recurDays);
    }
    
    public boolean isRecurring() {
        return recurring;
    }
}
