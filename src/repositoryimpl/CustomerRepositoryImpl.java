package repositoryimpl;

import model.Customer;
import repository.CustomerRepository;

import java.util.*;

public class CustomerRepositoryImpl
        implements CustomerRepository {

    private Map<Integer, Customer> customers
            = new HashMap<>();

    @Override
    public void save(Customer customer) {

        customers.put(customer.getCustomerId(), customer);
    }

    @Override
    public Customer findById(int id) {

        return customers.get(id);
    }

    @Override
    public boolean exists(int id) {

        return customers.containsKey(id);
    }

    @Override
    public List<Customer> findAll() {

        return new ArrayList<>(customers.values());
    }

    @Override
    public void delete(int id) {

        customers.remove(id);
    }
}
