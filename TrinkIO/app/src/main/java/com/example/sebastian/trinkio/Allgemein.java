package com.example.sebastian.trinkio;


import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Allgemein {
    //======================Toast Ausgabe===============================================================
    public static void toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    //=========================Button Ton Klick=========================================================
    public static void tonAbspielen(Context context, String datei) {
        if (gebePrefkeyBoolean(context, "TON")) {
            try {
                Uri uri = Uri.parse("android.resource://com.example.sebastian.trinkio/raw/" + datei);
                MediaPlayer mediaPlayer = MediaPlayer.create(context, uri);
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.release();
                    }
                });
            } catch (Exception e) {
                toast(context, "Musikdatei konnte nicht gefunden werden");
            }
        }
    }
//==========================Speicher ändere Ton boolean=============================================
    public static void tonaendern(Context context) {
        final SharedPreferences prefs;
        final SharedPreferences.Editor prefsedit;
        final String TON = "TON";
        prefs = context.getSharedPreferences("Speicher", MODE_PRIVATE);
        prefsedit = prefs.edit();
        if (prefs.getBoolean(TON, true)) {
            prefsedit.putBoolean(TON, false);
            prefsedit.commit();
            toast(context, "Ton ist deaktiviert");
        } else {
            prefsedit.putBoolean(TON, true);
            prefsedit.commit();
            toast(context, "Ton ist aktiviert");

        }
    }
//=====================Speichere INT Farbe==========================================================
    public static void addPrefInt(Context context, String key, int farbe) {
        final SharedPreferences prefs;
        final SharedPreferences.Editor prefsedit;
        prefs = context.getSharedPreferences("Speicher", MODE_PRIVATE);
        prefsedit = prefs.edit();
        prefsedit.putInt(key, farbe);
        prefsedit.commit();
        toast(context, "Farbe wurde erfolgreich gespeichert");
    }

//=================================Lade Tonvariable=================================================
    public static boolean gebePrefkeyBoolean(Context context, String key) {
        final SharedPreferences prefs;
        prefs = context.getSharedPreferences("Speicher", MODE_PRIVATE);
        boolean x = prefs.getBoolean(key, true);
        return x;
    }
//=======================Speicher Boolean===========================================================
    public static void speicherPrefKeyBoolean(Context context, String key, Boolean speichern) {
        final SharedPreferences prefs;
        final SharedPreferences.Editor prefsedit;
        prefs = context.getSharedPreferences("Speicher", MODE_PRIVATE);
        prefsedit = prefs.edit();
        prefsedit.putBoolean(key,speichern);
    }

//=================================Lade Farbe=======================================================
    public static int gebePrefkeyInt(Context context, String key, int Farbwertdef) {
        final SharedPreferences prefs;
        prefs = context.getSharedPreferences("Speicher", MODE_PRIVATE);
        int x = prefs.getInt(key, 65535);
        return x;
    }
}