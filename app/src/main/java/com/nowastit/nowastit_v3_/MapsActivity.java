package com.nowastit.nowastit_v3_;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    // private LocationManager locationManager;    private String provider;    private LatLng pos;
    private int nbOffres;
    private ArrayList<String> globalList;
    private ArrayList<String> nomList = new ArrayList<>();
    private ArrayList<String> adresseList = new ArrayList<>();
    private ArrayList<String> telephoneList = new ArrayList<>();
    private ArrayList<String> latitudeList = new ArrayList<>();
    private ArrayList<String> longitudeList = new ArrayList<>();
    private ArrayList<String> offreList = new ArrayList<>();
    private TextView contenuOffreTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        contenuOffreTv = (TextView)findViewById(R.id.contenuOffreTv);
        globalList = (ArrayList<String>) getIntent().getSerializableExtra("globalList");
        nbOffres = globalList.size();
        createInfoLists(globalList);
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void createInfoLists(ArrayList<String> globalList) {
        for(int i = 0; i < nbOffres; i++){
            String str = globalList.get(i);
            String[] strArray= str.split(Pattern.quote("|"));
            nomList.add(strArray[0]);
            adresseList.add(strArray[1]);
            telephoneList.add(strArray[2]);
            latitudeList.add(strArray[3]);
            longitudeList.add(strArray[4]);
            offreList.add(strArray[5]);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(int i = 0; i < nbOffres; i++) {
            String nom = i+":"+nomList.get(i); // format--> indice arraylist:index BD:nom
            String adresse = adresseList.get(i);
            String telephone = telephoneList.get(i);
            double latitude = Double.parseDouble(latitudeList.get(i));
            double longitude = Double.parseDouble(longitudeList.get(i));
            LatLng pos = new LatLng(latitude, longitude);
            builder.include(pos);
            contenuOffreTv.setText("offre : "+offreList.get(i));
            mMap.addMarker(new MarkerOptions().position(pos).title(nom).snippet(adresse+"/"+telephone));
        }
        // positionnement et zoom
        if (nbOffres == 0) { // pas d'image à afficher
            LatLng MEAUX = new LatLng(48.950001,2.86667); // On se place par défaut à Meaux ( modifié le 29/09/2017
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(MEAUX, 15);
            mMap.animateCamera(cameraUpdate);
        }  else {
            LatLngBounds zone = builder.build(); // zone qui contient toutes les images
            int padding = 100;
            // Gets screen size
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            // Calls moveCamera passing screen size as parameters
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(zone, width, height, padding);
            mMap.animateCamera(cameraUpdate);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker) {
                String[] title = marker.getTitle().split(":");
                int i = parseInt(title[0]); // indice dans ArrayList
                // Attention, si pas de saut dans l'indexation automatique décalage de 1 ou plus avec l'index de ArrayList.
                contenuOffreTv.setText("Offre : "+offreList.get(i));
                return false; // Affiche le texte du marqueur en même temps que l'image dans l'ImageView.
            }
        });

    }

}
