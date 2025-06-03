package org.example.Routes;

import com.sun.net.httpserver.HttpExchange;
import org.example.HttpRoute;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TestRoute implements HttpRoute {

    @Override
    public String getPath() {
        return "/register";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String request_method = exchange.getRequestMethod();
        String request_path = exchange.getRequestURI().getPath();

        if(!HttpRoute.ensurePath(exchange, request_path, getPath())) return;
        HttpRoute.log(request_method, this);

        String resToSend = "hi";
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(200, resToSend.getBytes().length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(resToSend.getBytes());
        }
    }

}
