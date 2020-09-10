package code.formathtml;

import code.expressionlanguage.Argument;
import code.expressionlanguage.analyze.MethodHeaders;
import code.expressionlanguage.analyze.ReportedMessages;
import code.expressionlanguage.analyze.opers.OperationNode;
import code.expressionlanguage.analyze.variables.AnaLocalVariable;
import code.expressionlanguage.analyze.variables.AnaLoopVariable;
import code.expressionlanguage.common.ConstType;
import code.expressionlanguage.common.Delimiters;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.errors.AnalysisMessages;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.exec.ExecutingUtil;
import code.expressionlanguage.exec.variables.LocalVariable;
import code.expressionlanguage.exec.variables.LoopVariable;
import code.expressionlanguage.functionid.MethodId;
import code.expressionlanguage.instr.ElResolver;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.structs.ClassMetaInfo;
import code.expressionlanguage.structs.ErrorStruct;
import code.formathtml.structs.BeanInfo;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.calls.util.CallingState;
import code.expressionlanguage.exec.Classes;
import code.expressionlanguage.common.ClassField;
import code.expressionlanguage.options.Options;
import code.expressionlanguage.structs.FieldableStruct;
import code.expressionlanguage.structs.Struct;
import code.formathtml.exec.RendDynOperationNode;
import code.formathtml.util.*;
import code.sml.Document;
import code.sml.DocumentBuilder;
import code.util.CustList;
import code.util.EntryCust;
import code.util.StringList;
import code.util.StringMap;

import static org.junit.Assert.assertTrue;

public abstract class CommonRender {

    protected static void addImportingPage(Configuration _conf) {
        addInnerPage(_conf);
    }

    protected static void addInnerPage(Configuration _conf) {
        _conf.addPage(new ImportingPage());
    }

    protected static void setLocale(String _locale, Configuration _conf) {
        _conf.setCurrentLanguage(_locale);
        _conf.getAnalyzingDoc().setLanguages(new StringList(_locale));
    }
    protected static Navigation newNavigation(Configuration _conf) {
        Navigation nav_ = new Navigation();
        nav_.setSession(_conf);
        ContextEl context_ = _conf.getContext();
        BeanLgNames standards_ = (BeanLgNames) context_.getStandards();
        nav_.getSession().setStandards(standards_);
        context_.setFullStack(new AdvancedFullStack(nav_.getSession()));
        return nav_;
    }

    public static void setupAnalyzing(Configuration _configuration) {
        boolean merged_ = false;
        boolean accept_ = false;
        String currentVarSetting_ = "";
        String globalClass_ = "";
        if (_configuration.getContext().getAnalyzing() != null) {
            merged_ = _configuration.getAnalyzing().isMerged();
            accept_ = _configuration.getAnalyzing().isAcceptCommaInstr();
            currentVarSetting_ = _configuration.getContext().getAnalyzing().getCurrentVarSetting();
            globalClass_ = _configuration.getGlobalClass();
        } else {
            _configuration.getContext().setAnalyzing();
        }
        _configuration.setupInts();
        _configuration.getContext().getAnalyzing().setGlobalClass(globalClass_);
        _configuration.getContext().getAnalyzing().setGlobalType(_configuration.getContext().getAnalyzing().getAnaClassBody(StringExpUtil.getIdFromAllTypes(globalClass_)));
        for (EntryCust<String,LocalVariable> e: _configuration.getLastPage().getValueVars().entryList()) {
            AnaLocalVariable a_ = new AnaLocalVariable();
            a_.setClassName(e.getValue().getClassName());
            a_.setConstType(ConstType.LOC_VAR);
            _configuration.getContext().getAnalyzing().getInfosVars().put(e.getKey(), a_);
        }
        for (EntryCust<String,LoopVariable> e: _configuration.getLastPage().getVars().entryList()) {
            AnaLoopVariable a_ = new AnaLoopVariable();
            a_.setIndexClassName(e.getValue().getIndexClassName());
            _configuration.getContext().getAnalyzing().getLoopsVars().put(e.getKey(), a_);
        }
        _configuration.getContext().getAnalyzing().setMerged(merged_);
        _configuration.getContext().getAnalyzing().setAcceptCommaInstr(accept_);
        _configuration.getContext().getAnalyzing().setCurrentVarSetting(currentVarSetting_);
    }

