package hmf.com.project.hmfinspection.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import hmf.com.project.hmfinspection.Api.FormApi;
import hmf.com.project.hmfinspection.Api.PendingUpdateApi;
import hmf.com.project.hmfinspection.AppController;
import hmf.com.project.hmfinspection.ESurvey;
import hmf.com.project.hmfinspection.R;
import hmf.com.project.hmfinspection.RecordingService;
import hmf.com.project.hmfinspection.activity.CameraSignActivity;
import hmf.com.project.hmfinspection.activity.MainActivity;
import hmf.com.project.hmfinspection.domains.InspectionPendingRes;
import hmf.com.project.hmfinspection.domains.SimpleRes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.melnykov.fab.FloatingActionButton;

import java.io.File;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POSITION = "position";
    private static final String LOG_TAG = RecordFragment.class.getSimpleName();

    private int position;

    private InspectionPendingRes inspectionPendingRes;
    List<String> gridlist,investcroplist,processedlist,consumerlist,planlist,quatityunitlist,turnoverlist,purposelist;
    ArrayAdapter<String> gridAdapter,investcropAdpter,processedAdpter,consumerAdpter,planAdpter,quatityunitAdpter,turnoverAdpter,purposeAdpter;
    Calendar myCalendar;
    String url,id,sign,g_type,appoint,totinvest,investinfo,quntiy,crop_type,quality_units,crop_process_type,
            consumer_type,plan_type,turn_type,purpose_type,delivery,status,orgname,gstnum,email,mob,PointofContact,c_add,r_add,pan,r_mob,w_add,d_add;

    private ProgressDialog progressDialog;
    public Spinner spgridtype,spinvestcroptype,spcurrentprocess,spcustomertype,spinvestplan,spturnover,purpose,quntityunits;
    public EditText adate,totalinvest,ed_investinfo,quntity,ed_delivery,org_name,gstname,name,Contact,current_address,register_address,ed_pan,
            register_phone,ware_house_address,delivery_address,ed_PointofContact;
    public Button submit;
    TextView txtstatus;
    ArrayList<String> images;
    ArrayList<String> audiolist;

    public ImageView adateimg;
    public LinearLayout warelay,delivertlay;
    List<String> digital;
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    //Recording controls
    private FloatingActionButton mRecordButton = null;
    private Button mPauseButton = null;

    private TextView mRecordingPrompt;
    private int mRecordPromptCount = 0;

    private boolean mStartRecording = true;
    private boolean mPauseRecording = true;

    private Chronometer mChronometer = null;
    long timeWhenPaused = 0; //stores time when user clicks pause button

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Record_Fragment.
     */
    public static RecordFragment newInstance(int position) {
        RecordFragment f = new RecordFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);

        return f;
    }

    public RecordFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View recordView = inflater.inflate(R.layout.fragment_record, container, false);

        id = MainActivity.id;
        sign=MainActivity.sign;
        images=MainActivity.images;
        audiolist=new ArrayList<String>();




     //   Log.e("id",id);
        myCalendar = Calendar.getInstance();

        form(id);
        warelay=(LinearLayout)recordView.findViewById(R.id.warelay);
        delivertlay=(LinearLayout)recordView.findViewById(R.id.delivertlay);
        submit=(Button)recordView.findViewById(R.id.submit);
        adateimg=(ImageView)recordView.findViewById(R.id.adateimg);
        spgridtype=(Spinner)recordView.findViewById(R.id.spgridtype);
        spinvestcroptype=(Spinner)recordView.findViewById(R.id.spinvestcroptype);
        spcurrentprocess=(Spinner)recordView.findViewById(R.id.spcurrentprocess);
        spcustomertype=(Spinner)recordView.findViewById(R.id.spcustomertype);
        spinvestplan=(Spinner)recordView.findViewById(R.id.spinvestplan);
        spturnover=(Spinner)recordView.findViewById(R.id.spturnover);
        purpose=(Spinner)recordView.findViewById(R.id.purpose);
        txtstatus=(TextView)recordView.findViewById(R.id.status);

        adate=(EditText) recordView.findViewById(R.id.adate);
        totalinvest=(EditText) recordView.findViewById(R.id.totalinvest);
        ed_investinfo=(EditText) recordView.findViewById(R.id.investinfo);
        quntity=(EditText) recordView.findViewById(R.id.quntity);
        ed_delivery=(EditText) recordView.findViewById(R.id.delivery);
        org_name=(EditText) recordView.findViewById(R.id.org_name);
        gstname=(EditText) recordView.findViewById(R.id.gstname);
        name=(EditText) recordView.findViewById(R.id.name);
        Contact=(EditText) recordView.findViewById(R.id.Contact);
        ed_PointofContact=(EditText)recordView.findViewById(R.id.PointofContact);
        current_address=(EditText) recordView.findViewById(R.id.current_address);
        register_address=(EditText) recordView.findViewById(R.id.register_address);
        ed_pan=(EditText) recordView.findViewById(R.id.pan);
        register_phone=(EditText) recordView.findViewById(R.id.register_phone);
        ware_house_address=(EditText) recordView.findViewById(R.id.ware_house_address);
        delivery_address=(EditText) recordView.findViewById(R.id.delivery_address);
        quntityunits=(Spinner) recordView.findViewById(R.id.quatityunits);

        mChronometer = (Chronometer) recordView.findViewById(R.id.chronometer);
        //update recording prompt text
        mRecordingPrompt = (TextView) recordView.findViewById(R.id.recording_status_text);

        mRecordButton = (FloatingActionButton) recordView.findViewById(R.id.btnRecord);
        mRecordButton.setColorNormal(getResources().getColor(R.color.primary));
        mRecordButton.setColorPressed(getResources().getColor(R.color.primary_dark));
        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord(mStartRecording);
                mStartRecording = !mStartRecording;
            }
        });

        mPauseButton = (Button) recordView.findViewById(R.id.btnPause);
        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPauseRecord(mPauseRecording);
                mPauseRecording = !mPauseRecording;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appoint=adate.getText().toString();
                totinvest=totalinvest.getText().toString();
                investinfo=ed_investinfo.getText().toString();
                quntiy=quntity.getText().toString();
                delivery=ed_delivery.getText().toString();
                status=txtstatus.getText().toString();
                orgname=org_name.getText().toString();
                gstnum=gstname.getText().toString();
                email=name.getText().toString();
                mob=Contact.getText().toString();
                c_add=current_address.getText().toString();
                r_add=register_address.getText().toString();
                pan=ed_pan.getText().toString();
                r_mob=register_phone.getText().toString();
                w_add=ware_house_address.getText().toString();
                d_add=delivery_address.getText().toString();
                PointofContact=ed_PointofContact.getText().toString();

                boolean isValid = true;
                View focusView = null;
                adate.setError(null);
                if (TextUtils.isEmpty(appoint)) {
                    adate.setError(getString(R.string.error_field_required));
                    focusView = adate;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(totinvest)){
                    totalinvest.setError(getString(R.string.error_field_required));
                    focusView = totalinvest;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(investinfo)){
                    ed_investinfo.setError(getString(R.string.error_field_required));
                    focusView = ed_investinfo;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(quntiy)){
                    quntity.setError(getString(R.string.error_field_required));
                    focusView = quntity;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(delivery)){
                    ed_delivery.setError(getString(R.string.error_field_required));
                    focusView = quntity;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(orgname)){
                    org_name.setError(getString(R.string.error_field_required));
                    focusView = org_name;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(gstnum)){
                    gstname.setError(getString(R.string.error_field_required));
                    focusView = gstname;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(email)){
                    name.setError(getString(R.string.error_field_required));
                    focusView = name;
                    isValid = false;
                }
                else  if(!email.matches(emailPattern)){
                    name.setError("Enter Email Pattern");
                    focusView = name;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(PointofContact)){
                    ed_PointofContact.setError(getString(R.string.error_field_required));
                    focusView=ed_PointofContact;
                    isValid=false;

                }
                else if(TextUtils.isEmpty(mob)){
                    Contact.setError(getString(R.string.error_field_required));
                    focusView = Contact;
                    isValid = false;
                }
                else if((!isPhonevalid(String.valueOf(mob)))){
                    Contact.setError("Enter validate patteren");
                    focusView = Contact;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(c_add)){
                    current_address.setError(getString(R.string.error_field_required));
                    focusView = current_address;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(r_add)){
                    register_address.setError(getString(R.string.error_field_required));
                    focusView = register_address;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(pan)){
                    ed_pan.setError(getString(R.string.error_field_required));
                    focusView = ed_pan;
                    isValid = false;
                }
                else if(TextUtils.isEmpty(r_mob)){
                    register_phone.setError(getString(R.string.error_field_required));
                    focusView = register_phone;
                    isValid = false;
                }
                else if((!isPhonevalid(String.valueOf(r_mob)))){
                    register_phone.setError("Enter validate patteren");
                    focusView = register_phone;
                    isValid = false;
                }
               else if(RecordingService.audio==null){

                    Toast.makeText(getContext(), "Please upload Audio first", Toast.LENGTH_SHORT).show();
                    return;

                }
                else {

                    audiolist.addAll(RecordingService.audio);

                }

                if (isValid){


                    //Toast.makeText(getContext(), RecordingService.mFilePath, Toast.LENGTH_SHORT).show();
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
                   /* progressDialog = ProgressDialog.show(getContext(), "Please wait.",
                            "Loading Data..!", true);*/
                    PendingUpdateApi pendingUpdateApi = retrofit.create(PendingUpdateApi.class);;
                     digital= new ArrayList<String>();
                    digital.add(sign);
                    InspectionPendingRes ve=new InspectionPendingRes();
                    ve.setId(id);
                    ve.setGridType(g_type);
                    ve.setInterestedCrop(crop_type);
                    ve.setAppointmentDate(appoint);
                    ve.setTotalInvestment(totinvest);
                    ve.setInvestmentInfo(investinfo);
                    ve.setExpectedQuantity(quntiy);
                    ve.setQuatityUnits(quality_units);
                    ve.setExpectedDelivery(delivery);
                    ve.setCurrentlyProcessed(crop_process_type);
                    ve.setTypeOfConsumer(consumer_type);
                    ve.setTypeOfInvestmentPlan(plan_type);
                    ve.setInspectionStatus(status);
                    ve.setOrganizationName(orgname);
                    ve.setGstNumber(gstnum);
                    ve.setTurnover(turn_type);
                    ve.setEmail(email);
                    ve.setPointOfContact(PointofContact);
                    ve.setPhone(inspectionPendingRes.getPhone());
                    ve.setContactNumber(mob);
                    ve.setCurrentAddress(c_add);
                    ve.setRegisteredAddress(r_add);
                    ve.setCompanyPAN(pan);
                    ve.setCompanyRegisteredPhone(r_mob);
                    ve.setWareHouseAddress(w_add);
                    ve.setDeliveryAddress(d_add);
                    ve.setDigitalSigns(digital);
                    ve.setImages(images);
                    ve.setAudios(audiolist);

                    Log.e("veera",ve.toString());
                    Call<SimpleRes> listCall= pendingUpdateApi.getUser(ve);

                    listCall.enqueue(new Callback<SimpleRes>() {
                        @Override
                        public void onResponse(Call<SimpleRes> call, retrofit2.Response<SimpleRes> response) {

                            progressDialog.dismiss();

                            if(response.isSuccessful()){
                                if(response.body()==null){

                                }else {


                                    Toast.makeText(getContext(),response.body().getMessage() , Toast.LENGTH_SHORT).show();

                                    Intent  i= new Intent(getContext(),CameraSignActivity.class);
                                    startActivity(i);
                                }

                            }
                            else {

                                Toast.makeText(getContext(),response.message() , Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<SimpleRes> call, Throwable t) {

                            progressDialog.dismiss();
                            Toast.makeText(getContext(),t.getMessage() , Toast.LENGTH_SHORT).show();

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


        return recordView;
    }

    private boolean isPhonevalid(String mobile) {
        //TODO: Replace this with your own logic
        return mobile.length() >= 10;
    }
    private void form(String id) {
        url= AppController.BaseUrl+"/b2bInspection/"+id;

        Log.e("url",url);

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
        progressDialog = ProgressDialog.show(getContext(), "Please wait.",
                "Loading Data..!", true);

        FormApi availabilityApi = retrofit.create(FormApi.class);
        Call<InspectionPendingRes> listCall= availabilityApi.getData(url);
        listCall.enqueue(new Callback<InspectionPendingRes>() {
            @Override
            public void onResponse(Call<InspectionPendingRes> call, retrofit2.Response<InspectionPendingRes> response) {

                progressDialog.dismiss();
                if(response.isSuccessful()){

                    if(response.body()==null){

                    }else if(response.body().equals("")) {

                    }else {

                        inspectionPendingRes=response.body();


                        adate.setText(inspectionPendingRes.getAppointmentDate());
                        totalinvest.setText(inspectionPendingRes.getTotalInvestment());
                        ed_investinfo.setText(inspectionPendingRes.getInvestmentInfo());
                        quntity.setText(inspectionPendingRes.getExpectedQuantity());
                        ed_delivery.setText(inspectionPendingRes.getExpectedDelivery());
                        txtstatus.setText(inspectionPendingRes.getInspectionStatus());
                        org_name.setText(inspectionPendingRes.getOrganizationName());
                        gstname.setText(inspectionPendingRes.getGstNumber());
                        name.setText(inspectionPendingRes.getEmail());
                        Contact.setText(inspectionPendingRes.getContactNumber());
                        current_address.setText(inspectionPendingRes.getCurrentAddress());
                        register_address.setText(inspectionPendingRes.getRegisteredAddress());
                        ed_pan.setText(inspectionPendingRes.getCompanyPAN());
                        register_phone.setText(inspectionPendingRes.getCompanyRegisteredPhone());
                        ed_PointofContact.setText(inspectionPendingRes.getPointOfContact());

                        gridlist=new ArrayList<String>();
                        gridlist.add("south");
                        gridlist.add("north");

                        investcroplist=new ArrayList<String>();
                        investcroplist.add("BLACKGRAM");
                        investcroplist.add("REDGRAM");
                        investcroplist.add("GREENGRAM");
                        investcroplist.add("SESAMUM");

                        processedlist=new ArrayList<String>();
                        processedlist.add("yes");
                        processedlist.add("no");

                        consumerlist=new ArrayList<String>();
                        consumerlist.add("retailer");
                        consumerlist.add("manufacturer");
                        consumerlist.add("food business");
                        consumerlist.add("whole seller");

                        planlist=new ArrayList<String>();
                        planlist.add("crop returns");
                        planlist.add("roi");

                        quatityunitlist=new ArrayList<String>();
                        quatityunitlist.add("kgs");
                        quatityunitlist.add("tonnes");


                        turnoverlist=new ArrayList<String>();
                        turnoverlist.add("kgs");
                        turnoverlist.add("tonnes");

                        purposelist=new ArrayList<String>();
                        purposelist.add("own");
                        purposelist.add("resale");

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

                                new DatePickerDialog(getContext(), fdate, myCalendar
                                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                            }
                        });



                        gridAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, gridlist);
                        gridAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spgridtype.setAdapter(gridAdapter);
                        spgridtype.setSelection(gridlist.indexOf(String.valueOf(inspectionPendingRes.getGridType())));

                        spgridtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                                g_type = (String) adapterView.getItemAtPosition(i);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });


                        investcropAdpter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, investcroplist);
                        investcropAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinvestcroptype.setAdapter(investcropAdpter);
                        spinvestcroptype.setSelection(investcroplist.indexOf(inspectionPendingRes.getInterestedCrop()));
                        spinvestcroptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                crop_type = (String) adapterView.getItemAtPosition(i);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        processedAdpter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, processedlist);
                        processedAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spcurrentprocess.setAdapter(processedAdpter);
                        spcurrentprocess.setSelection(processedlist.indexOf(inspectionPendingRes.getCurrentlyProcessed()));
                        spcurrentprocess.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                crop_process_type = (String) adapterView.getItemAtPosition(i);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        consumerAdpter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, consumerlist);
                        consumerAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spcustomertype.setAdapter(consumerAdpter);
                        spcustomertype.setSelection(consumerlist.indexOf(inspectionPendingRes.getTypeOfConsumer()));
                        spcustomertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                consumer_type = (String) adapterView.getItemAtPosition(i);


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        planAdpter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, planlist);
                        planAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinvestplan.setAdapter(planAdpter);
                        spinvestplan.setSelection(planlist.indexOf(inspectionPendingRes.getTypeOfInvestmentPlan()));
                        spinvestplan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                plan_type = (String) adapterView.getItemAtPosition(i);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        quatityunitAdpter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, quatityunitlist);
                        quatityunitAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        quntityunits.setAdapter(quatityunitAdpter);
                        quntityunits.setSelection(quatityunitlist.indexOf(inspectionPendingRes.getQuatityUnits()));
                        quntityunits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                                quality_units = (String) adapterView.getItemAtPosition(i);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        turnoverAdpter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, turnoverlist);
                        turnoverAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spturnover.setAdapter(turnoverAdpter);
                        spturnover.setSelection(turnoverlist.indexOf(inspectionPendingRes.getTurnover()));
                        spturnover.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                                turn_type = (String) adapterView.getItemAtPosition(i);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        purposeAdpter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, purposelist);
                        purposeAdpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        purpose.setAdapter(purposeAdpter);
                        purpose.setSelection(purposelist.indexOf(inspectionPendingRes.getTurnover()));
                        purpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                purpose_type = (String) adapterView.getItemAtPosition(i);

                                if(purpose_type.equals("own")){

                                    ware_house_address.setText(inspectionPendingRes.getWareHouseAddress());
                                    delivertlay.setVisibility(View.GONE);
                                    warelay.setVisibility(View.VISIBLE);
                                }
                                else  if(purpose_type.equals("resale")){
                                    delivertlay.setVisibility(View.VISIBLE);
                                    warelay.setVisibility(View.GONE);
                                    delivery_address.setText(inspectionPendingRes.getDeliveryAddress());
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    }


                }
            }

            @Override
            public void onFailure(Call<InspectionPendingRes> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(getContext(),t.getMessage(), Toast.LENGTH_SHORT).show();

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
            Toast.makeText(getContext(), "Plz select only present Days", Toast.LENGTH_SHORT).show();

        }

    }
    // Recording Start/Stop
    //TODO: recording pause
    private void onRecord(boolean start){

        Intent intent = new Intent(getActivity(), RecordingService.class);

        if (start) {
            // start recording
            mRecordButton.setImageResource(R.drawable.ic_media_stop);
            //mPauseButton.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(),R.string.toast_recording_start, Toast.LENGTH_SHORT).show();
            File folder = new File(Environment.getExternalStorageDirectory() + "/SoundRecorder");
            if (!folder.exists()) {
                //folder /SoundRecorder doesn't exist, create the folder
                folder.mkdir();
            }

            //start Chronometer
            mChronometer.setBase(SystemClock.elapsedRealtime());
            mChronometer.start();
            mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    if (mRecordPromptCount == 0) {
                        mRecordingPrompt.setText(getString(R.string.record_in_progress) + ".");
                    } else if (mRecordPromptCount == 1) {
                        mRecordingPrompt.setText(getString(R.string.record_in_progress) + "..");
                    } else if (mRecordPromptCount == 2) {
                        mRecordingPrompt.setText(getString(R.string.record_in_progress) + "...");
                        mRecordPromptCount = -1;
                    }

                    mRecordPromptCount++;
                }
            });

            //start RecordingService
            getActivity().startService(intent);
            //keep screen on while recording
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            mRecordingPrompt.setText(getString(R.string.record_in_progress) + ".");
            mRecordPromptCount++;

        } else {
            //stop recording
            mRecordButton.setImageResource(R.drawable.ic_mic_white_36dp);
            //mPauseButton.setVisibility(View.GONE);
            mChronometer.stop();
            mChronometer.setBase(SystemClock.elapsedRealtime());
            timeWhenPaused = 0;
            mRecordingPrompt.setText(getString(R.string.record_prompt));

            getActivity().stopService(intent);
            //allow the screen to turn off again once recording is finished
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    //TODO: implement pause recording
    private void onPauseRecord(boolean pause) {
        if (pause) {
            //pause recording
            mPauseButton.setCompoundDrawablesWithIntrinsicBounds
                    (R.drawable.ic_media_play ,0 ,0 ,0);
            mRecordingPrompt.setText((String)getString(R.string.resume_recording_button).toUpperCase());
            timeWhenPaused = mChronometer.getBase() - SystemClock.elapsedRealtime();
            mChronometer.stop();
        } else {
            //resume recording
            mPauseButton.setCompoundDrawablesWithIntrinsicBounds
                    (R.drawable.ic_media_pause ,0 ,0 ,0);
            mRecordingPrompt.setText((String)getString(R.string.pause_recording_button).toUpperCase());
            mChronometer.setBase(SystemClock.elapsedRealtime() + timeWhenPaused);
            mChronometer.start();
        }
    }
}