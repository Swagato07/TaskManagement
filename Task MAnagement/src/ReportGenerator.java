import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

public class ReportGenerator {
    private TaskManager taskManager;
    private TaskStatistics statistics;
    
    public ReportGenerator(TaskManager taskManager) {
        this.taskManager = taskManager;
        this.statistics = new TaskStatistics(taskManager);
    }
    
    public String generateSummaryReport() {
        StringBuilder report = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        
        report.append("\n");
        report.append("╔════════════════════════════════════════╗\n");
        report.append("║     TASK MANAGEMENT SUMMARY REPORT     ║\n");
        report.append("╚════════════════════════════════════════╝\n");
        
        report.append("\n--- Quick Stats ---\n");
        report.append("Total Tasks: ").append(statistics.calculateTotal()).append("\n");
        report.append("Completion Rate: ").append(String.format("%.1f%%", statistics.calculateCompletionRate())).append("\n");
        
        int overdue = statistics.countOverdueTasks();
        if (overdue > 0) {
            report.append("⚠ OVERDUE: ").append(overdue).append(" tasks\n");
        }
        
        int urgent = statistics.countHighPriorityTasks();
        if (urgent > 0) {
            report.append("⚠ URGENT: ").append(urgent).append(" tasks\n");
        }
        
        report.append("\n--- Status Distribution ---\n");
        for (TaskStatus status : TaskStatus.values()) {
            int count = statistics.calculateTotal(status);
            if (count > 0) {
                report.append(String.format("%-15s : %d\n", status.getDisplayName(), count));
            }
        }
        
        report.append("\n--- Category Distribution ---\n");
        for (TaskCategory category : TaskCategory.values()) {
            int count = statistics.calculateTotal(category);
            if (count > 0) {
                report.append(String.format("%-15s : %d\n", category.toString(), count));
            }
        }
        
        return report.toString();
    }
    
    public String generateDetailedReport() {
        StringBuilder report = new StringBuilder();
        
        report.append(generateSummaryReport());
        
        ArrayList<Task> overdueTasks = taskManager.getOverdueTasks();
        if (!overdueTasks.isEmpty()) {
            report.append("\n--- OVERDUE TASKS ---\n");
            int count = 1;
            for (Task task : overdueTasks) {
                report.append(count++).append(". ");
                report.append("[ID:").append(task.getId()).append("] ");
                report.append(task.getTitle()).append(" (");
                report.append(task.getPriority()).append(")\n");
            }
        }
        
        ArrayList<Task> upcoming = taskManager.getUpcomingTasks(7);
        if (!upcoming.isEmpty()) {
            report.append("\n--- UPCOMING (Next 7 Days) ---\n");
            int count = 1;
            for (Task task : upcoming) {
                report.append(count++).append(". ");
                report.append("[ID:").append(task.getId()).append("] ");
                report.append(task.getTitle()).append(" - Due in ");
                report.append(task.getDaysUntilDue()).append(" days\n");
            }
        }
        
        return report.toString();
    }
    
    public void exportReport(String filename) {
        String report = generateDetailedReport();
        System.out.println(report);
        System.out.println("\n[Note: In a full implementation, this would save to: " + filename + "]");
    }
    
    public void displayProductivityAnalysis() {
        StringBuilder analysis = new StringBuilder();
        
        analysis.append("\n");
        analysis.append("═══════════════════════════════════════\n");
        analysis.append("      PRODUCTIVITY ANALYSIS\n");
        analysis.append("═══════════════════════════════════════\n");
        
        double avgCompletionTime = statistics.calculateAverageCompletionTime();
        if (avgCompletionTime > 0) {
            analysis.append("\nAverage task completion: ");
            analysis.append(String.format("%.1f hours", avgCompletionTime));
            
            if (avgCompletionTime < 24) {
                analysis.append(" ✓ Excellent!");
            } else if (avgCompletionTime < 72) {
                analysis.append(" ✓ Good");
            } else {
                analysis.append(" - Could improve");
            }
            analysis.append("\n");
        }
        
        double completionRate = statistics.calculateCompletionRate();
        analysis.append("\nCompletion rate: ");
        analysis.append(String.format("%.1f%%", completionRate));
        
        if (completionRate >= 80) {
            analysis.append(" ✓ Outstanding!");
        } else if (completionRate >= 60) {
            analysis.append(" ✓ Good progress");
        } else if (completionRate >= 40) {
            analysis.append(" - Room for improvement");
        } else {
            analysis.append(" - Focus on completing tasks");
        }
        analysis.append("\n");
        
        Priority mostCommon = statistics.getMostCommonPriority();
        analysis.append("\nMost common priority: ").append(mostCommon).append("\n");
        
        TaskCategory mostActive = statistics.getMostActiveCategory();
        analysis.append("Most active category: ").append(mostActive).append("\n");
        
        int overdue = statistics.countOverdueTasks();
        int total = statistics.calculateTotal();
        if (total > 0) {
            double overduePercentage = (overdue * 100.0) / total;
            analysis.append("\nOverdue rate: ").append(String.format("%.1f%%", overduePercentage));
            
            if (overduePercentage == 0) {
                analysis.append(" ✓ Perfect!");
            } else if (overduePercentage < 10) {
                analysis.append(" ✓ Well managed");
            } else {
                analysis.append(" ⚠ Needs attention");
            }
            analysis.append("\n");
        }
        
        System.out.println(analysis.toString());
    }
}
