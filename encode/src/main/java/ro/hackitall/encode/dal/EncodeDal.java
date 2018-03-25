package ro.hackitall.encode.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ro.hackitall.encode.util.AddProduct;
import ro.hackitall.encode.util.Category;
import ro.hackitall.encode.util.Product;

import java.util.List;

/**
 * Created by Andrei-Daniel Ene on 3/24/2018.
 */
@Component
public class EncodeDal {

    private static final String ID_PROD = "Id_product";
    private static final String ID_CATEGORY = "Id_category";
    private static final String ID_STATUS = "Id_satus";
    private static final String NAME = "product_name";
    private static final String QUANTITY = "quantity";
    private static final String DATE_START = "start_date";
    private static final String DATE_NOTIFICATION = "notification_date";
    private static final String DATE_EXPIRATION = "expiration_date";
    private static final String EXPIRATION_DAY = "expiration_day";
    private static final String NAME_CATEGORY = "name";
    private static final String USER_EMAIL = "user_email";

    private static final String PRODUCT_TABLE = "product";
    private static final String CATEGORY_TABLE = "category";

    private static final String GET_MY_PRODUCTS = "SELECT * FROM " + PRODUCT_TABLE + " WHERE " + USER_EMAIL + " = ";
    private static final String GET_ALL_PRODUCTS = "SELECT * FROM " + PRODUCT_TABLE;
    private static final String GET_ALL_MY_PRODUCTS = "SELECT * FROM " + PRODUCT_TABLE + " WHERE " + ID_STATUS + " != 1 AND " + USER_EMAIL +" = ";
    private static final String GET_ALL_PRODUCTS_DONE = "SELECT * FROM " + PRODUCT_TABLE + " WHERE " + ID_STATUS + "=1";
    private static final String GET_ALL_PRODUCTS_SHARE = "SELECT * FROM " + PRODUCT_TABLE + " WHERE " + ID_STATUS + "=3 AND " + USER_EMAIL + " != " ;
    private static final String GET_ALL_PRODUCTS_PENDING = "SELECT * FROM " + PRODUCT_TABLE + " WHERE " + ID_STATUS + "=2";

    public static final String GET_CATEGORY = "SELECT * FROM " + CATEGORY_TABLE;
    public static final String GET_CATEGORY_DAYS = "SELECT * FROM " + CATEGORY_TABLE + " WHERE " + ID_CATEGORY + " = ";

    public static final String GET_NOTIFICATION_PRODUCTS = "SELECT product_name FROM " + PRODUCT_TABLE + " WHERE Id_satus=2 AND notification_date = ";

    private static final String ADD_PRODUCT = "INSERT INTO product ("+
            USER_EMAIL+", "+
            ID_CATEGORY+", "+
            ID_STATUS+", "+
            NAME+", "+
            QUANTITY+", "+
            DATE_START+", "+
            DATE_NOTIFICATION+", "+
            DATE_EXPIRATION+
            ") VALUES (?,?,?,?,?,?,?,?)";

