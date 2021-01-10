package org.drew.carcenter.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0")
/**
 * Simple rest controller used to smoke test the application
 */
public class HelloWorldController {
    @GetMapping("/hello-world")
    public @ResponseBody
    String helloWorld() {
        return "Hello, World";
    }
}
