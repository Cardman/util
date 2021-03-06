package code.expressionlanguage.exec.blocks;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.StackCall;
import code.expressionlanguage.exec.stacks.SwitchBlockStack;
import code.expressionlanguage.functionid.MethodAccessKind;
import code.expressionlanguage.fwd.blocks.ExecAnonFctContent;

public final class ExecSwitchValueMethod extends ExecAbstractSwitchMethod {

    public ExecSwitchValueMethod(boolean _retRef, String _name, MethodAccessKind _modifier, String _importedParamType, String _retType, ExecAnonFctContent _anonFctContent) {
        super(_retRef, _name, _modifier, _importedParamType, _retType, _anonFctContent);
    }

    @Override
    public ExecBlock processCase(ContextEl _cont, SwitchBlockStack _if, Argument _arg, StackCall _stack) {
        ExecResultCase found_ = ExecStdSwitchBlock.innerProcess(getImportedParamType(),_cont,_stack,this, _if, _arg);
        cover(_cont,_if,_arg,_stack, found_);
        return ExecResultCase.block(found_);
    }
}
