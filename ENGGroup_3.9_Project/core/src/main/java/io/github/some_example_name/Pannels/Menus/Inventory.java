package io.github.some_example_name.Pannels.Menus;

import java.util.HashMap;
import java.util.Map;

import io.github.some_example_name.Sprites.InventoryItem;

public class Inventory extends Menu {

    private Map<String, InventoryItem> items = new HashMap<>();

    public Inventory() {

    }

    /**
     * Adds items to inventory, 
     * creating new stack if its first of that item
     * 
     * @param itemID
     * @param amount
     * @author Erikas Jovaisa
     */
    public void addItem(String itemID, int amount) {
        if (items.containsKey(itemID)) {
            items.get(itemID).add(amount);
        } else {
            items.put(itemID, new InventoryItem(itemID, amount));
        }
    }

    /**
     *uses one of the chosen item if availible
     * 
     * @param itemID
     * @return
     * @author Erikas Jovaisa
     */
    public boolean useItem(String itemID) {
        InventoryItem item = items.get(itemID);
        if (item == null) return false;
        return item.remove(1);
    }

    /**
     *looks up how many of an item the player holds
     * 
     * @param itemID
     * @return
     * @author Erikas Jovaisa
     */
    public int getQuantity(String itemID) {
        InventoryItem item = items.get(itemID);
        return item == null ? 0 : item.getQuantity();
    }
}
