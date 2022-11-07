package service;

import data.HomeSQLRepository;
import domain.Inventory;

import java.util.List;

public class HomeService {

  private final HomeSQLRepository repo;

  public HomeService() {
    repo = new HomeSQLRepository();
  }

  public List<Inventory> getInventories() {
    return repo.getInventories();
  }

  public void createInventory(Inventory inventory) {
    repo.createInventory(inventory);
  }
}
