package Stage1;

import java.util.ArrayList;
import java.util.List;

public class Beneficiary extends Person {
    private int familySize;
    private List<Request> requests;

    public Beneficiary(String id, String name, String password, String address, String phone, int familySize) {
        super(id, name, password, address, phone);
        this.familySize = familySize;
        this.requests = new ArrayList<>();
    }

    public int getFamilySize() {
        return familySize;
    }

    public void addRequest(AidItem item, int qty) {
        if (item != null && qty > 0) {
            requests.add(new Request(item, qty));
        }
    }

    public void removeRequest(Request request) {
        if (request != null) {
            requests.remove(request);
        }
    }

    public List<Request> getRequests() {
        return new ArrayList<>(requests);
    }

    @Override
    public String toString() {
        return "Beneficiary [id=" + getId() + ", name=" + getName() + ", familySize=" + familySize + "]";
    }
}
