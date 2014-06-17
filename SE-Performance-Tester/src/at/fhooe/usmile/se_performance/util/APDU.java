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
package at.fhooe.usmile.se_performance.util;

public class APDU {

	  public static byte[] getCommandApdu(byte CLA, byte INS, byte P1, byte P2, byte[] data, byte Le){
	       
	        byte[] apduByte = null;
	        if(data.length == 0){
	            apduByte = new byte[5];
	        }else{
	            apduByte = new byte[data.length + 6];
	        }
	        apduByte[0] = CLA;
	        apduByte[1] = INS;
	        apduByte[2] = P1;
	        apduByte[3] = P2;
	        if(data.length > 0){
	             apduByte[4] = (byte) data.length;
	        System.arraycopy(data, 0, apduByte, 5, data.length);
	        apduByte[5 + data.length] = Le;
	        }else{
	            apduByte[4] = Le;
	        }
	       
	        
	        return apduByte;
	    }
}
