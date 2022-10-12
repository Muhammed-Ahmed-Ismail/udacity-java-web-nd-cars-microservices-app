package com.udacity.pricing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class TestPricingService {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    public void listAllPrices() {
        ResponseEntity<Object> response =
                testRestTemplate.getForEntity("http://localhost:" + port + "/prices/", Object.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

}
