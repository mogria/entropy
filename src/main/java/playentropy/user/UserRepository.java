package org.playentropy.user;

import org.springframework.data.domain.*;
import org.springframework.data.repository.*;

public interface UserRepository extends CrudRepository<User, String> {

    Page<User> findAll(Pageable pageable);

    User findByUsername(String username);
}
