package org.drew.carcenter.api;

import lombok.extern.slf4j.Slf4j;
import org.drew.carcenter.data.models.User;
import org.drew.carcenter.data.models.dto.UserDTO;
import org.drew.carcenter.exceptions.UserNotFoundException;
import org.drew.carcenter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0")
@Slf4j
@Controller

public class UserController
{
    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    /**
     * Creates a new user
     * @param params the information required for a user
     * @return the user object
     */
    @PostMapping("/users")
    public HttpStatus createUser(@RequestBody UserDTO params){
        log.info("Creating a new user {}", params.toString());
        try
        {
            userService.addUser(params);
            return HttpStatus.OK;
        }
        catch (UserNotFoundException e)
        {
            log.info("Username {} already exists", params.getUsername());
            return HttpStatus.METHOD_NOT_ALLOWED;
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<String> getUserById(@PathVariable long userId)
    {
        try {
            Optional<User> user = Optional.ofNullable(userService.getUserById(userId));
            return new ResponseEntity<>(user.toString(), HttpStatus.OK);
        }
        catch (UserNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
