package de.merlinbecker.affektbilanz;



import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class Add2_Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_4);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add2_, menu);
		return true;
	}
	
	public void btn_onClick(View v) {
		if (v == (View) findViewById(R.id.imageButton1)) finish();
	}


}
