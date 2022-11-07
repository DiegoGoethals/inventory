package domain;

import java.util.Objects;

public class Product {

  private final String name;
  private int quantity;
  private String image;

  public Product(String name, int quantity, String image) {
    this.name = name;
    this.quantity = quantity;
    this.image = Objects.requireNonNullElse(image, "/images/no_image_available.png");
  }

  public String getName() {
    return name;
  }

  public int getQuantity() {
    return quantity;
  }

  public String getImage() {
    return image;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return quantity == product.quantity && Objects.equals(name, product.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, quantity);
  }

  @Override
  public String toString() {
    return name + ": " + quantity;
  }
}
