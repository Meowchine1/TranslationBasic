package poliz;

import alphabet.Grammar;
import alphabet.entity.Leksem;
import alphabet.entity.LeksemType;
import exceptions.*;
import poliz.entity.CommandType;

public class RecursiveMetodi4ka {

    private Grammar grammar;
    public Poliz poliz;

    private Boolean posExist(){
        return pos < tokens.length;
    }

    private final Leksem[] tokens;
    private int pos = 0; // индекс текущего токена

    public RecursiveMetodi4ka(Leksem[] tokens, Grammar grammar) {
        this.tokens = tokens;
        this.grammar = grammar;
        poliz = new Poliz(this.grammar);
    }

    public Boolean DoUntilStatement() throws DoException, UntilException, ConstOrIdException, IdException,
            ConditionExpectedException, StatementExpectedException, LoopExpectedException, UnexpectedSymbols,
            AsException, IndefiniteCommandException {

        int addressFirst = poliz.getPostfixSize(); // address of the beginning of the cycle WRONG

        if(!posExist()){return false;}

        if (tokens[pos].getType() != LeksemType.DO)
        {
            throw new DoException("Expected <DO> position[" + pos + "]");
        }
        pos++;
        if(!posExist()){return false;}

        if (tokens[pos].getType() != LeksemType.UNTIL)
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

        if (tokens[pos].getType() != LeksemType.LOOP)
        {
            throw new LoopExpectedException("Expected <LOOP> position[" + pos + "]");
        }
        pos++;


        int indLast =  poliz.WriteCmd(CommandType.JMP); //заносим команду безусловного
        poliz.WriteCmdPtr(addressFirst); //заносим адрес начала цикла
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

        if (tokens[pos].getType() == LeksemType.REL) {  // если оператор,
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
            pos++;
            boolean operandExist = Operand();
            poliz.WriteCmd(cmdType);
            return operandExist; // то ожидаем константу или ид
        }
        return true;
    }

    Boolean Operand() throws ConstOrIdException {
        if ((tokens[pos].getType() != LeksemType.IDENTIFIER)
                        && (tokens[pos].getType() != LeksemType.CONSTANT) )
        {
            throw new ConstOrIdException("<Const> or <ID> expected position[" + pos + "]");
        }

        if(tokens[pos].getType() == LeksemType.IDENTIFIER){
            poliz.WriteVar(tokens[pos].getId());
        }
        else{
            poliz.WriteConst(tokens[pos].getId());
        }

        pos++; // если идентификатор или константа --> продолжаем анализ
        //<rel>

        return posExist();
    }

    Boolean Statement() throws IdException, ConstOrIdException, AsException, IndefiniteCommandException {
        if (tokens[pos].getType() != LeksemType.IDENTIFIER) {
            throw new IdException("<ID> expected position[" + pos + "]");
        }
        poliz.WriteVar(tokens[pos].getId());
        pos++;
        if (tokens[pos].getType() != LeksemType.AS) {
            throw new AsException("<as> expected position[" + pos + "]");
        }

        pos++;
        boolean arithExpr = ArithExpr();
        poliz.WriteCmd(CommandType.SET);


        return arithExpr;
    }
    Boolean ArithExpr() throws ConstOrIdException, IndefiniteCommandException {
        if (!Operand()){
            return false;
        }
        while (posExist() && tokens[pos].getType() == LeksemType.AO) {
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

            if(cmdType != CommandType.INDEFINITE){
                poliz.WriteCmd(cmdType);
            }
            else{
                throw new IndefiniteCommandException();
            }
        }
        return true;
    }
    
}
