package ibf2022.ssf.ssfassessment.service;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import ibf2022.ssf.ssfassessment.model.Quotation;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

@Service
public class QuotationService {

    private static final String QSYS_ENDPOINT = "https://quotation.chuklee.com/quotation";
    
    public Quotation getQuotations(List<String> items) throws Exception {
        Quotation quotation = new Quotation();

        // Build the body
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (String i : items) {
            arrBuilder.add(i);
        }

        JsonArray itemNamesArr = arrBuilder.build();
        
        // Build the POST request
        RequestEntity<String> req = RequestEntity
            .post(QSYS_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(itemNamesArr.toString(), String.class);

        // REST template
        RestTemplate rt = new RestTemplate();

        // Make the request
        ResponseEntity<String> resp = null;
        String payload = null;
        JsonObject payloadJson = null;
        String error = null;

        try {
            resp = rt.exchange(req, String.class);
        } catch (HttpClientErrorException ex) {
            payload = ex.getResponseBodyAsString();
            payloadJson = Json.createReader(new StringReader(payload)).readObject();
            error = payloadJson.getString("error");
            throw new Exception(error);
        }

        // Check status code and payload
        payload = resp.getBody();

        if (null == payload) {
            throw new Exception("Payload received was null");
        }

        payloadJson = Json.createReader(new StringReader(payload)).readObject();

        // Parse the JSON object
        Optional<JsonValue> optErrorMsg = Optional.ofNullable(payloadJson.get("error"));
        
        // If there is an error, then throw exception with the error message
        if (optErrorMsg.isPresent()) {
            error = payloadJson.getString("error");
            throw new Exception(error);
        }

        String quoteId = payloadJson.getString("quoteId");
        quotation.setQuoteId(quoteId);

        String item = null;
        Float unitPrice = null;
        JsonObject quotationObj = null; 
        JsonArray quotationsArr = payloadJson.getJsonArray("quotations");
        for (int i = 0; i < quotationsArr.size(); i++) {
            quotationObj = quotationsArr.getJsonObject(i);
            item = quotationObj.getString("item");
            unitPrice = Float.parseFloat(quotationObj.get("unitPrice").toString());
            quotation.addQuotation(item, unitPrice);
        }

        return quotation;
    }
}
