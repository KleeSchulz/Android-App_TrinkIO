package com.example.sebastian.trinkio;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;


public class Aufgaben extends AppCompatActivity {

    ListView lv_aufgabenanzeigen;
    ImageButton btn_add;
    ConstraintLayout rtv;;
    int loeschenpos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgaben);
//====================================Lade Farben===================================================
        rtv = (ConstraintLayout)findViewById(R.id.layout);
        rtv.setBackgroundColor(Allgemein.gebePrefkeyInt(Aufgaben.this,"HINTERGRUND",999999));
//===============================Lade anzeigen aufgaben=============================================
        lv_aufgabenanzeigen = (ListView) findViewById(R.id.lv_aufabe);
        final Aufgaben_Liste aufgaben_liste = new Aufgaben_Liste();
        aufgaben_liste.hinzufuegen();
        TinyDB tinyDB = new TinyDB(this);
        ArrayList<String> aufgaben = new ArrayList<String>();
        aufgaben = tinyDB.getListString("AUFGABEN");

        ArrayAdapter<String> anzeigeAufgabe = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,aufgaben);
        lv_aufgabenanzeigen.setAdapter(anzeigeAufgabe);
//======================Aufgabe löschen=============================================================
        registerForContextMenu(lv_aufgabenanzeigen);
        lv_aufgabenanzeigen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                loeschenpos = position;
                view.setSelected(true);
            }
        });
//===========================Anzeigen Dialog -> Hinzufügen von Daten================================
        btn_add = (ImageButton) findViewById(R.id.btn_hinzufuegen);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final Context c = (Context) Aufgaben.this; //Context Angeben  -> jeweilge Activity!
                    final EditText taskEditText = new EditText( c);
                    AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setTitle("Aufgabe hinzufügen");
                    builder.setMessage("Wie lautet die Aufgabe?");
                    builder.setView(taskEditText);
                    builder.setPositiveButton("Hinzufügem", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String aufgabe = String.valueOf(taskEditText.getText());
                            aufgaben_liste.Aufgabenliste.add(aufgabe);
                            TinyDB tinyDB = new TinyDB(Aufgaben.this);
                            ArrayList<String> uebergabe = new ArrayList<>();
                            uebergabe = tinyDB.getListString("AUFGABEN");
                            uebergabe.add(aufgabe);
                            tinyDB.putListString("AUFGABEN",uebergabe);
                            Allgemein.toast(Aufgaben.this,"Aufgabe wurde hinzugefügt");
                            onRestart();
                        }
                    });
                    builder.setNegativeButton("Abbrechen", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    @Override
//=====================================================Aktualsieren der View========================
    protected void onRestart(){
        super.onRestart();
        TinyDB tinyDB = new TinyDB(Aufgaben.this);
        ArrayList<String> aufgaben = tinyDB.getListString("AUFGABEN");
        ArrayAdapter<String> anzeigeAufgabe = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,aufgaben);
        lv_aufgabenanzeigen.setAdapter(anzeigeAufgabe);
    }
//==================================================Löschen von Aufgaben über Contextmenü===========
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menue_listview_aufgaben_loeschen,menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        TinyDB tinyDB = new TinyDB(Aufgaben.this);
        ArrayList<String> aufgaben = tinyDB.getListString("AUFGABEN");
        aufgaben.remove(loeschenpos);
        tinyDB.putListString("AUFGABEN",aufgaben);
        onRestart();
        Allgemein.toast(this,"Aufgabe wurde gelöscht");
        return true;
    }
}
