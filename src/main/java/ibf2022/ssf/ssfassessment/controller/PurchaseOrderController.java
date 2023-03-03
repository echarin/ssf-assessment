package ibf2022.ssf.ssfassessment.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ibf2022.ssf.ssfassessment.model.Cart;
import ibf2022.ssf.ssfassessment.model.Customer;
import ibf2022.ssf.ssfassessment.model.Item;
import ibf2022.ssf.ssfassessment.model.Order;
import ibf2022.ssf.ssfassessment.model.Quotation;
import ibf2022.ssf.ssfassessment.service.CartService;
import ibf2022.ssf.ssfassessment.service.OrderService;
import ibf2022.ssf.ssfassessment.service.QuotationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping
public class PurchaseOrderController {

    private Logger logger = Logger.getLogger(PurchaseOrderController.class.getName());

    @Autowired
    private CartService cartSvc;

    @Autowired
    private QuotationService qSvc;

    @Autowired
    private OrderService orderSvc;
    
    @GetMapping(path = {"/", "index.html"})
    public String getView1(
        HttpSession session, Model model
    ) {
        // Adding support for the back button from view 2
        Cart cart = (Cart) session.getAttribute("cart");
        if (null == cart) {
            cart = new Cart();
        }

        model.addAttribute("item", new Item());
        model.addAttribute("cart", cart);

        return "view1";
    }

    @PostMapping(path = "/")
    public String postItem(
        @Valid @ModelAttribute Item item, BindingResult result, HttpSession session, Model model
    ) {
        // Logging the initial item for debugging
        logger.info("To add: %s".formatted(item.toString()));

        Cart cart = (Cart) session.getAttribute("cart");
        if (null == cart) {
            cart = new Cart();
        }

        if (result.hasErrors()) {
            model.addAttribute("item", item);
            model.addAttribute("cart", cart);
            return "view1";
        }

        logger.info("Cart before: %s ".formatted(cart.toString()));

        // Perform business logic: semantic validation of item
        List<ObjectError> errors = cartSvc.validateItem(item);
        if (!errors.isEmpty()) {
            for (ObjectError e: errors)
                result.addError(e);
            model.addAttribute("item", item);
            model.addAttribute("cart", cart);
            return "view1";
        }

        // If item is valid, add item to cart
        cart.addToCart(item);
        session.setAttribute("cart", cart);

        logger.info("Cart after: %s ".formatted(cart.toString()));

        model.addAttribute("item", new Item());
        model.addAttribute("cart", cart);
        return "view1";
    }

    @GetMapping(path = "/shippingaddress")
    public String getView2(
        HttpSession session, Model model
    ) {
        // If cart is empty or null, redirect to View 1
        Cart cart = (Cart) session.getAttribute("cart");
        if ((null == cart) || (null == cart.getItems())) {
            return "redirect:/";
        }

        Customer customer = (Customer) session.getAttribute("customer");
        if (null == customer) {
            customer = new Customer();
        }
        
        model.addAttribute("customer", customer);

        // Injecting new Quotation for error display
        model.addAttribute("quotation", new Quotation());

        return "view2";
    }

    @PostMapping(path = "/quotation")
    public String postQuotation(
        @Valid @ModelAttribute Customer customer, BindingResult result, HttpSession session,  Model model
    ) throws Exception {
        logger.info("Customer: %s".formatted(customer.toString()));

        // Syntactic validation on customer
        if (result.hasErrors()) {
            model.addAttribute("customer", customer);
            model.addAttribute("quotation", new Quotation());
            return "view2";
        }

        // By right, the cart should not be null
        Cart cart = (Cart) session.getAttribute("cart");
        if (null == cart) {
            cart = new Cart();
        }

        List<String> cartItemNameList = cart.createItemNameList();

        /* 
         * Semantic validation on cart is done here
         * If QSys returns quotation error, then display the error in View 2 
         * along with previously-filled name/address
         */
        Quotation quotation = null;
        List<ObjectError> errors = new LinkedList<>();
        FieldError error;

        try {
            quotation = qSvc.getQuotations(cartItemNameList);
        } catch (Exception ex) {
            // See if you can place the error in quotation.quotations
            error = new FieldError("quotation", "quotations", ex.getMessage());
            errors.add(error);
            for (ObjectError e: errors)
                result.addError(e);

            model.addAttribute("customer", customer);
            model.addAttribute("quotation", quotation);
            return "view2";
        }

        logger.info("Quotation: %s".formatted(quotation.toString()));

        Order order = orderSvc.createOrder(customer, cart, quotation);

        logger.info("Order: %s".formatted(order.toString()));
        
        // Clear the contents of the customer's cart
        session.removeAttribute("cart");

        model.addAttribute("order", order);

        return "view3";
    }
}
