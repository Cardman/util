package code.formathtml.exec.blocks;

import code.formathtml.exec.opers.RendDynOperationNode;
import code.util.CustList;

public final class RendSwitchBlock extends RendAbsSwitchBlock {

    public RendSwitchBlock(String _label, int _valueOffset, CustList<RendDynOperationNode> _opValue, String _instanceTest) {
        super(_label, _valueOffset, _opValue, _instanceTest);
    }

}
