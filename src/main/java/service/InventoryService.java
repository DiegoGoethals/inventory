package service;

import data.ProductSQLRepository;
import domain.Inventory;
import domain.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class InventoryService {

  private final ProductSQLRepository repo;
  private static final Logger LOGGER = Logger.getLogger(InventoryService.class.getName());

  public InventoryService() {
    repo = new ProductSQLRepository();
  }

  public void addProduct(Product product) throws SQLException {
    repo.addProduct(product);
  }

  public List<Product> getProducts(Inventory inventory) {
    return repo.getProducts(inventory);
  }

  public void changeQuantity(Product product, int quantity) {
    repo.changeQuantity(product, quantity);
  }

  public List<Product> getFilteredProducts(String filter, Inventory inventory) {
    return repo.getFilteredProducts(filter, inventory);
  }

  public void addImage(String url, Product product) {
    repo.addImage(url, product);
  }
}
