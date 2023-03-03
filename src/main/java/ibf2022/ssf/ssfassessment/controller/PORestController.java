package ibf2022.ssf.ssfassessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2022.ssf.ssfassessment.model.Cart;
import ibf2022.ssf.ssfassessment.service.QuotationService;
import ibf2022.ssf.ssfassessment.model.Quotation;
import jakarta.servlet.http.HttpSession;

@RestController
public class PORestController {

    // @Autowired
    // private QuotationService qSvc;
    
    // @PostMapping(path = "/quotation", produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<String> postQuotation(
    //     HttpSession session
    // ) throws Exception {
    //     // By right, the cart should not be null
    //     Cart cart = (Cart) session.getAttribute("cart");
    //     if (null == cart) {
    //         cart = new Cart();
    //     }

    //     List<String> cartItemNameList = cart.createItemNameList();
    //     Quotation quotation = qSvc.getQuotations(cartItemNameList);

    //     return null;
    // }
}
