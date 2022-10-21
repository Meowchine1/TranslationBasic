import java.io.IOException;

public class Main {
public static int id = 0;
    public static void main(String[] args) throws IOException {
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
        analaizer.print();

    }
}
