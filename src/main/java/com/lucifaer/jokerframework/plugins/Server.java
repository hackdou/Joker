package com.lucifaer.jokerframework.plugins;

import org.springframework.scheduling.annotation.Async;

public interface Server {
    @Async
    void createServer();

    void stopServer();

    String getServerUrl();

    String getServerPort();
}