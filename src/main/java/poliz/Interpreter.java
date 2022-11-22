package poliz;

import alphabet.Grammar;
import alphabet.entity.Leksem;
import de.vandermeer.asciitable.AsciiTable;
import exceptions.EmptyPolizException;
import poliz.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Interpreter {
    protected Stack<Entity> stack;
    private Grammar grammar;
    private ArrayList<PostfixEntity> poliz;

    private HashMap<Leksem, Integer> ident = new HashMap<>();
    private int pos = 0;

    private int i  = 0;

    private AsciiTable at = new AsciiTable();


    public Interpreter(ArrayList<PostfixEntity> poliz, Grammar grammar) {
        this.poliz = poliz;
        this.grammar = grammar;
        stack = new Stack<>();
        Scanner myInput = new Scanner( System.in );
        for(Leksem id : grammar.identifiers){
            System.out.print("enter value " + id.getSymbol() + " = "  );
            int a = myInput.nextInt();
            ident.put(id, a);
            System.out.println();

        }
    }

    public void executePoliz() throws EmptyPolizException {
        initStackTrace();
        if (!poliz.isEmpty()) {

            int tmp;
            boolean boolTmp;
            while(pos < poliz.size()){
                PostfixEntity entity = poliz.get(pos);
                if(entity.getEntityType() ==  EntityType.CMD){
                    //if enntity type ptr -- то не команда
                    CommandType commandType = entity.getCommandType();
                    CommandType[] commandTypes = CommandType.values();
                    for(CommandType command : commandTypes){
                        if(command == commandType){

                            switch(command){
                                case JMP:
                                    pos  = stack.pop().getPtr();
                                    addStackTrace();
                                    break;
                                case JZ:
                                    tmp = stack.pop().getIntValue();// 12
                                    if(stack.pop().getBoolValue()){
                                        pos++;
                                    }
                                    else{
                                        pos = tmp;
                                    }
                                    addStackTrace();
                                    break;
                                case L:
                                    pushVal(popVal(stack.pop()) < popVal(stack.pop()));
                                    pos++;
                                    addStackTrace();
                                    break;
                                case LE:
                                    pushVal(popVal(stack.pop()) <= popVal(stack.pop()));
                                    pos++;
                                    addStackTrace();
                                    break;
                                case G:
                                    pushVal(popVal(stack.pop()) > popVal(stack.pop()));
                                    pos++;
                                    addStackTrace();
                                    break;
                                case GE:
                                    pushVal(popVal(stack.pop()) >= popVal(stack.pop()));
                                    pos++;
                                    addStackTrace();
                                    break;
                                case E:
                                    pushVal(popVal(stack.pop()) == popVal(stack.pop()));
                                    pos++;
                                    addStackTrace();
                                    break;
                                case NE:
                                  pushVal(popVal(stack.pop()) != popVal(stack.pop()));
                                    pos++;
                                    addStackTrace();
                                    break;
                                case ADD:
                                    pushVal(popVal(stack.pop()) + popVal(stack.pop()));
                                    pos++;
                                    addStackTrace();
                                    break;
                                case SUB:
                                    pushVal( - popVal(stack.pop()) + popVal(stack.pop()));
                                    pos++;
                                    addStackTrace();
                                    break;
                                case SET:
                                    setVarAndPop(stack.pop().getIntValue());
                                    addStackTrace();
                                    pos++;
                                    break;
                            }
                        }
                }
            }
             else{
                    if (entity.getEntityType() == EntityType.CMD_PTR ){
                        ArithResult aa = new ArithResult(entity.getPtr());
                        stack.push(aa);
                        pos++;
                    }
                    else{
                        boolean isConst = true;
                        int id = entity.getID();
                        for(HashMap.Entry<Leksem, Integer> entry : ident.entrySet()){
                            if (entry.getKey().getId() == id){
                                ArithResult a = new ArithResult(entry.getValue());
                                stack.push(a);
                                pos++;
                                isConst = false;
                            }
                        }

                        if(isConst){
                            ArithResult aa = new ArithResult
                                    (Integer.parseInt(grammar.getConstant(entity.getID()).getSymbol()));
                            stack.push(aa);
                            pos++;
                        }
                    }
                    addStackTrace();
                }
            }
        }
        else{
            throw new EmptyPolizException();
        }
    }

    private int popVal(Entity entity){

        return Integer.parseInt(entity.getValue());
    }


    private boolean popVal(Entity entity, boolean isBool){
        return Boolean.parseBoolean(entity.getValue());
    }



    private void initStackTrace(){
        at.addRule();
        at.addRow("Шаг", "Стек", "Значение переменной");
        at.addRule();
    }

    private void addStackTrace(){
        StringBuilder stackParams = new StringBuilder();
        StringBuilder idParams = new StringBuilder();

        for(Entity e : stack){
            stackParams.append("[").append(e.getValue()).append("] ");
            //System.out.print("[" + e.getValue() + "] ");
        }
        for(HashMap.Entry<Leksem, Integer> id : ident.entrySet()){
            idParams.append("(").append(id.getKey().getSymbol()).append(" = ").append(id.getValue()).append(")");
        }

        at.addRow(i++, stackParams, idParams);
        at.addRule();

    }

    public void printSatckTrace(){
        System.out.println(at.render());
    }

    private void pushVal(int value) {
        ArithResult arithResult = new ArithResult(value);
        stack.push(arithResult);
    }

    private void pushVal(boolean value) {
        LogicalResult logicalResult= new LogicalResult(value);
        stack.push(logicalResult);
    }

    private void setVarAndPop(int value) {

        int id = stack.pop().getID();
        for(HashMap.Entry<Leksem, Integer> entry : ident.entrySet()){
            if(entry.getKey().getId() == id){
                ident.put(entry.getKey(), value);
            }

        }

    }

}
