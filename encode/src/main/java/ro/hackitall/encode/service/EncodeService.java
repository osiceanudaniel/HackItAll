package ro.hackitall.encode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.hackitall.encode.dal.EncodeDal;
import ro.hackitall.encode.util.AddProduct;
import ro.hackitall.encode.util.Category;
import ro.hackitall.encode.util.Product;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Andrei-Daniel Ene on 3/24/2018.
 */
@Service
public class EncodeService {
    @Autowired
    EncodeDal encodeDal;

    public List<Product> getAllMyProducts(String email) {
        return encodeDal.getAllMyProducts(email);
    }

    public List<Product> getAllOtherProducts(String email) {
        return encodeDal.getAllOtherProducts(email);
    }

    public List<Product> getMyProductsWithStatus(String status, String email) {
        return encodeDal.getMyProductsWithStatus(status, email);
    }

    public List<Product> getHistoryProducts(String email) {
        return null;
    }

    public List<Category> getCategories() {
        return encodeDal.getCategories();
    }

    public void addProduct(String email, int idCategory, String name, String quantity) {
        AddProduct product = new AddProduct();

        product.setIdUser(email);
        product.setIdCategory(idCategory);
        product.setIdStatus(2);
        product.setName(name);
        product.setQuantity(quantity);

        int daysToExpiration = encodeDal.getDaysToExpiration(idCategory);
        int daysToNotification = daysToExpiration / 2;

        String dateStart = "2018-03-25";
//        String dateStart = "CURDATE()";
        String dateNotification = "2018-03-25";
//        String dateNotification = "DATE_ADD(CURDATE(), INTERVAL + " + daysToNotification + " DAY)";
        String dateExpiration = "2018-03-25";
//        String dateExpiration = "DATE_ADD(CURDATE(), INTERVAL + " + daysToExpiration + " DAY)";

        product.setDateStart(dateStart);
        product.setDateNotification(dateNotification);
        product.setDateExpiration(dateExpiration);

        encodeDal.addProduct(product);
    }

    public List<String> getProductsNotificationToday() {
        Date date = new Date(System.currentTimeMillis());
//        String data = date.toString();
        String data = "2018-03-26";
        return encodeDal.getNotificationProduct(data);
    }

    public void updateProduct(int idProduct, String status) {
        encodeDal.updateProduct(idProduct, status);
    }
}
