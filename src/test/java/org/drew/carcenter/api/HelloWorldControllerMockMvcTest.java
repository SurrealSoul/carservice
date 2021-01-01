package org.drew.carcenter.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This kind of test is useful to test the controller layer without the web layer (i.e. a unit test)
 */
@SpringBootTest
@AutoConfigureMockMvc
public class HelloWorldControllerMockMvcTest
{

    @Autowired
    private MockMvc mockMvc;

    private static final String HELLO_WORLD_URL = "/api/v1.0/hello-world";

    @Test
    public void shouldReturnDefaultMessage() throws Exception
    {
        this.mockMvc.perform(get(HELLO_WORLD_URL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Hello, World"));
    }

}
