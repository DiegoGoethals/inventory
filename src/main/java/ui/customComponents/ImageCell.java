package ui.customComponents;

import domain.Product;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageCell extends TableCell<String, Product> {

  private final ImageView image;

  public ImageCell(Product product) {
    image = new ImageView();
    image.setFitWidth(64);
    image.setFitHeight(64);
    image.setPreserveRatio(true);

    setGraphic(image);
    setMinHeight(70);

    System.out.println(product);
    image.setImage(new Image(String.valueOf(product.getClass().getResource(product.getImage()))));
    setText(product.getName());
  }
}
