package se.lexicon.model;

public class Customer {

    private int id;
    private  String name;
    private  String email;
    private  String phone;
    private CustomerType type;
    private String organizationNumber;

    /* --- This constructor is used when creating a new customer, the DB automatically generated the id --- */
    public Customer(String name, String email, String phone, CustomerType type, String organizationNumber) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.type = type;
        this.organizationNumber = organizationNumber;
    }

    /* --- This constructor is used when reading an existing customer from the DB --- */
    public Customer(int id, String name, String email, String phone, CustomerType type, String organizationNumber) {
        this(name, email, phone, type, organizationNumber);
        this.id = id;
    }

    /* --- Getters & Setters --- */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    public String getOrganizationNumber() {
        return organizationNumber;
    }

    public void setOrganizationNumber(String organizationNumber) {
        this.organizationNumber = organizationNumber;
    }

    /* --- Print info layout --- */
    @Override
    public String toString() {
        return String.format(
                "ID: %d | Name: %s | Email: %s | Phone: %s | Type: %s",
                id, name, email, phone, type
        );
    }
}
