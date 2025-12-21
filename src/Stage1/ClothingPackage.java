package Stage1;

import java.util.Date;

public class ClothingPackage extends AidItem {
    private String season;
    private String targetAgeGroup;

    public ClothingPackage(String id, String name, int quantity, int priorityLevel, String season, String targetAgeGroup) {
        super(id, name, quantity, priorityLevel);
        this.season = season;
        this.targetAgeGroup = targetAgeGroup;
    }

    public String getSeason() { return season; }
    public String getTargetAgeGroup() { return targetAgeGroup; }

    @Override
    public String getInfo() {
        return super.toString() + ", Season: " + season + ", Target Age Group: " + targetAgeGroup;
    }

    @Override
    public Date getExpiryDate() {
        return null; // Clothing packages do not have an expiry date
    }
}