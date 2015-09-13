package edu.cwru.vimig.fuber;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import edu.cwru.vimig.fuber.R;

public class MeetupActivity extends FragmentActivity {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_group);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        if (mMap == null) {
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                    }

                    public void onProviderDisabled(String provider) {
                    }
                };

                if (checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, locationListener);
                Location currentLocation = getLastKnownLocation(locationManager);
                if(currentLocation != null) {
                    System.out.println(currentLocation.getLatitude());
                    mMap.addMarker(new MarkerOptions().position(new LatLng(currentLocation.getLongitude(), currentLocation.getLatitude())).title("Marker"));
                    //setUpMap();
                }
            }
        }
    }

    public static int checkSelfPermission(Context context, String permission) {
        if (permission == null){
            throw new IllegalArgumentException("permission is null");
        }
        return context.checkPermission(permission, android.os.Process.myPid(),android.os.Process.myUid());
    }

    private Location getLastKnownLocation(LocationManager locationManager) {
        locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            try {
                Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = l;
                }
            } catch(SecurityException e){
                e.printStackTrace();
            }

        }
        return bestLocation;
    }

    private void setUpMap(LatLng groupLoc) {
        // I am so sorry.
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(42.2780436, -83.7382241), 17.0f) );
//        mMap.addMarker(new MarkerOptions().position(new LatLng(42.2780436, -83.7382241))
//                        .title("You")
//                        .draggable(false)
//                        .icon(BitmapDescriptorFactory.fromAsset("vimig.jpg"))
//        );
        LatLng myLoc = new LatLng(42.2780436, -83.7382241);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 17.0f));
        double avgLat = (myLoc.latitude * groupLoc.latitude)/2;
        double avgLong = (myLoc.longitude * groupLoc.longitude) / 2;

        mMap.addMarker(new MarkerOptions().position(new LatLng(avgLat, avgLong))
                            .title("Meet Here")
                            .draggable(false)
                            .icon(BitmapDescriptorFactory.fromAsset("x.png")));

    }

}