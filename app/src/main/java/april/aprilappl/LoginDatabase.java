package april.aprilappl;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import april.aprilappl.dao.LoginDao;
import april.aprilappl.model.ModelLogin;
import april.aprilappl.model.ModelRegister;


@Database(entities = {ModelLogin.class, ModelRegister.class}, version = 1, exportSchema = false)
public abstract class LoginDatabase extends RoomDatabase {

    private static volatile LoginDatabase INSTANCE;

    public abstract LoginDao loginDao();

    public static LoginDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LoginDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LoginDatabase.class, "DatabaseLogin.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

