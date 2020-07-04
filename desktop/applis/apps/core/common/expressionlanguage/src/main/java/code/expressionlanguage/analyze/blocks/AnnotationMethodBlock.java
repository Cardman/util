package code.expressionlanguage.analyze.blocks;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.analyze.inherits.AnaTemplates;
import code.expressionlanguage.common.*;
import code.expressionlanguage.exec.blocks.ExecAnnotationBlock;
import code.expressionlanguage.exec.blocks.ExecAnnotationMethodBlock;
import code.expressionlanguage.exec.blocks.ExecEnumBlock;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.files.OffsetAccessInfo;
import code.expressionlanguage.files.OffsetStringInfo;
import code.expressionlanguage.files.OffsetsBlock;
import code.expressionlanguage.analyze.inherits.Mapping;
import code.expressionlanguage.functionid.MethodAccessKind;
import code.expressionlanguage.functionid.MethodId;
import code.expressionlanguage.inherits.ClassArgumentMatching;
import code.expressionlanguage.inherits.PrimitiveTypeUtil;
import code.expressionlanguage.instr.ElUtil;
import code.expressionlanguage.analyze.opers.Calculation;
import code.expressionlanguage.exec.opers.ExecOperationNode;
import code.expressionlanguage.analyze.opers.OperationNode;
import code.expressionlanguage.stds.LgNames;
import code.util.CustList;
import code.util.Ints;
import code.util.StringList;
import code.util.StringMap;

public final class AnnotationMethodBlock extends NamedFunctionBlock implements
        GeneCustStaticMethod {

    private String defaultValue;
    private OperationNode root;
    private int defaultValueOffset;
    private int rightPar;
    private boolean ko;

    public AnnotationMethodBlock(OffsetStringInfo _retType, OffsetStringInfo _fctName,
                                 OffsetStringInfo _defaultValue,
                                 OffsetsBlock _offset, int _rightPar) {
        super(new OffsetAccessInfo(0, AccessEnum.PUBLIC),
                _retType, _fctName,
                new StringList(), new Ints(), new StringList(), new Ints(),
                _offset);
        defaultValue = _defaultValue.getInfo();
        defaultValueOffset = _defaultValue.getOffset();
        rightPar = _rightPar;
    }

    @Override
    public MethodAccessKind getStaticContext() {
        return MethodAccessKind.INSTANCE;
    }

    @Override
    public MethodId getId() {
        return new MethodId(MethodAccessKind.INSTANCE, getName(), new StringList(), false);
    }

    @Override
    public void buildImportedReturnTypes(ContextEl _stds) {
        super.buildImportedReturnTypes(_stds);
        LgNames stds_ = _stds.getStandards();
        String string_ = stds_.getAliasString();
        String class_ = stds_.getAliasClassType();
        String itype_ = getImportedReturnType();
        String type_ = itype_;
        String ctype_ = StringExpUtil.getQuickComponentType(type_);
        if (ctype_ != null) {
            type_ = ctype_;
        }
        if (PrimitiveTypeUtil.isPrimitiveOrWrapper(type_, _stds)) {
            return;
        }
        GeneType r_ = _stds.getClassBody(type_);
        if (r_ instanceof ExecAnnotationBlock) {
            return;
        }
        if (r_ instanceof ExecEnumBlock) {
            return;
        }
        if (StringList.quickEq(type_, string_)) {
            return;
        }
        if (StringList.quickEq(type_, class_)) {
            return;
        }
        FoundErrorInterpret cast_ = new FoundErrorInterpret();
        cast_.setFileName(_stds.getAnalyzing().getLocalizer().getCurrentFileName());
        cast_.setIndexFile(_stds.getAnalyzing().getLocalizer().getCurrentLocationIndex());
        //return type len
        cast_.buildError(_stds.getAnalysisMessages().getUnexpectedType(),
                itype_);
        _stds.addError(cast_);
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public int getDefaultValueOffset() {
        return defaultValueOffset;
    }

    @Override
    public boolean isStaticMethod() {
        return false;
    }

    public boolean isFinalMethod() {
        return false;
    }

    public boolean isAbstractMethod() {
        return true;
    }

    public boolean isNormalMethod() {
        return false;
    }

    public void buildExpressionLanguage(ContextEl _cont, ExecAnnotationMethodBlock _exec) {
        AnalyzedPageEl page_ = _cont.getAnalyzing();
        if (defaultValue.trim().isEmpty()) {
            _exec.setOpValue(new CustList<ExecOperationNode>());
            return;
        }
        page_.setGlobalOffset(defaultValueOffset);
        page_.setOffset(0);
        _cont.getCoverage().putBlockOperationsField(_cont,this);
        CustList<ExecOperationNode> ops_ = ElUtil.getAnalyzedOperationsReadOnly(defaultValue, _cont, Calculation.staticCalculation(MethodAccessKind.STATIC));
        root = page_.getCurrentRoot();
        _exec.setOpValue(ops_);
        String import_ = getImportedReturnType();
        StringMap<StringList> vars_ = new StringMap<StringList>();
        Mapping mapping_ = new Mapping();
        mapping_.setMapping(vars_);
        ClassArgumentMatching arg_ = ops_.last().getResultClass();
        mapping_.setArg(arg_);
        mapping_.setParam(import_);
        if (!AnaTemplates.isCorrectOrNumbers(mapping_, _cont)) {
            FoundErrorInterpret cast_ = new FoundErrorInterpret();
            cast_.setFileName(getFile().getFileName());
            cast_.setIndexFile(defaultValueOffset);
            //parentheses
            cast_.buildError(_cont.getAnalysisMessages().getBadImplicitCast(),
                    StringList.join(arg_.getNames(),"&"),
                    import_);
            _cont.addError(cast_);
        }
        if (PrimitiveTypeUtil.isPrimitive(import_, _cont)) {
            ops_.last().getResultClass().setUnwrapObject(import_);
        }
    }


    public OperationNode getRoot() {
        return root;
    }

    public void setKo() {
        ko = true;
    }

    public boolean isKo() {
        return ko;
    }

    public int getRightPar() {
        return rightPar;
    }
}
