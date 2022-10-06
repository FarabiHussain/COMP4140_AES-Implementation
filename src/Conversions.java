//  COMP 4140 - Assignment 3
//	Farabi Hussain
//  7878692

public class Conversions {
	int hexToInt(String hex) {
		return Integer.parseInt(hex, 16);
	}

	long hexToLong(String hex) {
		return Long.parseLong(hex, 16);
	}

	long hexToLong(String[] hexArray) {
		String hex = "";
		for (int i = 0; i < hexArray.length; i++) {
			hex += hexArray[i];
		}
		return Long.parseLong(hex, 16);
	}

	String binToHex(String bin) {
		return Integer.toHexString(Integer.parseInt(bin, 2));
	}

	String hexToBin(String hex) {
		hex = hex.toLowerCase();
		for (int i = 0; i < 16; i++) {
			String thisHex = Integer.toHexString(i);
			String withBinary = String.format("%04d", Integer.parseInt(Integer.toBinaryString(i)));
			hex = hex.replaceAll(thisHex, withBinary);
		}
		return hex;
	}

	String[][] hexToMatrix(String hexKey) {
		String[][] matrix = new String[4][4];
		int curr = 0;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				matrix[j][i] = hexKey.substring(curr, curr + 2);
				curr = curr + 2;
			}
		}

		return matrix;
	}

	String[][] hexToMatrix(String[] hexKey) {
		String[][] matrix = new String[4][4];
		int curr = 0;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				matrix[j][i] = hexKey[curr];
				curr++;
			}
		}

		return matrix;
	}
}
