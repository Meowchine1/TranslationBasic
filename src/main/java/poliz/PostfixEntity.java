package poliz;

public class PostfixEntity {
    private EntityType type;
    private CommandType commandType;
    private int ID;
    private int ptr;

    public EntityType getType() {
        return type;
    }

    public int getID() {
        return ID;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setPtr(int ptr) {
        this.ptr = ptr;
    }

    public int getPtr() {
        return ptr;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public PostfixEntity(EntityType type, CommandType commandType, int ID) {
        this.type = type;
        this.ID = ID;
        this.commandType = commandType;
    }



}
