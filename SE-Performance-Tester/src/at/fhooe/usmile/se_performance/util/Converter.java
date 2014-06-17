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



public  class Converter {
	public static String byteArrayToHexString(byte[] b) {
		   StringBuilder sb = new StringBuilder(b.length * 2);
		   for (int i = 0; i < b.length; i++) {
		     int v = b[i] & 0xff;
		     if (v < 16) {
		       sb.append('0');
		     }
		     
		    
		     sb.append(Integer.toHexString(v));
		     sb.append(' ');
		   }
		   return sb.toString().toUpperCase();
		   
		   

		 }
	
	  
		 
		public static byte[] hexStringToByteArray(String s) {
		       int len = s.length();
		       byte[] data = new byte[len / 2];
		       for (int i = 0; i < len; i += 2) {
		           data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
		                   + Character.digit(s.charAt(i+1), 16));
		       }
		       return data;
		   }
		
		public static byte[] concatArray(byte[] first, byte[]second){
			byte[] result = new byte[first.length + second.length];
			System.arraycopy(first, 0 , result, 0, first.length);
			System.arraycopy(second, 0, result, first.length, second.length);
			return result;
		}

}
