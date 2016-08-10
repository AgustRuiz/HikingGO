package es.agustruiz.hikinggo.ui.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import es.agustruiz.hikinggo.R;

public class MapTools {

    //region [Static public methods]

    public static void drawPath(Context context, GoogleMap map, List<LatLng> pointsList){

        PolylineOptions polyline = new PolylineOptions()
                .clickable(true)
                //.zIndex(100)
                //.width(10)
                .color(Color.argb(210, 33, 150, 243));
        for (LatLng point : pointsList) {
            polyline.add(point);
        }
        map.addPolyline(polyline);

        putMarker(context, map, R.drawable.marker_start, pointsList.get(0));
        putMarker(context, map, R.drawable.marker_stop, pointsList.get(pointsList.size() - 1));

    }


    //endregion

    //region [Private methods]

    private static void putMarker(Context context, GoogleMap map, int drawableId, LatLng position) {
        Drawable d = CompatTools.getDrawable(context, drawableId);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        d.draw(canvas);
        BitmapDescriptor bd = BitmapDescriptorFactory.fromBitmap(bitmap);
        MarkerOptions markerOptions = new MarkerOptions().position(position)
                //.title("Current Location")
                //.snippet("Thinking of finding some thing...")
                .icon(bd);
        map.addMarker(markerOptions);
    }

    //endregion

}
