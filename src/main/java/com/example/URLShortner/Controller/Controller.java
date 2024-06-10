package com.example.URLShortner.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @PostMapping
    public String helloWorld(){
        return "<h1>Hello World</h1>";
    }

}
