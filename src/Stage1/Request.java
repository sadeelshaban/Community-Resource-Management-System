package Stage1;

public class Request {
    private AidItem item;
    private int quantity;

    public Request(AidItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public AidItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Request: " + quantity + " of " + item.getId();
    }
}
