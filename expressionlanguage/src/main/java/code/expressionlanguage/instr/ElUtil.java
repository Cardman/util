package code.expressionlanguage.instr;

import code.expressionlanguage.Analyzable;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.calls.AbstractPageEl;
import code.expressionlanguage.errors.custom.BadElError;
import code.expressionlanguage.errors.custom.BadOperandsNumber;
import code.expressionlanguage.inherits.PrimitiveTypeUtil;
import code.expressionlanguage.methods.*;
import code.expressionlanguage.methods.util.ArgumentsPair;
import code.expressionlanguage.opers.*;
import code.expressionlanguage.opers.exec.*;
import code.expressionlanguage.opers.util.Assignment;
import code.expressionlanguage.opers.util.ClassArgumentMatching;
import code.expressionlanguage.opers.util.ClassField;
import code.expressionlanguage.opers.util.FieldInfo;
import code.expressionlanguage.structs.Struct;
import code.util.*;

public final class ElUtil {

    private ElUtil() {
    }

    public static Argument tryToCalculate(ContextEl _conf, ExpressionLanguage _right, int _offset) {
        if (_right.isFinished()) {
            return _right.getArgument();
        }
        IdMap<ExecOperationNode, ArgumentsPair> allRight_ = _right.getArguments();
        calculate(allRight_, _right, _conf, _offset);
        if (_conf.callsOrException()) {
            return _right.getArgument();
        }
        _right.finish();
        return _right.getArgument();
    }

    public static StringList getFieldNames(String _el, ContextEl _conf, Calculation _calcul) {
        boolean hiddenVarTypes_ = _calcul.isStaticBlock();
        _conf.setStaticContext(hiddenVarTypes_);
        Delimiters d_ = ElResolver.checkSyntax(_el, _conf, CustList.FIRST_INDEX);
        OperationsSequence opTwo_ = ElResolver.getOperationsSequence(CustList.FIRST_INDEX, _el, _conf, d_);
        StringList names_ = new StringList();
        if (opTwo_.getOperators().isEmpty()) {
            for (String v: opTwo_.getValues().values()) {
                names_.add(getFieldName(v));
            }
            names_.removeAllString("");
            return names_;
        }
        if (opTwo_.getPriority() == ElResolver.DECL_PRIO) {
            for (String v: opTwo_.getValues().values()) {
                names_.add(getFieldName(v));
            }
            names_.removeAllString("");
            return names_;
        }
        if (opTwo_.getPriority() == ElResolver.AFF_PRIO) {
            String var_ = opTwo_.getValues().firstValue();
            names_.add(getFieldName(var_));
        }
        names_.removeAllString("");
        return names_;
    }

    private static String getFieldName(String _v) {
        String v_ = _v.trim();
        int k_ = 0;
        int lenField_ = v_.length();
        StringBuilder fieldName_ = new StringBuilder();
        while (k_ < lenField_) {
            char fieldChar_ = v_.charAt(k_);
            if (!StringList.isDollarWordChar(fieldChar_)) {
                break;
            }
            fieldName_.append(fieldChar_);
            k_++;
        }
        return fieldName_.toString();
    }
    public static CustList<ExecOperationNode> getAnalyzedOperations(String _el, ContextEl _conf, Calculation _calcul) {
        boolean hiddenVarTypes_ = _calcul.isStaticBlock();
        _conf.setStaticContext(hiddenVarTypes_);
        Delimiters d_ = ElResolver.checkSyntax(_el, _conf, CustList.FIRST_INDEX);
        if (d_.getBadOffset() >= 0) {
            BadElError badEl_ = new BadElError();
            badEl_.setOffsetInEl(d_.getBadOffset());
            badEl_.setEl(_el);
            badEl_.setFileName(_conf.getCurrentFileName());
            badEl_.setIndexFile(_conf.getCurrentLocationIndex());
            _conf.getClasses().addError(badEl_);
            OperationsSequence tmpOp_ = new OperationsSequence();
            tmpOp_.setDelimiter(new Delimiters());
            ErrorPartOperation e_ = new ErrorPartOperation(0, 0, null, tmpOp_);
            String argClName_ = _conf.getStandards().getAliasObject();
            e_.setResultClass(new ClassArgumentMatching(argClName_));    
            Block currentBlock_ = _conf.getCurrentBlock();
            if (processAssign(_conf, currentBlock_)) {
                currentBlock_.defaultAssignmentBefore(_conf, e_);
                e_.tryAnalyzeAssignmentAfter(_conf);
                currentBlock_.defaultAssignmentAfter(_conf, e_);
            }
            e_.setOrder(0);
            return new CustList<ExecOperationNode>((ExecOperationNode)ExecOperationNode.createExecOperationNode(e_));
        }
        OperationsSequence opTwo_ = ElResolver.getOperationsSequence(CustList.FIRST_INDEX, _el, _conf, d_);
        OperationNode op_ = OperationNode.createOperationNode(CustList.FIRST_INDEX, CustList.FIRST_INDEX, null, opTwo_, _conf);
        String fieldName_ = _calcul.getFieldName();
        _conf.setStaticContext(hiddenVarTypes_ || op_ instanceof AbstractInvokingConstructor);
        if (op_ instanceof StandardInstancingOperation) {
            ((StandardInstancingOperation)op_).setFieldName(fieldName_);
        }
        CustList<OperationNode> all_ = getSortedDescNodes(op_, hiddenVarTypes_, _conf);
        return getExecutableNodes(_conf,all_);
    }

