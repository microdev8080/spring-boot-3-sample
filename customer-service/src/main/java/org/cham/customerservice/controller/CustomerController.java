package org.cham.customerservice.controller;

import org.cham.customerservice.client.CustomerCoreClient;
import org.cham.customerservice.domain.CustomerCore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class CustomerController {
    @Autowired
    private CustomerCoreClient customerCoreClient;
    @GetMapping("/api/customers")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerCore> findAllCustomersFromCoreAPI(){
        log.info("Inside CustomerController.findAllCustomersFromCoreAPI()");
        List<CustomerCore> customerList = customerCoreClient.findAllCustomers();
        customerList.stream().forEach(t -> log.info(t.toString()));
        return customerList;
    }


    @GetMapping("/api/customers/customer")
    @ResponseStatus(HttpStatus.OK)
    public CustomerCore findCustomerFromCoreAPI(@RequestParam(required =true) String username){
        log.info("Inside CustomerController.findCustomerFromCoreAPI()");
        CustomerCore customer = customerCoreClient.findCustomerByUsername(username);
       log.info("received customer {} for username {}",customer,username);
        return customer;
    }

}
