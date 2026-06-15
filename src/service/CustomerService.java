package service;

import model.Customer;
import repository.CustomerRepository;

import java.util.List;

public class CustomerService {

    private CustomerRepository customerRepository;

    private AuthenticationService authenticationService;

    public CustomerService(
            CustomerRepository customerRepository,
            AuthenticationService authenticationService) {

        this.customerRepository = customerRepository;
        this.authenticationService = authenticationService;
    }

    public void createCustomer(Customer customer) {

        customerRepository.save(customer);
    }

    public Customer registerCustomer(
            int customerId,
            String fullName,
            String userId,
            String password,
            model.Branch branch) {

        if (customerRepository.exists(customerId)) {
            throw new IllegalArgumentException("Customer ID already exists");
        }

        Customer customer = new Customer(
                customerId,
                fullName,
                userId,
                null,
                branch);

        authenticationService.register(customer, password);
        customerRepository.save(customer);
        return customer;
    }

    public Customer getCustomer(int id) {

        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }
}
