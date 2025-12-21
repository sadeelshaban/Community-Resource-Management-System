package Stage1;

import java.util.Date;
import java.text.SimpleDateFormat;

public class FoodPackage extends AidItem {
    private Date expiryDate;

    public FoodPackage(String id, String name, int quantity, int priorityLevel, Date expiryDate) {
        super(id, name, quantity, priorityLevel);
        this.expiryDate = expiryDate;
    }

    @Override
    public String getInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Expiry: " + (expiryDate != null ? sdf.format(expiryDate) : "N/A");
    }

    @Override
    public Date getExpiryDate() {
        return expiryDate;
    }
}
