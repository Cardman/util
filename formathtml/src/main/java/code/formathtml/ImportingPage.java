package code.formathtml;
import code.expressionlanguage.Argument;
import code.expressionlanguage.PageEl;
import code.expressionlanguage.opers.util.Struct;
import code.expressionlanguage.variables.LocalVariable;
import code.expressionlanguage.variables.LoopVariable;
import code.formathtml.util.BlockHtml;
import code.formathtml.util.NodeAttribute;
import code.formathtml.util.ProcessingHtml;
import code.formathtml.util.ReadWriteHtml;
import code.sml.Element;
import code.sml.Node;
import code.sml.RowCol;
import code.util.CustList;
import code.util.EntryCust;
import code.util.NatTreeMap;
import code.util.ObjectMap;
import code.util.StringList;
import code.util.StringMap;

public final class ImportingPage {

    private static final String EMPTY_STRING = "";

    private static final String EQ = "=";

    private static final String READ_URL = "readUrl";

    private static final String BEAN_NAME = "beanName";

    private static final String RETURNED_VALUES = "returned values";

    private static final String SEP_INFO = "\n";

    private static final String SEP_KEY_VAL = ":";

    private PageEl pageEl = new PageEl();

    private CustList<BlockHtml> blockStacks = new CustList<BlockHtml>();

    private String beanName;

    private StringMap<LocalVariable> returnedValues = new StringMap<LocalVariable>();

    private String readUrl;

    private String key;

    private String messageValue;

    private String renderedMessage;

    private String prefix = EMPTY_STRING;

    private ReadWriteHtml readWrite;

    private ProcessingHtml processingHtml;

    private boolean returning;

    private final boolean rendering;

    public ImportingPage(boolean _rendering) {
        rendering = _rendering;
        processingHtml = new ProcessingHtml();
    }

    public String getInfos(Configuration _context) {
        StringList list_ = new StringList();
        list_.add(RETURNED_VALUES);
        for (EntryCust<String,LocalVariable> e: returnedValues.entryList()) {
            list_.add(e.getKey()+SEP_KEY_VAL+SEP_INFO+e.getValue().getInfos());
        }
        String keyMessage_ = EMPTY_STRING;
        if (key != null) {
            String intro_ = key+EQ;
            keyMessage_ = intro_+messageValue+SEP_INFO;
            keyMessage_ += processingHtml.getHtml() + SEP_INFO;
        }
        String page_ = EMPTY_STRING;
        if (rendering) {
            page_ += SEP_INFO +processingHtml.getHtml() + SEP_INFO;
        }
        int off_ = pageEl.getOffset();
        String attribute_ = pageEl.getProcessingAttribute();
        int tabWidth_ = pageEl.getTabWidth();
        RowCol rc_ = processingHtml.getRowCol(attribute_, off_, tabWidth_);
        return READ_URL+SEP_KEY_VAL+readUrl+page_+SEP_INFO+keyMessage_+BEAN_NAME+SEP_KEY_VAL+beanName+SEP_INFO+pageEl.getCommonInfosAndRc(rc_, _context.toContextEl())+SEP_INFO+list_;
    }

    public PageEl getPageEl() {
        return pageEl;
    }

    public void addToOffset(int _offset) {
        pageEl.addToOffset(_offset);
    }
    public String getNextTempVar() {
        int i_ = CustList.FIRST_INDEX;
        while (true) {
            if (!pageEl.getLocalVars().contains(FormatHtml.TMP_VAR+i_)) {
                break;
            }
            i_++;
        }
        return FormatHtml.TMP_VAR+i_;
    }

    public boolean isRendering() {
        return rendering;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String _beanName) {
        beanName = _beanName;
    }

    public ReadWriteHtml getReadWrite() {
        return readWrite;
    }

    public void setReadWrite(ReadWriteHtml _readWrite) {
        readWrite = _readWrite;
    }

    public Element getRoot() {
        return processingHtml.getRoot();
    }

    public void setRoot(Element _root) {
        processingHtml.setRoot(_root);
    }

    public Argument getGlobalArgument() {
        return pageEl.getGlobalArgument();
    }

    public void setGlobalArgumentStruct(Struct _obj, Configuration _context) {
        pageEl.setGlobalClass(_obj.getClassName(_context.toContextEl()));
        pageEl.setGlobalArgumentStruct(_obj);
    }

