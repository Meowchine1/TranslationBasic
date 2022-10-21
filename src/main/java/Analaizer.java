
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
    private String buffer = ""; // �����
    private int i = 0; // ������� ������ ������� �������
    private char ch; // ���������� ����������, ��������� ������ ��� ����������� ������ �������� ���������
    private String strToken; // ������ ��������, � ������� �������� ������ �������� �����
    private ArrayList<String> errors = new ArrayList <> (); // ������ ������
    private ArrayList<String> codeLists = new ArrayList<>();
    /**
     * ��������� ��������� ���� � �����
     * @param fileSrc ���� � ����� ��� ������
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
     * ������� ������������ �������
     * @throws IOException
     */
    public void analyze() throws IOException
    {
        strToken = "";

        BIG_LOOP:
        for(int row = 0; row < codeLists.size(); row++)
        {
            buffer = codeLists.get(row); // ������� ������
            i = 0;
            // System.out.println (row + "" + buffer.length ()); // ������������ ��� ������������, ���������� ����� ������ ������ ����
            while(i < buffer.length())
            {
                strToken = "";
                getChar();
                skipWhiteCharacter (); // ���������� ������� �������
                if (isLetter (ch)) // ���� ch - �����
                {
                    while (isLetterOrDigit (ch)) // ���������� ����� ����� ���������� � ���� � ��������� �����
                    {
                        strToken += ch; // ���������� �����������
                        getChar();
                    }
                    retract (); // ����������� �� ����� � ����� ��������� � ���������
                    if(grammar.isKeyWord(strToken)){
                        leksemInput.add(grammar.getKeyword(strToken)); // strToken - �������� �����
                    }

                    else{
                        grammar.addIdentifier(strToken);  // strToken - ��� ����� �������������
                        leksemInput.add(grammar.getIdentifier(strToken));
                    }

                    strToken = "";
                }
                else if (isDigit (ch)) // ���� ch - �����
                {
                    while (isDigit (ch)) // ������ ����� �����
                    {
                        strToken += ch; // ���������� �����������
                        getChar();
                    }
                    // ����� ������ �� �����
                    if (! isLetter (ch)) // ���� ����� ����������
                    {
                        retract (); // �����������, ����� ���������
                        grammar.addConstant(strToken);
                        leksemInput.add(grammar.getConstant(strToken));
                    }
                    else{
                        // ����������
                    }

                    strToken = "";
                }

                else if (operatorsPattern.matcher(String.valueOf(ch)).find()) // �������� �����������
                {
                    while(operatorsPattern.matcher(String.valueOf(ch)).find()){//  =�� ������ ��������
                        strToken += ch;
                        getChar();
                    }
                    retract();
                    leksemInput.add(grammar.getOperator(strToken));
                }

                else if (grammar.isSeparators (ch)) // ���� ��� �����������
                {
                    writeFile("separators", "" + ch);
                }

                else if(ch == '\n' || ch == '\r'){
                    //
                }

                else
                {
                writeFile("error", "" + ch);
                if (ch == ' ') // ���� ����� �������� �������� ��� ��� �������������� �������, ������� ������ �� ����� ���� ��������������
                {

                }
                else // � ������� ������ ������������ �������
                    errors.add(new String("At line " + (row + 1) + ": unable to identify \'" + ch + "\'."));
            }
            }
        }
    }

    /**
     * �������� ��������� �������� ������ � ch � ����������� ��������� ������ �� ���� ������ ������
     * (����������� ������ ������������ ����� �� ���������� �����)
     */
    public void getChar()
    {
        ch = buffer.charAt(i++);
    }

    /**
     * ���������, �������� �� ������ � ch ������, ���� ��, ����������� �������� getChar (), ���� �� ����� ��������� �������� ������
     */
    public void skipWhiteCharacter()
    {
        while (Character.isWhitespace (ch) && i <buffer.length () - 2) // ����������, �������� �� ��� ������ ��������. ������� ������� �����, �������, ���������
            getChar();
    }

    /**
     * ������������ � ���������: �������� ����� ���������� ������ �� ������� ������� � ����� �������� ch �� ������ �����
     * (����������� ������ ������������ ����� �� ���������� �����)
     */
    public void retract()
    {
        i--;
        ch = ' ';
    }

    /**
     * �������� ���� �� ��������� �������
     * @param type ���������� ���
     * @param s ������� ������
     */
    public void writeFile(String type, String s)
    {

    }

    /**
     * �������� ������ ������
     * @return ������ ������ (����� ������)
     */
    public String getErrors()
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.errors.size(); i++)
            sb.append (this.errors.get (i) + "\n"); // ���������� ��� ��������� �� ������� � ���� (�������������) ������

        return sb.toString();
    }
}