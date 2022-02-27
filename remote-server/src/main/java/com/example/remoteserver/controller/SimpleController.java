package com.example.remoteserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SimpleController {

    @GetMapping("/remote-server/dummy/{second}")
    public void getDummyResponse(@PathVariable("second") String second) throws InterruptedException {
        log.debug( "__KENNY__ [remote-server] getDummyResponse {} second delay!!", second);

        Thread.sleep(Long.parseLong(second)*1000L);
    }
}
