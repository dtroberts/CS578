package cs578hw1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/** 
 * Attack on the Vignere cipher with using a known key.
 * @author Devin Roberts
 */
public class VignereDecrypter {
	private static char[] key = {'p', 'i', 'b', 'i', 's'};
	private static final String alphabet = "abcdefghijklmnopqrstuvwxyz"; 
	
	public static void main(String[] args) throws IOException {
		// read ciphertext from local file
		StringBuilder cipherText = new StringBuilder();
		String line = "";
		
		InputStream in = new FileInputStream(new File("ciphertext2.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        
        while ((line = reader.readLine()) != null) {
            cipherText.append(line);
            cipherText.append('\n');
        }
        reader.close();
        decryptAffine(new String(cipherText));
        
        System.out.println("successfully decrypted");
	}
	
	
	/**
	 * Perform the decryption using the given key. Prints to output file output2.txt.
	 * @param cipherText
	 * @throws FileNotFoundException
	 */
	private static void decryptAffine(String cipherText) throws FileNotFoundException {
		StringBuilder out = new StringBuilder();
		int keyCounter = 0;
		PrintWriter writer = new PrintWriter("output2.txt");
		boolean isUpperCase = false;
		char decrypted = 'a';		
		
		for (int i = 0; i < cipherText.length(); i++) {
			char c = cipherText.charAt(i);
			if (Character.isLetter(c)) {
				isUpperCase = Character.isUpperCase(c);
				decrypted = decryptedCharacter(c, key[keyCounter]);
				if (isUpperCase) {
					decrypted = Character.toUpperCase(decrypted);
				}
				out.append(decrypted);
				keyCounter = increment(keyCounter);
				
			}
			else {
				out.append(c);
			}
		}
		writer.println(out);
		writer.close();
	}
	
	/** Perform the decryption of the given character. */
	private static char decryptedCharacter(char c, char key) {
		char lowerCaseC = Character.toLowerCase(c);
		int k = (alphabet.indexOf(key) % 26);
		int ret = lowerCaseC - (k % 26);
		if (ret < 'a') {
			ret = ret + 26;
		}
		return (char) (ret);
	}
	
	/** Simple helper function to limit an integer from incrementing too far. */
	private static int increment(int i) {
		i++;
		if (i == key.length) {
			i = 0;
		}
		return i;
	}
}
