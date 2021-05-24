package code.formathtml.exec.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.ClassArgumentMatching;
import code.expressionlanguage.exec.opers.ExecNumericOperation;
import code.expressionlanguage.exec.util.ImplicitMethods;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.fwd.opers.ExecOperationContent;
import code.expressionlanguage.fwd.opers.ExecOperatorContent;
import code.expressionlanguage.fwd.opers.ExecStaticPostEltContent;
import code.formathtml.exec.RendStackCall;
import code.formathtml.util.BeanLgNames;
import code.util.IdMap;

public final class RendSemiAffectationNatOperation extends RendSemiAffectationOperation {
    private final ImplicitMethods converterFrom;
    private final ImplicitMethods converterTo;

    public RendSemiAffectationNatOperation(ExecOperationContent _content, ExecStaticPostEltContent _staticPostEltContent, ExecOperatorContent _operatorContent, ImplicitMethods _converterFrom, ImplicitMethods _converterTo) {
        super(_content, _staticPostEltContent, _operatorContent);
        converterFrom = _converterFrom;
        converterTo = _converterTo;
    }


    @Override
    protected void calculateSpec(IdMap<RendDynOperationNode, ArgumentsPair> _nodes, BeanLgNames _advStandards, ContextEl _context, RendStackCall _rendStack) {
        RendDynOperationNode left_ = getFirstNode(this);
        Argument leftStore_ = getArgument(_nodes,left_);
        Argument stored_ = getNullArgument(_nodes, getSettable());
        Argument before_ = stored_;
        if (converterFrom != null) {
            Argument conv_ = tryConvert(converterFrom.get(0),converterFrom.getOwnerClass(), leftStore_, _context, _rendStack);
            stored_ = Argument.getNullableValue(conv_);
        }
        if (converterTo != null) {
            String tres_ = converterTo.get(0).getFct().getImportedParametersTypes().get(0);
            byte cast_ = ClassArgumentMatching.getPrimitiveCast(tres_, _context.getStandards().getPrimTypes());
            Argument res_ = ExecNumericOperation.calculateIncrDecr(stored_, getOperatorContent().getOper(), cast_);
            Argument conv_ = tryConvert(converterTo.get(0),converterTo.getOwnerClass(), res_, _context, _rendStack);
            if (conv_ == null) {
                return;
            }
            conv_ = RendAffectationOperation.calculateChSetting(getSettable(),_nodes, conv_, _advStandards, _context, _rendStack);
            stored_ = getPrePost(getStaticPostEltContent().isPost(),before_,conv_);
            setSimpleArgument(stored_, _nodes, _context, _rendStack);
            return;
        }
        Argument arg_ = calculateSemiChSetting(_nodes, _advStandards, _context, _rendStack);
        setSimpleArgument(arg_, _nodes, _context, _rendStack);
    }

    private static Argument getNullArgument(IdMap<RendDynOperationNode, ArgumentsPair> _nodes, RendDynOperationNode _settable) {
        return getArgument(_nodes, _settable);
    }

    private Argument calculateSemiChSetting(IdMap<RendDynOperationNode, ArgumentsPair> _nodes, BeanLgNames _advStandards, ContextEl _context, RendStackCall _rendStackCall) {
        Argument arg_ = null;
        RendDynOperationNode settable_ = getSettable();
        if (settable_ instanceof RendStdRefVariableOperation) {
            arg_ = ((RendStdRefVariableOperation)settable_).calculateSemiSetting(_nodes, getOperatorContent().getOper(), getStaticPostEltContent().isPost(), getResultClass().getUnwrapObjectNb(), _advStandards, _context, _rendStackCall);
        }
        if (settable_ instanceof RendSettableFieldOperation) {
            arg_ = ((RendSettableFieldOperation)settable_).calculateSemiSetting(_nodes, getOperatorContent().getOper(), getStaticPostEltContent().isPost(), getResultClass().getUnwrapObjectNb(), _advStandards, _context, _rendStackCall);
        }
        if (settable_ instanceof RendCustArrOperation) {
            arg_ = ((RendCustArrOperation)settable_).calculateSemiSetting(_nodes, getOperatorContent().getOper(), getStaticPostEltContent().isPost(), getResultClass().getUnwrapObjectNb(), _advStandards, _context, _rendStackCall);
        }
        if (settable_ instanceof RendArrOperation) {
            arg_ = ((RendArrOperation)settable_).calculateSemiSetting(_nodes, getOperatorContent().getOper(), getStaticPostEltContent().isPost(), getResultClass().getUnwrapObjectNb(), _advStandards, _context, _rendStackCall);
        }
        if (settable_ instanceof RendSettableCallFctOperation) {
            arg_ = ((RendSettableCallFctOperation)settable_).calculateSemiSetting(_nodes, getOperatorContent().getOper(), getStaticPostEltContent().isPost(), getResultClass().getUnwrapObjectNb(), _advStandards, _context, _rendStackCall);
        }
        return Argument.getNullableValue(arg_);
    }


}
