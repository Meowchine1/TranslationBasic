package alphabet.reading;

import alphabet.Grammar;
import alphabet.entity.Leksem;
import exceptions.ConstConsistsAlphabetSymbolException;
import exceptions.ConstValueException;
import exceptions.IdSizeException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static java.lang.Character.*;

public class Analaizer
{
    private Grammar grammar;
    public static Pattern operatorsPattern = Pattern.compile("[+=><-]");
    private ArrayList<Leksem> tokens = new ArrayList<>();
    private String buffer = "";
    private int i = 0;
    private char ch;
    private String strToken;
    private ArrayList<String> errors = new ArrayList <> ();
    private ArrayList<String> codeLists = new ArrayList<>();

    public ArrayList<Leksem> getTokens(){
        return tokens;
    }

    public Analaizer(Grammar grammar, String fileSrc) throws IOException {
        this.grammar = grammar;
        ReadFile.readFile(codeLists, fileSrc);
    }

    public void print(){
        for(Leksem l : tokens){
            System.out.println(l.toString());
        }
    }

    public void analyze() throws IOException, IdSizeException, ConstValueException, ConstConsistsAlphabetSymbolException {
        strToken = "";

        BIG_LOOP:
        for(int row = 0; row < codeLists.size(); row++)
        {
            buffer = codeLists.get(row);
            i = 0;
            while(i < buffer.length())
            {
                strToken = "";
                getChar();
                skipWhiteCharacter ();
                if (isLetter (ch))
                {
                    while (isLetterOrDigit (ch))
                    {
                        strToken += ch;
                        getChar();
                    }
                    retract ();
                    if(grammar.isKeyWord(strToken)){
                        tokens.add(grammar.getKeyword(strToken));
                    }
                    else{
                        grammar.addIdentifier(strToken);
                        tokens.add(grammar.getIdentifier(strToken));
                    }
                    strToken = "";
                }
                else if (isDigit (ch))
                {
                    while (isDigit (ch))
                    {
                        strToken += ch;
                        getChar();
                    }

                    if (!isLetter (ch))
                    {
                        retract ();
                        grammar.addConstant(strToken);
                        tokens.add(grammar.getConstant(strToken));

                    }
                    else{
                        throw new ConstConsistsAlphabetSymbolException();
                    }

                    strToken = "";
                }

                else if (operatorsPattern.matcher(String.valueOf(ch)).find())
                {
                    while(operatorsPattern.matcher(String.valueOf(ch)).find()){
                        strToken += ch;
                        getChar();
                    }
                    retract();
                    tokens.add(grammar.getOperator(strToken));
                }

                else if (grammar.isSeparators (ch))
                {
                    writeFile("separators", "" + ch);
                }

                else if(ch == '\n' || ch == '\r'){
                    //
                }

                else
                {
                writeFile("error", "" + ch);
                if (ch == ' ')
                {
                    //???
                }
                else
                    errors.add(new String("At line " + (row + 1) + ": unable to identify \'" + ch + "\'."));
            }
            }
        }
    }

    public void getChar()
    {
        ch = buffer.charAt(i++);
    }
    public void skipWhiteCharacter()
    {
        while (Character.isWhitespace (ch) && i <buffer.length () - 2) // ??????????, ???????? ?? ??? ?????? ????????. ??????? ??????? ?????, ???????, ?????????
            getChar();
    }

    public void retract()
    {
        i--;
        ch = ' ';
    }

    public void writeFile(String type, String s)
    {

    }

    public String getErrors()
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.errors.size(); i++)
            sb.append (this.errors.get (i) + "\n");
        return sb.toString();
    }
}