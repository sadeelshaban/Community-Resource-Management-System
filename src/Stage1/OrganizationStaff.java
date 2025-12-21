package Stage1;

public class OrganizationStaff extends Person {
    private String role;
    private String organizationName;

    public OrganizationStaff(String id, String name, String password, String address, String phone, String role, String organizationName) {
        super(id, name, password, address, phone);
        this.role = role;
        this.organizationName = organizationName;
    }

    public String getRole() {
        return role;
    }

    public String getOrganizationName() {
        return organizationName;
    }
}