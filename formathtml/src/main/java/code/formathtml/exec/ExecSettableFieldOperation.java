package code.formathtml.exec;

import code.expressionlanguage.Analyzable;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ExecutableCode;
import code.expressionlanguage.calls.util.NotInitializedClass;
import code.expressionlanguage.inherits.PrimitiveTypeUtil;
import code.expressionlanguage.methods.Classes;
import code.expressionlanguage.methods.ProcessMethod;
import code.expressionlanguage.opers.SettableAbstractFieldOperation;
import code.expressionlanguage.opers.util.ClassArgumentMatching;
import code.expressionlanguage.opers.util.ClassField;
import code.expressionlanguage.opers.util.FieldInfo;
import code.expressionlanguage.stds.ResultErrorStd;
import code.expressionlanguage.structs.ErrorStruct;
import code.expressionlanguage.structs.FieldableStruct;
import code.expressionlanguage.structs.NullStruct;
import code.expressionlanguage.structs.Struct;
import code.formathtml.util.BeanLgNames;

public final class ExecSettableFieldOperation extends
        ExecAbstractFieldOperation implements ExecSettableElResult {

    private boolean variable;
    private FieldInfo fieldMetaInfo;
    private boolean staticAccess;

    private boolean catString;

    private int anc;

    public ExecSettableFieldOperation(SettableAbstractFieldOperation _s) {
        super(_s);
        variable = _s.isVariable();
        fieldMetaInfo = _s.getFieldMetaInfo();
        staticAccess = _s.isStaticAccess();
        catString = _s.isCatString();
        anc = _s.getAnc();
    }

    @Override
    public final boolean resultCanBeSet() {
        return variable;
    }

    @Override
    public final void setStaticAccess(boolean _staticAccess) {
        staticAccess = _staticAccess;
    }
    @Override
    public final boolean isStaticAccess() {
        return staticAccess;
    }
    @Override
    final Argument getCommonArgument(Argument _previous, ExecutableCode _conf) {
        int off_ = getOff();
        ClassField fieldId_ = fieldMetaInfo.getClassField();
        String className_ = fieldId_.getClassName();
        String fieldName_ = fieldId_.getFieldName();
        boolean staticField_ = fieldMetaInfo.isStaticField();
        if (resultCanBeSet()) {
            return Argument.createVoid();
        }
        Argument previous_ = new Argument();
        if (!staticField_) {
            previous_.setStruct(PrimitiveTypeUtil.getParent(anc, className_, _previous.getStruct(), _conf));
        }
        if (_conf.getContextEl().hasException()) {
            return Argument.createVoid();
        }
        return ExecInvokingOperation.getField(className_, fieldName_, staticField_, previous_, _conf, off_);
    }
    
    public final ClassField getFieldId() {
        if (fieldMetaInfo == null) {
            return null;
        }
        return fieldMetaInfo.getClassField();
    }

    public final FieldInfo getFieldMetaInfo() {
        return fieldMetaInfo;
    }

    @Override
    public final void tryCalculateNode(Analyzable _conf) {
        if (fieldMetaInfo == null) {
            return;
        }
        if (!fieldMetaInfo.isStaticField()) {
            return;
        }
        Classes cl_ = _conf.getClasses();
        ClassField fieldId_ = fieldMetaInfo.getClassField();
        if (!cl_.isCustomType(fieldId_.getClassName())) {
            ResultErrorStd res_ = _conf.getStandards().getSimpleResult(_conf, fieldId_);
            if (res_.getResult() != null) {
                Argument arg_ = Argument.createVoid();
                arg_.setStruct(res_.getResult());
                setSimpleArgumentAna(arg_,_conf);
            }
            return;
        }
        if (_conf.isGearConst() && isDeclaringField(this, _conf) && fieldMetaInfo.isFinalField()) {
            Argument arg_ = Argument.createVoid();
            setSimpleArgument(arg_);
            return;
        }
        Struct str_ = cl_.getStaticField(fieldId_);
        if (str_ != null) {
            Argument arg_ = Argument.createVoid();
            arg_.setStruct(str_);
            setSimpleArgumentAna(arg_,_conf);
        }
    }
    @Override
    public final void calculateSetting(ExecutableCode _conf, Argument _right) {
        Argument previous_;
        if (isIntermediateDottedOperation()) {
            previous_ = getPreviousArgument();
        } else {
            previous_ = _conf.getOperationPageEl().getGlobalArgument();
        }
        Argument arg_ = getCommonSetting(previous_, _conf, _right);
        NotInitializedClass statusInit_ = _conf.getContextEl().getInitClass();
        if (statusInit_ != null) {
            ProcessMethod.initializeClass(statusInit_.getClassName(), _conf.getContextEl());
            if (_conf.getContextEl().hasException()) {
                return;
            }
            arg_ = getCommonSetting(previous_, _conf, _right);
        }
        if (_conf.getContextEl().hasException()) {
            return;
        }
        setSimpleArgument(arg_, _conf);
    }
    @Override
    public final void calculateCompoundSetting(ExecutableCode _conf, String _op,
            Argument _right) {
        Argument previous_;
        if (isIntermediateDottedOperation()) {
            previous_ = getPreviousArgument();
        } else {
            previous_ = _conf.getOperationPageEl().getGlobalArgument();
        }
        Argument current_ = getArgument();
        Struct store_ = current_.getStruct();
        Argument arg_ = getCommonCompoundSetting(previous_, store_, _conf, _op, _right);
        if (_conf.getContextEl().hasException()) {
            return;
        }
        setSimpleArgument(arg_, _conf);
    }
    @Override
    public final void calculateSemiSetting(ExecutableCode _conf, String _op,
            boolean _post) {
        Argument previous_;
        if (isIntermediateDottedOperation()) {
            previous_ = getPreviousArgument();
        } else {
            previous_ = _conf.getOperationPageEl().getGlobalArgument();
        }
        Argument current_ = getArgument();
        Struct store_;
        if (current_ != null) {
            store_ = current_.getStruct();
        } else {
            store_ = NullStruct.NULL_VALUE;
        }
        Argument arg_ = getCommonSemiSetting(previous_, store_, _conf, _op, _post);
        if (_conf.getContextEl().hasException()) {
            return;
        }
        setSimpleArgument(arg_, _conf);
        
    }
    final Argument getCommonSetting(Argument _previous, ExecutableCode _conf, Argument _right) {
        int off_ = getOff();
        String fieldType_ = fieldMetaInfo.getRealType();
        boolean isStatic_ = fieldMetaInfo.isStaticField();
        boolean isFinal_ = fieldMetaInfo.isFinalField();
        ClassField fieldId_ = fieldMetaInfo.getClassField();
        String className_ = fieldId_.getClassName();
        String fieldName_ = fieldId_.getFieldName();
        Argument previous_ = new Argument();
        if (!isStatic_) {
            previous_.setStruct(PrimitiveTypeUtil.getParent(anc, className_, _previous.getStruct(), _conf));
        }
        if (_conf.getContextEl().hasExceptionOrFailInit()) {
            return Argument.createVoid();
        }
        //Come from code directly so constant static fields can be initialized here
        return ExecInvokingOperation.setField(className_, fieldName_, isStatic_, isFinal_, false, fieldType_, previous_, _right, _conf, off_);
    }
    final Argument getCommonCompoundSetting(Argument _previous, Struct _store, ExecutableCode _conf, String _op, Argument _right) {
        int off_ = getOff();
        setRelativeOffsetPossibleLastPage(getIndexInEl()+off_, _conf);
        Argument left_ = new Argument();
        Argument res_;

        String fieldType_;
        Classes classes_ = _conf.getClasses();
        ClassField fieldId_ = fieldMetaInfo.getClassField();
        String className_ = fieldId_.getClassName();
        if (fieldMetaInfo.isStaticField()) {
            fieldType_ = fieldMetaInfo.getRealType();
            left_.setStruct(_store);
            ClassArgumentMatching cl_ = new ClassArgumentMatching(fieldType_);
            res_ = ExecNumericOperation.calculateAffect(left_, _conf, _right, _op, catString, cl_);
            if (_conf.getContextEl().hasExceptionOrFailInit()) {
                return res_;
            }
            if (classes_.isCustomType(className_)) {
                if (_conf.getContextEl().isSensibleField(fieldId_.getClassName())) {
                    _conf.getContextEl().failInitEnums();
                    return _right;
                }
                classes_.initializeStaticField(fieldId_, res_.getStruct());
                Argument a_ = res_;
                return a_;
            }
            ResultErrorStd result_;
            result_ = BeanLgNames.setField(_conf.getContextEl(), fieldId_, NullStruct.NULL_VALUE, res_.getStruct());
            if (result_.getError() != null) {
                _conf.setException(new ErrorStruct(_conf,result_.getError()));
                return res_;
            }
            Argument a_ = res_;
            return a_;
        }
        Argument previous_ = new Argument();
        previous_.setStruct(PrimitiveTypeUtil.getParent(anc, className_, _previous.getStruct(), _conf));
        left_.setStruct(_store);
        fieldType_ = _conf.getStandards().getStructClassName(_store, _conf.getContextEl());
        ClassArgumentMatching cl_ = new ClassArgumentMatching(fieldType_);
        res_ = ExecNumericOperation.calculateAffect(left_, _conf, _right, _op, catString, cl_);
        if (_conf.getContextEl().hasExceptionOrFailInit()) {
            return res_;
        }
        if (previous_.getStruct() instanceof FieldableStruct) {
            if (_conf.getContextEl().isContainedSensibleFields(previous_.getStruct())) {
                _conf.getContextEl().failInitEnums();
                return _right;
            }
            ((FieldableStruct) previous_.getStruct()).setStruct(fieldId_, res_.getStruct());
            Argument a_ = res_;
            return a_;
        }
        ResultErrorStd result_;
        result_ = BeanLgNames.setField(_conf.getContextEl(), fieldId_, previous_.getStruct(), res_.getStruct());
        if (result_.getError() != null) {
            _conf.setException(new ErrorStruct(_conf,result_.getError()));
            return res_;
        }
        Argument a_ = res_;
        return a_;
    }
    final Argument getCommonSemiSetting(Argument _previous, Struct _store, ExecutableCode _conf, String _op, boolean _post) {
        int off_ = getOff();
        setRelativeOffsetPossibleLastPage(getIndexInEl()+off_, _conf);
        Argument left_ = new Argument();
        Argument res_;

        String fieldType_;
        Classes classes_ = _conf.getClasses();
        ClassField fieldId_ = fieldMetaInfo.getClassField();
        String className_ = fieldId_.getClassName();
        if (fieldMetaInfo.isStaticField()) {
            fieldType_ = fieldMetaInfo.getRealType();
            left_.setStruct(_store);
            ClassArgumentMatching cl_ = new ClassArgumentMatching(fieldType_);
            res_ = ExecNumericOperation.calculateIncrDecr(left_, _conf, _op, cl_);
            if (_conf.getContextEl().hasExceptionOrFailInit()) {
                return res_;
            }
            if (classes_.isCustomType(className_)) {
                if (_conf.getContextEl().isSensibleField(fieldId_.getClassName())) {
                    _conf.getContextEl().failInitEnums();
                    return res_;
                }
                classes_.initializeStaticField(fieldId_, res_.getStruct());
                return ExecSemiAffectationOperation.getPrePost(_post, left_, res_);
            }
            ResultErrorStd result_;
            result_ = BeanLgNames.setField(_conf.getContextEl(), fieldId_, NullStruct.NULL_VALUE, res_.getStruct());
            if (result_.getError() != null) {
                _conf.setException(new ErrorStruct(_conf,result_.getError()));
                return res_;
            }
            return ExecSemiAffectationOperation.getPrePost(_post, left_, res_);
        }
        Argument previous_ = new Argument();
        previous_.setStruct(PrimitiveTypeUtil.getParent(anc, className_, _previous.getStruct(), _conf));
        left_.setStruct(_store);
        fieldType_ = _conf.getStandards().getStructClassName(_store, _conf.getContextEl());
        ClassArgumentMatching cl_ = new ClassArgumentMatching(fieldType_);
        res_ = ExecNumericOperation.calculateIncrDecr(left_, _conf, _op, cl_);
        if (_conf.getContextEl().hasExceptionOrFailInit()) {
            return res_;
        }
        if (previous_.getStruct() instanceof FieldableStruct) {
            if (_conf.getContextEl().isContainedSensibleFields(previous_.getStruct())) {
                _conf.getContextEl().failInitEnums();
                return res_;
            }
            ((FieldableStruct) previous_.getStruct()).setStruct(fieldId_, res_.getStruct());
            return ExecSemiAffectationOperation.getPrePost(_post, left_, res_);
        }
        ResultErrorStd result_;
        result_ = BeanLgNames.setField(_conf.getContextEl(), fieldId_, previous_.getStruct(), res_.getStruct());
        if (result_.getError() != null) {
            _conf.setException(new ErrorStruct(_conf,result_.getError()));
            return res_;
        }
        return ExecSemiAffectationOperation.getPrePost(_post, left_, res_);
    }

    @Override
    public Argument endCalculate(ExecutableCode _conf, Argument _right) {
        return endCalculate(_conf, false, null, _right);
    }
    @Override
    public Argument endCalculate(ExecutableCode _conf, boolean _post,
            Argument _stored, Argument _right) {
        int off_ = getOff();
        setRelativeOffsetPossibleLastPage(getIndexInEl()+off_, _conf);
        Classes classes_ = _conf.getClasses();
        ClassField fieldId_ = fieldMetaInfo.getClassField();
        String className_ = fieldId_.getClassName();
        if (fieldMetaInfo.isStaticField()) {
            if (classes_.isCustomType(className_)) {
                classes_.initializeStaticField(fieldId_, _right.getStruct());
                Argument a_ = ExecSemiAffectationOperation.getPrePost(_post, _stored, _right);
                setSimpleArgument(a_, _conf);
                return a_;
            }
            ResultErrorStd result_;
            result_ = BeanLgNames.setField(_conf.getContextEl(), fieldId_, NullStruct.NULL_VALUE, _right.getStruct());
            if (result_.getError() != null) {
                _conf.setException(new ErrorStruct(_conf,result_.getError()));
                return _right;
            }
            Argument a_ = ExecSemiAffectationOperation.getPrePost(_post, _stored, _right);
            setSimpleArgument(a_, _conf);
            return a_;
        }
        Argument previousNode_;
        if (isIntermediateDottedOperation()) {
            previousNode_ = getPreviousArgument();
        } else {
            previousNode_ = _conf.getOperationPageEl().getGlobalArgument();
        }
        Argument previous_ = new Argument();
        previous_.setStruct(PrimitiveTypeUtil.getParent(anc, className_, previousNode_.getStruct(), _conf));
        if (previous_.getStruct() instanceof FieldableStruct) {
            ((FieldableStruct) previous_.getStruct()).setStruct(fieldId_, _right.getStruct());
            Argument a_ = ExecSemiAffectationOperation.getPrePost(_post, _stored, _right);
            setSimpleArgument(a_, _conf);
            return a_;
        }
        ResultErrorStd result_;
        result_ = BeanLgNames.setField(_conf.getContextEl(), fieldId_, previous_.getStruct(), _right.getStruct());
        if (result_.getError() != null) {
            _conf.setException(new ErrorStruct(_conf,result_.getError()));
            return _right;
        }
        Argument a_ = ExecSemiAffectationOperation.getPrePost(_post, _stored, _right);
        setSimpleArgument(a_, _conf);
        return a_;
    }
}
