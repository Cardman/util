package code.formathtml;

import code.bean.Bean;
import code.bean.BeanInfo;
import code.bean.validator.Validator;
import code.bean.validator.ValidatorInfo;
import code.expressionlanguage.*;
import code.expressionlanguage.calls.PageEl;
import code.expressionlanguage.calls.util.NotInitializedClass;
import code.expressionlanguage.common.GeneMethod;
import code.expressionlanguage.common.GeneType;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.errors.custom.*;
import code.expressionlanguage.errors.stds.StdErrorList;
import code.expressionlanguage.errors.stds.StdWordError;
import code.expressionlanguage.inherits.PrimitiveTypeUtil;
import code.expressionlanguage.inherits.Templates;
import code.expressionlanguage.instr.*;
import code.expressionlanguage.methods.*;
import code.expressionlanguage.opers.OperationNode;
import code.expressionlanguage.opers.util.*;
import code.expressionlanguage.options.KeyWords;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.structs.ArrayStruct;
import code.expressionlanguage.structs.CausingErrorStruct;
import code.expressionlanguage.structs.ClassMetaInfo;
import code.expressionlanguage.structs.NullStruct;
import code.expressionlanguage.structs.StackTraceElementStruct;
import code.expressionlanguage.structs.Struct;
import code.expressionlanguage.variables.LocalVariable;
import code.expressionlanguage.variables.LoopVariable;
import code.formathtml.errors.RendAnalysisMessages;
import code.formathtml.errors.RendKeyWords;
import code.formathtml.exec.RendDynOperationNode;
import code.formathtml.structs.BeanStruct;
import code.formathtml.structs.StdStruct;
import code.formathtml.util.AnalyzingDoc;
import code.formathtml.util.BeanCustLgNames;
import code.formathtml.util.BeanLgNames;
import code.formathtml.util.IndexesFormInput;
import code.formathtml.util.NodeContainer;
import code.sml.Document;
import code.sml.DocumentBuilder;
import code.sml.DocumentResult;
import code.util.*;

public final class Configuration implements ExecutableCode {

    private static final String NO_PARAM = "()";

    private static final String RETURN_LINE = "\n";

    private static final String SEP = ":";

    private static final String EMPTY_STRING = "";

    private static final int DEFAULT_TAB_WIDTH = 4;

    private String firstUrl = EMPTY_STRING;

    private StringMap<Validator> validators = new StringMap<Validator>();

    private StringMap<Bean> beans = new StringMap<Bean>();
    private StringMap<BeanInfo> beansInfos = new StringMap<BeanInfo>();

    private StringMap<StringMap<String>> navigation = new StringMap<StringMap<String>>();

    private StringMap<String> properties = new StringMap<String>();

    private String messagesFolder = EMPTY_STRING;

    private int tabWidth = DEFAULT_TAB_WIDTH;

    private String filesConfName = "";

    private ContextEl context;

    private StringMap<ValidatorInfo> lateValidators = new StringMap<ValidatorInfo>();

    private String prefix = EMPTY_STRING;
    private BeanLgNames standards;
    private String dataBaseClassName = EMPTY_STRING;

    private int nextIndex;

    private boolean staticContext;

    private final StringMap<Struct> builtBeans = new StringMap<Struct>();
    private final StringMap<Struct> builtValidators = new StringMap<Struct>();

    private HtmlPage htmlPage = new HtmlPage();

    private Document document;

    private String beanName;
    private final CustList<ImportingPage> importing = new CustList<ImportingPage>();

    private String currentUrl = "";

    private StringList addedFiles = new StringList();
    private StringList renderFiles = new StringList();

    private AnalyzingDoc analyzingDoc = new AnalyzingDoc();
    private RendLocalThrowing rendLocalThrowing = new RendLocalThrowing();
    private StringMap<RendDocumentBlock> renders = new StringMap<RendDocumentBlock>();

