package poliz;

import java.util.ArrayList;
import java.util.Stack;

public class Poliz {

    private Stack<PostfixEntity> stack;
    private ArrayList<PostfixEntity> arrayList;

    public void print(){
        for(PostfixEntity entry: stack){
            System.out.println(entry.getType()+ "  id = " +  entry.getID());

        }

    }

    public Poliz() {
        stack = new Stack<>();
        arrayList = new ArrayList<>();
    }

    public int WriteCmd(CommandType cmd){
        PostfixEntity command = new PostfixEntity(EntityType.CMD, ind);//what index
        stack.add(command);
        return stack.indexOf(command);
    }

    public int WriteVar(int ind){
        PostfixEntity var = new PostfixEntity(EntityType.VAR, ind);

        return var.getID();
    }

    public int WriteConst(int ind){
        PostfixEntity constant = new PostfixEntity(EntityType.CONST,ind);
        return constant.getID();
    }

    public int WriteCmdPtr(int ptr){
        PostfixEntity cmdPtr = new PostfixEntity(EntityType.CMD_PTR, ptr);
        arrayList.add(cmdPtr);
        return cmdPtr.getID();
    }

    public void SetCmdPtr(int ind, int ptr){
        arrayList.get(ind).setID(ptr);
    }
}
