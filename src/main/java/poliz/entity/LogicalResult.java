package poliz.entity;

public class LogicalResult implements Entity{
   private boolean value;

    public LogicalResult(boolean value) {
        this.value = value;
    }

    @Override
    public EntityType getEntityType() {
        return null;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public CommandType getCommandType() {
        return null;
    }

    @Override
    public void setPtr(int ptr) {

    }

    @Override
    public int getPtr() {
        return 0;
    }

    @Override
    public int getIntValue() {
        return 0;
    }

    @Override
    public boolean getBoolValue() {
        return value;
    }
}
