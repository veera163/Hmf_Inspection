package hmf.anyasoft.es.Hmf_inspection.Api;


import hmf.anyasoft.es.Hmf_inspection.domains.IrigationRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by home on 5/3/2018.
 */

public interface IrigationApi {

    @GET
    Call<IrigationRes> getData(@Url String url);
}
