/*******************************************************************************
 * Copyright (c) 2014 michaelhoelzl.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Michael Hölzl <mihoelzl@gmail.com> - initial API and implementation
 *     Endalkachew Asnake <endal.asnake@gmail.com> - initial API and implementation
 ******************************************************************************/
package at.fhooe.usmile.se_performance;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import at.fhooe.usmile.se_performance.R;

import at.fhooe.usmile.se_performance.tests.CryptoTest;

public class TestPerformerActivity extends Activity implements OnClickListener {

	Spinner spinnerDatalen;
	Spinner spinnerIteration;
	Button buttonTest;
	Handler mHandler = new Handler();
	List<String> message = new ArrayList<String>(0);
	Runnable mUpdateResults;
	
	int operation;
	 
	
	CryptoTest cryptoTest ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tester);

		Intent intent = getIntent();
		operation = intent.getIntExtra("operation", 0);
		 
		
		spinnerDatalen = (Spinner) findViewById(R.id.spinnerDataLen);
		spinnerIteration = (Spinner) findViewById(R.id.spinnerIteration);
		buttonTest = (Button) findViewById(R.id.btn_test);
		buttonTest.setOnClickListener(this);
		cryptoTest = new CryptoTest(this, SEReadersActivity.seConn, SEReadersActivity.readerIndex);

		if(operation == 4){
			spinnerDatalen.setEnabled(false);
		} 
		if(operation == 6){
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
			        R.array.datalengthRSA1024, android.R.layout.simple_spinner_item);
			// Specify the layout to use when the list of choices appears
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// Apply the adapter to the spinner
			spinnerDatalen.setAdapter(adapter);
		}
		final TextView logTextView = (TextView) findViewById(R.id.logtextView);
		final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollViewSDCard);
		mUpdateResults = new Runnable() {

			@Override
			public void run() {
				String msg = "";
				synchronized (this) {
					if (message.isEmpty() == false) {
						msg = message.remove(0);
					}
				}
				if (msg != null) {
					if (msg.equals("end")) {
						msg = "\n\n";
						buttonTest.post(new Runnable() {
							public void run() {
								synchronized (this) {
									buttonTest.setText("Start Test");
								}
							}
						});

					}
					logTextView.append(msg);
					Log.v(getResources().getString(R.string.app_name), msg);
					scrollView.post(new Runnable() {
						public void run() {
							synchronized (this) {
								scrollView.fullScroll(ScrollView.FOCUS_DOWN);
							}
						}
					});
				}

				
			}

		};

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tester, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		int datalen = Integer.valueOf(spinnerDatalen.getSelectedItem().toString());
		int iteration = Integer.valueOf(spinnerIteration.getSelectedItem().toString());
		if (v.getId() == R.id.btn_test) {
			switch (operation) {
			case 0:
				if(cryptoTest.aesTestRunning()){
					buttonTest.setText("Start Test");
					cryptoTest.stopAESTest();
				}else{
					
					if(cryptoTest.performAESTest(datalen, iteration, 128)){
						buttonTest.setText("Stop Test");
					}
				}
				break;
			case 1:
				if(cryptoTest.aesTestRunning()){
					buttonTest.setText("Start Test");
					cryptoTest.stopAESTest();
				}else{
					
					if(cryptoTest.performAESTest(datalen, iteration, 256)){
						buttonTest.setText("Stop Test");
					}
				}
				break;
			case 2:
				if(cryptoTest.SHAtestRunning()){
					buttonTest.setText("Start Test");
					cryptoTest.stopSHATest();
				}else{
					if( cryptoTest.performSHAtest(datalen, iteration)){
						buttonTest.setText("Stop Test");
					}
				}
				break;
			
			case 3:
				if(cryptoTest.trippleDESTestRunning()){
					buttonTest.setText("Start Test");
					cryptoTest.stop3DESTest();
				}else{
					buttonTest.setText("Stop Test");
					cryptoTest.perform3DEStest(datalen, iteration);
				}
				break;
			case 4:
				if(cryptoTest.ecdhTestRunning()){
					buttonTest.setText("Start Test");
					cryptoTest.stopECDHTest();
				}else{
					
					if(cryptoTest.performECDHTest(iteration)){
						buttonTest.setText("Stop Test");
					}
				}
				break;
			case 5:
				if(cryptoTest.roundtripTestRunning()){
					buttonTest.setText("Start Test");
					cryptoTest.stopRoundTripTest();
				}else{
					if(cryptoTest.performRoundTripTest(datalen, iteration)){
						buttonTest.setText("Stop Test");

					}
				}
				break;
				
			case 6:
				// RSA test
				if(cryptoTest.RSA1024TestRunning()){
					buttonTest.setText("Start Test");
					cryptoTest.stopRSA1024Test();
				}else{
					buttonTest.setText("Stop Test");
					cryptoTest.performRSA1024test(datalen, iteration);
				}
				//Toast.makeText(getApplicationContext(), "Not implemented yet", Toast.LENGTH_LONG).show();

				break;


			default:
				break;
			}
		}

	}

	public void logText(String msg) {
		message.add(msg);
		mHandler.post(mUpdateResults);
	}

}
