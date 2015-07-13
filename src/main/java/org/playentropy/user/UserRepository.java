package org.playentropy.user;

import org.springframework.data.domain.*;
import org.springframework.data.repository.*;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    Page<User> findAll(Pageable pageable);

    User findByUsername(String username);
}
