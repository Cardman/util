package code.expressionlanguage.exec.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.AccessEnum;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.exec.ExecutingUtil;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.functionid.*;
import code.expressionlanguage.fwd.blocks.ExecTypeFunction;
import code.expressionlanguage.fwd.opers.ExecLambdaCommonContent;
import code.expressionlanguage.fwd.opers.ExecLambdaMethodContent;
import code.expressionlanguage.fwd.opers.ExecOperationContent;
import code.expressionlanguage.structs.*;
import code.util.IdMap;

public final class ExecMethodLambdaOperation extends ExecAbstractLambdaOperation {

    private ExecLambdaMethodContent lambdaMethodContent;

    public ExecMethodLambdaOperation(ExecOperationContent _opCont, ExecLambdaCommonContent _lamCont, ExecLambdaMethodContent _lambdaMethodContent) {
        super(_opCont, _lamCont);
        lambdaMethodContent = _lambdaMethodContent;
    }

    @Override
    public void calculate(IdMap<ExecOperationNode, ArgumentsPair> _nodes,
                          ContextEl _conf) {
        Argument previous_ = getPreviousArg(this, _nodes, _conf);
        Argument res_ = getCommonArgument(previous_, _conf);
        setSimpleArgument(res_, _conf, _nodes);
    }

    Argument getCommonArgument(Argument _previous, ContextEl _conf) {
        return new Argument(newLambda(_previous,_conf, getFoundClass(), getReturnFieldType(), getAncestor(), lambdaMethodContent.isDirectCast(), lambdaMethodContent.isPolymorph(), lambdaMethodContent.getMethod().getConstraints()));
    }

    private Struct newLambda(Argument _previous, ContextEl _conf, String _foundClass, String _returnFieldType, int _ancestor, boolean _directCast, boolean _polymorph, MethodId _constraints) {
        String clArg_ = getResultClass().getSingleNameOrEmpty();
        String ownerType_ = _foundClass;
        ownerType_ = _conf.formatVarType(ownerType_);
        clArg_ = _conf.formatVarType(clArg_);
        return newLambda(_previous, _conf, ownerType_, _returnFieldType, _ancestor, _directCast, _polymorph, lambdaMethodContent.isAbstractMethod(), lambdaMethodContent.isExpCast(), isShiftArgument(), isSafeInstance(), clArg_, getFileName(), _constraints, new ExecTypeFunction(lambdaMethodContent.getDeclaring(), lambdaMethodContent.getFunction()));
    }

    public static Struct newLambda(Argument _previous, ContextEl _conf, String _ownerType, String _returnFieldType,
                                   int _ancestor, boolean _directCast, boolean _polymorph, boolean _abstractMethod, boolean _expCast, boolean _shiftArgument, boolean _safeInstance,
                                   String _clArg, String _fileName, MethodId _constraints, ExecTypeFunction _pair) {
        LambdaMethodStruct l_ = new LambdaMethodStruct(_clArg, _ownerType, _polymorph, _shiftArgument, _ancestor, _abstractMethod);
        l_.setInstanceCall(_previous);
        l_.setDirectCast(_directCast);
        l_.setExpCast(_expCast);
        l_.setSafeInstance(_safeInstance);
        l_.setMethodName(_constraints.getName());
        l_.setKind(_constraints.getKind());
        if (!(_ownerType.startsWith(StringExpUtil.ARR_CLASS) && _constraints.getName().startsWith("[]"))) {
            MethodMetaInfo metaInfo_ = buildMeta(_conf, _returnFieldType, _directCast, _expCast, _fileName, _ownerType, _constraints, l_, _pair);
            l_.setMetaInfo(metaInfo_);
        }
        return l_;
    }

    private static MethodMetaInfo buildMeta(ContextEl _conf, String _returnFieldType, boolean _directCast, boolean _expCast, String _fileName, String _ownerType, MethodId _id, LambdaMethodStruct _l, ExecTypeFunction _pair) {
        MethodId fid_ = ExecutingUtil.tryFormatId(_ownerType, _conf, _id);
        String className_;
        if (_l.isStaticCall()) {
            className_ = _ownerType;
        } else {
            className_ = StringExpUtil.getIdFromAllTypes(_ownerType);
        }
        String from_ = className_;
        if (className_.startsWith("[")) {
            from_ = StringExpUtil.getPrettyArrayType(_conf.getStandards().getContent().getCoreNames().getAliasObject());
        }
        String idCl_ = StringExpUtil.getIdFromAllTypes(_ownerType);
        String formCl_ = ExecutingUtil.tryFormatType(idCl_, _ownerType, _conf);
        MethodModifier met_;
        if (fid_.getKind() == MethodAccessKind.STATIC) {
            met_ = MethodModifier.STATIC;
        } else {
            met_ = MethodModifier.NORMAL;
        }
        MethodMetaInfo metaInfo_ = new MethodMetaInfo(_ownerType,AccessEnum.PUBLIC, from_, _id, met_, _returnFieldType, fid_, formCl_);
        metaInfo_.setDirectCast(_directCast);
        metaInfo_.setExpCast(_expCast);
        metaInfo_.setFileName(_fileName);
        metaInfo_.setAnnotableBlock(_pair.getFct());
        metaInfo_.setCallee(_pair.getFct());
        metaInfo_.setCalleeInv(_pair.getFct());
        metaInfo_.setDeclaring(_pair.getType());
        metaInfo_.setPair(_pair);
        return metaInfo_;
    }


}
