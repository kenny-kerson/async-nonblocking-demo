# Sync, Async, Blocking, Non-Blocking
- 동기, 비동기, 블럭킹, 넌블럭킹을 이해하기 위한 데모 프로젝트

## # TL;DR
- Async-Nonblocking이 가장 성능이 뛰어남  
- Async Blocking과 Nonblocking은 쓰레드수는 차이가 안났지만, latency 차이가 확연히 발생했음
- 동기, 비동기, 블럭킹, 넌블럭킹 정리내용 : https://bit.ly/3JYU8S0  
( 위 결과는 단순히 active thread수만을 지표로 판단한 결과임 )

## # 모듈구성
### 1. sync-blocking
- 동기 & 블럭킹 API를 제공하는 모듈
- Spring MVC & Tomcat
- RestTemplate + DBIO( 더미 )의 조합을 사용

### 2. async-nonblocking
- 비동기 & 넌블럭킹 API와 비동기 & 블럭킹 API를 제공하는 모듈
- Spring WebFlux + Netty
- WebClient + Mono를 통해 비동기 & 넌블럭킹 제공
- WebClient + Mono + DBIO( 더미 )을 통해 비동기 & 블럭킹 제공 

### 3. remote-server
- 1/2번에서 호출하는 외부API를 제공하는 서버
- Thread.sleep()을 통해, delay를 발생 
- Spring MVC + Tomcat

## # API 구성
### sync-blocking
1. http://localhost:8091/sync-blocking/delay/{second}
- Network IO + DB IO

### async-nonblocking
2. http://localhost:8092/async-nonblocking/delay/{second}
- Network IO

3. http://localhost:8092/async-blocking/delay/{second}
- Network IO + DB IO

### remote-server
4. http://localhost:8093/remote-server/dummy/{second}


## # 성능테스트
- 부하발생기 : jmeter

### Sync-Blocking vs Async-Nonblocking
- concurrency : 200 user
- ramp-up time : 1s
- loop : infinite
- Active Thread count : 247 vs 53

### Sync-Blocking vs Async-Blocking
- concurrency : 200 user
- ramp-up time : 1s
- loop : infinite
- Active Thread count : 247 vs 53

### Async-Blocking vs Async-Nonblocking
- concurrency : 1000 user
- ramp-up time : 1s
- loop : infinite
- Active Thread count : 53 vs 53
  - 왜 여기서 쓰레드수는 더 증가하지 않을까??
- Latency : avg 15934ms vs 5509ms 


### 성능모니터링 Unix Command
- 23672의 쓰레드수 1초마다 모니터링 : `watch -n 1 'ps -p 23672 -M | wc -l'`