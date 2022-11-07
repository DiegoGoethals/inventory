package ui;

import domain.Product;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import service.Service;
import ui.customComponents.ImageCell;
import util.ProductException;

import java.sql.SQLException;

public class Controller {

  private Service service;
  private boolean filtered;
  private String filter;
  @FXML TableView<Product> tblProducts;
  @FXML TableColumn<String, Product> tblclmName;
  @FXML TableColumn<Integer, Product> tblclmQuantity;
  @FXML TableColumn<String, Product> tblclmImage;
  @FXML TextField txtName;
  @FXML TextField txtQuantity;
  @FXML TextField txtSearch;

  public Controller() {
    service = new Service();
    filtered = false;
    filter = "";
  }

  @FXML
  public void initialize() {
    int index = 0;
    tblclmName.setCellValueFactory(new PropertyValueFactory<>("name"));
    tblclmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    tblclmImage.setCellFactory(param -> new ImageCell(tblProducts.getItems().get(index)));
    updateTable();
  }

  public void getAllProducts() {
    filtered = false;
    tblProducts.setItems(FXCollections.observableList(service.getProducts()));
  }

  public void getFilteredProducts() {
    tblProducts.setItems(FXCollections.observableList(service.getFilteredProducts(filter)));
  }

  public void updateTable() {
    if (filtered) {
      getFilteredProducts();
    } else {
      getAllProducts();
    }
  }

  public void addProduct() {
    try {
      service.addProduct(new Product(txtName.getText().trim(), Integer.parseInt(txtQuantity.getText()), null));
    } catch (SQLException ex) {
      throw new ProductException("Something went wrong, try again", ex);
    }
    updateTable();
  }

  public void increaseQuantity() {
    Product product = tblProducts.getSelectionModel().getSelectedItem();
    service.changeQuantity(product, 1);
    updateTable();
  }

  public void decreaseQuantity() {
    Product product = tblProducts.getSelectionModel().getSelectedItem();
    service.changeQuantity(product, -1);
    updateTable();
  }

  public void searchItems() {
    filter = txtSearch.getText();
    filtered = true;
    updateTable();
  }
}
