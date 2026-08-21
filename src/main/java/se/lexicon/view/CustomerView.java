package se.lexicon.view;

public class CustomerView {

    /* --- Create a new customer based on the information provided by the user --- */
    public Customer getCustomerInput(){

        String name = IO.readln("Name: ");
        String email = IO.readln("Email: ");
        String phone = IO.readln("Phone: ");
        String type = IO.readln("Customer type (COMPANY/INDIVIDUAL): ");
        String organizationNumber = IO.readln("Organization number (optional): ");

        return new Customer(name, email, phone, CustomerType.valueOf(type.toUpperCase()), organizationNumber.isBlank() ? null : organizationNumber);
    }

    /* --- Displays all the existing customers --- */
    public void displayCustomers(List<Customer> customers){
        if(customers.isEmpty()){
            IO.println("❌ No customers found.");
            return;
        }
        customers.forEach(IO::println);
    }
}
