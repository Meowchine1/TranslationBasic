package alphabet.reading;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile
{
    /**
     * Прочитать файл в буфер
     * @param buffer buffer
     * @param fileSrc путь к файлу
     */
    public static void readFile(ArrayList<String> buffer, String fileSrc) throws IOException {

       try{
           Scanner scanner = new Scanner(new File(fileSrc));
           while (scanner.hasNextLine()){
               buffer.add(scanner.nextLine() + "\n");

           }
       } catch (FileNotFoundException e) {
           throw new RuntimeException(e);
       }

    }

}
