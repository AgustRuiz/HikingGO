package es.agustruiz.hikinggo.system.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class CompassListener implements SensorEventListener {

    //region [Variables]

    private SensorManager mSensorManager;

    OnCompassChangedListener mOnCompassChangedListener = null;

    //endregion

    //region [Public methods]

    public CompassListener(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public void start() {
        //noinspection deprecation
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    public void stop() {
        mSensorManager.unregisterListener(this);
    }

    //endregion

    //region [SensorEventListener methods]

    @Override
    public void onSensorChanged(SensorEvent event) {
        //noinspection deprecation
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)
            mOnCompassChangedListener.onCompassChanged(Math.round(event.values[0]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Not needed...
    }

    //endregion

    //region [OnCompassChangedListener]

    public interface OnCompassChangedListener{
        void onCompassChanged(int degree);
    }

    public void setCompassChangedListener(OnCompassChangedListener eventListener){
        mOnCompassChangedListener = eventListener;
    }

    //endregion

}
