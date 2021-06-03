package com.example.hikaricpdemo.model.owner;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Owner {
    @Id
    @GeneratedValue
    private Long ownerId;

    private String ownerName;

    private Long bookId;
}
