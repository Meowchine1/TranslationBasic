package alphabet;

import alphabet.entity.Category;
import alphabet.entity.Leksem;
import alphabet.entity.LeksemType;
import exceptions.ConstValueException;
import exceptions.IdSizeException;

import java.awt.*;
import java.util.*;

public class Grammar {
        private int id = 0;
        // Массив ключевых слов
        private  static ArrayList<Leksem> keyWords;
        // массив операторов
        private  static ArrayList<Leksem> operators;
        // Массив разделителей
        private static ArrayList<Leksem> constants = new ArrayList<>();
        //массив констант
        public static ArrayList<Leksem> identifiers = new ArrayList<>();
        //массив идентификаторов



        public static ArrayList<Leksem> getIdentifiers() {
            return identifiers;
        }

    private final char[] separators = {',', ';', '{', '}', '(', ')', '[', ']', '_', ':', '.', '"', '\\', '\'', '?'};

        public Grammar(ArrayList<Leksem> keyWords, ArrayList<Leksem> operators){

            this.keyWords = keyWords;
            this.operators = operators;
        }

        public void print(){

            System.out.println("keywords");
            for(Leksem l : keyWords){
                System.out.println(l.toString());
            }

            System.out.println("\noperators");
            for(Leksem l : operators){
                System.out.println(l.toString());
            }

            System.out.println("\nconstants");
            for(Leksem l : constants){
                System.out.println(l.toString());
            }


            System.out.println("\nident");
            for (Leksem entry : identifiers){
                System.out.println(entry.toString());
            }

        }

        public Leksem getKeyword(String value){
            return keyWords.stream().filter(x -> x.getSymbol().equals(value.toLowerCase())).findFirst().orElseThrow();
        }

        public Leksem getOperator(String value){
            return operators.stream().filter(x -> x.getSymbol().equals(value)).findFirst().orElseThrow();
        }

        public Leksem getConstant(String value){
            return constants.stream().filter(x -> x.getSymbol().equals(value.toLowerCase())).findFirst().orElseThrow();
        }
        public Leksem getIdentifier(String value){
            return identifiers.stream().filter(x -> x.getSymbol().equals(value.toLowerCase())).findFirst().orElseThrow();
        }

        public Leksem getConstant(int id){
            return constants.stream().filter(x -> x.getId() == id).findFirst().orElseThrow();
        }
        public Leksem getIdentifier(int id){
            return identifiers.stream().filter(x -> x.getId() == id).findFirst().orElseThrow();
        }

        public void addConstant(String value) throws ConstValueException {
            int intValue = Integer.parseInt(value);
            if( intValue >= -32768 & intValue <= 32768 ){
                if(!constExist(value)){
                    Leksem constant = new Leksem(id++, value.toLowerCase(), Category.CONSTANT, LeksemType.CONSTANT, false);
                    constants.add(constant);
                }
            }
            else{
                 throw new ConstValueException("Const isnt in diapazone");
            }
        }

        public void addIdentifier(String value) throws IdSizeException {
            if(value.length() > 255){
                throw new IdSizeException("Id size is too big");
            }
            else{
                if(!idExist(value)){
                    Leksem identifier = new Leksem(id++, value.toLowerCase(), Category.IDENTIFIER, LeksemType.IDENTIFIER, false);
                    identifiers.add(identifier);
                }
            }
        }

        private boolean idExist(String value){
            return identifiers.stream().anyMatch(x-> x.getSymbol().equals(value.toLowerCase()));
        }

        private boolean constExist(String value){
            return constants.stream().anyMatch(x-> x.getSymbol().equals(value.toLowerCase()));
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
                if (keyWord.getSymbol().equals(s.toLowerCase()))
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
