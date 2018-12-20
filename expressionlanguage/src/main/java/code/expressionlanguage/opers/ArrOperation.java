package code.expressionlanguage.opers;
import code.expressionlanguage.Analyzable;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.ElUtil;
import code.expressionlanguage.ExecutableCode;
import code.expressionlanguage.OperationsSequence;
import code.expressionlanguage.PrimitiveTypeUtil;
import code.expressionlanguage.errors.custom.BadOperandsNumber;
import code.expressionlanguage.errors.custom.UnexpectedTypeOperationError;
import code.expressionlanguage.methods.util.ArgumentsPair;
import code.expressionlanguage.opers.util.ClassArgumentMatching;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.structs.ArrayStruct;
import code.expressionlanguage.structs.NumberStruct;
import code.expressionlanguage.structs.Struct;
import code.util.CustList;
import code.util.IdMap;
import code.util.NatTreeMap;

public final class ArrOperation extends ReflectableInvokingOperation implements SettableElResult {

    private boolean variable;

    private boolean catString;

    public ArrOperation(int _index,
            int _indexChild, MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    public void analyze(Analyzable _conf) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        if (chidren_.size() != 1) {
            setRelativeOffsetPossibleAnalyzable(getIndexInEl(), _conf);
            BadOperandsNumber badNb_ = new BadOperandsNumber();
            badNb_.setFileName(_conf.getCurrentFileName());
            badNb_.setOperandsNumber(chidren_.size());
            badNb_.setIndexFile(_conf.getCurrentLocationIndex());
            _conf.getClasses().addError(badNb_);
            setResultClass(new ClassArgumentMatching(_conf.getStandards().getAliasObject()));
            return;
        }
        ClassArgumentMatching class_ = getPreviousResultClass();
        OperationNode right_ = chidren_.last();
        ClassArgumentMatching indexClass_ = right_.getResultClass();
        setRelativeOffsetPossibleAnalyzable(right_.getIndexInEl(), _conf);
        LgNames stds_ = _conf.getStandards();
        String primInt_ = stds_.getAliasPrimInteger();
        Argument rightArg_ = right_.getArgument();
        boolean convertNumber_ = false;
        if (rightArg_ != null && rightArg_.getStruct() instanceof NumberStruct) {
            Number value_ = ((NumberStruct)rightArg_.getStruct()).getInstance();
            long valueUnwrapped_ = value_.longValue();
            if (valueUnwrapped_ >= Integer.MIN_VALUE && valueUnwrapped_ <= Integer.MAX_VALUE) {
                right_.getResultClass().setUnwrapObject(primInt_);
                convertNumber_ = true;
            }
        }
        if (!convertNumber_ && !indexClass_.isNumericInt(_conf)) {
            UnexpectedTypeOperationError un_ = new UnexpectedTypeOperationError();
            un_.setIndexFile(_conf.getCurrentLocationIndex());
            un_.setFileName(_conf.getCurrentFileName());
            un_.setExpectedResult(_conf.getStandards().getAliasPrimInteger());
            un_.setOperands(indexClass_);
            _conf.getClasses().addError(un_);
            class_ = new ClassArgumentMatching(_conf.getStandards().getAliasObject());
            setResultClass(class_);
            return;
        }
        setRelativeOffsetPossibleAnalyzable(chidren_.first().getIndexInEl(), _conf);
        if (class_ == null || !class_.isArray()) {
            UnexpectedTypeOperationError un_ = new UnexpectedTypeOperationError();
            un_.setIndexFile(_conf.getCurrentLocationIndex());
            un_.setFileName(_conf.getCurrentFileName());
            un_.setExpectedResult(PrimitiveTypeUtil.getPrettyArrayType(_conf.getStandards().getAliasObject()));
            un_.setOperands(class_);
            _conf.getClasses().addError(un_);
            class_ = new ClassArgumentMatching(_conf.getStandards().getAliasObject());
            setResultClass(class_);
            return;
        }
        if (!convertNumber_) {
            indexClass_.setUnwrapObject(PrimitiveTypeUtil.toPrimitive(indexClass_, true, _conf));
        }
        class_ = PrimitiveTypeUtil.getQuickComponentType(class_);
        setResultClass(class_);
    }
    @Override
    public void quickCalculate(Analyzable _conf) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        if (!_conf.isGearConst()) {
            return;
        }
        if (getPreviousArgument() == null) {
            return;
        }
        Struct array_;
        array_ = getPreviousArgument().getStruct();
        if (!(array_ instanceof ArrayStruct)) {
            return;
        }
        Struct o_ = chidren_.last().getArgument().getStruct();
        if (!(o_ instanceof NumberStruct)) {
            return;
        }
        int index_ = ((NumberStruct)o_).getInstance().intValue();
        if (index_ < 0) {
            return;
        }
        Struct[] str_ = ((ArrayStruct)array_).getInstance();
        if (index_ >= str_.length) {
            return;
        }
        Struct res_ = str_[index_];
        Argument arg_ = Argument.createVoid();
        arg_.setStruct(res_);
        setSimpleArgumentAna(arg_, _conf);
    }
    @Override
    public Argument calculate(IdMap<OperationNode, ArgumentsPair> _nodes,
            ContextEl _conf) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        setRelativeOffsetPossibleLastPage(chidren_.first().getIndexInEl(), _conf);
        int max_ = chidren_.size();
        if (resultCanBeSet()) {
            max_--;
        }
        Argument a_ = getArgument(_nodes, max_, _conf);
        if (resultCanBeSet()) {
            setQuickSimpleArgument(a_, _conf, _nodes);
        } else {
            setSimpleArgument(a_, _conf, _nodes);
        }
        return a_;
    }

    Argument getArgument(IdMap<OperationNode, ArgumentsPair> _nodes,
            int _maxIndexChildren, ContextEl _conf) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        Struct array_;
        array_ = ElUtil.getPreviousArgument(_nodes,this).getStruct();
        Argument a_ = new Argument();
        for (int i = CustList.FIRST_INDEX; i < _maxIndexChildren; i++) {
            OperationNode op_ = chidren_.get(i);
            NumberStruct o_ = (NumberStruct) ElUtil.getArgument(_nodes,op_).getStruct();
            int indexEl_ = chidren_.get(i).getIndexInEl();
            setRelativeOffsetPossibleLastPage(indexEl_, _conf);
            array_ = InvokingOperation.getElement(array_, o_, _conf);
            if (_conf.hasExceptionOrFailInit()) {
                return a_;
            }
        }
        a_.setStruct(array_);
        return a_;
    }

    @Override
    public void calculate(ExecutableCode _conf) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        setRelativeOffsetPossibleLastPage(chidren_.first().getIndexInEl(), _conf);
        int max_ = chidren_.size();
        if (resultCanBeSet()) {
            max_--;
        }
        Argument a_ = getArgument(max_, _conf);
        if (resultCanBeSet()) {
            setQuickSimpleArgument(a_, _conf);
        } else {
            setSimpleArgument(a_, _conf);
        }
    }

    Argument getArgument(int _maxIndexChildren, ExecutableCode _conf) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        Struct array_;
        array_ = getPreviousArgument().getStruct();
        Argument a_ = new Argument();
        for (int i = CustList.FIRST_INDEX; i < _maxIndexChildren; i++) {
            NumberStruct o_ = (NumberStruct)chidren_.get(i).getArgument().getStruct();
            int indexEl_ = chidren_.get(i).getIndexInEl();
            setRelativeOffsetPossibleLastPage(indexEl_, _conf);
            array_ = InvokingOperation.getElement(array_, o_, _conf);
            if (_conf.getContextEl().hasException()) {
                return a_;
            }
        }
        a_.setStruct(array_);
        return a_;
    }
    static void setCheckedElement(Struct _array,NumberStruct _index, Argument _element, ExecutableCode _conf) {
        InvokingOperation.setElement(_array, _index, _element.getStruct(), _conf);
    }
    
    @Override
    void calculateChildren() {
        NatTreeMap<Integer, String> vs_ = getOperations().getValues();
        vs_.removeKey(vs_.firstKey());
        getChildren().putAllMap(vs_);
    }

    @Override
    public boolean resultCanBeSet() {
        return variable;
    }

    @Override
    public void setVariable(boolean _variable) {
        variable = _variable;
    }

    public boolean isCatString() {
        return catString;
    }

    @Override
    public void setCatenizeStrings() {
        catString = true;
    }

    @Override
    public Argument calculateSetting(
            IdMap<OperationNode, ArgumentsPair> _nodes, ContextEl _conf,
            Argument _right) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        Argument a_ = ElUtil.getArgument(_nodes,this);
        setRelativeOffsetPossibleLastPage(chidren_.first().getIndexInEl(), _conf);
        OperationNode lastElement_ = chidren_.last();
        Struct array_;
        array_ = ElUtil.getPreviousArgument(_nodes,this).getStruct();
        Argument lastArg_ = ElUtil.getArgument(_nodes, lastElement_);
        a_.setStruct(affectArray(array_, lastArg_, lastElement_.getIndexInEl(), _right, _conf));
        setSimpleArgument(a_, _conf, _nodes);
        return a_;
    }

    @Override
    public void calculateSetting(ExecutableCode _conf, Argument _right) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        Argument a_ = getArgument();
        OperationNode lastElement_ = chidren_.last();
        Argument last_ = lastElement_.getArgument();
        Struct array_;
        array_ = getPreviousArgument().getStruct();
        a_.setStruct(affectArray(array_, last_, lastElement_.getIndexInEl(), _right, _conf));
        if (_conf.getContextEl().hasException()) {
            return;
        }
        setSimpleArgument(a_, _conf);
    }

    @Override
    public Argument calculateCompoundSetting(
            IdMap<OperationNode, ArgumentsPair> _nodes, ContextEl _conf,
            String _op, Argument _right) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        Argument a_ = ElUtil.getArgument(_nodes,this);
        Struct store_;
        store_ = a_.getStruct();
        setRelativeOffsetPossibleLastPage(chidren_.first().getIndexInEl(), _conf);
        OperationNode lastElement_ = chidren_.last();
        Struct array_;
        array_ = ElUtil.getPreviousArgument(_nodes, this).getStruct();
        Argument lastArg_ = ElUtil.getArgument(_nodes, lastElement_);
        a_.setStruct(compoundAffectArray(array_, store_, lastArg_, lastElement_.getIndexInEl(), _op,_right, _conf));
        setSimpleArgument(a_, _conf, _nodes);
        return a_;
    }

    @Override
    public void calculateCompoundSetting(ExecutableCode _conf, String _op,
            Argument _right) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        Argument a_ = getArgument();
        Struct store_;
        store_ = a_.getStruct();
        OperationNode lastElement_ = chidren_.last();
        Argument last_ = lastElement_.getArgument();
        Struct array_;
        array_ = getPreviousArgument().getStruct();
        a_.setStruct(compoundAffectArray(array_, store_, last_, lastElement_.getIndexInEl(), _op, _right, _conf));
        if (_conf.getContextEl().hasException()) {
            return;
        }
        setSimpleArgument(a_, _conf);
    }

    @Override
    public Argument calculateSemiSetting(
            IdMap<OperationNode, ArgumentsPair> _nodes, ContextEl _conf,
            String _op, boolean _post) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        Argument a_ = ElUtil.getArgument(_nodes,this);
        Struct store_;
        store_ = a_.getStruct();
        setRelativeOffsetPossibleLastPage(chidren_.first().getIndexInEl(), _conf);
        OperationNode lastElement_ = chidren_.last();
        Struct array_;
        array_ = ElUtil.getPreviousArgument(_nodes,this).getStruct();
        Argument lastArg_ = ElUtil.getArgument(_nodes, lastElement_);
        a_.setStruct(semiAffectArray(array_, store_, lastArg_, lastElement_.getIndexInEl(), _op, _post, _conf));
        setSimpleArgument(a_, _conf, _nodes);
        return a_;
    }

    @Override
    public void calculateSemiSetting(ExecutableCode _conf, String _op,
            boolean _post) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        Argument a_ = getArgument();
        Struct store_;
        store_ = a_.getStruct();
        OperationNode lastElement_ = chidren_.last();
        Argument last_ = lastElement_.getArgument();
        Struct array_;
        array_ = getPreviousArgument().getStruct();
        a_.setStruct(semiAffectArray(array_, store_, last_, lastElement_.getIndexInEl(), _op, _post, _conf));
        if (_conf.getContextEl().hasException()) {
            return;
        }
        setSimpleArgument(a_, _conf);
    }

    Struct affectArray(Struct _array,Argument _index, int _indexEl, Argument _right, ExecutableCode _conf) {
        setRelativeOffsetPossibleLastPage(_indexEl, _conf);
        NumberStruct o_ = (NumberStruct)_index.getStruct();
        if (_conf.getContextEl().hasExceptionOrFailInit()) {
            return _right.getStruct();
        }
        InvokingOperation.setElement(_array, o_, _right.getStruct(), _conf);
        return _right.getStruct();
    }

    Struct compoundAffectArray(Struct _array,Struct _stored,Argument _index, int _indexEl, String _op, Argument _right, ExecutableCode _conf) {
        setRelativeOffsetPossibleLastPage(_indexEl, _conf);
        NumberStruct o_ = (NumberStruct)_index.getStruct();
        if (_conf.getContextEl().hasExceptionOrFailInit()) {
            return _stored;
        }
        Argument left_ = new Argument();
        left_.setStruct(_stored);
        ClassArgumentMatching clArg_ = getResultClass();
        Argument res_;
        res_ = NumericOperation.calculateAffect(left_, _conf, _right, _op, catString, clArg_);
        if (_conf.getContextEl().hasExceptionOrFailInit()) {
            return _stored;
        }
        InvokingOperation.setElement(_array, o_, res_.getStruct(), _conf);
        return res_.getStruct();
    }
    Struct semiAffectArray(Struct _array,Struct _stored,Argument _index, int _indexEl, String _op, boolean _post, ExecutableCode _conf) {
        setRelativeOffsetPossibleLastPage(_indexEl, _conf);
        NumberStruct o_ = (NumberStruct)_index.getStruct();
        if (_conf.getContextEl().hasExceptionOrFailInit()) {
            return _stored;
        }
        Argument left_ = new Argument();
        left_.setStruct(_stored);
        ClassArgumentMatching clArg_ = getResultClass();
        Argument res_;
        res_ = NumericOperation.calculateIncrDecr(left_, _conf, _op, clArg_);
        if (_conf.getContextEl().hasExceptionOrFailInit()) {
            return _stored;
        }
        InvokingOperation.setElement(_array, o_, res_.getStruct(), _conf);
        Argument out_ = SemiAffectationOperation.getPrePost(_post, left_, res_);
        return out_.getStruct();
    }

    @Override
    public Argument endCalculate(ContextEl _conf, IdMap<OperationNode, ArgumentsPair> _nodes, Argument _right) {
        return endCalculate(_conf, _nodes, false, null, _right);
    }
    @Override
    public Argument endCalculate(ExecutableCode _conf, Argument _right) {
        return endCalculate(_conf, false, null, _right);
    }
    @Override
    public Argument endCalculate(ContextEl _conf,
            IdMap<OperationNode, ArgumentsPair> _nodes, boolean _post,
            Argument _stored, Argument _right) {
        Struct array_;
        array_ = ElUtil.getPreviousArgument(_nodes,this).getStruct();
        CustList<OperationNode> chidren_ = getChildrenNodes();
        setRelativeOffsetPossibleLastPage(chidren_.first().getIndexInEl(), _conf);
        OperationNode lastElement_ = chidren_.last();
        Argument index_ = ElUtil.getArgument(_nodes, lastElement_);
        InvokingOperation.setElement(array_, (NumberStruct)index_.getStruct(), _right.getStruct(), _conf);
        Argument out_ = SemiAffectationOperation.getPrePost(_post, _stored, _right);
        setSimpleArgument(out_, _conf, _nodes);
        return out_;
    }
    @Override
    public Argument endCalculate(ExecutableCode _conf, boolean _post,
            Argument _stored, Argument _right) {
        Struct array_;
        array_ = getPreviousArgument().getStruct();
        CustList<OperationNode> chidren_ = getChildrenNodes();
        Argument a_ = getArgument();
        setRelativeOffsetPossibleLastPage(chidren_.first().getIndexInEl(), _conf);
        OperationNode lastElement_ = chidren_.last();
        Argument index_ = lastElement_.getArgument();
        InvokingOperation.setElement(array_, (NumberStruct)index_.getStruct(), _right.getStruct(), _conf);
        if (_conf.getContextEl().hasException()) {
            return a_;
        }
        Argument out_ = SemiAffectationOperation.getPrePost(_post, _stored, _right);
        setSimpleArgument(out_, _conf);
        return out_;
    }
}
