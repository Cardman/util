package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.InfoErrorDto;
import code.expressionlanguage.analyze.blocks.*;
import code.expressionlanguage.analyze.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.analyze.inherits.AnaInherits;
import code.expressionlanguage.analyze.inherits.Mapping;
import code.expressionlanguage.analyze.instr.OperationsSequence;
import code.expressionlanguage.analyze.types.AnaClassArgumentMatching;
import code.expressionlanguage.analyze.types.AnaTypeUtil;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.linkage.ExportCst;
import code.maths.litteralcom.StrTypes;
import code.util.*;
import code.util.core.StringUtil;

public final class AnnotationInstanceArrOperation extends AnnotationInstanceOperation {

    private String className = "";
    public AnnotationInstanceArrOperation(int _index,
                                          int _indexChild, MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    public void preAnalyze(AnalyzedPageEl _page) {
        MethodOperation mOp_ = getParent();
        AbsBk curr_ = _page.getCurrentBlock();
        className = _page.getAliasObject();
        if (mOp_ == null) {
            className = ((NamedCalledFunctionBlock) curr_).getImportedReturnType();
        }
        if (mOp_ instanceof AssocationOperation) {
            AssocationOperation ass_ = (AssocationOperation) mOp_;
            String fieldName_ = ass_.getFieldName();
            MethodOperation mOpAss_ = ass_.getParent();
            AnnotationInstanceArobaseOperation inst_;
            inst_ = (AnnotationInstanceArobaseOperation)mOpAss_;
            String className_ = inst_.getClassName();
            RootBlock typeInfo_ = _page.getAnaClassBody(className_);
            if (typeInfo_ == null) {
                className = _page.getAliasObject();
                return;
            }
            String type_ = EMPTY_STRING;
            CustList<NamedCalledFunctionBlock> list_ = ClassesUtil.getMethodAnnotationBodiesById(typeInfo_, fieldName_);
            if (!list_.isEmpty()) {
                type_ = list_.first().getImportedReturnType();
            }
            if (!type_.isEmpty()) {
                className = type_;
            } else {
                className = _page.getAliasObject();
            }
        } else if (mOp_ instanceof AnnotationInstanceOperation) {
            if (!(mOp_ instanceof AnnotationInstanceArobaseOperation)) {
                className = _page.getAliasObject();
            } else {
                AnnotationInstanceArobaseOperation inst_;
                inst_ = (AnnotationInstanceArobaseOperation)mOp_;
                String className_ = inst_.getClassName();
                RootBlock type_ = _page.getAnaClassBody(className_);
                if (type_ == null) {
                    className = _page.getAliasObject();
                    return;
                }
                CustList<AbsBk> bls_ = ClassesUtil.getDirectChildren(type_);
                CustList<NamedCalledFunctionBlock> blsAnn_ = new CustList<NamedCalledFunctionBlock>();
                for (AbsBk b: bls_) {
                    if (!AbsBk.isAnnotBlock(b)) {
                        continue;
                    }
                    NamedCalledFunctionBlock a_ = (NamedCalledFunctionBlock) b;
                    blsAnn_.add(a_);
                }
                if (blsAnn_.size() != 1) {
                    className = _page.getAliasObject();
                } else {
                    NamedCalledFunctionBlock a_ =blsAnn_.first();
                    className = a_.getImportedReturnType();
                }
            }
        }
    }

    public String getClassName() {
        return className;
    }
    @Override
    public void analyze(AnalyzedPageEl _page) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+getOperations().getOperators().firstKey(), _page);
        StringMap<StringList> map_;
        map_ = new StringMap<StringList>();
        String eltType_ = StringExpUtil.getQuickComponentType(className);
        if (eltType_ == null) {
            FoundErrorInterpret un_ = new FoundErrorInterpret();
            int i_ = _page.getLocalizer().getCurrentLocationIndex();
            un_.setIndexFile(i_);
            un_.setFileName(_page.getLocalizer().getCurrentFileName());
            //first separator char
            un_.buildError(_page.getAnalysisMessages().getUnexpectedType(),
                    className);
            _page.getLocalizer().addError(un_);
            setPartOffsetsErr(new InfoErrorDto(un_.getBuiltError(),i_,1));
            setResultClass(new AnaClassArgumentMatching(className));
            return;
        }
        Mapping mapping_ = new Mapping();
        mapping_.setParam(eltType_);
        for (OperationNode o: chidren_) {
            int index_ = getPartOffsetsChildren().size();
            StrTypes operators_ = getOperations().getOperators();
            setRelativeOffsetPossibleAnalyzable(getIndexInEl()+ operators_.getKey(index_), _page);
            int i_ = _page.getLocalizer().getCurrentLocationIndex();
            InfoErrorDto parts_ = new InfoErrorDto("");
            AnaClassArgumentMatching argType_ = o.getResultClass();
            mapping_.setArg(argType_);
            mapping_.setMapping(map_);
            if (!AnaInherits.isCorrectOrNumbers(mapping_, _page)) {
                FoundErrorInterpret cast_ = new FoundErrorInterpret();
                cast_.setFileName(_page.getLocalizer().getCurrentFileName());
                cast_.setIndexFile(i_);
                //first separator char child
                cast_.buildError(_page.getAnalysisMessages().getBadImplicitCast(),
                        StringUtil.join(argType_.getNames(),ExportCst.JOIN_TYPES),
                        eltType_);
                _page.getLocalizer().addError(cast_);
                parts_=new InfoErrorDto(cast_.getBuiltError(),i_,1);
            }
            if (AnaTypeUtil.isPrimitive(eltType_, _page)) {
                o.getResultClass().setUnwrapObject(eltType_, _page.getPrimitiveTypes());
            }
            getPartOffsetsChildren().add(parts_);
        }
        setResultClass(new AnaClassArgumentMatching(className));
    }

}