    private LongMap<LongTreeMap<NodeContainer>> containersMap;
    private CustList<LongTreeMap<NodeContainer>> containersMapStack;
    private IndexesFormInput indexes;
    private CustList<CustList<RendDynOperationNode>> callsExps = new CustList<CustList<RendDynOperationNode>>();
    private CustList<StringList> anchorsArgs = new CustList<StringList>();
    private CustList<StringList> anchorsVars = new CustList<StringList>();
    private BooleanList constAnchors = new BooleanList();
    private StringList anchorsNames = new StringList();

    private CustList<CustList<RendDynOperationNode>> callsFormExps = new CustList<CustList<RendDynOperationNode>>();
    private CustList<StringList> formsArgs = new CustList<StringList>();
    private CustList<StringList> formsVars = new CustList<StringList>();
    private StringList formsNames = new StringList();
    private LongMap<StringList> formatIdMap = new LongMap<StringList>();
    private CustList<StringList> formatIdMapStack = new CustList<StringList>();
    private Longs formsNb = new Longs();
    private Longs inputs = new Longs();
    private long currentForm;

    private Struct mainBean;
    private String currentLanguage = "";
    private final RendAnalysisMessages rendAnalysisMessages = new RendAnalysisMessages();
    private final RendKeyWords rendKeyWords = new RendKeyWords();
    private final StdErrorList stdErrorDet = new StdErrorList();
    private final ErrorList errorsDet = new ErrorList();
    private final WarningList warningsDet = new WarningList();
    @Override
    public boolean isMerged() {
        return context.isMerged();
    }

    @Override
    public ExecutableCode getExecutingInstance() {
        return this;
    }

    @Override
    public ArrayStruct newStackTraceElementArray() {
        int count_ = importing.size();
        int lenArrCtx_ = context.nbPages();
        Struct[] arr_ = new Struct[count_+ lenArrCtx_];
        for (int i = 0; i < count_; i++) {
            arr_[i] = newStackTraceElement(i);
        }
        for (int i = 0; i < lenArrCtx_; i++) {
            arr_[i+count_] = context.newStackTraceElement(i);
        }
        String cl_ = getStandards().getAliasStackTraceElement();
        cl_ = PrimitiveTypeUtil.getPrettyArrayType(cl_);
        return new ArrayStruct(arr_, cl_);
    }

    @Override
    public StackTraceElementStruct newStackTraceElement(int _index) {
        ImportingPage call_ = importing.get(_index);
        int indexFileType_ = call_.getSum();
        int row_ = call_.getRowFile(indexFileType_);
        int col_ = call_.getColFile(indexFileType_,row_);
        String fileName_ = call_.getReadUrl();
        String currentClassName_ = call_.getGlobalClass();
        return new StackTraceElementStruct(fileName_,row_,col_,indexFileType_,currentClassName_,"");
    }
    @Override
    public void setMerged(boolean _merged) {
        context.setMerged(_merged);
    }

    @Override
    public boolean isAcceptCommaInstr() {
        return context.isAcceptCommaInstr();
    }

    @Override
    public void setAcceptCommaInstr(boolean _merged) {
        context.setAcceptCommaInstr(_merged);
    }

    public void init() {
        htmlPage = new HtmlPage();
        document = null;
        currentUrl = firstUrl;
        prefix = StringList.concat(prefix,SEP);
        standards.build();
        standards.setupOverrides(context);
        renderFiles.removeAllString(firstUrl);
        renderFiles.add(firstUrl);
    }

