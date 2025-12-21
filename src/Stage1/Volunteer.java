package Stage1;

import java.util.ArrayList;
import java.util.List;

public class Volunteer extends Person {
    private List<AidItem> deliveredItems;

    public Volunteer(String id, String name, String password, String address, String phone) {
        super(id, name, password, address, phone);
        this.deliveredItems = new ArrayList<>();
    }
    
    public void addDeliveredItem(AidItem item) {
        this.deliveredItems.add(item);
    }

    public List<AidItem> getDeliveredItems() {
        return deliveredItems;
    }
}