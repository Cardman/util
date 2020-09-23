package code.expressionlanguage.analyze.blocks;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.inherits.AnaTemplates;
import code.expressionlanguage.analyze.opers.IdFctOperation;
import code.expressionlanguage.analyze.types.GeneStringOverridable;
import code.expressionlanguage.analyze.types.ResolvingImportTypes;
import code.expressionlanguage.common.ExtractedParts;
import code.expressionlanguage.common.GeneCustStaticMethod;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.analyze.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.analyze.files.OffsetAccessInfo;
import code.expressionlanguage.analyze.files.OffsetStringInfo;
import code.expressionlanguage.analyze.files.OffsetsBlock;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.functionid.MethodAccessKind;
import code.expressionlanguage.functionid.MethodId;
import code.expressionlanguage.functionid.MethodModifier;
import code.expressionlanguage.inherits.Templates;
import code.expressionlanguage.analyze.instr.PartOffset;
import code.expressionlanguage.analyze.instr.PartOffsetsClassMethodId;
import code.expressionlanguage.options.KeyWords;
import code.expressionlanguage.stds.LgNames;
import code.util.CustList;
import code.util.Ints;
import code.util.StringList;
import code.util.StringMap;

public final class OverridableBlock extends NamedFunctionBlock implements GeneCustStaticMethod,ReturnableWithSignature {

    private int modifierOffset;

    private final boolean staticMethod;
    private final boolean staticCallMethod;

    private final boolean finalMethod;
    private final boolean abstractMethod;

