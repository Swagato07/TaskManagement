public enum TaskCategory {
    WORK,
    PERSONAL,
    SHOPPING,
    HEALTH,
    EDUCATION,
    FINANCE,
    OTHER;
    
    public String getIcon() {
        switch(this) {
            case WORK:
                return "[WORK]";
            case PERSONAL:
                return "[PERSONAL]";
            case SHOPPING:
                return "[SHOPPING]";
            case HEALTH:
                return "[HEALTH]";
            case EDUCATION:
                return "[EDUCATION]";
            case FINANCE:
                return "[FINANCE]";
            default:
                return "[OTHER]";
        }
    }
}
