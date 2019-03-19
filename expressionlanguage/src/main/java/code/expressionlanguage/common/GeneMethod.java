package code.expressionlanguage.common;

import code.expressionlanguage.opers.util.MethodId;


public interface GeneMethod extends GeneFunction {

    @Override
    MethodId getId();

    boolean isStaticMethod();

    boolean isFinalMethod();

    boolean isAbstractMethod();

}
