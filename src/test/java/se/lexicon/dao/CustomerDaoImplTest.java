package se.lexicon.dao;

import org.junit.jupiter.api.Test;
import se.lexicon.model.Customer;
import se.lexicon.model.CustomerType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDaoImplTest {

    private final CustomerDao customerDao = new CustomerDaoImpl();

    @Test
    void shouldSaveCustomerAndReturnGeneratedId() {
        Customer customer = new Customer(
                "Test Customer",
                "test@example.com",
                "0701234567",
                CustomerType.INDIVIDUAL,
                null
        );

        Customer savedCustomer = customerDao.save(customer);

        assertTrue(savedCustomer.getId() > 0);
    }

    @Test
    void shouldFindCustomerById() {
        Customer customer = new Customer(
                "Find By Id Customer",
                "find@example.com",
                "0701112233",
                CustomerType.COMPANY,
                "556123-4567"
        );

        Customer savedCustomer = customerDao.save(customer);

        Optional<Customer> result =
                customerDao.findById(savedCustomer.getId());

        assertTrue(result.isPresent());

        Customer foundCustomer = result.get();

        assertEquals(savedCustomer.getId(), foundCustomer.getId());
        assertEquals(customer.getName(), foundCustomer.getName());
        assertEquals(customer.getEmail(), foundCustomer.getEmail());
        assertEquals(customer.getPhone(), foundCustomer.getPhone());
        assertEquals(customer.getType(), foundCustomer.getType());
        assertEquals(customer.getOrganizationNumber(),
                foundCustomer.getOrganizationNumber());
    }

    @Test
    void shouldReturnEmptyOptionalWhenCustomerDoesNotExist() {
        Optional<Customer> result =
                customerDao.findById(Integer.MAX_VALUE);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindAllCustomers() {
        Customer customer = new Customer(
                "Find All Customer",
                "all@example.com",
                "0709998877",
                CustomerType.INDIVIDUAL,
                null
        );

        Customer savedCustomer = customerDao.save(customer);

        List<Customer> customers = customerDao.findAll();

        assertFalse(customers.isEmpty());

        assertTrue(
                customers.stream()
                        .anyMatch(c ->
                                c.getId() == savedCustomer.getId())
        );
    }
}