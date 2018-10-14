package com.example.sebastian.trinkio;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Spiel extends AppCompatActivity {
    ArrayList<String> spielerliste = new ArrayList<>();
    ArrayList<Integer> rundenliste = new ArrayList<>();
    ArrayList<String> geschlechtliste = new ArrayList<>();
    ConstraintLayout rtv;
    TextView textaufgabe;
    ImageButton erledigt,ton;
    int runde = 0;
    boolean start = true,tonboolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiel);
        rtv = (ConstraintLayout) findViewById(R.id.layout);
        rtv.setBackgroundColor(Allgemein.gebePrefkeyInt(Spiel.this, "HINTERGRUND", 999999));
//============================Spielerdaten Laden====================================================
        Bundle bundle = getIntent().getExtras();
        geschlechtliste.add("test");
        geschlechtliste.add("test2");
        spielerliste = bundle.getStringArrayList("SPIELERLISTE");
        rundenliste = bundle.getIntegerArrayList("RUNDENLISTE");
        geschlechtliste = bundle.getStringArrayList("GESCHLECHTERLISTE");
//======================================Zuweisung erster Start======================================
        textaufgabe = (TextView) findViewById(R.id.tv_aufgabetext);
        textaufgabe.setText(gebeAufgabeBearbeitet());
//===========================Button erledigt========================================================
        erledigt = (ImageButton) findViewById(R.id.ib_gemacht);
        erledigt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textaufgabe.setText(gebeAufgabeBearbeitet());
            }
        });
//============================Ton laden/ändern======================================================
        tonboolean = Allgemein.gebePrefkeyBoolean(Spiel.this,"TON");
        ton = (ImageButton)findViewById(R.id.ib_ton);
        if (tonboolean){
            ton.setImageResource(R.drawable.musik_entmute);
        }
        else {
            ton.setImageResource(R.drawable.musik_mute);
        }
        ton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tonboolean)
                {
                    ton.setImageResource(R.drawable.musik_mute);
                    tonboolean = false;
                }
                else {
                    ton.setImageResource(R.drawable.musik_entmute);
                    tonboolean = true;
                }
                Allgemein.tonaendern(Spiel.this);
            }
        });
    }
//================================Zurückbutton Spielbeenden=========================================
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Wollt ihr das aktuelle Spiel beenden?");
                alertDialogBuilder.setPositiveButton("Ja",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                spielerliste.clear();
                                rundenliste.clear();
                                geschlechtliste.clear();
                                startActivity(new Intent(Spiel.this, Hauptmenue.class));
                            }
                        });
        alertDialogBuilder.setNegativeButton("Nein",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    //=======================================gebe Aufgabe===========================================
    public String gebeAufgabeBearbeitet() {
        Random rdm = new Random();
        Aufgaben_Liste aufliste = new Aufgaben_Liste();
        aufliste.hinzufuegen();
        String aufgabe = aufliste.gebeaufgabe(Spiel.this);
        if (aufgabe.indexOf("@")>=0) {
            int schluck = rdm.nextInt(5) + 1;
            if (schluck == 1) {
                aufgabe = aufgabe.replaceAll("@", "ein Schluck");
            } else {
                aufgabe = aufgabe.replaceAll("@", String.valueOf(schluck));
            }
        }
        int spieler1 = rdm.nextInt(spielerliste.size());
        if (aufgabe.indexOf("-")>=0) {
            aufgabe = aufgabe.replaceAll("-", String.valueOf(spielerliste.get(spieler1)));
        }
        //Wenn Spieler 1 und 2 sind gleich
        if (aufgabe.indexOf("~")>=0) {
            int spieler2 = rdm.nextInt(spielerliste.size());
            while (spieler1 == spieler2) {
                spieler2 = rdm.nextInt(spielerliste.size());
            }
            aufgabe = aufgabe.replace("~", String.valueOf(spielerliste.get(spieler2)));
        }
        if (aufgabe.indexOf("@#_")>=0) {
            int ges = rdm.nextInt(geschlechtliste.size());
            aufgabe = aufgabe.replace("_", String.valueOf(geschlechtliste.get(ges)));
        }
        runde++;
        pruefeRunde();
        return aufgabe;
    }

//==========================prüfe Runde ggf. löschen, Spiel beenden================================
    public void pruefeRunde(){

        //for (int i = 0; i < rundenliste.size();i++){
          //  if (rundenliste.get(i)==runde){
            /*    spielerliste.remove(i);
                rundenliste.remove(i);
                geschlechtliste.remove(i);
                if (spielerliste.size()==1){
                    //Ansage das nurnoch 1 Spieler vorhanden
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setMessage("Es gibt keine Teilnehmer mehr! Spiel wird beendet");
                    alertDialogBuilder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                            startActivity(new Intent(Spiel.this, Hauptmenue.class));
                                }
                            });
                    }
                }
                i--;
            }*/
        }
    }