    public static void setVars(ImportingPage _importingPage, StringMap<LoopVariable> _vars) {
        _importingPage.getPageEl().setVars(_vars);
    }

    public static void setLocalVars(ImportingPage _importingPage, StringMap<LocalVariable> _localVars) {
        _importingPage.getPageEl().getValueVars().putAllMap(_localVars);
    }

    Configuration contextElFive() {
        return contextElFive(new StringMap<String>());
    }
    Configuration contextElFive(StringMap<String> _files) {
        Configuration conf_ =  EquallableExUtil.newConfiguration();
        conf_.setPrefix("c:");
        Options opt_ = new Options();
        opt_.setReadOnly(true);
        
        ContextEl cont_ = InitializationLgNames.buildStdThree(opt_);
        conf_.setContext(cont_);
        cont_.setFullStack(new AdvancedFullStack(conf_));
        BeanLgNames standards_ = (BeanLgNames) cont_.getStandards();
        conf_.setStandards(standards_);
        getHeaders(_files, cont_);
        assertTrue(cont_.isEmptyErrors());
        ((BeanCustLgNames)standards_).buildIterables(conf_);
        return conf_;
    }
    protected static MethodHeaders getHeaders(StringMap<String> _files,ContextEl _cont) {
        Classes.validateWithoutInit(_files,_cont);
        return _cont.getAnalyzing().getHeaders();
    }
    protected static RendDocumentBlock buildRendWithoutBean(String html_, Configuration conf_) {
        setLocale("en", conf_);
        Document doc_ = DocumentBuilder.parseSaxNotNullRowCol(html_).getDocument();
        RendDocumentBlock rendDocumentBlock_ = RendBlock.newRendDocumentBlock(conf_, "c:", doc_, html_);
        conf_.getRenders().put("page1.html",rendDocumentBlock_);
        setupAna(conf_);
        rendDocumentBlock_.buildFctInstructions(conf_);
        conf_.setDocument(doc_);
        tryInitStaticlyTypes(conf_);
        conf_.setDocument(doc_);
        return rendDocumentBlock_;
    }
    protected static RendDocumentBlock buildRendWithOneBean(String html_, Configuration context_) {
        setLocale("en", context_);
        Document doc_ = DocumentBuilder.parseSaxNotNullRowCol(html_).getDocument();
        CustList<RendDynOperationNode> ops_ = RenderExpUtil.getAnalyzedOperations("$new pkg.BeanOne()", 0, context_);
        BeanInfo b_ = new BeanInfo();
        b_.setClassName("pkg.BeanOne");
        b_.setExps(ops_);
        b_.setResolvedClassName(ops_.last().getResultClass().getSingleNameOrEmpty());
        context_.getBeansInfos().addEntry("bean_one",b_);
        RendDocumentBlock rendDocumentBlock_ = RendBlock.newRendDocumentBlock(context_, "c:", doc_, html_);
        context_.getRenders().put("page1.html",rendDocumentBlock_);
        setupAna(context_);
        rendDocumentBlock_.buildFctInstructions(context_);
        tryInitStaticlyTypes(context_);
        Struct bean_ = RenderExpUtil.calculateReuse(ops_, context_).getStruct();
        context_.getBuiltBeans().addEntry("bean_one", bean_);
        context_.clearPages();
        context_.setDocument(doc_);
        return rendDocumentBlock_;
    }

