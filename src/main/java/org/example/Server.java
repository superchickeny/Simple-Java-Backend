package org.example;

import com.sun.net.httpserver.HttpServer;
import org.example.Routes.Login;
import org.example.Routes.Register;

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
    private final SQL mysql = new SQL();

    private Server() throws IOException {
        setHttpRoutes();
        registerHttpRoutes();
        startServer();
        create_Tables();
    }

    private void create_Tables(){

        mysql.sendUpdate("CREATE TABLE IF NOT EXISTS users (\n" +
                "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    email VARCHAR(255) NOT NULL UNIQUE,\n" +
                "    password VARCHAR(255) NOT NULL,\n" +
                "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n" +
                ");");
    }

    private void setHttpRoutes(){
        httpRoutes.add(new Register());
        httpRoutes.add(new Login());
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

    public SQL getMysql() {
        return mysql;
    }

    public static void go(){

        try{
            instance = new Server();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static Server getServer() {
        return instance;
    }
}
