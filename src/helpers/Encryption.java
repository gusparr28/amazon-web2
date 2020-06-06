package helpers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
	
	public String getSHA256(String password, String salt) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(salt.getBytes(StandardCharsets.UTF_8));
		    byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
		    StringBuilder sb = new StringBuilder();
		    for (int i=0; i<bytes.length; i++) {
		    	sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		    }
		    generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}
}

