package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.opers.util.ConstructorInfo;
import code.expressionlanguage.analyze.opers.util.MethodInfo;
import code.expressionlanguage.analyze.types.AnaClassArgumentMatching;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.stds.LgNames;
import code.util.CustList;
import code.util.*;

public final class FirstOptOperation extends AbstractUnaryOperation {

    private int offset;
    public FirstOptOperation(int _index, int _indexChild, MethodOperation _m,
            OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    void calculateChildren() {
        IntTreeMap< String> vs_ = getOperations().getValues();
        offset = vs_.firstKey();
        vs_.removeKey(vs_.firstKey());
        getChildren().putAllMap(vs_);
    }

    @Override
    public void analyzeUnary(AnalyzedPageEl _page) {
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+offset, _page);
        LgNames stds_ = _page.getStandards();
        MethodOperation m_ = getParent();
        if (isNotChildOfCall(m_)) {
            FoundErrorInterpret varg_ = new FoundErrorInterpret();
            varg_.setFileName(_page.getLocalizer().getCurrentFileName());
            varg_.setIndexFile(_page.getLocalizer().getCurrentLocationIndex());
            //key word len
            varg_.buildError(_page.getAnalysisMessages().getUnexpectedLeaf(),
                    _page.getKeyWords().getKeyWordFirstopt());
            _page.getLocalizer().addError(varg_);
            getErrs().add(varg_.getBuiltError());
            setResultClass(new AnaClassArgumentMatching(stds_.getAliasObject()));
            return;
        }
        if (isFirstChildInParent()) {
            FoundErrorInterpret varg_ = new FoundErrorInterpret();
            varg_.setFileName(_page.getLocalizer().getCurrentFileName());
            varg_.setIndexFile(_page.getLocalizer().getCurrentLocationIndex());
            //key word len
            varg_.buildError(_page.getAnalysisMessages().getUnexpectedLeaf(),
                    _page.getKeyWords().getKeyWordFirstopt());
            _page.getLocalizer().addError(varg_);
            getErrs().add(varg_.getBuiltError());
            setResultClass(new AnaClassArgumentMatching(stds_.getAliasObject()));
            return;
        }
        if (m_ instanceof RetrieveMethod) {
            RetrieveMethod f_ = (RetrieveMethod) m_;
            OperationNode firstChild_ = f_.getFirstChild();
            int deltaCount_ = InvokingOperation.getDeltaCount(firstChild_);
            int indexChild_ = getIndexChild()-deltaCount_;
            CustList<CustList<MethodInfo>> methodInfos_ = f_.getMethodInfos();
            int len_ = methodInfos_.size();
            for (int i = 0; i < len_; i++) {
                int gr_ = methodInfos_.get(i).size();
                CustList<MethodInfo> newList_ = new CustList<MethodInfo>();
                for (int j = 0; j < gr_; j++) {
                    MethodInfo methodInfo_ = methodInfos_.get(i).get(j);
                    if (methodInfo_.getGeneFormatted().getParametersTypesLength() != indexChild_+1) {
                        continue;
                    }
                    newList_.add(methodInfo_);
                }
                methodInfos_.set(i,newList_);
            }
        }
        if (m_ instanceof RetrieveConstructor) {
            RetrieveConstructor f_ = (RetrieveConstructor) m_;
            OperationNode firstChild_ = f_.getFirstChild();
            int deltaCount_ = InvokingOperation.getDeltaCount(firstChild_);
            int indexChild_ = getIndexChild()-deltaCount_;
            CustList<ConstructorInfo> methodInfos_ = f_.getCtors();
            int len_ = methodInfos_.size();
            CustList<ConstructorInfo> newList_ = new CustList<ConstructorInfo>();
            for (int i = 0; i < len_; i++) {
                ConstructorInfo methodInfo_ = methodInfos_.get(i);
                if (methodInfo_.getGeneFormatted().getParametersTypesLength() != indexChild_+1) {
                    continue;
                }
                newList_.add(methodInfo_);
            }
            methodInfos_.clear();
            methodInfos_.addAllElts(newList_);
        }
        OperationNode child_ = getFirstChild();
        setResultClass(AnaClassArgumentMatching.copy(child_.getResultClass(), _page.getStandards()));
    }

    @Override
    public void quickCalculate(AnalyzedPageEl _page) {
        setArg(this, _page);
    }

    private static void setArg(FirstOptOperation _par, AnalyzedPageEl _page) {
        CustList<OperationNode> chidren_ = _par.getChildrenNodes();
        CustList<Argument> arguments_ = new CustList<Argument>();
        for (OperationNode o: chidren_) {
            arguments_.add(o.getArgument());
        }
        _par.setSimpleArgumentAna(arguments_.first(), _page);
    }
    public int getOffset() {
        return offset;
    }
}
