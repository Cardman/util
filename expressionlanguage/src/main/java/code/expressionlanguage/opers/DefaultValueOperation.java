package code.expressionlanguage.opers;

import code.expressionlanguage.Analyzable;
import code.expressionlanguage.Argument;
import code.expressionlanguage.inherits.PrimitiveTypeUtil;
import code.expressionlanguage.inherits.Templates;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.opers.exec.Operable;
import code.expressionlanguage.opers.exec.ReductibleOperable;
import code.expressionlanguage.opers.util.ClassArgumentMatching;
import code.util.CustList;
import code.util.StringList;

public final class DefaultValueOperation extends LeafOperation implements ReductibleOperable {

    private String className;

    DefaultValueOperation(int _indexInEl, int _indexChild, MethodOperation _m, OperationsSequence _op) {
        super(_indexInEl, _indexChild, _m, _op);
    }

    @Override
    public void analyze(Analyzable _conf) {
        OperationsSequence op_ = getOperations();
        int relativeOff_ = op_.getOffset();
        String originalStr_ = op_.getValues().getValue(CustList.FIRST_INDEX);
        String str_ = originalStr_.trim();
        int off_ = StringList.getFirstPrintableCharIndex(originalStr_) + relativeOff_;
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+off_, _conf);
        String realCl_ = str_.substring(str_.indexOf(PAR_LEFT)+1, str_.lastIndexOf(PAR_RIGHT));
        String classStr_;
        classStr_ = _conf.resolveCorrectType(realCl_, realCl_.contains(Templates.TEMPLATE_BEGIN));
        className = classStr_;
        setResultClass(new ClassArgumentMatching(className));
    }

    @Override
    public void tryCalculateNode(Analyzable _conf) {
        setArg(this, _conf, className);
    }
    private static void setArg(Operable _current, Analyzable _conf, String _className) {
        if (_className.contains(Templates.PREFIX_VAR_TYPE)) {
            return;
        }
        Argument a_ = new Argument();
        a_.setStruct(PrimitiveTypeUtil.defaultValue(_className,_conf));
        _current.setSimpleArgumentAna(a_, _conf);
    }

    public String getClassName() {
        return className;
    }
}
