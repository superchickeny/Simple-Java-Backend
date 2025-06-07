package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQL {

    private Connection connection;

    public SQL(){
        connect();
    }

    private void connect(){
        try{
            connection = DriverManager.getConnection(Util.dotenv.get("DB_HOST"), Util.dotenv.get("DB_USER"), Util.dotenv.get("DB_PASS"));
            System.out.println("Connection successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public ResultSet sendQuery(String pre_prepared_stmt, Object... args) {

        try{
            PreparedStatement statement = connection.prepareStatement(pre_prepared_stmt);

            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }

            return statement.executeQuery();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public boolean sendUpdate(String pre_prepared_stmt, Object... args){
        try{
            PreparedStatement statement = connection.prepareStatement(pre_prepared_stmt);

            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }

            statement.executeUpdate();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static List<String> parse_query(ResultSet resultSet, String... fields){

        List<String> response = new ArrayList<>();

        try{
            if (resultSet.next()){
                for(String obj : fields){
                    response.add(resultSet.getString(obj));
                }
            }

            return response.isEmpty() ? null : response;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

}
