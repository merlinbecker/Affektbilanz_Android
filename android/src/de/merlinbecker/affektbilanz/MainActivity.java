package de.merlinbecker.affektbilanz;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.*;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.FeatureInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;

class Sheets {
	
	View view;
	String tag;
	int plus;
	int minus;
	Context context;
	
	public Sheets(Context context,String tag,int plus,int minus) {
		this.tag = tag;
		this.plus=plus;
		this.minus=minus;
		this.context = context;
	}
	/*public Sheets(Context context,String seriell){
		String[] daten=DataWrapper.getInstance().explode(",", seriell);
		this.tag = daten[0];
		this.plus=Integer.parseInt(daten[1]);
		this.minus=Integer.parseInt(daten[2]);
		this.context = context;
	}*/

	//Sheet-Layout ausgelagert weil von mehrern Methoden gebraucht
	public View getView(){
		
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.view = (inflater.inflate(R.layout.bar_sheet, null));
			
			TextView txt_view = (TextView) this.view.findViewById(R.id.textView4);
    		txt_view.setText(this.tag);
    		
		return this.view;
	}
	
} //Klasse Ende





public class MainActivity extends Activity {
		
	//public HorizontalScrollView scr_view;
	LinearLayout linear_lt;
	
	//alle Sheets im Scrollview, gespeichert in Singleton für globalen Zugriff
	DataWrapper liste_container;
	
	/**Intents **/
	//Buch-screen
	Intent intent_book_Act;
	//Info-screen
	Intent intent_info_Act;
	//input-screen
	Intent intent_menu_Act;
	/****/
	
	//globale Deklaration, da in mehreren Methoden gebraucht
	EditText dialog_input;
	
    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/       
        
//Initialisierungen
/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
        
    
        
        intent_book_Act = new Intent(this, Add1_Activity.class);
        intent_info_Act = new Intent(this, Add2_Activity.class);
        intent_menu_Act = new Intent(this, Menu_Screen.class);
       
        linear_lt = new LinearLayout(this);
        linear_lt = (LinearLayout) this.findViewById(R.id.LinearLayoutScrollView);
        
        //globale Instanz des Singleton DataWrapper zum Speichern von liste
        liste_container = DataWrapper.getInstance();
        
        //Erstellen der Objekt-Liste
        liste_container.list = liste_container.getList();
       
        dialog_input = new EditText(this);
        
/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
        
        //Laden der Sheets-Objekte
        load();
    
        //Erstellen des Layouts auf Basis der geladenen Objekte
        for (Sheets i:liste_container.list) linear_lt.addView(i.getView());
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    protected void onPause(){
    	save();
    	super.onPause();
    }
    
    public void btn_OnClick(View v) {
    	//Buch-Symbol
    	if (v.getId() == R.id.book_btn_id) {
    		startActivity(intent_book_Act);
    	}
    	
    	//Info-Symbol
    	if (v.getId() == R.id.info_btn_id) {
    		startActivity(intent_info_Act);
    	}
    	
    	
    	/********************/
    	//Menu-Button
    	if (v.getId() == R.id.menu_btn_id) {
    		
    		//erst alle Views löschen, dann neu erstellen
    		linear_lt.removeAllViews();
    		
    		startActivityForResult(intent_menu_Act, 0);
    		
    		//-->siehe onActResult()
    		
    	}
    	/********************/
    	
    	//Plus-Button
    	if (v.getId() == R.id.plus_btn_id) {
    		
    		//Dialog
    		/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    		
    		AlertDialog.Builder dialog;
    		dialog = new AlertDialog.Builder(this);
    		
    		TextView dialog_txt = new TextView(this);
    		dialog_txt.setText("Name");
    		
    		dialog.setView(dialog_txt);
    		dialog.setView(dialog_input);
    		
    		//Button-Erstellung und Abfrage, Körper der onClick-Methode in OnClickListener-Erstellung
    		dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {
				
					if (dialog_input.getText().toString() != null) {
					
		    		//Speichern des Sheet-Layouts/Tags in neuem Sheets
					Sheets sheet = new Sheets(MainActivity.this, dialog_input.getText().toString(), 50, 50);
		    		liste_container.list.add(sheet);
		    		
		    		//Sheet-Layout von Sheets erfragen
		    		linear_lt.addView(sheet.getView());
					}
		    		
				}
			});
    		
    		dialog.show();
    		/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    		
    	}
    }
    
    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {	
    	Log.i("ActivityResult", "klappt");
			
    	//Update der Liste
		liste_container.list = liste_container.getList();
    		
    	for (Sheets i:liste_container.list) linear_lt.addView(i.getView());
    	
	}
   
    public void save() {
    	Properties object_file;
		FileOutputStream object_file_output;
		
		//Länge der Datei in Objekt-Schreibzyklen
		int mainindex = 0;
		
		liste_container.list = liste_container.getList();
		
		try {
			
			//Erstellt und öffnet Output-Stream, Ziel-Datei wird automatisch erzeugt
			object_file_output = openFileOutput("config", MODE_APPEND);
			
			object_file = new Properties();
			
			for (Sheets i:liste_container.list) {
				
				object_file.setProperty("tag" + String.valueOf(mainindex), i.tag);
				mainindex++;
			
			}
			
			
			//Speichern der Objekt-Anzahl für Laden
			object_file.setProperty("mainindex", String.valueOf(mainindex));
			
			object_file.store(object_file_output, null);
			Log.i("speichern", "klappt");
			
			object_file_output.close();
			
		}
		catch(IOException e) {
			Log.i("speichern", "klappt nicht");
		}
		
    } //Save Ende
    
    public void load() {
    	Properties object_file;
		FileInputStream object_file_input;
		Sheets sheet;
		
		//Länge der Datei in Objekt-Schreibzyklen
		int  mainindex = 0;
				
		//Zählvariable für Objekterstellung
		int index = 0;
		
		//if (liste_container.einn_list != null) return;
		
		try {
			
			object_file_input = openFileInput("config");
			
			object_file = new Properties();
			
			//Laden der Objekt-Anzahl für Ladeschleife
			object_file.load(object_file_input);
			mainindex = Integer.valueOf(object_file.getProperty("mainindex"));
			
			while (index < mainindex) {
				
				object_file.load(object_file_input);
				sheet = new Sheets(this, object_file.getProperty("tag" + String.valueOf(index)), 0, 0);
				liste_container.list.add(sheet);
				
				index++;
				Log.i("schleife", "S1");
			}
			
			index = 0;
			
			Log.i("laden", "klappt");
			//refreshListView();
			
			object_file_input.close();
		}
		catch(IOException e) {
			Log.i("laden", "klappt nicht");
		}
		
    } //Load Ende
   	
} //Klasse Ende
