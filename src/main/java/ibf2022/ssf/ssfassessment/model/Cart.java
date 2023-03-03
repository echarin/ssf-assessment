package ibf2022.ssf.ssfassessment.model;

import java.util.List;
import java.util.LinkedList;


public class Cart {
    private List<Item> items;

    public Cart() {
    }

    public Cart(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<String> createItemNameList() {
        // Create a list of item names in current cart
        List<String> itemNames = new LinkedList<>();

        // If this.items is not null, then populate itemNames
        if (null != this.items) {
            for (Item i : this.items) {
                if (!itemNames.contains(i.getName()))
                    itemNames.add(i.getName());
            }
        // If it is null, we set this.items to be new LinkedList
        } else {
            this.setItems(new LinkedList<>());
        }

        // If cart.getItems() is null, then return empty LinkedList
        return itemNames;
    }

    public void addToCart(Item itemToAdd) {
        // Create a list of item names in current cart
        List<String> itemNames = this.createItemNameList();

        // If this item does not exist in current cart, add it
        if (!itemNames.contains(itemToAdd.getName())) {
            this.items.add(itemToAdd);
        // Else we add quantity
        } else {
            // Iterate through the list of items to find the item that matches
            for (Item i : this.items) {
                if (i.getName().equals(itemToAdd.getName()))
                    i.increaseQuantity(itemToAdd);
            }
        }
    }

    @Override
    public String toString() {
        return "Cart [items=" + items + "]";
    }
}
