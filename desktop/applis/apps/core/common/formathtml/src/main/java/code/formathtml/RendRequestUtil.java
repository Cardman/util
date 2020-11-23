package code.formathtml;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.inherits.ExecTemplates;
import code.expressionlanguage.structs.LongStruct;
import code.expressionlanguage.structs.NullStruct;
import code.expressionlanguage.structs.Struct;
import code.expressionlanguage.exec.variables.LocalVariable;
import code.formathtml.exec.RenderExpUtil;
import code.formathtml.exec.opers.RendDynOperationNode;
import code.formathtml.util.BeanLgNames;
import code.formathtml.util.NodeContainer;
import code.util.CustList;
import code.util.StringList;
import code.util.core.NumberUtil;

final class RendRequestUtil {

    private RendRequestUtil() {
    }


    static Struct redirect(Configuration _conf, Argument _bean, int _url, BeanLgNames _advStandards, ContextEl _context) {
        StringList varNames_ = _conf.getHtmlPage().getAnchorsVars().get(_url);
        CustList<RendDynOperationNode> exps_ = _conf.getHtmlPage().getCallsExps().get(_url);
        StringList args_ = _conf.getHtmlPage().getAnchorsArgs().get(_url);
        return calculate(_conf, _bean, varNames_, exps_, args_, _advStandards, _context);
    }

    private static Struct calculate(Configuration _conf, Argument _bean, StringList _varNames, CustList<RendDynOperationNode> _exps, StringList _args, BeanLgNames _advStandards, ContextEl _context) {
        ImportingPage ip_ = _conf.getLastPage();
        int s_ = _varNames.size();
        for (int i = 0; i< s_; i++) {
            LocalVariable locVar_ = LocalVariable.newLocalVariable(new LongStruct(NumberUtil.parseLongZero(_args.get(i))), _advStandards.getAliasPrimLong());
            ip_.putValueVar(_varNames.get(i), locVar_);
        }
        Argument arg_ = RenderExpUtil.calculateReuse(_exps,_conf,_bean, _advStandards, _context);
        for (String n: _varNames) {
            ip_.removeLocalVar(n);
        }
        if (_context.callsOrException()) {
            return NullStruct.NULL_VALUE;
        }
        return arg_.getStruct();
    }

    static Struct redirectForm(Configuration _conf, Argument _bean, int _url, BeanLgNames _advStandards, ContextEl _context) {
        StringList varNames_ = _conf.getHtmlPage().getFormsVars().get(_url);
        CustList<RendDynOperationNode> exps_ = _conf.getHtmlPage().getCallsFormExps().get(_url);
        StringList args_ = _conf.getHtmlPage().getFormsArgs().get(_url);
        return calculate(_conf, _bean, varNames_, exps_, args_, _advStandards, _context);
    }
    static void setRendObject(Configuration _conf, NodeContainer _nodeContainer,
                              Struct _attribute, BeanLgNames _advStandards, ContextEl _context) {
        Struct obj_ = _nodeContainer.getUpdated();
        String attrName_ = _nodeContainer.getVarName();
        String prev_ = _nodeContainer.getVarPrevName();
        CustList<RendDynOperationNode> wr_ = _nodeContainer.getOpsWrite();
        ImportingPage ip_ = _conf.getLastPage();
        LocalVariable lv_ = LocalVariable.newLocalVariable(obj_, _nodeContainer.getUpdatedClass());
        ip_.putValueVar(prev_, lv_);
        int i_ = 0;
        CustList<Struct> structParam_ = _nodeContainer.getStructParam();
        CustList<String> structParamClass_ = _nodeContainer.getStructParamClass();
        for (String p: _nodeContainer.getVarParamName()) {
            Struct arg_ = structParam_.get(i_);
            lv_ = LocalVariable.newLocalVariable(arg_, structParamClass_.get(i_));
            ip_.putValueVar(p, lv_);
            i_++;
        }
        String wrap_ = ExecTemplates.toWrapper(_nodeContainer.getNodeInformation().getInputClass(), _context.getStandards());
        lv_ = LocalVariable.newLocalVariable(_attribute,wrap_);
        ip_.putValueVar(attrName_, lv_);
        RenderExpUtil.calculateReuse(wr_,_conf, _advStandards, _context);
        ip_.removeLocalVar(prev_);
        for (String p: _nodeContainer.getVarParamName()) {
            ip_.removeLocalVar(p);
        }
        ip_.removeLocalVar(attrName_);
    }
}
