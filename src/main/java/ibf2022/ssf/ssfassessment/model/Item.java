package ibf2022.ssf.ssfassessment.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Item {
    private String name;

    @NotNull(message = "You must add at least 1 item")
    @Min(value = 1, message = "You must add at least 1 item")
    private Integer quantity;

    public Item() {
    }

    public Item(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item [name=" + name + ", quantity=" + quantity + "]";
    }

    public void increaseQuantity(Item item) {
        if (item.getName().equals(this.getName())) {
            this.setQuantity(
                this.quantity + item.quantity
            );
        }
    }
}
