package hmf.com.project.hmfinspection.Api;

import hmf.com.project.hmfinspection.domains.ConsumerFormRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by home on 5/14/2018.
 */

public interface ConsumerFormListApi {

    @GET
    Call<ConsumerFormRes> getData(@Url String url);
}
