package com.example.handlers;

import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.Rendering;
import org.springframework.web.reactive.function.Request;
import org.springframework.web.reactive.function.Response;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

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

    /*
    request body として利用可能なのは、org.springframework.web.reactive.function.DefaultStrategiesSupplierBuilder.defaultConfiguration に実装されたもの。
     */

    // curl -X POST -H 'content-type: application/json' -d '{"thing":"hoge"}' -v http://localhost:8080/form-body
    public Response<Mono<String>> jsonBody(Request request) {
        Mono<String> body = request.body(toMono(RequestEntity.class))
                .map(RequestEntity::getThing);
        return Response.ok().body(fromPublisher(body, String.class));
    }

    // freemarker 使うパターン。
    public Response<Rendering> view(Request request) {
        return Response.ok().render("hello", ImmutableMap.builder()
                .put("name", "John")
                .build());
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class RequestEntity {
        String thing;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ResponseEntity {
        String thing;
    }
}
