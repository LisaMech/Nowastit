package com.nowastit.nowastit_v3_;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText nomEt;
    private EditText adresseEt;
    private EditText telephoneEt;
    private EditText offreEt;
    private Button insertBt, showListBt;
    private Button coordAdrBt, coordMobileBt;
    private TextView latTv;
    private TextView longTv;
    private TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Récupération des widgets
       /* nomEt = (EditText) findViewById(R.id.nomEt);
        adresseEt = (EditText) findViewById(R.id.adresseEt);
        telephoneEt = (EditText) findViewById(R.id.telephoneEt);
        offreEt = (EditText) findViewById(R.id.offreEt);
        latTv = (TextView) findViewById(R.id.latTv);
        longTv = (TextView) findViewById(R.id.longTv);
        resultTv = (TextView) findViewById(R.id.resultTv);
        coordAdrBt = (Button) findViewById(R.id.coordAdrBt);
        coordAdrBt.setOnClickListener(new coordAdrBtListener());
        coordMobileBt = (Button) findViewById(R.id.coordMobileBt);
        coordMobileBt.setOnClickListener(new coordMobileBtListener());
        insertBt = (Button) findViewById(R.id.insertBt);
        insertBt.setOnClickListener(new insertBtListener());
        showListBt = (Button) findViewById(R.id.showListBt);
        showListBt.setEnabled(false);
        showListBt.setOnClickListener(new showListBtListener());

        // Récupération des permissions et activation du bouton d'affichage de la carte
        localisationAutorisation();
        */
    }
    /********************************************************************/
    /**              Méthodes qui Demandent des permissions            **/
    /********************************************************************/
    private static final int MY_PERMISSION_REQUEST_LOCATION = 1;
    private void localisationAutorisation() {
        if((ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
            return;
        }
        // Update location
        showListBt.setEnabled(true);
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_LOCATION :
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // We can now safely use the API we requested access to
                    showListBt.setEnabled(true);
                } else {
                    // Permission was denied or request was cancelled
                    Toast.makeText(MainActivity.this, "Permission LOCALISATION", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    /******************************************************************/
      // Classes internes d'écoute des boutons
    private class insertBtListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String nom = nomEt.getText().toString().trim(); // nom = new String(nom.getBytes(), Charset.forName("UTF-8"));
            String adresse = adresseEt.getText().toString().trim();
           // adresse = adresse.replaceAll("'","''"); // adresse = new String(adresse.getBytes(), Charset.forName("UTF-8"));
            String telephone = telephoneEt.getText().toString().trim();
            String latitude = latTv.getText().toString().trim();
            String longitude = longTv.getText().toString().trim();
            String offre = offreEt.getText().toString().trim(); // offre = new String(offre.getBytes(), Charset.forName("UTF-8"));

            if(!nom.equals("") && !adresse.equals("") && !telephone.equals("") && !latitude.equals("") && !longitude.equals("") && !offre.equals("")) {
                // Lancement de la connection asynchrone avec les bons paramètres
                SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask(MainActivity.this);
                sendPostReqAsyncTask.execute(nom, adresse, telephone, latitude, longitude, offre, "inscription");
            } else {
                Toast.makeText(MainActivity.this, "Renseignez tous les champs ! ",Toast.LENGTH_LONG).show();
            }
        }
    }
    private class showListBtListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // Lancement de la connection asynchrone avec les bons paramètres
            SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask(MainActivity.this);
            sendPostReqAsyncTask.execute("", "", "", "", "", "", "listeInscrits");
        }
    }
    // Récupération des coordonnées du mobile
    private class coordMobileBtListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, GPSTrackerActivity.class);
            startActivityForResult(intent,1);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 1) {
            Bundle extras = data.getExtras();
            Double longitude = extras.getDouble("Longitude");
            Double latitude = extras.getDouble("Latitude");
            latTv.setText(latitude.toString());
            longTv.setText(longitude.toString());
        }
    }
    // Récupération des coordonnées de l'adresse
    private class coordAdrBtListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String adresse = adresseEt.getText().toString().trim();
            if(!adresse.equals("")) {
                Geocoder coder = new Geocoder(MainActivity.this);
                // May throw an IOException
                try {
                    List<Address> localisations;            // Liste de localisations (objets Address)
                    localisations = coder.getFromLocationName(adresse, 1);
                    if (localisations == null || localisations.size() == 0) {
                        Toast.makeText(MainActivity.this, "Pas de localisation trouvée pour l'adresse !", Toast.LENGTH_LONG).show();
                    } else {
                        Address location = localisations.get(0);
                        Double latitude = location.getLatitude();
                        Double longitude = location.getLongitude();
                        latTv.setText(latitude.toString());
                        longTv.setText(longitude.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(MainActivity.this, "Entrez une adresse !", Toast.LENGTH_LONG).show();
            }
        }
    }
    //Méthode qui affiche les données en retour du serveur, sur une GoogleMap
    public void populate(ArrayList<String> result) {
        if(result.get(0).equals("inscription")) {
            resultTv.setText("Retour du serveur : "+ result.get(1));
        } else {
            // On affiche la liste des inscrits
            // result.remove(result.get(result.size()-1));
            result.remove(0); // On supprime le premier élément inutile et génant à présent.
            Intent mapsIntent = new Intent(MainActivity.this, MapsActivity.class);
            mapsIntent.putExtra("globalList",result);
            startActivity(mapsIntent);
        }
    }


}

