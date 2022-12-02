package com.banulp.grpc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@NoArgsConstructor
@Document(collection = "userinfo")
public class SignUser {
//    @MongoId
    @Id
    private String id;
    private String uid;
    private Date created;

    public SignUser(String uid, Date created) {
        this.id = uid;
        this.uid = uid;
        this.created = created;
    }
}

