package code.formathtml;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.blocks.RootBlock;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.analyze.blocks.AccessedBlock;
import code.expressionlanguage.analyze.blocks.AccessibleBlock;
import code.expressionlanguage.analyze.blocks.ExecAccessingImportingBlock;
import code.expressionlanguage.files.OffsetsBlock;
import code.expressionlanguage.common.AccessEnum;
import code.expressionlanguage.functionid.MethodAccessKind;
import code.formathtml.util.AnalyzingDoc;
import code.sml.Element;
import code.util.StringList;
import code.util.CustList;

public final class RendDocumentBlock extends RendParentBlock implements AccessedBlock,ExecAccessingImportingBlock {

    private Element elt;

    private String file;
    private String fileName;
    private String beanName;
    private CustList<RendBlock> bodies = new CustList<RendBlock>();
    private StringList imports = new StringList();
    public RendDocumentBlock(Element _elt, String _file, OffsetsBlock _offset, String _fileName) {
        super(_offset);
        elt = _elt;
        file = _file;
        fileName = _fileName;
    }

    public void buildFctInstructions(Configuration _cont, AnalyzingDoc _anaDoc, AnalyzedPageEl _page) {
        beanName = elt.getAttribute(StringList.concat(_cont.getPrefix(),_cont.getRendKeyWords().getAttrBean()));
        imports = StringList.splitChar(elt.getAttribute(StringList.concat(_cont.getPrefix(),_cont.getRendKeyWords().getAttrAlias())),';');
        _page.setGlobalOffset(getOffset().getOffsetTrim());
        _page.setOffset(0);
        _page.setAccessStaticContext(MethodAccessKind.STATIC);
        _page.setGlobalDirType(null);
        if (_cont.getBeansInfos().contains(beanName)) {
            _page.setAccessStaticContext(MethodAccessKind.INSTANCE);
            String clName_ = _cont.getBeansInfos().getVal(beanName).getResolvedClassName();
            _page.setGlobalClass(clName_);
            _page.setGlobalType(_page.getAnaClassBody(StringExpUtil.getIdFromAllTypes(clName_)));
        } else {
            _page.setGlobalClass("");
            _page.setGlobalType((RootBlock)null);
        }
        RendBlock en_ = this;
        CustList<RendParentBlock> parents_ = new CustList<RendParentBlock>();
        CustList<RendBreakableBlock> parentsBreakables_ = new CustList<RendBreakableBlock>();
        CustList<RendLoop> parentsContinuable_ = new CustList<RendLoop>();
        CustList<RendEval> parentsReturnable_ = new CustList<RendEval>();
        StringList labels_ = new StringList();
        _anaDoc.setFileName(fileName);
        _anaDoc.setCurrentDoc(this);
        while (true) {
            _anaDoc.setCurrentBlock(en_);
            _page.setCurrentAnaBlock(en_);
            if (en_ instanceof RendStdElement) {
                if (StringList.quickEq(((RendStdElement)en_).getRead().getTagName(),_cont.getRendKeyWords().getKeyWordBody())) {
                    bodies.add(en_);
                }
            }
            checkBreakable(en_, labels_, _anaDoc, _page);
            if (en_ instanceof RendParentBlock) {
                RendBlock first_ = en_.getFirstChild();
                if (first_ == null) {
                    OffsetsBlock off_ = en_.getOffset();
                    RendEmptyInstruction empty_ = new RendEmptyInstruction(off_);
                    ((RendParentBlock)en_).appendChild(empty_);
                }
                if (en_ instanceof RendBreakableBlock) {
                    parentsBreakables_.add((RendBreakableBlock) en_);
                }
                if (en_ instanceof RendLoop) {
                    parentsContinuable_.add((RendLoop) en_);
                }
                if (en_ instanceof RendEval && !(en_ instanceof RendFinallyEval)) {
                    RendBlock ne_ = en_;
                    boolean fin_ = false;
                    while (ne_ instanceof RendEval) {
                        if (ne_ instanceof RendFinallyEval) {
                            fin_ = true;
                            break;
                        }
                        ne_ = ne_.getNextSibling();
                    }
                    if (fin_) {
                        parentsReturnable_.add((RendEval) en_);
                    }
                }
                parents_.add((RendParentBlock) en_);
            }
            RendBlock n_ = en_.getFirstChild();
            tryBuildExpressionLanguage(en_, _cont,this, _anaDoc);
            reduce(en_,_cont);
            if (n_ != null) {
                en_ = n_;
                continue;
            }
            while (true) {
                n_ = en_.getNextSibling();
                if (n_ != null) {
                    en_ = n_;
                    break;
                }
                RendParentBlock par_;
                par_ = en_.getParent();
                if (par_ == this) {
                    par_.removeAllVars(_page);
                    return;
                }
                parents_.removeLast();
                if (par_ instanceof RendBreakableBlock) {
                    parentsBreakables_.removeLast();
                }
                if (par_ instanceof RendLoop) {
                    parentsContinuable_.removeLast();
                }
                if (par_ instanceof RendEval && !(par_ instanceof RendFinallyEval)) {
                    RendBlock ne_ = par_;
                    boolean fin_ = false;
                    while (ne_ instanceof RendEval) {
                        if (ne_ instanceof RendFinallyEval) {
                            fin_ = true;
                            break;
                        }
                        ne_ = ne_.getNextSibling();
                    }
                    if (fin_) {
                        parentsReturnable_.removeLast();
                    }
                }
                par_.removeAllVars(_page);
                if (par_ instanceof RendBreakableBlock && !((RendBreakableBlock)par_).getRealLabel().isEmpty()) {
                    labels_.removeLast();
                }
                en_ = par_;
            }
        }
    }

