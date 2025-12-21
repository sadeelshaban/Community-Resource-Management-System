package Stage1;

import java.util.Date;

public abstract class ExpirableItem extends AidItem {
    private Date expiryDate;

    public ExpirableItem(String id, String name, int quantity, int priorityLevel, Date expiryDate) {
        super(id, name, quantity, priorityLevel);
        this.expiryDate = expiryDate;
    }
    
    // Helper method to check if the item is expired
    public boolean isExpired() {
        if (expiryDate == null) {
            return false;
        }
        return expiryDate.before(new Date());
    }
    
    // Override the abstract method from AidItem
    @Override
    public Date getExpiryDate() {
        return expiryDate;
    }
    
    // Add getInfo() here to display expiry info
    @Override
    public String getInfo() {
        return super.toString() + ", Expiry Date: " + expiryDate;
    }
}