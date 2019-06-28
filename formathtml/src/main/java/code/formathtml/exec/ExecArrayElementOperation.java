package code.formathtml.exec;

import code.expressionlanguage.Analyzable;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ExecutableCode;
import code.expressionlanguage.calls.PageEl;
import code.expressionlanguage.inherits.PrimitiveTypeUtil;
import code.expressionlanguage.opers.AbstractArrayInstancingOperation;
import code.expressionlanguage.structs.ArrayStruct;
import code.expressionlanguage.structs.IntStruct;
import code.expressionlanguage.structs.Struct;
import code.util.CustList;
import code.util.Ints;
import code.util.StringList;

public final class ExecArrayElementOperation extends
        ExecAbstractArrayInstancingOperation {

    public ExecArrayElementOperation(AbstractArrayInstancingOperation _abs) {
        super(_abs);
    }

    @Override
    public void quickCalculate(Analyzable _conf) {
        CustList<ExecDynOperationNode> chidren_ = getChildrenNodes();
        CustList<Argument> arguments_ = new CustList<Argument>();
        if (!_conf.isGearConst()) {
            return;
        }

        for (ExecDynOperationNode o: chidren_) {
            arguments_.add(o.getArgument());
        }
        int nbCh_ = chidren_.size();
        int[] args_;
        args_ = new int[CustList.ONE_ELEMENT];
        args_[CustList.FIRST_INDEX] = chidren_.size();
        Argument a_ = new Argument();

        Ints dims_;
        dims_ = new Ints();
        dims_.add(nbCh_);
        String cl_ = getClassName();
        ArrayStruct str_ = PrimitiveTypeUtil.newCustomArray(cl_, dims_, _conf);
        for (int i = CustList.FIRST_INDEX; i < nbCh_; i++) {
            Argument chArg_ = arguments_.get(i);
            if (!setCheckedElement(str_, i, chArg_, _conf)) {
                return;
            }
        }
        a_.setStruct(str_);
        setSimpleArgumentAna(a_, _conf);
    }
    @Override
    Argument getArgument(CustList<Argument> _arguments, ExecutableCode _conf) {
        String me_ = getMethodName();
        int off_ = StringList.getFirstPrintableCharIndex(me_);
        setRelativeOffsetPossibleLastPage(getIndexInEl()+off_, _conf);
        String cl_ = getClassName();
        String className_;
        PageEl page_ = _conf.getOperationPageEl();
        className_ = page_.formatVarType(cl_, _conf);

        int nbCh_ = _arguments.size();
        int[] args_;

        args_ = new int[CustList.ONE_ELEMENT];
        args_[CustList.FIRST_INDEX] = _arguments.size();
        Argument a_ = new Argument();

        Ints dims_;
        dims_ = new Ints();
        dims_.add(nbCh_);
        Struct str_ = PrimitiveTypeUtil.newCustomArray(className_, dims_, _conf);
        for (int i = CustList.FIRST_INDEX; i < nbCh_; i++) {
            Argument chArg_ = _arguments.get(i);
            IntStruct i_ = new IntStruct(i);
            ExecArrOperation.setCheckedElement(str_, i_, chArg_, _conf);
            if (_conf.getContextEl().hasExceptionOrFailInit()) {
                return a_;
            }
        }
        a_.setStruct(str_);
        return a_;
    }
}