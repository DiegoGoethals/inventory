package ui;

import domain.Product;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import service.Service;
import util.ProductException;

import java.sql.SQLException;

public class Controller {

  private final Service service;
  private boolean filtered;
  private String filter;
  @FXML TableView<Product> tblProducts;
  @FXML TableColumn<Product, String> tblclmName;
  @FXML TableColumn<Product, Integer> tblclmQuantity;
  @FXML TableColumn<Product, ImageView> tblclmImage;
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
    tblclmName.setCellValueFactory(new PropertyValueFactory<>("name"));
    tblclmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    tblclmImage.setCellValueFactory(new PropertyValueFactory<>("image"));
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
