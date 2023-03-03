package ibf2022.ssf.ssfassessment.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import ibf2022.ssf.ssfassessment.model.Cart;
import ibf2022.ssf.ssfassessment.model.Item;
import ibf2022.ssf.ssfassessment.service.CartService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping
public class PurchaseOrderController {

    private Logger logger = Logger.getLogger(PurchaseOrderController.class.getName());

    @Autowired
    private CartService cartSvc;
    
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
            return "index";
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
        // 

        model.addAttribute("customer", customer);

        return "view2";
    }
}