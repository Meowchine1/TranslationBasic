package poliz.entity;

public class ArithResult implements Entity{

    private int value;

    public ArithResult(int value) {
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
        return value;
    }

    @Override
    public boolean getBoolValue() {
        return false;
    }
}
