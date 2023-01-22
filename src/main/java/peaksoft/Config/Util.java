package peaksoft.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "12345"
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }
}
