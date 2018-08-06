package hmf.com.project.hmfinspection.domains;

import hmf.com.project.hmfinspection.Api.ImagesApi;
import hmf.com.project.hmfinspection.AppController;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by home on 5/16/2018.
 */

public class ImagesClient {


    /**
     * Upload URL of your folder with php file name...
     * You will find this file in php_upload folder in this project
     * You can copy that folder and paste in your htdocs folder...
     */
    private static final String ROOT_URL = AppController.BaseUrl;


    public ImagesClient() {

    }
    private static Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ImagesApi getApiService() {

        return getRetroClient().create(ImagesApi.class);
    }
}
