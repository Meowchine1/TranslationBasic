package poliz.entity;

public interface Entity {

    public EntityType getEntityType();
    public int getID();
    public CommandType getCommandType();
    public void setPtr(int ptr);
    public int getPtr();
    public int getIntValue();
    public boolean getBoolValue();
}
