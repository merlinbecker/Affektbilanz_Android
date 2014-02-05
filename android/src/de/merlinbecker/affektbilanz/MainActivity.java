package de.merlinbecker.affektbilanz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.*;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.FeatureInfo;
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
	public Sheets(Context context,String seriell){
		String[] daten=DataWrapper.getInstance().explode(",", seriell);
		this.tag = daten[0];
		this.plus=Integer.parseInt(daten[1]);
		this.minus=Integer.parseInt(daten[2]);
		this.context = context;
	}
	
	public int describeContents() {
		return 0;
	}
	public View getView(){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.view = (inflater.inflate(R.layout.bar_sheet, null));
			
			TextView txt_view = (TextView) this.view.findViewById(R.id.textView4);
    		txt_view.setText(this.tag);
		return this.view;
	}
	
	public String serialize(){
		String ser=tag+","+plus+","+minus;
		return ser;
	}
	
	
}
public class MainActivity extends Activity {
	//Slidersheet sheet;
	
	//globale Deklaration, da in mehreren Methoden gebraucht
	EditText dialog_input;
	//Variable fuer Textübergabe
	String txt;
	
/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/	
	//public HorizontalScrollView scr_view;
	LinearLayout linear_lt;
	
	//alle Sheets im Scrollview
	public ArrayList<Sheets> sheet_list;
	
	//alle clickbaren Image-Buttons
	//Button plus_btn, menu_btn, book_btn, info_btn;
	
	/**Intents **/
	//Buch-screen
	Intent intent_book_Act;
	//Info-screen
	Intent intent_info_Act;
	//input-screen
	Intent intent_menu_Act;
	/****/
	
	DataWrapper transfer;
	
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
        
        sheet_list = new ArrayList<Sheets>();
        
        //Instanz der Singleton DataWrapper zum globalen verfügbar Machen von list  
        transfer = DataWrapper.getInstance();
       
        
/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
 
        //this.loadData();
    
    }

    
    public void loadData(){
    	//zugriff auf Userdefaults
    	SharedPreferences prefs=this.getPreferences(MODE_PRIVATE);
    	String daten=prefs.getString("data","");
    	String[] alledaten=DataWrapper.getInstance().explode("**//**", daten);
    	for(int i=0;i<alledaten.length;i++){
    		Sheets s=new Sheets(this,alledaten[i]);
    		this.sheet_list.add(s);
    		linear_lt.addView(s.view);
    	}
    }
    public void saveData(){
    	//zugriff auf Userdefaults
    	SharedPreferences prefs=this.getPreferences(MODE_PRIVATE);
   	 	SharedPreferences.Editor editor=prefs.edit();
   	
   	 	ArrayList <String> alleSheets=new ArrayList<String>();
   	 	for(int i=0;i<DataWrapper.getInstance().getData().size();i++){
   	 		Sheets s=DataWrapper.getInstance().getData().get(i);
   	 		alleSheets.add(s.serialize());
   	 	}
   	 	
   	 	editor.putString("data",DataWrapper.getInstance().implode("**//**", (String[])alleSheets.toArray()));
   	 	editor.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    protected void onPause(){
    	//this.saveData();
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
    		transfer.sendData(sheet_list);
    		
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
    		
            
            dialog_input = new EditText(this);
            
    		dialog.setView(dialog_txt);
    		dialog.setView(dialog_input);
    		
    		//Button-Erstellung und Abfrage (Riesen-Methode)
    		dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					txt = dialog_input.getText().toString();
					
					if (txt != null) {
						
		    		//Speichern des Sheet-Layouts/Tags in neuem Sheets
		    		
					Sheets sheet=new Sheets(MainActivity.this,txt,50,50);
		    		sheet_list.add(sheet);
		    		
		    		
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
    	System.out.println("hier");
			
		sheet_list = transfer.getData();
    		
    	for (Sheets i:sheet_list) {
    	System.out.println("hi");
    	linear_lt.addView(i.getView());
    	}
	}
   
}
