package com.badlogic.androidgames.framework.impl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.WindowManager;


public class AccelerometerHandler implements SensorEventListener {
    float accelX;
    float accelY;
    float accelZ;
    
    int rotation;
    
    
    

    public AccelerometerHandler(Context context) {
    	Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        rotation = display.getRotation();
        
        
        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            manager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nothing to do here
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
    	switch(rotation){
    	case 0:
    		accelX = event.values[0];
            accelY = event.values[1];
            accelZ = event.values[2];
            break;
    	case 1:
    		accelX = -event.values[1];
            accelY = event.values[0];
            accelZ = event.values[2];
            break;
    	case 2:
    		accelX = -event.values[0];
            accelY = -event.values[1];
            accelZ = event.values[2];
            break;
    	case 3:
    		accelX = event.values[1];
            accelY = -event.values[0];
            accelZ = event.values[2];
            break;
    	}
        
    }

    public float getAccelX() {
        return accelX;
    }

    public float getAccelY() {
        return accelY;
    }

    public float getAccelZ() {
        return accelZ;
    }
}
