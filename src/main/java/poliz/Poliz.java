package poliz;

import java.util.ArrayList;

import static poliz.entity.EntityType.*;
import alphabet.Grammar;
import poliz.entity.CommandType;
import poliz.entity.EntityType;
import poliz.entity.PostfixEntity;

public class Poliz {
    private Grammar grammar;
    private ArrayList<PostfixEntity> poliz;
    private int emptyIndexForCmd = 0;

    public ArrayList<PostfixEntity> getPoliz() {
        return poliz;
    }

    public void print(){
        for(PostfixEntity entry: poliz){

            if(entry.getCommandType() != CommandType.INDEFINITE){
                System.out.println( poliz.indexOf(entry) + "]  cmdType is " + entry.getCommandType() + "  id"+
                        entry.getID());
            }
            else{
                if(entry.getEntityType() == CMD_PTR){
                    System.out.println( poliz.indexOf(entry) + "]  cmdType is " + entry.getCommandType() + "  id"+
                            " PTR = " + entry.getPtr());
                }

                if(entry.getEntityType() == EntityType.VAR){
                    System.out.println(poliz.indexOf(entry) + "]  "
                            + grammar.getIdentifier(entry.getID()).getSymbol());
                    grammar.getIdentifier(entry.getID()).getSymbol();
                } else if (entry.getEntityType() == CONST) {
                    System.out.println(poliz.indexOf(entry) + "]  "
                            + grammar.getConstant(entry.getID()).getSymbol());
                }

            }

        }

    }

    public Poliz(Grammar grammar) {
        poliz = new ArrayList<>();
        this.grammar = grammar;
    }

    public int WriteCmd(CommandType cmd){
        PostfixEntity command = new PostfixEntity(CMD, cmd, emptyIndexForCmd);
        poliz.add(command);
        return poliz.indexOf(command);
    }

    public int WriteVar(int id){
        PostfixEntity var = new PostfixEntity(VAR, CommandType.INDEFINITE,  id);
        poliz.add(var);
        return poliz.indexOf(var);
    }

    public int WriteConst(int id){
        PostfixEntity constant = new PostfixEntity(CONST, CommandType.INDEFINITE, id);
        poliz.add(constant);
        return poliz.indexOf(constant);
    }

    public int WriteCmdPtr(int ptr){
        PostfixEntity cmdPtr = new PostfixEntity(CMD_PTR, CommandType.INDEFINITE, ptr);
        poliz.add(cmdPtr);
        return poliz.indexOf(cmdPtr);
    }

    public void SetCmdPtr(int ind, int ptr){
        poliz.get(ind).setPtr(ptr);
    }

    public int getPostfixSize() {
        return 0;
    }
}
