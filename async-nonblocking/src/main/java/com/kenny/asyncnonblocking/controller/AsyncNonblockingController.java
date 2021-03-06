package com.kenny.asyncnonblocking.controller;

import com.kenny.asyncnonblocking.repository.SimpleRepository;
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
    private final SimpleRepository simpleRepository;

    /**
     * Async-Nonblocking 테스트 API
     */
    @GetMapping("/async-nonblocking/delay/{second}")
    public Mono<String> getAsyncNonblocking(@PathVariable("second") final String second ) {
        log.debug( "__KENNY__ getAsyncNonblocking() START : {}", second);

        // WebClient + Mono = Async + NonBlockingk
        final Mono<String> responseMono = webClient.get()
                .uri("/remote-server/dummy/" + second)
                .retrieve()
                .bodyToMono(String.class);

        log.debug( "__KENNY__ webClient call completed!! responseMono : {}", responseMono );

        responseMono.subscribe(System.out::println);

        return responseMono;
    }


    /**
     * Async-Blocking 테스트 API
     *    ㄴ Http Request는 NonBlocking
     *    ㄴ DBIO는 Blocking
     */
    @GetMapping("/async-blocking/delay/{second}")
    public Mono<String> getAsyncBlocking(@PathVariable("second") final String second ) throws InterruptedException {
        log.debug( "__KENNY__ getAsyncNonblocking() START : {}", second);

        // WebClient + Mono = Async + NonBlocking
        final Mono<String> responseMono = webClient.get()
                .uri("/remote-server/dummy/" + second)
                .retrieve()
                .bodyToMono(String.class)
        ;

        responseMono.subscribe(System.out::println);

        log.debug( "__KENNY__ webClient call completed!! responseMono : {}", responseMono );

        // Blocking IO 호출
        simpleRepository.findAll();

        return responseMono;
    }
}
