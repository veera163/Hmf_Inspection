package hmf.com.project.hmfinspection.Api;

import hmf.com.project.hmfinspection.domains.SimpleRes;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by home on 5/16/2018.
 */

public interface ImagesApi {

    @Multipart
    @POST("/upload/b2bInspectionImages/id/{id}")
    Call<SimpleRes> uploadImage(@Part MultipartBody.Part[] file, @Path("id") String id);
}
