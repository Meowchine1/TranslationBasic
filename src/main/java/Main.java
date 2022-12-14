import alphabet.Grammar;
import alphabet.entity.Leksem;
import alphabet.reading.Analaizer;
import alphabet.reading.ReadTerminalSymbols;
import exceptions.*;
import poliz.Interpreter;
import poliz.RecursiveMetodi4ka;

import java.io.IOException;

public class Main {



    public static void main(String[] args) throws IOException, ConstValueException, IdSizeException, LoopExpectedException, ConstOrIdException, ConditionExpectedException, UnexpectedSymbols, UntilException, AsException, DoException, IdException, StatementExpectedException, IndefiniteCommandException, ConstConsistsAlphabetSymbolException, EmptyPolizException {
        String chain = "";
        ReadTerminalSymbols readTerminalSymbols = new ReadTerminalSymbols();
        for (int i = 0; i < args.length; i++) {
            if ("-d".equals(args[i])) {
                readTerminalSymbols = new ReadTerminalSymbols(args[++i]);
            } else if ("-c".equals(args[i])) {
                chain = args[++i];
            }


        }


        Grammar grammar = new Grammar(readTerminalSymbols.keyWords, readTerminalSymbols.operators);
        Analaizer analaizer = new Analaizer(grammar, chain);
        analaizer.analyze();
      //  analaizer.print();
        RecursiveMetodi4ka recursiveMetodi4ka =
                new RecursiveMetodi4ka(analaizer.getTokens().toArray(Leksem[]::new), grammar);
        recursiveMetodi4ka.DoUntilStatement();

        recursiveMetodi4ka.poliz.print();

          Interpreter interpreter = new Interpreter(recursiveMetodi4ka.poliz.getPoliz(), grammar);
          interpreter.executePoliz();
          interpreter.printSatckTrace();

    }
}
