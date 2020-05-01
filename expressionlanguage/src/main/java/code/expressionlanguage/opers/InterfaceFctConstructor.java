package code.expressionlanguage.opers;

import code.expressionlanguage.Analyzable;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.inherits.Templates;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.methods.InterfaceBlock;
import code.expressionlanguage.methods.RootBlock;
import code.expressionlanguage.opers.util.ClassArgumentMatching;

public final class InterfaceFctConstructor extends AbstractInvokingConstructor {
    private String className = EMPTY_STRING;
    public InterfaceFctConstructor(int _index, int _indexChild, MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    ClassArgumentMatching getFrom(Analyzable _conf) {
        int index_ = getIndexChild();
        if (index_ <= 0) {
            return null;
        }
        String cl_ = getMethodName();
        cl_ = cl_.substring(cl_.indexOf(PAR_LEFT)+1, cl_.lastIndexOf(PAR_RIGHT));
        cl_ = _conf.resolveAccessibleIdType(cl_.indexOf(PAR_LEFT)+1,cl_);
        if (!(_conf.getClasses().getClassBody(cl_) instanceof InterfaceBlock)) {
            FoundErrorInterpret call_ = new FoundErrorInterpret();
            call_.setFileName(_conf.getCurrentFileName());
            call_.setIndexFile(_conf.getCurrentLocationIndex());
            //type len
            call_.buildError(_conf.getContextEl().getAnalysisMessages().getCallCtorIntFromSuperInt());
            _conf.addError(call_);
            return null;
        }
        OperationNode par_ = getParent();
        String className_ = EMPTY_STRING;
        if (par_.getParent() instanceof CastOperation) {
            className_ = ((CastOperation)par_.getParent()).getClassName();
        }
        className = className_;
        if (!ExplicitOperation.customCast(className_)) {
            FoundErrorInterpret call_ = new FoundErrorInterpret();
            call_.setFileName(_conf.getCurrentFileName());
            call_.setIndexFile(_conf.getCurrentLocationIndex());
            //type len
            call_.buildError(_conf.getContextEl().getAnalysisMessages().getCallCtorIntFromSuperInt());
            _conf.addError(call_);
            return null;
        }
        String idCl_ = Templates.getIdFromAllTypes(className_);
        RootBlock sub_ = _conf.getClasses().getClassBody(idCl_);
        if (!(sub_ instanceof InterfaceBlock)|| !sub_.isStaticType()) {
            FoundErrorInterpret call_ = new FoundErrorInterpret();
            call_.setFileName(_conf.getCurrentFileName());
            call_.setIndexFile(_conf.getCurrentLocationIndex());
            //type len
            call_.buildError(_conf.getContextEl().getAnalysisMessages().getCallCtorIntFromSuperInt());
            _conf.addError(call_);
            return null;
        }
        String superClass_ = Templates.getFullTypeByBases(className_, cl_, _conf);
        if (superClass_.isEmpty()) {
            FoundErrorInterpret call_ = new FoundErrorInterpret();
            call_.setFileName(_conf.getCurrentFileName());
            call_.setIndexFile(_conf.getCurrentLocationIndex());
            //type len
            call_.buildError(_conf.getContextEl().getAnalysisMessages().getCallCtorIntFromSuperInt());
            _conf.addError(call_);
            return null;
        }
        return new ClassArgumentMatching(superClass_);
    }

    @Override
    void checkPositionBasis(Analyzable _conf) {
        int index_ = getIndexChild();
        if (index_ <= 0) {
            FoundErrorInterpret call_ = new FoundErrorInterpret();
            call_.setFileName(_conf.getCurrentFileName());
            call_.setIndexFile(getFullIndexInEl());
            //key word len
            call_.buildError(_conf.getContextEl().getAnalysisMessages().getCallCtorEnd());
            _conf.addError(call_);
        }
    }

    public String getClassName() {
        return className;
    }
}
