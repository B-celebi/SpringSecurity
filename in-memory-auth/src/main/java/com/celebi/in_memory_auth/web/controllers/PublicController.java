package com.celebi.in_memory_auth.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController
{
    @GetMapping()
    public String getSome()
    {
        return "Hello World!";
    }

    @GetMapping("/1")
    public String getSome1()
    {
        return "It's 1.";
    }

    @GetMapping("/2")
    public String getSome2()
    {
        return "It's 2.";
    }
}
