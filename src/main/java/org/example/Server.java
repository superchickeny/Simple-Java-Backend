package org.example;

import com.sun.net.httpserver.HttpServer;
import org.example.Routes.TestRoute;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class Server {

    private static Server instance;
    private final int port = 1234;
    private final HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
    private final List<HttpRoute> httpRoutes = new ArrayList<>();

    private Server() throws IOException {
        setHttpRoutes();
        registerHttpRoutes();
        startServer();
    }

    private void setHttpRoutes(){
        httpRoutes.add(new TestRoute());
    }

    private void registerHttpRoutes(){
        for (HttpRoute route : httpRoutes){
            server.createContext(route.getPath(), route::handle);
        }
    }

    private void startServer(){
        server.setExecutor(Executors.newFixedThreadPool(50));
        server.start();
        System.out.println("Server Started");
    }

    public static void go(){

        try{
            instance = new Server();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static Server getInstance() {

        if(instance == null){
            try{
                return new Server();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        return instance;
    }
}
