// Die Klasse wird dazu verwendet, um Aufgaben hinzuzufügen, damit die Klasse DBHelper nicht zu sehr
//überlastet wird. Hierzu müssen die Aufgaben bei der Methode "hinzufuegen" eingetragen werden. Die
//Aufgaben werden dann über eine Whileschleife hinzugefügt werden und mit dem loeschen gelöscht.

package com.example.sebastian.trinkio;
import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

public class Aufgaben_Liste {

    public ArrayList<String> Aufgabenliste = new ArrayList<>();

    //  @ zufallszahl Schluck (1 - 5) = wenn @ = 1 wird Schlücke durch Schluck ersetzt
    //  -# Spieler1 Zufall
    //  ~ Spieler2 Zufall
    //  _ Zufall für Männer/Frauen

    public void hinzufuegen() {
        Aufgabenliste.add("- sage bis zum Ende des Spiels 'Over ~ and out'");
        Aufgabenliste.add("- Jeder darf dir eine unangenehme Frage stellen, willst du diese nicht beantworten, trinke @ Schlücke");
        Aufgabenliste.add("- Jeder der ein Samsunghandy hat trinkt @ Schlücke");
    }

    public String gebeaufgabe(Context context) {
        TinyDB tinyDB = new TinyDB(context);
        ArrayList<String> test = new ArrayList<>();
        String aufgabe;
        try {
            //Initalsiere Random Objekt
            Random rdm = new Random();
            int aufgabenid = rdm.nextInt(Aufgabenliste.size()); //Anzahl maximal anzahl Schlücke
            aufgabe = Aufgabenliste.get(aufgabenid);
            return aufgabe;
        }
        catch (Exception e){
            return "error";
        }
    }
}



//        Variablen Prefkeys
//                              TON -> TON spielen                  Boolean
//                              HINTERGRUND -> Hintergrundfarbe     INT
//                              AUFGABEN -> Aufgabenliste           ArrayList
//                              ERSTSTART -> erster Start prüfen    Boolean
//                                           bzw. neuladen aufgaben
//
//
//