    public void setupRendClasses(StringMap<String> _files) {
        if (!(standards instanceof BeanCustLgNames)) {
            return;
        }
        String conf_ = getFilesConfName();
        StringList content_ = new StringList();
        for (EntryCust<String, String> e: _files.entryList()) {
            if (StringList.quickEq(e.getKey(),conf_)) {
                content_ = StringList.splitStrings(e.getValue(), RETURN_LINE);
                break;
            }
        }
        StringMap<String> classFiles_ = new StringMap<String>();
        for (String f: content_) {
            for (EntryCust<String, String> e: _files.entryList()) {
                if (StringList.quickEq(e.getKey(), f)) {
                    classFiles_.put(f, e.getValue());
                    break;
                }
            }
        }
        //!classFiles_.isEmpty()
        Classes.validateWithoutInit(classFiles_, context);
        ((BeanCustLgNames)standards).buildIterables(this);
    }
    public void setupRenders(StringMap<String> _files) {
        renders.clear();
        analyzingDoc.setFiles(_files);
        context.setAnalyzing();
        getAnalyzing().setEnabledInternVars(false);
        for (String s: renderFiles) {
            String link_ = RendExtractFromResources.getRealFilePath(currentLanguage,s);
            String file_ = _files.getVal(link_);
            DocumentResult res_ = DocumentBuilder.parseSaxNotNullRowCol(file_);
            Document document_ = res_.getDocument();
            if (document_ == null) {
                FoundErrorInterpret badEl_ = new FoundErrorInterpret();
                badEl_.setFileName(getCurrentFileName());
                badEl_.setIndexFile(getCurrentLocationIndex());
                badEl_.buildError(getRendAnalysisMessages().getBadDocument(),
                        res_.getLocation().display());
                addError(badEl_);
                continue;
            }
            currentUrl = link_;
            renders.put(link_,RendBlock.newRendDocumentBlock(this,getPrefix(), document_, file_));
        }
        for (EntryCust<String,RendDocumentBlock> d: renders.entryList()) {
            d.getValue().buildFctInstructions(this);
        }
    }
    public void initForms() {
        callsExps = new CustList<CustList<RendDynOperationNode>>();
        anchorsArgs = new CustList<StringList>();
        anchorsVars = new CustList<StringList>();
        constAnchors = new BooleanList();
        anchorsNames = new StringList();
        containersMap = new LongMap<LongTreeMap<NodeContainer>>();
        containersMapStack = new CustList<LongTreeMap<NodeContainer>>();
        indexes = new IndexesFormInput();
        callsFormExps = new CustList<CustList<RendDynOperationNode>>();
        formatIdMap = new LongMap<StringList>();
        formatIdMapStack = new CustList<StringList>();
        formsNb = new Longs();
        inputs = new Longs();
        formsArgs = new CustList<StringList>();
        formsVars = new CustList<StringList>();
        formsNames = new StringList();
        currentForm = 0;
    }

    public Struct newSimpleBean(String _language, Struct _dataBase, BeanInfo _bean) {
        addPage(new ImportingPage());
        String keyWordNew_ = getKeyWords().getKeyWordNew();
        Struct strBean_ = RenderExpUtil.processQuickEl(StringList.concat(keyWordNew_," ",_bean.getClassName(),NO_PARAM), 0, this).getStruct();
        BeanStruct str_ = (BeanStruct) strBean_;
        Bean bean_ = str_.getBean();
        Object db_ = null;
        if (_dataBase instanceof StdStruct) {
            db_ = ((StdStruct)_dataBase).getInstance();
        }
        bean_.setDataBase(db_);
        bean_.setForms(new StringMapObject());
        bean_.setLanguage(_language);
        bean_.setScope(_bean.getScope());
        removeLastPage();
        return strBean_;
    }

    Struct newBean(String _language, Struct _bean, BeanInfo _info) {
        addPage(new ImportingPage());
        Argument arg_ = RenderExpUtil.calculateReuse(_info.getExps(), this);
        if (context.hasException()) {
            removeLastPage();
            return NullStruct.NULL_VALUE;
        }
        Struct strBean_ = arg_.getStruct();
        standards.forwardDataBase(_bean,strBean_,this);
        if (context.hasException()) {
            removeLastPage();
            return NullStruct.NULL_VALUE;
        }
        standards.setStoredForms(strBean_, this);
        if (context.hasException()) {
            removeLastPage();
            return NullStruct.NULL_VALUE;
        }
        standards.setLanguage(strBean_, _language,this);
        if (context.hasException()) {
            removeLastPage();
            return NullStruct.NULL_VALUE;
        }
        String str_ = standards.getScope(_bean,this);
        if (context.hasException()) {
            removeLastPage();
            return NullStruct.NULL_VALUE;
        }
        standards.setScope(strBean_, str_,this);
        removeLastPage();
        if (context.hasException()) {
            return NullStruct.NULL_VALUE;
        }
        return strBean_;
    }

    public String getFirstUrl() {
        return firstUrl;
    }

