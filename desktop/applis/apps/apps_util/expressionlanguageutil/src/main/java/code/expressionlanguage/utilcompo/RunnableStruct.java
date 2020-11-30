package code.expressionlanguage.utilcompo;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.ClassField;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.exec.*;
import code.expressionlanguage.exec.blocks.ExecNamedFunctionBlock;
import code.expressionlanguage.exec.blocks.ExecRootBlock;
import code.expressionlanguage.exec.calls.util.AbstractReflectElement;
import code.expressionlanguage.exec.inherits.ExecTemplates;
import code.expressionlanguage.exec.inherits.Parameters;
import code.expressionlanguage.exec.util.ExecOverrideInfo;
import code.expressionlanguage.fwd.blocks.ExecTypeFunction;
import code.expressionlanguage.structs.EnumerableStruct;
import code.expressionlanguage.structs.Struct;
import code.expressionlanguage.structs.WithParentStruct;
import code.util.CustList;

public final class RunnableStruct implements WithParentStruct, EnumerableStruct,Runnable {
    private Struct parent;

    private String className;

    private CustList<ClassFieldStruct> fields;

    private String name;
    private int ordinal;
    private final String parentClassName;
    private CommonExecutionInfos executionInfos;

    RunnableStruct(ContextEl _original, String _className,
                   String _name, int _ordinal,
                   CustList<ClassFieldStruct> _fields, Struct _parent, String _parendClassName) {
        executionInfos = _original.getExecutionInfos();
        name = _name;
        ordinal = _ordinal;
        className = _className;
        fields = _fields;
        parent = _parent;
        parentClassName = _parendClassName;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOrdinal() {
        return ordinal;
    }

    @Override
    public ClassFieldStruct getEntryStruct(ClassField _classField) {
        return ClassFieldStruct.getPair(fields,_classField);
    }

    @Override
    public CustList<ClassFieldStruct> getFields() {
        return fields;
    }

    @Override
    public Struct getParent() {
        return parent;
    }

    @Override
    public String getParentClassName() {
        return parentClassName;
    }

    @Override
    public void setParent(Struct _parent) {
        parent = _parent;
    }

    @Override
    public String getClassName(ContextEl _contextEl) {
        return className;
    }

    @Override
    public boolean sameReference(Struct _other) {
        return this == _other;
    }

    @Override
    public void run() {
        RunnableContextEl r_ = new RunnableContextEl(InitPhase.NOTHING, executionInfos);
        setupThread(r_);
        invoke(this,r_, ((LgNamesWithNewAliases) r_.getStandards()).getExecutingBlocks().getRunnableType(), ((LgNamesWithNewAliases) r_.getStandards()).getExecutingBlocks().getRunMethod(),new CustList<Argument>());
    }

    public static void invoke(Struct _instance, RunnableContextEl _r, ExecRootBlock _rootBlock, ExecNamedFunctionBlock _method, CustList<Argument> _args) {
        String base_ = StringExpUtil.getIdFromAllTypes(_instance.getClassName(_r));
        ExecOverrideInfo mId_ = _rootBlock.getRedirections().getVal(_method,base_);
        if (mId_ == null) {
            _r.getCustInit().removeThreadFromList(_r);
            return;
        }
        Argument arg_ = new Argument(_instance);
        RunnableStruct.invoke(arg_, mId_.getClassName(), _args, _r, mId_.getPair());
    }
    public static Argument invoke(Argument _global, String _class, CustList<Argument> _args, RunnableContextEl _cont, ExecTypeFunction _pair) {
        Parameters parameters_ = ExecTemplates.wrapAndCall(_pair, _class, _global, _args, _cont);
        Argument arg_ = ProcessMethod.calculateArgument(_global, _class, _pair, parameters_, _cont).getValue();
        _cont.getCustInit().prExc(_cont);
        return arg_;
    }
    public static Argument reflect(RunnableContextEl _cont, AbstractReflectElement _ref) {
        Argument arg_ = ProcessMethod.reflectArgument(_cont,_ref).getValue();
        _cont.getCustInit().prExc(_cont);
        return arg_;
    }
    public static long setupThread(RunnableContextEl _r) {
        long nb_ = _r.getCustInit().increment();
        StringBuilder dtPart_ = new StringBuilder();
        dtPart_.append(CustAliases.getDateTimeText("_", "_", "_"));
        dtPart_.append("__");
        dtPart_.append(nb_);
        dtPart_.append(".txt");
        _r.getCustInit().putNewCustTreadIdDate(_r, dtPart_.toString());
        return nb_;
    }
}
