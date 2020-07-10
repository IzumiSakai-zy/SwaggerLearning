package com.demo.controller;


import com.demo.entities.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public String index(){
        return "hello world";
    }

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public User user(){
        return new User();
    }
}
