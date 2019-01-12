package code.expressionlanguage.errors.custom;
import code.expressionlanguage.methods.Classes;
import code.util.StringList;

public final class BadNumberArgMethod extends FoundErrorInterpret {

    private static final String CLASS_NAME = "bad matching arguments number; type:{0} variables:{1} in {2}";

    private String id;

    private int nbTypes;

    private int nbVars;

    @Override
    public String display(Classes _classes) {
        String message_ = StringList.simpleStringsFormat(CLASS_NAME, Long.toString(nbTypes), Long.toString(nbVars), id);
        return StringList.concat(super.display(_classes),message_,SEP_INFO);
    }

    public void setId(String _id) {
        id = _id;
    }

    public void setNbTypes(int _nbTypes) {
        nbTypes = _nbTypes;
    }

    public void setNbVars(int _nbVars) {
        nbVars = _nbVars;
    }

}
