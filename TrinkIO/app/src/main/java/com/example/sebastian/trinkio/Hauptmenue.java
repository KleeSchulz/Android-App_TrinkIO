package com.example.sebastian.trinkio;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class Hauptmenue extends AppCompatActivity {

    Button btn_start;
    Button btn_aufgaben;
    Button btn_einstellungen;
    ConstraintLayout rtv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hauptmenue);
//====================================Lade Farben===================================================
        rtv = (ConstraintLayout) findViewById(R.id.layout);
        rtv.setBackgroundColor(Allgemein.gebePrefkeyInt(Hauptmenue.this, "HINTERGRUND", 999999));
//============================Erstmaliges Hinzufügen von Aufgaben===================================
       if (Allgemein.gebePrefkeyBoolean(this,"ERSTSTART")){
            Aufgaben_Liste aufgaben_liste = new Aufgaben_Liste();
            aufgaben_liste.hinzufuegen();
            ArrayList<String> erstmalsadden = new ArrayList<>();
            erstmalsadden = aufgaben_liste.Aufgabenliste;
            TinyDB tinyDB = new TinyDB(this);
            tinyDB.putListString("AUFGABEN", erstmalsadden);
            Allgemein.speicherPrefKeyBoolean(this,"ERSTSTART",false);
        }/*
//==============================Meldung Alkohol gefährlich==========================================
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Alkohl ist ein Nervengift, welches Gesundheittschädlich ist. Verantwortungsbewusster Umgang unter Beachtung der Altersfreigabe nicht zu vernachlässigen. Ihr haftet selbst für die Nutzung der App. Ich aktzeptiere die Folgen von TrinkIO");
        alertDialogBuilder.setPositiveButton("Ja",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {}
                });
        alertDialogBuilder.setNegativeButton("Nein",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
*/
//=================================Öffnen hinzufügen Spieler========================================
        btn_start = (Button) findViewById(R.id.btn_spiel);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Hauptmenue.this, game_add.class));
                Allgemein.tonAbspielen(Hauptmenue.this,"menue");
            }
        });
//================================Öffnen Aufgaben===================================================
        btn_aufgaben = (Button) findViewById(R.id.btn_aufgaben);
        btn_aufgaben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Hauptmenue.this, Aufgaben.class));
                Allgemein.tonAbspielen(Hauptmenue.this,"menue");
            }
        });
//=====================================Öffne Einstellungen==========================================
        btn_einstellungen = (Button)findViewById(R.id.btn_Einstellungen);
        btn_einstellungen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Hauptmenue.this,Einstellungen.class));
                Allgemein.tonAbspielen(Hauptmenue.this,"menue");
            }
        });
    }

}