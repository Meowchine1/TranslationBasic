import java.io.BufferedReader;
import java.io.FileReader;

class ReadFile
{
    /**
     * Прочитать файл в буфер
     * @param buffer buffer
     * @param fileSrc путь к файлу
     */
    public static void readFile(StringBuilder buffer, String fileSrc)
    {
        try(BufferedReader br = new BufferedReader(new FileReader(fileSrc)))
        {
            String temp = "";
            while((temp = br.readLine()) != null)
                buffer.append(temp).append('\n');
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