    protected static RendDocumentBlock buildTwoPages(String html_, String htmlTwo_, Configuration conf_) {
        setLocale("en", conf_);
        Document doc_ = DocumentBuilder.parseSaxNotNullRowCol(html_).getDocument();
        Document secDoc_ = DocumentBuilder.parseSaxNotNullRowCol(htmlTwo_).getDocument();
        RendDocumentBlock rendDocumentBlock_ = RendBlock.newRendDocumentBlock(conf_, "c:", doc_, html_);
        RendDocumentBlock rendSecDocumentBlock_ = RendBlock.newRendDocumentBlock(conf_, "c:", secDoc_, htmlTwo_);
        conf_.getRenders().put("page1.html",rendDocumentBlock_);
        conf_.getRenders().put("page2.html",rendSecDocumentBlock_);
        rendDocumentBlock_.buildFctInstructions(conf_);
        rendSecDocumentBlock_.buildFctInstructions(conf_);
        tryInitStaticlyTypes(conf_);
        conf_.setDocument(doc_);
        return rendDocumentBlock_;
    }

    protected static RendDocumentBlock builtTwoPagesOne(String html_, String htmlTwo_, Configuration context_) {
        setLocale("en", context_);
        Document doc_ = DocumentBuilder.parseSaxNotNullRowCol(html_).getDocument();
        Document docSec_ = DocumentBuilder.parseSaxNotNullRowCol(htmlTwo_).getDocument();
        CustList<RendDynOperationNode> ops_ = RenderExpUtil.getAnalyzedOperations("$new pkg.BeanOne()", 0, context_);
        BeanInfo b_ = new BeanInfo();
        b_.setClassName("pkg.BeanOne");
        b_.setExps(ops_);
        b_.setResolvedClassName(ops_.last().getResultClass().getSingleNameOrEmpty());
        context_.getBeansInfos().addEntry("bean_one",b_);
        RendDocumentBlock rendDocumentBlock_ = RendBlock.newRendDocumentBlock(context_, "c:", doc_, html_);
        RendDocumentBlock rendDocumentBlockSec_ = RendBlock.newRendDocumentBlock(context_, "c:", docSec_, htmlTwo_);
        context_.getRenders().put("page1.html",rendDocumentBlock_);
        context_.getRenders().put("page2.html",rendDocumentBlockSec_);
        setupAna(context_);
        rendDocumentBlock_.buildFctInstructions(context_);
        rendDocumentBlockSec_.buildFctInstructions(context_);
        tryInitStaticlyTypes(context_);
        Struct bean_ = RenderExpUtil.calculateReuse(ops_, context_).getStruct();
        context_.getBuiltBeans().addEntry("bean_one", bean_);
        context_.clearPages();
        context_.setDocument(doc_);
        return rendDocumentBlock_;
    }

    protected static void setupAna(Configuration context_) {
        context_.setupInts();
    }

