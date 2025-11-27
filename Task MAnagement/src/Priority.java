public enum Priority {
    LOW(1, "Low Priority"),
    MEDIUM(2, "Medium Priority"),
    HIGH(3, "High Priority"),
    URGENT(4, "Urgent - Critical");
    
    private int level;
    private String description;
    
    Priority(int level, String description) {
        this.level = level;
        this.description = description;
    }
    
    public int getLevel() {
        return level;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isUrgent() {
        return this == URGENT || this == HIGH;
    }
}
