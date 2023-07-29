package com.javarush.ivovnyanko.project_1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Encryption class implements the Cracker interface and is an implementation of text encryption.
 * Its main function is to encrypt the text using the cipher that is represented in the map
 */
public class Encryption implements Cracker {
    private static final String ENCRYPTED_FILE_PROMPT = "Create a new encrypted message file: ";
    private static final String FILE_EXISTS_PROMPT = "The file exists! Try again\nEnter file path: ";
    private static final ArrayList<Character> map = Map.map();

    /**
     * performs encryption of the text that is passed as an argument
     * The ciphertext is then written to that file using BufferedWriter
     *
     * @param letters ciphertext
     */
    public void encrypt(String letters) {
        String path = createPathFile();
        String encryptedText ="";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            encryptedText = encryptLetters(letters);
            writer.write(encryptedText, 0, encryptedText.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Your text:" + letters + "\n" +
                "Your encrypted text:" + encryptedText + "\n" +
                "Text saved at - " + path);
    }

    /**
     * serves to get the path to a new file
     *
     * @return path of a new file
     */
    private String createPathFile() {
        int textModeForWrongChoice = 0; // helps determine whether to display a file path request message for the first time
        String filePathForWrite;

        do {
            String messageForUser = textModeForWrongChoice == 0 ? ENCRYPTED_FILE_PROMPT : FILE_EXISTS_PROMPT;
            System.out.print(messageForUser);
            Scanner scanner = new Scanner(System.in);
            filePathForWrite = scanner.nextLine();
            textModeForWrongChoice = 1;
        } while ((Files.exists(Paths.get(filePathForWrite)))); ///////////
        return filePathForWrite;
    }

    /**
     * performs encryption of the text "letters" by replacing characters with other characters from the map
     *
     * @param letters file text
     * @return ciphertext
     */
    private String encryptLetters(String letters) {
        // Added encryption key request
        int key = 0;
        Scanner scanner = new Scanner(System.in);
        while (key <= 0) {
            System.out.print("Enter the encryption key: ");
            if (scanner.hasNextInt()) {
                key = scanner.nextInt();
            }
        }

        StringBuilder builder = new StringBuilder();
        char[] buffer = letters.toCharArray();

        for (int i = 0; i < letters.length(); i++) {
            char symbol = buffer[i];

            if (map.contains(symbol)) {
                int indexSymbol = map.indexOf(symbol);
                int newIndexSymbol = (indexSymbol + key) % map.size(); //New character index after encryption
                char encryptSymbol = map.get(newIndexSymbol);
                builder.append(encryptSymbol);
            }
        }

        return builder.toString();
    }

}