    private static boolean processAssign(ContextEl _conf, Block _currentBlock) {
        return _currentBlock != null && !_conf.isAnnotAnalysis() && !_conf.isGearConst();
    }


    private static CustList<OperationNode> getSortedDescNodes(OperationNode _root, boolean _staticBlock,ContextEl _context) {
        CustList<OperationNode> list_ = new CustList<OperationNode>();
        Block currentBlock_ = _context.getCurrentBlock();
        if (processAssign(_context, currentBlock_)) {
            currentBlock_.defaultAssignmentBefore(_context, _root);
        }
        OperationNode c_ = _root;
        while (true) {
            if (c_ == null) {
                if (processAssign(_context, currentBlock_)) {
                    currentBlock_.defaultAssignmentAfter(_context, _root);
                }
                break;
            }
            if (c_ instanceof PreAnalyzableOperation) {
                ((PreAnalyzableOperation)c_).preAnalyze(_context);
            }
            c_ = getAnalyzedNext(c_, _root, list_, _staticBlock, _context);
        }
        return list_;
    }

    private static OperationNode getAnalyzedNext(OperationNode _current, OperationNode _root, CustList<OperationNode> _sortedNodes, boolean _staticBlock,ContextEl _context) {
        
        OperationNode next_ = createFirstChild(_current, _context, 0);
        if (next_ != null) {
            ((MethodOperation) _current).appendChild(next_);
            if (!_context.isAnnotAnalysis() && !_context.isGearConst()) {
                ((MethodOperation) _current).tryAnalyzeAssignmentBefore(_context, next_);
            }
            return next_;
        }
        OperationNode current_ = _current;
        while (true) {
            _context.setOkNumOp(true);
            current_.analyze(_context);
            current_.setOrder(_sortedNodes.size());
            if (current_ instanceof ReductibleOperable) {
                ((ReductibleOperable)current_).tryCalculateNode(_context);
            }
            if (!_context.isAnnotAnalysis() && !_context.isGearConst()) {
                current_.tryAnalyzeAssignmentAfter(_context);
            }
            _sortedNodes.add(current_);
            if (current_ instanceof StaticInitOperation) {
                next_ = createFirstChild(current_.getParent(), _context, 1);
            } else {
                next_ = createNextSibling(current_, _context);
            }
            MethodOperation par_ = current_.getParent();
            if (next_ != null) {
                if (par_ instanceof DotOperation) {
                    if (!(next_ instanceof PossibleIntermediateDotted)) {
                        next_.setRelativeOffsetPossibleAnalyzable(next_.getIndexInEl(), _context);
                        BadOperandsNumber badNb_ = new BadOperandsNumber();
                        badNb_.setFileName(_context.getCurrentFileName());
                        badNb_.setOperandsNumber(0);
                        badNb_.setIndexFile(_context.getCurrentLocationIndex());
                        _context.getClasses().addError(badNb_);
                    } else {
                        PossibleIntermediateDotted possible_ = (PossibleIntermediateDotted) next_;
                        boolean static_ = current_ instanceof StaticAccessOperation;
                        possible_.setIntermediateDotted();
                        possible_.setPreviousArgument(current_.getArgument());
                        possible_.setPreviousResultClass(current_.getResultClass(), static_);
                        current_.setSiblingSet(possible_);
                    }
                }
                par_.appendChild(next_);
                if (!_context.isAnnotAnalysis() && !_context.isGearConst()) {
                    par_.tryAnalyzeAssignmentBeforeNextSibling(_context, next_, current_);
                }
                return next_;
            }
            if (par_ == _root) {
                _context.setOkNumOp(true);
                par_.analyze(_context);
                ClassArgumentMatching cl_ = par_.getResultClass();
                if (PrimitiveTypeUtil.isPrimitive(cl_, _context)) {
                    cl_.setUnwrapObject(cl_);
                    par_.cancelArgument();
                }
                par_.tryCalculateNode(_context);
                if (!_context.isAnnotAnalysis() && !_context.isGearConst()) {
                    par_.tryAnalyzeAssignmentAfter(_context);
                }
                par_.setOrder(_sortedNodes.size());
                _sortedNodes.add(par_);
                return null;
            }
            if (par_ == null) {
                return null;
            }
            current_ = par_;
        }
    }
    private static OperationNode createFirstChild(OperationNode _block, ContextEl _context, int _index) {
        if (!(_block instanceof MethodOperation)) {
            return null;
        }
        MethodOperation block_ = (MethodOperation) _block;
        if (block_.getChildren().isEmpty()) {
            if (_context.getOptions().isInitializeStaticClassFirst() && block_ instanceof StandardInstancingOperation) {
                if (_index == CustList.FIRST_INDEX) {
                    Delimiters d_ = block_.getOperations().getDelimiter();
                    OperationsSequence opSeq_ = new OperationsSequence();
                    opSeq_.setFctName(block_.getOperations().getFctName());
                    opSeq_.setDelimiter(new Delimiters());
                    opSeq_.getDelimiter().setIndexBegin(d_.getIndexBegin());
                    return new StaticInitOperation(block_.getIndexInEl(), CustList.FIRST_INDEX, block_, opSeq_);
                }
            }
            return null;
        }
        String value_ = block_.getChildren().getValue(0);
        Delimiters d_ = block_.getOperations().getDelimiter();
        int curKey_ = block_.getChildren().getKey(0);
        int offset_ = block_.getIndexInEl()+curKey_;
        if (_context.getOptions().isInitializeStaticClassFirst() && block_ instanceof StandardInstancingOperation) {
            if (_index == CustList.FIRST_INDEX) {
                OperationsSequence opSeq_ = new OperationsSequence();
                opSeq_.setFctName(block_.getOperations().getFctName());
                opSeq_.setDelimiter(new Delimiters());
                opSeq_.getDelimiter().setIndexBegin(d_.getIndexBegin());
                return new StaticInitOperation(block_.getIndexInEl(), CustList.FIRST_INDEX, block_, opSeq_);
            }
        }
        OperationsSequence r_ = ElResolver.getOperationsSequence(offset_, value_, _context, d_);
        OperationNode op_ = OperationNode.createOperationNode(offset_, _index, block_, r_, _context);
        return op_;
    }

