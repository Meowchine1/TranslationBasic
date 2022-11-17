package alphabet.entity;

public class Leksem {
    private int id;
    private String symbol;
    private Category category;
    private LeksemType type;
    private Boolean isDelimiter;

    public Leksem(int id, String symbol, Category category, LeksemType type, Boolean isDelimiter) {
        this.id = id;
        this.symbol = symbol;
        this.category = category;
        this.type = type;
        this.isDelimiter = isDelimiter;
    }

    @Override
    public String toString() {
        return "id= " + id + " symbol = "+ symbol ;
    }

    public int getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public Category getCategory() {
        return category;
    }

    public LeksemType getType() {
        return type;
    }

    public Boolean getDelimiter() {
        return isDelimiter;
    }


}
