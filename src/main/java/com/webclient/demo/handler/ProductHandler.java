package com.webclient.demo.handler;

import com.webclient.demo.entity.Product;
import com.webclient.demo.proxy.ProxyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ProductHandler {

    private final ProxyClient proxyClient;

    @Autowired
    public ProductHandler(ProxyClient proxyClient) {
        this.proxyClient = proxyClient;
    }

    public Mono<ServerResponse> getAllProducts(ServerRequest request) {
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(proxyClient.getProducts(), Product.class);
    }
}
