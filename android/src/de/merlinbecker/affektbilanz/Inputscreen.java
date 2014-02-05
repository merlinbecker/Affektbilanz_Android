package de.merlinbecker.affektbilanz;

import java.nio.charset.Charset;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Inputscreen extends Activity {
	
	Intent intent_main_Act;
	EditText input;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inputscreen);
		
		intent_main_Act = new Intent(this, MainActivity.class);
		
		input = new EditText(this);
		input = (EditText) findViewById(R.id.editText1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inputscreen, menu);
		return true;
	}

	public void btn_onClick(View v) {
		
		if (v == findViewById(R.id.button5)) {
		intent_main_Act.putExtra("transfer", input.getText().toString());
		this.setResult(RESULT_OK, intent_main_Act);
		this.finish();
		}
	}
}
