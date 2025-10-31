// PublicPageController.java
package com.example.cabs.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicPageController {
    @GetMapping("/public/register")
    public String registerForm() { return "public/register"; }

    @GetMapping("/public/register-success")
    public String registerSuccess() { return "public/register-success"; }

    @GetMapping("/public/contact")
    public String contact() { return "public/contact"; }
}
