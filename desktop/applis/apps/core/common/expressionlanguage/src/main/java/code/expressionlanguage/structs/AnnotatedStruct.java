package code.expressionlanguage.structs;

import code.expressionlanguage.exec.blocks.ExecAbstractSwitchMethod;
import code.expressionlanguage.exec.blocks.ExecAnonymousFunctionBlock;
import code.expressionlanguage.exec.blocks.ExecBlock;
import code.expressionlanguage.exec.blocks.ExecFileBlock;
import code.expressionlanguage.exec.util.ExecFormattedRootBlock;
import code.expressionlanguage.fwd.blocks.ExecAnnotContent;
import code.util.CustList;

public interface AnnotatedStruct extends DisplayableStruct {
    String getFileName();

    ExecFormattedRootBlock getFormatted();

    ExecBlock getBl();
    CustList<ExecAnnotContent> getAnnotationsOps();
    CustList<ExecAnonymousFunctionBlock> getAnonymousLambda();
    CustList<ExecAbstractSwitchMethod> getSwitchMethods();
}
