package code.expressionlanguage.exec.blocks;
import code.expressionlanguage.exec.stacks.AbruptCallingFinally;
import code.expressionlanguage.structs.Struct;

public interface CallingFinally {

    AbruptCallingFinally newAbruptCallingFinally(Struct _struct);
}
