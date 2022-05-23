package org.wildhamsters.battleships;

import java.time.LocalDateTime;

import org.springframework.web.client.RestTemplate;

class Logger {
    static void log(Log.Level level, Class<?> className, String msg) {
        RestTemplate restTemplate = new RestTemplate();
        Log log = new Log(level.toString(), LocalDateTime.now().toString(),
                "battleShips", className.getName(), msg);
        restTemplate.postForObject("http://64.225.104.111:8000/log", log, Log.class);
    }
}
