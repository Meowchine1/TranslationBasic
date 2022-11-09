import alphabet.entities.base.Leksem;
import alphabet.entities.base.Type;
import exceptions.ConstValueException;
import exceptions.IdSizeException;

import java.io.IOException;

public class Main {
public static int id = 0;
    public static void main(String[] args) throws IOException, ConstValueException, IdSizeException {
        String chain = "";
        ReadTerminalSymbols readTerminalSymbols = new ReadTerminalSymbols();
        for (int i = 0; i< args.length; i++){
            if ("-d".equals(args[i])) {
                readTerminalSymbols = new ReadTerminalSymbols(args[++i]);
            } else if ("-c".equals(args[i])) {
                chain = args[++i];
            }


        }


        Grammar grammar = new Grammar(readTerminalSymbols.keyWords, readTerminalSymbols.operators);
        Analaizer analaizer = new Analaizer(grammar, chain);
        analaizer.analyze();
        //analaizer.print();
        RecursiveMetodi4ka recursiveMetodi4ka =
                new RecursiveMetodi4ka(analaizer.getTokens().toArray(Leksem[]::new));
        recursiveMetodi4ka.DoUntilStatement();

    }
}
