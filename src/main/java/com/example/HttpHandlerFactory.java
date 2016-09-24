package com.example;

import com.example.handlers.DemoHandler;
import com.example.handlers.JsonHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.reactive.function.Response;
import org.springframework.web.reactive.function.RouterFunction;
import org.springframework.web.reactive.function.RouterFunctions;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.RequestPredicates.GET;
import static org.springframework.web.reactive.function.RouterFunctions.route;

@Slf4j
public class HttpHandlerFactory {
    public static HttpHandler build() {
        DemoHandler handler = new DemoHandler();

        RouterFunction<?> simpleRouter =
                route(GET("/hello-world"), handler::helloWorld)
                        .andSame(route(GET("/the-answer"), handler::theAnswer))
                        .filter((request, next) -> {
                            Response<String> response = next.handle(request);
                            log.info("filtering..!");
                            return Response.from(response).body(fromObject(response.body().toUpperCase()));
                        });

        JsonHandler jsonHandler = new JsonHandler();
        RouterFunction<?> jsonRouter =
                route(GET("/json"), jsonHandler::json)
                        .and(route(GET("/hello/{name}"), jsonHandler::pathVariableDemo))
                        .and(route(GET("/query-param"), jsonHandler::queryParamDemo))
                        .and(route(GET("/uri"), jsonHandler::uri));

        return RouterFunctions.toHttpHandler(
                simpleRouter.and(jsonRouter)
        );
    }
}
