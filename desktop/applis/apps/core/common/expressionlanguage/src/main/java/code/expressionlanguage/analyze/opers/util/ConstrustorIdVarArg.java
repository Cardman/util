package code.expressionlanguage.analyze.opers.util;

import code.expressionlanguage.analyze.util.AnaFormattedRootBlock;
import code.expressionlanguage.functionid.ConstructorId;
import code.expressionlanguage.stds.StandardType;

public final class ConstrustorIdVarArg {

    private ConstructorId realId;
    private AnaTypeFct pair;
    private ConstructorId constId;

    private StandardType standardType;

    private boolean varArgToCall;
    private String fileName;
    private AnaFormattedRootBlock formattedType;
    private MemberId memberId = new MemberId();

    public ConstructorId getRealId() {
        return realId;
    }

    public void setRealId(ConstructorId _realId) {
        realId = _realId;
    }

    public AnaTypeFct getPair() {
        return pair;
    }

    public void setPair(AnaTypeFct _pair) {
        pair = _pair;
    }

    public ConstructorId getConstId() {
        return constId;
    }

    public void setConstId(ConstructorId _constId) {
        constId = _constId;
    }

    public StandardType getStandardType() {
        return standardType;
    }

    public void setStandardType(StandardType _standardType) {
        standardType = _standardType;
    }

    public boolean isVarArgToCall() {
        return varArgToCall;
    }

    public void setVarArgToCall(boolean _varArgToCall) {
        varArgToCall = _varArgToCall;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String _fileName) {
        this.fileName = _fileName;
    }

    public AnaFormattedRootBlock getFormattedType() {
        return formattedType;
    }

    public void setFormattedType(AnaFormattedRootBlock _formattedType) {
        this.formattedType = _formattedType;
    }

    public MemberId getMemberId() {
        return memberId;
    }

    public void setMemberId(MemberId _memberId) {
        this.memberId = _memberId;
    }

}
