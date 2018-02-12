package com.nowastit.nowastit_v3_;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PremierePage extends AppCompatActivity {

    private Button directionPagecguBt;
    private Intent intentdirectionPagecguBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.premiere_page);

        directionPagecguBt= (Button)findViewById(R.id.directionPagecguBt);
        directionPagecguBt.setOnClickListener(new directionPagecguBtListener());

    }

    private class directionPagecguBtListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            intentdirectionPagecguBt  = new Intent();
            //Explicite Intent
            intentdirectionPagecguBt.setClassName(getApplicationContext(),
                    "com.nowastit.nowastit_v3_.ConditionG");
            startActivity(intentdirectionPagecguBt);
        }
    }
}