    private static OperationNode createNextSibling(OperationNode _block, ContextEl _context) {
        MethodOperation p_ = _block.getParent();
        if (p_ == null) {
            return null;
        }
        IntTreeMap<String> children_ = p_.getChildren();
        int delta_ = 1;
        if (p_ instanceof StandardInstancingOperation) {
            if (p_.getFirstChild() instanceof StaticInitOperation) {
                delta_ = 0;
            }
        }
        if (_block.getIndexChild() + delta_ >= children_.size()) {
            return null;
        }
        String value_ = children_.getValue(_block.getIndexChild() + delta_);
        Delimiters d_ = _block.getOperations().getDelimiter();
        int curKey_ = children_.getKey(_block.getIndexChild() + delta_);
        int offset_ = p_.getIndexInEl()+curKey_;
        OperationsSequence r_ = ElResolver.getOperationsSequence(offset_, value_, _context, d_);
        OperationNode op_ = OperationNode.createOperationNode(offset_, _block.getIndexChild() + 1, p_, r_, _context);
        return op_;
    }
    public static CustList<OperationNode> filterInvoking(CustList<OperationNode> _list) {
        CustList<OperationNode> out_ = new CustList<OperationNode>();
        for (OperationNode o: _list) {
            if (o instanceof StaticInitOperation) {
                continue;
            }
            out_.add(o);
        }
        return out_;
    }
    public static boolean isInlineDeclaringField(Operable _var, Analyzable _an) {
        if (!_an.isGearConst()) {
            return false;
        }
        return isDeclaringField(_var,_an);
    }
    public static boolean isDeclaringField(Operable _var, Analyzable _an) {
        Block bl_ = _an.getCurrentBlock();
        if (!(bl_ instanceof FieldBlock)) {
            return false;
        }
        if (!(_var instanceof StandardFieldOperable)) {
            return false;
        }
        return isDeclaringVariable(_var);
    }

