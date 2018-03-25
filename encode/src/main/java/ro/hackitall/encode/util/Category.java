package ro.hackitall.encode.util;

/**
 * Created by Andrei-Daniel Ene on 3/24/2018.
 */
public class Category {
    private int idCategory;
    private String name;
    private int dayExpiration;

    public Category() {}

    public Category(int idCategory, String name, int dayExpiration) {
        this.idCategory = idCategory;
        this.name = name;
        this.dayExpiration = dayExpiration;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDayExpiration() {
        return dayExpiration;
    }

    public void setDayExpiration(int dayExpiration) {
        this.dayExpiration = dayExpiration;
    }
}
