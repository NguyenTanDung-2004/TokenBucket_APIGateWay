package com.example.APIGateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.APIGateway.repository.CartClient;

import reactor.core.publisher.Mono;

@Service
public class CartService {
    @Autowired
    private CartClient cartClient;

    public Mono<ResponseEntity<String>> addToCart() {
        return cartClient.addToCart();
    }
}
