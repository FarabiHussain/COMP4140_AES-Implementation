//  COMP 4140 - Assignment 3
//	Farabi Hussain
//  7878692

import java.io.*;

public class Operations {
	Conversions c = new Conversions();

	private static String[] key;
	private static String[] plaintext;

	static String[] Rcon = {
			"01000000", "02000000", "04000000",
			"08000000", "10000000", "20000000",
			"40000000", "80000000", "1B000000",
			"36000000", "6C000000", "D8000000",
			"AB000000", "4D000000", "9A000000"
	};

	private static String[][] sbox = {
			{"63", "7c", "77", "7b", "f2", "6b", "6f", "c5", "30", "01", "67", "2b", "fe", "d7", "ab", "76"},
			{"ca", "82", "c9", "7d", "fa", "59", "47", "f0", "ad", "d4", "a2", "af", "9c", "a4", "72", "c0"},
			{"b7", "fd", "93", "26", "36", "3f", "f7", "cc", "34", "a5", "e5", "f1", "71", "d8", "31", "15"},
			{"04", "c7", "23", "c3", "18", "96", "05", "9a", "07", "12", "80", "e2", "eb", "27", "b2", "75"},
			{"09", "83", "2c", "1a", "1b", "6e", "5a", "a0", "52", "3b", "d6", "b3", "29", "e3", "2f", "84"},
			{"53", "d1", "00", "ed", "20", "fc", "b1", "5b", "6a", "cb", "be", "39", "4a", "4c", "58", "cf"},
			{"d0", "ef", "aa", "fb", "43", "4d", "33", "85", "45", "f9", "02", "7f", "50", "3c", "9f", "a8"},
			{"51", "a3", "40", "8f", "92", "9d", "38", "f5", "bc", "b6", "da", "21", "10", "ff", "f3", "d2"},
			{"cd", "0c", "13", "ec", "5f", "97", "44", "17", "c4", "a7", "7e", "3d", "64", "5d", "19", "73"},
			{"60", "81", "4f", "dc", "22", "2a", "90", "88", "46", "ee", "b8", "14", "de", "5e", "0b", "db"},
			{"e0", "32", "3a", "0a", "49", "06", "24", "5c", "c2", "d3", "ac", "62", "91", "95", "e4", "79"},
			{"e7", "c8", "37", "6d", "8d", "d5", "4e", "a9", "6c", "56", "f4", "ea", "65", "7a", "ae", "08"},
			{"ba", "78", "25", "2e", "1c", "a6", "b4", "c6", "e8", "dd", "74", "1f", "4b", "bd", "8b", "8a"},
			{"70", "3e", "b5", "66", "48", "03", "f6", "0e", "61", "35", "57", "b9", "86", "c1", "1d", "9e"},
			{"e1", "f8", "98", "11", "69", "d9", "8e", "94", "9b", "1e", "87", "e9", "ce", "55", "28", "df"},
			{"8c", "a1", "89", "0d", "bf", "e6", "42", "68", "41", "99", "2d", "0f", "b0", "54", "bb", "16"}
	};

