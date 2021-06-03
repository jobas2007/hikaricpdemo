package com.example.hikaricpdemo.model.book;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@NoArgsConstructor
public class Book implements Serializable {
    @Id
    private Long bookId;

    private String title;

    private String author;
}
