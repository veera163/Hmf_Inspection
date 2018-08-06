package hmf.com.project.hmfinspection.Api;


import hmf.com.project.hmfinspection.domains.InspectionPendingRes;
import hmf.com.project.hmfinspection.domains.SimpleRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

/**
 * Created by home on 5/9/2018.
 */

public interface PendingUpdateApi {

    @Headers("Content-Type: application/json")
    @PUT("/b2bInspection/")
    Call<SimpleRes> getUser(@Body InspectionPendingRes body);
}
