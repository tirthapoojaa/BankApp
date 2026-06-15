package repository;

import model.Customer;

import java.util.List;

public interface CustomerRepository {

    void save(Customer customer);

    Customer findById(int id);

    List<Customer> findAll();

    void delete(int id);
}