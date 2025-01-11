package com.example.CartService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {
    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart() {
        return ResponseEntity.ok().body("Add to cart Successfully!");
    }
}
