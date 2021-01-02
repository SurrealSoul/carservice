package org.drew.carcenter.services;

import org.drew.carcenter.data.models.User;
import org.drew.carcenter.data.models.dto.UserDTO;
import org.drew.carcenter.data.repos.UserRepository;
import org.drew.carcenter.exceptions.UserExistException;
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
    public User addUser(UserDTO user) throws UserExistException {
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
            throw new UserExistException("There is already a user with the same user name");
        }

        return newUser;
    }

    @Override
    public User updateUser(UserDTO user, long userId) throws UserNotFoundException, UserExistException
    {
        // check if the user id is already present, do not update if there does not exist a user
        if (!userRepository.findById(userId).isPresent())
        {
            throw new UserNotFoundException("user not found");
        }
        else
        {
            try
            {
                // new details of the user
                User newUser = User.builder()
                        .id(userId)
                        .phone(user.getPhone())
                        .lastName(user.getLastName())
                        .firstName(user.getFirstName())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build();
                userRepository.save(newUser);
                return newUser;
            }
            catch (Exception e)
            {
                throw new UserExistException("There is already a user with the same user name");
            }
        }
    }
}
