package org.monitis.utils;

import java.security.SignatureException;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


/**
 * This class defines common routines for generating authentication signatures
 * for AWS requests.
 */
public class CodingUtility {
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	private static final String mySecret = "&W@!/>:'{M";
	

	/**
	 * Computes RFC 2104-compliant HMAC signature. *
	 * 
	 * @param data
	 *            The data to be signed.
	 * @param key
	 *            The signing key.
	 * @return The Base64-encoded RFC 2104-compliant HMAC signature.
	 * @throws java.security.SignatureException
	 *             when signature generation fails
	 */
	public static String calculateRFC2104HMAC(String data, String secretKey)
			throws java.security.SignatureException {
		String result;
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(),
					HMAC_SHA1_ALGORITHM);
			mac.init(key);
			byte[] authentication = mac.doFinal(data.getBytes());
			result  = new String(new Base64().encodeBase64(authentication), Constants.ENCODING);
		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : "
					+ e.getMessage());
		}
		System.out.println("ch = "+result);
		return result;
	}
	/**
	 * Encode given data using DESECBPKCS5 encoding
	 * @param data
	 * @return encoded bytes
	 * @throws Exception
	 */
	public static byte[] encodeDESECBPKCS5Padding(byte[] data) throws Exception{
		DESKeySpec myDesKey = new DESKeySpec(mySecret.getBytes());
		SecretKey secretKey = SecretKeyFactory.getInstance("DES").generateSecret(myDesKey);
	    Cipher desCipher;

	    // Create the cipher 
	    desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

	    // Initialize the cipher for encryption
	    desCipher.init(Cipher.ENCRYPT_MODE, secretKey);

	    // Encrypt the text
	    byte[] textEncrypted = desCipher.doFinal(data);
	    return textEncrypted;
	}
	/**
	 * Decode given data encoded by DESECBPKCS5
	 * @param data
	 * @return decoded data
	 * @throws Exception
	 */
	public static byte[] decodeDESECBPKCS5Padding(byte[] data) throws Exception{
		DESKeySpec myDesKey = new DESKeySpec(mySecret.getBytes());
		SecretKey secretKey = SecretKeyFactory.getInstance("DES").generateSecret(myDesKey);
	    Cipher desCipher;

	    // Create the cipher 
	    desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

	    // Initialize the cipher for decryption
	    desCipher.init(Cipher.DECRYPT_MODE, secretKey);
	    
	    // Decrypt the text
	    byte[] textDecrypted = desCipher.doFinal(data);
	    return textDecrypted;
	}
	
	
	
	/**
	 * For testing purposes 
	 * @param args
	 */
	public static void main(String[] args) {
		try{
//			FileReader fileReader = new FileReader("C:\\EC2\\gsg-keypair");
//			StringBuffer buffer = new StringBuffer();
//			int c;
//			while((c=fileReader.read()) != -1){
//				buffer.append((char)c);
//			}
//			
//			fileReader.close();
//			String myText = buffer.toString();
//			System.out.println(myText + " " + myText.length());
//			byte[] encode = encodeDESECBPKCS5Padding(myText.getBytes());
//			System.out.println(new String(encode));
//			byte[] decode = decodeDESECBPKCS5Padding(encode);
//			System.out.println(new String(decode));
		   
			String ss = "actionsuspendExternalMonitorapikey16H8S8K39G1SFH3B7G36TU03VImonitorIds3449timestamp2011-03-10 22:37:39version2";
			CodingUtility.calculateRFC2104HMAC(ss, "7NV318N6R4CGP758CR4245HQQM");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}