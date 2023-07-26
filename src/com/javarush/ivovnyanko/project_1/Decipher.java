package com.javarush.ivovnyanko.project_1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This Decipher class implements the Cracker interface
 * and provides methods to decrypt text using the decrypt method
 */
public class Decipher implements Cracker {
    private static final String ENCRYPTED_FILE_PROMPT = "Create a new encrypted message file: ";
    private static final String FILE_EXISTS_PROMPT = "The file exists! Try again\nEnter file path: ";
    private static final ArrayList<Character> map = Map.map();

    /**
     * performs text decryption, and writes the decryption results to a new file using the createPathFile() method
     *
     * @param letters accepts ciphertext
     */
    public void decoding(String letters) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(createPathFile()))) {
            String str = decodingLetters(letters);
            writer.write(str, 0, str.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * is used to get the path to the file in which the results of decryption
     * or decryption operations will be written
     *
     * @return path to the file where the results of decryption or decryption operations will be written
     */
    private String createPathFile() {
        int textModeForWrongChoice = 0; // used to control repeated path requests
        String filePathForWrite;

        do {
            String messageForUser = textModeForWrongChoice == 0 ? ENCRYPTED_FILE_PROMPT : FILE_EXISTS_PROMPT;
            System.out.print(messageForUser);
            Scanner scanner = new Scanner(System.in);
            filePathForWrite = scanner.nextLine();
            textModeForWrongChoice = 1;
        } while ((Files.exists(Paths.get(filePathForWrite))));
        return filePathForWrite;
    }

    /**
     * designed to decipher text that was encrypted using the "Caesar" cipher
     * with a 3-character shift in the alphabet
     *
     * @param letters accepts encrypted text
     * @return decoded text as a string
     */
    private String decodingLetters(String letters) {
        StringBuilder builder = new StringBuilder(); // will be used to construct the decrypted text
        char[] buffer = letters.toCharArray(); // to iterate over each character
        char decryptedSymbol;

        for (int i = 0, j = 0; i < letters.length(); i++, j++) {
            char symbol = buffer[i];

            if (map.contains(symbol)) {
                int indexSymbol = map.indexOf(symbol);
                if (indexSymbol >= 3) {
                    decryptedSymbol = map.get(indexSymbol - 3);
                } else {
                    decryptedSymbol = map.get(map.size() - (3 - indexSymbol));
                }
                builder.append(decryptedSymbol);
            }
        }
        return builder.toString();
    }
}
