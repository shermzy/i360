/*
 * Created on Nov 30, 2004
 *
 */
package CP_Classes;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @author Markus Adrian Karjad
 *
 */
public class UnicodeHelper {
	
	public static String getUnicodeStringAmp(String strData) {
		String strResult = "";
		if (strData.indexOf("&#") == -1) {
			strResult = strData;
		} else {
			strResult = "";
			String[] arStr = strData.split("&#");
			for (int i = 0; i < arStr.length; i++) {
				String strItem = arStr[i];
				if (!strItem.equalsIgnoreCase("")) {
					if (strItem.indexOf(";") > 0) {
						if (strItem.indexOf(";") + 1 < strItem.length()) {
							String[] arStr2 = new String[2];
							arStr2[0] = strItem.substring(0, strItem.indexOf(";"));
	                        arStr2[1] = strItem.substring(strItem.indexOf(";") + 1);
	                        if (isNumeric(arStr2[0])) {
	                        	byte[] b = new byte[1];
	                        	b[0] = (byte) (new Integer(arStr2[0]).intValue()-3680);
	                            strResult += getRealChar(b);
	                        } else {
	                            strResult += arStr2[0];
	                        }
	                        strResult += arStr2[1];
						} else {
                        	byte[] b = new byte[1];
                        	b[0] = (byte) (new Integer(strItem.replace(';', ' ').trim()).intValue()-3680);							
	                        strResult += getRealChar(b);
						}
					} else {
	                    strResult += strItem;	
					}					
				} 
			}	
		}
		return strResult;
	}

	public static String getUnicodeString(String strData) {
		String retString = "";
		if (strData.trim().length()==0)
			return strData;
		boolean hasNonNumeric = false;
		String[] strDataItem = strData.split(";");
		
		byte[] bTmp = new byte[strDataItem.length];
		int intLoop = 0;
		for (intLoop = 0; intLoop < strDataItem.length; intLoop++) {
			String tmp = strDataItem[intLoop];
			if (isNumeric(tmp)){
				bTmp[intLoop] = (byte)Integer.parseInt(strDataItem[intLoop]);
			} else {
				hasNonNumeric = true;
				break;
			}
		}
		byte[] b = null;
		if (hasNonNumeric) 
			b = getRealByteArray(bTmp, intLoop);
		else
			b = bTmp;

//		String strEncoding = "TIS-620";
//	    Charset charset = Charset.forName(strEncoding);
//	    CharsetDecoder decoder = charset.newDecoder();
//		try {
//	        ByteBuffer bbuf = ByteBuffer.wrap(b);
//	        CharBuffer cbuf = decoder.decode(bbuf);
//	        retString = cbuf.toString();
//	    } catch (CharacterCodingException e) {
//	    }
	    retString = getRealChar(b);
	    if (hasNonNumeric)
	    	return retString + getPipeData(strDataItem[intLoop]) + getUnicodeString(getNextData(strData, intLoop+1));
	    else
	    	return retString;
	}
	
	private static String getRealChar(byte[] b) {
		String strEncoding = "TIS-620";
		String retString = "";
	    Charset charset = Charset.forName(strEncoding);
	    CharsetDecoder decoder = charset.newDecoder();
		try {
	        ByteBuffer bbuf = ByteBuffer.wrap(b);
	        CharBuffer cbuf = decoder.decode(bbuf);
	        retString = cbuf.toString();
	    } catch (CharacterCodingException e) {
	    }
	    return retString;
	}
	
	private static String getPipeData(String strData) {
		String strResult = strData;
		if (strResult.charAt(0)=='|') {
			if (strResult.length()>1) {
				strResult = strResult.substring(1, (strResult.charAt(strResult.length()-1)=='|'?strResult.length()-1:strResult.length()));
			} else {
				strResult = "";
			}
		}
		return strResult;
	}
	
	private static byte[] getRealByteArray(byte[] b, int intPos) {
		byte[] retByte = new byte[intPos];
		for (int i = 0; i < retByte.length; i++) {
			retByte[i] = b[i];			
		}
		return retByte;
	}
	
	public static String getNextData(String strData, int intPos) {
		String strResult = "";
		String[] strDataItem = strData.split(";");
		if (intPos < strDataItem.length) {
			for (int i = intPos; i < strDataItem.length; i++) {
				strResult = strResult + strDataItem[i] + ";";				
			}
		} 
		return strResult;
	}
	
	private static boolean isNumeric(String tmp){
		int i = 0;
		try {
			i = Integer.parseInt(tmp);			
		} catch (Exception e) {
			return false;
		}		
		return true;
	}
}
