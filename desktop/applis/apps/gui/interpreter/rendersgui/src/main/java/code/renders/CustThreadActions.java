package code.renders;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.analyze.AbstractFileBuilder;
import code.expressionlanguage.analyze.ReportedMessages;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.exec.ExecClassesUtil;
import code.expressionlanguage.exec.InitPhase;
import code.expressionlanguage.exec.ProcessMethod;
import code.expressionlanguage.exec.StackCall;
import code.expressionlanguage.exec.blocks.ExecNamedFunctionBlock;
import code.expressionlanguage.exec.blocks.ExecOverridableBlock;
import code.expressionlanguage.exec.blocks.ExecRootBlock;
import code.expressionlanguage.exec.calls.util.CustomFoundMethod;
import code.expressionlanguage.exec.inherits.ExecTemplates;
import code.expressionlanguage.exec.inherits.Parameters;
import code.expressionlanguage.exec.util.ArgumentListCall;
import code.expressionlanguage.exec.util.ExecFormattedRootBlock;
import code.expressionlanguage.functionid.MethodAccessKind;
import code.expressionlanguage.functionid.MethodId;
import code.expressionlanguage.fwd.blocks.ExecTypeFunction;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.structs.ArrayStruct;
import code.expressionlanguage.structs.StringStruct;
import code.formathtml.exec.RendStackCall;
import code.formathtml.util.BeanCustLgNames;
import code.formathtml.util.DefaultConfigurationLoader;
import code.formathtml.util.DualAnalyzedContext;
import code.formathtml.util.DualConfigurationContext;
import code.gui.document.AbstractThreadActions;
import code.gui.document.RenderedPage;
import code.renders.utilcompo.CustBeanFileBuilder;
import code.renders.utilcompo.LgNamesRenderUtils;
import code.util.CustList;
import code.util.StringList;
import code.util.StringMap;

public final class CustThreadActions extends AbstractThreadActions {

    private String lgCode = "";

    private String fileName;
    private StringMap<String> fileNames;

    private BeanCustLgNames stds;

    private String classDbName;

    private String methodName;

    private CustThreadActions(RenderedPage _page){
        super(_page);
    }

    public static CustThreadActions inst(RenderedPage _page, BeanCustLgNames _lgNames, String _lgCode, String _fileName, StringMap<String> _fileNames) {
        CustThreadActions t_ = new CustThreadActions(_page);
        t_.stds = _lgNames;
        t_.lgCode = _lgCode;
        t_.fileName = _fileName;
        t_.fileNames = _fileNames;
        return t_;
    }
    void setClassDbName(String _classDbName) {
        classDbName = _classDbName;
    }

    void setMethodName(String _methodName) {
        methodName = _methodName;
    }

    @Override
    public void run() {
        String content_ = fileNames.getVal(fileName);
        if (content_ == null) {
            afterActionWithoutRemove(null, null);
            return;
        }
        DefaultConfigurationLoader def_ = new DefaultConfigurationLoader(stds);
        AbstractFileBuilder fileBuilder_;
        fileBuilder_ = new CustBeanFileBuilder(stds.getContent(), stds.getBeanAliases(), ((LgNamesRenderUtils)stds).getCustAliases());
        DualAnalyzedContext du_ = getPage().getNavigation().loadConfiguration(content_, lgCode, stds, fileBuilder_, def_);
        if (du_.getContext().isKo()) {
            afterActionWithoutRemove(null, null);
            return;
        }
        du_.getAnalyzed().getOptions().setDelimiterCase(';');
        getPage().getNavigation().setFiles(fileNames);
        ContextEl ctx_ = stds.setupAll(getPage().getNavigation(), getPage().getNavigation().getSession(), getPage().getNavigation().getFiles(), du_);
        if (ctx_ == null) {
            ReportedMessages reportedMessages_ = du_.getAnalyzed().getMessages();
            if (getPage().getArea() != null) {
                getPage().getArea().append(reportedMessages_.displayErrors());
            }
            finish();
            return;
        }
        getPage().setContext(ctx_);
        RendStackCall rendStackCall_ = new RendStackCall(InitPhase.NOTHING, ctx_);
        if (fileNames != null) {
            LgNames stds_ = ctx_.getStandards();
            String arrStr_ = StringExpUtil.getPrettyArrayType(stds_.getContent().getCharSeq().getAliasString());
            MethodId id_ = new MethodId(MethodAccessKind.STATIC, methodName, new StringList(arrStr_,arrStr_));
            ExecRootBlock classBody_ = ctx_.getClasses().getClassBody(classDbName);
            if (classBody_ != null) {
                CustList<ExecOverridableBlock> methods_ = ExecClassesUtil.getMethodBodiesById(classBody_, id_);
                if (!methods_.isEmpty()) {
                    ProcessMethod.initializeClass(classDbName, classBody_,ctx_, rendStackCall_.getStackCall());
                    if (ctx_.callsOrException(rendStackCall_.getStackCall())) {
                        afterActionWithoutRemove(ctx_, rendStackCall_);
                        return;
                    }
                    Argument arg_ = new Argument();
                    CustList<Argument> args_ = new CustList<Argument>();
                    int len_ = fileNames.size();
                    ArrayStruct arrNames_ = new ArrayStruct(len_,arrStr_);
                    for (int i = 0; i < len_; i++) {
                        arrNames_.set(i, new StringStruct(fileNames.getKey(i)));
                    }
                    ArrayStruct arrContents_ = new ArrayStruct(len_,arrStr_);
                    for (int i = 0; i < len_; i++) {
                        arrContents_.set(i, new StringStruct(fileNames.getValue(i)));
                    }
                    args_.add(new Argument(arrNames_));
                    args_.add(new Argument(arrContents_));
                    ExecNamedFunctionBlock method_ = methods_.first();
                    ExecTypeFunction pair_ = new ExecTypeFunction(classBody_, method_);
                    ArgumentListCall argList_ = new ArgumentListCall(args_);
                    Parameters parameters_ = ExecTemplates.wrapAndCall(pair_, new ExecFormattedRootBlock(classBody_, classDbName),arg_, ctx_, rendStackCall_.getStackCall(), argList_);
                    Argument out_ = ProcessMethod.calculateArgument(new CustomFoundMethod(arg_, new ExecFormattedRootBlock(classBody_, classDbName), pair_, parameters_), ctx_, rendStackCall_.getStackCall()).getValue();
                    if (ctx_.callsOrException(rendStackCall_.getStackCall())) {
                        afterActionWithoutRemove(ctx_, rendStackCall_);
                        return;
                    }
                    getPage().getNavigation().setDataBaseStruct(out_.getStruct());
                }
            }
        }
        getPage().getNavigation().initializeRendSession(ctx_, du_.getStds(), rendStackCall_);
        afterActionWithoutRemove(ctx_, rendStackCall_);
    }
}
