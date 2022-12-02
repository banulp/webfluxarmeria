package com.banulp.grpc.controller;

import com.banulp.grpc.model.SignUser;
import com.banulp.grpc.service.SignUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@Validated
public class SignUserController {

    @Autowired
    private SignUserService signUserService;

    @GetMapping("/sign/{uid}")
    public Mono<SignUser> getSignUser(
            @PathVariable(name = "uid")
            @Pattern(regexp = "^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$", message = "Odd UID")
            String uid) {
        return signUserService.get(uid);
    }

    @PostMapping("/sign/{uid}")
    public Mono<SignUser> postSignUser(
            @PathVariable(name = "uid")
            @Pattern(regexp = "^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$", message = "Odd UID")
            String uid) {
        return signUserService.insert(uid);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Object exception(Exception e) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", new Date());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.put("message", e.getMessage());
        return error;
    }
}

