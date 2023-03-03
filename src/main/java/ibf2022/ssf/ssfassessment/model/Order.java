package ibf2022.ssf.ssfassessment.model;

public class Order {
    private Customer customer;
    private Quotation quotation;
    private Float price;

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Quotation getQuotation() {
        return quotation;
    }
    public void setQuotation(Quotation quotation) {
        this.quotation = quotation;
    }
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }

    // Getters from dependencies
    /*
     * Additional fields
     * id
     * name
     * address
     */

     public String getId() {
        return this.quotation.getQuoteId();
    }

    public String getName() {
        return this.customer.getName();
    }

    public String getAddress() {
        return this.customer.getAddress();
    }

    @Override
    public String toString() {
        return "Order [customer=" + customer + ", quotation=" + quotation + ", price=" + price + "]";
    }
}
