package com.javarush.ivovnyanko.project_1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This BruteForce class implements the Cracker interface
 * and provides methods to decrypt text using the "brute force" method
 */
public class BruteForce implements Cracker {
    private static final String ENCRYPTED_FILE_PROMPT = "Create a new encrypted message file: ";
    private static final String FILE_EXISTS_PROMPT = "The file exists! Try again\nEnter file path: ";
    private final ArrayList<Character> map = Map.map();

    /**
     * decrypts the text using the "brute force" method, that is, iterates over all possible shifts.
     * It accepts ciphertext as an encryptedText input parameter.
     *
     * @param encryptedText
     */
    public void bruteForceDecrypt(String encryptedText) {
        // gets the alphabet size from the map list to know the number of possible shifts.
        int mapSize = map.size();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(createPathFile()))) {
            for (int shift = 1; shift < mapSize; shift++) {

                StringBuilder decryptedText = new StringBuilder();

                for (char symbol : encryptedText.toCharArray()) {
                    if (map.contains(symbol)) {
                        int indexSymbol = map.indexOf(symbol);
                        int decryptedIndex = (indexSymbol - shift + mapSize) % mapSize;
                        char decryptedSymbol = map.get(decryptedIndex);
                        decryptedText.append(decryptedSymbol);
                    } else {
                        decryptedText.append(symbol);
                    }
                }

                writer.write("Shift " + shift + ": " + decryptedText.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * is designed to get the path of the file
     * where the decryption results will be written when using the "brute force" method
     *
     * @return path of a new file
     */
    private String createPathFile() {
        int textModeForWrongChoice = 0; // to prevent repeated requests for a file path if it was specified incorrectly
        String filePathForWrite;

        do {
            String messageForUser = textModeForWrongChoice == 0 ? ENCRYPTED_FILE_PROMPT : FILE_EXISTS_PROMPT;
            System.out.print(messageForUser);
            Scanner scanner = new Scanner(System.in);
            filePathForWrite = scanner.nextLine();
            textModeForWrongChoice++;
        } while ((Files.exists(Paths.get(filePathForWrite))));
        return filePathForWrite;
    }
}
