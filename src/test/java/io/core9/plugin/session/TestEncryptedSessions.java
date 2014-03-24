package io.core9.plugin.session;

import org.junit.Test;
import static org.junit.Assert.*;

import io.core9.plugin.session.encryption.AES;

public class TestEncryptedSessions {

	static String plaintext = "test text 123\0\0\0"; /* Note null padding */
	static String encryptionKey = "0123456789abcdef";

	@Test
	public void testEncryptions() {

		try {

			System.out.println("==Java==");
			System.out.println("plain:   " + plaintext);
			
			assertTrue("test text 123\0\0\0".equals(plaintext));
			


			byte[] cipher = AES.encrypt(plaintext, encryptionKey);

			System.out.print("cipher:  ");
			for (int i = 0; i < cipher.length; i++) {
				System.out.print(new Integer(cipher[i]) + " ");
			}
			System.out.println("");

			String decrypted = AES.decrypt(cipher, encryptionKey);

			System.out.println("decrypt: " + decrypted);
			assertTrue("test text 123\0\0\0".equals(decrypted));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
