
import com.javarush.ivovnyanko.project_1.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class test {
    private static final String USER_CHOICE = "Please, select program mode. Decrypt or encrypt a file";
    private static final String USER_CHOICE2 = "For decrypt - enter '1', for encrypt - enter '2', for Brute force - 3";
    private static final String USER_CHOICE3 = "Your choice is: ";


    public static void main(String[] args) {
        int userChoice = beginningProgram();
        Cracker mode = turnInMode(userChoice);
//        workProgram(mode);

        ArrayList<Character> map = Map.map();
        System.out.println(map);

        // выбор режима програмы // шифр или розшифр
        // подключение текса
        // обработка или шифр или разшифр
        // сохранение нового файла
    }

    private static int beginningProgram() {
        int result = 0;
        boolean checkCorrectChoice = true;

        try {
            System.out.println(USER_CHOICE);
            Thread.sleep(350);
            System.out.println(USER_CHOICE2);
            Thread.sleep(350);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (checkCorrectChoice) {
            Scanner scanner = new Scanner(System.in); // A special object with a parameter to read from the console
            System.out.print(USER_CHOICE3);
            if (scanner.hasNextInt()) {
                result = scanner.nextInt();
                if (result == 1 || result == 2 || result == 3) {
                    checkCorrectChoice = false;
                } else {
                    System.out.println("\n You've made the wrong choice! Try again");
                }
            } else {
                System.out.println("You can only enter numbers! Try again");
            }
        }
        return result;
    }

    private static Cracker turnInMode(int userChoice) {
        return switch (userChoice) {
            case 1 -> new Decipher();
            case 2 -> new Encryption();
            default -> new BruteForce();
        };
    }

//    private static void workProgram(Cracker mode) {
//        ByteBuffer bufferWritten;
//        ByteBuffer byteBuffer = readerUserFile();
//
//        if (mode instanceof Encryption) {
//            Encryption encryption = (Encryption) mode;
//            byteBuffer.flip();
//            encryption.encrypt(byteBuffer);
//        }
//    }

    public static ByteBuffer readerUserFile() {
        String messageForUser;
        String filePathForRead;
        ByteBuffer buffer = null;
        int textModeForWrongChoice = 0;

        do {
            messageForUser = textModeForWrongChoice == 0 ? "Enter file path: " : "The file does not exist! Try again \n" +
                    "Enter file path: ";
            System.out.print(messageForUser);
            Scanner scanner = new Scanner(System.in);
            filePathForRead = scanner.nextLine();
            textModeForWrongChoice = 1;
        } while (!(Files.exists(Paths.get(filePathForRead))));

        try (FileChannel fileChannel = FileChannel.open(Paths.get(filePathForRead), StandardOpenOption.READ)) {
            // Получаем размер файла для создания буфера нужного размера
            long fileSize = fileChannel.size();
            buffer = ByteBuffer.allocate((int) fileSize);

            // Чтение данных из файла в буфер
            int bytesRead = fileChannel.read(buffer);
            while (bytesRead != -1 && buffer.hasRemaining()) {
                bytesRead = fileChannel.read(buffer);
            }

            // Подготавливаем буфер для чтения
            buffer.flip();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer; // Возвращаем полученный ByteBuffer из метода
    }
}

//        Path path = Paths.get(filePathForRead);
//        if (Files.exists(path) && Files.isRegularFile(path)) {
//            // Подключение файла для чтения с помощью BufferedReader
//            try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
//
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//
//                    // Здесь вы можете обработать содержимое файла
//
//                    System.out.println(line);
//                }
//            } catch (IOException e) {
//                System.out.println("Error while reading file: " + e.getMessage());
//            }
//        } else {
//            System.out.println("The file does not exist or is not a file. Try again ");
//            readerUserFile();
//        }
//    }
//}


//        try (RandomAccessFile sourceFile = new RandomAccessFile(path.toFile(), "r");
//             FileChannel sourceChannel = sourceFile.getChannel()) {
//
//            // Получаем размер файла для создания буфера нужного размера
//            long fileSize = sourceChannel.size();
//            buffer = ByteBuffer.allocate((int) fileSize);
//
//            // Чтение данных из источника в буфер
//            int bytesRead = sourceChannel.read(buffer);
//            while (buffer.hasRemaining()) {
//                byte b = buffer.get();
//                System.out.print((char) b);
//            }
//            while (bytesRead != -1 && buffer.hasRemaining()) {
//                bytesRead = sourceChannel.read(buffer);
//                buffer.flip();
//
//                // Читаем и выводим содержимое буфера на экран
//                while (buffer.hasRemaining()) {
//                    byte b = buffer.get();
//                    System.out.print((char) b);
//                }
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return buffer;
//    }



