package service;

import data.ProductSQLRepository;
import domain.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class InventoryService {

  private ProductSQLRepository repo;
  private static final Logger LOGGER = Logger.getLogger(InventoryService.class.getName());

  public InventoryService() {
    repo = new ProductSQLRepository();
  }

  public void addProduct(Product product) throws SQLException {
    repo.addProduct(product);
  }

  public List<Product> getProducts() {
    return repo.getProducts();
  }

  public void changeQuantity(Product product, int quantity) {
    repo.changeQuantity(product, quantity);
  }

  public List<Product> getFilteredProducts(String filter) {
    return repo.getFilteredProducts(filter);
  }
}
