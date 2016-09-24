package com.example.handlers;

import org.springframework.web.reactive.function.Request;
import org.springframework.web.reactive.function.Response;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

public class JsonHandler {
    public Response<ResponseEntity> json(Request request) {
        return Response.ok().body(fromObject(new ResponseEntity()));
    }

    public Response<String> pathVariableDemo(Request request) {
        String message = "Helllo, " + request.pathVariable("name").orElse("Unknown");
        return Response.ok().body(fromObject(message));
    }

    public Response<String> queryParamDemo(Request request) {
        String message = "Helllo, " + request.queryParam("name").orElse("Unknown");
        return Response.ok().body(fromObject(message));
    }

    public static class ResponseEntity {
        public String getThing() {
            return "Hello";
        }
    }
}
