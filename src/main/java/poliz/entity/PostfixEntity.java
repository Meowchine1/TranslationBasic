package poliz.entity;

public class PostfixEntity implements Entity{
    private EntityType entityType;
    private CommandType commandType;
    private int ID;
    private int ptr;


    public EntityType getEntityType() {
        return entityType;
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

    @Override
    public int getIntValue() {
        return 0;
    }

    @Override
    public boolean getBoolValue() {
        return false;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public PostfixEntity(EntityType entityType, CommandType commandType, int ID) {
        this.entityType = entityType;
        this.ID = ID;
        this.commandType = commandType;
    }



}
