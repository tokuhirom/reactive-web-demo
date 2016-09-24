package com.example;

import com.example.handlers.DemoHandler;
import com.example.handlers.JsonHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.reactive.config.WebReactiveConfiguration;
import org.springframework.web.reactive.function.Response;
import org.springframework.web.reactive.function.RouterFunction;
import org.springframework.web.reactive.function.RouterFunctions;
import org.springframework.web.reactive.function.StrategiesSupplier;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.RequestPredicates.GET;
import static org.springframework.web.reactive.function.RequestPredicates.POST;
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
                        .and(route(GET("/view"), jsonHandler::view))
                        .and(route(POST("/form-body"), jsonHandler::jsonBody))
                        .and(route(GET("/uri"), jsonHandler::uri));

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(WebReactiveConfiguration.class);
        context.refresh();

        DispatcherHandler dispatcherHandler = new DispatcherHandler();
        dispatcherHandler.setApplicationContext(context);

        HttpHandler httpHandler = WebHttpHandlerBuilder.webHandler(dispatcherHandler).build();

        return RouterFunctions.toHttpHandler(
                simpleRouter.and(jsonRouter),
                StrategiesSupplier.builder()
                        .viewResolver(new FreeMarkerViewResolver())
                        .build()
        );
    }
}
