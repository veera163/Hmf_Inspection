package hmf.com.project.hmfinspection.Api;

import java.util.List;


import hmf.com.project.hmfinspection.domains.FilterInfo;
import hmf.com.project.hmfinspection.domains.WareHouseInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by home on 7/6/2018.
 */

public interface FilterApi {

    @Headers("Content-Type: application/json")
    @POST("/warehouseDetails/getWarehouseDetailsBySearchFilter")
    Call<List<WareHouseInfo>> getUser(@Body FilterInfo filterInfo);
}