    public void setFirstUrl(String _firstUrl) {
        firstUrl = _firstUrl;
        setCurrentUrl(currentUrl);
    }

    public StringMap<Validator> getValidators() {
        return validators;
    }

    public void setValidators(StringMap<Validator> _validators) {
        validators = _validators;
    }

    public StringMap<BeanInfo> getBeansInfos() {
        return beansInfos;
    }

    public void setBeansInfos(StringMap<BeanInfo> _beansInfos) {
        beansInfos = _beansInfos;
    }

    public StringMap<Bean> getBeans() {
        return beans;
    }

    public void setBeans(StringMap<Bean> _beans) {
        beans = _beans;
    }

    public StringMap<StringMap<String>> getNavigation() {
        return navigation;
    }

    public void setNavigation(StringMap<StringMap<String>> _navigation) {
        navigation = _navigation;
    }

    public StringMap<String> getProperties() {
        return properties;
    }

    public void setProperties(StringMap<String> _properties) {
        properties = _properties;
    }

    public String getMessagesFolder() {
        return messagesFolder;
    }

    public void setMessagesFolder(String _messagesFolder) {
        messagesFolder = _messagesFolder;
    }

    public HtmlPage getHtmlPage() {
        return htmlPage;
    }

    public int getTabWidth() {
        return tabWidth;
    }

    public void setTabWidth(int _tabWidth) {
        tabWidth = _tabWidth;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document _document) {
        document = _document;
    }


    public boolean noPages() {
        return importing.isEmpty();
    }
    public void clearPages() {
        importing.clear();
    }
    public void addPage(ImportingPage _page) {
        importing.add(_page);
    }
    public ImportingPage getLastPage() {
        return importing.last();
    }
    public void removeLastPage() {
        importing.removeLast();
    }
    public CustList<ImportingPage> getImporting() {
        return importing;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String _currentUrl) {
        currentUrl = _currentUrl;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String _prefix) {
        prefix = _prefix;
    }

    public String getFilesConfName() {
        return filesConfName;
    }

    public void setFilesConfName(String _filesConfName) {
        filesConfName = _filesConfName;
    }

    public ContextEl getContext() {
        return context;
    }

    public void setContext(ContextEl _context) {
        context = _context;
    }

    public StringMap<ValidatorInfo> getLateValidators() {
        return lateValidators;
    }

    public void setLateValidators(StringMap<String> _lateValidators) {
        StringMap<ValidatorInfo> lateValidators_ = new StringMap<ValidatorInfo>();
        for (EntryCust<String, String> e: _lateValidators.entryList()) {
            ValidatorInfo val_ = new ValidatorInfo();
            val_.setClassName(e.getValue());
            lateValidators_.addEntry(e.getKey(), val_);
        }
        lateValidators = lateValidators_;
    }

    public StringMap<Struct> getBuiltBeans() {
        return builtBeans;
    }

    public StringMap<Struct> getBuiltValidators() {
        return builtValidators;
    }

    @Override
    public GeneType getClassBody(String _type) {
        return getContext().getClassBody(_type);
    }
    @Override
    public LgNames getStandards() {
        return getAdvStandards();
    }

    public BeanLgNames getAdvStandards() {
        return standards;
    }
    public void setStandards(BeanLgNames _standards) {
        standards = _standards;
    }

    public String getDataBaseClassName() {
        return dataBaseClassName;
    }

    public void setDataBaseClassName(String _dataBaseClassName) {
        dataBaseClassName = _dataBaseClassName;
    }

    @Override
    public String getGlobalClass() {
        return context.getGlobalClass();
    }

    @Override
    public void setGlobalClass(String _globalClass) {
        getLastPage().setGlobalClass(_globalClass);
    }

    public String getLocationFile(String _fileName, int _sum) {
        return StringList.concat(Integer.toString(_sum));
    }

    public void addWarning(FoundWarningInterpret _warning) {
        _warning.setLocationFile(getLocationFile(_warning.getFileName(),_warning.getIndexFile()));
        warningsDet.add(_warning);
    }
    @Override
    public void addError(FoundErrorInterpret _error) {
        _error.setLocationFile(getLocationFile(_error.getFileName(),_error.getIndexFile()));
        errorsDet.add(_error);
    }

