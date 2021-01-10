package org.drew.carcenter.api;

import lombok.extern.slf4j.Slf4j;
import org.drew.carcenter.data.models.User;
import org.drew.carcenter.data.models.dto.UserDTO;
import org.drew.carcenter.exceptions.UserExistException;
import org.drew.carcenter.exceptions.UserNotFoundException;
import org.drew.carcenter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1.0")
@Slf4j
@Controller
/**
 * The rest controller for modifying users
 */
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO params) {
        try {
            User user = userService.addUser(params);
            return new ResponseEntity<>(UserDTO.fromEntity(user), HttpStatus.OK);
        } catch (UserExistException e) {
            throw new ResponseStatusException(
                    HttpStatus.METHOD_NOT_ALLOWED,
                    String.format("A user already exist with the username %s", params.getUsername()), e);
        }
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO params, @PathVariable long userId) {
        try {
            User updatedUser = userService.updateUser(params, userId);
            return new ResponseEntity<>(UserDTO.fromEntity(updatedUser), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UserExistException e) {
            throw new ResponseStatusException(
                    HttpStatus.METHOD_NOT_ALLOWED,
                    String.format("A user already exist with the username %s", params.getUsername()), e);
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long userId) {
        try {
            User user = userService.getUserById(userId);
            return new ResponseEntity<>(UserDTO.fromEntity(user), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }
    }
}
