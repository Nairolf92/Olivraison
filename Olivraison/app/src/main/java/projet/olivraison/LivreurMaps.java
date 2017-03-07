package projet.olivraison;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import static android.Manifest.permission_group.LOCATION;

public class LivreurMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Location mLastLocation;
    public LocationManager mLocationManager;
    private GoogleApiClient mGoogleApiClient;
    private  String APIKEYDIRECTION = "AIzaSyD5h5tczzGszSA26zOu-xhEgTVTEvcmC4o";
    private String APIDIRECTIONURL1 = "https://maps.googleapis.com/maps/api/directions/json?origin=";
    private String APIDIRECTIONURL2 = "&destination=";
    private String APIDIRECTIONURL3 =  "&key=AIzaSyD5h5tczzGszSA26zOu-xhEgTVTEvcmC4o&mode=driving";
    private String GEOCODEURL ="https://maps.googleapis.com/maps/api/geocode/json?address=";

    //27%20avenue%20de%20l%27europe%2092700

    public double DepartLatitude = 48.951434;
    public double DepartLongitude = 2.386974;
    LatLng sydney = new LatLng(DepartLatitude, DepartLongitude);
    public double MaLatitude, MaLongitude;
    Polyline line;
    List<Polyline> polylines = new ArrayList<Polyline>();
    Integer id = 0;
    String adresse = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livreur_maps);

        int LOCATION_REFRESH_TIME = 100;
        int LOCATION_REFRESH_DISTANCE = 1;

        Intent intent = getIntent();
        id = intent.getIntExtra("id", id);
        adresse = intent.getStringExtra("adresse");
        Log.i("test", "adresse: " + adresse);





        // fin requete



        // ----------------------------- bouton onclick --------------------------------------//
        Button monBouton = (Button) findViewById(R.id.terminer);

        monBouton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String url = "http://antoine-lucas.fr/api_android/web/index.php/api/commande/update/1?statut=2";

                JsonObjectRequest jsObjRequestgeocode = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                               // Log.i("test", "Response: " + response.toString());
                                //Intent i = new Intent (getBaseContext(), indexLivreur.class);
                                //startActivity(i);
                                try
                                {
                                    String jsonResultat = response.toString();
                                    JSONObject jsonObject = new JSONObject(jsonResultat);

                                    JSONArray geometries = jsonObject.getJSONArray("geometrie");
                                    Log.i("test", "geometries: dddddddddddddddddddddddddddddddddddddddddddddddd" + geometries);
                                    Log.i("test", "geometries: " + geometries);



                                } catch (JSONException e) {
                                    Log.i("test", "geometries: fail");
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("test", "geometries: " + error);
                                // TODO Auto-generated method stub

                            }
                        });
                Volley.newRequestQueue(getBaseContext()).add(jsObjRequestgeocode);
            }
        });
        // fin bouton click -------------------------------------------------------------

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, mLocationListener);


        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {

        }
            /*
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(48, 2))
                    .title("Hello world"));
            */
            //mMap.addMarker(new MarkerOptions().position(sydney).title("destination"));
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));


        //------------ requete pour recuperer la position de la destination -----------------//
        adresse = adresse.replaceAll("\\s+","+");
        adresse = adresse.replaceAll("'","");

        String url = GEOCODEURL + adresse;
        Log.i("test", "urllllllllllllllllllllllllll: " + url);

        JsonObjectRequest jsObjRequests = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.i("test", "Response: " + response.toString());
                        try {
                            String jsonResultat = response.toString();

                            JSONObject jsonObject = new JSONObject(jsonResultat);

                            JSONArray results = jsonObject.getJSONArray("results");
                            Log.i("test", "urllllllllllllllllllllllllll: " + jsonObject);
                            JSONObject result = results.getJSONObject(0);
                            JSONObject geometrie = result.getJSONObject("geometry");
                            JSONObject location = geometrie.getJSONObject("location");

                            double lat = location.getDouble("lat");
                            double lng = location.getDouble("lng");

                            sydney = new LatLng(lat, lng);
                            mMap.addMarker(new MarkerOptions().position(sydney).title("destination"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));



                            Log.i("test", "geometries: " + location);

                        } catch (JSONException e) {
                            Log.i("test", "geometries: fail");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        Volley.newRequestQueue(getBaseContext()).add(jsObjRequests);

    }


    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //code
            System.out.println("onLocationChanged");
            Log.i("test", "yaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            mLastLocation = location;

            MaLatitude = location.getLatitude();
            MaLongitude = location.getLongitude();

            MetreAJourItineraire();
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            System.out.println("onStatusChanged");
        }
        @Override
        public void onProviderEnabled(String provider) {
            System.out.println("onProviderEnabled");
        }
        @Override
        public void onProviderDisabled(String provider) {
            System.out.println("onProviderDisabled");
            //turns off gps services
        }
    };

    public void MetreAJourItineraire () {
        /*
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(MaLatitude, MaLongitude))
                .title("Hello world"));
                */
        LatLng positionActuelle = new LatLng(MaLatitude, MaLongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionActuelle , 16));

        String urL = APIDIRECTIONURL1 + MaLatitude + "," + MaLongitude + APIDIRECTIONURL2 + DepartLatitude + "," + DepartLongitude + APIDIRECTIONURL3;
        String url = "http://antoine-lucas.fr/api_android/web/index.php/api/commande/1";
        Log.i("test", urL);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, urL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //mTxtDisplay.setText("Response: " + response.toString());
                        Log.i("test", response.toString());


                        try
                        {   double DebutLat = 0;
                            double DebutLong = 0;

                            double FinLat = 0;
                            double FinLong = 0;

                            String jsonResultat = response.toString();
                            JSONObject jsonObject = new JSONObject(jsonResultat);

                            JSONArray routesArray = jsonObject.getJSONArray("routes");
                            Log.i("route", routesArray.toString());
                            JSONObject route = routesArray.getJSONObject(0);
                            Log.i("route", route.toString());


                            JSONArray legs = route.getJSONArray("legs");
                            JSONObject leg = legs.getJSONObject(0);

                            JSONArray steps = leg.getJSONArray("steps");
                            Log.i("line", "ligne : "+line);
                            /*
                            if(line != null) {
                                line.remove();

                            }
    */
                            for(Polyline line : polylines)
                            {
                                line.remove();
                            }
                            polylines.clear();

                            int nsteps = steps.length() ;
                            for(int i=0;i<nsteps;i++) {
                                    JSONObject new_onj = steps.getJSONObject(i);

                                    JSONObject start_location = new_onj.getJSONObject("start_location");
                                    JSONObject end_location = new_onj.getJSONObject("end_location");

                                    DebutLat = (double) start_location.get("lat");
                                    DebutLong = (double) start_location.get("lng");

                                    FinLat = (double) end_location.get("lat") ;
                                    FinLong = (double) end_location.get("lng");

                                    Log.i("LAtidude de depart", "metres : "+DebutLat);
                                Log.i("Long de depart", "metres : "+DebutLong);
                                Log.i("LAtidude de fin", "metres : "+FinLat);
                                Log.i("longitude de fin", "metres : "+FinLong);
    /*
                                 line = mMap.addPolyline(new PolylineOptions()
                                        .add(new LatLng(DebutLat,DebutLong), new LatLng(FinLat, FinLong))
                                        .width(5)
                                        .color(Color.BLUE)
                                );
                                */
                                polylines.add(mMap.addPolyline(new PolylineOptions()
                                        .add(new LatLng(DebutLat,DebutLong), new LatLng(FinLat, FinLong))
                                        .width(5)
                                        .color(Color.BLUE)
                                ));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        Volley.newRequestQueue(this).add(jsObjRequest);


    }
    /*    @Override
        public void onBackPressed() {}*/


}
