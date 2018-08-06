package hmf.anyasoft.es.Hmf_inspection.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import hmf.anyasoft.es.Hmf_inspection.Api.IrigationApi;
import hmf.anyasoft.es.Hmf_inspection.AppController;
import hmf.anyasoft.es.Hmf_inspection.R;
import hmf.anyasoft.es.Hmf_inspection.domains.IrigationRes;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
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

public class IrigationAnalysisActivity extends AppCompatActivity implements OnChartValueSelectedListener, AdapterView.OnItemSelectedListener {

    public static final Integer[]PASTEL_COLORS = {
            Color.rgb(64, 89, 128), Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),
            Color.rgb(191, 134, 134), Color.rgb(179, 48, 80), Color.rgb(64, 89, 128), Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),
            Color.rgb(191, 134, 134), Color.rgb(179, 48, 80), Color.rgb(179, 48, 80)
    };

    Spinner irrigationtype;
    String type,url;
    PieData data;
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    List<String> dataaaaa;
    PieDataSet dataSet;
    List<String> xVals;

    PieChart pieChart;
    ArrayList<Entry> yvalues;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irigation);
        pieChart = (PieChart) findViewById(R.id.piechart);

        irrigationtype=(Spinner)findViewById(R.id.irrigationtype);
        irrigationtype.setOnItemSelectedListener(this);
        irrigationtype.setSelection(0);

        type= irrigationtype.getSelectedItem().toString();

        Irigation(type);

    }

    private void Irigation(String type) {
        url= AppController.BaseUrl+"irrigationAnalysis/crop/"+type;

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
        IrigationApi availabilityApi = retrofit.create(IrigationApi.class);
        Call<IrigationRes> listCall= availabilityApi.getData(url);
        listCall.enqueue(new Callback<IrigationRes>() {
            @Override
            public void onResponse(Call<IrigationRes> call, retrofit2.Response<IrigationRes> response) {
                if(response.isSuccessful()){

                    IrigationRes irigationRes=response.body();
                    if(irigationRes==null){

                    }
                    else {

                        if(irigationRes.getLabels()==null){

                        }
                        else  if(irigationRes.getLabels().size()==0){

                        }else {
                            xVals=irigationRes.getLabels();
                        }
                        if(irigationRes.getData()==null){

                        }
                        else  if(irigationRes.getData().size()==0){

                        }else {
                            dataaaaa=irigationRes.getData();
                        }

                        yvalues = new ArrayList<Entry>();


                        for(int i=0;i<dataaaaa.size();i++) {
                            Log.e("ram", dataaaaa.get(i) + "...." + i);
                            yvalues.add(new Entry(Integer.parseInt(dataaaaa.get(i)), i));
                        }

                        dataSet = new PieDataSet(yvalues, "Labels");

                        data = new PieData(xVals, dataSet);


                          /*  Legend l = pieChart.getLegend();
                            l.setFormSize(10f); // set the size of the legend forms/shapes
                            l.setForm(Legend.LegendForm.SQUARE); // set what type of form/shape should be used
                            l.setTextSize(12f);
                            l.setTextColor(Color.BLACK);
                            l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
                            l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis
                            l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);

                            List<Integer> coloras = Arrays.asList(PASTEL_COLORS);

                            l.setCustom(coloras,xVals);
*/
                        // data.setValueFormatter(new PercentFormatter());
                        pieChart.setData(data);
                        pieChart.setDescription("");
                        pieChart.getLegend().setEnabled(false);   // Hide the legend

                        pieChart.setDrawHoleEnabled(true);
                        pieChart.setTransparentCircleRadius(25f);
                        pieChart.setHoleRadius(25f);

                        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
                        data.setValueTextSize(13f);
                        data.setValueTextColor(Color.WHITE);
                        pieChart.setOnChartValueSelectedListener(IrigationAnalysisActivity.this);
                        pieChart.animateXY(1400, 1400);
                    }

                }
            }

            @Override
            public void onFailure(Call<IrigationRes> call, Throwable t) {

                Toast.makeText(IrigationAnalysisActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)

            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        irrigationtype = (Spinner) parent;
        if (irrigationtype.getId() == R.id.irrigationtype) {
            type = (String) parent.getItemAtPosition(position);
            Irigation(type);

        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
