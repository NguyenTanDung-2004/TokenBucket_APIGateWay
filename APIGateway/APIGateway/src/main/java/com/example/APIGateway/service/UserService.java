package com.example.APIGateway.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.APIGateway.repository.ApiGatewayClient;
import com.example.APIGateway.repository.IdentityClient;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public UserService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public ResponseEntity<String> addUserToken(String userId) {
        // Check if the user already exists in Redis
        Map<Object, Object> userTokens = redisTemplate.opsForHash().entries("usertoken:" + userId);
        // If the user does not exist, create a new user with a token of 1
        if (userTokens == null || userTokens.isEmpty()) {
            System.out.println("Create user successfully!");
            Map<String, Object> newUserTokens = new HashMap<>();
            newUserTokens.put("userId", userId);
            newUserTokens.put("token", 1);
            redisTemplate.opsForHash().putAll("usertoken:" + userId, newUserTokens);
            return ResponseEntity.ok().body("User created successfully with token set to 1!");
        } else {
            // If the user exists, get the current token value
            Integer currentToken = (Integer) userTokens.get("token");
            Integer integer = new Integer(1);
            // Check if the current token is less than 5
            if (currentToken < 5) {
                // Increment the token by 1
                Map<String, Object> newUserTokens = new HashMap<>();
                newUserTokens.put("userId", userId);
                newUserTokens.put("token", currentToken + 1);
                redisTemplate.opsForHash().putAll("usertoken:" + userId, newUserTokens);
                System.out.println("Add token to bucket successfully! The number of tokens is " + (currentToken + 1));
                return ResponseEntity.ok().body("Token incremented successfully!");
            } else {
                System.out.println("Add token to bucket fail! The number of tokens is 5");
                return ResponseEntity.ok().body("Token limit reached. Current token: " + currentToken);
            }
        }
    }

    public ResponseEntity<Map<String, Object>> getNumberUserToken(String userId) {
        Map<Object, Object> userTokens = redisTemplate.opsForHash().entries("usertoken:" + userId);
        Integer currentToken = (Integer) userTokens.get("token");

        // Create response
        Map<String, Object> response = new HashMap<>();
        response.put("Code", 1000);
        response.put("Message", "found " + currentToken + " in bucket");

        if (currentToken <= 0) {
            response.put("Code", 1001);
            response.put("Message", "Not found token in bucket");
            response.put("TimeBack", "6s");
        }

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<Map<String, Object>> deleteToken(String userId) {
        // Check if the user already exists in Redis
        Map<Object, Object> userTokens = redisTemplate.opsForHash().entries("usertoken:" + userId);
        Integer currentToken = (Integer) userTokens.get("token");
        int token = 0;
        if (currentToken > 0) {
            Map<String, Object> newUserTokens = new HashMap<>();
            newUserTokens.put("userId", userId);
            newUserTokens.put("token", currentToken - 1);
            redisTemplate.opsForHash().putAll("usertoken:" + userId, newUserTokens);
            token = currentToken - 1;
        }
        Map<String, Object> response = new HashMap<>();
        response.put("Code", 1000);
        response.put("Token", token);
        response.put("Message", "Remove token successfully!");
        System.out.println(response);
        return ResponseEntity.ok().body(response);

    }

    @Autowired
    private ApiGatewayClient apiGatewayClient;

    public Mono<ResponseEntity> addToken(String userId) {
        return apiGatewayClient.addToken(userId);
    }

    public Mono<ResponseEntity<Map<String, Object>>> checkToken(String userId) {
        return apiGatewayClient.getNumberOfToken(userId);
    }

    public Mono<ResponseEntity<Map<String, Object>>> deleteTokenMono(String userId) {
        return apiGatewayClient.deleteToken(userId);
    }
}