    public static boolean isDeclaringLoopVariable(MutableLoopVariableOperation _var, Analyzable _an) {
        Block bl_ = _an.getCurrentBlock();
        if (!_an.isMerged()) {
            return false;
        }
        if (!(bl_ instanceof ForMutableIterativeLoop)) {
            return false;
        }
        if (_an.getForLoopPartState() != ForLoopPart.INIT) {
            return false;
        }
        return isDeclaringVariable(_var);
    }
    public static boolean isDeclaringLoopVariable(MethodOperation _par, Analyzable _an) {
        Block bl_ = _an.getCurrentBlock();
        if (!_an.isMerged()) {
            return false;
        }
        if (!(bl_ instanceof ForMutableIterativeLoop)) {
            return false;
        }
        if (_an.getForLoopPartState() != ForLoopPart.INIT) {
            return false;
        }
        return isDeclaringVariable(_par);
    }
    public static boolean isDeclaringVariable(VariableOperation _var, Analyzable _an) {
        Block bl_ = _an.getCurrentBlock();
        if (!_an.isMerged()) {
            return false;
        }
        if (!(bl_.getPreviousSibling() instanceof DeclareVariable)) {
            return false;
        }
        return isDeclaringVariable(_var);
    }
    public static boolean isDeclaringVariable(MethodOperation _par, Analyzable _an) {
        Block bl_ = _an.getCurrentBlock();
        if (!_an.isMerged()) {
            return false;
        }
        if (!(bl_.getPreviousSibling() instanceof DeclareVariable)) {
            return false;
        }
        return isDeclaringVariable(_par);
    }
    public static boolean isDeclaringVariable(Operable _var) {
        ParentOperable par_ = _var.getParentOperable();
        if (par_ == null) {
            return true;
        }
        if (par_ instanceof DeclaringOperable) {
            return true;
        }
        if (par_ instanceof AffectationOperable) {
            if (par_.getParentOperable() == null) {
                return _var == par_.getFirstChildOperable();
            }
            if (par_.getParentOperable() instanceof DeclaringOperable) {
                return _var == par_.getFirstChildOperable();
            }
        }
        return false;
    }
    public static boolean isDeclaringVariable(MethodOperation _par) {
        if (_par == null) {
            return true;
        }
        if (_par instanceof DeclaringOperable) {
            return true;
        }
        if (_par instanceof AffectationOperable) {
            if (_par.getParent() == null) {
                return null == _par.getFirstChild();
            }
            if (_par.getParent() instanceof DeclaringOperable) {
                return null == _par.getFirstChild();
            }
        }
        return false;
    }
    public static boolean checkFinalVar(Analyzable _conf, Assignment _ass) {
        if (!_ass.isUnassignedAfter()) {
            return true;
        }
        return stepForLoop(_conf);
    }
    public static boolean checkFinalField(Analyzable _conf, SettableAbstractFieldOperation _cst, StringMap<Assignment> _ass) {
        boolean fromCurClass_ = _cst.isFromCurrentClass(_conf);
        ClassField cl_ = _cst.getFieldId();
        if (cl_ == null) {
            return false;
        }
        String fieldName_ = cl_.getFieldName();
        if (stepForLoop(_conf)) {
            return true;
        }
        boolean checkFinal_;
        if (_conf.isAssignedFields()) {
            checkFinal_ = true;
        } else if (_conf.isAssignedStaticFields()) {
            FieldInfo meta_ = _conf.getFieldInfo(cl_);
            if (meta_.isStaticField()) {
                checkFinal_ = true;
            } else if (!fromCurClass_) {
                checkFinal_ = true;
            } else {
                if (isDeclaringField(_cst, _conf)) {
                    checkFinal_ = false;
                } else {
                    checkFinal_ = false;
                    for (EntryCust<String, Assignment> e: _ass.entryList()) {
                        if (!StringList.quickEq(e.getKey(),fieldName_)) {
                            continue;
                        }
                        if (e.getValue().isUnassignedAfter()) {
                            continue;
                        }
                        checkFinal_ = true;
                    }
                }
            }
        } else if (!fromCurClass_) {
            checkFinal_ = true;
        } else {
            if (isDeclaringField(_cst, _conf)) {
                checkFinal_ = false;
            } else {
                checkFinal_ = false;
                for (EntryCust<String, Assignment> e: _ass.entryList()) {
                    if (!StringList.quickEq(e.getKey(), fieldName_)) {
                        continue;
                    }
                    if (e.getValue().isUnassignedAfter()) {
                        continue;
                    }
                    checkFinal_ = true;
                }
            }
        }
        return checkFinal_;
    }
    private static boolean stepForLoop(Analyzable _conf) {
        if (_conf.getCurrentBlock() instanceof ForMutableIterativeLoop) {
            return _conf.getForLoopPartState() == ForLoopPart.STEP;
        }
        return false;
    }