    private final boolean normalMethod;
    private MethodKind kind;
    private CustList<PartOffsetsClassMethodId> allInternTypesParts = new CustList<PartOffsetsClassMethodId>();
    private String definition  = "";
    private int definitionOffset;
    private StringMap<GeneStringOverridable> overrides = new StringMap<GeneStringOverridable>();
    private int nameOverrideNumber;
    private String returnTypeGet = "";
    public OverridableBlock(OffsetAccessInfo _access,
                            OffsetStringInfo _retType, OffsetStringInfo _fctName,
                            StringList _paramTypes, Ints _paramTypesOffset,
                            StringList _paramNames, Ints _paramNamesOffset,
                            OffsetStringInfo _modifier, OffsetsBlock _offset, AnalyzedPageEl _page) {
        super(_access, _retType, _fctName, _paramTypes, _paramTypesOffset, _paramNames, _paramNamesOffset, _offset);
        modifierOffset = _modifier.getOffset();
        String modifier_ = _modifier.getInfo();
        KeyWords keyWords_ = _page.getKeyWords();
        String keyWordStatic_ = keyWords_.getKeyWordStatic();
        String keyWordStaticCall_ = keyWords_.getKeyWordStaticCall();
        String keyWordFinal_ = keyWords_.getKeyWordFinal();
        String keyWordAbstract_ = keyWords_.getKeyWordAbstract();
        String keyWordNormal_ = keyWords_.getKeyWordNormal();
        staticMethod = StringList.quickEq(modifier_, keyWordStatic_);
        staticCallMethod = StringList.quickEq(modifier_, keyWordStaticCall_);
        finalMethod = StringList.quickEq(modifier_, keyWordFinal_);
        abstractMethod = StringList.quickEq(modifier_, keyWordAbstract_);
        normalMethod = StringList.quickEq(modifier_, keyWordNormal_);
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setDefinitionOffset(int definitionOffset) {
        this.definitionOffset = definitionOffset;
    }

    @Override
    public String getSignature(AnalyzedPageEl _page) {
        return getId().getSignature(_page);
    }

    public int getModifierOffset() {
        return modifierOffset;
    }

    public MethodModifier getModifier() {
        if (abstractMethod) {
            return MethodModifier.ABSTRACT;
        }
        if (finalMethod) {
            return MethodModifier.FINAL;
        }
        if (staticCallMethod) {
            return MethodModifier.STATIC_CALL;
        }
        if (staticMethod) {
            return MethodModifier.STATIC;
        }
        return MethodModifier.NORMAL;
    }

    public boolean hiddenInstance() {
        return staticCallMethod || staticMethod;
    }
    @Override
    public MethodId getId() {
        String name_ = getName();
        StringList types_ = getImportedParametersTypes();
        int len_ = types_.size();
        StringList pTypes_ = new StringList();
        if (kind == MethodKind.EXPLICIT_CAST || kind == MethodKind.IMPLICIT_CAST
                ||kind == MethodKind.TRUE_OPERATOR || kind == MethodKind.FALSE_OPERATOR) {
            pTypes_.add(getImportedReturnType());
        }
        for (int i = CustList.FIRST_INDEX; i < len_; i++) {
            String n_ = types_.get(i);
            pTypes_.add(n_);
        }
        return new MethodId(MethodId.getKind(getModifier()), name_, pTypes_, isVarargs());
    }

    @Override
    public boolean isStaticMethod() {
        return staticMethod;
    }

    public boolean isFinalMethod() {
        return finalMethod;
    }

    public boolean isAbstractMethod() {
        return abstractMethod;
    }

    public boolean isStaticCallMethod() {
        return staticCallMethod;
    }

    public boolean isNormalMethod() {
        return normalMethod;
    }

    @Override
    public MethodAccessKind getStaticContext() {
        if (staticMethod) {
            return MethodAccessKind.STATIC;
        }
        if (staticCallMethod) {
            return MethodAccessKind.STATIC_CALL;
        }
        return MethodAccessKind.INSTANCE;
    }

    public void buildTypes(RootBlock _root, AnalyzedPageEl _page) {
        int indexDefOv_ = definition.indexOf('(');
        _page.setGlobalOffset(definitionOffset+indexDefOv_+1);
        ExtractedParts extractedParts_ = StringExpUtil.tryToExtract(definition, '(', ')');
        StringList overrideList_ = StringList.splitChar(extractedParts_.getSecond(), ';');
        int sum_ = 0;
        for (String o: overrideList_) {
            _page.setOffset(sum_);
            int indexDef_ = o.indexOf(Templates.EXTENDS_DEF);
            StringList parts_ = StringList.splitInTwo(o, indexDef_);
            if (parts_.size() <= 1) {
                sum_ += o.length()+1;
                continue;
            }
            String key_ = parts_.first();
            int off_ = StringList.getFirstPrintableCharIndex(key_);
            String clKey_ = ResolvingImportTypes.resolveAccessibleIdType(off_,key_, _page);
            CustList<PartOffset> allPartTypes_ = new CustList<PartOffset>();
            CustList<PartOffset> allPartSuperTypes_ = new CustList<PartOffset>();
             allPartTypes_.addAllElts(_page.getCurrentParts());
            RootBlock root_ = _page.getAnaClassBody(clKey_);
            if (root_ == null) {
                sum_ += o.length()+1;
                allInternTypesParts.add(new PartOffsetsClassMethodId(allPartTypes_,allPartSuperTypes_,null, 0, 0));
                continue;
            }
            if (!root_.isSubTypeOf(_root.getFullName(), _page)) {
                sum_ += o.length()+1;
                allInternTypesParts.add(new PartOffsetsClassMethodId(allPartTypes_,allPartSuperTypes_,null, 0, 0));
                continue;
            }
            String sgn_ = parts_.last().substring(1);
            ExtractedParts extr_ = StringExpUtil.tryToExtract(sgn_,'(',')');
            String nameLoc_ = extr_.getFirst().trim();
            if (StringExpUtil.isIndexerOrInexist(nameLoc_)) {
                sum_ += o.length() + 1;
                allInternTypesParts.add(new PartOffsetsClassMethodId(allPartTypes_,allPartSuperTypes_,null, 0, 0));
                continue;
            }
            _page.setOffset(sum_+indexDef_+1);
            StringList args_ = StringExpUtil.getAllSepCommaTypes(extr_.getSecond());
            String firstFull_ = args_.first();
            off_ = StringList.getFirstPrintableCharIndex(firstFull_);
            String fromType_ = StringExpUtil.removeDottedSpaces(firstFull_);
            int firstPar_ = extr_.getFirst().length();
            String clDest_ = ResolvingImportTypes.resolveAccessibleIdType(off_+firstPar_+1,fromType_, _page);
            CustList<PartOffset> superPartOffsets_ = new CustList<PartOffset>();
            superPartOffsets_.addAllElts(_page.getCurrentParts());
            String formattedDest_ = AnaTemplates.getOverridingFullTypeByBases(root_, clDest_, _page);
            RootBlock formattedDestType_ = _page.getAnaClassBody(StringExpUtil.getIdFromAllTypes(formattedDest_));
            if (formattedDestType_ == null) {
                allPartSuperTypes_.addAllElts(superPartOffsets_);
                sum_ += o.length()+1;
                allInternTypesParts.add(new PartOffsetsClassMethodId(allPartTypes_,allPartSuperTypes_,null, 0, 0));
                continue;
            }
            MethodId methodIdDest_ = IdFctOperation.resolveArguments(1, clDest_,nameLoc_,MethodAccessKind.INSTANCE,args_,sgn_, superPartOffsets_, _page);
            if (methodIdDest_ == null) {
                allPartSuperTypes_.addAllElts(superPartOffsets_);
                sum_ += o.length()+1;
                allInternTypesParts.add(new PartOffsetsClassMethodId(allPartTypes_,allPartSuperTypes_,null, 0, 0));
                continue;
            }
            CustList<OverridableBlock> methods_ = ClassesUtil.getMethodExecBlocks(formattedDestType_);
            String formattedDeclaring_ = AnaTemplates.getOverridingFullTypeByBases(root_, _root.getFullName(), _page);
            if (!getId().quickOverrideFormat(_root,formattedDeclaring_).eqPartial(MethodId.to(methodIdDest_.quickFormat(formattedDestType_,formattedDest_)))) {
                allPartSuperTypes_.addAllElts(superPartOffsets_);
                sum_ += o.length()+1;
                allInternTypesParts.add(new PartOffsetsClassMethodId(allPartTypes_,allPartSuperTypes_,null, 0, 0));
                continue;
            }
            ClassMethodId id_ = null;
            int rc_ = _page.getTraceIndex() +off_;
            for (OverridableBlock m: methods_) {
                if (m.isAbstractMethod()) {
                    continue;
                }
                if (m.getId().eq(methodIdDest_)) {
                    id_ = new ClassMethodId(clDest_,m.getId());
                    overrides.put(clKey_,new GeneStringOverridable(formattedDest_,formattedDestType_,m));
                    break;
                }
            }
            allPartSuperTypes_.addAllElts(superPartOffsets_);
            allInternTypesParts.add(new PartOffsetsClassMethodId(allPartTypes_,allPartSuperTypes_,id_, rc_, nameLoc_.length()));
            sum_ += o.length()+1;
        }
    }

    public StringMap<GeneStringOverridable> getOverrides() {
        return overrides;
    }

    public CustList<PartOffsetsClassMethodId> getAllInternTypesParts() {
        return allInternTypesParts;
    }

    public MethodKind getKind() {
        return kind;
    }

    public void setKind(MethodKind _kind) {
        kind = _kind;
    }

    public int getNameOverrideNumber() {
        return nameOverrideNumber;
    }

    public void setNameOverrideNumber(int nameOverrideNumber) {
        this.nameOverrideNumber = nameOverrideNumber;
    }

    public String getReturnTypeGet() {
        return returnTypeGet;
    }

    public void setReturnTypeGet(String returnTypeGet) {
        this.returnTypeGet = returnTypeGet;
    }
}
