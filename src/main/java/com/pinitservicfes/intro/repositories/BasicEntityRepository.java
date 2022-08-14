package com.pinitservicfes.intro.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.pinitservicfes.intro.model.BasicEntity;

public interface BasicEntityRepository<T extends BasicEntity> extends MongoRepository<T, String> {

    @Query("""
            {"_id": ?0, "lastUpdate" : {$gt: ?1}}
            """)
    T findIfNewer(String id, long date);

    @Query("{creationDate: {$gte: ?0}")
    long countByCreationDate(long creationDate);
}
