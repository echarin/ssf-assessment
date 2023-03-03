package ibf2022.ssf.ssfassessment.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Customer {

    @NotEmpty(message = "Must enter your name")
    @Size(min = 2, message = "Name must be at least 2 chars long")
    private String name;

    @NotEmpty(message = "Must enter your address")
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Customer() {
    }

    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer [name=" + name + ", address=" + address + "]";
    }
}
