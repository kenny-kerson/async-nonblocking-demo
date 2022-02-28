package com.kenny.asyncnonblocking.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Random;

@Slf4j
@Repository
public class SimpleRepository {

    /*
     * Blocking이 걸리는 DBIO를 가정한다.
     */
    public void findAll() throws InterruptedException {
        final Random random = new Random();
        final int delay = random.nextInt(5) % 3;

        Thread.sleep(1000 * delay);

        log.debug( "__KENNY__ findAll() : {}", delay);
    }
}
