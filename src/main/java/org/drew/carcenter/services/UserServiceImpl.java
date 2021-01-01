package org.drew.carcenter.services;

import org.drew.carcenter.data.models.User;
import org.drew.carcenter.data.models.dto.UserDTO;
import org.drew.carcenter.data.repos.UserRepository;
import org.drew.carcenter.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService
{
    final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent())
        {
            return user.get();
        }
        else
        {
            throw new UserNotFoundException("user not found");
        }
    }

    @Override
    public User addUser(UserDTO user) throws UserNotFoundException {
        User newUser = User.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .phone(user.getPhone())
                .build();
        try
        {
            userRepository.save(newUser);
        }
        catch (Exception e)
        {
            throw new UserNotFoundException("There is already a user with the same user name");
        }

        return newUser;
    }
}
