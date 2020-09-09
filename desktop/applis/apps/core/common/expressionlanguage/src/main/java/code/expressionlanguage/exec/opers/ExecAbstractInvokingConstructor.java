package code.expressionlanguage.exec.opers;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.blocks.ExecNamedFunctionBlock;
import code.expressionlanguage.exec.blocks.ExecRootBlock;
import code.expressionlanguage.analyze.opers.AbstractInvokingConstructor;
import code.expressionlanguage.functionid.ConstructorId;

public abstract class ExecAbstractInvokingConstructor extends ExecInvokingOperation {

    private ConstructorId constId;
    private String classFromName;

    private String lastType;

    private int naturalVararg;
    private int offsetOper;
    private ExecRootBlock rootBlock;
    private ExecNamedFunctionBlock ctor;
    public ExecAbstractInvokingConstructor(AbstractInvokingConstructor _abs, ContextEl _context) {
        super(_abs);
        constId = _abs.getConstId();
        lastType = _abs.getLastType();
        naturalVararg = _abs.getNaturalVararg();
        offsetOper = _abs.getOffsetOper();
        classFromName = _abs.getClassFromName();
        rootBlock = fetchType(_context,_abs.getRootNumber());
        ctor = fetchFunction(_context,_abs.getRootNumber(),_abs.getMemberNumber());
    }

    public int getOffsetOper() {
        return offsetOper;
    }

    public final ConstructorId getConstId() {
        return constId;
    }

    public String getLastType() {
        return lastType;
    }

    public int getNaturalVararg() {
        return naturalVararg;
    }

    public String getClassFromName() {
        return classFromName;
    }

    public ExecRootBlock getRootBlock() {
        return rootBlock;
    }

    public ExecNamedFunctionBlock getCtor() {
        return ctor;
    }
}
