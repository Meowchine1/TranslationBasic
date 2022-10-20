package alphabet.entities.base;

public class Leksem {
    private int id;
    private String symbol;
    private Category category;
    private Type type;
    private Boolean isDelimiter;

    public Leksem(int id, String symbol, Category category, Type type, Boolean isDelimiter) {
        this.id = id;
        this.symbol = symbol;
        this.category = category;
        this.type = type;
        this.isDelimiter = isDelimiter;
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

    public Type getType() {
        return type;
    }

    public Boolean getDelimiter() {
        return isDelimiter;
    }


}
