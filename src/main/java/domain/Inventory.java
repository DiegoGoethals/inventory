package domain;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

  private String name;
  private int productCount;

  public Inventory(String name, int productCount) {
    this.name = name;
    this.productCount = productCount;
  }

  public String getName() {
    return name;
  }

  public int getProductCount() {
    return productCount;
  }

  @Override
  public String toString() {
    return name + ": " + getProductCount();
  }
}
