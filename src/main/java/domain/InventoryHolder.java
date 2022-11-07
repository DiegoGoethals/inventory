package domain;

public final class InventoryHolder {

  private Inventory inventory;
  private final static InventoryHolder INSTANCE = new InventoryHolder();

  private InventoryHolder() {}

  public static InventoryHolder getInstance() {
    return INSTANCE;
  }

  public void setInventory(Inventory inventory) {
    this.inventory = inventory;
  }

  public Inventory getInventory() {
    return this.inventory;
  }
}
