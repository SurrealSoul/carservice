package org.drew.carcenter.services;

import org.drew.carcenter.data.models.User;
import org.drew.carcenter.data.repos.UserRepository;
import org.drew.carcenter.exceptions.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        User drew = new User();
        drew.setPhone("5133285317");
        drew.setLastName("davis");
        drew.setFirstName("drew");
        drew.setEmail("andrewcdavis0@gmail.com");
        drew.setUsername("andrewcdavis0");

        Mockito.when(userRepository.findUserById(10L))
                .thenReturn(drew);
    }

    @Test
    public void whenGetUser_thenDoNotThrowException() throws UserNotFoundException {
        User foundUser = userService.getUserById(10L);
        assertNotNull(foundUser);
        assertDoesNotThrow(() -> new UserNotFoundException("user not found"));
    }

    @Test
    public void whenGetUserNotFound_thenThrowUserNotFound() {
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> userService.getUserById(5L),
                "Expecting userService to throw an exception");

        assertTrue(thrown.getMessage().equals("user not found"));
    }

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }
}
