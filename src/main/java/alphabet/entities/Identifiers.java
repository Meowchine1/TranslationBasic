package alphabet.entities;

import alphabet.entities.base.Category;
import alphabet.entities.base.Leksem;
import alphabet.entities.base.Type;

import java.util.ArrayList;

public class Identifiers  {
    public ArrayList<Leksem> identifiers = new ArrayList<>();

    public void addIdentifier(Leksem identirier){
        identifiers.add(identirier);

    }
}
