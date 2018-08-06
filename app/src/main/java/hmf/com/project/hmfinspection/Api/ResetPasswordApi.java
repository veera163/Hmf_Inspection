package hmf.com.project.hmfcustomer.Api;

import hmf.com.project.hmfcustomer.domains.SimpleRes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by home on 6/21/2018.
 */

public interface ResetPasswordApi {

    @Headers("Content-Type: application/json")
    @POST("/userManager/resetPassword")
    Call<SimpleRes> getUser(@Body String body);
}
