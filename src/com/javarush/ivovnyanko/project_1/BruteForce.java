package com.javarush.ivovnyanko.project_1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * This BruteForce class implements the Cracker interface
 * and provides methods to decrypt text using the "brute force" method
 */
public class BruteForce implements Cracker {
    private static final String ENCRYPTED_FILE_PROMPT = "Create a new deciphered message file: ";
    private static final String FILE_EXISTS_PROMPT = "The file exists! Try again\nEnter file path: ";
    private final ArrayList<Character> map = Map.map();
    private static HashSet<String> englishWords;

    /**
     * decrypts the text using the "brute force" method, that is, iterates over all possible shifts.
     * It accepts ciphertext as an encryptedText input parameter.
     *
     * @param encryptedText
     */
    public void bruteForceDecrypt(String encryptedText) {
        initializeDictionary();
        boolean isWordPresent = false;
        // gets the alphabet size from the map list to know the number of possible shifts.
        int mapSize = map.size();
        String path = createPathFile();
        StringBuilder decryptedText = new StringBuilder();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (int shift = 1; shift < mapSize; shift++) {

                for (char symbol : encryptedText.toCharArray()) {
                    if (map.contains(symbol)) {
                        int indexSymbol = map.indexOf(symbol);
                        int decryptedIndex = (indexSymbol - shift + mapSize) % mapSize;
                        char decryptedSymbol = map.get(decryptedIndex);
                        decryptedText.append(decryptedSymbol);
                    }
                }

                if (isEnglishWord(decryptedText.toString())) {
                    writer.write(decryptedText.toString());
                    writer.newLine();
                    isWordPresent = true;
                    break;
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!(isWordPresent)) {
            System.out.println("This word does not exist in the dictionary");
            if (Files.exists(Paths.get(path))) {
                try {
                    Files.delete(Paths.get(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else {
            System.out.println("Your text(encrypted) word: " + encryptedText.toString() + "\n" +
                    "Your deciphered word: " + decryptedText.toString() + "\n" +
                    "Text saved at - " + path);
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

    private static void initializeDictionary() {
        englishWords = new HashSet<>();
        Path path = Paths.get("src/english_dictionary.txt");
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String word;
            while ((word = reader.readLine()) != null) {
                englishWords.add(word.trim().toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isEnglishWord(String word) {
        return englishWords.contains(word.trim().toLowerCase());
    }


}
