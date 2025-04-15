package com.assignment1.clothes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @GetMapping(value = "/login")
    public String login() {
        LOG.info("/login");

        LOG.info("Return login");

        return "login";
    }
}
