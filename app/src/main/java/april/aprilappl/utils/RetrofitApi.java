package april.aprilappl.utils;

import april.aprilappl.model.ModelResponse;
import april.aprilappl.model.PostLogin;
import april.aprilappl.model.PostRegister;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitApi {

    @Headers("Content-Type: application/json")
    @POST("/login")
    Observable<ModelResponse> sendLoginPost(@Body PostLogin postLogin);


    @Headers("Content-Type: application/json")
    @POST("/addUser")
    Observable<ModelResponse> postRegister(@Body PostRegister postRegister);

}