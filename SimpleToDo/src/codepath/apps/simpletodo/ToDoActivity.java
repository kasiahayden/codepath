package codepath.apps.simpletodo;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ToDoActivity extends Activity {
	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	private final int REQUEST_CODE = 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do);
		lvItems = (ListView) findViewById(R.id.lvItems);
		populateArrayItems();
		itemsAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		readItems();
		setupListViewListener();
	}
	
	private void populateArrayItems() {
		items = new ArrayList<String>();
		items.add("First Item");
		items.add("Second Item");
		items.add("Third item");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do, menu);
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

	public void addTodoItem(View v) {
		EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
		itemsAdapter.add(etNewItem.getText().toString());
		Toast.makeText(this, etNewItem.getText().toString() + " added",
				Toast.LENGTH_SHORT).show();
		etNewItem.setText("");
		saveItems(); // writes to file
	}

	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item,
					int position, long id) {
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				// saveItems();
				return true;
			}
		});

		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("info", "text to edit: next line:");
				
				TextView txt = (TextView) parent.getChildAt(position);
				String editMe = txt.getText().toString();
				Log.d("info", "text to edit: " + editMe);
				launchEditItemView(editMe, position);
			}
		});
		
		
	}

	public void launchEditItemView(String editMe, int position) {
		Log.d("info", "putExtras: " + editMe + " " + position);
		Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
		i.putExtra("text_to_edit", editMe);
		i.putExtra("position", position);
		startActivityForResult(i, REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Toast.makeText(this,
				"Added item: " + data.getExtras().getString("edited_text"),
				Toast.LENGTH_SHORT).show();
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			String edited_text = data.getExtras().getString("edited_text");
			int position = data.getExtras().getInt("position");
			
			itemsAdapter.insert(edited_text, position);
			//items.set(position, edited_text);
			//items.remove(position+1);
			
//			lvItems.getItemIdAtPosition(position)
//			
//			TextView txt = (TextView) parent.getChildAt(position);
			
			//TextView txt = (TextView) lvItems.getItemAtPosition(position);
			
			//TextView txt = (TextView) lvItems.getChildAt(0);
			//txt.setText(edited_text);
//			Log.d("info", "text of elt at 0th pos: " + txt.
//					toString());
			
//			itemsAdapter.notifyDataSetChanged();
//			saveItems();
		} 
	}

	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e) {
			items = new ArrayList<String>();
			e.printStackTrace();
		}
	}

	private void saveItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			FileUtils.readLines(todoFile); // , items); //error-must be Charset
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
