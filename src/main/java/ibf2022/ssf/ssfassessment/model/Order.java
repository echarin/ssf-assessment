package ibf2022.ssf.ssfassessment.model;

public class Order {
    private Customer customer;
    private Quotation quotation;
    private Float price;

    /*
     * Additional fields
     * id
     * Name
     * Address
     */

     public String getQuoteId() {
        return this.quotation.getQuoteId();
    }

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


    @Override
    public String toString() {
        return "Order [customer=" + customer + ", quotation=" + quotation + ", price=" + price + "]";
    }
}
