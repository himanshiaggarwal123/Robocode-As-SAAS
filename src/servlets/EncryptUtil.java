package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class EncryptUtil{
	private static final String ALGORITHM = "RSA";
	//private static final String LOGIN_PRIVATE_KEY_FILE =Paths.get(EncryptUtil.class.getResource(".").toURI()).getParent().getParent().getParent().toString() +File.separator +"Decryption/loginKeys/private.key";

	private static final String LOGIN_PRIVATE_KEY_FILE ="/Decryption/loginKeys/private.key";
	private static final String LOGIN_PUBLIC_KEY_FILE = "/Decryption/loginKeys/public.key";
	private static final String ROBO_PRIVATE_KEY_FILE = "/Decryption/roboKeys/private.key";
	private static final String ROBO_PUBLIC_KEY_FILE = "/Decryption/roboKeys/public.key";
	private static PublicKey loginPublicKey;
	private static PrivateKey loginPrivateKey;
	private static PublicKey roboPublicKey;
	private static PrivateKey roboPrivateKey;
	
	static{
			try {
				readInLoginPublicKey();
			
	    	readInLoginPrivateKey();
	    	System.out.println("Finished reading Login key file");
		
			readInRoboPublicKey();
	    	readInRoboPrivateKey();
	    	System.out.println("Finished reading Robo key file");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private static void readInLoginPublicKey() throws Exception {
		ObjectInputStream inputStream = null;
		try {
	        Path path = Paths.get(EncryptUtil.class.getResource(".").toURI());
	        Path projPath = path.getParent().getParent().getParent(); 
			System.out.println(projPath.toString()+ LOGIN_PUBLIC_KEY_FILE);
			inputStream = new ObjectInputStream(new FileInputStream(new File(projPath.toString()+ LOGIN_PUBLIC_KEY_FILE)));
			//inputStream = new ObjectInputStream(new FileInputStream(LOGIN_PUBLIC_KEY_FILE));
			loginPublicKey = (PublicKey) inputStream.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void readInLoginPrivateKey() throws Exception {
		ObjectInputStream inputStream = null;
		try {
			Path path = Paths.get(EncryptUtil.class.getResource(".").toURI());
	        Path projPath = path.getParent().getParent().getParent(); 
			inputStream = new ObjectInputStream(new FileInputStream(new File(projPath.toString()+ LOGIN_PRIVATE_KEY_FILE)));
			loginPrivateKey = (PrivateKey) inputStream.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private static void readInRoboPublicKey() throws Exception{
		ObjectInputStream inputStream = null;
		try {
			Path path = Paths.get(EncryptUtil.class.getResource(".").toURI());
	        Path projPath = path.getParent().getParent().getParent();
			inputStream = new ObjectInputStream(new FileInputStream(new File(projPath.toString()+ ROBO_PUBLIC_KEY_FILE)));
			roboPublicKey = (PublicKey) inputStream.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private static void readInRoboPrivateKey() throws Exception {
		ObjectInputStream inputStream = null;
		try {
			Path path = Paths.get(EncryptUtil.class.getResource(".").toURI());
			Path projPath = path.getParent().getParent().getParent();
			inputStream = new ObjectInputStream(new FileInputStream(new File(projPath.toString()+ ROBO_PRIVATE_KEY_FILE)));
			roboPrivateKey = (PrivateKey) inputStream.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/*public static boolean areLoginKeysPresent(){
	    File privateKey = new File(LOGIN_PRIVATE_KEY_FILE);
	    File publicKey = new File(LOGIN_PUBLIC_KEY_FILE);

	    if (privateKey.exists() && publicKey.exists()) {
	      return true;
	    }
	    return false;
	}
	public static boolean areRoboKeysPresent(){
	    File privateKey = new File(ROBO_PRIVATE_KEY_FILE);
	    File publicKey = new File(ROBO_PUBLIC_KEY_FILE);

	    if (privateKey.exists() && publicKey.exists()) {
	      return true;
	    }
	    return false;
	}*/
	public static byte[] encryptWithPublicKey(String text, PublicKey key) {
	    byte[] cipherText = null;
	    try {
	      // get an RSA cipher object and print the provider
	      final Cipher cipher = Cipher.getInstance(ALGORITHM);
	      // encrypt the plain text using the public key
	      cipher.init(Cipher.ENCRYPT_MODE, key);
	      cipherText = cipher.doFinal(text.getBytes());
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return cipherText;
	 }
	public static String decryptWithPrivateKey(byte[] text, PrivateKey key) {
	    byte[] dectyptedText = null;
	    try {
	      // get an RSA cipher object and print the provider
	      final Cipher cipher = Cipher.getInstance(ALGORITHM);

	      // decrypt the text using the private key
	      cipher.init(Cipher.DECRYPT_MODE, key);
	      dectyptedText = cipher.doFinal(text);

	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }

	    return new String(dectyptedText);
	  }

	public static PublicKey getLoginPublicKey() {
		return loginPublicKey;
	}

	public static PrivateKey getLoginPrivateKey() {
		return loginPrivateKey;
	}

	public static PublicKey getRoboPublicKey() {
		return roboPublicKey;
	}

	
	public static PrivateKey getRoboPrivateKey() {
		return roboPrivateKey;
	}
}