    static void reduce(RendBlock _block,Configuration _cont) {
        if (_block instanceof RendReducableOperations) {
            ((RendReducableOperations)_block).reduce(_cont);
        }
    }
    private static void checkBreakable(RendBlock _block, StringList _labels, AnalyzingDoc _anaDoc, AnalyzedPageEl _page) {
        if (_block instanceof RendBreakableBlock) {
            String label_ = ((RendBreakableBlock)_block).getRealLabel();
            boolean wc_ = true;
            for (char c: label_.toCharArray()) {
                if (StringList.isDollarWordChar(c)) {
                    continue;
                }
                wc_ = false;
                break;
            }
            if (!wc_) {
                FoundErrorInterpret bad_ = new FoundErrorInterpret();
                bad_.setFileName(_anaDoc.getFileName());
                bad_.setIndexFile(_block.getOffset().getOffsetTrim());
                bad_.buildError(_page.getAnalysisMessages().getBadLabel());
                Configuration.addError(bad_, _anaDoc, _page);
            } else if (!label_.isEmpty()){
                if (StringList.contains(_labels, label_)) {
                    FoundErrorInterpret dup_ = new FoundErrorInterpret();
                    dup_.setFileName(_anaDoc.getFileName());
                    dup_.setIndexFile(_block.getOffset().getOffsetTrim());
                    dup_.buildError(_page.getAnalysisMessages().getDuplicatedLabel());
                    Configuration.addError(dup_, _anaDoc, _page);
                } else {
                    _labels.add(label_);
                }
            }
        }
    }

    public Element getElt() {
        return elt;
    }

    public String getBeanName() {
        return beanName;
    }

    public CustList<RendBlock> getBodies() {
        return bodies;
    }

    @Override
    public void buildExpressionLanguage(Configuration _cont, RendDocumentBlock _doc, AnalyzingDoc _anaDoc) {
    }

    public String getFile() {
        return file;
    }

    @Override
    public StringList getFileImports() {
        return getImports();
    }

    @Override
    public StringList getImports() {
        return imports;
    }

    @Override
    public boolean isTypeHidden(AccessibleBlock _class, AnalyzedPageEl _analyzable) {
        return _class.getAccess() != AccessEnum.PUBLIC;
    }
}
