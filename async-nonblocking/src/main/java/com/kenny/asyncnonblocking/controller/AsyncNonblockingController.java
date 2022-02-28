package com.kenny.asyncnonblocking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AsyncNonblockingController {

    private final WebClient webClient;

    @GetMapping("/async-nonblocking/delay/{second}")
    public Mono<String> getAsyncNonblocking(@PathVariable("second") final String second ) {
        log.debug( "__KENNY__ getAsyncNonblocking() START : {}", second);

        final Mono<String> responseMono = webClient.get()
                .uri("/remote-server/dummy/" + second)
                .retrieve()
                .bodyToMono(String.class);

        log.debug( "__KENNY__ webClient call completed!! responseMono : {}", responseMono );

        responseMono.subscribe(System.out::println);

        return responseMono;
    }


    @GetMapping("/async-nonblocking/delay2/{second}")
    public Mono<String> getAsyncNonblocking2(@PathVariable("second") final String second ) throws ExecutionException, InterruptedException {
        log.debug( "__KENNY__ getAsyncNonblocking() START : {}", second);

        final CompletableFuture<Mono<String>> cf = CompletableFuture.supplyAsync(() -> {
            final Mono<String> responseMono = webClient.get()
                    .uri("/remote-server/dummy/" + second)
                    .retrieve()
                    .bodyToMono(String.class);

            log.debug("__KENNY__ webClient call completed!! responseMono : {}", responseMono);

            return responseMono;
        });

        log.debug( "__KENNY__ CompletableFuture.supplyAsync() done!!");

        final Mono<String> stringMono = cf.get();

        log.debug( "__KENNY__ CompletableFuture get() done!!");

        stringMono.subscribe(System.out::println);
//        final String response = stringMono.block();
//
//        log.debug( "__KENNY__ mono block() done!! : {}", response);
//
//        return response;
        return stringMono;
    }
}
