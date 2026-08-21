package se.lexicon.model;

public class BookingUser {

    private int id;
    private Customer customer;
    private String name;
    private String email;

    /* --- This constructor is used when creating a new booking user, the DB automatically generates the id --- */
    public BookingUser(Customer customer, String name, String email) {
        this.customer = customer;
        this.name = name;
        this.email = email;
    }

    /* --- This constructor is used when reading an existing booking user from the DB --- */
    public BookingUser(int id, Customer customer, String name, String email) {
        this(customer, name, email);
        this.id = id;
    }

    /* --- Getters & Setters --- */
    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
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

    /* --- Print info layout --- */
    @Override
    public String toString() {
        return String.format(
                "ID: %d | Name: %s | Email: %s",
                id, name, email
        );
    }
}
