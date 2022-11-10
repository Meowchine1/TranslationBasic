import alphabet.entities.base.Leksem;
import alphabet.entities.base.Type;
import exceptions.*;
import poliz.CommandType;
import poliz.Poliz;

public class RecursiveMetodi4ka {

    protected Poliz poliz = new Poliz();

    private Boolean posExist(){
        return pos < tokens.length;
    }

    private final Leksem[] tokens;
    private int pos = 0; // индекс текущего токена

    public RecursiveMetodi4ka(Leksem[] tokens) {
        this.tokens = tokens;
    }

    public Boolean DoUntilStatement() throws DoException, UntilException, ConstOrIdException, IdException,
            ConditionExpectedException, StatementExpectedException, LoopExpectedException, UnexpectedSymbols,
            AsException, IndefiniteCommandException {

        int addresFirst = poliz.getPostfixSize(); // address of the beginning of the cycle

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

        // сформирована часть ПОЛИЗа, вычисляющая условие цикла

        int indJmp = poliz.WriteCmdPtr(-1); //заносим фиктивное значение
        //адреса условного перехода
        poliz.WriteCmd(CommandType.JZ); //заносим команду условного перехода

        if (!Statement()) {
            throw new StatementExpectedException("<Statement> expected position[" + pos + "]");
        }
        // сформирована часть ПОЛИЗа для тела цикла

        if (tokens[pos].getType() != Type.LOOP)
        {
            throw new LoopExpectedException("Expected <LOOP> position[" + pos + "]");
        }
        pos++;

        poliz.WriteCmdPtr(addresFirst); //заносим адрес начала цикла
        int indLast =  poliz.WriteCmd(CommandType.JMP); //заносим команду безусловного
        //перехода и сохраняем ее адрес
        poliz.SetCmdPtr(indJmp, indLast+1); //изменяем фиктивное значение
        //адреса условного перехода

        if (posExist()) {
            throw new UnexpectedSymbols("Unexpected symbols position[" + pos + "]");
        }
        return true;
    }

    Boolean Condition() throws ConstOrIdException, IndefiniteCommandException {
        if (!Operand()) {
            return false;
        }

        // сформирована часть ПОЛИЗа для вычисления
        // логического подвыражения

        if (tokens[pos].getType() == Type.REL) {  // если оператор,
            CommandType cmdType = CommandType.INDEFINITE;
            switch(tokens[pos].getId()){
                case(5): cmdType = CommandType.L;
                        break;
                case(7): cmdType = CommandType.G;
                        break;
                case(6): cmdType = CommandType.LE;
                        break;
                case(8): cmdType = CommandType.GE;
                        break;
                case(9): cmdType = CommandType.E;
                        break;
                case(10): cmdType = CommandType.NE;
                        break;
            }
            if(cmdType.equals(CommandType.INDEFINITE)){
                throw new IndefiniteCommandException();
            }
            poliz.WriteCmd(cmdType);
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

        if(tokens[pos].getType() == Type.IDENTIFIER){
            poliz.WriteVar(tokens[pos].getId());
        }
        else{
            poliz.WriteConst(tokens[pos].getId());
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
        poliz.WriteVar(tokens[pos].getId());
        pos++;
        if (tokens[pos].getType() != Type.AS) {
            throw new AsException("<as> expected position[" + pos + "]");
        }
        poliz.WriteCmd(CommandType.SET);
        pos++;

        return ArithExpr();
    }
    Boolean ArithExpr() throws ConstOrIdException {
        if (!Operand()){
            return false;
        }
        while (posExist() && tokens[pos].getType() == Type.AO) {
            CommandType cmdType = CommandType.INDEFINITE;
            if(tokens[pos].getId() == 12){
                cmdType = CommandType.ADD;
            }
            if(tokens[pos].getId() == 13){
                cmdType = CommandType.SUB;
            }
            pos++;
            if (!Operand()) {
                return false;
            }
            poliz.WriteCmd(cmdType);
        }
        return true;
    }
    
}
