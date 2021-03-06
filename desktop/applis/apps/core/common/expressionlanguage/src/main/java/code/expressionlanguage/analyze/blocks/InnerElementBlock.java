package code.expressionlanguage.analyze.blocks;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.inherits.AnaInherits;
import code.expressionlanguage.analyze.reach.opers.ReachOperationUtil;
import code.expressionlanguage.analyze.syntax.ResultExpression;
import code.expressionlanguage.analyze.types.ResolvingTypes;
import code.expressionlanguage.common.AccessEnum;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.analyze.files.OffsetAccessInfo;
import code.expressionlanguage.analyze.files.OffsetStringInfo;
import code.expressionlanguage.analyze.instr.ElUtil;
import code.expressionlanguage.analyze.instr.PartOffset;
import code.expressionlanguage.analyze.instr.PartOffsetAffect;
import code.expressionlanguage.analyze.opers.Calculation;
import code.expressionlanguage.analyze.opers.OperationNode;
import code.expressionlanguage.fwd.blocks.AnaElementContent;
import code.expressionlanguage.options.KeyWords;
import code.util.*;
import code.util.core.StringUtil;

public final class InnerElementBlock extends RootBlock implements InnerTypeOrElement,UniqueRootedBlock {

    private final AnaElementContent elementContent;

    private String importedClassName;

    private final CustList<PartOffset> partOffsets = new CustList<PartOffset>();
    private int trOffset;
    private final ResultExpression res = new ResultExpression();
    private int fieldNumber;
    private final StringList fieldList = new StringList();
    private int numberInner = -1;
    private final CustList<AnonymousTypeBlock> anonymous = new CustList<AnonymousTypeBlock>();
    private final CustList<NamedCalledFunctionBlock> anonymousFct = new CustList<NamedCalledFunctionBlock>();
    private final CustList<SwitchMethodBlock> switchMethods = new CustList<SwitchMethodBlock>();
    public InnerElementBlock(EnumBlock _m, String _pkgName,OffsetStringInfo _fieldName,
                             OffsetStringInfo _type,
                             OffsetStringInfo _value, int _offset) {
        super(_fieldName.getOffset(), _pkgName, new OffsetAccessInfo(0,AccessEnum.PUBLIC), "", new IntMap< String>(), _offset, _fieldName.getInfo());
        elementContent = new AnaElementContent(_m, _fieldName, _type, _value);
        setupOffsets(_fieldName.getInfo().trim(),_pkgName);
        setParentType(_m);
    }

    @Override
    public RootBlock getDeclaringType() {
        return getParentEnum();
    }

    public EnumBlock getParentEnum() {
        return elementContent.getParentEnum();
    }

    @Override
    public void setupBasicOverrides(AnalyzedPageEl _page) {
        checkAccess(_page);
    }

    public String getValue() {
        return elementContent.getValue();
    }

    public String getTempClass() {
        return elementContent.getTempClass();
    }

    public int getTempClassOffset() {
        return elementContent.getTempClassOffset();
    }

    @Override
    public boolean mustImplement() {
        return true;
    }

    @Override
    public String getUniqueFieldName() {
        return elementContent.getFieldName();
    }

    @Override
    public void retrieveNames(StringList _fieldNames, AnalyzedPageEl _page) {
        CustList<PartOffsetAffect> fields_ = new CustList<PartOffsetAffect>();
        fields_.add(new PartOffsetAffect(new FieldPartOffset(elementContent.getFieldName(),elementContent.getValueOffest()),true));
        FieldBlock.checkFieldsNames(this,_fieldNames,fields_, _page);
        for (PartOffsetAffect n: fields_) {
            getNameErrors().addAllElts(n.getErrs());
        }
        for (PartOffsetAffect n: fields_) {
            StringList errs_ = n.getErrs();
            FieldPartOffset p_ = n.getPartOffset();
            String name_ = p_.getName();
            if (errs_.isEmpty()) {
                fieldList.add(name_);
            }
        }
    }

    @Override
    public int getFieldNameOffset() {
        return elementContent.getFieldNameOffest();
    }

