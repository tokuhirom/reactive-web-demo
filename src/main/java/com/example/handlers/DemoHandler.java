package com.example.handlers;

import org.springframework.web.reactive.function.Request;
import org.springframework.web.reactive.function.Response;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class DemoHandler {
    public Response<String> helloWorld(Request request) {
        return Response.ok().body(fromObject("Hello World"));
    }


    public Response<String> theAnswer(Request request) {
        return Response.ok().body(fromObject("42"));
    }
}
