package com.example.pumpupeo.app;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private TextView text1;
        private TextView text2;
        private int s_data;
        private boolean isUp = true;
        private int counter = 0;
        private SensorManager sMgr;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            text1 = (TextView) rootView.findViewById(R.id.textView);
            text2 = (TextView) rootView.findViewById(R.id.textView2);
            sMgr = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
            Sensor light_sensor =  sMgr.getDefaultSensor(Sensor.TYPE_LIGHT);

            text2.setText(light_sensor.getName());

            sMgr.registerListener(lightSensorEventListener,
                    light_sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
            return rootView;
        }

        SensorEventListener lightSensorEventListener = new SensorEventListener(){

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

            public void onSensorChanged(SensorEvent event) {
                if(event.sensor.getType()==Sensor.TYPE_LIGHT){
                    float currentReading = event.values[0];
                    text1.setText(String.valueOf(currentReading));
                    s_data = (int) currentReading;
                    if(s_data < 10 && isUp){
                        isUp = false;
                        text2.setText(String.valueOf(counter++));
                    }
                    if(s_data > 70 && !isUp) isUp = true;
                }
            }
        };
    }

}
