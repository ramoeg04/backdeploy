package com.soaint.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soaint.domain.Auth;
import com.soaint.service.ChatBotService;
import com.soaint.service.AuthService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/bot")
public class ChatBotController {

    private static final Logger logger = LoggerFactory.getLogger(ChatBotController.class);

    @Autowired
    private ChatBotService chatBotService;

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/login", produces = MediaType.TEXT_PLAIN_VALUE)
    public String login() {
        String token = authService.login();
        return token;
    }

    @GetMapping(value = "/botQuestion")
    public String bot(@RequestParam String question){
        String x = chatBotService.chatbotService(question);
        return x;
    }

}
