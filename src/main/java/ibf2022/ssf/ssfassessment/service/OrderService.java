package ibf2022.ssf.ssfassessment.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import ibf2022.ssf.ssfassessment.model.Cart;
import ibf2022.ssf.ssfassessment.model.Customer;
import ibf2022.ssf.ssfassessment.model.Item;
import ibf2022.ssf.ssfassessment.model.Order;
import ibf2022.ssf.ssfassessment.model.Quotation;

@Service
public class OrderService {
    
    /*
     * Given a cart of items and a quotation containing item name and unit price
     * Calculate total cost of the cart
     */
    public Float calculatePrice(Cart cart, Quotation quotation) {
        Float total = 0f;

        Map<String, Float> quotations = quotation.getQuotations();
        String itemName = null;
        Integer quantity = null;
        Float unitPrice = null;

        for (Item i : cart.getItems()) {
            itemName = i.getName();
            quantity = i.getQuantity();
            unitPrice = quotations.get(itemName);
            total += quantity * unitPrice;
        }

        return total;
    }

    public Order createOrder(Customer cust, Cart cart, Quotation quo) {
        Order order = new Order();

        order.setCustomer(cust);
        order.setQuotation(quo);
        order.setPrice(calculatePrice(cart, quo));
        
        return order;
    }
}
