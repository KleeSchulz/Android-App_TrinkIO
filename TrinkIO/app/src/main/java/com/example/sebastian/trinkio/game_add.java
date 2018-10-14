package com.example.sebastian.trinkio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import java.util.ArrayList;

public class game_add extends AppCompatActivity {

    boolean mannadd = true;
    boolean frauadd = true;
    Boolean frau, mann;
    RadioButton men, female;
    EditText runde,spieler;
    ImageButton add,play;
    ConstraintLayout rtv;
    ArrayList<Editable> spielerlist = new ArrayList<Editable>();
    ArrayList<String> gescchlechterliste = new ArrayList<>();
    ArrayList<Integer> rundenliste = new ArrayList<>();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_add);
//====================================Lade Farben===================================================
        rtv = (ConstraintLayout)findViewById(R.id.layout);
        rtv.setBackgroundColor(Allgemein.gebePrefkeyInt(game_add.this,"HINTERGRUND",999999));
    //====================Radio Buttons=============================================================
        female =(RadioButton)findViewById(R.id.rb_female);
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                frau = true;
                mann = false;
            }
        });
        men = (RadioButton)findViewById(R.id.rb_men);
        men.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mann = true;
                frau = false;
            }
        });
    //====================Hinzufügen Spieler========================================================
        runde = (EditText) findViewById(R.id.et_round);
        spieler = (EditText)findViewById(R.id.et_spieler);
        add = (ImageButton)findViewById(R.id.btn_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spieler.getText().toString().length()<=0){
                    Allgemein.toast(game_add.this,"Spielername darf nicht leer sein");
                }
                else if (spieler.getText().toString().indexOf('@')>=0){
                    Allgemein.toast(game_add.this,"Spielername darf kein @ enthalten");
                }
                else if (spieler.getText().toString().indexOf('~')>=0){
                    Allgemein.toast(game_add.this,"Spielername darf kein ~ enthalten");
                }
                else if (spieler.getText().toString().indexOf('_')>=0){
                    Allgemein.toast(game_add.this,"Spielername darf kein _ enthalten");
                }
                else if (spieler.getText().toString().indexOf('-')>=0){
                    Allgemein.toast(game_add.this,"Spielername darf kein - enthalten");
                }
                else  if (female.isChecked() == men.isChecked()){
                    Allgemein.toast(game_add.this,"Bitte Geschlecht auswählen");
                }
                else if (runde.getText().toString().length()<=0){
                    Allgemein.toast(game_add.this,"Rundenanzahl darf nicht leer sein");
                }
                else {
                    spielerlist.add(spieler.getText());
                    rundenliste.add(Integer.parseInt(runde.getText().toString()));
                    Allgemein.toast(game_add.this,spielerlist.get(spielerlist.size()-1) + " wurde hinzugefügt!");
                    spieler.setText("");
                }
                Allgemein.tonAbspielen(game_add.this,"menue");
            }
        });
    //=========================Zum Spiel1===========================================================
        play = (ImageButton) findViewById(R.id.btn_play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spielerlist.size() == 0){
                    Allgemein.toast(game_add.this,"Es wurden keine Spieler hinzugefügt! Bitte füge mindestens 2 Spieler hinzu!");
                }
                else if (spielerlist.size() == 1){
                    Allgemein.toast(game_add.this,"Bitte füge noch einen weiteren Spieler hinzu");
                }
                else {

                    if (men.isChecked()){
                        if (mannadd){
                            gescchlechterliste.add("Frauen");
                            mannadd = false;
                        }
                    }
                    if (female.isChecked()){
                        if (frauadd){
                            gescchlechterliste.add("Männer");
                            frauadd = false;
                        }
                    }
                    Intent intent = new Intent(game_add.this, Spiel.class);
                    intent.putExtra("SPIELERLISTE",spielerlist);
                    intent.putExtra("GESCHLECHTERLISTE",rundenliste);
                    startActivity(intent);
                    Allgemein.tonAbspielen(game_add.this,"menue");
                }
            }
        });
    }
}

