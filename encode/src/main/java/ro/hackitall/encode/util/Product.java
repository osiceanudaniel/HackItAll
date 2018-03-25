package ro.hackitall.encode.util;

import java.sql.Date;

/**
 * Created by Andrei-Daniel Ene on 3/24/2018.
 */
public class Product {
    private int idProd;
    private String idUser;
    private int idCategory;
    private int idStatus;
    private String name;
    private String quantity;
    private Date dateStart;
    private Date dateNotification;
    private Date dateExpiration;

    public Product(int idProd, String idUser, int idCategory, int idStatus, String name, String quantity, Date dateStart, Date dateNotification, Date dateExpiration) {
        this.idProd = idProd;
        this.idUser = idUser;
        this.idCategory = idCategory;
        this.idStatus = idStatus;
        this.name = name;
        this.quantity = quantity;
        this.dateStart = dateStart;
        this.dateNotification = dateNotification;
        this.dateExpiration = dateExpiration;
    }

    public Product() {}

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateNotification() {
        return dateNotification;
    }

    public void setDateNotification(Date dateNotification) {
        this.dateNotification = dateNotification;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }
}
