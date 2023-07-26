package com.javarush.ivovnyanko.project_1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Decipher implements Cracker{
    private static final String ENCRYPTED_FILE_PROMPT = "Create a new encrypted message file: ";
    private static final String FILE_EXISTS_PROMPT = "The file exists! Try again\nEnter file path: ";
    private static final ArrayList<Character> map = Map.map();

    public void decoding (String letters) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(createPathFile()))) {
            String i = decodingLetters(letters);
            writer.write(i, 0, i.length());
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

    private String decodingLetters(String letters) {
        StringBuilder builder = new StringBuilder();
        char[] buffer = letters.toCharArray();
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

        System.out.println(builder.toString());
        return builder.toString();
    }
}
