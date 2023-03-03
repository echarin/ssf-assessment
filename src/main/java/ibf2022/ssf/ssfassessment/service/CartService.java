package ibf2022.ssf.ssfassessment.service;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;

import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import ibf2022.ssf.ssfassessment.model.Item;

@Service
public class CartService {
    public static final String[] ITEM_NAMES = {
        "apple", "orange", "bread", "cheese", "chicken", "mineral water", "instant noodles"
    };

    public Set<String> setOfItemNames;

    public CartService() {
        setOfItemNames = new HashSet<>(Arrays.asList(ITEM_NAMES));
    }

    public List<ObjectError> validateItem(Item item) {
        List<ObjectError> errors = new LinkedList<>();
        FieldError error;

        if (!setOfItemNames.contains(item.getName().toLowerCase())) {
            error = new FieldError("item", "name", "We do not stock %s".formatted(item.getName()));
            errors.add(error);
        }

        return errors;
    }
}
