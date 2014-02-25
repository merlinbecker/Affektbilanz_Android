package de.merlinbecker.affektbilanz;

import java.util.ArrayList;
import java.util.Iterator;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

//zZt nutzlos
/*class Buttons {
	
	int id;
	String text;
	
	public Buttons(int id, String text) {
		this.id = id;
		this.text = text;
	}
	
} */

public class Menu_Screen extends Activity {
	
	//List-Item
	LayoutInflater inflater;
	
	ListView listview;
	ArrayAdapter<View> listview_Adapter;
	View listview_view;
	TextView listview_txtview;
	int listview_numberofitems;
	
	//globale Deklarationen fuer ItemClick-Methode
	View listrow;
	Button listview_btndelete;
	TextView listrow_txtview;
	
	ArrayList<Sheets> test;
	DataWrapper liste_container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu_screen);
		
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		listview = new ListView(this);
		listview = (ListView) findViewById(R.id.listView1);
		
		listview_Adapter = new ArrayAdapter<View>(this, R.id.LinearLayout1);
		listview_view = new View(this);
		listview_txtview = new TextView(this);
		
		liste_container = DataWrapper.getInstance();
		
		liste_container.list = liste_container.getList();
		
		
		//Listenerzeugung: jedes Sheets ein Item
		/************************************/
		for (Sheets i:liste_container.list) {
			
			listview_view = inflater.inflate(R.layout.list_item, null);
			//listview_view.setFocusable(false);
			listview_txtview = (TextView) listview_view.findViewById(R.id.textView1);
			listview_txtview.setText(i.tag);
			
			listview.addFooterView(listview_view);
			}
		/************************************/
		
		
		listview.setAdapter(listview_Adapter);
		
		OnItemClickListener listview_listener;
		listrow = new View(this);
		listrow_txtview = new TextView(this);
		
		listview.setOnItemClickListener(listview_listener = new OnItemClickListener() {

			// v = View, das geklickt wurde
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long arg3) {
				//listview_btndelete = (Button) listview_view.findViewById(R.id.button5);
				//if (listview.getItemAtPosition(position) == listview_btndelete) {
				
					//onItemClick erhält geklicktes View als Parameter View  v
					listrow_txtview = (TextView) v.findViewById(R.id.textView1);
				
					Iterator<Sheets> iterator = liste_container.list.iterator();	
					while (iterator.hasNext()) {
						Sheets i = iterator.next();
						if (i.tag == (listrow_txtview.getText().toString())) iterator.remove();
					}
					finish();
				//}
				finish();
			}
			
		});
		//transfer.sendData(test);
		//setResult(RESULT_CANCELED);
		//finish();
	}
	
	public void btn_onClick(View v) {
		/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		//Abfrage Delete-Button
		/*if (v == listview_view.findViewById(R.id.button5)) {
			
		}*/
		/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
		
		//Abfrage Delete-All-Button
		/*****************************/
		if (v == findViewById(R.id.button1)) {
			liste_container.list.clear();
			
		}
		/*****************************/
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu__screen, menu);
		return true;
	}

	
}
