package april.aprilappl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
 
public class PostRegister {
 
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("postal_code")
    @Expose
    private String postal_code;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

}