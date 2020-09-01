package code.expressionlanguage.exec.blocks;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.analyze.blocks.*;
import code.expressionlanguage.analyze.util.Members;
import code.expressionlanguage.common.AccessEnum;
import code.expressionlanguage.common.GeneType;
import code.expressionlanguage.exec.ExpressionLanguage;
import code.expressionlanguage.exec.opers.ExecOperationNode;
import code.expressionlanguage.exec.util.ExecFormattedRootBlock;
import code.expressionlanguage.exec.util.ExecFunctionalInfo;
import code.expressionlanguage.exec.util.ExecTypeVar;
import code.expressionlanguage.functionid.ClassMethodIdOverrides;
import code.expressionlanguage.inherits.Templates;
import code.util.*;

public abstract class ExecRootBlock extends ExecBracedBlock implements GeneType, ExecAnnotableBlock {
    private final String name;

    private final String packageName;

    private AccessEnum access;

    private final ClassMethodIdOverrides redirections = new ClassMethodIdOverrides();

    private StringList paramTypes;

    private StringMap<ExecTypeVar> paramTypesMap = new StringMap<ExecTypeVar>();

    private int idRowCol;

    private StringList staticInitImportedInterfaces = new StringList();

    private CustList<CustList<ExecOperationNode>> annotationsOps = new CustList<CustList<ExecOperationNode>>();
    private final CustList<ExecFormattedRootBlock> allGenericSuperTypes = new CustList<ExecFormattedRootBlock>();
    private final CustList<ExecFunctionalInfo> functionalBodies = new CustList<ExecFunctionalInfo>();
    private String importedDirectSuperClass = "";
    private ExecRootBlock uniqueType;
    private StringList importedDirectSuperInterfaces = new StringList();
    private final StringList allSuperTypes = new StringList();
    private ExecRootBlock parentType;
    private final CustList<ExecRootBlock> childrenTypes = new CustList<ExecRootBlock>();
    private final CustList<ExecFieldBlock> instanceFields = new CustList<ExecFieldBlock>();
    private final CustList<ExecAnnotationMethodBlock> annotationsFields = new CustList<ExecAnnotationMethodBlock>();
    private final CustList<ExecInnerTypeOrElement> enumElements = new CustList<ExecInnerTypeOrElement>();
    private String suffix;
    private ExecNamedFunctionBlock emptyCtor;
    private CustList<ExecRootBlock> anonymousRoot = new CustList<ExecRootBlock>();
    private CustList<ExecAnonymousFunctionBlock> anonymousRootLambda = new CustList<ExecAnonymousFunctionBlock>();

    ExecRootBlock(RootBlock _offset) {
        super(_offset.getOffset());
        packageName = _offset.getPackageName();
        name = _offset.getName();
        suffix = _offset.getSuffix();
        access = _offset.getAccess();
        idRowCol = _offset.getIdRowCol();
        paramTypes = _offset.getParamTypesAsStringList();
    }

    @Override
    public StringList getParamTypesValues() {
        StringList l_ = new StringList();
        for (ExecRootBlock r: getSelfAndParentTypes()) {
            for (String t: r.paramTypes) {
                l_.add(t);
            }
        }
        return l_;
    }
    @Override
    public CustList<ExecTypeVar> getParamTypesMapValues() {
        return paramTypesMap.values();
    }
    @Override
    public CustList<StringList> getBoundAll() {
        CustList<StringList> boundsAll_ = new CustList<StringList>();
        for (ExecTypeVar t: getParamTypesMapValues()) {
            boolean contained_ = false;
            for (String u: paramTypes) {
                if (!StringList.quickEq(t.getName(),u)) {
                    continue;
                }
                contained_ = true;
            }
            if (!contained_) {
                continue;
            }
            StringList localBound_ = new StringList();
            for (String b: t.getConstraints()) {
                localBound_.add(b);
            }
            boundsAll_.add(localBound_);
        }
        return boundsAll_;
    }

    public Ints getTypeVarCounts() {
        Ints generic_ = new Ints();
        CustList<ExecRootBlock> pars_ = getSelfAndParentTypes();
        CustList<ExecRootBlock> stPars_ = pars_.first().getAllParentTypesReverse();
        int len_ = stPars_.size();
        for (int i = 0; i < len_; i++) {
            generic_.add(0);
        }
        for (ExecRootBlock r: pars_) {
            generic_.add(r.paramTypes.size());
        }
        return generic_;
    }
    public String getWildCardElement() {
        StringList allElements_ = new StringList();
        for (ExecInnerTypeOrElement e: enumElements) {
            String type_ = e.getRealImportedClassName();
            allElements_.add(type_);
        }
        String className_;
        if (allElements_.onlyOneElt()) {
            className_ = allElements_.first();
        } else {
            className_ = getWildCardString();
        }
        return className_;
    }

