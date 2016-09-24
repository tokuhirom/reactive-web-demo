package com.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleException;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import reactor.ipc.netty.http.HttpServer;

@Slf4j
public class ReactorServerApplication {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) throws LifecycleException, InterruptedException {
        HttpHandler httpHandler = HttpHandlerFactory.build();
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer server = HttpServer.create(HOST, PORT);
        server.startAndAwait(adapter);
    }

}
