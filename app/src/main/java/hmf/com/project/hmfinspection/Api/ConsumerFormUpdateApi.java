package hmf.com.project.hmfinspection.Api;

import hmf.com.project.hmfinspection.domains.ConsumerFormRes;
import hmf.com.project.hmfinspection.domains.SimpleRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

/**
 * Created by home on 5/14/2018.
 */

public interface ConsumerFormUpdateApi {

    @Headers("Content-Type: application/json")
    @PUT("/b2cInspection/")
    Call<SimpleRes> getUser(@Body ConsumerFormRes consumerFormRes);
}