    public final String getWildCardString() {
        String pkg_ = getPackageName();
        StringBuilder generic_ = new StringBuilder();
        RootBlock.addPkgIfNotEmpty(pkg_, generic_);
        CustList<ExecRootBlock> pars_ = getSelfAndParentTypes();
        ExecRootBlock previous_ = null;
        for (ExecRootBlock r: pars_.first().getAllParentTypesReverse()) {
            appendParts(generic_, previous_, r);
            generic_.append(r.getSuffixedName());
            previous_ = r;
        }
        for (ExecRootBlock r: pars_) {
            appendParts(generic_, previous_, r);
            generic_.append(r.getSuffixedName());
            if (!r.paramTypes.isEmpty()) {
                StringList vars_ = new StringList();
                int count_ = r.paramTypes.size();
                for (int i = 0; i < count_; i++) {
                    vars_.add(Templates.SUB_TYPE);
                }
                generic_.append(Templates.TEMPLATE_BEGIN);
                generic_.append(StringList.join(vars_, Templates.TEMPLATE_SEP));
                generic_.append(Templates.TEMPLATE_END);
            }
            previous_ = r;
        }
        return generic_.toString();
    }

    private static void appendParts(StringBuilder generic_, ExecRootBlock previous_, ExecRootBlock r) {
        if (previous_ != null) {
            if (r instanceof ExecInnerElementBlock) {
                generic_.append("-");
            } else {
                generic_.append("..");
            }
        }
    }

    @Override
    public boolean withoutInstance() {
        return isStaticType();
    }

    @Override
    public abstract boolean isStaticType();

    public final CustList<ExecFormattedRootBlock> getAllGenericSuperTypes() {
        return allGenericSuperTypes;
    }

    public StringList getStaticInitImportedInterfaces() {
        return staticInitImportedInterfaces;
    }

    public final ExecRootBlock getParentType() {
        return parentType;
    }

    public final void setParentType(ExecRootBlock _parentType) {
        parentType = _parentType;
    }

    public final CustList<ExecRootBlock> getAllParentTypesReverse() {
        return getAllParentTypes().getReverse();
    }
    public final CustList<ExecRootBlock> getAllParentTypes() {
        CustList<ExecRootBlock> pars_ = new CustList<ExecRootBlock>();
        ExecRootBlock c_ = getParentType();
        while (c_ != null) {
            pars_.add(c_);
            c_ = c_.getParentType();
        }
        return pars_;
    }
    public final CustList<ExecRootBlock> getSelfAndParentTypes() {
        CustList<ExecRootBlock> pars_ = new CustList<ExecRootBlock>();
        ExecRootBlock c_ = this;
        boolean add_ = true;
        while (c_ != null) {
            if (add_) {
                pars_.add(c_);
            }
            if (c_.withoutInstance()) {
                add_ = false;
            }
            c_ = c_.getParentType();
        }
        return pars_.getReverse();
    }
    @Override
    public void reduce(ContextEl _context) {
        CustList<CustList<ExecOperationNode>> annotationsOps_;
        annotationsOps_ = new CustList<CustList<ExecOperationNode>>();
        for (CustList<ExecOperationNode> a: annotationsOps) {
            ExecOperationNode r_ = a.last();
            annotationsOps_.add(ExpressionLanguage.getReducedNodes(r_));
        }
        annotationsOps = annotationsOps_;
    }
    @Override
    public CustList<CustList<ExecOperationNode>> getAnnotationsOps() {
        return annotationsOps;
    }

    @Override
    public String getGenericString() {
        String pkg_ = getPackageName();
        StringBuilder generic_ = new StringBuilder();
        RootBlock.addPkgIfNotEmpty(pkg_, generic_);
        CustList<ExecRootBlock> pars_ = getSelfAndParentTypes();
        ExecRootBlock previous_ = null;
        for (ExecRootBlock r: pars_.first().getAllParentTypesReverse()) {
            appendParts(generic_, previous_, r);
            generic_.append(r.getSuffixedName());
            previous_ = r;
        }
        for (ExecRootBlock r: pars_) {
            appendParts(generic_, previous_, r);
            generic_.append(r.getSuffixedName());
            if (!r.paramTypes.isEmpty()) {
                StringList vars_ = new StringList();
                for (String t:r.paramTypes) {
                    vars_.add(StringList.concat(Templates.PREFIX_VAR_TYPE,t));
                }
                generic_.append(Templates.TEMPLATE_BEGIN);
                generic_.append(StringList.join(vars_, Templates.TEMPLATE_SEP));
                generic_.append(Templates.TEMPLATE_END);
            }
            previous_ = r;
        }
        return generic_.toString();
    }

