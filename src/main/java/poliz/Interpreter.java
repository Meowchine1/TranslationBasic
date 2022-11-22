package poliz;

import alphabet.Grammar;
import alphabet.entity.Leksem;
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
                                    printStackTrace();
                                    break;
                                case JZ:
                                    tmp = stack.pop().getIntValue();// 12
                                    if(stack.pop().getBoolValue()){
                                        pos++;
                                    }
                                    else{
                                        pos = tmp;
                                    }
                                    printStackTrace();
                                    break;
                                case L:
                                    pushVal(popVal(stack.pop()) < popVal(stack.pop()));
                                    pos++;
                                    printStackTrace();
                                    break;
                                case LE:
                                    pushVal(popVal(stack.pop()) <= popVal(stack.pop()));
                                    pos++;
                                    printStackTrace();
                                    break;
                                case G:
                                    pushVal(popVal(stack.pop()) > popVal(stack.pop()));
                                    pos++;
                                    printStackTrace();
                                    break;
                                case GE:
                                    pushVal(popVal(stack.pop()) >= popVal(stack.pop()));
                                    pos++;
                                    printStackTrace();
                                    break;
                                case E:
                                    pushVal(popVal(stack.pop()) == popVal(stack.pop()));
                                    pos++;
                                    printStackTrace();
                                    break;
                                case NE:
                                  pushVal(popVal(stack.pop()) != popVal(stack.pop()));
                                    pos++;
                                    printStackTrace();
                                    break;
                                case ADD:
                                    pushVal(popVal(stack.pop()) + popVal(stack.pop()));
                                    pos++;
                                    printStackTrace();
                                    break;
                                case SUB:
                                    pushVal( - popVal(stack.pop()) + popVal(stack.pop()));
                                    pos++;
                                    printStackTrace();
                                    break;
                                case SET:
                                    setVarAndPop(stack.pop().getIntValue());
                                    printStackTrace();
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
                    printStackTrace();
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

    private void printStackTrace(){

        System.out.println("шаг " + i++ );
        for(Entity e : stack){
            System.out.print("[" + e.getValue() + "] ");
        }
        System.out.println("\n_____");
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
