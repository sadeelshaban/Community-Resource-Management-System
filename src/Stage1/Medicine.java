package Stage1;

import java.util.Date;

public class Medicine extends ExpirableItem {
    private boolean requiresPrescription;

    public Medicine(String id, String name, int quantity, int priorityLevel, boolean requiresPrescription, Date expiryDate) {
        super(id, name, quantity, priorityLevel, expiryDate);
        this.requiresPrescription = requiresPrescription;
    }

    public boolean isRequiresPrescription() {
        return requiresPrescription;
    }
    
    @Override
    public String getInfo() {
        return super.getInfo() + ", Requires Prescription: " + requiresPrescription;
    }
}