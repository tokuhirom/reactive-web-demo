package com.example.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.Request;
import org.springframework.web.reactive.function.Response;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class JsonHandler {
    public Response<ResponseEntity> json(Request request) {
        return Response.ok().body(fromObject(new ResponseEntity("hello")));
    }

    public Response<String> pathVariableDemo(Request request) {
        String message = "Hello, " + request.pathVariable("name").orElse("Unknown");
        return Response.ok().body(fromObject(message));
    }

    public Response<String> queryParamDemo(Request request) {
        String message = "Hello, " + request.queryParam("name").orElse("Unknown");
        return Response.ok().body(fromObject(message));
    }

    public Response<String> uri(Request request) {
        return Response.ok().body(fromObject(request.uri().toASCIIString()));
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ResponseEntity {
        String thing;
    }
}
