package ibf2022.ssf.ssfassessment.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PORestController {
    
    @PostMapping(path = "/quotation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postQuotation() {
        return null;
    }
}
