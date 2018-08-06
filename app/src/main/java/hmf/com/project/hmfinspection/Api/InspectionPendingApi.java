package hmf.com.project.hmfinspection.Api;


import hmf.com.project.hmfinspection.domains.InspectionPendingRes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by home on 5/8/2018.
 */

public interface InspectionPendingApi {

    @GET
    Call<List<InspectionPendingRes>> getData(@Url String url);
}
