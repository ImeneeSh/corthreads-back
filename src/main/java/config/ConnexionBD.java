package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {
    public final static String URL="jdbc:mysql://localhost:3306/corthreads";
    public final static String USER="root";
    public final static String PASSWORD="";

    static{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static Connection getConnexion() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
