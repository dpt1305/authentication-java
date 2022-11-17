package com.tungjj.user.repository.accountRepository;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private ObjectId _id;
    private String username;
    private String password;
    private String name;
    private int age;
    private Date createdAt;
    private Date updatedAt;
    private String jwt;
    private String _2FA;
    private boolean deleted;
}