    private static final String UPDATE_PRODUCT = "UPDATE product SET Id_satus= ";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    // --------------- PRODUCTS -------------------
    // ------------------ ALL ---------------------
    public List<Product> getAllMyProducts(String email) {
        return jdbcTemplate.query(
                String.format("%s'%s'", GET_ALL_MY_PRODUCTS, email),
                (rs, rowNum) -> new Product(
                        rs.getInt(ID_PROD),
                        rs.getString(USER_EMAIL),
                        rs.getInt(ID_CATEGORY),
                        rs.getInt(ID_STATUS),
                        rs.getString(NAME),
                        rs.getString(QUANTITY),
                        rs.getDate(DATE_START),
                        rs.getDate(DATE_NOTIFICATION),
                        rs.getDate(DATE_EXPIRATION))
        );
    }
/*    public List<Product> getAllProducts() {
        return jdbcTemplate.query(
                GET_ALL_PRODUCTS,
                (rs, rowNum) -> new Product(
                        rs.getInt(ID_PROD),
                        rs.getInt(ID_USER),
                        rs.getInt(ID_CATEGORY),
                        rs.getInt(ID_STATUS),
                        rs.getString(NAME),
                        rs.getString(QUANTITY),
                        rs.getDate(DATE_START),
                        rs.getDate(DATE_NOTIFICATION),
                        rs.getDate(DATE_EXPIRATION))
        );
    }*/
    public List<Product> getAllOtherProducts(String email) {
        return jdbcTemplate.query(
                String.format("%s'%s'", GET_ALL_PRODUCTS_SHARE, email),
                (rs, rowNum) -> new Product(
                        rs.getInt(ID_PROD),
                        rs.getString(USER_EMAIL),
                        rs.getInt(ID_CATEGORY),
                        rs.getInt(ID_STATUS),
                        rs.getString(NAME),
                        rs.getString(QUANTITY),
                        rs.getDate(DATE_START),
                        rs.getDate(DATE_NOTIFICATION),
                        rs.getDate(DATE_EXPIRATION))
        );
    }
    // ---------------- STATUS --------------------
    public List<Product> getAllProductsStatus(String status) {

        String sql;
        switch (status) {
            case "DONE":
                sql = GET_ALL_PRODUCTS_DONE;
                break;
            case "SHARE":
                sql = GET_ALL_PRODUCTS_SHARE;
                break;
            case "PENDING":
                sql = GET_ALL_PRODUCTS_PENDING;
                break;
            default:
                sql = GET_ALL_PRODUCTS;
                break;
        }
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Product(
                        rs.getInt(ID_PROD),
                        rs.getString(USER_EMAIL),
                        rs.getInt(ID_CATEGORY),
                        rs.getInt(ID_STATUS),
                        rs.getString(NAME),
                        rs.getString(QUANTITY),
                        rs.getDate(DATE_START),
                        rs.getDate(DATE_NOTIFICATION),
                        rs.getDate(DATE_EXPIRATION))
        );
    }
    // ------------------ ADD ---------------------
    public void addProduct(AddProduct product) {
        jdbcTemplate.update(
                ADD_PRODUCT,
                product.getIdUser(),
                product.getIdCategory(),
                product.getIdStatus(),
                product.getName(),
                product.getQuantity(),
                product.getDateStart(),
                product.getDateNotification(),
                product.getDateExpiration()
        );
    }

    // --------------- CATEGORY -------------------
    public List<Category> getCategories() {
        return jdbcTemplate.query(
                GET_CATEGORY,
                (rs, rowNum) -> new Category(
                        rs.getInt(ID_CATEGORY),
                        rs.getString(NAME_CATEGORY),
                        rs.getInt(EXPIRATION_DAY))
        );
    }

    public List<Product> getMyProductsWithStatus(String status, String email) {

        String statusCode;
        switch (status) {
            case "done":
                statusCode = "1";
                break;
            case "share":
                statusCode = "2";
                break;
            case "pending":
                statusCode = "3";
                break;
            default:
                statusCode = "1";
                break;
        }

        return jdbcTemplate.query(
                String.format("%s'%s'%s%s", GET_MY_PRODUCTS, email," AND " + ID_STATUS + " = ",statusCode),
                (rs, rowNum) -> new Product(
                        rs.getInt(ID_PROD),
                        rs.getString(USER_EMAIL),
                        rs.getInt(ID_CATEGORY),
                        rs.getInt(ID_STATUS),
                        rs.getString(NAME),
                        rs.getString(QUANTITY),
                        rs.getDate(DATE_START),
                        rs.getDate(DATE_NOTIFICATION),
                        rs.getDate(DATE_EXPIRATION))
        );
    }

    public int getDaysToExpiration(int idCategory) {
        List<Category> categories = jdbcTemplate.query(
                String.format("%s%s", GET_CATEGORY_DAYS, idCategory),
                (rs, rowNum) -> new Category(
                        rs.getInt(ID_CATEGORY),
                        rs.getString(NAME_CATEGORY),
                        rs.getInt(EXPIRATION_DAY))
        );
        return categories.get(0).getDayExpiration();
    }

    public List<String> getNotificationProduct(String date) {
        return jdbcTemplate.query(
                String.format("%s\"%s\"", GET_NOTIFICATION_PRODUCTS, date),
                (rs, rowNum) -> new String(rs.getString(NAME))
        );
    }

    public void updateProduct(int idProduct, String status) {
        jdbcTemplate.execute(String.format("%s%s%s%s",UPDATE_PRODUCT,status," WHERE Id_product = ",idProduct));
    }
}
