package poliz;

import alphabet.Grammar;
import alphabet.entity.Leksem;
import exceptions.EmptyPolizException;
import poliz.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Interpreter {
    protected Stack<Entity> stack;
    private Grammar grammar;
    private ArrayList<PostfixEntity> poliz;
    private int pos = 0;


    public Interpreter(ArrayList<PostfixEntity> poliz, Grammar grammar) {
        this.poliz = poliz;
        this.grammar = grammar;
        stack = new Stack<>();
    }

    public void executePoliz() throws EmptyPolizException {
        if (!poliz.isEmpty()) {

            int tmp;
            boolean boolTmp;
            while(pos < poliz.size()){
                PostfixEntity entity = poliz.get(pos);
                if(entity.getEntityType() == EntityType.CMD_PTR ||
                        entity.getEntityType() ==  EntityType.CMD){

                    CommandType commandType = entity.getCommandType();
                    CommandType[] commandTypes = CommandType.values();
                    for(CommandType command : commandTypes){
                        if(command == commandType){

                            switch(command){
                                case JMP:
                                    pos  = stack.pop().getPtr();
                                    break;
                                case JZ:
                                    tmp = stack.pop().getPtr();
                                    if(stack.pop().getBoolValue()){
                                        pos++;
                                    }
                                    else{
                                        pos = tmp;
                                    }
                                    break;
                                case L:
                                    pushVal(popVal(stack.pop()) < popVal(stack.pop()));
                                    pos++;
                                    break;
                                case LE:
                                    pushVal(popVal(stack.pop()) <= popVal(stack.pop()));
                                    pos++;
                                    break;
                                case G:
                                    pushVal(popVal(stack.pop()) > popVal(stack.pop()));
                                    pos++;
                                    break;
                                case GE:
                                    pushVal(popVal(stack.pop()) >= popVal(stack.pop()));
                                    pos++;
                                    break;
                                case E:
                                    pushVal(popVal(stack.pop()) == popVal(stack.pop()));
                                    pos++;
                                    break;
                                case NE:
                                    pushVal(popVal(stack.pop()) != popVal(stack.pop()));
                                    pos++;
                                    break;
                                case ADD:
                                    pushVal(popVal(stack.pop()) + popVal(stack.pop()));
                                    pos++;
                                    break;
                                case SUB:
                                    pushVal( - popVal(stack.pop()) + popVal(stack.pop()));
                                    pos++;
                                    break;
                                case SET:
                                    setVarAndPop(stack.pop().getIntValue());
                                    pos++;
                                    break;

                            }
                        }
                }
            }
             else{

                 stack.push(entity);
                }
            }
        }
        else{
            throw new EmptyPolizException();
        }
    }

    private int popVal(Entity entity ){
        int value1 = 0;

        if(entity.getEntityType() == EntityType.VAR){
            value1 = Integer.parseInt(grammar.getIdentifier(entity.getID()).getSymbol());
        } else if (entity.getEntityType() == EntityType.CONST) {
            value1 =  Integer.parseInt(grammar.getConstant(entity.getID()).getSymbol());
        }

        return value1;
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
        grammar.setIdentifier(stack.pop().getID(), value);
    }

}
