package hmf.anyasoft.es.Hmf_inspection.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;


import hmf.anyasoft.es.Hmf_inspection.Api.CropApi;
import hmf.anyasoft.es.Hmf_inspection.AppController;
import hmf.anyasoft.es.Hmf_inspection.domains.CropRes;
import hmf.anyasoft.es.Hmf_inspection.domains.Dataset;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by home on 5/3/2018.
 */

public class CropAnalysisActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    Spinner irrigationtype;
    String type, url;
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    BarChart barChart;
    BarData data;
    BarDataSet barDataSet;
    List<String> labels;
    List<IBarDataSet> dataSets;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(hmf.anyasoft.es.Hmf_inspection.R.layout.activity_crop);
        barChart = (BarChart) findViewById(hmf.anyasoft.es.Hmf_inspection.R.id.barchart);

        irrigationtype = (Spinner) findViewById(hmf.anyasoft.es.Hmf_inspection.R.id.irrigationtype);
        irrigationtype.setOnItemSelectedListener(this);
        irrigationtype.setSelection(0);
        type= irrigationtype.getSelectedItem().toString();
        crop(type);

    }

    private void crop(String type) {

        url = AppController.BaseUrl+"cropAnalysis/yearwise/crop/"+type;
        Log.e("url",url);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("content-type", "application/json")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppController.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        CropApi availabilityApi = retrofit.create(CropApi.class);
        Call<CropRes> listCall = availabilityApi.getData(url);
        listCall.enqueue(new Callback<CropRes>() {
            @Override
            public void onResponse(Call<CropRes> call, retrofit2.Response<CropRes> response) {
                if (response.isSuccessful()) {

                    CropRes cropRes = response.body();

                    if (cropRes == null) {

                    } else {
                         labels = cropRes.getLabels();

                         dataSets = new ArrayList<>();
                        int j=1;
                        for (Dataset dataset : cropRes.getDatasets()) {

                            List<BarEntry> barGroup = new ArrayList<>();
                            int i = 0;
                            for (Integer integer : dataset.getData()) {

                                barGroup.add(new BarEntry(integer, i));
                                ++i;
                            }

                             barDataSet = new BarDataSet(barGroup, dataset.getLabel());
                            barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
                            ++j;
                            dataSets.add(barDataSet);
                        }
                        data = new BarData(labels, dataSets);
                        barChart.setData(data);
                        barChart.getLegend().setEnabled(false);   // Hide the legend

                        barChart.setDescription("");
                        barChart.animateY(5000);



                    }
                }
            }

            @Override
            public void onFailure(Call<CropRes> call, Throwable t) {

                Toast.makeText(CropAnalysisActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        irrigationtype = (Spinner) parent;
        if (irrigationtype.getId() == hmf.anyasoft.es.Hmf_inspection.R.id.irrigationtype) {
            type = (String) parent.getItemAtPosition(position);
            crop(type);
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
