package codepath.apps.simpletodo;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.os.Build;

public class EditItemActivity extends Activity {
	String text_to_edit;
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		Log.d("info", "intent string: " + getIntent().getStringExtra("text_to_edit"));
		text_to_edit = getIntent().getStringExtra("text_to_edit");
		Log.d("info", "text to edit: " + text_to_edit);
		position = getIntent().getIntExtra("position", 0);
		EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
		etEditItem.setText(text_to_edit);
		etEditItem.setSelection(etEditItem.length());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

//	public void onSubmit(View v) {
//		// closes the activity and returns to first screen
//		this.finish();
//	}
//	
	public void onSubmit(View v) {
	  EditText etName = (EditText) findViewById(R.id.etEditItem);
	  Intent data = new Intent();
	  data.putExtra("edited_text", etName.getText().toString());
	  data.putExtra("position", position);
	  setResult(RESULT_OK, data);
	  finish();
	} 

}
