package code.expressionlanguage.analyze.blocks;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.AccessEnum;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.exec.blocks.ExecAnnotableBlock;
import code.expressionlanguage.exec.blocks.ExecAnnotableParametersBlock;
import code.expressionlanguage.files.OffsetAccessInfo;
import code.expressionlanguage.files.OffsetStringInfo;
import code.expressionlanguage.files.OffsetsBlock;
import code.expressionlanguage.instr.ElUtil;
import code.expressionlanguage.instr.PartOffset;
import code.expressionlanguage.analyze.opers.Calculation;
import code.expressionlanguage.exec.opers.ExecOperationNode;
import code.expressionlanguage.analyze.opers.OperationNode;
import code.expressionlanguage.functionid.MethodAccessKind;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.analyze.types.ResolvingImportTypes;
import code.util.CustList;
import code.util.Ints;
import code.util.StringList;

public abstract class NamedFunctionBlock extends MemberCallingsBlock implements AnnotableParametersBlock {
    private StringList annotations = new StringList();

    private Ints annotationsIndexes = new Ints();

    private final String name;

    private int nameOffset;

    private final StringList parametersTypes;

    private Ints parametersTypesOffset;

    private final String returnType;

    private String importedReturnType;

    private final StringList importedParametersTypes;

    private int returnTypeOffset;

    private final StringList parametersNames;

    private Ints parametersNamesOffset;

    private final AccessEnum access;

    private int accessOffset;

    private final boolean varargs;
    private CustList<StringList> annotationsParams = new CustList<StringList>();
    private CustList<Ints> annotationsIndexesParams = new CustList<Ints>();

    private CustList<CustList<PartOffset>> partOffsetsParams = new CustList<CustList<PartOffset>>();
    private CustList<PartOffset> partOffsetsReturn = new CustList<PartOffset>();

    private CustList<OperationNode> roots = new CustList<OperationNode>();
    private CustList<CustList<OperationNode>> rootsList = new CustList<CustList<OperationNode>>();

    private final StringList nameErrors = new StringList();
    private final CustList<StringList> paramErrors = new CustList<StringList>();
    private int nameNumber;

    public NamedFunctionBlock(OffsetAccessInfo _access,
                              OffsetStringInfo _retType, OffsetStringInfo _fctName,
                              StringList _paramTypes, Ints _paramTypesOffset,
                              StringList _paramNames, Ints _paramNamesOffset,
                              OffsetsBlock _offset) {
        super(_offset);
        importedParametersTypes = new StringList();
        name = _fctName.getInfo();
        nameOffset = _fctName.getOffset();
        parametersTypes = new StringList();
        int i_ = CustList.FIRST_INDEX;
        int len_ = _paramTypes.size();
        boolean varargs_ = false;
        while (i_ < len_) {
            String className_ = _paramTypes.get(i_);
            if (i_+1 == len_) {
                varargs_ = className_.endsWith(VARARG);
                if (varargs_) {
                    parametersTypes.add(className_.substring(CustList.FIRST_INDEX, className_.length()-VARARG.length()));
                } else {
                    parametersTypes.add(className_);
                }
            } else {
                parametersTypes.add(className_);
            }
            i_++;
        }
        varargs = varargs_;
        access = _access.getInfo();
        accessOffset = _access.getOffset();
        returnType = _retType.getInfo();
        returnTypeOffset = _retType.getOffset();
        parametersNames = new StringList();
        i_ = CustList.FIRST_INDEX;
        while (i_ < len_) {
            parametersNames.add(_paramNames.get(i_));
            i_++;
        }
        parametersTypesOffset = _paramTypesOffset;
        parametersNamesOffset = _paramNamesOffset;
    }


    public void buildAnnotations(ContextEl _context, ExecAnnotableBlock _ex) {
        buildAnnotationsBasic(_context,_ex);
    }

    @Override
    public void buildAnnotationsParameters(ContextEl _context, ExecAnnotableParametersBlock _ann) {
        CustList<CustList<CustList<ExecOperationNode>>> ops_ = new CustList<CustList<CustList<ExecOperationNode>>>();
        int j_ = 0;
        rootsList = new CustList<CustList<OperationNode>>();
        for (Ints l: annotationsIndexesParams) {
            CustList<CustList<ExecOperationNode>> annotation_;
            annotation_ = new CustList<CustList<ExecOperationNode>>();
            CustList<OperationNode> rootList_ = new CustList<OperationNode>();
            int len_ = l.size();
            AnalyzedPageEl page_ = _context.getAnalyzing();
            StringList list_ = annotationsParams.get(j_);
            for (int i = 0; i < len_; i++) {
                int begin_ = l.get(i);
                page_.setGlobalOffset(begin_);
                page_.setOffset(0);
                Calculation c_ = Calculation.staticCalculation(MethodAccessKind.STATIC);
                annotation_.add(ElUtil.getAnalyzedOperationsReadOnly(list_.get(i), _context, c_));
                rootList_.add(page_.getCurrentRoot());
            }
            rootsList.add(rootList_);
            ops_.add(annotation_);
            j_++;
        }
        _ann.getAnnotationsOpsParams().addAllElts(ops_);
    }

    @Override
    public void setAssignmentAfterCallReadOnly(ContextEl _an, AnalyzingEl _anEl) {
        checkReturnFct(_an, _anEl);
    }

