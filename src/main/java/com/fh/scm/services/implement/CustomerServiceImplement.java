package com.fh.scm.services.implement;

import com.fh.scm.pojo.Customer;
import com.fh.scm.repository.CustomerRepository;
import com.fh.scm.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImplement implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer get(Long id) {
        return this.customerRepository.get(id);
    }

    @Override
    public Customer getByPhone(String phone) {
        return this.customerRepository.getByPhone(phone);
    }

    @Override
    public void insert(Customer customer) {
        this.customerRepository.insert(customer);
    }

    @Override
    public void update(Customer customer) {
        this.customerRepository.update(customer);
    }

    @Override
    public void delete(Long id) {
        this.customerRepository.delete(id);
    }

    @Override
    public void softDelete(Long id) {
        this.customerRepository.softDelete(id);
    }

    @Override
    public Long count() {
        return this.customerRepository.count();
    }

    @Override
    public List<Customer> getAll(Map<String, String> params) {
        return this.customerRepository.getAll(params);
    }
}
