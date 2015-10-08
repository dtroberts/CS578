package cs578hw1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;

/** 
 * Brute-force attack a ciphertext using affine decryption.  
 * @author Devin Roberts
 * */
public class AffineDecrypter {
	
	public static void main(String[] args) throws IOException {
		// read ciphertext from local file
		StringBuilder cipherText = new StringBuilder();
		String line = "";
		
		InputStream in = new FileInputStream(new File("ciphertext1.txt"));
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
	 * Perform the brute-force attack. Prints to output file output1.txt. 
	 * @param cipherText input to attack
	 * @throws FileNotFoundException
	 */
	private static void decryptAffine(String cipherText) throws FileNotFoundException {
		StringBuilder out = null;
		int x; // represents a^-1
		PrintWriter writer = new PrintWriter("output1.txt");
		boolean isUpperCase = false;
		char decrypted = 'a';
		
		for (int a = 0; a < 26; a++) {
			if (coprime(a, 26)) {
				x = getInverse(a).intValue();
				for (int b = 0; b < 26; b++) {
					out = new StringBuilder();
					out.append("--- " + a + " --- " + b + " ---\n");
					// Perform the  decryption of the cipher text using the key (a, b)
					for (int i = 0; i < cipherText.length(); i++) {
						char c = cipherText.charAt(i);
						if (Character.isLetter(c)) {
							isUpperCase = Character.isUpperCase(c);
							decrypted = decryptedCharacter(x, c, b);
							if (isUpperCase) {
								decrypted = Character.toUpperCase(decrypted);
							}
							out.append(decrypted);
						}
						else {
							out.append(c);
						}
					}
					writer.println(out);
				}
			}
			
		}
		writer.close();
	}
	
	/** Simple function which determines if two numbers are coprime. */
	private static boolean coprime(int x, int y) {
		int temp;

		while (y != 0) {
			temp = x;
			x = y;
			y = temp % y;
		}
		return x == 1;
	}
	
	/** Simple helper function which returns the inverse of a given integer. */
	private static BigInteger getInverse(int a) {
		return BigInteger.valueOf(a).modInverse(BigInteger.valueOf(26));
	}
	
	/** Perform the decryption of the given character. */
	private static char decryptedCharacter(int x, char c, int b) {
		char lowerCaseC = Character.toLowerCase(c);
		int i = x * (lowerCaseC - 'a' - b + 26);
		return (char) (i % 26 + 'a');
	}
}
