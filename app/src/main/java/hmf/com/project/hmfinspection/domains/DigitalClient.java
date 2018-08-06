package hmf.com.project.hmfinspection.domains;

import hmf.com.project.hmfinspection.Api.DigitalSignApi;
import hmf.com.project.hmfinspection.AppController;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by home on 5/15/2018.
 */

public class DigitalClient {


    private static final String ROOT_URL = AppController.BaseUrl;


    public DigitalClient() {

    }
    private static Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static DigitalSignApi getApiService() {

        return getRetroClient().create(DigitalSignApi.class);
    }
}
