package code.expressionlanguage.structs;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.common.DisplayedStrings;
import code.expressionlanguage.common.NumParsers;

public final class BooleanStruct extends WithoutParentIdStruct implements DisplayableStruct,AnaDisplayableStruct {

    private static final BooleanStruct FALSE = new BooleanStruct();
    private static final BooleanStruct TRUE = new BooleanStruct();

    private BooleanStruct() {
    }

    public static BooleanStruct of(boolean _value) {
        return ofValue(_value);
    }

    private static BooleanStruct ofValue(boolean _value) {
        if (_value) {
            return TRUE;
        }
        return FALSE;
    }

    @Override
    public String getClassName(ContextEl _context) {
        return _context.getStandards().getContent().getNbAlias().getAliasBoolean();
    }

    @Override
    public StringStruct getDisplayedString(AnalyzedPageEl _an) {
        return getDisplayed(_an.getDisplayedStrings());
    }

    @Override
    public StringStruct getDisplayedString(ContextEl _an) {
        return getDisplayed(_an.getStandards().getDisplayedStrings());
    }

    private StringStruct getDisplayed(DisplayedStrings _displayedStrings) {
        if (this == TRUE) {
            return new StringStruct(_displayedStrings.getTrueString());
        }
        return new StringStruct(_displayedStrings.getFalseString());
    }

    public StringStruct exportValue() {
        if (this == TRUE) {
            return new StringStruct("1");
        }
        return new StringStruct("0");
    }

    public BooleanStruct neg() {
        if (this == TRUE) {
            return FALSE;
        }
        return TRUE;
    }

    public BooleanStruct and(BooleanStruct _other) {
        if (this == FALSE) {
            return FALSE;
        }
        return _other;
    }

    public BooleanStruct or(BooleanStruct _other) {
        if (this == TRUE) {
            return TRUE;
        }
        return _other;
    }

    @Override
    public long randCode() {
        return NumParsers.randCode(this);
    }
    public static boolean isTrue(Struct _other) {
        return _other == TRUE;
    }

    public static boolean isFalse(Struct _other) {
        return _other == FALSE;
    }


}
