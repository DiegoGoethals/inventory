package data;

import data.util.SQLConnection;
import domain.Inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeSQLRepository {

  Logger LOGGER = Logger.getLogger(HomeSQLRepository.class.getName());
  private static final String SQL_SELECT_ALL_INVENTORIES = "select * from inventories";
  private static final String SQL_CREATE_INVENTORY = "insert into inventories(name) values(?)";
  private static final String SQL_GET_PRODUCT_COUNT = "select COUNT(*) as count FROM products WHERE inventory = '";

  public List<Inventory> getInventories() {
    try (Connection connection = SQLConnection.getConnection();
         PreparedStatement prep = connection.prepareStatement(SQL_SELECT_ALL_INVENTORIES);
         ResultSet rs = prep.executeQuery()) {
      List<Inventory> inventories = new ArrayList<>();
      while (rs.next()) {
        inventories.add(resultSetToInventory(rs));
      }
      return inventories;
    } catch (SQLException ex) {
      LOGGER.log(Level.SEVERE, "A database error occured.", ex);
      throw new RuntimeException("A database error occured.");
    }
  }

  public int getProductCount(String inventory) {
    try (Connection connection = SQLConnection.getConnection();
         PreparedStatement prep = connection.prepareStatement(SQL_GET_PRODUCT_COUNT + inventory + "'");
          ResultSet rs = prep.executeQuery()) {
      if (rs.next()) {
        return rs.getInt("count");
      }
      return 0;
    } catch (SQLException ex) {
        LOGGER.log(Level.SEVERE, "A database error occured.", ex);
        throw new RuntimeException("A database error occured.");
    }
  }

  private Inventory resultSetToInventory(ResultSet rs) throws SQLException {
    String inventory = rs.getString("name");
    return new Inventory(inventory, getProductCount(inventory));
  }

  public void createInventory(Inventory inventory) {
    try (Connection connection = SQLConnection.getConnection();
         PreparedStatement prep = connection.prepareStatement(SQL_CREATE_INVENTORY)) {
      prep.setString(1, inventory.getName());
      prep.executeUpdate();
    } catch (SQLException ex) {
      LOGGER.log(Level.SEVERE, "A database error occured.", ex);
      throw new RuntimeException("A database error occured.");
    }
  }
}
