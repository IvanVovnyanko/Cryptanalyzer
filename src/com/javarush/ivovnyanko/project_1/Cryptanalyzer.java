package com.javarush.ivovnyanko.project_1;

import java.io.*;
import java.util.Scanner;
import java.util.stream.Collectors;


/**
 * class: Cryptanalyzer
 * Receives a file from the user, then offers to select the program mode: decrypt, encrypt, Brute force
 * According to the program mode, decrypts or encrypts the text of the file. The result is saved to a new file.
 */
public class Cryptanalyzer {
    // for the methode showStartMessageProgramAndChoiceUser()
    private static final String PROGRAM_MESSAGE1 = "Please, select program mode. Decrypt or encrypt a file";
    private static final String PROGRAM_MESSAGE2 = "For decrypt - enter '1', for encrypt - enter '2', for Brute force - 3";
    private static final String CHOICE_IS = "Your choice is: ";
    private static final String WRONG_CHOICE = "You've made the wrong choice! Try again";
    private static final String WRONG_CHOICE_NOT_NUMBER = "You can only enter numbers! Try again";
    private static final String CORRECT_CHOICE = "Excellent! The file is selected. Getting Started";
    private static final long SLEEP_FOR_MESSAGE = 750;

    // for the method inputPathFile()
    private static final String INPUT_PATH = "Enter file path: ";
    private static final String FILE_NOT_EXIT = "The file does not exist! Try again \n";


    public static void main(String[] args) {
        // set the program mode
        int userChoice = showStartMessageProgramAndChoiceUser();
        Cracker mode = turnInMode(userChoice);

        // performs work according to the selected mode
        makeModeProgram(mode);
    }

    /**
     * Shows instructions for managing input information
     *
     * @return the program mode
     */
    private static int showStartMessageProgramAndChoiceUser() {
        int result = 0;
        boolean checkCorrectChoice = true; // checking if the selected mode is correct

        // show start text
        try {
            System.out.println(PROGRAM_MESSAGE1);
            Thread.sleep(SLEEP_FOR_MESSAGE);
            System.out.println(PROGRAM_MESSAGE2);
            Thread.sleep(SLEEP_FOR_MESSAGE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // the user enters the path to the file and checks for the existence of the file
        while (checkCorrectChoice) {
            Scanner scanner = new Scanner(System.in);
            System.out.print(CHOICE_IS);
            if (scanner.hasNextInt()) {
                result = scanner.nextInt();
                if (result == 1 || result == 2 || result == 3) {
                    checkCorrectChoice = false; // the user has selected the correct mode. loop exit
                } else {
                    System.out.println(WRONG_CHOICE);
                }
            } else {
                System.out.println(WRONG_CHOICE_NOT_NUMBER);
            }
        }
        System.out.println(CORRECT_CHOICE);
        return result;
    }

    /**
     * Turns on program mode selected by the user
     *
     * @param userChoice
     * @return program mode
     */
    private static Cracker turnInMode(int userChoice) {
        return switch (userChoice) {
            case 1 -> new Decipher();
            case 2 -> new Encryption();
            default -> new BruteForce();
        };
    }

    /**
     * Starts the program mode selected by the user
     *
     * @param mode user choice
     */
    private static void makeModeProgram(Cracker mode) {
        String letters = readerUserFile(); // text from the file specified by the user

        if (mode instanceof Encryption) {
            Encryption encryption = (Encryption) mode;
            encryption.encrypt(letters);
        } else if (mode instanceof Decipher) {
            Decipher decipher = (Decipher) mode;
            decipher.decoding(letters);
        } else if (mode instanceof BruteForce) {
            BruteForce bruteForce = (BruteForce) mode;
            bruteForce.bruteForceDecrypt(letters);
        }
    }

    /**
     * Reads text from a file specified by the user
     *
     * @return text from the file specified by the user
     */
    public static String readerUserFile() {
        String letters = "";

        try (FileReader fileReader = new FileReader(inputPathFile());
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            letters = bufferedReader.lines()
                    .collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(letters);
        return letters;
    }

    /**
     * Gets the path of an existing file
     *
     * @return the path to the file
     */
    private static String inputPathFile() {
        String messageForUser;
        String filePathForRead;
        int textModeForWrongChoice = 0; // switcher in for loop text

        do {
            messageForUser = textModeForWrongChoice == 0 ? INPUT_PATH : FILE_NOT_EXIT + INPUT_PATH;
            System.out.print(messageForUser);
            Scanner scanner = new Scanner(System.in);
            filePathForRead = scanner.nextLine();
            textModeForWrongChoice = 1;
        } while (!new File(filePathForRead).exists());

        return filePathForRead;
    }
}