package com.banulp.grpc.repository;

import com.banulp.grpc.model.SignUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class SignUserRepository {
    @Autowired
    ReactiveMongoTemplate template;

    public Mono<SignUser> findOneById(String id) {
        return template.findById(id, SignUser.class);
    }

    public <T> Mono<T> insert(T structure) {
        return template.insert(structure);
    }

}
