package hmf.com.project.hmfinspection.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import hmf.com.project.hmfinspection.Api.ConsumerFormListApi;
import hmf.com.project.hmfinspection.Api.ConsumerFormUpdateApi;
import hmf.com.project.hmfinspection.AppController;
import hmf.com.project.hmfinspection.ESurvey;
import hmf.com.project.hmfinspection.R;
import hmf.com.project.hmfinspection.domains.ConsumerFormRes;
import hmf.com.project.hmfinspection.domains.SimpleRes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by home on 5/15/2018.
 */

public class ConsumerFormUpadteActivity extends AppCompatActivity {

    ConsumerFormRes consumerFormRes;

    List<String> gridlist,croplist,processedlist,pofessionlist,planlist,acklist;
    ArrayAdapter<String> gridAdapter,investcropAdpter,processedAdpter,professionAdpter,planAdpter,ackAdpter;
    Calendar myCalendar;
    private ProgressDialog progressDialog;
    public Spinner spgridtype,spinvestcroptype,spcurrentprocess,spprofession,spinvestplan,spacktype;

    String url,Profession,id,g_type,name,appoint,investamount,investcroptype,crop_process_type,peroid,profision_type,plan_type,status,ack,aadhar,bank,ifsc,email,mob,c_add,pan,d_add;

    TextView txtstatus;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    public EditText ed_Profession,ed_name,adate,investmentamount,ed_period,current_address,delivery_address,ed_aadhar,ed_pan,ed_bank,ed_ifsc,ed_mobile,ed_email;

