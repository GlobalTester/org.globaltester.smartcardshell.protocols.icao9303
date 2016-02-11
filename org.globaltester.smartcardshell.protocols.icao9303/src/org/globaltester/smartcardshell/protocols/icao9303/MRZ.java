package org.globaltester.smartcardshell.protocols.icao9303;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class MRZ {

	private String mrz;
	
	
	public MRZ(String mrz){
		this.mrz = mrz;
	}
	
	public String getDocumentNumber(){
		return mrz.substring(44, 53);
	}

	public String getDateOfBirth(){
		return mrz.substring(57, 63);
	}

	public String getDateOfExpiration(){
		return mrz.substring(65, 71);
	}

	public static String computeCheckDigit(String str) {
		byte[] pbuffer = str.getBytes();
		int[] weight = { 7, 3, 1 };
		int csum = 0;
		byte ctemp = (byte) 0x00;

		for (int i = 0; i < pbuffer.length; i++) {
			ctemp = pbuffer[i];
			/*
			 * '<' will be neglected '0' has value 0 (dec) ... '9' has value 9
			 * (dec) 'A' has value 10 (dec) ... 'Z' has value 35 (dec) the rest
			 * is 0 (dec)
			 */
			if ((ctemp >= 'A') && (ctemp <= 'Z')) {
				ctemp -= (byte) 0x37;
			} else if ((ctemp >= '0') && (ctemp <= '9')) {
				ctemp -= (byte) 0x30;
			} else
				ctemp = 0;
			csum += ctemp * weight[(i) % 3];
		}

		ctemp = (byte) (csum % 10);
		ctemp += (byte) 0x30;
		
		return String.valueOf((char)ctemp);
	}

	
	public String getMRZInformation(){
		String mrzInfo = "";
		mrzInfo = mrzInfo.concat(getDocumentNumber());
		mrzInfo = mrzInfo.concat(computeCheckDigit(getDocumentNumber()));
		mrzInfo = mrzInfo.concat(getDateOfBirth());
		mrzInfo = mrzInfo.concat(computeCheckDigit(getDateOfBirth()));
		mrzInfo = mrzInfo.concat(getDateOfExpiration());
		mrzInfo = mrzInfo.concat(computeCheckDigit(getDateOfExpiration()));
		
		return mrzInfo;
	}
	
	
	public byte[] computeHash(){
		MessageDigest shaDigest = null;
		try {
			shaDigest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException ex) {
			System.err.println(ex);
		}
		try {
			shaDigest.update(getDocumentNumber().getBytes("UTF-8"));
			shaDigest.update(computeCheckDigit(getDocumentNumber()).getBytes("UTF-8"));
			shaDigest.update(getDateOfBirth().getBytes("UTF-8"));
			shaDigest.update(computeCheckDigit(getDateOfBirth()).getBytes("UTF-8"));
			shaDigest.update(getDateOfExpiration().getBytes("UTF-8"));
			shaDigest.update(computeCheckDigit(getDateOfExpiration()).getBytes("UTF-8"));
		} catch(UnsupportedEncodingException ex) {
			System.err.println(ex);
		}
		
		byte[] hash = shaDigest.digest();
		return hash;
	}

	public byte[] computeKeySeed(){
		byte[] keySeed = new byte[16];
		System.arraycopy(computeHash(), 0, keySeed, 0, 16);
		return keySeed;
	}
	
	public byte[] getEncoded(){
		return mrz.getBytes();
	}
	
	public String toString(){
		return mrz;
	}

	
}
