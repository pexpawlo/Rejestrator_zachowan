package com.example.pexpa.rejestrator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import static java.lang.Integer.parseInt;

public class RozpocznijBadanieMenu extends AppCompatActivity {


    private int liczbaMinuti;
    private RadioGroup rg1;
    private EditText edit;
    private boolean wybor=false;
    private void actv(final boolean active)
    {
        edit.setEnabled(active);
        if (active)
        {
            edit.requestFocus();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rozpocznij_badanie_menu);
        edit = (EditText) findViewById(R.id.liczbaMinut);
        actv(false);
        rg1 = (RadioGroup) findViewById(R.id.radioGroupMinuty);
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonReczny:
                        wybor=false;
                        actv(false);
                        break;

                    case R.id.radioButtonMinuty:
                        wybor=true;
                        actv(true);
                        break;

                }

            }
        });









        Button badaj=(Button)findViewById(R.id.rozpocznij_badanie_button);
        badaj.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Rejestracja.class);
                edit = (EditText) findViewById(R.id.liczbaMinut);
                liczbaMinuti  = Integer.parseInt(edit.getText().toString() );
               i.putExtra("czas", liczbaMinuti);
                i.putExtra("wybor", wybor);
                startActivity(i);
            }
        });
    }
}
