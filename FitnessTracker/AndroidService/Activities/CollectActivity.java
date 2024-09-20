package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class CollectActivity extends AppCompatActivity implements SensorEventListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 5;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Button start_button, stop_button;
    private Spinner exercise_dropdown;
    private String selectedItem;
    private boolean isMeasuring, startButtonClickedFirstTime;
    private File directory, file;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private Thread publishThread;
    private ConnectionFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //Granting permisions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                // Check for WRITE_EXTERNAL_STORAGE permission
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Permission(s) not granted, request them
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE);
        }
        //Initializing accelerometer
        Log.d(TAG, "onCreate: Initializing Sensor Services");
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(CollectActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onCreate: Registered accelerometer listener");

        //Initializing dropdown menu
        exercise_dropdown = (Spinner) findViewById(R.id.exercise_dropdown);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.exercise_array,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exercise_dropdown.setAdapter(adapter);
        exercise_dropdown.setOnItemSelectedListener(this);

        isMeasuring = false;//Boolean for knowing if we start measuring or not

        //For CSV/////////////////////////////////////////////////////
        String filename = "Measurements_" + System.currentTimeMillis() + ".csv";
        directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        file = new File(directory, filename);

        try {
            fileWriter = new FileWriter(file, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bufferedWriter = new BufferedWriter(fileWriter);

        startButtonClickedFirstTime = false;


        //RabbitMQ//////////////////////////////////////////////////////////////////
        factory = new ConnectionFactory();
        setupConnectionFactory();
        publishToAMQP();


        //Initializing Start Button/////////////////////////////////////////////////
        start_button =(Button) findViewById(R.id.start_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(startButtonClickedFirstTime == false){
                    Log.d(TAG, "Button clicked");
                    createCSV();
                }
                isMeasuring = true;
            }
        });

        //Initializing Stop Button
        stop_button = (Button) findViewById(R.id.stop_button);
        stop_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopMeasuring();
            }
        });


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Log.d(TAG, "onSensorChanged: X:" + event.values[0] + "Y: " + event.values[1] +  "Z: " + event.values[2]);
        LocalDateTime timestamp = LocalDateTime.now();
        if (bufferedWriter != null && isMeasuring) {
            try {
                String message = event.values[0] + ", " + event.values[1] + "," + event.values[2] + ", " + timestamp + ", "+ selectedItem + "\n";
                bufferedWriter.write(message);
                publishMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void createCSV(){

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            try {
                // Write accelerometer data to CSV file
                bufferedWriter.write("X_accelerom, Y_accelerom, Z_accelerom, Timestamp, Exercise_Type\n");

                isMeasuring = true;
                startButtonClickedFirstTime = true;

                Log.d(TAG, "CSV file created.");
            } catch (IOException e) {
                Log.e(TAG, "Error writing accelerometer data to CSV file", e);
                Toast.makeText(this, "Error writing accelerometer data to CSV file", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "External storage not available", Toast.LENGTH_SHORT).show();
        }

    }

    public void stopMeasuring(){
        isMeasuring = false;
        Log.d(TAG, "S-a apasat butonul de stop.");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedItem =  (String) parent.getItemAtPosition(position);
        Log.d("DROPDOWN", "Selected Item is: " + selectedItem);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    ///RabbitMQ ////////////////////////////////////////////////////////////////////////////////////
    private BlockingDeque<String> queue = new LinkedBlockingDeque<String>();
    void publishMessage(String message) {
        try {
            Log.d(TAG,"[q] " + message);
            queue.putLast(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void setupConnectionFactory() {
        String uri = "amqps://gwqwxywo:ntgFhoV5MGfdg-drZjLtPBHYRnSJvx1C@goose.rmq2.cloudamqp.com/gwqwxywo";
        try {
            factory.setAutomaticRecoveryEnabled(false);
            factory.setUri(uri);
        } catch (KeyManagementException | NoSuchAlgorithmException | URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

    public void publishToAMQP()
    {
        publishThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Connection connection = factory.newConnection();
                        Channel ch = connection.createChannel();
                        ch.confirmSelect();

                        ch.queueDeclare("queue", false, false, false, null);

                        while (true) {
                            Log.d("", "se intra aici");
                            String message = queue.takeFirst();
                            try{
                                ch.basicPublish("", "queue", null, message.getBytes());
                                Log.d("", "[s] " + message);
                                ch.waitForConfirmsOrDie();
                            } catch (Exception e){
                                Log.d("","[f] " + message);
                                queue.putFirst(message);
                                throw e;
                            }
                        }
                    } catch (InterruptedException e) {
                        break;
                    } catch (Exception e) {
                        Log.d("", "Connection broken: " + e.getClass().getName());
                        try {
                            Thread.sleep(5000); //sleep and then try again
                        } catch (InterruptedException e1) {
                            break;
                        }
                    }
                }
            }
        });
        publishThread.start();
    }

    //Thread-ul este intrerupt cand se inchide aplicatia
    @Override
    protected void onDestroy() {
        super.onDestroy();
        publishThread.interrupt();
    }
}
