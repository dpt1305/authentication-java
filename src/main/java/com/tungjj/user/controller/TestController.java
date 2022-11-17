package com.tungjj.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
   @PostMapping(value = "/") 
   public String test() {
    return "Hello";
   }
}
