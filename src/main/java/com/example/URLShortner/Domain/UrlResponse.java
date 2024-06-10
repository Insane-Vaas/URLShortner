package com.example.URLShortner.Domain;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "URL")
@Data
public class UrlResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "LongURL")
    private String longURL;
    @Column(name = "ShortURL")
    private String shortURL;

}
