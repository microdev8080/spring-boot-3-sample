package com.cham.customerservice.controller;

import com.cham.customerservice.client.CustomerCoreClient;
import com.cham.customerservice.config.TestServiceInstance;
import com.cham.customerservice.domain.CustomerCore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
public class CustomerControllerIntegrationTest {

    @Autowired
    private CustomerCoreClient customerCoreClient;


    private static int port = 8762;

    CustomerCore rob = CustomerCore
            .builder()
            .id("12345679")
            .ssn("ssn-0023")
            .accountNumber("9876534")
            .firstName("Rob")
            .lastName("Atkins")
            .build();
    CustomerCore tony = CustomerCore
            .builder()
            .id("456789032")
            .ssn("ssn-987654")
            .accountNumber("67654387")
            .firstName("Tony")
            .lastName("Stark")
            .build();

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public ServiceInstanceListSupplier serviceInstanceListSupplier() {
            return new TestServiceInstance("customer-core", port);
        }
    }

    @RegisterExtension
    static WireMockExtension CUSTOMER_SERVICE = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(port))
            .build();

    @BeforeEach
    public void setup() throws JsonProcessingException {

        List<CustomerCore> customers = new ArrayList<>();
        customers.add(rob);
        customers.add(tony);
        ObjectMapper objectMapper = new ObjectMapper();
        CUSTOMER_SERVICE.stubFor(get(urlEqualTo("/api/customer-core"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(customers))));
    }

    @Test
    public void testFindAllCustomersFromCoreAPI() {
        // call CustomerController endpoint
        List<CustomerCore> customerList = customerCoreClient.findAllCustomers();

        // Assert response
        assertEquals(2, customerList.size());
        assertThat(rob).usingRecursiveComparison().isEqualTo(customerList.get(0));
        assertThat(tony).usingRecursiveComparison().isEqualTo(customerList.get(1));
    }
}
