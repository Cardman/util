package code.expressionlanguage.opers;

import code.expressionlanguage.Analyzable;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ArgumentCall;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.CustomError;
import code.expressionlanguage.InvokingConstructor;
import code.expressionlanguage.Mapping;
import code.expressionlanguage.OperationsSequence;
import code.expressionlanguage.PrimitiveTypeUtil;
import code.expressionlanguage.Templates;
import code.expressionlanguage.methods.util.InstancingStep;
import code.expressionlanguage.opers.util.CharStruct;
import code.expressionlanguage.opers.util.ClassArgumentMatching;
import code.expressionlanguage.opers.util.ConstructorId;
import code.expressionlanguage.opers.util.NumberStruct;
import code.expressionlanguage.opers.util.StdStruct;
import code.expressionlanguage.opers.util.Struct;
import code.expressionlanguage.stds.LgNames;
import code.util.CustList;
import code.util.StringList;

public final class CurrentInvokingConstructor extends AbstractInvokingConstructor {

    public CurrentInvokingConstructor(int _index, int _indexChild,
            MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    ClassArgumentMatching getFrom(Analyzable _conf) {
        String clCurName_ = _conf.getGlobalClass();
        return new ClassArgumentMatching(clCurName_);
    }

    @Override
    ArgumentCall getArgument(CustList<Argument> _arguments, ContextEl _conf) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        int off_ = getOffsetOper();
        setRelativeOffsetPossibleLastPage(getIndexInEl()+off_, _conf);
        LgNames stds_ = _conf.getStandards();
        String null_;
        String cast_;
        null_ = stds_.getAliasNullPe();
        cast_ = stds_.getAliasCast();

        Argument arg_ = _conf.getLastPage().getGlobalArgument();
        String clCurName_ = arg_.getObjectClassName(_conf);
        String gl_ = _conf.getLastPage().getGlobalClass();
        gl_ = StringList.getAllTypes(gl_).first();
        String base_ = StringList.getAllTypes(gl_).first();
        gl_ = Templates.getFullTypeByBases(clCurName_, gl_, _conf);
        CustList<Argument> firstArgs_;
        String calledCtor_ = base_;
        String calledCtorTemp_ = gl_;
        String lastType_ = getLastType();
        lastType_ = Templates.format(gl_, lastType_, _conf);
        int natvararg_ = getNaturalVararg();
        ConstructorId ctorId_ = getConstId();
        firstArgs_ = listArguments(chidren_, natvararg_, lastType_, _arguments, _conf);
        StringList params_ = new StringList();
        String classFormat_ = calledCtor_;
        classFormat_ = Templates.getFullTypeByBases(clCurName_, classFormat_, _conf);
        if (classFormat_ == null) {
            _conf.setException(new StdStruct(new CustomError(_conf.joinPages()),cast_));
            Argument a_ = new Argument();
            return ArgumentCall.newArgument(a_);
        }
        int j_ = 0;
        for (String c: ctorId_.getParametersTypes()) {
            String c_ = c;
            c_ = Templates.format(classFormat_, c_, _conf);
            if (j_ + 1 == ctorId_.getParametersTypes().size() && ctorId_.isVararg()) {
                c_ = PrimitiveTypeUtil.getPrettyArrayType(c_);
            }
            params_.add(c_);
            j_++;
        }
        int i_ = CustList.FIRST_INDEX;
        for (Argument a: firstArgs_) {
            if (i_ < params_.size()) {
                Struct str_ = a.getStruct();
                if (PrimitiveTypeUtil.primitiveTypeNullObject(params_.get(i_), str_, _conf)) {
                    _conf.setException(new StdStruct(new CustomError(_conf.joinPages()),null_));
                    Argument a_ = new Argument();
                    return ArgumentCall.newArgument(a_);
                }
                if (!str_.isNull()) {
                    Mapping mapping_ = new Mapping();
                    mapping_.setArg(a.getObjectClassName(_conf));
                    mapping_.setParam(params_.get(i_));
                    if (!Templates.isCorrect(mapping_, _conf)) {
                        setRelativeOffsetPossibleLastPage(chidren_.last().getIndexInEl(), _conf);
                        _conf.setException(new StdStruct(new CustomError(_conf.joinPages()),cast_));
                        Argument a_ = new Argument();
                        return ArgumentCall.newArgument(a_);
                    }
                }
                if (str_ instanceof NumberStruct || str_ instanceof CharStruct) {
                    ClassArgumentMatching clArg_ = new ClassArgumentMatching(params_.get(i_));
                    a.setStruct(PrimitiveTypeUtil.convertObject(clArg_, str_, _conf));
                }
            }
            i_++;
        }
        StringList called_ = _conf.getLastPage().getCallingConstr().getCalledConstructors();
        called_.add(calledCtor_);
        InvokingConstructor inv_ = new InvokingConstructor(calledCtorTemp_, EMPTY_STRING, -1, ctorId_, arg_, firstArgs_, InstancingStep.USING_THIS, called_);
        return ArgumentCall.newCall(inv_);
    }

}
