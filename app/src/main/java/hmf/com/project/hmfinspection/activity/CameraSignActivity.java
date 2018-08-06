package hmf.com.project.hmfinspection.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import hmf.com.project.hmfinspection.Api.DigitalSignApi;
import hmf.com.project.hmfinspection.Api.ImagesApi;
import hmf.com.project.hmfinspection.R;
import hmf.com.project.hmfinspection.domains.DigitalClient;
import hmf.com.project.hmfinspection.domains.ImagesClient;
import hmf.com.project.hmfinspection.domains.SimpleRes;
import hmf.com.project.hmfinspection.permission.PermissionsChecker;
import hmf.com.project.hmfinspection.utils.InternetConnection;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by home on 5/11/2018.
 */

public class CameraSignActivity extends AppCompatActivity implements View.OnClickListener {

    Button captureBtn = null;
    final int CAMERA_CAPTURE = 1;
    private Uri picUri;
    Button submit,cap_cancle;
    private GridView grid;
    PermissionsChecker checker;
    public static String GridViewDemo_ImagePath;
    private List<String> listOfImagesPath;
    String _path;
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Button mClear, mGetSign, mCancel;
    File file;
    String id,deviceId,trip="T1";
    TextView ti;
    LinearLayout mContent;
    Button next;
    View view;
    signature mSignature;
    LinearLayout signlay,imglay;
    String sign;
    ImageView signimg,close;
    Bitmap bitmap;
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/HMFSignature/";
    String pic_name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    String StoredPath = DIRECTORY + pic_name + ".png";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_sign);
        captureBtn = (Button)findViewById(R.id.capture_btn1);
        cap_cancle=(Button)findViewById(R.id.cap_cancle);
        signlay=(LinearLayout)findViewById(R.id.signlay);
        imglay=(LinearLayout)findViewById(R.id.imaglay);
        close=(ImageView)findViewById(R.id.close);
        signimg=(ImageView)findViewById(R.id.signimg);
        submit=(Button)findViewById(R.id.submit);
        next=(Button)findViewById(R.id.next);
        captureBtn.setOnClickListener(this);
        grid = (GridView) findViewById(R.id.gridviewimg);
        checker = new PermissionsChecker(this);
        id=MainActivity.id;
        sign=MainActivity.sign;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(CameraSignActivity.this,InspectionActivity.class);
                startActivity(i);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signlay.setVisibility(View.VISIBLE);
                imglay.setVisibility(View.GONE);

            }
        });
        if(sign==null){

            signlay.setVisibility(View.VISIBLE);

        }else  if(sign.equals("")){

            signlay.setVisibility(View.VISIBLE);

        }else {

            Glide.with(CameraSignActivity.this)
                    .load(sign)
                    .crossFade()
                    .thumbnail(0.5f)
                    .error(hmf.com.project.hmfinspection.R.mipmap.noimage)      // optional
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(signimg);

            signlay.setVisibility(View.GONE);

        }
      //  GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/HMFInspection/"+phone+"/"+loc+"/";
       // GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/HMFInspection/";
        GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/HMFInspection/"+MainActivity.id+"/";

        mContent = (LinearLayout) findViewById(R.id.canvasLayout);
        mSignature = new signature(getApplicationContext(), null);
        mSignature.setBackgroundColor(Color.WHITE);
        // Dynamically generating Layout through java code
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mClear = (Button) findViewById(R.id.clear);
        mGetSign = (Button) findViewById(R.id.getsign);
        mGetSign.setEnabled(false);
        mCancel = (Button) findViewById(R.id.cancel);
        view = mContent;
        mGetSign.setOnClickListener(onButtonClick);
        mClear.setOnClickListener(onButtonClick);
        mCancel.setOnClickListener(onButtonClick);

        // Method to create Directory, if the Directory doesn't exists
        file = new File(DIRECTORY);
        if (!file.exists()) {
            file.mkdir();
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("paths", String.valueOf(listOfImagesPath));

                if(!TextUtils.isEmpty(_path)){
                    if (!TextUtils.isEmpty(String.valueOf(listOfImagesPath))) {

                        if (InternetConnection.checkConnection(CameraSignActivity.this)) {
                            /******************Retrofit***************/
                            uploadCaptureImage();
                        } else {
                            Toast.makeText(CameraSignActivity.this, hmf.com.project.hmfinspection.R.string.string_internet_connection_warning,Toast.LENGTH_LONG).show();
                            //Snackbar.make(parentView, R.string.string_internet_connection_warning, Snackbar.LENGTH_INDEFINITE).show();
                        }
                    }
                }
                else {

                    Toast.makeText(CameraSignActivity.this, hmf.com.project.hmfinspection.R.string.string_message_to_attach_file,Toast.LENGTH_LONG).show();
                    //Snackbar.make(parentView, R.string.string_message_to_attach_file, Snackbar.LENGTH_INDEFINITE).show();
                }

    /*  File root = new File(GridViewDemo_ImagePath);
        File[] Files = root.listFiles();
        if(Files != null) {
            int j;
            for(j = 0; j < Files.length; j++) {
                System.out.println(Files[j].getAbsolutePath());
                System.out.println(Files[j].delete());
            }
        }*/

                // grid.setAdapter(null);
            }
        });

        cap_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File root = new File(GridViewDemo_ImagePath);
                File[] Files = root.listFiles();
                if(Files != null) {
                    int j;
                    for(j = 0; j < Files.length; j++) {
                        System.out.println(Files[j].getAbsolutePath());
                        System.out.println(Files[j].delete());


                    }
                }

                grid.setAdapter(null);
            }
        });

        listOfImagesPath = null;
        listOfImagesPath = RetriveCapturedImagePath();
        if(listOfImagesPath!=null){
            grid.setAdapter(new ImageListAdapter(this,listOfImagesPath));
        }


    }

    private void uploadCaptureImage() {


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(CameraSignActivity.this);
        progressDialog.setMessage(getString(hmf.com.project.hmfinspection.R.string.string_title_upload_progressbar_));
        progressDialog.show();
        File file = new File(_path);
        ImagesApi service = ImagesClient.getApiService();

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //Log.e("request", String.valueOf(requestFile));
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);

        MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[listOfImagesPath.size()];

        for (int index = 0; index < listOfImagesPath.size(); index++) {
            file = new File(listOfImagesPath.get(index));
            RequestBody surveyBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            surveyImagesParts[index] = MultipartBody.Part.createFormData("SurveyImage", file.getName(), surveyBody);
        }

        Call<SimpleRes> resultCall = service.uploadImage(surveyImagesParts,id);
        resultCall.enqueue(new Callback<SimpleRes>() {
            @Override
            public void onResponse(Call<SimpleRes> call, retrofit2.Response<SimpleRes> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();

                   /* File root = new File(GridViewDemo_ImagePath);
                        File[] Files = root.listFiles();
                        if(Files != null) {
                            int j;
                            for(j = 0; j < Files.length; j++) {
                                System.out.println(Files[j].getAbsolutePath());
                                System.out.println(Files[j].delete());
                            }
                        }
                   */
                 //   grid.setAdapter(null);
                    Toast.makeText(CameraSignActivity.this,"Successfully Uploaded",Toast.LENGTH_LONG).show();
                    //  Intent i= new Intent(Collect.this, MapsActivity.class);
                    // startActivity(i);
                }
                else {

                    progressDialog.dismiss();
                    Toast.makeText(CameraSignActivity.this,response.message(),Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<SimpleRes> call, Throwable t) {

                Toast.makeText(CameraSignActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == hmf.com.project.hmfinspection.R.id.capture_btn1) {

            try {
//use standard intent to capture an image
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//we will handle the returned data in onActivityResult
                startActivityForResult(captureIntent, CAMERA_CAPTURE);
            } catch(ActivityNotFoundException anfe){
//display an error message
                String errorMessage = "Whoops - your device doesn't support capturing images!";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
//user is returning from capturing an image using the camera
            if(requestCode == CAMERA_CAPTURE){
                Bundle extras = data.getExtras();
                Bitmap thePic = extras.getParcelable("data");
                String imgcurTime = (new Date()).getTime()+"";//dateFormat.format(new Date());
                File imageDirectory = new File(GridViewDemo_ImagePath);
                imageDirectory.mkdirs();
                _path = GridViewDemo_ImagePath +imgcurTime+".jpg";
                Log.e("path", String.valueOf(_path));
                try {
                    FileOutputStream out = new FileOutputStream(_path);
                    thePic.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();
                } catch (FileNotFoundException e) {
                    e.getMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                listOfImagesPath = null;
                listOfImagesPath = RetriveCapturedImagePath();
                Log.e("values", String.valueOf(listOfImagesPath));

                if(listOfImagesPath!=null){
                    grid.setAdapter(new ImageListAdapter(this,listOfImagesPath));
                }
            }
        }
    }

    private List<String> RetriveCapturedImagePath() {
        List<String> tFileList = new ArrayList<String>();
        File f = new File(GridViewDemo_ImagePath);
        if (f.exists()) {
            File[] files=f.listFiles();
            Arrays.sort(files);

            for(int i=0; i<files.length; i++){
                File file = files[i];
                if(file.isDirectory())
                    continue;
                tFileList.add(file.getPath());
            }
        }
        return tFileList;
    }


    public class ImageListAdapter extends BaseAdapter
    {
        private Context context;
        private List<String> imgPic;
        public ImageListAdapter(Context c, List<String> thePic)
        {
            context = c;
            imgPic = thePic;
        }
        public int getCount() {
            if(imgPic != null)
                return imgPic.size();
            else
                return 0;
        }

        //---returns the ID of an item---
        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        //---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView;
            BitmapFactory.Options bfOptions=new BitmapFactory.Options();
            bfOptions.inDither=false;                     //Disable Dithering mode
            bfOptions.inPurgeable=true;                   //Tell to gc that whether it needs free memory, the Bitmap can be cleared
            bfOptions.inInputShareable=true;              //Which kind of reference will be used to recover the Bitmap data after being clear, when it will be used in the future
            bfOptions.inTempStorage=new byte[32 * 1024];
            if (convertView == null) {
                imageView = new ImageView(context);

                GridView.LayoutParams lp= new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT, GridView.LayoutParams.WRAP_CONTENT);
                //   lp.setMargins(left, top, right, bottom);

                // view.setLayoutParams(/* your layout params */); //where view is cell view
                imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT, GridView.LayoutParams.WRAP_CONTENT));
                imageView.setPadding(10, 10, 10, 10);
                // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageView = (ImageView) convertView;
            }
            FileInputStream fs = null;
            Bitmap bm;
            try {

                    fs = new FileInputStream(new File(imgPic.get(position).toString()));

                if(fs!=null) {
                    bm= BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
                    imageView.setImageBitmap(bm);
                    imageView.setId(position);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setLayoutParams(new GridView.LayoutParams(900, 600));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if(fs!=null) {
                    try {
                        fs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return imageView;
        }
    }

    Button.OnClickListener onButtonClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (v == mClear) {
                Log.v("log_tag", "Panel Cleared");
                mSignature.clear();
                mGetSign.setEnabled(false);
            } else if (v == mGetSign) {
                Log.v("log_tag", "Panel Saved");
                if (Build.VERSION.SDK_INT > 23) {
                    isStoragePermissionGranted();
                } else {
                    view.setDrawingCacheEnabled(true);
                    mSignature.save(view, StoredPath);

                    Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_SHORT).show();
                    // Calling the same class
                    recreate();
                }
            } else if(v == mCancel){
                Log.v("log_tag", "Panel Canceled");
                finish();
                // Calling the BillDetailsActivity
                // Intent intent = new Intent(Signature.this, MapsActivity.class);
                // intent.putExtra("fulladdress",address);
                //  startActivity(intent);
            }
        }
    };

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT > 23) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            view.setDrawingCacheEnabled(true);
            mSignature.save(view, StoredPath);
            Toast.makeText(getApplicationContext(), "Successfully Saved", Toast.LENGTH_SHORT).show();
            // Calling the same class
            recreate();
        }
        else
        {
            Toast.makeText(this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
        }
    }
    public class signature extends View {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v, String StoredPath) {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(bitmap);
            try {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                Log.e("path",StoredPath);
                if (InternetConnection.checkConnection(CameraSignActivity.this)) {
                    /******************Retrofit***************/
                    upload(StoredPath);

                } else {
                    Toast.makeText(CameraSignActivity.this, hmf.com.project.hmfinspection.R.string.string_internet_connection_warning,Toast.LENGTH_LONG).show();
                    //Snackbar.make(parentView, R.string.string_internet_connection_warning, Snackbar.LENGTH_INDEFINITE).show();
                }
                // Intent intent = new Intent(Signature.this, MapsActivity.class);
                // intent.putExtra("imagePath", StoredPath);
                // intent.putExtra("fulladdress",address);
                //  startActivity(intent);
                finish();
                mFileOutStream.flush();
                mFileOutStream.close();

            } catch (Exception e) {

                Log.v("log_tag", e.toString());
            }

        }
        public void clear() {
            path.reset();
            invalidate();
            mGetSign.setEnabled(false);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            mGetSign.setEnabled(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {

            Log.v("log_tag", string);

        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    private void upload(String storedPath) {

        File file = new File(storedPath);

        DigitalSignApi digitalSignApi = DigitalClient.getApiService();

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //Log.e("request", String.valueOf(requestFile));
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);
        Call<SimpleRes> resultCall = digitalSignApi.uploadImage(body,id);
        resultCall.enqueue(new Callback<SimpleRes>() {
            @Override
            public void onResponse(Call<SimpleRes> call, retrofit2.Response<SimpleRes> response) {
                if(response.isSuccessful()){

                    Toast.makeText(CameraSignActivity.this,"Successfully Uploaded",Toast.LENGTH_LONG).show();
                    Intent i= new Intent(CameraSignActivity.this,LandingActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(CameraSignActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onFailure(Call<SimpleRes> call, Throwable t) {


                Toast.makeText(CameraSignActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });



    }

}
