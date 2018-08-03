package code.expressionlanguage;

import code.expressionlanguage.common.GeneMethod;
import code.expressionlanguage.common.GeneType;
import code.expressionlanguage.methods.AnnotationBlock;
import code.expressionlanguage.methods.AnnotationMethodBlock;
import code.expressionlanguage.methods.Classes;
import code.expressionlanguage.opers.ExpressionLanguage;
import code.expressionlanguage.opers.OperationNode;
import code.expressionlanguage.opers.util.MethodMetaInfo;
import code.util.CustList;
import code.util.StringList;

public class ReflectGetDefaultValuePageEl extends AbstractReflectPageEl {

    private boolean init;
    private CustList<OperationNode> ops;

    @Override
    public boolean checkCondition(ContextEl _context) {
        MethodMetaInfo instance_ = (MethodMetaInfo) getGlobalArgument().getStruct();
        String cl_ = instance_.getFormClassName();
        String id_ = Templates.getIdFromAllTypes(cl_);
        GeneType type_ = _context.getClassBody(id_);
        if (!(type_ instanceof AnnotationBlock)) {
            setReturnedArgument(new Argument());
            return true;
        }
        if (!init) {
            String name_ = instance_.getName();
            AnnotationBlock ann_ = (AnnotationBlock) type_;
            for (GeneMethod m: Classes.getMethodBlocks(ann_)) {
                if (!(m instanceof AnnotationMethodBlock)) {
                    continue;
                }
                AnnotationMethodBlock a_ = (AnnotationMethodBlock) m;
                if (!StringList.quickEq(a_.getName(), name_)) {
                    continue;
                }
                ops = a_.getOpValue();
            }
            init = true;
        }
        ExpressionLanguage el_;
        if (!isEmptyEl()) {
            el_ = getLastEl();
        } else {
            el_ = new ExpressionLanguage(ops);
            addCurrentEl(el_);
        }
        Argument ret_ = el_.calculateMember(_context);
        if (_context.callsOrException()) {
            return false;
        }
        clearCurrentEls();
        setReturnedArgument(ret_);
        return true;
    }

}
