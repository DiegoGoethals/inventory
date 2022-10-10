package ui;

import domain.Product;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import service.Service;
import util.ProductException;

import java.sql.SQLException;

public class Controller {

  private Service service;
  @FXML ListView<Product> lstProducts;
  @FXML TextField txtName;
  @FXML TextField txtQuantity;

  public Controller() {
    service = new Service();
  }

  @FXML
  public void initialize() {
    updateList();
  }

  public void updateList() {
    lstProducts.setItems(FXCollections.observableList(service.getProducts()));
  }

  public void addProduct() {
    try {
      service.addProduct(new Product(txtName.getText().trim(), Integer.parseInt(txtQuantity.getText())));
    } catch (SQLException ex) {
      throw new ProductException("Something went wrong, try again", ex);
    }
    updateList();
  }
}
