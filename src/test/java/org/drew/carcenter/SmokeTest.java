package org.drew.carcenter;

import org.drew.carcenter.api.HelloWorldController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
/**
 * Just a simple smoke test to confirm the application can run
 */
public class SmokeTest {

    @Autowired
    HelloWorldController helloWorldController;

    @Test
    public void contextLoads() {
        assertNotNull(helloWorldController);
    }
}
