package com.nowastit.nowastit_v3_;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class PartageActivity extends AppCompatActivity {

    private Button PartageBt;
    private Intent intentPartageBt;
    private Button VoirCarteBt;
    private Intent intentVoirCarteBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partage);

        PartageBt= (Button)findViewById(R.id.PartageBt);
        PartageBt.setOnClickListener(new PartageBtListener());

        VoirCarteBt= (Button)findViewById(R.id.VoirCarteBt);
        VoirCarteBt.setOnClickListener(new VoirCarteBtListener());


    }

    private class PartageBtListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            intentPartageBt  = new Intent();
            //Explicite Intent
            intentPartageBt.setClassName(getApplicationContext(),
                    "com.nowastit.nowastit_v3_.MainActivity");
            startActivity(intentPartageBt);
        }
    }

    private class VoirCarteBtListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            intentVoirCarteBt  = new Intent();
            //Explicite Intent
            intentVoirCarteBt.setClassName(getApplicationContext(),
                    "com.nowastit.nowastit_v3_.MapsActivity");
            startActivity(intentVoirCarteBt);
        }
    }
}