	static String[][] inv_sbox = {
			{"52", "09", "6a", "d5", "30", "36", "a5", "38", "bf", "40", "a3", "9e", "81", "f3", "d7", "fb"},
			{"7c", "e3", "39", "82", "9b", "2f", "ff", "87", "34", "8e", "43", "44", "c4", "de", "e9", "cb"},
			{"54", "7b", "94", "32", "a6", "c2", "23", "3d", "ee", "4c", "95", "0b", "42", "fa", "c3", "4e"},
			{"08", "2e", "a1", "66", "28", "d9", "24", "b2", "76", "5b", "a2", "49", "6d", "8b", "d1", "25"},
			{"72", "f8", "f6", "64", "86", "68", "98", "16", "d4", "a4", "5c", "cc", "5d", "65", "b6", "92"},
			{"6c", "70", "48", "50", "fd", "ed", "b9", "da", "5e", "15", "46", "57", "a7", "8d", "9d", "84"},
			{"90", "d8", "ab", "00", "8c", "bc", "d3", "0a", "f7", "e4", "58", "05", "b8", "b3", "45", "06"},
			{"d0", "2c", "1e", "8f", "ca", "3f", "0f", "02", "c1", "af", "bd", "03", "01", "13", "8a", "6b"},
			{"3a", "91", "11", "41", "4f", "67", "dc", "ea", "97", "f2", "cf", "ce", "f0", "b4", "e6", "73"},
			{"96", "ac", "74", "22", "e7", "ad", "35", "85", "e2", "f9", "37", "e8", "1c", "75", "df", "6e"},
			{"47", "f1", "1a", "71", "1d", "29", "c5", "89", "6f", "b7", "62", "0e", "aa", "18", "be", "1b"},
			{"fc", "56", "3e", "4b", "c6", "d2", "79", "20", "9a", "db", "c0", "fe", "78", "cd", "5a", "f4"},
			{"1f", "dd", "a8", "33", "88", "07", "c7", "31", "b1", "12", "10", "59", "27", "80", "ec", "5f"},
			{"60", "51", "7f", "a9", "19", "b5", "4a", "0d", "2d", "e5", "7a", "9f", "93", "c9", "9c", "ef"},
			{"a0", "e0", "3b", "4d", "ae", "2a", "f5", "b0", "c8", "eb", "bb", "3c", "83", "53", "99", "61"},
			{"17", "2b", "04", "7e", "ba", "77", "d6", "26", "e1", "69", "14", "63", "55", "21", "0c", "7d"}
	};


// ==========================================================================================
// MATH FUNCTIONS
// ==========================================================================================

	// -------------------------- //
	// 			ADDITION		  //
	// -------------------------- //

	// Returns the XOR of 2 elements
	String add(String hexA, String hexB) {
		int sum = c.hexToInt(hexA);
		sum ^= c.hexToInt(hexB);
		return Integer.toHexString(sum);
	}

	// Returns the XOR of 3 elements
	String add(String hexA, String hexB, String hexC) {
		int sum = c.hexToInt(hexA);
		sum ^= c.hexToInt(hexB);
		sum ^= c.hexToInt(hexC);
		return Integer.toHexString(sum);
	}

	// Returns the XOR of 4 elements
	String add(String hexA, String hexB, String hexC, String hexD) {
		int sum = c.hexToInt(hexA);
		sum ^= c.hexToInt(hexB);
		sum ^= c.hexToInt(hexC);
		sum ^= c.hexToInt(hexD);
		return Integer.toHexString(sum);
	}

	// -------------------------- //
	// 		 MULTIPLICATION	  	  //
	// -------------------------- //

	// takes the input hex as strings returns the GF(2^8) product
	String mult(String hexA, String hex) {
		if (hexA.length() == 1) hexA = 0 + hexA;
		if (hex.length() == 1) hex = 0 + hex;

		if (hexA.equals("02")) {
			// multiplication with 0x02
			return mult_02(hex);
		} else if (hexA.equals("03")) {
			// multiplication with 0x02 and 0x01, then return their XOR
			return add(mult_02(hex), hex);
		} else if (hexA.equals("09")) {
			// multiplication with 0x08 and 0x01, then return their XOR
			return add(mult_02(mult_02(mult_02(hex))), hex);
		} else if (hexA.equals("0b")) {
			// multiplication with 0x08, 0x02, and 0x01, then return their XOR
			return add(mult_02(mult_02(mult_02(hex))), mult_02(hex), hex);
		} else if (hexA.equals("0d")) {
			// multiplication with 0x08, 0x04, and 0x01, then return their XOR
			return add(mult_02(mult_02(mult_02(hex))), mult_02(mult_02(hex)), hex);
		} else if (hexA.equals("0e")) {
			// multiplication with 0x08, 0x04, and 0x02, then return their XOR
			return add(mult_02(mult_02(mult_02(hex))), mult_02(mult_02(hex)), mult_02(hex));
		} else {
			return "\nRestricted multiplication";
		}
	}

	// returns the GF(2^8) product with 0x02
	private String mult_02(String hex) {
		String hexProd = "";
		String hexABin = c.hexToBin(hex);

		// if the leading bit is 1, we add 0x1b to the result
		// if the leading bit is 0, the result is returned as-is
		if (hexABin.charAt(0) == '1') {
			hexProd = Integer.toBinaryString(leftShift(hex));
			hexProd = c.binToHex(hexProd.substring(1));
			hexProd = add(hexProd, "1b");
		} else if (hexABin.charAt(0) == '0') {
			hexProd = Integer.toBinaryString(leftShift(hex));
			hexProd = c.binToHex(hexProd);
		}
		return fixHexLength(hexProd, 2);
	}

// ==========================================================================================
// CIPHER FUNCTIONS
// ==========================================================================================

