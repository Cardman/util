package code.formathtml.exec;

import code.expressionlanguage.analyze.opers.AbstractUnaryOperation;

public abstract class RendAbstractUnaryOperation extends RendMethodOperation implements RendCalculableOperation {

    public RendAbstractUnaryOperation(AbstractUnaryOperation _a) {
        super(_a);
    }

}