    private void checkReturnFct(ContextEl _an, AnalyzingEl _anEl) {
        LgNames stds_ = _an.getStandards();
        if (!StringList.quickEq(getImportedReturnType(), stds_.getAliasVoid())) {
            if (_anEl.canCompleteNormally(this)) {
                //error
                FoundErrorInterpret miss_ = new FoundErrorInterpret();
                miss_.setIndexFile(getOffset().getOffsetTrim());
                miss_.setFileName(getFile().getFileName());
                //return type len
                miss_.buildError(_an.getAnalysisMessages().getMissingAbrupt(),
                        _an.getKeyWords().getKeyWordThrow(),
                        _an.getKeyWords().getKeyWordReturn(),
                        getPseudoSignature(_an));
                _an.addError(miss_);
                addNameErrors(miss_);
            }
        }
    }

    public Ints getParametersTypesOffset() {
        return parametersTypesOffset;
    }

    public Ints getParametersNamesOffset() {
        return parametersNamesOffset;
    }

    public int getNameOffset() {
        return nameOffset;
    }

    public int getAccessOffset() {
        return accessOffset;
    }

    public int getReturnTypeOffset() {
        return returnTypeOffset;
    }

    public String getName() {
        return name;
    }

    public final StringList getParametersTypes() {
        return new StringList(parametersTypes);
    }

    public final void buildImportedTypes(ContextEl _stds) {
        StringList params_ = new StringList();
        int i_ = 0;
        AnalyzedPageEl page_ = _stds.getAnalyzing();
        page_.setCurrentBlock(this);
        page_.setCurrentAnaBlock(this);
        page_.setCurrentFct(this);
        for (String p: parametersTypes) {
            CustList<PartOffset> partOffsets_ = new CustList<PartOffset>();
            page_.setGlobalOffset(parametersTypesOffset.get(i_));
            page_.setOffset(0);
            params_.add(ResolvingImportTypes.resolveCorrectType(_stds,p));
            partOffsets_.addAllElts(_stds.getAnalyzing().getCurrentParts());
            partOffsetsParams.add(partOffsets_);
            i_++;
        }
        importedParametersTypes.clear();
        importedParametersTypes.addAllElts(params_);
        buildImportedReturnTypes(_stds);
    }

    public void buildImportedReturnTypes(ContextEl _stds) {
        String void_ = _stds.getStandards().getAliasVoid();
        if (StringList.quickEq(returnType.trim(), void_)) {
            importedReturnType = void_;
            return;
        }
        AnalyzedPageEl page_ = _stds.getAnalyzing();
        page_.setCurrentBlock(this);
        page_.setCurrentAnaBlock(this);
        page_.setGlobalOffset(returnTypeOffset);
        page_.setOffset(0);
        importedReturnType = ResolvingImportTypes.resolveCorrectType(_stds,returnType);
        partOffsetsReturn.addAllElts(_stds.getAnalyzing().getCurrentParts());
    }
    public String getReturnType() {
        return returnType;
    }

    public final StringList getParametersNames() {
        return new StringList(parametersNames);
    }

    public final boolean isVarargs() {
        return varargs;
    }

    public final AccessEnum getAccess() {
        return access;
    }

    public StringList getImportedParametersTypes() {
        return importedParametersTypes;
    }

    public String getImportedReturnType() {
        return importedReturnType;
    }

    public void buildAnnotationsBasic(ContextEl _context, ExecAnnotableBlock _ex) {
        CustList<CustList<ExecOperationNode>> ops_ = new CustList<CustList<ExecOperationNode>>();
        roots = new CustList<OperationNode>();
        int len_ = annotationsIndexes.size();
        AnalyzedPageEl page_ = _context.getAnalyzing();
        for (int i = 0; i < len_; i++) {
            int begin_ = annotationsIndexes.get(i);
            page_.setGlobalOffset(begin_);
            page_.setOffset(0);
            Calculation c_ = Calculation.staticCalculation(MethodAccessKind.STATIC);
            ops_.add(ElUtil.getAnalyzedOperationsReadOnly(annotations.get(i), _context, c_));
            roots.add(page_.getCurrentRoot());
        }
        _ex.getAnnotationsOps().addAllElts(ops_);
    }

    public StringList getAnnotations() {
        return annotations;
    }

    public Ints getAnnotationsIndexes() {
        return annotationsIndexes;
    }
    public void setImportedReturnType(String _importedReturnType) {
        importedReturnType = _importedReturnType;
    }

    public CustList<StringList> getAnnotationsParams() {
        return annotationsParams;
    }

    public CustList<Ints> getAnnotationsIndexesParams() {
        return annotationsIndexesParams;
    }

    public CustList<CustList<PartOffset>> getPartOffsetsParams() {
        return partOffsetsParams;
    }

    public CustList<PartOffset> getPartOffsetsReturn() {
        return partOffsetsReturn;
    }

    public CustList<OperationNode> getRoots() {
        return roots;
    }

    public CustList<CustList<OperationNode>> getRootsList() {
        return rootsList;
    }

    public void addNameErrors(FoundErrorInterpret _error) {
        nameErrors.add(_error.getBuiltError());
    }

    public void addParamErrors() {
        paramErrors.add(new StringList());
    }
    public void addParamErrors(int _i,FoundErrorInterpret _error) {
        paramErrors.get(_i).add(_error.getBuiltError());
    }
    public StringList getNameErrors() {
        return nameErrors;
    }

    public CustList<StringList> getParamErrors() {
        return paramErrors;
    }

    public int getNameNumber() {
        return nameNumber;
    }

    public void setNameNumber(int nameNumber) {
        this.nameNumber = nameNumber;
    }
}
