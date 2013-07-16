package at.fhooe.usmile.se_performance;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import at.fhooe.usmile.se_performance.connection.SEConnection;

/**
 * @author Local2013
 *
 */
public class SEReadersActivity extends Activity {

	private static ListAdapter readerListAdapter;
	private static ListView listviewReaders;
	static Context context;
 	static SEConnection seConn;
	static int readerIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_readers);
		
		
		listviewReaders = (ListView)findViewById(R.id.listviewReaders);
		context = getApplicationContext();
		seConn = new SEConnection(this);
 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.readers, menu);
		return true;
	}
	
	public void setReaders(String[] readers){
		
		readerListAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_text, readers);		
		listviewReaders.setAdapter(readerListAdapter);		
		listviewReaders.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			 public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {
				Intent opListIntent = new Intent(getApplicationContext(), OperationsActivity.class);
				readerIndex = position;
 				startActivity(opListIntent);			
		     }  
		});
	}

}
