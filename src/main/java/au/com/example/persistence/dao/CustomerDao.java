package au.com.example.persistence.dao;

import au.com.example.api.model.customer.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> getCustomers();

    Customer addCustomer(Customer customer);

    boolean deleteCustomer(Long id);

    boolean saveCustomer(Customer customer);
}
