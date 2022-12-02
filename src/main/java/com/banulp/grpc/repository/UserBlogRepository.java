package com.banulp.grpc.repository;

import com.banulp.grpc.model.UserBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserBlogRepository {
    @Autowired
    ReactiveMongoTemplate template;

    public Mono<UserBlog> findOneById(String id) {
        return template.findById(id, UserBlog.class);
    }

    public <T> Mono<T> insert(T structure) {
        return template.insert(structure);
    }

}
