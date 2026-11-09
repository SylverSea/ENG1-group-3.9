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
     *saves iutem id and starting wuantity
     * 
     * @param itemID
     * @param quantity
     * @author Erikas
     */
    public InventoryItem(String itemID, int quantity) {
        this.itemID = itemID;
        this.quantity = quantity;
    }

    /**
     * return itemid
     * 
     * @return
     * @author Erikas
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * returns how many of this itenm player has
     * 
     * @return
     * @author Erikas
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     *adds more of this item
     * 
     * @param amount
     * @author Erikas
     */
    public void add(int amount) {
        quantity += amount;
    }

    /**
     *removes this item if theres enough
     * 
     * @param amount
     * @return
     * @author Erikas
     */
    public boolean remove(int amount) {
        if (quantity < amount) return false;
        quantity -= amount;
        return true;
    }

}
