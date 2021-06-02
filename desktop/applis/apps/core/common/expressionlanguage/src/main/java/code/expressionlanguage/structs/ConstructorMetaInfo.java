package code.expressionlanguage.structs;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.AccessEnum;
import code.expressionlanguage.common.GeneType;
import code.expressionlanguage.common.NumParsers;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.exec.blocks.*;
import code.expressionlanguage.exec.opers.ExecOperationNode;
import code.expressionlanguage.exec.util.ExecFormattedRootBlock;
import code.expressionlanguage.functionid.ConstructorId;
import code.expressionlanguage.fwd.blocks.ExecTypeFunction;
import code.expressionlanguage.fwd.opers.ExecLambdaCommonContent;
import code.expressionlanguage.stds.StandardConstructor;
import code.expressionlanguage.stds.StandardType;
import code.util.CustList;
import code.util.StringList;
import code.util.core.StringUtil;

public final class ConstructorMetaInfo extends AbsAnnotatedStruct implements AnnotatedParamStruct {

    private final String formDeclaringClass;
    private final ConstructorId realId;
    private final AccessEnum access;
    private final ConstructorId fid;
    private final String returnType;
    private final boolean invokable;
    private final String fileName;
    private final ExecTypeFunction pair;
    private final ExecFormattedRootBlock formatted;
    private final StandardType standardType;
    private final GeneType declType;

    public ConstructorMetaInfo(){
        invokable = false;
        formDeclaringClass = "";
        realId = new ConstructorId("",new StringList(),false);
        fid = new ConstructorId("",new StringList(),false);
        access = AccessEnum.PRIVATE;
        returnType = "";
        pair = new ExecTypeFunction((ExecRootBlock)null,null);
        formatted = ExecFormattedRootBlock.defValue();
        fileName = "";
        standardType = null;
        declType = null;
    }
    public ConstructorMetaInfo(ExecTypeFunction _pair, ExecLambdaCommonContent _common, ExecFormattedRootBlock _declaringClass, ConstructorId _realId) {
        String className_ = StringExpUtil.getIdFromAllTypes(_declaringClass.getFormatted());
        ConstructorId fid_ = tryFormatId(_declaringClass, _realId);
        invokable = true;
        access = AccessEnum.PUBLIC;
        realId = _realId;
        returnType = StringUtil.nullToEmpty(_common.getReturnFieldType());
        fid = fid_;
        formDeclaringClass = StringUtil.nullToEmpty(className_);
        pair = _pair;
        setOwner(_pair.getType());
        formatted = _declaringClass;
        fileName = _common.getFileName();
        standardType = null;
        declType = _pair.getType();
    }
    public ConstructorMetaInfo(StandardType _standardType, ExecLambdaCommonContent _common, ExecFormattedRootBlock _declaringClass, ConstructorId _realId) {
        standardType = _standardType;
        String className_ = StringExpUtil.getIdFromAllTypes(_declaringClass.getFormatted());
        ConstructorId fid_ = tryFormatId(_declaringClass, _realId);
        invokable = true;
        access = AccessEnum.PUBLIC;
        realId = _realId;
        returnType = StringUtil.nullToEmpty(_common.getReturnFieldType());
        fid = fid_;
        formDeclaringClass = StringUtil.nullToEmpty(className_);
        pair = new ExecTypeFunction((ExecRootBlock) null,null);
        formatted = _declaringClass;
        fileName = _common.getFileName();
        declType = _standardType;
    }
    public ConstructorMetaInfo(ExecFormattedRootBlock _formatted, ExecConstructorBlock _ctor, ContextEl _context) {
        ExecRootBlock type_ = _formatted.getRootBlock();
        String formatted_ = _formatted.getFormatted();
        ConstructorId id_ = new ConstructorId(formatted_, new StringList(), false);
        AccessEnum acc_ = type_.getAccess();
        String ret_ = _context.getStandards().getContent().getCoreNames().getAliasVoid();
        ConstructorId fid_ = id_;
        String idType_ = type_.getFullName();
        String formCl_ = tryFormatType(idType_, formatted_, _context);
        if (_ctor != null) {
            id_ = _ctor.getGenericId(type_.getGenericString());
            ret_ = _ctor.getImportedReturnType();
            acc_ = _ctor.getAccess();
            fid_ = tryFormatId(formatted_, _context, id_);
        }
        invokable = true;
        access = acc_;
        realId = id_;
        returnType = StringUtil.nullToEmpty(ret_);
        fid = fid_;
        formDeclaringClass = StringUtil.nullToEmpty(formCl_);
        pair = new ExecTypeFunction(_formatted,_ctor);
        setOwner(type_);
        formatted  = _formatted;
        fileName = type_.getFile().getFileName();
        standardType = null;
        declType = type_;
    }
    public ConstructorMetaInfo(ContextEl _context, StandardType _std, StandardConstructor _ctor, String _declaringClass, ExecFormattedRootBlock _formatted) {
        ConstructorId id_ = new ConstructorId(_declaringClass, new StringList(), false);
        String ret_ = _context.getStandards().getContent().getCoreNames().getAliasVoid();
        if (_ctor != null) {
            id_ = new ConstructorId(_declaringClass, _ctor.getImportedParametersTypes(), _ctor.isVarargs());
            ret_ = _ctor.getImportedReturnType();
        }
        String decl_ = _std.getFullName();
        standardType = _std;
        invokable = true;
        access = AccessEnum.PUBLIC;
        realId = id_;
        returnType = StringUtil.nullToEmpty(ret_);
        fid = id_;
        formDeclaringClass = StringUtil.nullToEmpty(decl_);
        pair = new ExecTypeFunction((ExecRootBlock)null,null);
        formatted = _formatted;
        fileName = "";
        declType = _std;
    }

