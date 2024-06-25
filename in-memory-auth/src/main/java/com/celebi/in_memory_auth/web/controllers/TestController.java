package com.celebi.in_memory_auth.web.controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController
{
    // Method 1 : Secured Annotation way
    // Method 2 : PreaAuthorize Annotation way
    // Method 3 : Using Secured Annotation with Hierarchy Model.

    //@Secured({"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
    //@PreAuthorize("hasAnyRole('ADMIN','USER','MODERATOR')")
    @Secured("ROLE_USER")
    @GetMapping("/user")
    public String getForUser()
    {
        return "User content.";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public String getForAdmin()
    {
        return "Admin content.";
    }

    //@PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    //@Secured({"ROLE_ADMIN", "ROLE_MODERATOR"})
    @GetMapping("/mod")
    @Secured("ROLE_MODERATOR")
    public String getForModerator()
    {
        return "Moderator content";
    }
}
