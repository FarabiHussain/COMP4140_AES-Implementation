# Project details

1. Project SDK: openjdk-17

# Building and running the program

1. From within the directory where the java files are located, run  `javac -cp . src/*.java` to build the program. The output files will be located in the same directory.

2. Once the program is built, run the command `java A3_FarabiHussain <plaintext_filename> <key_filename>` to run the program with the provided arguments. 
3. The program (hopefully) runs to completion with all output printed onto the console.

# Separate routines and their locations

1. `SubBytes()` (line 9), `InvSubBytes()` (line 91), `ShiftRows()` (line 19), `InvShiftRows()` (line 101), `MixColumns()` (line 34), `InvMixColumns()` (line 132) are in the class `Transformations.java`.
2. `KeyExpansion()` (line 157) is in the class `Operations.java`. Other essential routines like `RotWord()` and `SubWord()` are in this class as well.
3. The encrypt and decrypt routines are named `Cipher()` (line 5) and `InvCipher()` (line 42) respectively. They are located in the `Encyption.java` class.