    public void buildMapParamType(RootBlock _root) {
        paramTypes = _root.getParamTypesAsStringList();
        paramTypesMap = _root.getParamTypesMapAsExec();
        importedDirectSuperClass = _root.getImportedDirectGenericSuperClass();
        importedDirectSuperInterfaces = _root.getImportedDirectSuperInterfaces();
    }

    public String getName() {
        return name;
    }

    public String getSuffixedName() {
        return StringList.concat(getName(),suffix);
    }
    @Override
    public String getPackageName() {
        return packageName;
    }

    public AccessEnum getAccess() {
        return access;
    }

    @Override
    public String getFullName() {
        CustList<ExecRootBlock> all_ = new CustList<ExecRootBlock>(this);
        all_.addAllElts(getAllParentTypes());
        ExecRootBlock p_ = null;
        StringBuilder strBuilder_ = new StringBuilder();
        RootBlock.addPkgIfNotEmpty(packageName,strBuilder_);
        for (ExecRootBlock r: all_.getReverse()) {
            appendParts(strBuilder_,p_,r);
            strBuilder_.append(r.getSuffixedName());
            p_ = r;
        }
        return strBuilder_.toString();
    }


    public CustList<ExecFunctionalInfo> getFunctionalBodies() {
        return functionalBodies;
    }

    public boolean isSubTypeOf(String _fullName, ContextEl _an) {
        if (StringList.quickEq(getFullName(),_fullName)) {
            return true;
        }
        return StringList.contains(getAllSuperTypes(),_fullName);
    }

    public final void validateIds(RootBlock _key, IdMap<RootBlock, Members> _mapMembers) {
        Members mem_ = _mapMembers.getVal(_key);
        for (EntryCust<OverridableBlock,ExecOverridableBlock> e: mem_.getAllMethods().entryList()) {
            e.getValue().buildImportedTypes(e.getKey());
            String returnTypeGet_ = e.getKey().getReturnTypeGet();
            if (!returnTypeGet_.isEmpty()) {
                e.getValue().setImportedReturnType(returnTypeGet_);
            }
        }
        for (EntryCust<ConstructorBlock,ExecConstructorBlock> e: mem_.getAllCtors().entryList()) {
            e.getValue().buildImportedTypes(e.getKey());
        }
        for (EntryCust<AnnotationMethodBlock,ExecAnnotationMethodBlock> e: mem_.getAllAnnotMethods().entryList()) {
            e.getValue().buildImportedTypes(e.getKey());
        }
        for (EntryCust<InfoBlock,ExecInfoBlock> e: mem_.getAllFields().entryList()) {
            e.getValue().buildImportedTypes(e.getKey());
        }
    }

    public int getIdRowCol() {
        return idRowCol;
    }


    public String getImportedDirectGenericSuperClass() {
        return importedDirectSuperClass;
    }

    public StringList getImportedDirectGenericSuperInterfaces() {
        return importedDirectSuperInterfaces;
    }

    public final StringList getAllSuperTypes() {
        return allSuperTypes;
    }

    public ClassMethodIdOverrides getRedirections() {
        return redirections;
    }

    public CustList<ExecRootBlock> getChildrenTypes() {
        return childrenTypes;
    }

    public CustList<ExecFieldBlock> getInstanceFields() {
        return instanceFields;
    }

    public CustList<ExecAnnotationMethodBlock> getAnnotationsFields() {
        return annotationsFields;
    }

    public CustList<ExecInnerTypeOrElement> getEnumElements() {
        return enumElements;
    }

    public ExecRootBlock getUniqueType() {
        return uniqueType;
    }

    public void setUniqueType(ExecRootBlock _uniqueType) {
        uniqueType = _uniqueType;
    }

    public ExecNamedFunctionBlock getEmptyCtor() {
        return emptyCtor;
    }

    public void setEmptyCtor(ExecNamedFunctionBlock _emptyCtor) {
        emptyCtor = _emptyCtor;
    }

    public CustList<ExecRootBlock> getAnonymousRoot() {
        return anonymousRoot;
    }

    public CustList<ExecAnonymousFunctionBlock> getAnonymousRootLambda() {
        return anonymousRootLambda;
    }
}
