package com.kenny.syncblocking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SyncBlockingController {

    private final RestTemplate restTemplate;

    @GetMapping("/sync-blocking/delay/{second}")
    public void getSyncBlocking(@PathVariable("second") String second) {
        log.debug( "__KENN__ getSyncBlocking() START : {}", second);

//        new HttpEntity<>()

//        restTemplate.exchange( "/remote-server/delay/" + second
//                , HttpMethod.GET
//                ,
//                , String.class
//        );
    }
}