	// Performs a cyclic permutation on the input word
	String[] RotWord(String word) {
		return new String[]{word.substring(2, 4), word.substring(4, 6), word.substring(6, 8), word.substring(0, 2)};
	}

	// Takes a word as input and applies the S-box substitution to each byte
	String[] SubWord(String[] word) {
		String a0 = getFromBox(word[0]);
		String a1 = getFromBox(word[1]);
		String a2 = getFromBox(word[2]);
		String a3 = getFromBox(word[3]);
		return new String[]{a0, a1, a2, a3};
	}

	// Takes the key as input and generates a key schedule. Generates a total of 44 words for 128-bit key
	String[] KeyExpansion(String[] key) {
		String[] expandedKey = new String[44];
		String temp;
		int Nk = 4, i = 0;

		while (i < 4) {
			expandedKey[i] = key[4 * i] + key[4 * i + 1] + key[4 * i + 2] + key[4 * i + 3];
			i++;
		}

		i = Nk;

		while (i < expandedKey.length) {
			temp = expandedKey[i - 1];

			if (i % Nk == 0)
				temp = Long.toHexString(c.hexToLong(SubWord(RotWord(temp))) ^ c.hexToLong(Rcon[(i / Nk) - 1]));

			expandedKey[i] = Long.toHexString(c.hexToLong(expandedKey[i - Nk]) ^ c.hexToLong(temp));
			expandedKey[i] = fixHexLength(expandedKey[i], 8);
			i++;
		}
		return expandedKey;
	}

// ==========================================================================================
// UTILITIES
// ==========================================================================================

	// returns the result after applying a left bit shift
	int leftShift(String target) {
		return Integer.parseInt(target, 16) << 1;
	}

	// Calls the private function below to read the specified file
	void readFiles(String key_file, String plaintext_file) {
		key = readFile(key_file);
		plaintext = readFile(plaintext_file);
	}

	// Prints the result of the key expansion
	void keyExpansionOutput(String[] expandedKeys) {
		System.out.println("\nKey Schedule");
		int nextLine = 0;
		for (int i = 0; i < expandedKeys.length; i++) {
			nextLine++;
			if (nextLine == 4) {
				System.out.print(expandedKeys[i]);
				nextLine = 0;
				System.out.println();
			} else {
				System.out.print(expandedKeys[i] + ", ");
			}
		}
	}

	// Prints the matrix with formatting
	void printMatrix(String[][] matrix) {
		System.out.println();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(matrix[j][i] + " ");
			}
			System.out.print("  ");
		}
		System.out.print("\n");
	}

	// Private function that reads individual files specified in the parameter. If any string in the input file is 1-character long, a zero is prefixed
	private String[] readFile(String filename) {
		StringBuilder fileContents = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				fileContents.append(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] temp = fileContents.toString().trim().split("\\s+");

		for (int i = 0; i < temp.length; i++) {
			temp[i] = fixHexLength(temp[i], 2);
		}

		return temp;
	}

	// Returns the element situated at the array index (location[0], location[1]) -- for sbox
	String getFromBox(String location) {
		return sbox[c.hexToInt(location.substring(0, 1))][c.hexToInt(location.substring(1, 2))];
	}

	// Returns the element situated at the array index (location[0], location[1]) -- for inv_sbox
	String getFromInvBox(String location) {
		return inv_sbox[c.hexToInt(location.substring(0, 1))][c.hexToInt(location.substring(1, 2))];
	}

	// Fixes the length of the hex to the specified length -- adds leading zeros if necessary
	String fixHexLength(String hex, int limit) {
		String temp = "";

		if (hex.length() < limit) {
			int zerosToPrefix = limit - hex.length();
			for (int i = 0; i < zerosToPrefix; i++) {
				temp += "0";
			}
			temp += hex;
		} else {
			temp = hex;
		}
		return temp;
	}

	String[] getPlaintext() {
		return plaintext;
	}

	String[] getKey() {
		return key;
	}
}