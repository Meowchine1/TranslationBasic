import alphabet.entities.base.Category;
import alphabet.entities.base.Leksem;
import alphabet.entities.base.Type;
import java.util.ArrayList;
import java.util.Objects;
public class Grammar {
        // Массив ключевых слов
        private  ArrayList<Leksem> keyWords;
        // массив операторов
        private  ArrayList<Leksem> operators;
        // Массив разделителей
        private ArrayList<Leksem> constants = new ArrayList<>();
        //массив констант

        private ArrayList<Leksem> identifiers = new ArrayList<>();
        //массив идентификаторов

        private final char[] separators = {',', ';', '{', '}', '(', ')', '[', ']', '_', ':', '.', '"', '\\', '\'', '?'};

        public Grammar(ArrayList<Leksem> keyWords, ArrayList<Leksem> operators){

            this.keyWords = keyWords;
            this.operators = operators;
        }

        public Leksem getKeyword(String value){
            return keyWords.stream().filter(x -> x.getSymbol().equals(value)).findFirst().orElseThrow();
        }

        public Leksem getOperator(String value){
            return operators.stream().filter(x -> x.getSymbol().equals(value)).findFirst().orElseThrow();
        }

        public Leksem getConstant(String value){
            return constants.stream().filter(x -> x.getSymbol().equals(value)).findFirst().orElseThrow();
        }
        public Leksem getIdentifier(String value){
            return identifiers.stream().filter(x -> x.getSymbol().equals(value)).findFirst().orElseThrow();
        }

        public void addConstant(String value){
            Leksem constant = new Leksem(Main.id++, value, Category.CONSTANT, Type.CONSTANT, false);
            constants.add(constant);
        }

        public void addIdentifier(String value){
            Leksem identifier = new Leksem(Main.id++, value, Category.IDENTIFIER, Type.IDENTIFIER, false);
            constants.add(identifier);
        }

    /**
         * Определите, является ли это ключевым словом
         *
         * @param s         Строка для оценки
         * @return true, если это ключевое слово; false, если это не так
         */
        public boolean isKeyWord(String s)
        {
            for (Leksem keyWord : keyWords) {
                if (keyWord.getSymbol().equals(s))
                    return true;
            }
            return false;
        }

        /**
         * Определить, является ли это оператор
         * @param s Персонаж, подлежащий оценке
         * @return true, если это оператор; false, если это не так
         */
        public boolean isOperator(String s)
        {
            for (Leksem operator : operators) {
                if (Objects.equals(s, operator.getSymbol()))
                    return true;
            }
            return false;
        }

        /**
         * Определить, является ли это разделителем
         * @param ch Персонаж, подлежащий оценке
         * @return true, если это разделитель; false, если это не так
         */
        public boolean isSeparators(char ch)
        {
            for (char separator : separators) {
                if (ch == separator)
                    return true;
            }
            return false;
        }

    }
