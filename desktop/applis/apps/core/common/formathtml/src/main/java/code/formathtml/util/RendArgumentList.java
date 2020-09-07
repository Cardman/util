package code.formathtml.util;

import code.expressionlanguage.Argument;
import code.formathtml.exec.RendDynOperationNode;
import code.util.CustList;

public final class RendArgumentList {
    private final CustList<Argument> arguments = new CustList<Argument>();
    private final CustList<RendDynOperationNode> filter = new CustList<RendDynOperationNode>();

    public CustList<Argument> getArguments() {
        return arguments;
    }

    public CustList<RendDynOperationNode> getFilter() {
        return filter;
    }

}
