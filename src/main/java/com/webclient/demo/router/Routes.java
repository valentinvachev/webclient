package com.webclient.demo.router;


import com.webclient.demo.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> router(ProductHandler productHandler) {
        return route().GET("getProducts",productHandler::getAllProducts).build();
    }
}
