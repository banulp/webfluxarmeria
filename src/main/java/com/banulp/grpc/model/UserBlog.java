package com.banulp.grpc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@AllArgsConstructor
@Document(collection = "userblog")
public class UserBlog {
//    @MongoId
    @Id
    private String id;
    private String title;
    private String content;
    private Date createdAt;
    private Date modifiedAt;

}

