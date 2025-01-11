package com.example.APIGateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import com.example.APIGateway.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // Define a scheduled task to run automatically after a fixed delay
    @Scheduled(fixedDelay = 6000) // Delay in milliseconds (6000 ms = 6 seconds)
    public void addUserTokenAutomatically() {
        String userId = "4567"; // Replace with logic to fetch a userId if needed
        this.userService.addUserToken(userId);
    }

    @GetMapping("/getNumberTokens")
    public ResponseEntity<Map<String, Object>> getNumberOfTokens(@RequestParam(name = "userId") String userId) {
        return this.userService.getNumberUserToken(userId);
    }

    @PostMapping("/deleteToken")
    public ResponseEntity<Map<String, Object>> deleteToken(@RequestParam(name = "userId") String userId) {
        return this.userService.deleteToken(userId);
    }

}
