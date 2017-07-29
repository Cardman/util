package code.expressionlanguage.methods;
import code.expressionlanguage.ContextEl;
import code.util.StringList;
import code.xml.RowCol;

public interface RootedBlock extends AccessibleBlock {

    String getFullName();

    String getName();

    String getSuperClass();

    String getPackageName();

    StringList getAllSuperClasses();

    StringList getDirectSuperClasses();

    RowCol getRowCol(int _offset, int _tabWidth,String _attribute);

    Block getFirstChild();

    void validateConstructors(ContextEl _cont);

    boolean isFinalType();
    
    boolean isAbstractType();
}
