package at.fhooe.usmile.se_performance;



 import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @author Local2013
 *
 */
public class OperationsActivity extends Activity implements OnItemClickListener {

	private  ListAdapter operationsListAdapter;
	private  ListView listviewTestOperations;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test__operations);
		
		listviewTestOperations = (ListView) findViewById(R.id.listviewOperations);
		operationsListAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_text, getResources().getStringArray(R.array.operation));
		listviewTestOperations.setAdapter(operationsListAdapter);
		
		
		listviewTestOperations.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test__operations, menu);
		return true;
	}

 

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(), listviewTestOperations.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show(); 
		Intent intent = new Intent(this,TestPerformerActivity.class);
		intent.putExtra("operation", position);
		 
		startActivity(intent);
	}

}
