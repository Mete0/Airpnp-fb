package airpnp.pennapps.com.airpnp;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v13.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class EventsActivity extends AppCompatActivity {
    private ProgressDialog pleaseWait;

    public ProgressDialog getPleaseWait() {
        return pleaseWait;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    private LatLng latLng;
    private LocationManager m_LocationManager;
    private Location m_Location;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 6;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        FragmentManager fm = getSupportFragmentManager();
        fragment = new EventsFragment();
        fm.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit();





    }
    @Override
    public void onAttachFragment(Fragment fragment){
        getLocationPermission();
    }

    public void getLocationPermission() {
        Log.d("!!!", "getting permissions");

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EventsActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        pleaseWait=ProgressDialog.show(this,"Please wait","Fetching data...",true,false);
        getLocation();

    }

    public void getLocation() {
        System.out.println("#####"+ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)+"#####");
        System.out.println("#####"+PackageManager.PERMISSION_GRANTED+"#####");
        System.out.println("#####"+ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)+"#####");
        System.out.println("#####"+PackageManager.PERMISSION_GRANTED+"#####");
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //System.out.println("#####"+"1"+"#####");
            m_LocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            System.out.println("#####"+m_LocationManager+"#####");
            Criteria criteria = new Criteria();
            //System.out.println("#####"+"3"+"#####");
            String bestProvider = m_LocationManager.getBestProvider(criteria, true);


            System.out.println("#####"+bestProvider+"#####");
            if (m_Location==null) {
                m_Location = m_LocationManager.getLastKnownLocation(bestProvider);
                System.out.println("#####" + m_Location + "#####");
            }


            //if (m_Location != null) {
                System.out.println("#####"+"6"+"#####");
                latLng = new LatLng(33.762909, -84.422675);
                System.out.println("#####"+"7"+"#####");
                ((EventsFragment) fragment).getEventList();
             //   System.out.println("#####"+"8"+"#####");
            //}
            //else{
             //   Toast toast = Toast.makeText(EventsActivity.this,"Problem getting GPS",Toast.LENGTH_LONG);
            //    toast.show();
            //}
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Map", "Permission granted");
                    getLocation();
                } else {
                    Log.d("Map", "Permission denied");
                }
                return;
            }
        }
    }


}

