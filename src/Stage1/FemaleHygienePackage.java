package Stage1;

import java.util.Date;

public class FemaleHygienePackage extends AidItem {
    private String contentDescription;
    private int quantityOfItems;

    public FemaleHygienePackage(String id, String name, int quantity, int priorityLevel, String contentDescription, int quantityOfItems) {
        super(id, name, quantity, priorityLevel);
        this.contentDescription = contentDescription;
        this.quantityOfItems = quantityOfItems;
    }

    public String getContentDescription() { return contentDescription; }
    public int getQuantityOfItems() { return quantityOfItems; }

    @Override
    public String getInfo() {
        return super.toString() + ", Content: " + contentDescription + ", Items per Package: " + quantityOfItems;
    }

    @Override
    public Date getExpiryDate() {
        return null; // Female hygiene packages do not have an expiry date
    }
}