    public RendAnalysisMessages getRendAnalysisMessages() {
        return rendAnalysisMessages;
    }

    public RendKeyWords getRendKeyWords() {
        return rendKeyWords;
    }

    public void addStdError(StdWordError _err) {
        stdErrorDet.add(_err);
    }

    public StdErrorList getStdErrorDet() {
        return stdErrorDet;
    }

    public boolean isEmptyStdErrors() {
        return stdErrorDet.isEmpty();
    }

    public boolean isEmptyErrors() {
        return getContext().getClasses().isEmptyErrors() && getErrorsDet().isEmpty();
    }

    public ErrorList getErrorsDet() {
        return errorsDet;
    }

    @Override
    public Classes getClasses() {
        return getContext().getClasses();
    }

    @Override
    public String getCurrentFileName() {
        return analyzingDoc.getFileName();
    }

    @Override
    public LoopVariable getVar(String _var) {
        return context.getVar(_var);
    }

    public StringMap<LoopVariable> getVars() {
        return getLastPage().getVars();
    }

    @Override
    public LocalVariable getLocalVar(String _key) {
        return context.getLocalVar(_key);
    }

    @Override
    public boolean containsLocalVar(String _key) {
        return context.containsLocalVar(_key);
    }

    @Override
    public void putLocalVar(String _key, LocalVariable _loc) {
        context.putLocalVar(_key, _loc);
    }

    public StringMap<LocalVariable> getLocalVars() {
        return getLastPage().getLocalVars();
    }

    public StringMap<LocalVariable> getCatchVars() {
        return getLastPage().getCatchVars();
    }

    @Override
    public LocalVariable getCatchVar(String _key) {
        return context.getCatchVar(_key);
    }

    @Override
    public StringMap<LocalVariable> getParameters() {
        return context.getParameters();
    }

    @Override
    public void setAnalyzedOffset(int _offset) {
        context.setAnalyzedOffset(_offset);
    }

    @Override
    public void setOffset(int _offset) {
        getLastPage().setOffset(_offset);
    }
    public void setOpOffset(int _offset) {
        getLastPage().setOpOffset(_offset);
    }

    @Override
    public MethodAccessKind getStaticContext() {
        return MethodId.getKind(staticContext);
    }

    @Override
    public boolean isStaticContext() {
        return isStaticAccess();
    }

    @Override
    public boolean isStaticAccess() {
        return staticContext;
    }

    @Override
    public void setAccessStaticContext(MethodAccessKind _staticContext) {
        staticContext = _staticContext == MethodAccessKind.STATIC;
    }

    public int getNextIndex() {
        return nextIndex;
    }

    public void setNextIndex(int _nextIndex) {
        nextIndex = _nextIndex;
    }

    @Override
    public int getCurrentChildTypeIndex(OperationNode _op, GeneType _type, String _fieldName, String _realClassName) {
        if (ContextEl.isEnumType(_type)) {
            FoundErrorInterpret call_ = new FoundErrorInterpret();
            call_.setFileName(getCurrentFileName());
            call_.setIndexFile(getCurrentLocationIndex());
            call_.buildError(context.getAnalysisMessages().getIllegalCtorEnum());
            addError(call_);
            _op.setResultClass(new ClassArgumentMatching(_realClassName));
            return -2;
        }
        return -1;
    }

    @Override
    public String getCurrentVarSetting() {
        return context.getCurrentVarSetting();
    }

    @Override
    public void setCurrentVarSetting(String _currentVarSetting) {
        context.setCurrentVarSetting(_currentVarSetting);
    }

    @Override
    public boolean isFinalVariable() {
        return context.isFinalVariable();
    }

    @Override
    public void setFinalVariable(boolean _finalVariable) {
        context.setFinalVariable(_finalVariable);
    }

    @Override
    public AnalyzedBlock getCurrentAnaBlock() {
        return analyzingDoc.getCurrentBlock();
    }

    @Override
    public Block getCurrentBlock() {
        return context.getCurrentBlock();
    }

