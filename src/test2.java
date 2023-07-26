
import com.javarush.ivovnyanko.project_1.Cracker;
import com.javarush.ivovnyanko.project_1.Map;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class test2 implements Cracker { // шифрование
    private static final String ENCRYPTED_FILE_PROMPT = "Create a new encrypted message file: ";
    private static final String FILE_EXISTS_PROMPT = "The file exists! Try again\nEnter file path: ";
    ArrayList<Character> map = Map.map();





    public void encrypt(ByteBuffer byteBuffer) {
        for (Character t : map) {
            System.out.println(t);
        }
        Character encryptSymbol;
        int textModeForWrongChoice = 0;
        String filePathForWrite;

        do {
            String messageForUser = textModeForWrongChoice == 0 ? ENCRYPTED_FILE_PROMPT : FILE_EXISTS_PROMPT;
            System.out.print(messageForUser);
            Scanner scanner = new Scanner(System.in);
            filePathForWrite = scanner.nextLine();
            textModeForWrongChoice = 1;
        } while (Files.exists(Paths.get(filePathForWrite)));

        try (FileChannel fileChannel = FileChannel.open(Paths.get(filePathForWrite), StandardOpenOption.CREATE,
                StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
            while (byteBuffer.hasRemaining()) {
                char symbol = (char) byteBuffer.get();
                if (map.contains(symbol)) {
                    int indexSymbol = map.indexOf(symbol);
                    if (indexSymbol < map.size() - 3) {
                        encryptSymbol = map.get(indexSymbol + 3);
                    } else if (indexSymbol == map.size() - 3) {
                        encryptSymbol = map.get(0);
                    } else if (indexSymbol == map.size() - 2) {
                        encryptSymbol = map.get(1);
                    } else if (indexSymbol == map.size() - 1) {
                        encryptSymbol = map.get(2);
                    } else {
                        System.out.println("continue");
                        continue;
                    }

                    ByteBuffer buffer = ByteBuffer.allocate(1);
                    buffer.put((byte) encryptSymbol.charValue());
                    buffer.flip();
                    int bytesWritten = fileChannel.write(buffer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

