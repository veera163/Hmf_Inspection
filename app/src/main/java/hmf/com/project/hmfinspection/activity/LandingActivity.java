package hmf.anyasoft.es.Hmf_inspection.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import hmf.anyasoft.es.Hmf_inspection.ESurvey;
import hmf.anyasoft.es.Hmf_inspection.R;
import hmf.anyasoft.es.Hmf_inspection.utility.CustomAlertDialog;


public class LandingActivity extends AppCompatActivity {

    CardView card_inspection,card_yield,card_crop,card_irigation,customerinspection;
    Toolbar toolbar;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        card_inspection=(CardView)findViewById(R.id.card_inspection);
        card_yield=(CardView)findViewById(R.id.card_yield);
        card_crop=(CardView)findViewById(R.id.card_crop);
        card_irigation=(CardView)findViewById(R.id.card_irigation);
        customerinspection=(CardView)findViewById(R.id.customerinspection);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.e("veera", ESurvey.getAccessToken());

        customerinspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(getApplicationContext(),ConsumerInspectionActivity.class);
                startActivity(i);
            }
        });

        card_inspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),InspectionActivity.class);
                startActivity(i);
            }
        });

        card_yield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),CalculatorsActivity.class);
                startActivity(i);
            }
        });

        card_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),CropAnalysisActivity.class);
                startActivity(i);
            }
        });

        card_irigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),IrigationAnalysisActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.exit:
                 bundle = new Bundle();
                bundle.putString(CustomAlertDialog.MESSAGE, "would you Like to close this App");
                alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Exit Application?");
                alertDialogBuilder
                        .setMessage("would you Like to close this App!")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(homeIntent);


                                    }
                                })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });

                 alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            case R.id.signout:

                 bundle = new Bundle();
                bundle.putString(CustomAlertDialog.MESSAGE, "would you Like to Signout this App");
                alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Signout Application?");
                alertDialogBuilder
                        .setMessage("would you Like to Signout this App!")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        ESurvey.clearCache();
                                        Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });

                alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("would you Like to close this App !")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                                homeIntent.addCategory( Intent.CATEGORY_HOME );
                                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(homeIntent);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}