    public ExecFormattedRootBlock getFormatted() {
        return formatted;
    }

    public CustList<CustList<ExecOperationNode>> getAnnotationsOps(){
        ExecNamedFunctionBlock fct_ = pair.getFct();
        if (fct_ != null) {
            return fct_.getAnnotationsOps();
        }
        return new CustList<CustList<ExecOperationNode>>();
    }
    public CustList<CustList<CustList<ExecOperationNode>>> getAnnotationsOpsParams(){
        ExecNamedFunctionBlock fct_ = pair.getFct();
        if (fct_ != null) {
            return fct_.getAnnotationsOpsParams();
        }
        return new CustList<CustList<CustList<ExecOperationNode>>>();
    }

    public GeneType getDeclType() {
        return declType;
    }

    public ExecMemberCallingsBlock getCallee() {
        return pair.getFct();
    }

    public ExecRootBlock getPairType() {
        return pair.getType();
    }

    public ExecTypeFunction getPair() {
        return pair;
    }

    public StandardType getStandardType() {
        return standardType;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    public ConstructorId getRealId() {
        return realId;
    }
    public boolean isVararg() {
        return realId.isVararg();
    }
    public boolean isPublic() {
        return access == AccessEnum.PUBLIC;
    }
    
    public boolean isProtected() {
        return access == AccessEnum.PROTECTED;
    }
    
    public boolean isPackage() {
        return access == AccessEnum.PACKAGE;
    }

    public boolean isPrivate() {
        return access == AccessEnum.PRIVATE;
    }
    
    public String getFormDeclaringClass() {
        return formDeclaringClass;
    }

    @Override
    public CustList<ExecAnonymousFunctionBlock> getAnonymousLambda() {
        ExecNamedFunctionBlock fct_ = pair.getFct();
        if (fct_ != null) {
            return fct_.getAnonymousLambda();
        }
        return new CustList<ExecAnonymousFunctionBlock>();
    }
    @Override
    public CustList<ExecAbstractSwitchMethod> getSwitchMethods() {
        ExecNamedFunctionBlock fct_ = pair.getFct();
        if (fct_ != null) {
            return fct_.getSwitchMethods();
        }
        return new CustList<ExecAbstractSwitchMethod>();
    }

    public ConstructorId getFid() {
        return fid;
    }

    public String getReturnType() {
        return returnType;
    }

    @Override
    public String getClassName(ContextEl _contextEl) {
        return _contextEl.getStandards().getContent().getReflect().getAliasConstructor();
    }

    @Override
    public boolean sameReference(Struct _other) {
        if (!(_other instanceof ConstructorMetaInfo)) {
            return false;
        }
        ConstructorMetaInfo info_ = (ConstructorMetaInfo) _other;
        if (!StringUtil.quickEq(formatted.getFormatted(), info_.formatted.getFormatted())) {
            return false;
        }
        return realId.eq(info_.realId);
    }

    @Override
    public long randCode() {
        return NumParsers.randCode(formatted.getFormatted());
    }
    @Override
    public StringStruct getDisplayedString(ContextEl _an) {
        return new StringStruct(StringUtil.concat(formatted.getFormatted(),";",realId.getSignature(_an)));
    }

    public StringList getParametersTypes() {
        return realId.getParametersTypes();
    }

    public boolean isInvokable() {
        return invokable;
    }
}
