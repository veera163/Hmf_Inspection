package hmf.com.project.hmfinspection.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import hmf.com.project.hmfinspection.Api.YeildApi;
import hmf.com.project.hmfinspection.AppController;
import hmf.com.project.hmfinspection.ESurvey;
import hmf.com.project.hmfinspection.R;
import hmf.com.project.hmfinspection.domains.YeildRes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by home on 5/7/2018.
 */

public class YeildFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner spcroptype,useagetype;
    String type,code,quntity,utype;
    TextView cropcode,rescroptype,rescropcode,resquntity,cost,useage;
    EditText ed_quntity;
    Button submit;
    private ProgressDialog progressDialog;

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_yield, container, false);
        spcroptype = (Spinner)view.findViewById(R.id.spcroptype);
        useagetype=(Spinner)view.findViewById(R.id.usagetype);
        cropcode=(TextView)view.findViewById(R.id.cropcode);
        ed_quntity=(EditText)view.findViewById(R.id.quantity);

        rescroptype=(TextView) view.findViewById(R.id.rescroptype);
        rescropcode=(TextView)view.findViewById(R.id.rescropcode);
        resquntity=(TextView)view.findViewById(R.id.resquntity);

        cost=(TextView)view.findViewById(R.id.cost);
        useage=(TextView)view.findViewById(R.id.txuseage);


        submit=(Button) view.findViewById(R.id.submit);

        spcroptype.setOnItemSelectedListener(this);
        useagetype.setOnItemSelectedListener(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean isValid = true;
                View focusView = null;
                ed_quntity.setError(null);
                quntity=ed_quntity.getText().toString();
                code=cropcode.getText().toString();

                if (TextUtils.isEmpty(quntity)) {
                    ed_quntity.setError(getString(R.string.error_field_required));
                    focusView = ed_quntity;
                    isValid = false;
                }
                if (isValid){

                    yeild(type,code,quntity,utype);

                }
                else {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                }
            }
        });

        return view;
    }

    private void yeild(String type, String code, String quntity, final String utype) {

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("authorization","Bearer " + ESurvey.getAccessToken())
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
        progressDialog = ProgressDialog.show(getContext(), "Please wait.",
                "Loading Data..!", true);
        YeildApi yeildApi = retrofit.create(YeildApi.class);;
        YeildRes ve=new YeildRes();
        ve.setCropName(type);
        ve.setCropdCode(code);
        ve.setExpectedQuantity(quntity);
        ve.setUsage(utype);
        ve.setProductionCost("");
        ve.setProductionCostForResale("");
        Log.e("chinna",ve.toString());

        Call<YeildRes> listCall= yeildApi.getUser(ve);
        listCall.enqueue(new Callback<YeildRes>() {
            @Override
            public void onResponse(Call<YeildRes> call, retrofit2.Response<YeildRes> response) {

                if(response.isSuccessful()){

                    progressDialog.dismiss();
                    YeildRes yeildRes=response.body();

                    Log.e("veera",response.body().toString());
                    if(yeildRes==null){

                    }else {

                        rescroptype.setText(yeildRes.getCropName());
                        rescropcode.setText(yeildRes.getCropdCode());
                        resquntity.setText(yeildRes.getExpectedQuantity());
                        useage.setText(yeildRes.getUsage());

                        if(utype.equals("Own")){
                            cost.setText(yeildRes.getProductionCost());

                        }
                        else {
                            cost.setText(yeildRes.getProductionCostForResale());

                        }

                    }

                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),response.message(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<YeildRes> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spcroptype = (Spinner) adapterView;
        useagetype=(Spinner) adapterView;
        if (spcroptype.getId() == R.id.spcroptype) {
            type = (String) adapterView.getItemAtPosition(i);
            if(type.equals("REDGRAM")){
                cropcode.setText("10504");
            }
            else   if(type.equals("BLACKGRAM")){
                cropcode.setText("10503");
            }
            else   if(type.equals("GREENGRAM")){
                cropcode.setText("10502");
            }
            else   if(type.equals("SESAMUM")){
                cropcode.setText("20302");
            }
            else {

                Toast.makeText(getContext(), "Some Data is Missing", Toast.LENGTH_SHORT).show();
            }

        }
        else  if(useagetype.getId()==R.id.usagetype){

            utype = (String) adapterView.getItemAtPosition(i);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
