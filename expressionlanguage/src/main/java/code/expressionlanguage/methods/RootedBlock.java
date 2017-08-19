package code.expressionlanguage.methods;
import code.util.StringList;
import code.xml.RowCol;

public interface RootedBlock extends AccessibleBlock {

    String getFullName();

    String getName();

    String getPackageName();

    StringList getAllSuperClasses();

    StringList getAllSuperTypes();

    StringList getDirectSuperClasses();

    RowCol getRowCol(int _offset, int _tabWidth,String _attribute);

    Block getFirstChild();

    boolean isFinalType();
    boolean isAbstractType();

    boolean mustImplement();
}
