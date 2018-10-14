package com.example.sebastian.trinkio;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import net.margaritov.preference.colorpicker.ColorPickerDialog;

public class Einstellungen extends AppCompatActivity {

    ConstraintLayout rtv;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellungen);
//====================================Lade Farben===================================================
        rtv = (ConstraintLayout)findViewById(R.id.layout);
        rtv.setBackgroundColor(Allgemein.gebePrefkeyInt(Einstellungen.this,"HINTERGRUND",999999));
//==================================Lade Togglebutton===============================================
        ToggleButton tbtn_Ton;
        tbtn_Ton = (ToggleButton) findViewById(R.id.toggleButton);
        tbtn_Ton.setChecked(Allgemein.gebePrefkeyBoolean(Einstellungen.this,"TON"));
//========================================Ton an/aus================================================
        //Shared Prefenrces
        tbtn_Ton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Allgemein.tonaendern(Einstellungen.this);
                Allgemein.tonAbspielen(Einstellungen.this, "menue");
            }
        });
//=======================================Hintergrundfarbe setzen====================================
        Button btn_hintergrund;
        btn_hintergrund = (Button)findViewById(R.id.btn_farbe_h);

        btn_hintergrund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog cp;
                cp = new ColorPickerDialog(Einstellungen.this,Allgemein.gebePrefkeyInt(Einstellungen.this,"HINTERGRUND",999999));
                cp.setAlphaSliderVisible(true);
                cp.setCanceledOnTouchOutside(true);
                cp.setCancelable(true);
                cp.setHexValueEnabled(true);
                cp.setTitle("Wähle deine Hintergrundfarbe");
                cp.setOnColorChangedListener(new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void onColorChanged(int color) {
                        rtv.setBackgroundColor(color);
                        Allgemein.addPrefInt(Einstellungen.this,"HINTERGRUND",color);
                    }
                });
                cp.show();
            }
        });
    }
//==========Zurückbutton speeren, bzw Hauptmenü neuladen -> sonst keine Farbe angezeigt=============
    @Override
    public void onBackPressed() {
        startActivity(new Intent(Einstellungen.this, Hauptmenue.class));
    }
}