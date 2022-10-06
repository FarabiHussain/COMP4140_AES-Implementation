//  COMP 4140 - Assignment 3
//	Farabi Hussain
//  7878692

public class Transformations {
	Operations ops = new Operations();

// ==========================================================================================
// TRANSFORMATIONS
// ==========================================================================================

	// Substitutes each byte of the State from the substitution table
	public String[][] SubBytes(String[][] state) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				state[i][j] = ops.getFromBox(state[i][j]);
			}
		}
		return state;
	}

	// Cyclically shifts the last three rows of the State
	public String[][] ShiftRows(String[][] state) {
		String[][] shiftedState = new String[4][4];

		// mathematically calculates which element is placed where after cyclical shift
		for (int i = 0; i < 4; i++) {
			shiftedState[i][0] = ops.fixHexLength(state[i][i], 2);
			shiftedState[i][1] = ops.fixHexLength(state[i][(i + 1) % 4], 2);
			shiftedState[i][2] = ops.fixHexLength(state[i][(i + 2) % 4], 2);
			shiftedState[i][3] = ops.fixHexLength(state[i][(i + 3) % 4], 2);
		}

		return shiftedState;
	}

	// Replaces the four bytes on a column with the result of a multiplication
	public String[][] MixColumns(String[][] state) {
		String[][] mixedState = new String[4][4];

		// applies the formula found in the FIPS document for each array element
		// fixes length to two digits afterwards to maintain string length of 2
		for (int c = 0; c < 4; c++) {
			mixedState[0][c] = ops.fixHexLength(ops.add(
							ops.mult("02", state[0][c]),
							ops.mult("03", state[1][c]),
							state[2][c],
							state[3][c]),
					2);

			mixedState[1][c] = ops.fixHexLength(ops.add(
							state[0][c],
							ops.mult("02", state[1][c]),
							ops.mult("03", state[2][c]),
							state[3][c]),
					2);

			mixedState[2][c] = ops.fixHexLength(ops.add(
							state[0][c],
							state[1][c],
							ops.mult("02", state[2][c]),
							ops.mult("03", state[3][c])),
					2);

			mixedState[3][c] = ops.fixHexLength(ops.add(
							ops.mult("03", state[0][c]),
							state[1][c],
							state[2][c],
							ops.mult("02", state[3][c])),
					2);
		}

		return mixedState;
	}

	// Adds the Round Key to the State by a bitwise XOR operation.
	public String[][] AddRoundKey(String[][] state, String[][] roundKey) {
		String[][] temp = new String[4][4];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				temp[i][j] = ops.add(state[i][j], roundKey[i][j]);
				temp[i][j] = ops.fixHexLength(temp[i][j], 2);
			}
		}

		return temp;
	}

// ==========================================================================================
// INVERSE TRANSFORMATIONS
// ==========================================================================================

	// Substitutes each byte of the State from the inverse substitution table
	public String[][] InvSubBytes(String[][] state) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				state[i][j] = ops.getFromInvBox(state[i][j]);
			}
		}
		return state;
	}

	// Cyclically shifts the last three rows of the State
	public String[][] InvShiftRows(String[][] state) {
		String[][] shiftedState = new String[4][4];

		// the new array is empty, so the first row is places as-is
		shiftedState[0][0] = ops.fixHexLength(state[0][0], 2);
		shiftedState[0][1] = ops.fixHexLength(state[0][1], 2);
		shiftedState[0][2] = ops.fixHexLength(state[0][2], 2);
		shiftedState[0][3] = ops.fixHexLength(state[0][3], 2);

		// row 1 shift
		shiftedState[1][0] = ops.fixHexLength(state[1][3], 2);
		shiftedState[1][1] = ops.fixHexLength(state[1][0], 2);
		shiftedState[1][2] = ops.fixHexLength(state[1][1], 2);
		shiftedState[1][3] = ops.fixHexLength(state[1][2], 2);

		// row 2 shift
		shiftedState[2][0] = ops.fixHexLength(state[2][2], 2);
		shiftedState[2][1] = ops.fixHexLength(state[2][3], 2);
		shiftedState[2][2] = ops.fixHexLength(state[2][0], 2);
		shiftedState[2][3] = ops.fixHexLength(state[2][1], 2);

		// row 3 shift
		shiftedState[3][0] = ops.fixHexLength(state[3][1], 2);
		shiftedState[3][1] = ops.fixHexLength(state[3][2], 2);
		shiftedState[3][2] = ops.fixHexLength(state[3][3], 2);
		shiftedState[3][3] = ops.fixHexLength(state[3][0], 2);

		return shiftedState;
	}

	// Inverse of MixColumns() - multiplications with 0x9, 0xB, 0xD, 0xE instead
	public String[][] InvMixColumns(String[][] state) {
		String[][] mixedState = new String[4][4];

		// applies the formula found in the FIPS document for each array element
		// fixes length to two digits afterwards to maintain string length of 2
		for (int c = 0; c < 4; c++) {
			mixedState[0][c] = ops.fixHexLength(ops.add(
							ops.mult("0e", state[0][c]),
							ops.mult("0b", state[1][c]),
							ops.mult("0d", state[2][c]),
							ops.mult("09", state[3][c])),
					2);

			mixedState[1][c] = ops.fixHexLength(ops.add(
							ops.mult("09", state[0][c]),
							ops.mult("0e", state[1][c]),
							ops.mult("0b", state[2][c]),
							ops.mult("0d", state[3][c])),
					2);

			mixedState[2][c] = ops.fixHexLength(ops.add(
							ops.mult("0d", state[0][c]),
							ops.mult("09", state[1][c]),
							ops.mult("0e", state[2][c]),
							ops.mult("0b", state[3][c])),
					2);

			mixedState[3][c] = ops.fixHexLength(ops.add(
							ops.mult("0b", state[0][c]),
							ops.mult("0d", state[1][c]),
							ops.mult("09", state[2][c]),
							ops.mult("0e", state[3][c])),
					2);
		}
		return mixedState;
	}
}
