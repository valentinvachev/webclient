package com.webclient.demo.proxy;

import com.webclient.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProxyClient {

    private final WebClient webClient;

    @Autowired
    public ProxyClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<Product> getProducts() {
        return webClient.get().uri("/all")
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Product.class))
//                .onErrorResume(throwable -> Mono.just(new Product(0,"error","errorDesc"))); // Return this if has error. Not recommended
//                .onErrorResume(WebClientRequestException.class,e->Mono.just(new Product(0,"error","errorDesc"))); // Only on specific class. This is the recommended
//                .onErrorResume(e->e.getMessage().equals("custom message"),e->Mono.just(new Product(0,"error","errorDesc"))); // The case with predicate
//                .onErrorReturn(new Product(0,"error","errorDesc"));
//                  .onErrorMap(e->new IllegalStateException("Not valid")); //Way to map to another exception
                .doOnNext(n->{
                    if (n.getName().equals("Fist")) {
                        throw new IllegalStateException("Custom Error");
                    }
                })
                .retry(4)
                .onErrorContinue((e,o)-> System.out.println(e.getMessage())); // Working with the exception and the object which causes the error
//                  .retry(4); // retry with number of time;
    }

}
