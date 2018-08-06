package hmf.com.project.hmfinspection.Api;


import hmf.com.project.hmfinspection.domains.YeildRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by home on 5/7/2018.
 */

public interface YeildApi {


    @Headers("Content-Type: application/json")
    @POST("yieldEstimation/getProductionCost")
    Call<YeildRes> getUser(@Body YeildRes body);
}
