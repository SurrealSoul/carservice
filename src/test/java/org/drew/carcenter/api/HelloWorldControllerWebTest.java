package org.drew.carcenter.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This kind of test is useful for testing with a web layer (i.e. an integration or functional test)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloWorldControllerWebTest {
    private static final String HELLO_WORLD_URL = "/api/v1.0/hello-world";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void helloWorldShouldReturnDefaultMessage() {
        String response = restTemplate.getForObject("http://localhost:" + port + HELLO_WORLD_URL, String.class);
        assertEquals("Hello, World", response);
    }

}
