package controller;

import model.Customer;
import model.Branch;
import service.CustomerService;

public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void createCustomer(
            int id,
            String name,
            String username,
            String password,
            Branch branch) {

        customerService.registerCustomer(
                id, name, username, password, branch);

        System.out.println("Customer created successfully");
    }

    public void viewCustomer(int id) {

        Customer customer =
                customerService.getCustomer(id);

        if (customer == null) {
            System.out.println("Customer not found");
        } else {
            System.out.println(customer.getName());
        }
    }
}
