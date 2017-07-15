package code.expressionlanguage.methods;
import org.w3c.dom.Element;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.PageEl;
import code.expressionlanguage.PrimitiveTypeUtil;
import code.expressionlanguage.methods.exceptions.AlreadyDefinedVarException;
import code.expressionlanguage.variables.LocalVariable;
import code.util.NatTreeMap;
import code.util.StringMap;

public final class DeclareVariable extends Leaf implements InitVariable {

    private final String variableName;

    private final String className;

    DeclareVariable(Element _el, ContextEl _importingPage, int _indexChild,
            BracedBlock _m) {
        super(_el, _importingPage, _indexChild, _m);
        variableName = _el.getAttribute(ATTRIBUTE_VAR);
        className = _el.getAttribute(ATTRIBUTE_CLASS);
    }

    @Override
    public NatTreeMap<String,String> getClassNames() {
        NatTreeMap<String,String> tr_ = new NatTreeMap<String,String>();
        tr_.put(ATTRIBUTE_CLASS, className);
        return tr_;
    }

    @Override
    public String getVariableName() {
        return variableName;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public void buildExpressionLanguage(ContextEl _cont) {
        PageEl page_ = _cont.getLastPage();
//        page_.setProcessingNode(getAssociateElement());
        page_.setProcessingAttribute(ATTRIBUTE_CLASS);
//        page_.setLookForAttrValue(true);
        page_.setOffset(0);
//        try {
//            ConstClasses.classForNameNotInit(className);
//        } catch (RuntimeClassNotFoundException _0) {
//            throw new RuntimeClassNotFoundException(_cont.joinPages());
//        }
//        ConstClasses.classForNameNotInit(className);
        if (_cont.getLastPage().getLocalVars().contains(variableName)) {
            page_.setProcessingAttribute(ATTRIBUTE_VAR);
//            page_.setLookForAttrValue(true);
            page_.setOffset(0);
            throw new AlreadyDefinedVarException(variableName+RETURN_LINE+_cont.joinPages());
        }
        LocalVariable lv_ = new LocalVariable();
        lv_.setClassName(className);
        _cont.getLastPage().getLocalVars().put(variableName, lv_);
//        removeLocalVariablesFromParent();
    }

    @Override
    boolean canBeLastOfBlockGroup() {
        return false;
    }

    @Override
    public void checkCallConstructor(ContextEl _cont) {
    }

    @Override
    public String getTagName() {
        return TAG_DECLARE;
    }

    @Override
    public void processEl(ContextEl _cont) {
        PageEl ip_ = _cont.getLastPage();
        LocalVariable lv_ = new LocalVariable();
        String className_ = getClassName();
        lv_.setClassName(className_);
        lv_.setElement(PrimitiveTypeUtil.defaultValue(className_));
        String name_ = getVariableName();
        StringMap<LocalVariable> map_ = ip_.getLocalVars();
        map_.put(name_, lv_);
        processBlock(_cont);
    }
}
