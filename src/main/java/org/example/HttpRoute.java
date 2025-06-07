package org.example;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;

public interface HttpRoute {

    String getPath();
    void handle(HttpExchange exchange) throws IOException;

    static boolean ensurePath(HttpExchange exchange, String requestPath, String desiredPath) throws IOException {
        if (!requestPath.equals(desiredPath)) {
            String response = "Not Found!";
            exchange.sendResponseHeaders(404, response.length());
            try (OutputStream output = exchange.getResponseBody()) {
                output.write(response.getBytes());
            }
            return false;
        }
        return true;
    }

    static void log(String method, HttpRoute route){
        System.out.println(method + " Received " + route.getPath());
    }

    static void sendResponse(HttpExchange exchange, int http_code, String res){

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        try (OutputStream output = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(http_code, res.getBytes().length);
            output.write(res.getBytes());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
