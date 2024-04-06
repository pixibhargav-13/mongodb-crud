package com.bhargav.mongodbcrud.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;

@Document
@Getter
@Setter
@Data
public class Employee {
    @Id
    private String id;
    private String name;
    private String designation;
    private double salary;
    @Indexed(unique = true)
    private String email;

    @Indexed(expireAfterSeconds = 40)
    private Date createdAt;

    public void populateCreatedAt(){
        this.createdAt = Date.from(Instant.now());
    }
 }
