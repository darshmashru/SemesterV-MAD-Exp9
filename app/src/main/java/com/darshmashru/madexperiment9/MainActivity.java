package com.darshmashru.madexperiment9;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.content.res.AppCompatResources;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.annotations.Annotation;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapView);
        mapView.getMapboxMap().loadStyleUri(
                Style.MAPBOX_STREETS,
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(Style style) {
                        addAnnotationToMap();
                    }
                }
        );
    }

    private void addAnnotationToMap() {
        // Create an instance of the Annotation API and get the PointAnnotationManager.
        Bitmap bitmap = bitmapFromDrawableRes(this, R.drawable.red_marker);
        if (bitmap != null) {
            annotations annotationsApi = mapView.annotations();
            PointAnnotationManager pointAnnotationManager = annotationsApi.createPointAnnotationManager(mapView);
            // Set options for the resulting symbol layer.
            PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                    .withPoint(Point.fromLngLat(18.06, 59.31))
                    .withIconImage(bitmap);
            // Add the resulting pointAnnotation to the map.
            pointAnnotationManager.create(pointAnnotationOptions);
        }
    }

    private Bitmap bitmapFromDrawableRes(Context context, @DrawableRes int resourceId) {
        return convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId));
    }

    private Bitmap convertDrawableToBitmap(Drawable sourceDrawable) {
        if (sourceDrawable == null) {
            return null;
        }
        if (sourceDrawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) sourceDrawable).getBitmap();
        }

        // copying drawable object to not manipulate on the same reference
        Drawable.ConstantState constantState = sourceDrawable.getConstantState();
        if (constantState == null) {
            return null;
        }
        Drawable drawable = constantState.newDrawable().mutate();
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}