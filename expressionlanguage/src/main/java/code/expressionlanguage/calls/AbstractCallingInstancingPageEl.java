package code.expressionlanguage.calls;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.calls.util.CustomFoundConstructor;
import code.expressionlanguage.calls.util.InstancingStep;
import code.expressionlanguage.calls.util.NotInitializedFields;
import code.expressionlanguage.calls.util.ReadWrite;
import code.expressionlanguage.inherits.Templates;
import code.expressionlanguage.methods.*;
import code.expressionlanguage.opers.util.ConstructorId;
import code.util.CustList;
import code.util.StringList;


public abstract class AbstractCallingInstancingPageEl extends AbstractPageEl implements ForwardPageEl {

    private boolean calledImplicitConstructor;

    private boolean firstField;

    public boolean isFirstField() {
        return firstField;
    }

    public void setFirstField(boolean _firstField) {
        firstField = _firstField;
    }

    @Override
    public void tryProcessEl(ContextEl _context) {
        //constructor walk through (class, enum, interface)
        ReadWrite rw_ = getReadWrite();
        Block en_ = rw_.getBlock();
        if (en_ instanceof WithEl) {
            ((WithEl)en_).processEl(_context);
            return;
        }
        setNullReadWrite();
    }
    @Override
    public final boolean checkCondition(ContextEl _context) {
        Classes classes_ = _context.getClasses();
        boolean implicitConstr_ = false;
        ConstructorBlock ctor_ = (ConstructorBlock) getBlockRoot();
        if (ctor_ == null) {
            //No constructor found in the current class => call the super one
            implicitConstr_ = true;
        } else if (ctor_.implicitConstr()) {
            //Constructor found in the current class but no explicit call to super in the code => call the super one
            implicitConstr_ = true;
        }
        if (implicitConstr_) {
            String curClass_ = getGlobalClass();
            String curClassBase_ = Templates.getIdFromAllTypes(curClass_);
            RootBlock class_ = classes_.getClassBody(curClassBase_);
            if (class_ instanceof UniqueRootedBlock) {
                //class or enum (included inner enum)
                UniqueRootedBlock root_ = (UniqueRootedBlock) class_;
                String id_ = root_.getImportedDirectGenericSuperClass();
                String superClassBase_ = Templates.getIdFromAllTypes(id_);
                RootBlock superClass_ = classes_.getClassBody(superClassBase_);
                if (!calledImplicitConstructor && superClass_ != null) {
                    calledImplicitConstructor = true;
                    ConstructorId super_ = new ConstructorId(superClassBase_, new StringList(), false);
                    Argument global_ = getGlobalArgument();
                    _context.setCallingState(new CustomFoundConstructor(Templates.quickFormat(curClass_,id_,_context), superClass_,EMPTY_STRING, -1, super_, global_, new CustList<Argument>(), InstancingStep.USING_SUPER_IMPL));
                    return false;
                }
                //the super constructor is called here
            }
            boolean initFields_ = false;
            Block bl_ = null;
            if (ctor_ != null) {
                bl_ = ctor_.getFirstChild();
            }
            if (!(bl_ instanceof Line)) {
                initFields_ = true;
            } else {
                Line l_ = (Line) bl_;
                if (!l_.isCallInts()) {
                    initFields_ = true;
                }
            }
            //initialize fields if there is no interface constructors to call
            if (!firstField && initFields_) {
                firstField = true;
                Argument global_ = getGlobalArgument();
                _context.setCallingState(new NotInitializedFields(curClass_, global_));
                return false;
            }
            //fields of the current class are initialized if there is no interface constructors to call
        }
        return true;
    }

    @Override
    public final void forwardTo(AbstractPageEl _page, ContextEl _context) {
        Argument a_ = getGlobalArgument();
        _page.receive(a_, _context);
    }
}
