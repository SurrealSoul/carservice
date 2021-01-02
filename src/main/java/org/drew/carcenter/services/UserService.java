package org.drew.carcenter.services;

import org.drew.carcenter.data.models.User;
import org.drew.carcenter.data.models.dto.UserDTO;
import org.drew.carcenter.exceptions.UserExistException;
import org.drew.carcenter.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserService
{
    User getUserById(Long id) throws UserNotFoundException;

    User addUser(UserDTO user) throws UserExistException;

    User updateUser(UserDTO user, long userId) throws UserExistException, UserNotFoundException;
}
