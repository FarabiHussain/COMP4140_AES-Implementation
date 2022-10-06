//  COMP 4140 - Assignment 3
//	Farabi Hussain
//  7878692

public class Encryption {
	Operations ops = new Operations();

	// Applies the encryption to the input plaintext. Array w[] is the 44 keys obtained from expansion
	String[][] Cipher(String[][] plaintext, String[] w) {
		System.out.println("\n\n\nENCRYPTION PROCESS\n" + "------------------");

		Conversions con = new Conversions();
		Transformations trn = new Transformations();
		Operations ops = new Operations();

		String[][] state = plaintext;
		System.out.print("Plaintext:");
		ops.printMatrix(state);
		int position = 0;

		// combines the next 4 words to obtain the scheduled key
		String currWord = w[position++] + w[position++] + w[position++] + w[position++];
		state = trn.AddRoundKey(state, con.hexToMatrix(currWord));

		for (int i = 1; i <= 9; i++) {
			System.out.print("\nState after call " + i + " to MixColumns()\n" + "-------------------------------------");
			currWord = w[position++] + w[position++] + w[position++] + w[position++];
			state = trn.SubBytes(state);
			state = trn.ShiftRows(state);
			state = trn.MixColumns(state);
			ops.printMatrix(state);
			state = trn.AddRoundKey(state, con.hexToMatrix(currWord));
		}

		currWord = w[position++] + w[position++] + w[position++] + w[position++];
		state = trn.SubBytes(state);
		state = trn.ShiftRows(state);
		state = trn.AddRoundKey(state, con.hexToMatrix(currWord));
		System.out.print("\nCiphertext");
		ops.printMatrix(state);

		return state;
	}

	// Decrypts the ciphertext taken as input. Array w[] is the 44 keys obtained from expansion
	String[][] InvCipher(String[][] ciphertext, String[] w) {
		System.out.println("\n\n\nDECRYPTION PROCESS\n" + "------------------");

		Conversions con = new Conversions();
		Transformations trn = new Transformations();
		Operations ops = new Operations();

		String[][] state = ciphertext;
		System.out.print("Ciphertext");
		ops.printMatrix(state);
		int position = w.length - 1;

		// combines the previous 4 words to obtain the scheduled key
		String currWord = w[position - 3] + w[position - 2] + w[position - 1] + w[position];
		position -= 4;
		state = trn.AddRoundKey(state, con.hexToMatrix(currWord));

		for (int i = 1; i <= 9; i++) {
			System.out.print("\nState after call " + i + " to InvMixColumns()\n" + "-------------------------------------");
			currWord = w[position - 3] + w[position - 2] + w[position - 1] + w[position];
			state = trn.InvShiftRows(state);
			state = trn.InvSubBytes(state);
			state = trn.AddRoundKey(state, con.hexToMatrix(currWord));
			state = trn.InvMixColumns(state);
			ops.printMatrix(state);
			position -= 4;
		}

		currWord = w[0] + w[1] + w[2] + w[3];
		state = trn.InvShiftRows(state);
		state = trn.InvSubBytes(state);
		System.out.print("\nPlaintext:");
		state = trn.AddRoundKey(state, con.hexToMatrix(currWord));
		ops.printMatrix(state);

		return state;
	}
}