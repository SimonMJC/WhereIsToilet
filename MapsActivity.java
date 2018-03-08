package nhj0401.googlemapexample;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    //GoogleMap 객체
    GoogleMap googleMap;
//    MapFragment mapFragment;
    LocationManager locationManager;
    RelativeLayout boxMap;
    double sLat = 36.0;
    double sLong = 126.0;


    //나의 위도 경도 고도
    double mLatitude;  //위도
    double mLongitude; //경도


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        boxMap = (RelativeLayout)findViewById(R.id.boxMap);


        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        boxMap.setVisibility(View.VISIBLE);
            //GPS가 켜져있는가 체크한다
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //GPS 설정화면으로 이동
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            startActivity(intent); //--activity start--
            finish(); //activity end
        }


        // 마시멜로우버전 이상=> 권한 요청하기
        if(Build.VERSION.SDK_INT >= 23){
            //nonPermission
            if(ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION ,
                        Manifest.permission.ACCESS_FINE_LOCATION} , 1);
            Toast.makeText(this, "권한없음", Toast.LENGTH_SHORT);
            }
            //Permission
            else{
                requestMyLocation();
                Toast.makeText(this, "현재위치 불러오는중", Toast.LENGTH_LONG).show();

            }
        }
        //마시멜로우 아래
        else{
            requestMyLocation();
            Toast.makeText(this, "버전이하", Toast.LENGTH_SHORT).show();
        }
    }



    //권한 요청후 응답 callback
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //ACCESS_COARSE_LOCATION 권한
        if(requestCode==1){
            //권한받음
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                requestMyLocation();

            }
            //권한못받음
            else{
                Toast.makeText(this, "권한못받음", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

    //현재 위치 요청
    public void requestMyLocation(){

        if(ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission
                (MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        //요청
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, locationListener);
    }

    //Location information find Listener
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }
            //현재 위치를 한번만 가져오기 위해
            locationManager.removeUpdates(locationListener);

            //위도 경도
            mLatitude = location.getLatitude();   //위도
            mLongitude = location.getLongitude(); //경도

            //Map Making
            SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
            //Callback method Setting
            mapFragment.getMapAsync(MapsActivity.this);



        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { Log.d("gps", "onStatusChanged"); }

        @Override
        public void onProviderEnabled(String provider) { }

        @Override
        public void onProviderDisabled(String provider) { }
    };

    //구글 맵 세팅
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        //Map Type- normal
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //지도 보여주기



        //현재위치 설정 => Latitude, Longitude
        LatLng position = new LatLng(mLatitude, mLongitude);

        //화면중앙의 위치,  카메라 줌비율
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
       //2
        boxMap.setVisibility(View.VISIBLE);
       //1
        googleMap.addMarker(new MarkerOptions().position(new LatLng(mLatitude, mLongitude)).icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title("현위치"));



//JSON File Import
        loadJSONFromAsset();
    }

    //Import JSON
    private void loadJSONFromAsset() {
        String jsonString = null;

        try {
            InputStream is = getAssets().open("toilet.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try
        {
            StringBuffer sb = new StringBuffer();
            Log.v("test", "1");
            JSONObject toiletJSON = new JSONObject(jsonString);
            Log.v("test2", "1");
            JSONArray dataArray = toiletJSON.getJSONArray("DATA");
            for(int i=0; i< dataArray.length(); i++)
            {
                JSONObject data = dataArray.getJSONObject(i);
                String guName = data.getString("GU_NM"); //동 명
                    Log.v("test", guName);
                    String name = data.getString("HNR_NAM");
                    Double age = data.getDouble("LAT");
                    Double aaa = data.getDouble("LNG");
                    //JSON File import => marker
                    googleMap.addMarker(new MarkerOptions().position(new LatLng(age, aaa)).icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                            .title("화장실"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}




