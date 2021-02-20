package code.maths.litteral;
import code.util.CustList;
import code.util.StringMap;
import code.util.core.IndexConstants;

final class MathUtil {

    private MathUtil() {
    }

    static Argument processEl(String _el, boolean _onlycheckSyntax, StringMap<String> _conf) {
        ErrorStatus err_ = new ErrorStatus();
        Delimiters d_ = MathResolver.checkSyntax(_el, err_);
        if (err_.isError()) {
            Argument arg_ = new Argument();
            arg_.setArgClass(MathType.NOTHING);
            arg_.setObject(err_);
            return arg_;
        }
        OperationsSequence opTwo_ = MathResolver.getOperationsSequence(IndexConstants.FIRST_INDEX, _el, d_);
        OperationNode op_ = OperationNode.createOperationNode(IndexConstants.FIRST_INDEX, IndexConstants.FIRST_INDEX, null, opTwo_);
        if (op_ == null) {
            Argument arg_ = new Argument();
            arg_.setArgClass(MathType.NOTHING);
            arg_.setObject(err_);
            return arg_;
        }
        CustList<OperationNode> all_ = getSortedDescNodes(op_,_conf,err_);
        if (err_.isError()) {
            Argument arg_ = new Argument();
            arg_.setArgClass(MathType.NOTHING);
            arg_.setObject(err_);
            op_.setArgument(arg_);
        }
        if (!_onlycheckSyntax) {
            calculate(all_, _conf, err_);
            if (err_.isError()) {
                Argument arg_ = new Argument();
                arg_.setArgClass(MathType.NOTHING);
                arg_.setObject(err_);
                op_.setArgument(arg_);
            }
            return op_.getArgument();
        }
        Argument a_ = new Argument();
        a_.setArgClass(op_.getResultClass());
        return a_;
    }

    public static CustList<OperationNode> getSortedDescNodes(OperationNode _root, StringMap<String> _context, ErrorStatus _error) {
        CustList<OperationNode> list_ = new CustList<OperationNode>();
        OperationNode c_ = _root;
        while (c_ != null) {
            c_ = getAnalyzedNext(c_, _root, list_, _context, _error);
        }
        return list_;
    }

    public static OperationNode getAnalyzedNext(OperationNode _current, OperationNode _root, CustList<OperationNode> _sortedNodes,StringMap<String> _context, ErrorStatus _error) {
        OperationNode next_ = createFirstChild(_current, _error);
        if (_error.isError()) {
            return null;
        }
        if (next_ != null) {
            ((MethodOperation) _current).appendChild(next_);
            return next_;
        }
        OperationNode current_ = _current;
        while (true) {
            current_.analyze(_context, _error);
            if (_error.isError()) {
                return null;
            }
            current_.setOrder(_sortedNodes.size());
            tryReduce(_context, _error, current_);
            _sortedNodes.add(current_);
            next_ = createNextSibling(current_, _error);
            if (_error.isError()) {
                return null;
            }
            if (next_ != null) {
                next_.getParent().appendChild(next_);
                return next_;
            }
            OperationNode par_ = current_.getParent();
            if (par_ == _root) {
                return processRoot(_sortedNodes, _context, _error, par_);
            }
            if (par_ == null) {
                return null;
            }
            current_ = par_;
        }
    }

    private static OperationNode processRoot(CustList<OperationNode> _sortedNodes, StringMap<String> _context, ErrorStatus _error, OperationNode _par) {
        _par.analyze(_context, _error);
        if (!_error.isError()) {
            _par.setOrder(_sortedNodes.size());
            tryReduce(_context, _error, _par);
            _sortedNodes.add(_par);
        }
        return null;
    }

    private static void tryReduce(StringMap<String> _context, ErrorStatus _error, OperationNode _current) {
        if (_current instanceof ReductibleOperable) {
            ((ReductibleOperable) _current).tryCalculateNode(_context, _error);
        }
    }

    private static OperationNode createFirstChild(OperationNode _block, ErrorStatus _error) {
        if (!(_block instanceof MethodOperation)) {
            return null;
        }
        MethodOperation block_ = (MethodOperation) _block;
        if (block_.getChildren().isEmpty()) {
            return null;
        }
        String value_ = block_.getChildren().getValue(0);
        Delimiters d_ = block_.getOperations().getDelimiter();
        int curKey_ = block_.getChildren().getKey(0);
        int offset_ = block_.getIndexInEl()+curKey_;
        OperationsSequence r_ = MathResolver.getOperationsSequence(offset_, value_, d_);
        OperationNode op_ = OperationNode.createOperationNode(offset_, IndexConstants.FIRST_INDEX, block_, r_);
        if (op_ == null) {
            _error.setIndex(offset_);
            _error.setError(true);
            return null;
        }
        return op_;
    }

    private static OperationNode createNextSibling(OperationNode _block, ErrorStatus _error) {
        MethodOperation p_ = _block.getParent();
        if (p_ == null) {
            return null;
        }
        StrTypes children_ = p_.getChildren();
        if (_block.getIndexChild() + 1 >= children_.size()) {
            return null;
        }
        String value_ = children_.getValue(_block.getIndexChild() + 1);
        Delimiters d_ = _block.getOperations().getDelimiter();
        int curKey_ = children_.getKey(_block.getIndexChild() + 1);
        int offset_ = p_.getIndexInEl()+curKey_;
        OperationsSequence r_ = MathResolver.getOperationsSequence(offset_, value_, d_);
        OperationNode op_ = OperationNode.createOperationNode(offset_, _block.getIndexChild() + 1, p_, r_);
        if (op_ == null) {
            _error.setIndex(offset_);
            _error.setError(true);
            return null;
        }
        return op_;
    }
    public static CustList<OperationNode> getDirectChildren(MethodOperation _element) {
        CustList<OperationNode> list_ = new CustList<OperationNode>();
        OperationNode elt_ = _element.getFirstChild();
        while (elt_ != null) {
            list_.add(elt_);
            elt_ = elt_.getNextSibling();
        }
        return list_;
    }
    static void calculate(CustList<OperationNode> _nodes, StringMap<String> _context, ErrorStatus _error) {
        int fr_ = 0;
        int len_ = _nodes.size();
        while (fr_ < len_) {
            OperationNode o = _nodes.get(fr_);
            if (o.getArgument() != null) {
                fr_++;
                continue;
            }
            o.calculate(_context, _error);
            if (_error.isError()) {
                return;
            }
            Argument res_ = o.getArgument();
            boolean st_ = res_.isBoolVal();
            fr_ = OperationNode.getNextIndex(o, st_);
        }
    }
}
