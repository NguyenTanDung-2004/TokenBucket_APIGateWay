package com.example.APIGateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.example.APIGateway.repository.ApiGatewayClient;
import com.example.APIGateway.repository.CartClient;
import com.example.APIGateway.repository.IdentityClient;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient identityWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080/identity")
                .build();
    }

    @Bean
    public IdentityClient identityClient(WebClient identityWebClient) {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(identityWebClient)).build();

        return httpServiceProxyFactory.createClient(IdentityClient.class);
    }

    @Bean
    public WebClient apiGatewayWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8888") // API Gateway base URL
                .build();
    }

    @Bean
    public ApiGatewayClient apiGatewayClient(WebClient apiGatewayWebClient) {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(apiGatewayWebClient)).build();

        return httpServiceProxyFactory.createClient(ApiGatewayClient.class);
    }

    @Bean
    public WebClient cartServiceWebClient() { // Renamed from cartWebClient to cartServiceWebClient
        return WebClient.builder()
                .baseUrl("http://localhost:8081/cart")
                .build();
    }

    @Bean
    public CartClient cartClient(WebClient cartServiceWebClient) { // Renamed parameter to match the renamed method
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(cartServiceWebClient)).build();

        return httpServiceProxyFactory.createClient(CartClient.class);
    }
}
