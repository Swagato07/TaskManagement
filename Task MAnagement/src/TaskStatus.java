public enum TaskStatus {
    TODO("To Do", "Not started yet"),
    IN_PROGRESS("In Progress", "Currently working on it"),
    BLOCKED("Blocked", "Waiting for something"),
    COMPLETED("Completed", "Successfully finished"),
    CANCELLED("Cancelled", "No longer needed");
    
    private String displayName;
    private String description;
    
    TaskStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isComplete() {
        return this == COMPLETED || this == CANCELLED;
    }
}
