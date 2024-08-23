package com.fh.scms.services.implement;

import com.fh.scms.pojo.Customer;
import com.fh.scms.repository.CustomerRepository;
import com.fh.scms.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CustomerServiceImplement implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer findById(Long id) {
        return this.customerRepository.findById(id);
    }

    @Override
    public Customer findByPhone(String phone) {
        return this.customerRepository.findByPhone(phone);
    }

    @Override
    public void save(Customer customer) {
        this.customerRepository.save(customer);
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
    public Long count() {
        return this.customerRepository.count();
    }

    @Override
    public List<Customer> findAllWithFilter(Map<String, String> params) {
        return this.customerRepository.findAllWithFilter(params);
    }
}
