package hmf.com.project.hmfinspection.Api;

import hmf.com.project.hmfinspection.domains.InspectionPendingRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by home on 5/10/2018.
 */

public interface FormApi {

    @GET
    Call<InspectionPendingRes> getData(@Url String url);
}
