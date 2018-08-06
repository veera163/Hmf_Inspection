package hmf.com.project.hmfinspection.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import hmf.com.project.hmfinspection.Api.ConsumerFormListApi;
import hmf.com.project.hmfinspection.Api.ConsumerFormUpdateApi;
import hmf.com.project.hmfinspection.AppController;
import hmf.com.project.hmfinspection.ESurvey;
import hmf.com.project.hmfinspection.R;
import hmf.com.project.hmfinspection.RecordingService;
import hmf.com.project.hmfinspection.activity.CameraSignActivity;
import hmf.com.project.hmfinspection.domains.ConsumerFormRes;
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
 * Created by home on 5/14/2018.
 */

public class ConsumerRecordFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_POSITION = "position";
    private static final String LOG_TAG = RecordFragment.class.getSimpleName();

    private int position;
    ConsumerFormRes consumerFormRes;

    List<String> gridlist,croplist,processedlist,pofessionlist,planlist,acklist;
    ArrayAdapter<String> gridAdapter,investcropAdpter,processedAdpter,professionAdpter,planAdpter,ackAdpter;
    Calendar myCalendar;
    private ProgressDialog progressDialog;
    public Spinner spgridtype,spinvestcroptype,spcurrentprocess,spprofession,spinvestplan,spacktype;

    String url,Profession,id,g_type,name,appoint,investamount,investcroptype,crop_process_type,peroid,profision_type,plan_type,status,ack,aadhar,bank,ifsc,email,mob,c_add,pan,d_add;

    TextView txtstatus;


    public EditText ed_Profession,ed_name,adate,investmentamount,ed_period,current_address,delivery_address,ed_aadhar,ed_pan,ed_bank,ed_ifsc,ed_mobile,ed_email;

    public Button submit;
    public ImageView adateimg;

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

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
    public static ConsumerRecordFragment newInstance(int position) {
        ConsumerRecordFragment f = new ConsumerRecordFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);

        return f;
    }

    public ConsumerRecordFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View recordView = inflater.inflate(R.layout.consumerfragmentlist, container, false);

     //   id = ConsumerMainActivity.id;
        //   Log.e("id",id);

        form(id);

        myCalendar = Calendar.getInstance();

        submit=(Button)recordView.findViewById(R.id.submit);
        adateimg=(ImageView)recordView.findViewById(R.id.adateimg);
        spgridtype=(Spinner)recordView.findViewById(R.id.spgridtype);
        spinvestcroptype=(Spinner)recordView.findViewById(R.id.spinvestcroptype);
        spcurrentprocess=(Spinner)recordView.findViewById(R.id.spcurrentprocess);
        spprofession=(Spinner)recordView.findViewById(R.id.spprofession);
        spinvestplan=(Spinner)recordView.findViewById(R.id.spinvestplan);
        spacktype=(Spinner)recordView.findViewById(R.id.spacktype);

        txtstatus=(TextView)recordView.findViewById(R.id.status);
        ed_Profession=(EditText)recordView.findViewById(R.id.Profession);
        ed_name=(EditText) recordView.findViewById(R.id.name);
        adate=(EditText) recordView.findViewById(R.id.adate);
        investmentamount=(EditText) recordView.findViewById(R.id.investmentamount);
        ed_period=(EditText) recordView.findViewById(R.id.period);
        ed_aadhar=(EditText) recordView.findViewById(R.id.aadhar);
        ed_bank=(EditText) recordView.findViewById(R.id.bank);
        ed_ifsc=(EditText) recordView.findViewById(R.id.ifsc);
        ed_mobile=(EditText) recordView.findViewById(R.id.mobile);
        current_address=(EditText) recordView.findViewById(R.id.current_address);
        ed_pan=(EditText) recordView.findViewById(R.id.pan);
        ed_email=(EditText) recordView.findViewById(R.id.email);
        delivery_address=(EditText) recordView.findViewById(R.id.delivery_address);

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
                pan=ed_pan.getText().toString();
                d_add=delivery_address.getText().toString();
                Profession=ed_Profession.getText().toString();

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
        });


        return recordView;
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
        progressDialog = ProgressDialog.show(getContext(), "Please wait.",
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


                        adate.setText(consumerFormRes.getAppointmentDate());
                        investmentamount.setText(consumerFormRes.getInvestmentAmount());
                        ed_period.setText(consumerFormRes.getInvestmentPeriod());
                        current_address.setText(consumerFormRes.getCurrentAddress());
                        delivery_address.setText(consumerFormRes.getDeliveryAddress());
                        ed_aadhar.setText(consumerFormRes.getAadharNumber());
                        ed_bank.setText(consumerFormRes.getBankAccount());
                        ed_pan.setText(consumerFormRes.getPan());
                        ed_ifsc.setText(consumerFormRes.getIfsc());
                        ed_mobile.setText(consumerFormRes.getRegisteredMobileNumber());
                        ed_email.setText(consumerFormRes.getEmail());
                        ed_name.setText(consumerFormRes.getName());
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

                                new DatePickerDialog(getContext(), fdate, myCalendar
                                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                            }
                        });


                        gridAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, gridlist);
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

                        investcropAdpter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, croplist);
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
                        processedAdpter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, processedlist);
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

                        planAdpter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, planlist);
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

                        professionAdpter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, pofessionlist);
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


                        ackAdpter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, acklist);
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
