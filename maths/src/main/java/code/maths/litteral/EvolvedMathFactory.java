package code.maths.litteral;
import code.maths.LgInt;
import code.maths.MathList;
import code.maths.Rate;
import code.util.Numbers;
import code.util.StringList;
import code.util.StringMap;
import code.util.ints.AdvancedMathFactory;

public final class EvolvedMathFactory implements AdvancedMathFactory<Rate> {

    private final LgInt maxRd = LgInt.getMaxLongPlusOne();

    public static StringList getFunctions() {
        StringList list_ = new StringList();
        list_.add(OperationNode.PUIS);
        list_.add(OperationNode.QUOT);
        list_.add(OperationNode.MOD);
        list_.add(OperationNode.MODTAUX);
        list_.add(OperationNode.ABS);
        list_.add(OperationNode.ENT);
        list_.add(OperationNode.TRONC);
        list_.add(OperationNode.NUM);
        list_.add(OperationNode.DEN);
        list_.add(OperationNode.MIN);
        list_.add(OperationNode.MAX);
        list_.add(OperationNode.MOY);
        list_.add(OperationNode.VAR);
        list_.add(OperationNode.CARAC_FERME);
        list_.add(OperationNode.CARAC_OUVERT);
        list_.add(OperationNode.CARAC_SEMI_OUVERT_G);
        list_.add(OperationNode.CARAC_SEMI_OUVERT_D);
        list_.add(OperationNode.CARAC_DROITE_OUVERT);
        list_.add(OperationNode.CARAC_DROITE_FERME);
        list_.add(OperationNode.CARAC_GAUCHE_OUVERT);
        list_.add(OperationNode.CARAC_GAUCHE_FERME);
        list_.add(OperationNode.SGN);
        list_.add(OperationNode.CARD);
        list_.add(OperationNode.INTER);
        list_.add(OperationNode.UNION);
        list_.add(OperationNode.COMPL);
        list_.add(OperationNode.INCL);
        list_.add(OperationNode.NON_INCL);
        list_.add(OperationNode.EQ_NUM);
        list_.add(OperationNode.NON_EQ_NUM);
        list_.add(OperationNode.DIV_FCT);
        return list_;
    }
    @Override
    public Rate evaluateDirectlyRate(String _numExp) {
        return (Rate) MathUtil.processEl(_numExp, 0, false, new StringMap<String>()).getObject();
    }

    @Override
    public Boolean evaluateDirectlyBoolean(String _booleanExp) {
        return (Boolean) MathUtil.processEl(_booleanExp, 0, false, new StringMap<String>()).getObject();
    }

    @Override
    public String toString(Object _gotArg) {
        if (_gotArg instanceof Boolean) {
            if ((Boolean) _gotArg) {
                return getTrueString();
            }
            return getFalseString();
        }
        if (_gotArg instanceof MathList) {
            return ((MathList)_gotArg).escapedList();
        }
        if (_gotArg instanceof LgInt) {
            return ((LgInt)_gotArg).toNumberString();
        }
        if (_gotArg instanceof Rate) {
            return ((Rate)_gotArg).toNumberString();
        }
        if (_gotArg instanceof Number) {
            return Numbers.toString((Number) _gotArg);
        }
        return "";
    }

    @Override
    public EvolvedNumString createNumericableString(String _chaineNumerique,
            StringMap<String> _vars) {
        return new EvolvedNumString(_chaineNumerique, _vars);
    }

    @Override
    public EvolvedBooleanString createBooleanString(String _chaineBooleenne,
            StringMap<String> _vars) {
        return new EvolvedBooleanString(_chaineBooleenne, _vars);
    }

    @Override
    public String getTrueString() {
        return OperationNode.TRUE_STRING;
    }

    @Override
    public String getFalseString() {
        return OperationNode.FALSE_STRING;
    }

    @Override
    public char getSepartorSetChar() {
        return OperationNode.DELIMITER_STRING_SEP;
    }

    @Override
    public Rate evaluateNumericable(String _numericString, StringMap<String> _variables,
            Rate _default) {
        Object obj_ = MathUtil.processEl(_numericString, 0, false, _variables).getObject();
        if (obj_ instanceof Rate) {
            return (Rate) obj_;
        }
        return new Rate(_default);
    }

    @Override
    public Rate evaluatePositiveOrZeroExp(String _numericString,
            StringMap<String> _variables, Rate _default) {
        Object obj_ = MathUtil.processEl(_numericString, 0, false, _variables).getObject();
        if (!(obj_ instanceof Rate)) {
            return _default.absNb();
        }
        Rate r_ = (Rate) obj_;
        if (!r_.isZeroOrGt()) {
            return _default.absNb();
        }
        return r_;
    }

    @Override
    public Rate evaluatePositiveExp(String _numericString,
            StringMap<String> _variables, Rate _default) {
        Object obj_ = MathUtil.processEl(_numericString, 0, false, _variables).getObject();
        if (!(obj_ instanceof Rate)) {
            return _default.absNb();
        }
        Rate r_ = (Rate) obj_;
        if (r_.isZero()) {
            return _default.absNb();
        }
        if (!r_.isZeroOrGt()) {
            return _default.absNb();
        }
        return r_;
    }

    @Override
    public Boolean evaluateBoolean(String _booleanString,
            StringMap<String> _variables, Boolean _default) {
        Object obj_ = MathUtil.processEl(_booleanString, 0, false, _variables).getObject();
        if (!(obj_ instanceof Boolean)) {
            return _default;
        }
        return (Boolean)obj_;
    }
    public LgInt getMaxRandomNb() {
        return maxRd;
    }
}
