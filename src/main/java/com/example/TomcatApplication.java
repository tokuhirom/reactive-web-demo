package com.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServletHttpHandlerAdapter;

import javax.servlet.http.HttpServlet;

@Slf4j
public class TomcatApplication {

    public static void main(String[] args) throws LifecycleException, InterruptedException {
        HttpHandler httpHandler = HttpHandlerFactory.build();
        HttpServlet servlet = new ServletHttpHandlerAdapter(httpHandler);
        Tomcat server = new Tomcat();
        Context rootContext = server.addContext("",
                System.getProperty("java.io.tmpdir"));
        Tomcat.addServlet(rootContext, "servlet", servlet);
        rootContext.addServletMappingDecoded("/", "servlet");
        log.info("Starting server");
        server.start();

        while (true) {
            Thread.sleep(100000);
        }

//        SpringApplication.run(TomcatApplication.class, args);
    }

}
