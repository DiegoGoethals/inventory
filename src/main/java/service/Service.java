package service;

import data.SQLRepository;
import domain.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class Service {

  private SQLRepository repo;
  private static final Logger LOGGER = Logger.getLogger(Service.class.getName());

  public Service() {
    repo = new SQLRepository();
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
}
