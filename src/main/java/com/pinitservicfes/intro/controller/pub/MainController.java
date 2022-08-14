package com.pinitservicfes.intro.controller.pub;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("public/main")
public class MainController {

    @GetMapping
    public String test() {
        return "Success!!";
    }
}