    protected static RendDocumentBlock buildTwoPagesTwo(String html_, String htmlTwo_, Configuration context_) {
        setLocale("en", context_);
        Document doc_ = DocumentBuilder.parseSaxNotNullRowCol(html_).getDocument();
        Document docSec_ = DocumentBuilder.parseSaxNotNullRowCol(htmlTwo_).getDocument();
        CustList<RendDynOperationNode> ops_ = RenderExpUtil.getAnalyzedOperations("$new pkg.BeanOne()", 0, context_);
        BeanInfo b1_ = new BeanInfo();
        b1_.setExps(ops_);
        b1_.setClassName("pkg.BeanOne");
        b1_.setResolvedClassName(ops_.last().getResultClass().getSingleNameOrEmpty());
        context_.getBeansInfos().addEntry("bean_one",b1_);
        CustList<RendDynOperationNode> ops2_ = RenderExpUtil.getAnalyzedOperations("$new pkg.BeanTwo()", 0, context_);
        BeanInfo b2_ = new BeanInfo();
        b2_.setExps(ops2_);
        b2_.setClassName("pkg.BeanTwo");
        b2_.setResolvedClassName(ops2_.last().getResultClass().getSingleNameOrEmpty());
        context_.getBeansInfos().addEntry("bean_two", b2_);
        RendDocumentBlock rendDocumentBlock_ = RendBlock.newRendDocumentBlock(context_, "c:", doc_, html_);
        RendDocumentBlock rendDocumentBlockSec_ = RendBlock.newRendDocumentBlock(context_, "c:", docSec_, htmlTwo_);
        context_.getRenders().put("page1.html",rendDocumentBlock_);
        context_.getRenders().put("page2.html",rendDocumentBlockSec_);
        setupAna(context_);
        rendDocumentBlock_.buildFctInstructions(context_);
        rendDocumentBlockSec_.buildFctInstructions(context_);
        tryInitStaticlyTypes(context_);
        Struct bean_ = RenderExpUtil.calculateReuse(ops_, context_).getStruct();
        context_.getBuiltBeans().addEntry("bean_one", bean_);
        bean_ = RenderExpUtil.calculateReuse(ops2_, context_).getStruct();
        context_.getBuiltBeans().addEntry("bean_two", bean_);
        context_.clearPages();
        context_.setDocument(doc_);
        return rendDocumentBlock_;
    }
    protected static RendDocumentBlock buildThree(String html_, String htmlTwo_, String htmlThree_, Configuration context_) {
        setLocale("en", context_);
        Document doc_ = DocumentBuilder.parseSaxNotNullRowCol(html_).getDocument();
        Document docSec_ = DocumentBuilder.parseSaxNotNullRowCol(htmlTwo_).getDocument();
        Document docThird_ = DocumentBuilder.parseSaxNotNullRowCol(htmlThree_).getDocument();
        CustList<RendDynOperationNode> ops_ = RenderExpUtil.getAnalyzedOperations("$new pkg.BeanOne()", 0, context_);
        BeanInfo b1_ = new BeanInfo();
        b1_.setExps(ops_);
        b1_.setClassName("pkg.BeanOne");
        b1_.setResolvedClassName(ops_.last().getResultClass().getSingleNameOrEmpty());
        context_.getBeansInfos().addEntry("bean_one",b1_);
        CustList<RendDynOperationNode> ops2_ = RenderExpUtil.getAnalyzedOperations("$new pkg.BeanTwo()", 0, context_);
        BeanInfo b2_ = new BeanInfo();
        b2_.setExps(ops2_);
        b2_.setClassName("pkg.BeanTwo");
        b2_.setResolvedClassName(ops2_.last().getResultClass().getSingleNameOrEmpty());
        context_.getBeansInfos().addEntry("bean_two", b2_);
        CustList<RendDynOperationNode> ops3_ = RenderExpUtil.getAnalyzedOperations("$new pkg.BeanThree()", 0, context_);
        BeanInfo b3_ = new BeanInfo();
        b3_.setExps(ops3_);
        b3_.setClassName("pkg.BeanThree");
        b3_.setResolvedClassName(ops3_.last().getResultClass().getSingleNameOrEmpty());
        context_.getBeansInfos().addEntry("bean_three", b3_);
        RendDocumentBlock rendDocumentBlock_ = RendBlock.newRendDocumentBlock(context_, "c:", doc_, html_);
        RendDocumentBlock rendDocumentBlockSec_ = RendBlock.newRendDocumentBlock(context_, "c:", docSec_, htmlTwo_);
        RendDocumentBlock rendDocumentBlockThird_ = RendBlock.newRendDocumentBlock(context_, "c:", docThird_, htmlThree_);
        context_.getRenders().put("page1.html",rendDocumentBlock_);
        context_.getRenders().put("page2.html",rendDocumentBlockSec_);
        context_.getRenders().put("page3.html",rendDocumentBlockThird_);
        setupAna(context_);
        rendDocumentBlock_.buildFctInstructions(context_);
        rendDocumentBlockSec_.buildFctInstructions(context_);
        rendDocumentBlockThird_.buildFctInstructions(context_);
        tryInitStaticlyTypes(context_);
        Struct bean_ = RenderExpUtil.calculateReuse(ops_, context_).getStruct();
        context_.getBuiltBeans().addEntry("bean_one", bean_);
        bean_ = RenderExpUtil.calculateReuse(ops2_, context_).getStruct();
        context_.getBuiltBeans().addEntry("bean_two", bean_);
        bean_ = RenderExpUtil.calculateReuse(ops3_, context_).getStruct();
        context_.getBuiltBeans().addEntry("bean_three", bean_);
        context_.clearPages();
        context_.setDocument(doc_);
        return rendDocumentBlock_;
    }