    public int getValueOffest() {
        return elementContent.getValueOffest();
    }

    public void buildExpressionLanguageReadOnly(AnalyzedPageEl _page) {
        _page.setGlobalOffset(elementContent.getFieldNameOffest());
        _page.zeroOffset();
        KeyWords keyWords_ = _page.getKeyWords();
        String newKeyWord_ = keyWords_.getKeyWordNew();
        String fullInstance_ = buildVirtualCreate(newKeyWord_);
        int trOffset_ = retrieveTr(newKeyWord_);
        trOffset = trOffset_;
        _page.setTranslatedOffset(trOffset_);
        res.setRoot(ElUtil.getRootAnalyzedOperationsReadOnly(res, fullInstance_, new Calculation(this, getNameErrors()), _page));
        ReachOperationUtil.tryCalculate(res.getRoot(), _page);
        _page.setTranslatedOffset(0);
    }

    public String buildVirtualCreate(String _newKeyWord) {
        return StringUtil.concat(elementContent.getFieldName(),"=", _newKeyWord, PAR_LEFT, elementContent.getValue(), PAR_RIGHT);
    }

    public int retrieveTr(String _newKeyWord) {
        return elementContent.getValueOffest() - elementContent.getFieldNameOffest() + diffTr(_newKeyWord);
    }

    public int diffTr(String _newKeyWord) {
        return elementContent.diffTr(_newKeyWord);
    }


    @Override
    public boolean withoutInstance() {
        return true;
    }

    @Override
    public String getImportedClassName() {
        return getFullName();
    }

    @Override
    public String getRealImportedClassName() {
        return importedClassName;
    }

    @Override
    public void buildImportedType(AnalyzedPageEl _page) {
        _page.setGlobalOffset(elementContent.getTempClassOffset());
        _page.zeroOffset();
        _page.setCurrentBlock(this);
        int i_ = 1;
        StringList j_ = new StringList();
        String fullName_ = elementContent.getParentEnum().getFullName();
        for (String p: StringExpUtil.getAllTypes(StringUtil.concat(fullName_, elementContent.getTempClass())).mid(1)) {
            int loc_ = StringUtil.getFirstPrintableCharIndex(p);
            j_.add(ResolvingTypes.resolveCorrectType(i_+loc_,p, _page));
            partOffsets.addAllElts(_page.getCurrentParts());
            i_ += p.length() + 1;
        }
        StringMap<StringList> varsCt_ = _page.getCurrentConstraints().getCurrentConstraints();
        StringList errs_ = new StringList();
        importedClassName = AnaInherits.check(errs_,fullName_,j_,varsCt_, _page);
        for (String e: errs_) {
            addNameErrors(e);
        }
    }

    public ResultExpression getRes() {
        return res;
    }

    @Override
    public CustList<PartOffset> getTypePartOffsets() {
        return partOffsets;
    }

    @Override
    public boolean isFinalField() {
        return true;
    }

    @Override
    public boolean isStaticField() {
        return true;
    }


    @Override
    public StringList getFieldName() {
        return fieldList;
    }

    public int getFieldNameOffest() {
        return elementContent.getFieldNameOffest();
    }

    public int getTrOffset() {
        return trOffset;
    }

    public OperationNode getRoot() {
        return res.getRoot();
    }

    @Override
    public int getFieldNumber() {
        return fieldNumber;
    }

    @Override
    public void setFieldNumber(int _fieldNumber) {
        this.fieldNumber = _fieldNumber;
    }

    public int getNumberInner() {
        return numberInner;
    }

    public void setNumberInner(int _numberInner) {
        this.numberInner = _numberInner;
    }

    @Override
    public CustList<AnonymousTypeBlock> getAnonymous() {
        return anonymous;
    }

    @Override
    public CustList<NamedCalledFunctionBlock> getAnonymousFct() {
        return anonymousFct;
    }

    @Override
    public CustList<SwitchMethodBlock> getSwitchMethods() {
        return switchMethods;
    }

    public AnaElementContent getElementContent() {
        return elementContent;
    }
}
