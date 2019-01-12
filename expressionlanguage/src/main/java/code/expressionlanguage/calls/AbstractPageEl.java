package code.expressionlanguage.calls;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.calls.util.ReadWrite;
import code.expressionlanguage.methods.Block;
import code.expressionlanguage.methods.FileBlock;
import code.expressionlanguage.methods.WithNotEmptyEl;
import code.expressionlanguage.opers.ExpressionLanguage;
import code.expressionlanguage.stacks.LoopBlockStack;
import code.expressionlanguage.stacks.RemovableVars;
import code.expressionlanguage.variables.LocalVariable;
import code.util.CustList;
import code.util.StringMap;

public abstract class AbstractPageEl extends PageEl {

    protected static final String EMPTY_STRING = "";

    private ReadWrite readWrite;
    private Block blockRoot;

    private CustList<RemovableVars> blockStacks = new CustList<RemovableVars>();

    private CustList<ExpressionLanguage> currentEls = new CustList<ExpressionLanguage>();

    private boolean finallyToProcess;

    private int globalOffset;

    private int translatedOffset;

    private int offset;
    private StringMap<LocalVariable> internVars = new StringMap<LocalVariable>();
    private FileBlock file;

    public boolean receive(Argument _argument, ContextEl _context) {
        getLastEl().setArgument(_argument, _context);
        if (_context.isFailInit()) {
            return false;
        }
        return _context.processException();
    }

    public void setTranslatedOffset(int _translatedOffset) {
        translatedOffset = _translatedOffset;
    }

    public void setGlobalOffset(int _globalOffset) {
        globalOffset = _globalOffset;
    }

    public void setOffset(int _offset) {
        offset = _offset;
    }

    public int getTraceIndex() {
        return globalOffset + offset + translatedOffset;
    }

    public abstract boolean checkCondition(ContextEl _context);

    public ExpressionLanguage getCurrentEl(ContextEl _context, WithNotEmptyEl _block, int _index, int _indexProcess) {
        ExpressionLanguage el_;
        if (_index < currentEls.size()) {
            el_ = currentEls.get(_index);
        } else {
            el_ = _block.getEl(_context, _indexProcess);
            currentEls.add(el_);
        }
        return el_;
    }

    public boolean hasBlock() {
        return !blockStacks.isEmpty();
    }

    public LoopBlockStack getLastLoopIfPossible() {
        LoopBlockStack c_ = null;
        if (hasBlock() && getLastStack() instanceof LoopBlockStack) {
            c_ = (LoopBlockStack) getLastStack();
        }
        return c_;
    }

    public RemovableVars getLastStack() {
        return blockStacks.last();
    }

    public void addBlock(RemovableVars _b) {
        blockStacks.add(_b);
    }

    public void removeLastBlock() {
        blockStacks.removeLast();
    }

    public boolean isFinallyToProcess() {
        return finallyToProcess;
    }

    public void setFinallyToProcess(boolean _finallyToProcess) {
        finallyToProcess = _finallyToProcess;
    }

    public void clearCurrentEls() {
        currentEls.clear();
    }

    public int sizeEl(){
        return currentEls.size();
    }

    public Argument getValue(int _index){
        return currentEls.get(_index).getArgument();
    }

    public boolean isEmptyEl() {
        return currentEls.isEmpty();
    }
    public ExpressionLanguage getLastEl() {
        return currentEls.last();
    }

    public void addCurrentEl(ExpressionLanguage _el) {
        currentEls.add(_el);
    }

    public ReadWrite getReadWrite() {
        return readWrite;
    }
    public void setNullReadWrite() {
        readWrite = null;
    }

    public void setReadWrite(ReadWrite _readWrite) {
        readWrite = _readWrite;
    }

    public abstract void tryProcessEl(ContextEl _context);

    public Block getBlockRoot() {
        return blockRoot;
    }

    public void setBlockRoot(Block _blockRoot) {
        blockRoot = _blockRoot;
    }

    public StringMap<LocalVariable> getInternVars() {
        return internVars;
    }

    public FileBlock getFile() {
        return file;
    }

    public void setFile(FileBlock _file) {
        file = _file;
    }
    
}