    public void setGlobalArgumentObj(Object _obj) {
        pageEl.setGlobalClass(_obj.getClass().getName());
        pageEl.setGlobalArgumentObj(_obj);
    }

    public void setGlobalArgument(Argument _globalArgument, Configuration _context) {
        pageEl.setGlobalClass(_globalArgument.getStruct().getClassName(_context.toContextEl()));
        pageEl.setGlobalArgument(_globalArgument);
    }

    public StringMap<LoopVariable> getVars() {
        return pageEl.getVars();
    }

    public void setVars(StringMap<LoopVariable> _vars) {
        pageEl.setVars(_vars);
    }

    public StringMap<LocalVariable> getLocalVars() {
        return pageEl.getLocalVars();
    }

    public void setLocalVars(StringMap<LocalVariable> _localVars) {
        pageEl.setLocalVars(_localVars);
    }

    public StringMap<LocalVariable> getCatchVars() {
        return pageEl.getCatchVars();
    }

    public void setCatchVars(StringMap<LocalVariable> _catchVars) {
        pageEl.setCatchVars(_catchVars);
    }

    public StringMap<LocalVariable> getParameters() {
        return pageEl.getParameters();
    }

    public void setParameters(StringMap<LocalVariable> _parameters) {
        pageEl.setParameters(_parameters);
    }

    public StringMap<LocalVariable> getReturnedValues() {
        return returnedValues;
    }

    public void setReturnedValues(StringMap<LocalVariable> _returnedValues) {
        returnedValues = _returnedValues;
    }

    public BlockHtml getLastStack() {
        return blockStacks.last();
    }

    public boolean noBlock() {
        return blockStacks.isEmpty();
    }

    public void addBlock(BlockHtml _b) {
        blockStacks.add(_b);
    }
    public void removeLastBlock() {
        blockStacks.removeLast();
    }

    public CustList<BlockHtml> getBlockStacks() {
        return blockStacks;
    }

    public void setBlockStacks(CustList<BlockHtml> _blockStacks) {
        blockStacks = _blockStacks;
    }

    public boolean isReturning() {
        return returning;
    }

    public void setReturning(boolean _returning) {
        returning = _returning;
    }

    public String getReadUrl() {
        return readUrl;
    }

    public void setReadUrl(String _readUrl) {
        readUrl = _readUrl;
    }

    public String getHtml() {
        return processingHtml.getHtml();
    }

    public void setHtml(String _html) {
        processingHtml.setHtml(_html);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String _key) {
        key = _key;
    }

    public String getMessageValue() {
        return messageValue;
    }

    public void setMessageValue(String _messageValue) {
        messageValue = _messageValue;
    }

    public String getRenderedMessage() {
        return renderedMessage;
    }

    public void setRenderedMessage(String _renderedMessage) {
        renderedMessage = _renderedMessage;
    }

    public Node getProcessingNode() {
        return processingHtml.getProcessingNode();
    }

    public void setProcessingNode(Node _processingNode) {
        processingHtml.setProcessingNode(_processingNode);
    }

    public int getTabWidth() {
        return pageEl.getTabWidth();
    }

    public void setTabWidth(int _tabWidth) {
        pageEl.setTabWidth(_tabWidth);
    }

    public ObjectMap<NodeAttribute,NatTreeMap<Integer,Integer>> getEncodedChars() {
        return processingHtml.getEncodedChars();
    }

    public void setEncodedChars(
            ObjectMap<NodeAttribute,NatTreeMap<Integer,Integer>> _encodedChars) {
        processingHtml.setEncodedChars(_encodedChars);
    }

    public int getOffset() {
        return pageEl.getOffset();
    }

    public void setOffset(int _offset) {
        pageEl.setOffset(_offset);
    }

    public String getProcessingAttribute() {
        return pageEl.getProcessingAttribute();
    }

    public void setProcessingAttribute(String _processingAttribute) {
        pageEl.setProcessingAttribute(_processingAttribute);
    }

    public boolean isLookForAttrValue() {
        return processingHtml.isLookForAttrValue();
    }

    public void setLookForAttrValue(boolean _lookForAttrValue) {
        processingHtml.setLookForAttrValue(_lookForAttrValue);
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String _prefix) {
        prefix = _prefix;
    }
}
