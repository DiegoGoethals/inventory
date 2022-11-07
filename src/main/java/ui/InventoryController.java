package ui;

import domain.Product;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import service.InventoryService;
import util.ProductException;

import java.sql.SQLException;

public class InventoryController {

  private final InventoryService inventoryService;
  private boolean filtered;
  private String filter;
  @FXML TableView<Product> tblProducts;
  @FXML TableColumn<Product, String> tblclmName;
  @FXML TableColumn<Product, Integer> tblclmQuantity;
  @FXML TableColumn<Product, ImageView> tblclmImage;
  @FXML TextField txtName;
  @FXML TextField txtQuantity;
  @FXML TextField txtSearch;

  public InventoryController() {
    inventoryService = new InventoryService();
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
    tblProducts.setItems(FXCollections.observableList(inventoryService.getProducts()));
  }

  public void getFilteredProducts() {
    tblProducts.setItems(FXCollections.observableList(inventoryService.getFilteredProducts(filter)));
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
      inventoryService.addProduct(new Product(txtName.getText().trim(), Integer.parseInt(txtQuantity.getText()), null));
    } catch (SQLException ex) {
      throw new ProductException("Something went wrong, try again", ex);
    }
    updateTable();
  }

  public void increaseQuantity() {
    Product product = tblProducts.getSelectionModel().getSelectedItem();
    inventoryService.changeQuantity(product, 1);
    updateTable();
  }

  public void decreaseQuantity() {
    Product product = tblProducts.getSelectionModel().getSelectedItem();
    inventoryService.changeQuantity(product, -1);
    updateTable();
  }

  public void searchItems() {
    filter = txtSearch.getText();
    filtered = true;
    updateTable();
  }

  public void emptyText() {
    txtSearch.setText("");
  }
}
