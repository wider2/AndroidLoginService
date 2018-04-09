package april.aprilappl.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ModelLogin {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "last_visit")
    private long lastVisit;

    public void setModelLogin(String username, String password, long lastVisit) {
        this.username = username;
        this.password = password;
        this.lastVisit = lastVisit;
    }

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

    public long getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(long lastVisit) {
        this.lastVisit = lastVisit;
    }

}