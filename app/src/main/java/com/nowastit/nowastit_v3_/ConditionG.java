package com.nowastit.nowastit_v3_;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ConditionG extends AppCompatActivity {

    private Button CGUBt;
    private Button precedentMainBt;
    private Intent intentPrecedentMain;
    private Button directionPagePartageBt;
    private Intent intentDirectionPagePartageBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition_g);

        CGUBt= (Button)findViewById(R.id.CGUBt);
        CGUBt.setOnClickListener(new CGUBtListener());

        precedentMainBt= (Button)findViewById(R.id.precedentMainBt);
        precedentMainBt.setOnClickListener(new precedentMainBtListener());

        directionPagePartageBt= (Button)findViewById(R.id.directionPagePartageBt);
       directionPagePartageBt.setOnClickListener(new directionPagePartageBtListener());

    }

    private class CGUBtListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            String url = "http://www.nowastit.com/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        }
    }

    private class precedentMainBtListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            intentPrecedentMain  = new Intent();
            //Explicite Intent
            intentPrecedentMain.setClassName(getApplicationContext(),
                    "com.nowastit.nowastit_v3_.PremierePage");
            startActivity(intentPrecedentMain);
        }
    }

    private class directionPagePartageBtListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            intentDirectionPagePartageBt  = new Intent();
            //Explicite Intent
            intentDirectionPagePartageBt.setClassName(getApplicationContext(),
                    "com.nowastit.nowastit_v3_.PartageActivity");
            startActivity(intentDirectionPagePartageBt);

        }
    }
}
