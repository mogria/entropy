package org.playentropy.map;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface MapRepository extends MongoRepository<Map, String> {
    Page<Map> findAll(Pageable pageable);

    Map findByName(String name);
}
