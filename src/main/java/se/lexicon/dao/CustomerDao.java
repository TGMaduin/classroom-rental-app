package se.lexicon.dao;

import java.util.List;
import java.util.Optional;

import se.lexicon.model.Customer;

public interface CustomerDao {

    Customer save(Customer customer);

    Optional<Customer> findById(int id);

    List<Customer> findAll();
}