import alphabet.entities.base.Leksem;
import alphabet.entities.base.Type;
import exceptions.*;

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

    public Boolean DoUntilStatement() throws DoException, UntilException, ConstOrIdException, IdException, ConditionExpectedException, StatementExpectedException, LoopExpectedException, UnexpectedSymbols, AsException {
        if(!posExist()){return false;}

        if (tokens[pos].getType() != Type.DO)
        {
            throw new DoException("Expected <DO> position[" + pos + "]");
        }
        pos++;
        if(!posExist()){return false;}

        if (tokens[pos].getType() != Type.UNTIL)
        {
            throw new UntilException("Expected <UNTIL> position[" + pos + "]");
        }

        pos++;
        if(!posExist()){
            return false;
        }

        if (!Condition()){
            throw new ConditionExpectedException("<Condition> expected position[" + pos + "]");
        }

        if (!Statement()) {
            throw new StatementExpectedException("<Statement> expected position[" + pos + "]");
        }


        if (tokens[pos].getType() != Type.LOOP)
        {
            throw new LoopExpectedException("Expected <LOOP> position[" + pos + "]");
        }
        pos++;

        if (posExist()) {
            throw new UnexpectedSymbols("Unexpected symbols position[" + pos + "]");
        }
        return true;
    }
    Boolean Condition() throws ConstOrIdException {
        if (!Operand()) {
            return false;
        }
        if (tokens[pos].getType() == Type.REL) {  // если оператор,
            pos++;
            return Operand(); // то ожидаем константу или ид
        }
        return true;
    }
    Boolean Operand() throws ConstOrIdException {
        if ((tokens[pos].getType() != Type.IDENTIFIER)
                        && (tokens[pos].getType() != Type.CONSTANT) )
        {
            throw new ConstOrIdException("<Const> or <ID> expected position[" + pos + "]");
        }
        pos++; // если идентификатор или константа --> продолжаем анализ
        //<rel>

        return posExist();
    }

    Boolean Rel(){
        return true;
    }

    Boolean Statement() throws IdException, ConstOrIdException, AsException {
        if (tokens[pos].getType() != Type.IDENTIFIER) {
            throw new IdException("<ID> expected position[" + pos + "]");
        }
        pos++;
        if (tokens[pos].getType() != Type.AS) {
            throw new AsException("<as> expected position[" + pos + "]");
        }
        pos++;

        return ArithExpr();
    }
    Boolean ArithExpr() throws ConstOrIdException {
        if (!Operand()){
            return false;
        }
        while (posExist() && tokens[pos].getType() == Type.AO) {
            pos++;
            if (!Operand()) {
                return false;
            }
        }
        return true;
    }
    
}
