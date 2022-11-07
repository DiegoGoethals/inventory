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

public class ProductSQLRepository {

  Logger LOGGER = Logger.getLogger(ProductSQLRepository.class.getName());
  private static final String SQL_SELECT_ALL_PRODUCTS = "select * from products";
  private static final String SQL_ADD_PRODUCT = "insert into products(name, quantity) values(?, ?)";
  private static final String SQL_SELECT_FILTERED_PRODUCTS = "select * from products WHERE name LIKE ?";

  public void addProduct(Product product) throws SQLException {
    if (checkIfExists(product.getName())) {
      changeQuantity(product, product.getQuantity());
    } else{
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
      rs.getInt("quantity"), rs.getString("image"));
  }

  public boolean checkIfExists(String name) throws SQLException {
    int count = 0;
    ResultSet rset;
    try(Connection connection = SQLConnection.getConnection();
        PreparedStatement prep = connection.prepareStatement("SELECT Count(name) from products WHERE name=?")) {
      prep.setString(1, name);
      rset = prep.executeQuery();
      if (rset.next())
        count = rset.getInt(1);
      return count > 0;
    }
  }

  public void changeQuantity(Product product, int quantity) {
    try(Connection connection = SQLConnection.getConnection();
        PreparedStatement prep = connection.prepareStatement("UPDATE products SET quantity = quantity + ? WHERE name = ?")) {
      prep.setString(1, Integer.toString(quantity));
      prep.setString(2, product.getName());
      prep.executeUpdate();
    } catch (SQLException ex) {
      LOGGER.log(Level.SEVERE, "A database error occured.", ex);
      throw new RuntimeException("A database error occured.");
    }
  }

  public List<Product> getFilteredProducts(String name) {
    try (Connection connection = SQLConnection.getConnection();
         PreparedStatement prep = connection.prepareStatement(SQL_SELECT_FILTERED_PRODUCTS)) {
      prep.setString(1, "%" + name + "%");
      ResultSet rs = prep.executeQuery();
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
}
