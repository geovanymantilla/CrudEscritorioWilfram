package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/crud_db";
    private static final String USER = "root"; // Cambia por tu usuario
    private static final String PASSWORD = ""; // Cambia por tu contraseña

    public static Connection getConnection() throws SQLException {
        // Registrar el driver (si es necesario)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}