    @Override
    public boolean hasDeclarator() {
        RendBlock currentBlock_ = analyzingDoc.getCurrentBlock();
        return currentBlock_.getPreviousSibling() instanceof RendDeclareVariable;
    }

    @Override
    public void setupDeclaratorClass(String _className) {
        RendBlock currentBlock_ = analyzingDoc.getCurrentBlock();
        RendBlock previousSibling_ = currentBlock_.getPreviousSibling();
        ((RendDeclareVariable)previousSibling_).setImportedClassName(_className);
    }

    @Override
    public boolean hasLoopDeclarator() {
        RendBlock currentBlock_ = analyzingDoc.getCurrentBlock();
        return currentBlock_ instanceof RendForMutableIterativeLoop;
    }

    @Override
    public void setupLoopDeclaratorClass(String _className) {
        RendBlock currentBlock_ = analyzingDoc.getCurrentBlock();
        ((RendForMutableIterativeLoop)currentBlock_).setImportedClassName(_className);
    }

    @Override
    public CustList<StringMap<LocalVariable>> getLocalVariables() {
        return context.getLocalVariables();
    }

    @Override
    public PageEl getOperationPageEl() {
        return importing.last().getPageEl();
    }

    @Override
    public void setException(Struct _struct) {
        context.setException(_struct);
    }

    @Override
    public ContextEl getContextEl() {
        return context;
    }

    @Override
    public StringMap<LocalVariable> getInternVars() {
        return context.getInternVars();
    }

    @Override
    public boolean isEnabledInternVars() {
        return context.isEnabledInternVars();
    }

    public boolean isInternGlobal() {
        return !analyzingDoc.getInternGlobalClass().isEmpty();
    }

    public Struct getInternGlobal() {
        return getLastPage().getInternGlobal();
    }

    public String getInternGlobalClass() {
        return analyzingDoc.getInternGlobalClass();
    }

    @Override
    public void appendParts(int _begin, int _end, String _in, CustList<PartOffset> _parts) {
        //implement
    }

    @Override
    public void appendTitleParts(int _begin, int _end, String _in, CustList<PartOffset> _parts) {
        //implement
    }

    private static AccessingImportingBlock getAccessingImportingBlock(AccessingImportingBlock _r, RootBlock _root) {
        AccessingImportingBlock a_;
        if (_root != null) {
            a_ = _root;
        } else {
            a_ = _r;
        }
        return a_;
    }

    @Override
    public ClassMetaInfo getExtendedClassMetaInfo(String _name) {
        return context.getExtendedClassMetaInfo(_name);
    }

    @Override
    public FieldInfo getFieldInfo(ClassField _classField) {
        return context.getFieldInfo(_classField);
    }

    @Override
    public AnalyzedPageEl getAnalyzing() {
        return context.getAnalyzing();
    }

    public CustList<StringMap<LocalVariable>> getLocalVarsAna() {
        return context.getAnalyzing().getLocalVars();
    }

    @Override
    public StringMap<Integer> getAvailableVariables() {
        return context.getAvailableVariables();
    }

    @Override
    public StringList getVariablesNamesLoopToInfer() {
        return context.getVariablesNamesLoopToInfer();
    }
    @Override
    public StringList getVariablesNamesToInfer() {
        return context.getVariablesNamesToInfer();
    }

    @Override
    public StringList getVariablesNames() {
        return context.getVariablesNames();
    }

    @Override
    public boolean containsMutableLoopVar(String _string) {
        return context.containsMutableLoopVar(_string);
    }

    @Override
    public LoopVariable getMutableLoopVar(String _key) {
        return context.getMutableLoopVar(_key);
    }

    @Override
    public void putMutableLoopVar(String _string, LoopVariable _loc) {
        context.putMutableLoopVar(_string, _loc);
    }

    @Override
    public ForLoopPart getForLoopPartState() {
        return context.getForLoopPartState();
    }

    @Override
    public String getIndexClassName() {
        RendBlock currentBlock_ = analyzingDoc.getCurrentBlock();
        return ((RendForMutableIterativeLoop)currentBlock_).getImportedClassIndexName();
    }

