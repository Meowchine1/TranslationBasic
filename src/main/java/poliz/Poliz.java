package poliz;

import java.util.ArrayList;
import java.util.Stack;

import static poliz.EntityType.*;
import alphabet.Grammar;

public class Poliz {


    private Grammar grammar;
    private ArrayList<PostfixEntity> array; // string      stack -- var const m   cmd -- list
    private int emptyIndexForCmd = 0;
    public void print(){
        for(PostfixEntity entry: array){

            if(entry.getCommandType() != CommandType.INDEFINITE){
                System.out.println( array.indexOf(entry) + "]  cmdType is " + entry.getCommandType() + "  id"+
                        entry.getID());
            }
            else{
                if(entry.getType() == CMD_PTR){
                    System.out.println( array.indexOf(entry) + "]  cmdType is " + entry.getCommandType() + "  id"+
                            " PTR = " + entry.getPtr());
                }

                if(entry.getType() == EntityType.VAR){
                    System.out.println(array.indexOf(entry) + "]  "
                            + grammar.getIdentifier(entry.getID()).getSymbol());
                    grammar.getIdentifier(entry.getID()).getSymbol();
                } else if (entry.getType() == CONST) {
                    System.out.println(array.indexOf(entry) + "]  "
                            + grammar.getConstant(entry.getID()).getSymbol());
                }

            }

        }

    }

    public Poliz(Grammar grammar) {
        array = new ArrayList<>();
        this.grammar = grammar;
    }

    public int WriteCmd(CommandType cmd){
        PostfixEntity command = new PostfixEntity(CMD, cmd, emptyIndexForCmd);
        array.add(command);
        return array.indexOf(command);
    }

    public int WriteVar(int id){
        PostfixEntity var = new PostfixEntity(VAR, CommandType.INDEFINITE,  id);
        array.add(var);
        return array.indexOf(var);
    }

    public int WriteConst(int id){
        PostfixEntity constant = new PostfixEntity(CONST, CommandType.INDEFINITE, id);
        array.add(constant);
        return array.indexOf(constant);
    }

    public int WriteCmdPtr(int ptr){
        PostfixEntity cmdPtr = new PostfixEntity(CMD_PTR, CommandType.INDEFINITE, ptr);
        array.add(cmdPtr);
        return array.indexOf(cmdPtr);
    }

    public void SetCmdPtr(int ind, int ptr){
        array.get(ind).setPtr(ptr);
    }

    public int getPostfixSize() {
        return 0;
    }
}
