//  COMP 4140 - Assignment 3
//	Farabi Hussain
//  7878692


public class A3_FarabiHussain {
	public static void main(String[] args) {
		Operations ops = new Operations();
		Conversions con = new Conversions();
		Encryption enc = new Encryption();

		// Reading in the files
		ops.readFiles(args[1], args[0]);
		String[] key = ops.getKey();
		String[] roundKeys = ops.KeyExpansion(key);
		String[][] plaintext = con.hexToMatrix(ops.getPlaintext());
		String[][] keyMatrix = con.hexToMatrix(ops.getKey());

		// Prints necessary information
		System.out.print("\nPlaintext");
		ops.printMatrix(plaintext);
		System.out.print("\nKey");
		ops.printMatrix(keyMatrix);
		ops.keyExpansionOutput(roundKeys);

		// Calling the encryption and decryption functions
		// Intermediate results are printed from within their respective functions
		String[][] ciphertext = enc.Cipher(plaintext, roundKeys);
		String[][] decrypted = enc.InvCipher((ciphertext), roundKeys);

		System.out.println("\n\n\nEnd of processing\n");
	}
}
