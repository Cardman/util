package code.expressionlanguage.methods.util;
import code.expressionlanguage.Argument;
import code.expressionlanguage.methods.ConstructorBlock;
import code.expressionlanguage.opers.util.FctConstraints;
import code.util.StringList;

public final class CallConstructor {

    private InstancingStep instancingStep = InstancingStep.NOTHING;

    private FctConstraints id;

    private Argument argument;

    private StringList calledConstructors = new StringList();

    private boolean calledImplicitConstructor;

    private ConstructorBlock usedConstructor;
    
    private boolean initializedFields;

    private boolean firstField;

    public InstancingStep getInstancingStep() {
        return instancingStep;
    }

    public void setInstancingStep(InstancingStep _instancingStep) {
        instancingStep = _instancingStep;
    }

    public FctConstraints getId() {
        return id;
    }

    public void setId(FctConstraints _id) {
        id = _id;
    }

    public Argument getArgument() {
        return argument;
    }

    public void setArgument(Argument _argument) {
        argument = _argument;
    }

    public StringList getCalledConstructors() {
        return calledConstructors;
    }

    public void setCalledConstructors(StringList _calledConstructors) {
        calledConstructors = _calledConstructors;
    }

    public boolean isCalledImplicitConstructor() {
        return calledImplicitConstructor;
    }

    public void setCalledImplicitConstructor(boolean _calledImplicitConstructor) {
        calledImplicitConstructor = _calledImplicitConstructor;
    }

    public ConstructorBlock getUsedConstructor() {
        return usedConstructor;
    }

    public void setUsedConstructor(ConstructorBlock _usedConstructor) {
        usedConstructor = _usedConstructor;
    }

    public boolean isInitializedFields() {
        return initializedFields;
    }

    public void setInitializedFields(boolean _initializedFields) {
        initializedFields = _initializedFields;
    }

    public boolean isFirstField() {
        return firstField;
    }

    public void setFirstField(boolean _firstField) {
        firstField = _firstField;
    }

}