    public Button submit;
    public ImageView adateimg;

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumerform_update);

        Bundle bundle = getIntent().getExtras();
        id = String.valueOf(bundle.getString("id"));
        Log.e("id",id);
        form(id);

        myCalendar = Calendar.getInstance();

        submit=(Button)findViewById(hmf.com.project.hmfinspection.R.id.submit);
        adateimg=(ImageView)findViewById(R.id.adateimg);
        spgridtype=(Spinner)findViewById(R.id.spgridtype);
        spinvestcroptype=(Spinner)findViewById(R.id.spinvestcroptype);
        spcurrentprocess=(Spinner)findViewById(R.id.spcurrentprocess);
        spprofession=(Spinner)findViewById(R.id.spprofession);
        spinvestplan=(Spinner)findViewById(R.id.spinvestplan);
        spacktype=(Spinner)findViewById(R.id.spacktype);

        txtstatus=(TextView)findViewById(R.id.status);
        ed_Profession=(EditText)findViewById(R.id.Profession);
        ed_name=(EditText) findViewById(R.id.name);
        adate=(EditText) findViewById(R.id.adate);
        investmentamount=(EditText) findViewById(R.id.investmentamount);
        ed_period=(EditText) findViewById(R.id.period);
        ed_aadhar=(EditText) findViewById(R.id.aadhar);
        ed_bank=(EditText) findViewById(R.id.bank);
        ed_ifsc=(EditText) findViewById(R.id.ifsc);
        ed_mobile=(EditText) findViewById(R.id.mobile);
        current_address=(EditText) findViewById(R.id.current_address);
        ed_pan=(EditText) findViewById(R.id.pan);
        ed_email=(EditText) findViewById(R.id.email);
        delivery_address=(EditText) findViewById(R.id.delivery_address);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                appoint=adate.getText().toString();
                investamount=investmentamount.getText().toString();
                peroid=ed_period.getText().toString();
                aadhar=ed_aadhar.getText().toString();
                name=ed_name.getText().toString();
                status=txtstatus.getText().toString();
                pan=ed_pan.getText().toString();
                bank=ed_bank.getText().toString();
                email=ed_email.getText().toString();
                mob=ed_mobile.getText().toString();
                c_add=current_address.getText().toString();
                ifsc=ed_ifsc.getText().toString();
                d_add=delivery_address.getText().toString();
                Profession=ed_Profession.getText().toString();

                boolean isValid = true;
                View focusView = null;
                adate.setError(null);
                if (TextUtils.isEmpty(appoint)) {
                    adate.setError(getString(hmf.com.project.hmfinspection.R.string.error_field_required));
                    focusView = adate;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(investamount)){
                    investmentamount.setError(getString(hmf.com.project.hmfinspection.R.string.error_field_required));
                    focusView = investmentamount;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(peroid)){
                    ed_period.setError(getString(hmf.com.project.hmfinspection.R.string.error_field_required));
                    focusView = ed_period;
                    isValid = false;
                }


                else if(TextUtils.isEmpty(name)){
                    ed_name.setError(getString(hmf.com.project.hmfinspection.R.string.error_field_required));
                    focusView = ed_name;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(c_add)){
                    current_address.setError(getString(hmf.com.project.hmfinspection.R.string.error_field_required));
                    focusView = current_address;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(d_add)){
                    delivery_address.setError(getString(hmf.com.project.hmfinspection.R.string.error_field_required));
                    focusView = delivery_address;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(Profession)){
                    ed_Profession.setError(getString(hmf.com.project.hmfinspection.R.string.error_field_required));
                    focusView = ed_aadhar;
                    isValid = false;
                }

                else if(TextUtils.isEmpty(aadhar)){
                    ed_aadhar.setError(getString(hmf.com.project.hmfinspection.R.string.error_field_required));
                    focusView = ed_aadhar;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(pan)){
                    ed_pan.setError(getString(hmf.com.project.hmfinspection.R.string.error_field_required));
                    focusView = ed_pan;
                    isValid = false;
                }

                else if(TextUtils.isEmpty(bank)){
                    ed_bank.setError(getString(hmf.com.project.hmfinspection.R.string.error_field_required));
                    focusView = ed_bank;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(ifsc)){
                    ed_ifsc.setError(getString(hmf.com.project.hmfinspection.R.string.error_field_required));
                    focusView = ed_ifsc;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(mob)){
                    ed_mobile.setError(getString(hmf.com.project.hmfinspection.R.string.error_field_required));
                    focusView = ed_mobile;
                    isValid = false;
                }
                else if((!isPhonevalid(String.valueOf(mob)))){
                    ed_mobile.setError("Enter validate patteren");
                    focusView = ed_mobile;
                    isValid = false;
                }

                else if(TextUtils.isEmpty(email)){
                    ed_email.setError(getString(hmf.com.project.hmfinspection.R.string.error_field_required));
                    focusView = ed_email;
                    isValid = false;
                }
                else  if(!email.matches(emailPattern)){
                    ed_email.setError("Enter Email Pattern");
                    focusView = ed_email;
                    isValid = false;
                }


                if (isValid){



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
                    progressDialog = ProgressDialog.show(ConsumerFormUpadteActivity.this, "Please wait.",
                            "Loading Data..!", true);
                    ConsumerFormUpdateApi pendingUpdateApi = retrofit.create(ConsumerFormUpdateApi.class);;
                    ConsumerFormRes ve=new ConsumerFormRes();

                    ve.setAadharNumber(aadhar);
                    ve.setAcknowledgment(ack);
                    ve.setAppointmentDate(appoint);
                    ve.setBankAccount(bank);
                    ve.setCurrentAddress(c_add);
                    ve.setCurrentProcessed(crop_process_type);
                    ve.setDeliveryAddress(d_add);
                    ve.setEmail(email);
                    ve.setEmployeeOf(profision_type);
                    ve.setGridType(g_type);
                    ve.setName(name);
                    ve.setId(id);
                    ve.setIfsc(ifsc);
                    ve.setInspectionStatus(status);
                    ve.setInterestedCrop(investcroptype);
                    ve.setInvestmentAmount(investamount);
                    ve.setInvestmentPeriod(peroid);
                    ve.setInvestmentPlan(plan_type);
                    ve.setPan(pan);
                    ve.setPhone(mob);
                    ve.setRegisteredMobileNumber(mob);
                    ve.setProfession(Profession);

                    Log.e("veera",ve.getId());
                    Call<SimpleRes> listCall= pendingUpdateApi.getUser(ve);

                    listCall.enqueue(new Callback<SimpleRes>() {
                        @Override
                        public void onResponse(Call<SimpleRes> call, retrofit2.Response<SimpleRes> response) {

                            progressDialog.dismiss();

                            if(response.isSuccessful()){
                                if(response.body()==null){

                                }else {


                                    Toast.makeText(ConsumerFormUpadteActivity.this,response.body().getMessage() , Toast.LENGTH_SHORT).show();

                                    Intent i= new Intent(ConsumerFormUpadteActivity.this,ConsumerInspectionActivity.class);
                                    startActivity(i);
                                }

                            }
                            else {

                                Toast.makeText(ConsumerFormUpadteActivity.this,response.message() , Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<SimpleRes> call, Throwable t) {

                            progressDialog.dismiss();
                            Toast.makeText(ConsumerFormUpadteActivity.this,t.getMessage() , Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                else {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                }


            }
        });
    }

    private boolean isPhonevalid(String mobile) {
        //TODO: Replace this with your own logic
        return mobile.length() >= 10;
    }
    private void form(String id) {

        url= AppController.BaseUrl+"/b2cInspection/"+id;

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("authorization", ESurvey.getAccessToken())
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
        progressDialog = ProgressDialog.show(ConsumerFormUpadteActivity.this, "Please wait.",
                "Loading Data..!", true);
        ConsumerFormListApi availabilityApi = retrofit.create(ConsumerFormListApi.class);
        Call<ConsumerFormRes> listCall= availabilityApi.getData(url);
        listCall.enqueue(new Callback<ConsumerFormRes>() {
            @Override
            public void onResponse(Call<ConsumerFormRes> call, retrofit2.Response<ConsumerFormRes> response) {


                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    if (response.body() == null) {

                    } else if (response.body().equals("")) {

                    } else {

                        consumerFormRes = response.body();

                        Log.e("chinna",consumerFormRes.toString());


                        adate.setText(consumerFormRes.getAppointmentDate());
                        investmentamount.setText(consumerFormRes.getInvestmentAmount());
                        ed_period.setText(consumerFormRes.getInvestmentPeriod());
                        current_address.setText(consumerFormRes.getCurrentAddress());
                        delivery_address.setText(consumerFormRes.getDeliveryAddress());
                        ed_aadhar.setText(consumerFormRes.getAadharNumber());
                        ed_bank.setText(consumerFormRes.getBankAccount());
                        ed_pan.setText(consumerFormRes.getPan());
                        ed_ifsc.setText(consumerFormRes.getIfsc());
                        ed_mobile.setText(consumerFormRes.getPhone());
                        ed_email.setText(consumerFormRes.getEmail());
                        ed_name.setText(consumerFormRes.getName());
                        ed_Profession.setText(consumerFormRes.getProfession());
                        txtstatus.setText(consumerFormRes.getInspectionStatus());


                        gridlist=new ArrayList<String>();
                        gridlist.add("south");
                        gridlist.add("north");

                        croplist=new ArrayList<String>();
                        croplist.add("BLACKGRAM");
                        croplist.add("REDGRAM");
                        croplist.add("GREENGRAM");
                        croplist.add("SESAMUM");


                        processedlist=new ArrayList<String>();
                        processedlist.add("yes");
                        processedlist.add("no");

                        planlist=new ArrayList<String>();
                        planlist.add("crop returns");
                        planlist.add("roi");


                        pofessionlist =new ArrayList<String>();
                        pofessionlist.add("self");
                        pofessionlist.add("employee");

                        acklist=new ArrayList<String>();
                        acklist.add("yes");
                        acklist.add("no");
                        final DatePickerDialog.OnDateSetListener fdate = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // TODO Auto-generated method stub

                                // myCalendar.set(2013, 05, 23);
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                fupdateLabe();
                            }
                        };

                        adateimg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                new DatePickerDialog(ConsumerFormUpadteActivity.this, fdate, myCalendar
                                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                            }
                        });


                        gridAdapter = new ArrayAdapter<String>(ConsumerFormUpadteActivity.this, android.R.layout.simple_spinner_item, gridlist);
                        gridAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spgridtype.setAdapter(gridAdapter);
                        spgridtype.setSelection(gridlist.indexOf(String.valueOf(consumerFormRes.getGridType())));

                        spgridtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                                g_type = (String) adapterView.getItemAtPosition(i);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        investcropAdpter = new ArrayAdapter<String>(ConsumerFormUpadteActivity.this, android.R.layout.simple_spinner_item, croplist);
                        investcropAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinvestcroptype.setAdapter(investcropAdpter);
                        spinvestcroptype.setSelection(croplist.indexOf(consumerFormRes.getInterestedCrop()));
                        spinvestcroptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                investcroptype = (String) adapterView.getItemAtPosition(i);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        processedAdpter = new ArrayAdapter<String>(ConsumerFormUpadteActivity.this, android.R.layout.simple_spinner_item, processedlist);
                        processedAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spcurrentprocess.setAdapter(processedAdpter);
                        spcurrentprocess.setSelection(processedlist.indexOf(consumerFormRes.getCurrentProcessed()));
                        spcurrentprocess.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                crop_process_type = (String) adapterView.getItemAtPosition(i);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        planAdpter = new ArrayAdapter<String>(ConsumerFormUpadteActivity.this, android.R.layout.simple_spinner_item, planlist);
                        planAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinvestplan.setAdapter(planAdpter);
                        spinvestplan.setSelection(planlist.indexOf(consumerFormRes.getInvestmentPlan()));
                        spinvestplan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                plan_type = (String) adapterView.getItemAtPosition(i);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        professionAdpter = new ArrayAdapter<String>(ConsumerFormUpadteActivity.this, android.R.layout.simple_spinner_item, pofessionlist);
                        professionAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spprofession.setAdapter(professionAdpter);
                        spprofession.setSelection(pofessionlist.indexOf(consumerFormRes.getProfession()));
                        spprofession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                profision_type = (String) adapterView.getItemAtPosition(i);


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });


                        ackAdpter = new ArrayAdapter<String>(ConsumerFormUpadteActivity.this, android.R.layout.simple_spinner_item, acklist);
                        ackAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spacktype.setAdapter(ackAdpter);
                        spacktype.setSelection(acklist.indexOf(consumerFormRes.getAcknowledgment()));
                        spacktype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                ack = (String) adapterView.getItemAtPosition(i);


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<ConsumerFormRes> call, Throwable t) {


                progressDialog.dismiss();
                Toast.makeText(ConsumerFormUpadteActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });




    }

    private void fupdateLabe() {

        String myFormat = "dd/MM/yyyy" + ""; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(date);

        Date cDate = null;
        Date sedate=null;
        try {
            cDate = sdf.parse(formattedDate);
            sedate = sdf.parse(sdf.format(myCalendar.getTime()));


        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ctimeInMilliseconds = cDate.getTime();
        long stimeInMilliseconds = sedate.getTime();

        if(stimeInMilliseconds>=ctimeInMilliseconds) {

            adate.setText(sdf.format(myCalendar.getTime()));
            Log.e("date",String.valueOf(sdf.format(myCalendar.getTime())));
        }
        else {
            Toast.makeText(ConsumerFormUpadteActivity.this, "Plz select only present Days", Toast.LENGTH_SHORT).show();

        }

    }

}
