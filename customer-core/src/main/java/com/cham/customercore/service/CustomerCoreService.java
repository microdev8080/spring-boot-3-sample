package com.cham.customercore.service;

import com.cham.customercore.domain.CustomerCore;
import com.cham.customercore.repository.CustomerCoreRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCoreService {
    @Autowired
    private CustomerCoreRepository customerCoreRepository;
    public Optional<CustomerCore> findCustomerById(String id){
        Optional<CustomerCore> customer;
        try {
            customer = customerCoreRepository.findById(id);
        }catch(DataAccessException e){
            log.error("Exception while accessing Redis", e);
            throw  new RuntimeException("Exception while accessing Redis",e);
        }
        return customer;
    }

    public Optional<CustomerCore> findCustomerByFirstName(String firstName){
        Optional<CustomerCore> customer;
        try {
            customer = customerCoreRepository.findByFirstName(firstName);
        }catch(DataAccessException e){
            log.error("Exception while accessing Redis", e);
            throw  new RuntimeException("Exception while accessing Redis",e);
        }
        return customer;
    }

    public Optional<CustomerCore> findCustomerBySsn(String ssn){
        Optional<CustomerCore> customer;
        try {
            customer = customerCoreRepository.findBySsn(ssn);
        }catch(DataAccessException e){
            log.error("Exception while accessing Redis", e);
            throw  new RuntimeException("Exception while accessing Redis",e);
        }
        return customer;
    }
    public List<CustomerCore> findAllCustomers(){
       List<CustomerCore> customerList;
        try {
            customerList = customerCoreRepository.findAll();
        }catch(DataAccessException e){
            log.error("Exception while accessing Redis", e);
            throw  new RuntimeException("Exception while accessing Redis",e);
        }
        return customerList;
    }

    public String saveCustomer(CustomerCore customerCore){
        CustomerCore returnedCustomer;
        try{
            returnedCustomer = customerCoreRepository.save(customerCore);
        }catch(DataAccessException e){
            log.error("Exception while accessing Redis", e);
            throw  new RuntimeException("Exception while accessing Redis",e);
        }
        return returnedCustomer.getId();
    }
}
