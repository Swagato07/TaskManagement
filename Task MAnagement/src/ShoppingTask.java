import java.time.LocalDateTime;
import java.util.ArrayList;

public class ShoppingTask extends Task {
    private ArrayList<String> items;
    private double estimatedBudget;
    private double actualCost;
    private String store;
    
    public ShoppingTask(String title, String description, Priority priority, LocalDateTime dueDate,
                       double estimatedBudget, String store) throws InvalidTaskException {
        super(title, description, priority, TaskCategory.SHOPPING, dueDate);
        this.items = new ArrayList<>();
        this.estimatedBudget = estimatedBudget;
        this.actualCost = 0.0;
        this.store = store;
    }
    
    @Override
    public String getTaskType() {
        return "Shopping Task";
    }
    
    @Override
    public void displayDetails() {
        displayBasicInfo();
        System.out.println("Store: " + store);
        System.out.println("Estimated budget: $" + String.format("%.2f", estimatedBudget));
        
        if (actualCost > 0) {
            System.out.println("Actual cost: $" + String.format("%.2f", actualCost));
            double difference = actualCost - estimatedBudget;
            if (difference > 0) {
                System.out.println("Over budget by: $" + String.format("%.2f", difference));
            } else {
                System.out.println("Under budget by: $" + String.format("%.2f", Math.abs(difference)));
            }
        }
        
        if (!items.isEmpty()) {
            System.out.println("Shopping list (" + items.size() + " items):");
            int count = 1;
            for (String item : items) {
                System.out.println("  " + count++ + ". " + item);
            }
        }
    }
    
    public void addItem(String item) {
        items.add(item);
    }
    
    public void removeItem(String item) {
        items.remove(item);
    }
    
    public void setActualCost(double cost) {
        this.actualCost = cost;
    }
    
    public double calculateSavings() {
        return estimatedBudget - actualCost;
    }
    
    public int getItemCount() {
        return items.size();
    }
}
