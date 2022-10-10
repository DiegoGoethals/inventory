package data.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

  private static final String URL = "jdbc:mysql://localhost/inventory";
  private static final String USER = "user";
  private static final String PASSWORD = "user";

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }
}
