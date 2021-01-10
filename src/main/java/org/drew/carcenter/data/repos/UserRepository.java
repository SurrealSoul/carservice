package org.drew.carcenter.data.repos;

import org.drew.carcenter.data.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserById(Long id);

    User save(User user);
}
