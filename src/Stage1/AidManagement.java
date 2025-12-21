package Stage1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AidManagement class: manages users, aid items, and distributions.
 * Using ArrayList for dynamic data management.
 */
public class AidManagement implements Distributable {
    private List<Person> people;
    private List<AidItem> aidItems;
    private List<Distribution> distributions;

    public AidManagement() {
        people = new ArrayList<>();
        aidItems = new ArrayList<>();
        distributions = new ArrayList<>();
    }

    // User Management 
    public boolean isIdUnique(String id) {
        return people.stream()
                .noneMatch(person -> person.getId().equals(id));
    }

    public void registerUser(Person p) {
        if (p != null && isIdUnique(p.getId())) {
            people.add(p);
        }
    }

    public void registerBeneficiary(Beneficiary b) {
        if (b != null && isIdUnique(b.getId())) {
            people.add(b);
        }
    }

    public Person findPersonById(String id) {
        return people.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Beneficiary findBeneficiaryById(String id) {
        return people.stream()
                .filter(person -> person instanceof Beneficiary && person.getId().equals(id))
                .map(person -> (Beneficiary) person)
                .findFirst()
                .orElse(null);
    }

    public List<Beneficiary> getAllBeneficiaries() {
        return people.stream()
                .filter(person -> person instanceof Beneficiary)
                .map(person -> (Beneficiary) person)
                .collect(Collectors.toList());
    }

    // Aid Management 
    public void addAidItem(AidItem item) {
        if (item != null) {
            aidItems.add(item);
        }
    }

    public AidItem findAidById(String id) {
        return aidItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<AidItem> getAvailableAid() {
        Date now = new Date();
        return aidItems.stream()
                .filter(item -> item.getQuantity() > 0 &&
                        (item.getExpiryDate() == null || item.getExpiryDate().after(now)))
                .collect(Collectors.toList());
    }

    public List<AidItem> searchAid(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String lowerKeyword = keyword.toLowerCase();
        return aidItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    // Requests & Distribution 
    public boolean requestAid(Beneficiary b, AidItem item, int qty) {
        if (b == null || item == null || qty <= 0) {
            return false;
        }
        if (item.getQuantity() >= qty) {
            b.addRequest(item, qty);
            return true;
        }
        return false;
    }

    public boolean assignAidToBeneficiary(Beneficiary b) {
        if (b == null) {
            return false;
        }
        List<Request> requests = b.getRequests();
        if (requests.isEmpty()) {
            return false;
        }
        
        boolean assigned = false;
        List<Request> toRemove = new ArrayList<>();
        
        for (Request request : requests) {
            if (request != null) {
                AidItem item = request.getItem();
                int qty = request.getQuantity();
                if (item != null && item.getQuantity() >= qty) {
                    item.setQuantity(item.getQuantity() - qty);
                    distributions.add(new Distribution(b, item, qty, new Date()));
                    toRemove.add(request);
                    assigned = true;
                }
            }
        }
        
        // Remove processed requests
        toRemove.forEach(b::removeRequest);
        return assigned;
    }

    @Override
    public void assignTo(AidItem item, Beneficiary beneficiary) {
        if (item != null && beneficiary != null && item.getQuantity() > 0) {
            int qty = 1; // Default quantity
            if (requestAid(beneficiary, item, qty)) {
                assignAidToBeneficiary(beneficiary);
            }
        }
    }

    // Reports 
    public String generateDistributionReport() {
        if (distributions.isEmpty()) {
            return "No distributions yet.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=== Distribution Report ===\n");
        distributions.forEach(distribution -> 
            sb.append(distribution.toString()).append("\n")
        );
        return sb.toString();
    }

    // Getter for distributions list
    public List<Distribution> getDistributions() {
        return new ArrayList<>(distributions);
    }
}
