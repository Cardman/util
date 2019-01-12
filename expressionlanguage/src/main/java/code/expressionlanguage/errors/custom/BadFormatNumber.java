package code.expressionlanguage.errors.custom;

import code.expressionlanguage.methods.Classes;
import code.util.StringList;

public class BadFormatNumber extends FoundErrorInterpret {

    private static final String BAD_FORMAT_NUMBER= "bad format number";

    private String number;

    @Override
    public String display(Classes _classes) {
        return StringList.concat(super.display(_classes),SEP_INFO,BAD_FORMAT_NUMBER,SEP_KEY_VAL,number);
    }

    public void setNumber(String _number) {
        number = _number;
    }
}
