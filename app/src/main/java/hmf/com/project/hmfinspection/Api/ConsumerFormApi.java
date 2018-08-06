package hmf.com.project.hmfinspection.Api;


import hmf.com.project.hmfinspection.domains.ConsumerFormRes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by home on 5/11/2018.
 */

public interface ConsumerFormApi {

    @GET
    Call<List<ConsumerFormRes>> getData(@Url String url);
}
