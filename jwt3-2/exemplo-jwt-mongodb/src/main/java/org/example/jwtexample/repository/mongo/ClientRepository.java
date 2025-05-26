package org.example.jwtexample.repository.mongo;

import org.example.jwtexample.model.mongo.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {
    // MongoRepository<Client, String>
}