    protected static void tryInitStaticlyTypes(Configuration _context) {
        if (_context.isEmptyErrors()) {
            Classes.forwardAndClear(_context.getContext());
            AnalysisMessages analysisMessages_ = _context.getContext().getAnalyzing().getAnalysisMessages();
            ReportedMessages messages_ = _context.getContext().getAnalyzing().getMessages();
            Classes.tryInitStaticlyTypes(_context.getContext(),analysisMessages_,messages_, _context.getContext().getAnalyzing().getOptions());
        }
        addInnerPage(_context);
    }

    protected static Struct getStruct(Struct _struct, ClassField _cl) {
        return ((FieldableStruct) _struct).getEntryStruct(_cl).getStruct();
    }
    protected static void setStruct(Struct _struct, ClassField _cl, Struct _value) {
        ((FieldableStruct) _struct).getEntryStruct(_cl).setStruct(_value);
    }
    protected static Struct getException(Configuration _cont) {
        CallingState str_ = _cont.getContext().getCallingState();
        if (str_ instanceof Struct) {
            return (Struct) str_;
        }
        return null;
    }

    public static Argument processEl(String _el, int _index, Configuration _conf) {
        ContextEl context_ = _conf.getContext();
        CustList<RendDynOperationNode> out_ = getAnalyzed(_el,_index,_conf);
        if (!_conf.isEmptyErrors()) {
            FoundErrorInterpret badEl_ = new FoundErrorInterpret();
            badEl_.setFileName(_conf.getCurrentFileName());
            badEl_.setIndexFile(_conf.getCurrentLocationIndex());
            badEl_.setLocationFile(_conf.getLocationFile(badEl_.getFileName(),badEl_.getIndexFile()));
            badEl_.buildError(_conf.getContext().getAnalysisMessages().getBadExpression(),
                    " ",
                    Integer.toString(_index),
                    _el);
            _conf.setException(new ErrorStruct(_conf.getContext(), badEl_.display(), _conf.getStandards().getAliasIllegalArg()));
            return Argument.createVoid();
        }
        Classes.forwardAndClear(context_);
        for (ClassMetaInfo c: context_.getClasses().getClassMetaInfos()) {
            String name_ = c.getName();
            ClassMetaInfo.forward(ExecutingUtil.getClassMetaInfo(context_, name_), c);
        }
        out_ = RenderExpUtil.getReducedNodes(out_.last());
        return RenderExpUtil.calculateReuse(out_, _conf);
    }

    private static CustList<RendDynOperationNode> getAnalyzed(String _el, int _index, Configuration _conf) {
        setupAnalyzing(_conf);
        Argument argGl_ = _conf.getPageEl().getGlobalArgument();
        boolean static_ = argGl_.isNull();
        _conf.getAnalyzing().setAccessStaticContext(MethodId.getKind(static_));
        Delimiters d_ = ElResolver.checkSyntax(_el, _conf.getContext(), _index);
        int badOffset_ = d_.getBadOffset();
        if (badOffset_ >= 0) {
            _conf.getLastPage().setOffset(badOffset_);
            FoundErrorInterpret badEl_ = new FoundErrorInterpret();
            badEl_.setFileName(_conf.getCurrentFileName());
            badEl_.setIndexFile(_conf.getCurrentLocationIndex());
            badEl_.buildError(_conf.getContext().getAnalysisMessages().getBadExpression(),
                    " ",
                    Integer.toString(badOffset_),
                    _el);
            _conf.addError(badEl_);
            _conf.setException(new ErrorStruct(_conf.getContext(), badEl_.display(), _conf.getStandards().getAliasIllegalArg()));
            return new CustList<RendDynOperationNode>();
        }
        String el_ = _el.substring(_index);
        OperationsSequence opTwo_ = RenderExpUtil.getOperationsSequence(_index, el_, _conf, d_);
        OperationNode op_ = RenderExpUtil.createOperationNode(_index, CustList.FIRST_INDEX, null, opTwo_, _conf);
        CustList<OperationNode> all_ = RenderExpUtil.getSortedDescNodes(op_, _conf);
        return RenderExpUtil.getExecutableNodes(all_,_conf.getContext());
    }
}
