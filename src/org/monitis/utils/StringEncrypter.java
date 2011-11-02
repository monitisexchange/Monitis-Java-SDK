package org.monitis.utils;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;



/**
 * 
 * @source: http://www.devx.com/Java/10MinuteSolution/21385/1763/page/2
 * @author: ngaspary
 * 
 **/

public class StringEncrypter
{    

     private KeySpec keySpec;
     private SecretKeyFactory keyFactory;
     private Cipher cipher;
     
     private static final String UNICODE_FORMAT = "UTF8";

     public StringEncrypter( String encryptionKey )
               throws EncryptionException
     {

          if ( encryptionKey == null )
                    throw new IllegalArgumentException( "encryption key was null" );
          if ( encryptionKey.trim().length() < 24 )
                    throw new IllegalArgumentException(
                              "encryption key was less than 24 characters" );

          try
          {
               byte[] keyAsBytes = encryptionKey.getBytes( UNICODE_FORMAT );
               
               keySpec = new DESedeKeySpec( keyAsBytes );
               
               keyFactory = SecretKeyFactory.getInstance( "DESede" );
               cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");

          }
          catch (Exception e)
          {
               throw new EncryptionException( e );
          }
     }

     public String encrypt( String unencryptedString ) throws EncryptionException
     {
          if ( unencryptedString == null || unencryptedString.trim().length() == 0 )
                    throw new IllegalArgumentException(
                              "unencrypted string was null or empty" );

          try
          {
               SecretKey key = keyFactory.generateSecret( keySpec );
               cipher.init( Cipher.ENCRYPT_MODE, key );
               byte[] cleartext = unencryptedString.getBytes( UNICODE_FORMAT );
               byte[] ciphertext = cipher.doFinal( cleartext );
               return StringUtils.getHexString( ciphertext );

          }
          catch (Exception e)
          {
               throw new EncryptionException( e );
          }
     }

     public String decrypt( String encryptedString ) throws EncryptionException
     {
          if ( encryptedString == null || encryptedString.trim().length() <= 0 )
                    throw new IllegalArgumentException( "encrypted string was null or empty" );

          try
          {
               SecretKey key = keyFactory.generateSecret( keySpec );
               cipher.init( Cipher.DECRYPT_MODE, key );
               byte[] cleartext = StringUtils.hexStringToByteArray( encryptedString );
               byte[] ciphertext = cipher.doFinal( cleartext );

               return bytes2String( ciphertext );
          }
          catch (Exception e)
          {
               throw new EncryptionException( e );
          }
     }

     private static String bytes2String( byte[] bytes )
     {
          StringBuffer stringBuffer = new StringBuffer();
          for (int i = 0; i < bytes.length; i++)
          {
               stringBuffer.append( (char) bytes[i] );
          }
          return stringBuffer.toString();
     }

     public static class EncryptionException extends Exception
     {
          public EncryptionException( Throwable t )
          {
               super( t );
          }
     }
     
     
     public static void main(String[] args) {
 		try {
 			StringEncrypter stringEncrypter = new StringEncrypter("secretkeyparamValueStrsecret");
  			System.out.println(stringEncrypter.encrypt("paramValueStr"));
  			//System.out.println(stringEncrypter.decrypt("a7725f4b492a54bd"));
 			//System.out.println(new BASE64Encoder().encode(new String("abcd").getBytes()));
 			//System.out.println(CodingUtility.calculateRFC2104HMAC("abcd", "keykeykeykeykeykeykeykey"));
 			
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		
 	}
     
}