package com.banulp.grpc.service;

import com.banulp.grpc.model.SignUser;
import com.banulp.grpc.repository.SignUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class SignUserService {
    @Autowired
    private SignUserRepository signUserRepository;

    public Mono<SignUser> get(String uid) {
        return signUserRepository.findOneById(uid)
                .switchIfEmpty(Mono.just(new SignUser(uid, null)));
    }

    public Mono<SignUser> insert(String uid) {
        return signUserRepository.insert(new SignUser(uid, new Date()));
    }

}
