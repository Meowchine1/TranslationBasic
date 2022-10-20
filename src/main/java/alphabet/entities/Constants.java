package alphabet.entities;

import alphabet.entities.base.Category;
import alphabet.entities.base.Leksem;
import alphabet.entities.base.Type;

import java.util.ArrayList;

public class Constants  {
  public ArrayList<Leksem> constants = new ArrayList<>();
  public void addConstant(Leksem constant){
      constants.add(constant);
  }
}
