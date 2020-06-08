package code.expressionlanguage.opers;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.inherits.Templates;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.instr.PartOffset;
import code.expressionlanguage.opers.util.*;
import code.expressionlanguage.types.ResolvingImportTypes;
import code.util.CustList;
import code.util.StringList;

public final class ExplicitOperatorOperation extends InvokingOperation {
    private ClassMethodId classMethodId;
    private String methodName;
    private String lastType = EMPTY_STRING;

    private int naturalVararg = -1;

    private int offsetOper;

    private CustList<PartOffset> partOffsets = new CustList<PartOffset>();
    public ExplicitOperatorOperation(int _index, int _indexChild, MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
        methodName = getOperations().getFctName();
        offsetOper = getOperations().getOperators().firstKey();
    }

    @Override
    public void analyze(ContextEl _conf) {
        String cl_ = methodName;
        setRelativeOffsetPossibleAnalyzable(getIndexInEl(), _conf);
        cl_ = cl_.substring(cl_.indexOf(PAR_LEFT)+1, cl_.lastIndexOf(PAR_RIGHT));
        StringList args_ = Templates.getAllSepCommaTypes(cl_);
        String op_ = args_.first();
        String from_ = "";
        if (args_.size() > 1) {
            int off_ = StringList.getFirstPrintableCharIndex(args_.get(1));
            String fromType_ = StringExpUtil.removeDottedSpaces(args_.get(1));
            from_ = ResolvingImportTypes.resolveAccessibleIdType(_conf,off_+methodName.indexOf(',')+1,fromType_);
            partOffsets.addAllElts(_conf.getCoverage().getCurrentParts());
        }
        int varargOnly_ = lookOnlyForVarArg();
        ClassMethodIdAncestor idMethod_ = lookOnlyForId();
        CustList<OperationNode> chidren_ = getChildrenNodes();
        CustList<ClassArgumentMatching> firstArgs_ = listClasses(chidren_);
        if (args_.size() > 2) {
            FoundErrorInterpret undefined_ = new FoundErrorInterpret();
            undefined_.setFileName(_conf.getAnalyzing().getLocalizer().getCurrentFileName());
            undefined_.setIndexFile(_conf.getAnalyzing().getLocalizer().getCurrentLocationIndex());
            //_name len
            StringList classesNames_ = new StringList();
            for (ClassArgumentMatching c: firstArgs_) {
                classesNames_.add(StringList.join(c.getNames(), "&"));
            }
            undefined_.buildError(_conf.getAnalysisMessages().getUndefinedMethod(),
                    new MethodId(MethodAccessKind.STATIC, cl_, classesNames_).getSignature(_conf));
            _conf.getAnalyzing().getLocalizer().addError(undefined_);
            setResultClass(new ClassArgumentMatching(_conf.getStandards().getAliasObject()));
            return;
        }
        ClassMethodId id_ = null;
        if (idMethod_ != null) {
            id_ = idMethod_.getClassMethodId();
            MethodId s_ = id_.getConstraints();
            id_ = new ClassMethodId(from_,new MethodId(MethodAccessKind.STATIC,op_,s_.getParametersTypes(),s_.isVararg()));
        }
        ClassMethodIdReturn cust_;
        if (from_.isEmpty()) {
            cust_ = getOperator(_conf, id_,varargOnly_,false,op_, ClassArgumentMatching.toArgArray(firstArgs_));
        } else {
            cust_ = tryGetDeclaredCustMethod(_conf, -1, MethodAccessKind.STATIC,
                    false, new StringList(from_), op_, false, false, false, null,
                    ClassArgumentMatching.toArgArray(firstArgs_));
        }
        if (!cust_.isFoundMethod()) {
            FoundErrorInterpret undefined_ = new FoundErrorInterpret();
            undefined_.setFileName(_conf.getAnalyzing().getLocalizer().getCurrentFileName());
            undefined_.setIndexFile(_conf.getAnalyzing().getLocalizer().getCurrentLocationIndex());
            //_name len
            StringList classesNames_ = new StringList();
            for (ClassArgumentMatching c: firstArgs_) {
                classesNames_.add(StringList.join(c.getNames(), "&"));
            }
            undefined_.buildError(_conf.getAnalysisMessages().getUndefinedMethod(),
                    new MethodId(MethodAccessKind.STATIC, cl_, classesNames_).getSignature(_conf));
            _conf.getAnalyzing().getLocalizer().addError(undefined_);
            setResultClass(new ClassArgumentMatching(_conf.getStandards().getAliasObject()));
            return;
        }
        setResultClass(new ClassArgumentMatching(cust_.getReturnType()));
        String foundClass_ = cust_.getRealClass();
        foundClass_ = Templates.getIdFromAllTypes(foundClass_);
        classMethodId = new ClassMethodId(foundClass_, cust_.getRealId());
        MethodId realId_ = cust_.getRealId();
        if (cust_.isVarArgToCall()) {
            StringList paramtTypes_ = cust_.getRealId().getParametersTypes();
            naturalVararg = paramtTypes_.size() - 1;
            lastType = paramtTypes_.last();
        }
        InvokingOperation.unwrapArgsFct(chidren_, realId_, naturalVararg, lastType, firstArgs_, _conf);
    }


    public int getOffsetOper() {
        return offsetOper;
    }
    public int getNaturalVararg() {
        return naturalVararg;
    }

    public String getLastType() {
        return lastType;
    }

    public ClassMethodId getClassMethodId() {
        return classMethodId;
    }

    public CustList<PartOffset> getPartOffsets() {
        return partOffsets;
    }
}
