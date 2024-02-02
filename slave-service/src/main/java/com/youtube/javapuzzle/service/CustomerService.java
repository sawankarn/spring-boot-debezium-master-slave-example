package com.youtube.javapuzzle.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtube.javapuzzle.entity.Customer;
import com.youtube.javapuzzle.repository.CustomerRepository;
import io.debezium.data.Envelope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(new Customer());
    }

    public Customer createCustomer(Customer customer) {
        // You can add additional validation or business logic here
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        // You can add additional validation or business logic here
        if (customerRepository.existsById(id)) {
            updatedCustomer.setId(id);
            return customerRepository.save(updatedCustomer);
        }
        return new Customer(); // or throw an exception indicating not found
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public void replicateData(Map<String, Object> customerData, Envelope.Operation operation) {
        final ObjectMapper mapper = new ObjectMapper();
        final Customer customer = mapToCustomer(customerData);
        System.out.println(customer);
        if (Envelope.Operation.DELETE == operation) {
            System.out.println("delete the record");
            customerRepository.deleteById(customer.getId());
        } else {
            System.out.println("inserting the record");
            customerRepository.save(customer);
        }
    }

    public static Customer mapToCustomer(Map<String, Object> map) {
        Customer customer = new Customer();

        if (map.containsKey("id") && map.get("id") instanceof Number) {
            customer.setId(((Number) map.get("id")).longValue());
        }

        if (map.containsKey("full_name") && map.get("full_name") instanceof String) {
            customer.setFullName((String) map.get("full_name"));
        }

        if (map.containsKey("email") && map.get("email") instanceof String) {
            customer.setEmail((String) map.get("email"));
        }

        return customer;
    }
}
