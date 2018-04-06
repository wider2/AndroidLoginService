package april.aprilappl.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import april.aprilappl.model.ModelLogin;
import april.aprilappl.model.ModelRegister;


@Dao
public interface LoginDao {

    @Insert
    public abstract void insertLogin(ModelLogin modelLogin);

    @Query("SELECT * FROM ModelLogin ORDER BY last_visit DESC")
    ModelLogin loadLastLogin();

    @Query("SELECT * FROM ModelLogin ORDER BY last_visit DESC LIMIT 2")
    List<ModelLogin> loadRecentLogins();

    @Query("SELECT * FROM ModelLogin WHERE username = :username")
    ModelLogin loadLoginByUsername(String username);

    @Query("SELECT * FROM ModelLogin WHERE last_visit > :last_visit")
    List<ModelLogin> checkLastVisits(long last_visit);


    @Insert
    public abstract void insertRegister(ModelRegister modelRegister);

    @Query("SELECT * FROM ModelRegister ORDER BY date_register DESC LIMIT 1")
    ModelRegister loadLastRegistration();

    @Query("SELECT * FROM ModelRegister WHERE username = :username")
    ModelRegister loadByUsername(String username);

    @Query("UPDATE ModelRegister SET city = :city WHERE username = :username")
    void updateRegistration(String username, String city);

}
