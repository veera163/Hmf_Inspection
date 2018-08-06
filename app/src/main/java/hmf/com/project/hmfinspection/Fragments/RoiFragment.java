package hmf.com.project.hmfinspection.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import hmf.com.project.hmfinspection.Api.RoiApi;
import hmf.com.project.hmfinspection.AppController;
import hmf.com.project.hmfinspection.ESurvey;
import hmf.com.project.hmfinspection.R;
import hmf.com.project.hmfinspection.domains.RoiRes;
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

public class RoiFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner spcroptype;
    String type,code,amount;
    TextView cropcode,rescroptype,rescropcode,resamount,resroi;
    EditText ed_amount;
    Button submit;
    private ProgressDialog progressDialog;

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_roi, container, false);
        spcroptype = (Spinner)view.findViewById(R.id.spcroptype);
        cropcode=(TextView)view.findViewById(R.id.cropcode);
        ed_amount=(EditText)view.findViewById(R.id.amount);
        rescroptype=(TextView) view.findViewById(R.id.rescroptype);
        rescropcode=(TextView)view.findViewById(R.id.rescropcode);
        resamount=(TextView)view.findViewById(R.id.resamount);
        resroi=(TextView)view.findViewById(R.id.resroi);


        submit=(Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean isValid = true;
                View focusView = null;
                ed_amount.setError(null);
                amount=ed_amount.getText().toString();
                code=cropcode.getText().toString();

                if (TextUtils.isEmpty(amount)) {
                    ed_amount.setError(getString(hmf.com.project.hmfinspection.R.string.error_field_required));
                    focusView = ed_amount;
                    isValid = false;
                }
                if (isValid){

                    roi(type,code,amount);


                }
                else {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                }
            }
        });

        spcroptype.setOnItemSelectedListener(this);
        return view;
    }

    private void roi(String type, String code, String amount) {

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
        RoiApi roiApi = retrofit.create(RoiApi.class);;
        RoiRes ve=new RoiRes();
        ve.setCropName(type);
        ve.setCropdCode(code);
        ve.setAmountInvested(amount);
        Call<RoiRes> listCall= roiApi.getUser(ve);
        listCall.enqueue(new Callback<RoiRes>() {
            @Override
            public void onResponse(Call<RoiRes> call, retrofit2.Response<RoiRes> response) {

                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    RoiRes roiRes=response.body();
                    if(roiRes==null){

                    }else {

                        rescroptype.setText(roiRes.getCropName());
                        rescropcode.setText(roiRes.getCropdCode());
                        resamount.setText(roiRes.getAmountInvested());
                        resroi.setText(" ₹ "+roiRes.getRoiOnCrop());

                    }

                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),response.message(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<RoiRes> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spcroptype = (Spinner) adapterView;
        if (spcroptype.getId() == hmf.com.project.hmfinspection.R.id.spcroptype) {
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
           /* else   if(type.equals("SESAMUM")){
                cropcode.setText("20302");
            }*/
            else {

                Toast.makeText(getContext(), "Some Data is Missing", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
