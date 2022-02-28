package com.kenny.syncblocking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
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
        log.debug( "__KENNY__ getSyncBlocking() START : {}", second);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);

        final ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8093/remote-server/dummy/" + second
                , HttpMethod.GET
                , httpEntity
                , String.class
        );

        log.debug( "__KENNY__ responseEntity.getBody() : {}", responseEntity.getBody() );
    }
}
