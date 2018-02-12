package com.nowastit.nowastit_v3_;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by DGA on 05/05/2017.
 */

public class SendPostReqAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {
    private MainActivity activity;
    private HttpClient httpClient;
    private ProgressDialog progress;

    public SendPostReqAsyncTask(MainActivity activity) {
        this.activity = activity;
        this.httpClient = HttpClientBuilder.create().build();
        this.progress = new ProgressDialog(this.activity);
    }
    @Override
    protected void onPreExecute() {
        progress.setTitle("Veuillez patienter");
        progress.setMessage("Inscription ou Récupération des données à afficher en cours...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        // Toast.makeText(activity, "Envoi de l'inscription ....",Toast.LENGTH_LONG).show();
    }
    @Override
    protected ArrayList<String> doInBackground(String... params) {
        String nom = params[0];
        String adresse = params[1];
        String telephone = params[2];
        String latitude = params[3];
        String longitude = params[4];
        String offre = params[5];
        String actionRequested = params[6];
        // Pour s'inscrire dans la base de données
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("nom", nom));
        nameValuePairs.add(new BasicNameValuePair("adresse", adresse));
        nameValuePairs.add(new BasicNameValuePair("telephone", telephone));
        nameValuePairs.add(new BasicNameValuePair("latitude", latitude));
        nameValuePairs.add(new BasicNameValuePair("longitude", longitude));
        nameValuePairs.add(new BasicNameValuePair("offre", offre));
        nameValuePairs.add(new BasicNameValuePair("actionRequested", actionRequested));
        // Pour récupérer la liste des individus inscrits
        ArrayList<String> listInscrits = new ArrayList<String>();

        InputStream content = null;
        try {

            String ServeurURL = "http://10.0.2.2/androidPHPMapsServerDB/insertlistmapsdbPDO2.php";

            HttpPost httpPost = new HttpPost(ServeurURL);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8)); // UTF_8 : Important pour transmettre le bon encodage

            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();
            content = entity.getContent();
            switch(actionRequested){
                case "inscription" : // S'inscrire
                    listInscrits.add("inscription");
                    BufferedReader reader= new BufferedReader(new InputStreamReader(content));
                    // Lire depuis le BufferedReader. Ici une ligne à lire:
                    listInscrits.add(reader.readLine());
                    break;
                case "listeInscrits" : // Lire la liste des inscrits
                    listInscrits.add("listeInscrits");
                    Scanner scanner= new Scanner(new InputStreamReader(content));
                    scanner.useDelimiter("#");
                    while (scanner.hasNext()) {
                        String str = scanner.next();  // System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> "+str);
                        str = str.replace("\n",""); str = str.trim();
                        listInscrits.add(str) ; }
                    break;
            }
        } catch (ClientProtocolException e) {
            listInscrits.add("erreur : ");
            listInscrits.add(new String("ClientProtocolException: " + e.getMessage()));
        } catch (IOException e) {
            listInscrits.add("erreur : ");
            listInscrits.add(new String("IOException: " + e.getMessage()));
        }
        return listInscrits;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
        if(progress.isShowing()) progress.dismiss();
        this.activity.populate(result);
    }
}

/********
 package cours.da.prepa2017.androidphpserver_1;
 import android.app.ProgressDialog;
 import android.os.AsyncTask;

 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.util.ArrayList;
 import java.util.List;

 import cz.msebera.android.httpclient.HttpEntity;
 import cz.msebera.android.httpclient.HttpResponse;
 import cz.msebera.android.httpclient.NameValuePair;
 import cz.msebera.android.httpclient.client.ClientProtocolException;
 import cz.msebera.android.httpclient.client.HttpClient;
 import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
 import cz.msebera.android.httpclient.client.methods.HttpPost;
 import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
 import cz.msebera.android.httpclient.message.BasicNameValuePair;

 / **
 * Created by DGA on 05/05/2017.
 * /

 public class SendPostReqAsyncTask_OLD2 extends AsyncTask<String, Void, String> {
 private MainActivity activity;
 private HttpClient httpClient;
 private ProgressDialog progress;

 public SendPostReqAsyncTask_OLD2(MainActivity activity) {
 this.activity = activity;
 this.httpClient = HttpClientBuilder.create().build();
 this.progress = new ProgressDialog(this.activity);
 }
 @Override
 protected void onPreExecute() {
 progress.setTitle("Veuillez patienter");
 progress.setMessage("Récupération des données à afficher en cours...");
 progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
 progress.show();
 // Toast.makeText(activity, "Envoi de l'inscription ....",Toast.LENGTH_LONG).show();
 }
 @Override
 protected String doInBackground(String... params) {
 String nom = params[0];
 String adresse = params[1];
 String actionRequested = params[2];
 // Pour s'inscrire dans la base de données
 List<NameValuePair> nameValuePairs = new ArrayList<>();
 nameValuePairs.add(new BasicNameValuePair("actionRequested", actionRequested));
 nameValuePairs.add(new BasicNameValuePair("adresse", adresse));
 nameValuePairs.add(new BasicNameValuePair("nom", nom));
 // Pour récupérer la liste des individus inscrits
 String listInscrits = "";

 InputStream content = null;
 try {
 String ServeurURL = "http://192.168.1.50/simpleclservdb/insert-db.php";
 HttpPost httpPost = new HttpPost(ServeurURL);
 httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

 HttpResponse response = httpClient.execute(httpPost);

 HttpEntity entity = response.getEntity();
 content = entity.getContent();
 BufferedReader reader= new BufferedReader(new InputStreamReader(content));
 switch(actionRequested){
 case "inscription" : // S'inscrire
 listInscrits +="inscription";
 // Lire depuis ce BufferedReader. Si vous ne devez lire qu'une ligne :
 listInscrits += reader.readLine();
 break;
 case "listeInscrits" : // Lire la liste des inscrits
 listInscrits +="listeInscrits";
 // Lire depuis ce BufferedReader. Si vous ne devez lire qu'une ligne :
 listInscrits += reader.readLine();
 break;
 }
 } catch (ClientProtocolException e) {
 listInscrits = "erreur";
 listInscrits += new String("ClientProtocolException: " + e.getMessage());
 } catch (IOException e) {
 listInscrits = "erreur";
 listInscrits += new String("IOException: " + e.getMessage());
 }
 return listInscrits;
 }

 @Override
 protected void onPostExecute(String  result) {
 if(progress.isShowing()) progress.dismiss();
 this.activity.populate(result);
 }
 }
 ******/
