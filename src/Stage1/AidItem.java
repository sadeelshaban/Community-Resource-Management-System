package Stage1;

import java.util.Date;

public abstract class AidItem {
    private String id;
    private String name;
    private int quantity;
    private int priorityLevel;

    // Constructor for items without expiry date
    public AidItem(String id, String name, int quantity, int priorityLevel) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.priorityLevel = priorityLevel;
    }
    
    // Abstract method to get specific info
    public abstract String getInfo();

    // Abstract method to get expiry date (will be implemented in ExpirableItem)
    public abstract Date getExpiryDate();

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getPriorityLevel() { return priorityLevel; }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Quantity: " + quantity + ", Priority: " + priorityLevel;
    }
}