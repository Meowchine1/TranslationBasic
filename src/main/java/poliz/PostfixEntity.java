package poliz;

public class PostfixEntity {
    private EntityType type;
    private int ID;

    public EntityType getType() {
        return type;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public PostfixEntity(EntityType type, int ID) {
        this.type = type;
        this.ID = ID;
    }



}
