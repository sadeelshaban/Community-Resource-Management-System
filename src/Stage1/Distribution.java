package Stage1;
import java.util.Date;

public class Distribution {
    private Beneficiary beneficiary;
    private AidItem distributedItem;
    private int quantity;
    private Date distributionDate;

    public Distribution(Beneficiary beneficiary, AidItem distributedItem, int quantity, Date distributionDate) {
        this.beneficiary = beneficiary;
        this.distributedItem = distributedItem;
        this.quantity = quantity;
        this.distributionDate = distributionDate;
    }

    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public AidItem getDistributedItem() {
        return distributedItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getDistributionDate() {
        return distributionDate;
    }

    @Override
    public String toString() {
        return "Distribution to: " + beneficiary.getName() + " | Item: " + distributedItem.getId() + " - " + distributedItem.getClass().getSimpleName() + " | Quantity: " + quantity + " | Date: " + distributionDate;
    }
}