    @Override
    public void setForLoopPartState(ForLoopPart _state) {
        context.setForLoopPartState(_state);
    }

    @Override
    public boolean isAnnotAnalysis(OperationNode _op, OperationsSequence _seq) {
        return false;
    }

    @Override
    public void putLocalVar(String _string) {
        context.putLocalVar(_string);
    }

    @Override
    public StringList getInfersLocalVars() {
        return context.getInfersLocalVars();
    }

    @Override
    public StringList getInfersMutableLocalVars() {
        return context.getInfersMutableLocalVars();
    }

    @Override
    public void putMutableLoopVar(String _string) {
        context.putMutableLoopVar(_string);
    }

    @Override
    public String getLookLocalClass() {
        return context.getLookLocalClass();
    }

    @Override
    public void setLookLocalClass(String _lookLocalClass) {
        context.setLookLocalClass(_lookLocalClass);
    }

    @Override
    public boolean isOkNumOp() {
        return context.isOkNumOp();
    }

    @Override
    public void setOkNumOp(boolean _okNumOp) {
        context.setOkNumOp(_okNumOp);
    }

    @Override
    public KeyWords getKeyWords() {
        return context.getKeyWords();
    }

    @Override
    public StringMap<StringList> getCurrentConstraints() {
        return new StringMap<StringList>();
    }

    @Override
    public Ints getCurrentBadIndexes() {
        return context.getCurrentBadIndexes();
    }

    @Override
    public int getCurrentLocationIndex() {
        AnalyzedPageEl analyzing_ = context.getAnalyzing();
        int offset_ = analyzing_.getOffset();
        return analyzingDoc.getSum(offset_)+analyzing_.getTraceIndex()-offset_;
    }

    @Override
    public boolean isValidSingleToken(String _id) {
        if (!isValidToken(_id)) {
            return false;
        }
        return context.idDisjointToken(_id);
    }

    @Override
    public boolean isValidToken(String _id) {
        return context.isValidToken(_id,false);
    }

    @Override
    public boolean isHidden(AccessingImportingBlock _global, RootBlock _type) {
        if (_global == null) {
            return false;
        }
        return _global.isTypeHidden(_type,this);
    }

    @Override
    public void processInternKeyWord(String _string, int _fr,
            ResultAfterInstKeyWord _out) {
        KeyWords keyWords_ = getKeyWords();
        String keyWordIntern_ = keyWords_.getKeyWordIntern();
        String sub_ = _string.substring(_fr);
        int i_ = _fr;
        if (isInternGlobal()) {
            if (StringExpUtil.startsWithKeyWord(sub_, keyWordIntern_)) {
                int afterSuper_ = i_ + keyWordIntern_.length();
                String trim_ = _string.substring(afterSuper_).trim();
                if (trim_.startsWith(".")) {
                    //_string.charAt(afterSuper_) != EXTERN_CLASS && !foundHat_
                    i_ = _string.indexOf('.',afterSuper_);
                    _out.setNextIndex(i_);
                }
            }
        }
    }

    public void setupAnalyzing() {
        boolean merged_ = false;
        boolean accept_ = false;
        String currentVarSetting_ = "";
        String globalClass_ = "";
        if (context.getAnalyzing() != null) {
            merged_ = isMerged();
            accept_ = isAcceptCommaInstr();
            currentVarSetting_ = getCurrentVarSetting();
            globalClass_ = getGlobalClass();
        }
        context.setAnalyzing();
        context.getAnalyzing().setGlobalClass(globalClass_);
        context.getAnalyzing().initLocalVars();
        context.getAnalyzing().initMutableLoopVars();
        CustList<StringMap<LocalVariable>> l_ = new CustList<StringMap<LocalVariable>>();
        l_.add(getLocalVars());
        context.getAnalyzing().setLocalVars(l_);
        CustList<StringMap<LoopVariable>> lv_ = new CustList<StringMap<LoopVariable>>();
        lv_.add(getVars());
        context.getAnalyzing().setVars(lv_);
        CustList<StringMap<LocalVariable>> lc_ = new CustList<StringMap<LocalVariable>>();
        lc_.add(getCatchVars());
        context.getAnalyzing().setCatchVars(lc_);
        context.getAnalyzing().getParameters().putAllMap(getParameters());
        context.getAnalyzing().setMerged(merged_);
        context.getAnalyzing().setAcceptCommaInstr(accept_);
        context.getAnalyzing().setCurrentVarSetting(currentVarSetting_);
    }

