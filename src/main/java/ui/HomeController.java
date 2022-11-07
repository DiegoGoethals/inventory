package ui;

import data.ProductSQLRepository;
import domain.Inventory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.HomeService;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeController {

  Logger LOGGER = Logger.getLogger(HomeController.class.getName());
  private final HomeService service;
  @FXML ListView<Inventory> lstInventories;
  @FXML TextField txtName;

  public HomeController() {
    service = new HomeService();
  }

  @FXML
  public void initialize() {
    updateList();
  }

  public List<Inventory> getInventories() {
    return service.getInventories();
  }

  public void updateList() {
    lstInventories.setItems(FXCollections.observableList(getInventories()));
  }

  public void createInventory() {
    service.createInventory(new Inventory(txtName.getText(), 0));
    updateList();
  }

  public void openInventory() {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(getClass().getResource("/fxml/inventory.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      Stage stage = new Stage();
      stage.setTitle(lstInventories.getSelectionModel().getSelectedItem().getName());
      stage.setScene(scene);
      stage.show();
    } catch (IOException e) {
      Logger logger = Logger.getLogger(getClass().getName());
      logger.log(Level.SEVERE, "Failed to create new Window.", e);
    }
  }
}
