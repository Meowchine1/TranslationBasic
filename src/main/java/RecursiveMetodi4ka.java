import alphabet.entities.base.Leksem;
import alphabet.entities.base.Type;

public class RecursiveMetodi4ka {

    /*
Hотация Бэкуса-Наура:
    <DoUntilStatement>  -> do until <Condition>  <Statement>  loop
<Condition>     → <Operand>|<Operand> rel <Operand>
<Operand>       → ID | CONST
<Statement>     → ID = <ArithExpr>
<ArithExpr>     → <Operand> | <ArithExpr> ao <Operand>


*/
    private Boolean posExist(){
        return pos < tokens.length;
    }

    private final Leksem[] tokens;
    private int pos = 0; // индекс текущего токена

    public RecursiveMetodi4ka(Leksem[] tokens) {
        this.tokens = tokens;
    }

    Boolean DoUntilStatement()
    {
        if(!posExist()){return false;}

        if (tokens[pos].getType().compareTo(Type.DO) == 0 )
        {
            System.out.println("Ожидается do" + pos);
            return false;
        }
        pos++;
        if(!posExist()){return false;}

        if (tokens[pos].getType().compareTo(Type.UNTIL) == 0)
        {
            System.out.println("Ожидается until" + pos);
            return false;
        }

        pos++;
        if(!posExist()){return false;}

        if (!Condition())
            return false;

        if (!Statement()) return false;

        if (tokens[pos].getType().compareTo(Type.LOOP) == 0)
        {
            System.out.println("Ожидается loop" + pos);
            return false;
        }
        pos++;

        if (posExist()) {
            System.out.println("Лишние символы" + pos);
            return false;
        }
        return true;
    }
    Boolean Condition()
    {
        if (!Operand()) {
            return false;
        }
        if (tokens[pos].getType().compareTo(Type.REL) > 0) {  // если оператор,
            pos++;
            return Operand(); // то ожидаем константу или ид
        }
        return true;
    }
    Boolean Operand()
    {
        if ((tokens[pos].getType().compareTo(Type.IDENTIFIER) == 0
                        && tokens[pos].getType().compareTo(Type.CONSTANT) == 0 ))
        {
            System.out.println("Ожидается переменная или константа " + pos);
            return false;
        }
        pos++; // если идентификатор или константа --> продолжаем анализ
        return posExist();
    }

    Boolean Statement()
    {
        if (tokens[pos].getType().compareTo(Type.IDENTIFIER) == 0) {
            System.out.println("Ожидается переменная " + pos);
            return false;
        }
        pos++;
        if (tokens[pos].getType().compareTo(Type.AS) == 0) {
            System.out.println("Ожидается присваивание " + pos);
            return false; }
        pos++;

        return ArithExpr();
    }
    Boolean ArithExpr()
    {
        if (!Operand()){
            return false;
        }
        while (posExist() && tokens[pos].getType().compareTo(Type.AO) > 0) {
            pos++;
            if (!Operand()) {
                return false;
            }
        }
        return true;
    }
    
}
