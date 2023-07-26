package com.javarush.ivovnyanko.project_1;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class BruteForce implements Cracker {
    private static final String ENCRYPTED_FILE_PROMPT = "Create a new encrypted message file: ";
    private static final String FILE_EXISTS_PROMPT = "The file exists! Try again\nEnter file path: ";
    private final ArrayList<Character> map = Map.map();

    public void bruteForceDecrypt(String encryptedText) {
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
    private String createPathFile() {
        int textModeForWrongChoice = 0;
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
}
