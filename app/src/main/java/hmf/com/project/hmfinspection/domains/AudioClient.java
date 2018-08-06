package hmf.com.project.hmfinspection.domains;

import hmf.com.project.hmfinspection.Api.AudioApi;
import hmf.com.project.hmfinspection.AppController;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by home on 5/16/2018.
 */

public class AudioClient {

    private static final String ROOT_URL = AppController.BaseUrl;


    public AudioClient() {

    }
    private static Retrofit getRetroClient() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static AudioApi getApiService() {

        return getRetroClient().create(AudioApi.class);
    }

}
