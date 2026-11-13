package io.github.some_example_name.Sprites;

/**
 *describes one stack of a single item tyope
 * 
 * @author Erikas
 */
public class InventoryItem {

    private String itemID;
    private int quantity;

    /**
     *saves item id and starting wuantity
     * 
     * @param itemID    ID of the item
     * @param quantity  quantity of the item
     * @author Erikas
     */
    public InventoryItem(String itemID, int quantity) {
        this.itemID = itemID;
        this.quantity = quantity;
    }

    /**
     * return itemID
     * 
     * @return ID of the item
     * @author Erikas
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * returns how many of this itenm player has
     * 
     * @return how many of this itenm player has
     * @author Erikas
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *adds more of this item
     * 
     * @param amount Ammount to be added
     * @author Erikas
     */
    public void add(int amount) {
        quantity += amount;
    }

    /**
     *removes this item if there's enough
     * 
     * @param amount Ammount to be removed
     * @return  Whether there are enough items to be removed 
     * @author Erikas
     */
    public boolean remove(int amount) {
        if (quantity < amount) return false;
        quantity -= amount;
        return true;
    }
}
