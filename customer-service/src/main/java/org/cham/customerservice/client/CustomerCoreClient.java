package org.cham.customerservice.client;

import org.cham.customerservice.domain.CustomerCore;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "customer-core")
public interface CustomerCoreClient {

    @GetMapping("/api/customer-core")
    List<CustomerCore> findAllCustomers();

    @GetMapping("/api/customer-core/username/{username}")
    CustomerCore findCustomerByUsername(@PathVariable("username") String username);

}
