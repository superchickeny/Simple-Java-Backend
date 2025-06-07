package org.example.Routes;

import com.sun.net.httpserver.HttpExchange;
import org.example.HttpRoute;
import org.example.JSON.JsonStructures.Auth_JSON;
import org.example.JSON.JsonStructures.Status;
import org.example.JSON.JsonUtil;
import org.example.SQL;
import org.example.Server;

import java.io.IOException;
import java.sql.SQLException;

public class Register implements HttpRoute {

    @Override
    public String getPath() {
        return "/register";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String request_method = exchange.getRequestMethod();
        String request_path = exchange.getRequestURI().getPath();

        if(!request_method.equalsIgnoreCase("POST"))return;
        if(!HttpRoute.ensurePath(exchange, request_path, getPath())) return;
        HttpRoute.log(request_method, this);

        Auth_JSON req = JsonUtil.parse_json(exchange, Auth_JSON.class);
        String email = req.email;
        String password = req.password;
        SQL sql = Server.getServer().getMysql();

        if(sql.sendUpdate("INSERT INTO forum.users (email, password) VALUES (?, ?)", email, password)) {
            HttpRoute.sendResponse(exchange, 200, JsonUtil.object_to_json(new Status("ok")));
            return;
        }

        HttpRoute.sendResponse(exchange, 401, JsonUtil.object_to_json(new Status("bad")));
    }
}
