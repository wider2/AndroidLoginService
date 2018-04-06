package april.aprilappl.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ModelRegister {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "date_register")
    private long dateRegister;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "zip")
    private String zip;

    @ColumnInfo(name = "country")
    private String country;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(long dateRegister) {
        this.dateRegister = dateRegister;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}