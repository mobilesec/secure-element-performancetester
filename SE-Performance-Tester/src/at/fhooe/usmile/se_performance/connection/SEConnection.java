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
package at.fhooe.usmile.se_performance.connection;



import java.io.IOException;

import org.simalliance.openmobileapi.Channel;
import org.simalliance.openmobileapi.Reader;
import org.simalliance.openmobileapi.SEService;
import org.simalliance.openmobileapi.Session;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import at.fhooe.usmile.se_performance.SEReadersActivity;

public class SEConnection implements SEService.CallBack {

	static public SEService seService;
	private Reader reader = null;
	private Context context;
	private Reader[] readers;
	Channel channel ;
 
	
	long elapsedTime = 0L;
	
	SEReadersActivity readerUi;
	
	
	public SEConnection(SEReadersActivity _readerUI ){		
		try{
			readerUi = _readerUI; 
			seService = new SEService(_readerUI.getApplicationContext(), this);
			
		  } catch (SecurityException e) {
			    Log.e("Security Exception", "Binding not allowed, uses-permission org.simalliance.openmobileapi.SMARTCARD?");
		} catch (Exception e) {
			    Log.e("Exception: " ,e.getMessage());
		}
	}
	
	public void closeConnection(){
		 if (seService != null && seService.isConnected()) {
		      seService.shutdown();
		      Log.i("Connection ", "SE Connection Closed");		 
		      }
	}
	@Override
	public void serviceConnected(SEService arg0) {
		// TODO Auto-generated method stub
		readers = seService.getReaders();
		String[] readerList = new String[readers.length];
		int i = 0;
		for(Reader r : readers){
			Log.i("reader " , r.getName());
			readerList[i] = r.getName();
			i += 1;
			
		}
		readerUi.setReaders(readerList);
		
	
	}
	public boolean selectApplet(byte[] _aid, int _readerIndex){
		
		if (readers.length > 0 ){
			reader = readers[_readerIndex];
		      Log.i("Info ", "Create Session from the first reader...");
		   
		      Session session;
			try {
				session = reader.openSession();
				Log.i("Info", "Create logical channel within the session...");				  
			    channel = session.openLogicalChannel(_aid); 
			    return true;
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				Toast.makeText(readerUi.getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
			}
	    
		}
		return false;
	}
	
	public byte[] sendCommand(byte[] cmdApdu){
		if(!channel.isClosed()){
	    	byte[] respApdu = new byte[0];
			
	    	try {
				
				long commandTime = 0L;
				long responseTime = 0L;
				commandTime = System.nanoTime();
				respApdu = channel.transmit(cmdApdu);
				responseTime = System.nanoTime();
				elapsedTime = responseTime - commandTime;
				
			//	Log.i("Response ", Converter.byteArrayToHexString(respApdu) + " in " + elapsedTime/1000 + " micro second");
				 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Toast.makeText(readerUi.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
			}
	    	return respApdu;
		}
		return null;
	}
	
	public long getElapsedTime(){
		return elapsedTime;
	}

}