    public StringList getAddedFiles() {
        return addedFiles;
    }

    public void setAddedFiles(StringList _addedFiles) {
        addedFiles = _addedFiles;
    }

    @Override
    public boolean hasToExit(String _className) {
        Classes classes_ = getClasses();
        if (classes_.isCustomType(_className)) {
            InitClassState res_ = classes_.getLocks().getState(getContextEl(), _className);
            if (res_ == InitClassState.NOT_YET) {
                getContextEl().setCallingState(new NotInitializedClass(_className));
                return true;
            }
            if (res_ == InitClassState.ERROR) {
                CausingErrorStruct causing_ = new CausingErrorStruct(_className,this);
                setException(causing_);
                return true;
            }
        }
        return false;
    }

    public boolean hasPages() {
        return !noPages();
    }

    public AnalyzingDoc getAnalyzingDoc() {
        return analyzingDoc;
    }

    public RendLocalThrowing getRendLocalThrowing() {
        return rendLocalThrowing;
    }

    public StringMap<RendDocumentBlock> getRenders() {
        return renders;
    }

    public void setRenderFiles(StringList _renderFiles) {
        renderFiles = _renderFiles;
    }

    public StringList getRenderFiles() {
        return renderFiles;
    }

    public IndexesFormInput getIndexes() {
        return indexes;
    }

    public CustList<LongTreeMap<NodeContainer>> getContainersMapStack() {
        return containersMapStack;
    }

    public LongMap<LongTreeMap<NodeContainer>> getContainersMap() {
        return containersMap;
    }

    public CustList<CustList<RendDynOperationNode>> getCallsExps() {
        return callsExps;
    }

    public CustList<StringList> getAnchorsArgs() {
        return anchorsArgs;
    }

    public CustList<StringList> getAnchorsVars() {
        return anchorsVars;
    }

    public BooleanList getConstAnchors() {
        return constAnchors;
    }

    public StringList getAnchorsNames() {
        return anchorsNames;
    }

    public CustList<CustList<RendDynOperationNode>> getCallsFormExps() {
        return callsFormExps;
    }

    public CustList<StringList> getFormatIdMapStack() {
        return formatIdMapStack;
    }

    public Longs getFormsNb() {
        return formsNb;
    }

    public Longs getInputs() {
        return inputs;
    }

    public LongMap<StringList> getFormatIdMap() {
        return formatIdMap;
    }

    public StringList getFormsNames() {
        return formsNames;
    }

    public CustList<StringList> getFormsVars() {
        return formsVars;
    }

    public CustList<StringList> getFormsArgs() {
        return formsArgs;
    }

    public long getCurrentForm() {
        return currentForm;
    }

    public void setCurrentForm(long _currentForm) {
        currentForm = _currentForm;
    }

    public Struct getMainBean() {
        return mainBean;
    }

    public void setMainBean(Struct _mainBean) {
        mainBean = _mainBean;
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public void setCurrentLanguage(String _currentLanguage) {
        currentLanguage = _currentLanguage;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String _beanName) {
        beanName = _beanName;
    }

    @Override
    public AccessingImportingBlock getCurrentGlobalBlock() {
        return analyzingDoc.getCurrentDoc();
    }

    @Override
    public AccessingImportingBlock getCurrentGlobalBlock(AccessingImportingBlock _bl) {
        String gl_ = getGlobalClass();
        RootBlock root_ = getContext().getClasses().getClassBody(Templates.getIdFromAllTypes(gl_));
        return getAccessingImportingBlock(_bl, root_);
    }

    @Override
    public CustList<PartOffset> getCurrentParts() {
        return new CustList<PartOffset>();
    }

    @Override
    public void buildCurrentConstraintsFull() {
        getAvailableVariables().clear();
    }
}
