
import alphabet.entities.base.Leksem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static java.lang.Character.*;

public class Analaizer
{
    private Grammar grammar;
    public static Pattern operatorsPattern = Pattern.compile("[+=><-]");
    private ArrayList<Leksem> leksemInput = new ArrayList<>();
    private String buffer = ""; // буфер
    private int i = 0; // Текущее чтение позиции символа
    private char ch; // Символьная переменная, сохраняем только что прочитанный символ исходной программы
    private String strToken; // Массив символов, в котором хранится строка символов слова
    private ArrayList<String> errors = new ArrayList <> (); // Список ошибок
    private ArrayList<String> codeLists = new ArrayList<>();
    /**
     * Прочитать указанный путь к файлу
     * @param fileSrc путь к файлу для чтения
     */
    public Analaizer(Grammar grammar, String fileSrc) throws IOException {
        this.grammar = grammar;
        ReadFile.readFile(codeLists, fileSrc);
    }

    public void print(){
        for(Leksem l : leksemInput){
            System.out.println(l.toString());
        }
    }

    /**
     * Процесс лексического анализа
     * @throws IOException
     */
    public void analyze() throws IOException
    {
        strToken = "";

        BIG_LOOP:
        for(int row = 0; row < codeLists.size(); row++)
        {
            buffer = codeLists.get(row); // текущая строка
            i = 0;
            // System.out.println (row + "" + buffer.length ()); // Используется при тестировании, показывает длину каждой строки кода
            while(i < buffer.length())
            {
                strToken = "";
                getChar();
                skipWhiteCharacter (); // пропускаем символы пробела
                if (isLetter (ch)) // если ch - буква
                {
                    while (isLetterOrDigit (ch)) // допустимые слова могут начинаться с букв и содержать числа
                    {
                        strToken += ch; // продолжаем накапливать
                        getChar();
                    }
                    retract (); // Выскакиваем из цикла и нужно вернуться к персонажу
                    if(grammar.isKeyWord(strToken)){
                        leksemInput.add(grammar.getKeyword(strToken)); // strToken - ключевое слово
                    }

                    else{
                        grammar.addIdentifier(strToken);  // strToken - это общий идентификатор
                        leksemInput.add(grammar.getIdentifier(strToken));
                    }

                    strToken = "";
                }
                else if (isDigit (ch)) // если ch - число
                {
                    while (isDigit (ch)) // читаем целое число
                    {
                        strToken += ch; // продолжаем накапливать
                        getChar();
                    }
                    // после выхода из цикла
                    if (! isLetter (ch)) // Если номер начинается
                    {
                        retract (); // недопустимо, нужно вернуться
                        grammar.addConstant(strToken);
                        leksemInput.add(grammar.getConstant(strToken));
                    }
                    else{
                        // исключение
                    }

                    strToken = "";
                }

                else if (operatorsPattern.matcher(String.valueOf(ch)).find()) // оператор разделители
                {
                    while(operatorsPattern.matcher(String.valueOf(ch)).find()){//  =не должен заходить
                        strToken += ch;
                        getChar();
                    }
                    retract();
                    leksemInput.add(grammar.getOperator(strToken));
                }

                else if (grammar.isSeparators (ch)) // если это разделитель
                {
                    writeFile("separators", "" + ch);
                }

                else if(ch == '\n' || ch == '\r'){
                    //
                }

                else
                {
                writeFile("error", "" + ch);
                if (ch == ' ') // Если после пропуска пробелов все еще обнаруживаются пробелы, текущая строка не может быть скомпилирована
                {

                }
                else // В текущей строке недопустимые символы
                    errors.add(new String("At line " + (row + 1) + ": unable to identify \'" + ch + "\'."));
            }
            }
        }
    }

    /**
     * Прочтите следующий вводимый символ в ch и переместите индикатор поиска на один символ вперед
     * (Виртуальная машина оптимизирует метод во встроенный метод)
     */
    public void getChar()
    {
        ch = buffer.charAt(i++);
    }

    /**
     * Проверьте, является ли символ в ch пустым, если да, продолжайте вызывать getChar (), пока не будет обнаружен непустой символ
     */
    public void skipWhiteCharacter()
    {
        while (Character.isWhitespace (ch) && i <buffer.length () - 2) // Определяем, является ли это пустым символом. Включая разрывы строк, пробелы, табуляции
            getChar();
    }

    /**
     * Отслеживание с возвратом: обратный вызов индикатора поиска на позицию символа и сброс значения ch на пустое слово
     * (Виртуальная машина оптимизирует метод во встроенный метод)
     */
    public void retract()
    {
        i--;
        ch = ' ';
    }

    /**
     * Запишите файл по бинарному правилу
     * @param type символьный тип
     * @param s текущий символ
     */
    public void writeFile(String type, String s)
    {

    }

    /**
     * Получить список ошибок
     * @return список ошибок (целая строка)
     */
    public String getErrors()
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.errors.size(); i++)
            sb.append (this.errors.get (i) + "\n"); // Объединяем все сообщения об ошибках в одну (многострочную) строку

        return sb.toString();
    }
}