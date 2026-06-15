package service;

import model.Customer;
import repository.CustomerRepository;

import java.util.List;

public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(
            CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer) {

        customerRepository.save(customer);
    }

    public Customer getCustomer(int id) {

        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }
}