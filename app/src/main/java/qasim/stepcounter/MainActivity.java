package qasim.stepcounter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor  sensor;
    private SensorEventListener sensorEventListener;
    FragmentManager fm = getSupportFragmentManager();
    static double counter=0;
    static double goal=9999;
    double km=0;
    TextView stepView, textViewProcent, textViewKm;//, goalView;
    Button btnSetGoal;
    ProgressBar progressBar;
    DecimalFormat decFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        decFormat = new DecimalFormat("#0.0");
        progressBar = (ProgressBar)findViewById(R.id.progressBar);  //progressBar.setVisibility(View.GONE);
        btnSetGoal = (Button)findViewById(R.id.btnSetGoal);
        Typeface tf = Typeface.createFromAsset(getAssets(), "digital-7.ttf");
        stepView = (TextView)findViewById(R.id.stepTextView);
        textViewProcent = (TextView)findViewById(R.id.textViewProcent);
        textViewKm = (TextView)findViewById(R.id.textViewKm);
        //goalView = (TextView)findViewById(R.id.goalTextView);
        //goalView.setTypeface(tf);
        //goalView.setText("" + goal);
        stepView.setTypeface(tf);
        stepView.setText("" + (int)counter);



        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(sensor == null){
            Log.i("TAG", "sensor couldn't be initilized");
        }
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                counter++;
                //km=counter;
                km = getKiloMeter();
                if(counter <= goal) {
                    stepView.setText(" " + (int) counter);
                    double result = (counter / goal) * 100;
                    progressBar.setProgress((int) result);
                    textViewKm.setText("Kilometer: " + km);
                    textViewProcent.setText("" + decFormat.format(result) + "%");
                }
                if(counter == goal){
                    final MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.done);
                    mp.start();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setMessage("Congratulations!");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("Thanks",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    mp.release();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }



            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        btnSetGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DialogFrag();
                dialogFragment.show(fm, "setGoal");
            }
        });
    }
    private double getKiloMeter(){
        if(counter!=0){
            km = (counter*0.75)/1000;
        }
        return km;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(sensor!=null){
            sensorManager.registerListener(sensorEventListener, sensor, sensorManager.SENSOR_DELAY_FASTEST);
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        if(sensor!=null){
            sensorManager.unregisterListener(sensorEventListener);
        }
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
