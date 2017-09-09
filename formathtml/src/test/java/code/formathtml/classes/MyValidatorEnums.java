package code.formathtml.classes;
import org.w3c.dom.Node;

import code.bean.validator.Validator;
import code.bean.validator.ValidatorException;

public class MyValidatorEnums implements Validator {

    private static final String BAD_SELECTION = "Bad selection";

    @Override
    public void validate(Object _navigation, Node _node, Object _value) {
        if (_value instanceof EnumNumbers) {
            for (Object o: (EnumNumbers) _value) {
                if (o == EnumNumber.FOUR) {
                    throw new ValidatorException(BAD_SELECTION);
                }
            }
        }
    }

}
