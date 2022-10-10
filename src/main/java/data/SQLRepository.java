package data;

import data.util.SQLConnection;
import domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLRepository {

  Logger LOGGER = Logger.getLogger(SQLRepository.class.getName());
  private static final String SQL_SELECT_ALL_PRODUCTS = "select * from products";
  private static final String SQL_ADD_PRODUCT = "insert into products(name, quantity) values(?, ?)";

  public void addProduct(Product product) throws SQLException {
    try (Connection connection = SQLConnection.getConnection();
         PreparedStatement prep = connection.prepareStatement(SQL_ADD_PRODUCT)) {
      prep.setString(1, product.getName());
      prep.setInt(2, product.getQuantity());
      prep.executeUpdate();
    } catch (SQLException ex) {
      LOGGER.log(Level.SEVERE, "A database error occured.", ex);
      throw new RuntimeException("A database error occured.");
    }
  }

  public List<Product> getProducts() {
    try (Connection connection = SQLConnection.getConnection();
         PreparedStatement prep = connection.prepareStatement(SQL_SELECT_ALL_PRODUCTS);
         ResultSet rs = prep.executeQuery()) {
      List<Product> products = new ArrayList<>();
      while (rs.next()) {
        products.add(resultSetToProduct(rs));
      }
      return products;
    } catch (SQLException ex) {
      LOGGER.log(Level.SEVERE, "A database error occured.", ex);
      throw new RuntimeException("A database error occured.");
    }
  }

  private Product resultSetToProduct(ResultSet rs) throws SQLException {
    return new Product(rs.getString("name"),
      rs.getInt("quantity"));
  }
}
