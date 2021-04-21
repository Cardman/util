package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.analyze.instr.OperationsSequence;
import code.expressionlanguage.analyze.instr.PartOffset;
import code.expressionlanguage.analyze.types.AnaClassArgumentMatching;
import code.expressionlanguage.analyze.types.AnaTypeUtil;
import code.expressionlanguage.analyze.util.ClassMethodIdReturn;
import code.expressionlanguage.linkage.ExportCst;
import code.maths.litteralcom.StrTypes;
import code.util.CustList;
import code.util.StringList;
import code.util.core.StringUtil;

public final class RangeOperation extends MethodOperation {
    private int opOffset;
    private boolean okNum;
    private boolean implicitMiddle;
    public RangeOperation(int _index, int _indexChild, MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    void calculateChildren() {
        StrTypes vs_ = getOperations().getValues();
        if (vs_.size() == 2 || vs_.size() == 3) {
            if (vs_.getValue(1).trim().isEmpty()) {
                vs_.remove(1);
                implicitMiddle = true;
            }
        }
        getChildren().addAllEntries(vs_);
    }

    @Override
    public void analyze(AnalyzedPageEl _page) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        okNum = true;
        if (chidren_.size() > 3) {
            okNum = false;
            _page.setOkNumOp(false);
            int in_ = Math.min(getOperations().getOperators().size()-1,2);
            setRelativeOffsetPossibleAnalyzable(getIndexInEl()+getOperations().getOperators().getKey(in_), _page);
            FoundErrorInterpret badNb_ = new FoundErrorInterpret();
            badNb_.setFileName(_page.getLocalizer().getCurrentFileName());
            int index_ = _page.getLocalizer().getCurrentLocationIndex();
            badNb_.setIndexFile(index_);
            //first oper
            badNb_.buildError(_page.getAnalysisMessages().getOperatorNbDiff(),
                    Long.toString(3),
                    Long.toString(chidren_.size()),
                    "???"
            );
            _page.getLocalizer().addError(badNb_);
            for (int i = 0; i < in_;i++) {
                CustList<PartOffset> err_ = new CustList<PartOffset>();
                getPartOffsetsChildren().add(err_);
            }
            CustList<PartOffset> err_ = new CustList<PartOffset>();
            err_.add(new PartOffset(ExportCst.anchorErr(badNb_.getBuiltError()),index_));
            err_.add(new PartOffset(ExportCst.END_ANCHOR,index_+getOperations().getOperators().getValue(in_).length()));
            getPartOffsetsChildren().add(err_);
            setResultClass(new AnaClassArgumentMatching(_page.getAliasRange()));
            return;
        }
        opOffset= getOperations().getOperators().firstKey();
        AnaClassArgumentMatching clMatchLeft_ = chidren_.first().getResultClass();
        if (chidren_.size() == 3||chidren_.size() == 2) {
            AnaClassArgumentMatching clMatchRight_ = chidren_.get(1).getResultClass();
            CustList<PartOffset> err_ = new CustList<PartOffset>();
            if (!clMatchLeft_.isNumericInt(_page)) {
                ClassMethodIdReturn res_ = OperationNode.tryGetDeclaredImplicitCast(_page.getAliasPrimInteger(), clMatchLeft_, _page);
                if (res_.isFoundMethod()) {
                    clMatchLeft_.implicitInfos(res_);
                } else {
                    okNum = false;
                    _page.setOkNumOp(false);
                    setRelativeOffsetPossibleAnalyzable(getIndexInEl()+getOperations().getOperators().getKey(0), _page);
                    FoundErrorInterpret un_ = new FoundErrorInterpret();
                    int index_ = _page.getLocalizer().getCurrentLocationIndex();
                    un_.setIndexFile(index_);
                    un_.setFileName(_page.getLocalizer().getCurrentFileName());
                    //oper
                    un_.buildError(_page.getAnalysisMessages().getUnexpectedOperandTypes(),
                            StringUtil.join(new StringList(
                                    StringUtil.join(clMatchLeft_.getNames(),ExportCst.JOIN_TYPES),
                                    StringUtil.join(clMatchRight_.getNames(),ExportCst.JOIN_TYPES)
                            ),ExportCst.JOIN_OPERANDS),
                            "???");
                    _page.getLocalizer().addError(un_);
                    err_.add(new PartOffset(ExportCst.anchorErr(un_.getBuiltError()),index_));
                    err_.add(new PartOffset(ExportCst.END_ANCHOR,index_+ 1));
                }
            }
            if (!clMatchRight_.isNumericInt(_page)) {
                ClassMethodIdReturn res_ = OperationNode.tryGetDeclaredImplicitCast(_page.getAliasPrimInteger(), clMatchRight_, _page);
                if (res_.isFoundMethod()) {
                    clMatchRight_.implicitInfos(res_);
                } else {
                    okNum = false;
                    _page.setOkNumOp(false);
                    setRelativeOffsetPossibleAnalyzable(getIndexInEl()+getOperations().getOperators().getKey(0), _page);
                    FoundErrorInterpret un_ = new FoundErrorInterpret();
                    int index_ = _page.getLocalizer().getCurrentLocationIndex();
                    un_.setIndexFile(index_);
                    un_.setFileName(_page.getLocalizer().getCurrentFileName());
                    //oper
                    un_.buildError(_page.getAnalysisMessages().getUnexpectedOperandTypes(),
                            StringUtil.join(new StringList(
                                    StringUtil.join(clMatchLeft_.getNames(),ExportCst.JOIN_TYPES),
                                    StringUtil.join(clMatchRight_.getNames(),ExportCst.JOIN_TYPES)
                            ),ExportCst.JOIN_OPERANDS),
                            "???");
                    _page.getLocalizer().addError(un_);
                    err_.add(new PartOffset(ExportCst.anchorErr(un_.getBuiltError()),index_ +2));
                    err_.add(new PartOffset(ExportCst.END_ANCHOR,index_+ 3));
                }
            }
            if (!err_.isEmpty()) {
                getPartOffsetsChildren().add(err_);
            }
            clMatchLeft_.setUnwrapObject(AnaTypeUtil.toPrimitive(clMatchLeft_, _page), _page.getPrimitiveTypes());
            clMatchRight_.setUnwrapObject(AnaTypeUtil.toPrimitive(clMatchRight_, _page), _page.getPrimitiveTypes());
            if (chidren_.size() == 3) {
                if (getPartOffsetsChildren().isEmpty()) {
                    getPartOffsetsChildren().add(new CustList<PartOffset>());
                }
                AnaClassArgumentMatching clMatchStep_ = chidren_.last().getResultClass();
                if (!clMatchStep_.isNumericInt(_page)) {
                    ClassMethodIdReturn res_ = OperationNode.tryGetDeclaredImplicitCast(_page.getAliasPrimInteger(), clMatchStep_, _page);
                    if (res_.isFoundMethod()) {
                        clMatchStep_.implicitInfos(res_);
                    } else {
                        okNum = false;
                        _page.setOkNumOp(false);
                        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+getOperations().getOperators().getKey(1), _page);
                        FoundErrorInterpret un_ = new FoundErrorInterpret();
                        int index_ = _page.getLocalizer().getCurrentLocationIndex();
                        un_.setIndexFile(index_);
                        un_.setFileName(_page.getLocalizer().getCurrentFileName());
                        //oper
                        un_.buildError(_page.getAnalysisMessages().getUnexpectedOperandTypes(),
                                StringUtil.join(clMatchStep_.getNames(),ExportCst.JOIN_TYPES),
                                "???");
                        _page.getLocalizer().addError(un_);
                        CustList<PartOffset> step_ = new CustList<PartOffset>();
                        step_.add(new PartOffset(ExportCst.anchorErr(un_.getBuiltError()),index_ +2));
                        step_.add(new PartOffset(ExportCst.END_ANCHOR,index_+ 3));
                        getPartOffsetsChildren().add(step_);
                    }
                }
                clMatchStep_.setUnwrapObject(AnaTypeUtil.toPrimitive(clMatchStep_, _page), _page.getPrimitiveTypes());
            }
        } else {
            if (!clMatchLeft_.isNumericInt(_page)) {
                ClassMethodIdReturn res_ = OperationNode.tryGetDeclaredImplicitCast(_page.getAliasPrimInteger(), clMatchLeft_, _page);
                if (res_.isFoundMethod()) {
                    clMatchLeft_.implicitInfos(res_);
                } else {
                    okNum = false;
                    _page.setOkNumOp(false);
                    setRelativeOffsetPossibleAnalyzable(getIndexInEl()+getOperations().getOperators().getKey(0), _page);
                    FoundErrorInterpret un_ = new FoundErrorInterpret();
                    int index_ = _page.getLocalizer().getCurrentLocationIndex();
                    un_.setIndexFile(index_);
                    un_.setFileName(_page.getLocalizer().getCurrentFileName());
                    //oper
                    un_.buildError(_page.getAnalysisMessages().getUnexpectedOperandTypes(),
                            StringUtil.join(clMatchLeft_.getNames(),ExportCst.JOIN_TYPES),
                            "???");
                    _page.getLocalizer().addError(un_);
                    CustList<PartOffset> err_ = new CustList<PartOffset>();
                    err_.add(new PartOffset(ExportCst.anchorErr(un_.getBuiltError()),index_));
                    err_.add(new PartOffset(ExportCst.END_ANCHOR,index_+ 1));
                    getPartOffsetsChildren().add(err_);
                }
            }
            clMatchLeft_.setUnwrapObject(AnaTypeUtil.toPrimitive(clMatchLeft_, _page), _page.getPrimitiveTypes());
        }
        setResultClass(new AnaClassArgumentMatching(_page.getAliasRange()));
    }

    public int getOpOffset() {
        return opOffset;
    }

    public boolean isOkNum() {
        return okNum;
    }

    public boolean isImplicitMiddle() {
        return implicitMiddle;
    }
}
