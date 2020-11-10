package code.formathtml.exec.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.exec.ExecutingUtil;
import code.expressionlanguage.exec.opers.*;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.fwd.blocks.ExecTypeFunction;
import code.expressionlanguage.fwd.opers.*;
import code.expressionlanguage.stds.StandardMethod;
import code.formathtml.Configuration;
import code.formathtml.util.BeanLgNames;
import code.util.IdMap;

public final class RendLambdaOperation extends RendLeafOperation implements RendCalculableOperation,RendPossibleIntermediateDotted {

    private ExecLambdaCommonContent lambdaCommonContent;
    private ExecLambdaMethodContent lambdaMethodContent;
    private ExecLambdaConstructorContent lambdaConstructorContent;
    private ExecLambdaFieldContent lambdaFieldContent;
    private StandardMethod standardMethod;

    public RendLambdaOperation(ExecOperationContent _content, ExecLambdaCommonContent _lambdaCommonContent, ExecLambdaMethodContent _lambdaMethodContent, ExecLambdaConstructorContent _lambdaConstructorContent, ExecLambdaFieldContent _lambdaFieldContent, StandardMethod _standardMethod) {
        super(_content);
        lambdaCommonContent = _lambdaCommonContent;
        lambdaMethodContent = _lambdaMethodContent;
        lambdaConstructorContent = _lambdaConstructorContent;
        lambdaFieldContent = _lambdaFieldContent;
        standardMethod = _standardMethod;
    }

    @Override
    public void calculate(IdMap<RendDynOperationNode, ArgumentsPair> _nodes, Configuration _conf, BeanLgNames _advStandards, ContextEl _context) {
        Argument previous_ = getPreviousArg(this,_nodes,_conf);
        Argument res_ = getCommonArgument(previous_, _context);
        setSimpleArgument(res_, _conf,_nodes, _context);
    }

    Argument getCommonArgument(Argument _previous, ContextEl _context) {
        String name_ = getResultClass().getSingleNameOrEmpty();
        String ownerType_ = lambdaCommonContent.getFoundClass();
        ClassMethodId methodId_ = lambdaMethodContent.getMethod();
        if (standardMethod != null) {
            return new Argument(ExecStdMethodLambdaOperation.newLambda(_previous, _context, ownerType_, lambdaCommonContent.getReturnFieldType(),
                    lambdaCommonContent.isShiftArgument(), lambdaCommonContent.isSafeInstance(), name_, standardMethod, methodId_.getConstraints()));
        }
        if (methodId_ == null && lambdaConstructorContent.getRealId() == null) {
            String idCl_ = StringExpUtil.getIdFromAllTypes(ownerType_);
            String formCl_ = ExecutingUtil.tryFormatType(idCl_, ownerType_, _context);
            return new Argument(ExecFieldLambdaOperation.newLambda(_previous, ownerType_, lambdaCommonContent.getReturnFieldType(), lambdaFieldContent.getClassField(), lambdaCommonContent.getAncestor(),
                    lambdaFieldContent.isAffField(), lambdaFieldContent.isStaticField(), lambdaFieldContent.isFinalField(), lambdaCommonContent.isShiftArgument(), lambdaCommonContent.isSafeInstance(), name_, lambdaCommonContent.getFileName(),lambdaFieldContent.getRootBlock(),lambdaFieldContent.getInfoBlock(), lambdaCommonContent.getReturnFieldType(), formCl_));
        }
        if (methodId_ == null) {
            if (lambdaConstructorContent.getPair() != null) {
                return new Argument(ExecConstructorLambdaOperation.newLambda(_previous, _context, ownerType_, lambdaConstructorContent.getRealId(), lambdaCommonContent.getReturnFieldType(),
                        lambdaCommonContent.isShiftArgument(), lambdaCommonContent.isSafeInstance(), name_, lambdaCommonContent.getFileName(), lambdaConstructorContent.getPair()));
            }
            return new Argument(ExecConstructorLambdaOperation.newLambda(_previous, _context, ownerType_, lambdaConstructorContent.getRealId(), lambdaCommonContent.getReturnFieldType(),
                    lambdaCommonContent.isShiftArgument(), lambdaCommonContent.isSafeInstance(), name_, lambdaCommonContent.getFileName(), new ExecTypeFunction(null,null)));
        }
        if (lambdaMethodContent.getPair() != null) {
            return new Argument(ExecCustMethodLambdaOperation.newLambda(_previous, _context, ownerType_, lambdaCommonContent.getReturnFieldType(), lambdaCommonContent.getAncestor(),
                    lambdaMethodContent.isDirectCast(), lambdaMethodContent.isPolymorph(), lambdaMethodContent.isAbstractMethod(), lambdaMethodContent.isExpCast(), lambdaCommonContent.isShiftArgument(), lambdaCommonContent.isSafeInstance(), name_, lambdaCommonContent.getFileName(), methodId_.getConstraints(),lambdaMethodContent.getPair()));
        }
        return new Argument(ExecMethodLambdaOperation.newLambda(_previous, _context, ownerType_, lambdaCommonContent.getReturnFieldType(), lambdaCommonContent.getAncestor(),
                lambdaMethodContent.isDirectCast(), lambdaMethodContent.isPolymorph(), lambdaMethodContent.isAbstractMethod(), lambdaMethodContent.isExpCast(), lambdaCommonContent.isShiftArgument(), lambdaCommonContent.isSafeInstance(), name_, lambdaCommonContent.getFileName(), methodId_.getConstraints(), new ExecTypeFunction(lambdaMethodContent.getDeclaring(), lambdaMethodContent.getFunction())));
    }

    @Override
    public boolean isIntermediateDottedOperation() {
        return lambdaCommonContent.isIntermediate();
    }

}
