package code.expressionlanguage.opers;

import code.expressionlanguage.Analyzable;
import code.expressionlanguage.Argument;
import code.expressionlanguage.CustomError;
import code.expressionlanguage.ExecutableCode;
import code.expressionlanguage.OperationsSequence;
import code.expressionlanguage.PrimitiveTypeUtil;
import code.expressionlanguage.Templates;
import code.expressionlanguage.methods.Block;
import code.expressionlanguage.methods.BracedBlock;
import code.expressionlanguage.methods.CustomFoundConstructor;
import code.expressionlanguage.methods.InterfaceBlock;
import code.expressionlanguage.methods.Line;
import code.expressionlanguage.methods.util.BadConstructorCall;
import code.expressionlanguage.methods.util.BadInheritedClass;
import code.expressionlanguage.methods.util.InstancingStep;
import code.expressionlanguage.opers.util.ClassArgumentMatching;
import code.expressionlanguage.opers.util.ConstructorId;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.structs.StdStruct;
import code.util.CustList;
import code.util.StringList;

public final class InterfaceInvokingConstructor extends AbstractInvokingConstructor {

    public InterfaceInvokingConstructor(int _index, int _indexChild,
            MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    ClassArgumentMatching getFrom(Analyzable _conf) {
        String clCurName_ = _conf.getGlobalClass();
        String cl_ = getMethodName();
        cl_ = cl_.substring(cl_.indexOf(PAR_LEFT)+1, cl_.lastIndexOf(PAR_RIGHT));
        cl_ = _conf.resolveIdType(cl_);
        if (!(_conf.getClasses().getClassBody(cl_) instanceof InterfaceBlock)) {
            BadConstructorCall call_ = new BadConstructorCall();
            call_.setFileName(_conf.getCurrentFileName());
            call_.setRc(_conf.getCurrentLocation());
            _conf.getClasses().addError(call_);
            return null;
        }
        String superClass_ = Templates.getFullTypeByBases(clCurName_, cl_, _conf);
        if (superClass_ == null) {
            BadConstructorCall call_ = new BadConstructorCall();
            call_.setFileName(_conf.getCurrentFileName());
            call_.setRc(_conf.getCurrentLocation());
            _conf.getClasses().addError(call_);
            return null;
        }
        return new ClassArgumentMatching(superClass_);
    }

    @Override
    void checkPosition(Analyzable _conf) {
        Block curBlock_ = _conf.getCurrentBlock();
        Line curLine_ = (Line)curBlock_;
        BracedBlock br_ = curBlock_.getParent();
        if (br_.getParent() instanceof InterfaceBlock) {
            BadConstructorCall call_ = new BadConstructorCall();
            call_.setFileName(curLine_.getFile().getFileName());
            call_.setRc(curLine_.getRowCol(0, curLine_.getExpressionOffset()));
            call_.setLocalOffset(curLine_.getRowCol(getFullIndexInEl(), curLine_.getExpressionOffset()));
            _conf.getClasses().addError(call_);
        }
        Block f_ = br_.getFirstChild();
        if (f_ != curBlock_) {
            StringList previousInts_ = new StringList();
            if (f_ instanceof Line){
                if (!((Line)f_).getExp().isEmpty()) {
                    OperationNode root_ = ((Line)f_).getExp().last();
                    if (root_ instanceof InterfaceInvokingConstructor) {
                        AbstractInvokingConstructor ctor_ = (AbstractInvokingConstructor) root_;
                        ConstructorId cid_ = ctor_.getConstId();
                        if (cid_ != null) {
                            String cl_ = cid_.getName();
                            cl_ = Templates.getIdFromAllTypes(cl_);
                            previousInts_.add(cl_);
                        }
                    }
                }
            }
            while (true) {
                Block n_ = f_.getNextSibling();
                if (n_ == curBlock_) {
                    if (!(f_ instanceof Line)) {
                        //error
                        BadConstructorCall call_ = new BadConstructorCall();
                        call_.setFileName(curLine_.getFile().getFileName());
                        call_.setRc(curLine_.getRowCol(0, curLine_.getExpressionOffset()));
                        call_.setLocalOffset(curLine_.getRowCol(getFullIndexInEl(), curLine_.getExpressionOffset()));
                        _conf.getClasses().addError(call_);
                    } else {
                        if (!((Line)f_).getExp().isEmpty()) {
                            //the case ((Line)f_).getExp().isEmpty() leads already to an error
                            OperationNode root_ = ((Line)f_).getExp().last();
                            if (!(root_ instanceof AbstractInvokingConstructor)) {
                                //error
                                BadConstructorCall call_ = new BadConstructorCall();
                                call_.setFileName(curLine_.getFile().getFileName());
                                call_.setRc(curLine_.getRowCol(0, curLine_.getExpressionOffset()));
                                call_.setLocalOffset(curLine_.getRowCol(getFullIndexInEl(), curLine_.getExpressionOffset()));
                                _conf.getClasses().addError(call_);
                            }
                        }
                    }
                    break;
                }
                if (n_ instanceof Line){
                    if (!((Line)n_).getExp().isEmpty()) {
                        OperationNode root_ = ((Line)n_).getExp().last();
                        if (root_ instanceof InterfaceInvokingConstructor) {
                            AbstractInvokingConstructor ctor_ = (AbstractInvokingConstructor) root_;
                            ConstructorId cid_ = ctor_.getConstId();
                            if (cid_ != null) {
                                String cl_ = cid_.getName();
                                cl_ = Templates.getIdFromAllTypes(cl_);
                                if (!previousInts_.isEmpty()) {
                                    String sup_ = previousInts_.last();
                                    if (PrimitiveTypeUtil.canBeUseAsArgument(false, cl_, sup_, _conf)) {
                                        BadInheritedClass undef_;
                                        undef_ = new BadInheritedClass();
                                        undef_.setClassName(cl_);
                                        undef_.setFileName(n_.getFile().getFileName());
                                        undef_.setRc(n_.getRowCol(0, 0));
                                        _conf.getClasses().addError(undef_);
                                    }
                                }
                                previousInts_.add(cl_);
                            }
                        }
                    }
                }
                f_ = n_;
            }
            ConstructorId cid_ = getConstId();
            if (cid_ != null) {
                String cl_ = cid_.getName();
                cl_ = Templates.getIdFromAllTypes(cl_);
                if (!previousInts_.isEmpty()) {
                    String sup_ = previousInts_.last();
                    if (PrimitiveTypeUtil.canBeUseAsArgument(false, cl_, sup_, _conf)) {
                        BadInheritedClass undef_;
                        undef_ = new BadInheritedClass();
                        undef_.setClassName(cl_);
                        undef_.setFileName(curBlock_.getFile().getFileName());
                        undef_.setRc(curBlock_.getRowCol(0, 0));
                        _conf.getClasses().addError(undef_);
                    }
                }
            }
        }
    
    }

    @Override
    Argument getArgument(CustList<Argument> _arguments, ExecutableCode _conf) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        int off_ = getOffsetOper();
        setRelativeOffsetPossibleLastPage(getIndexInEl()+off_, _conf);
        LgNames stds_ = _conf.getStandards();
        String cast_;
        cast_ = stds_.getAliasCast();

        Argument arg_ = _conf.getOperationPageEl().getGlobalArgument();
        String clCurName_ = arg_.getObjectClassName(_conf.getContextEl());
        String gl_ = _conf.getOperationPageEl().getGlobalClass();
        gl_ = Templates.getIdFromAllTypes(gl_);
        String base_ = gl_;
        gl_ = Templates.getFullTypeByBases(clCurName_, gl_, _conf);
        CustList<Argument> firstArgs_;
        String calledCtor_ = base_;
        String cl_ = getConstId().getName();
        cl_ = Templates.getIdFromAllTypes(cl_);
        String superClass_ = Templates.getFullTypeByBases(clCurName_, cl_, _conf);
        String superClassBase_ = Templates.getIdFromAllTypes(superClass_);
        String lastType_ = getLastType();
        lastType_ = Templates.quickFormat(superClass_, lastType_, _conf);
        int natvararg_ = getNaturalVararg();
        ConstructorId ctorId_ = getConstId();
        firstArgs_ = listArguments(chidren_, natvararg_, lastType_, _arguments, _conf);
        calledCtor_ = superClassBase_;
        String calledCtorTemp_ = superClass_;
        StringList params_ = new StringList();
        String classFormat_ = calledCtor_;
        classFormat_ = Templates.getFullTypeByBases(clCurName_, classFormat_, _conf);
        if (classFormat_ == null) {
            _conf.setException(new StdStruct(new CustomError(_conf.joinPages()),cast_));
            Argument a_ = new Argument();
            return a_;
        }
        int j_ = 0;
        for (String c: ctorId_.getParametersTypes()) {
            String c_ = c;
            c_ = Templates.quickFormat(classFormat_, c_, _conf);
            if (j_ + 1 == ctorId_.getParametersTypes().size() && ctorId_.isVararg()) {
                c_ = PrimitiveTypeUtil.getPrettyArrayType(c_);
            }
            params_.add(c_);
            j_++;
        }
        processArgs(_conf, firstArgs_, params_);
        if (_conf.getContextEl().hasException()) {
            Argument a_ = new Argument();
            return a_;
        }
        _conf.getContextEl().setCallCtor(new CustomFoundConstructor(calledCtorTemp_, EMPTY_STRING, -1, ctorId_, arg_, firstArgs_, InstancingStep.USING_SUPER));
        return Argument.createVoid();
    }

}