    private static void calculate(IdMap<ExecOperationNode,ArgumentsPair> _nodes, ExpressionLanguage _el, ContextEl _context, int _offset) {
        AbstractPageEl pageEl_ = _context.getLastPage();
        pageEl_.setTranslatedOffset(_offset);
        int fr_ = _el.getIndex();
        int len_ = _nodes.size();
        while (fr_ < len_) {
            ExecOperationNode o = _nodes.getKey(fr_);
            ArgumentsPair pair_ = _nodes.getValue(fr_);
            if (!(o instanceof AtomicExecCalculableOperation)) {
                _context.getCoverage().passBlockOperation(_context,o,Argument.createVoid());
                fr_++;
                continue;
            }
            if (pair_.getArgument() != null) {
                _context.getCoverage().passBlockOperation(_context,o,pair_.getArgument());
                fr_++;
                continue;
            }
            AtomicExecCalculableOperation a_ = (AtomicExecCalculableOperation)o;
            a_.calculate(_nodes, _context);
            if (_context.hasExceptionOrFailInit()) {
                pageEl_.setTranslatedOffset(0);
                pageEl_.clearCurrentEls();
                return;
            }
            if (_context.calls()) {
                _el.setCurrentOper(o);
                return;
            }
            Argument res_ = pair_.getArgument();
            Struct st_ = res_.getStruct();
            fr_ = ExecOperationNode.getNextIndex(o, st_);
        }
        _context.getLastPage().setTranslatedOffset(0);
    }
    public static void tryCalculate(FieldBlock _field, ContextEl _context, String _fieldName) {
        CustList<ExecOperationNode> nodes_ = _field.getOpValue();
        ExecOperationNode root_ = nodes_.last();
        CustList<ExecOperationNode> sub_;
        if (!(root_ instanceof ExecDeclaringOperation)) {
            sub_ = nodes_;
        } else {
            ExecMethodOperation m_ = (ExecMethodOperation)root_;
            int index_ = StringList.indexOf(_field.getFieldName(),_fieldName);
            CustList<ExecOperationNode> ch_ = m_.getChildrenNodes();
            ExecOperationNode rootLoc_ = ch_.get(index_);
            int from_;
            int to_ = rootLoc_.getOrder() + 1;
            if (index_ == 0) {
                from_ = 0;
            } else {
                from_ = ch_.get(index_-1).getOrder() + 1;
            }
            sub_ = nodes_.sub(from_, to_);
        }
        int ind_ = 0;
        int len_ = sub_.size();
        while (ind_ < len_) {
            ExecOperationNode curr_ = sub_.get(ind_);
            Argument a_ = curr_.getArgument();
            if (a_ != null) {
                ind_++;
                continue;
            }
            if (curr_ instanceof ReductibleOperable) {
                ((ReductibleOperable)curr_).tryCalculateNode(_context);
            }
            a_ = curr_.getArgument();
            if (a_ == null) {
                return;
            }
            ind_ = ExecOperationNode.getNextIndex(curr_, a_.getStruct());
        }
    }
    private static CustList<ExecOperationNode> getExecutableNodes(Analyzable _an,CustList<OperationNode> _list) {
        Block bl_ = _an.getCurrentBlock();
        CustList<ExecOperationNode> out_ = new CustList<ExecOperationNode>();
        OperationNode root_ = _list.last();
        OperationNode current_ = root_;
        ExecOperationNode exp_ = (ExecOperationNode) ExecOperationNode.createExecOperationNode(current_);
        _an.getContextEl().getCoverage().putBlockOperation(_an,bl_,current_,exp_);
        while (current_ != null) {
            OperationNode op_ = current_.getFirstChild();
            if (op_ != null) {
                ExecOperationNode loc_ = (ExecOperationNode) ExecOperationNode.createExecOperationNode(op_);
                _an.getContextEl().getCoverage().putBlockOperation(_an,bl_,op_,loc_);
                ((ExecMethodOperation)exp_).appendChild(loc_);
                exp_ = loc_;
                current_ = op_;
                continue;
            }
            while (true) {
                if (exp_ instanceof ExecAffectationOperation) {
                    ((ExecAffectationOperation)exp_).setup();
                }
                if (exp_ instanceof ExecSemiAffectationOperation) {
                    ((ExecSemiAffectationOperation)exp_).setup();
                }
                if (exp_ instanceof ExecCompoundAffectationOperation) {
                    ((ExecCompoundAffectationOperation)exp_).setup();
                }
                out_.add(exp_);
                op_ = current_.getNextSibling();
                if (op_ != null) {
                    ExecOperationNode loc_ = (ExecOperationNode) ExecOperationNode.createExecOperationNode(op_);
                    _an.getContextEl().getCoverage().putBlockOperation(_an,bl_,op_,loc_);
                    ExecMethodOperation par_ = exp_.getParent();
                    par_.appendChild(loc_);
                    if (op_.getParent() instanceof DotOperation && loc_ instanceof ExecPossibleIntermediateDotted) {
                        exp_.setSiblingSet((ExecPossibleIntermediateDotted) loc_);
                    }
                    exp_ = loc_;
                    current_ = op_;
                    break;
                }
                op_ = current_.getParent();
                if (op_ == null) {
                    current_ = null;
                    break;
                }
                ExecMethodOperation par_ = exp_.getParent();
                if (op_ == root_) {
                    if (par_ instanceof ExecAffectationOperation) {
                        ((ExecAffectationOperation)par_).setup();
                    }
                    if (par_ instanceof ExecSemiAffectationOperation) {
                        ((ExecSemiAffectationOperation)par_).setup();
                    }
                    if (par_ instanceof ExecCompoundAffectationOperation) {
                        ((ExecCompoundAffectationOperation)par_).setup();
                    }
                    out_.add(par_);
                    current_ = null;
                    break;
                }
                current_ = op_;
                exp_ = par_;
            }
        }
        return out_;
    }
    public static CustList<ExecOperationNode> getReducedNodes(ExecOperationNode _root) {
        CustList<ExecOperationNode> out_ = new CustList<ExecOperationNode>();
        ExecOperationNode current_ = _root;
        while (current_ != null) {
            ExecOperationNode op_ = current_.getFirstChild();
            if (op_ != null) {
                if (current_.getArgument() == null) {
                    current_ = op_;
                    continue;
                }
            }
            while (true) {
                current_.setOrder(out_.size());
                out_.add(current_);
                op_ = current_.getNextSibling();
                if (op_ != null) {
                    current_ = op_;
                    break;
                }
                op_ = current_.getParent();
                if (op_ == _root) {
                    op_.setOrder(out_.size());
                    out_.add(op_);
                    current_ = null;
                    break;
                }
                if (op_ == null) {
                    current_ = null;
                    break;
                }
                current_ = op_;
            }
        }
        return out_;
    }
}
