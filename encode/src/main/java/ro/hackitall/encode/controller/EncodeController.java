package ro.hackitall.encode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ro.hackitall.encode.service.EncodeService;
import ro.hackitall.encode.util.Category;
import ro.hackitall.encode.util.Customer;
import ro.hackitall.encode.util.Product;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Andrei-Daniel Ene on 3/24/2018.
 */
@RestController
public class EncodeController {

    @Autowired
    EncodeService encodeService;

    @Value("${firebase.database.url}")
    String firebaseUrl;

    @Value("${send.email}")
    String uri;

    public static final String USER = "user";
    private static final String EMAIL = "user@encode.com";

    @RequestMapping(value = "/api/signin", method = RequestMethod.POST)
    public ResponseEntity signIn(
            @RequestParam(value = "X-Authorization-Firebase", required = false) String idToken,
            HttpSession session) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = (Customer) authentication.getPrincipal();

        session.setAttribute(USER, customer.getEmail());

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(uri + customer.getEmail(), String.class);

        System.out.println("--- SignIn with user: " + customer.getEmail());

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/myproducts", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<Product>> getMyProducts(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "email", required = false) String email) {

        List<Product> response = null;

        if(email == null ) {
            email = EMAIL;
        }
        if(status == null) {
            System.out.println("--- Get My Products for user: " + email);
            response = encodeService.getAllMyProducts(email);
        } else {
            System.out.println("--- Get My Products with status: " + status);
            response = encodeService.getMyProductsWithStatus(status,email);
        }

        return new ResponseEntity(response,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/otherproducts", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<Product>> getOtherProducts(
            @RequestParam(value = "email", required = false) String email) {
        List<Product> response = null;

        if(email == null ) {
            email = EMAIL;
        }

        System.out.println("--- Get products from other users ...");
        response = encodeService.getAllOtherProducts(email);

        return new ResponseEntity(response,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/categories", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<Product>> getCategories() {
        List<Category> response = null;

        System.out.println("--- Get all categories ...");
        response = encodeService.getCategories();

        return new ResponseEntity(response,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/addproduct", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Void> addProduct(
            @RequestParam(value = "id_category", required = false, defaultValue = "1") int idCategory,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "quantity", required = false) String quantity,
            @RequestParam(value = "email", required = false) String email
    ) {

        System.out.println("--- Add new Product ...");
        if(name == null) {
            name = "Portocale";
        }
        if(quantity == null) {
            quantity = "1 kg";
        }
        if(email == null ) {
            email = EMAIL;
        }

        encodeService.addProduct(email, idCategory, name, quantity);

        System.out.println("--- Product added :)");
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/notificationday", method = RequestMethod.GET)
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Integer> getProductsNotificationToday() {
        List<String> response = null;

        response = encodeService.getProductsNotificationToday();

        int ceva = 1;
        if(response.isEmpty()) {
            ceva = 0;
        }
        System.out.println("--- Check for notification : " + ceva);
        return new ResponseEntity(ceva,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/updatestatus", method = RequestMethod.POST)
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<List<Product>> updateStatus(
            @RequestParam(value = "id_product", required = false, defaultValue = "1") int idProduct,
            @RequestParam(value = "status", required = false, defaultValue = "done") String status
    ) {

        System.out.println("--- Update Product Status...");
        String statusCode;
        switch (status) {
            case "done":
                statusCode = "1";
                break;
            case "pending":
                statusCode = "2";
                break;
            case "share":
                statusCode = "3";
                break;
            default:
                statusCode = "1";
                break;
        }

        encodeService.updateProduct(idProduct, statusCode);

        System.out.println("--- Update Complete :)");
        return new ResponseEntity(HttpStatus.OK);
    }

}
