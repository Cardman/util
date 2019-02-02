package code.expressionlanguage.methods;
import static code.expressionlanguage.EquallableElUtil.assertEq;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.InitializationLgNames;
import code.expressionlanguage.inherits.TypeUtil;
import code.expressionlanguage.methods.util.TypeVar;
import code.expressionlanguage.opers.util.ClassField;
import code.expressionlanguage.opers.util.ClassMethodId;
import code.expressionlanguage.opers.util.MethodId;
import code.expressionlanguage.options.Options;
import code.expressionlanguage.structs.NumberStruct;
import code.expressionlanguage.structs.Struct;
import code.expressionlanguage.variables.VariableSuffix;
import code.util.CustList;
import code.util.EntryCust;
import code.util.EqList;
import code.util.ObjectMap;
import code.util.StringList;
import code.util.StringMap;


public final class ClassesTest {
    @Test
    public void emptyClassesTest() {
        StringMap<String> files_ = new StringMap<String>();
        Options opt_ = new Options();
        ContextEl cont_ = InitializationLgNames.buildStdOne(opt_);
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().isEmptyErrors());
        assertNotNull(cont_.getMemoryError());
    }

    @Test
    public void resolve1Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T> :Ex<#T>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.ExTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkg.Ex", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }

    @Test
    public void resolve2Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.Ex;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :Ex<#T>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.ExTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkgtwo.Ex", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }

    @Test
    public void resolve3Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.*;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :Ex<#T>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.ExTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkgtwo.Ex", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }

    @Test
    public void resolve4Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("$public $class pkgtwo.ExThree<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.ExThree;\n");
        xml_.append("pkgtwo.Ex;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :Ex<#T>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.ExTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkgtwo.Ex", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }

    @Test
    public void resolve5Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.Ex;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :Ex<#T>{}\n");
        xml_.append("$public $class pkg.Ex<#T> {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.ExTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkgtwo.Ex", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }

    @Test
    public void resolve6Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.*;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :Ex<#T>{}\n");
        xml_.append("$public $class pkg.Ex<#T> {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.ExTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkg.Ex", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }

    @Test
    public void resolve7Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.*;\n");
        xml_.append("pkgthree.*;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :Ex<#T>{}\n");
        xml_.append("$public $class pkgthree.ExFour<#T> {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.ExTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkgtwo.Ex", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }
    @Test
    public void resolve8Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.ExThree<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T:ExThree<#T>> :Ex<#T>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        CustList<TypeVar> types_ = context_.getClassBody("pkg.ExTwo").getParamTypesMapValues();
        assertEq(1, types_.size());
        assertEq("T", types_.first().getName());
        assertEq(1, types_.first().getConstraints().size());
        assertEq("pkg.ExThree<#T>", types_.first().getConstraints().first());
    }
    @Test
    public void resolve9Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("$public $class pkgtwo.ExThree<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.ExThree;\n");
        xml_.append("$public $class pkg.ExTwo<#T:ExThree<#T>> :Ex<#T>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        CustList<TypeVar> types_ = context_.getClassBody("pkg.ExTwo").getParamTypesMapValues();
        assertEq(1, types_.size());
        assertEq("T", types_.first().getName());
        assertEq(1, types_.first().getConstraints().size());
        assertEq("pkgtwo.ExThree<#T>", types_.first().getConstraints().first());
    }
    @Test
    public void resolve10Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("$public $class pkgtwo.ExThree<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.*;\n");
        xml_.append("$public $class pkg.ExTwo<#T:ExThree<#T>> :Ex<#T>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        CustList<TypeVar> types_ = context_.getClassBody("pkg.ExTwo").getParamTypesMapValues();
        assertEq(1, types_.size());
        assertEq("T", types_.first().getName());
        assertEq(1, types_.first().getConstraints().size());
        assertEq("pkgtwo.ExThree<#T>", types_.first().getConstraints().first());
    }
    @Test
    public void resolve11Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Outer {\n");
        xml_.append(" $public $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerTwo:pkg.Outer..Inner {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        unfullValidateInheritingClasses(files_);
    }
    @Test
    public void resolve12Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Outer {\n");
        xml_.append(" $public $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerTwo:Outer..Inner {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        unfullValidateInheritingClasses(files_);
    }
    @Test
    public void resolve13Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Outer {\n");
        xml_.append(" $public $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerTwo:..Inner {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        unfullValidateInheritingClasses(files_);
    }
    @Test
    public void resolve14Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.OuterTwo;\n");
        xml_.append("$public $class pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerTwo:OuterTwo..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.OuterTwo {\n");
        xml_.append(" $public $class InnerThree {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerFour:..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        unfullValidateInheritingClasses(files_);
    }
    @Test
    public void resolve15Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.*;\n");
        xml_.append("$public $class pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerTwo:OuterTwo..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.OuterTwo {\n");
        xml_.append(" $public $class InnerThree {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerFour:..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        unfullValidateInheritingClasses(files_);
    }

    @Test
    public void resolve16Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.Ex;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :Ex<pkg.ExThree<?#T>>{}\n");
        xml_.append("$public $class pkg.ExThree<#U>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.ExTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkgtwo.Ex", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }
    @Test
    public void resolve17Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.Ex;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :Ex<pkg.ExThree<?>>{}\n");
        xml_.append("$public $class pkg.ExThree<#U>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.ExTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkgtwo.Ex", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }
    @Test
    public void resolve18Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.Ex;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :Ex<pkg.ExThree<!#T>>{}\n");
        xml_.append("$public $class pkg.ExThree<#U>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.ExTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkgtwo.Ex", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }
    @Test
    public void resolve19Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.Ex;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :Ex<pkg.ExThree<?#T[]>>{}\n");
        xml_.append("$public $class pkg.ExThree<#U>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.ExTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkgtwo.Ex", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }
    @Test
    public void resolve20Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.Ex;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :Ex<pkg.ExThree<!#T[]>>{}\n");
        xml_.append("$public $class pkg.ExThree<#U>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.ExTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkgtwo.Ex", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }
    @Test
    public void resolve21Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerTwo:OuterTwo..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.OuterTwo {\n");
        xml_.append(" $protected $class InnerThree {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerFour:..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.Outer..InnerTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkg.OuterTwo..InnerThree", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }
    @Test
    public void resolve22Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $static $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $class InnerTwo:OuterTwo..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.OuterTwo {\n");
        xml_.append(" $protected $static $class InnerThree {\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $class InnerFour:..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.Outer..InnerTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkg.OuterTwo..InnerThree", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }
    @Test
    public void resolve23Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class [pkgtwo.OuterTwo;] pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $static $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $class InnerTwo:OuterTwo..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.OuterTwo {\n");
        xml_.append(" $protected $static $class InnerThree {\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $class InnerFour:..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.Outer..InnerTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkgtwo.OuterTwo..InnerThree", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }
    @Test
    public void resolve24Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class [pkgtwo.OuterTwo;] pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerTwo:OuterTwo..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.OuterTwo {\n");
        xml_.append(" $protected $class InnerThree {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerFour:..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.Outer..InnerTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkgtwo.OuterTwo..InnerThree", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }
    @Test
    public void resolve25Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class [pkgtwo.OuterTwo;] pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $static $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $class InnerTwo:OuterTwo..InnerThree<java.lang.Number> {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.OuterTwo {\n");
        xml_.append(" $protected $static $class InnerThree<#T> {\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $class InnerFour:..InnerThree<java.lang.Number> {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.Outer..InnerTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkgtwo.OuterTwo..InnerThree", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }
    @Test
    public void resolve26Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class [pkgtwo.OuterTwo;] pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerTwo:OuterTwo..InnerThree<java.lang.Number> {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.OuterTwo {\n");
        xml_.append(" $protected $class InnerThree<#T> {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerFour:..InnerThree<java.lang.Number> {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.Outer..InnerTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkgtwo.OuterTwo..InnerThree", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }
    @Test
    public void resolve27Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class [pkgtwo.OuterTwo..InnerThree;pkgtwo.OuterTwo;] pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $static $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $class InnerTwo:..InnerThree<java.lang.Number> {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.OuterTwo {\n");
        xml_.append(" $protected $static $class InnerThree<#T> {\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $class InnerFour:..InnerThree<java.lang.Number> {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.Outer..InnerTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkgtwo.OuterTwo..InnerThree", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }
    @Test
    public void resolve28Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class [pkgtwo.OuterTwo..InnerThree;pkgtwo.OuterTwo;] pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerTwo:..InnerThree<java.lang.Number> {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.OuterTwo {\n");
        xml_.append(" $protected $class InnerThree<#T> {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerFour:..InnerThree<java.lang.Number> {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.Outer..InnerTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkgtwo.OuterTwo..InnerThree", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }
    @Test
    public void resolve29Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class [pkgtwo.OuterTwo..InnerThree;pkgtwo.OuterTwo;] pkg.Outer {\n");
        xml_.append(" $public $static $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $class InnerTwo:..InnerThree<java.lang.Number> {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.OuterTwo {\n");
        xml_.append(" $protected $static $class InnerThree<#T> {\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $class InnerFour:..InnerThree<java.lang.Number> {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateInheritingClasses(files_);
        StringList types_ = context_.getClassBody("pkg.Outer..InnerTwo").getAllSuperClasses();
        assertEq(2, types_.size());
        assertEq("pkgtwo.OuterTwo..InnerThree", types_.first());
        assertEq(context_.getStandards().getAliasObject(), types_.last());
    }
    @Test
    public void resolve1FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("$public $class pkgtwo.ExThree<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.*;\n");
        xml_.append("$public $class pkg.ExTwo<#T:ExFour<#T>> :Ex<#T>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    @Test
    public void resolve2FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("$public $class pkgtwo.ExThree<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.*;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :ExFour<#T>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    @Test
    public void resolve3FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("$public $class pkgtwo.ExThree<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.*;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    @Test
    public void resolve4FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("$public $class pkgtwo.ExThree<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.*;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :Ex<#S>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    @Test
    public void resolve5FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E:pkg.Ex<#E>> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("$public $class pkgtwo.ExThree<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.*;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :Ex<#T>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    @Test
    public void resolve6FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E:pkg.Ex<#E>> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("$public $class pkgtwo.ExThree<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.*;\n");
        xml_.append("$public $class pkg.ExTwo<#S,#T:pkg.Ex<#S>>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    @Test
    public void resolve7FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Outer:pkg.Outer..Inner {\n");
        xml_.append(" $public $class Inner {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    @Test
    public void resolve8FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.Ex;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :Ex<?#T>{}\n");
        xml_.append("$public $class pkg.ExThree<#U>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    @Test
    public void resolve9FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.Ex;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :Ex<?>{}\n");
        xml_.append("$public $class pkg.ExThree<#U>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    @Test
    public void resolve10FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.Ex;\n");
        xml_.append("$public $class pkg.ExTwo<#T> :Ex<!#T>{}\n");
        xml_.append("$public $class pkg.ExThree<#U>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    @Test
    public void resolve11FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.OuterTwo;\n");
        xml_.append("$public $class pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerTwo:OuterTwo..InnerThree..InnerFive {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.OuterTwo {\n");
        xml_.append(" $protected $static $class InnerThree {\n");
        xml_.append("  $protected $class InnerFive {\n");
        xml_.append("  }\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerFour:..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    @Test
    public void resolve12FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.OuterTwo;\n");
        xml_.append("$public $class pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerTwo:OuterTwo..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.OuterTwo {\n");
        xml_.append(" $public $class InnerThree {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerFour:..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    @Test
    public void resolve13FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.OuterTwo;\n");
        xml_.append("$public $class pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerTwo:pkgtwo.OuterTwo..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.OuterTwo {\n");
        xml_.append(" $protected $class InnerThree {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerFour:..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    @Test
    public void resolve14FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.OuterTwo;\n");
        xml_.append("$public $class pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerTwo:pkgtwo.OuterTwo..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.OuterTwo {\n");
        xml_.append(" $public $class InnerThree {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerFour:..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    @Test
    public void resolve15FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.OuterTwo;\n");
        xml_.append("$public $class pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $static $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $class InnerTwo:OuterTwo..InnerThree..InnerFive {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.OuterTwo {\n");
        xml_.append(" $protected $static $class InnerThree {\n");
        xml_.append("  $protected $static $class InnerFive {\n");
        xml_.append("  }\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $class InnerFour:..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    @Test
    public void resolve16FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.OuterTwo;\n");
        xml_.append("$public $class pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $static $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $class InnerTwo:OuterTwo..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.OuterTwo {\n");
        xml_.append(" $public $class InnerThree {\n");
        xml_.append(" }\n");
        xml_.append(" $public $class InnerFour:..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    @Test
    public void resolve17FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.OuterTwo;\n");
        xml_.append("$public $class pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $static $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $class InnerTwo:pkgtwo.OuterTwo..InnerThree..InnerFive {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.OuterTwo {\n");
        xml_.append(" $protected $static $class InnerThree {\n");
        xml_.append("  $protected $static $class InnerFive {\n");
        xml_.append("  }\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $class InnerFour:..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    @Test
    public void resolve18FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("pkgtwo.OuterTwo;\n");
        xml_.append("$public $class pkg.Outer: OuterTwo {\n");
        xml_.append(" $public $static $class Inner {\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $class InnerTwo:pkgtwo.OuterTwo..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$protected $class pkgtwo.OuterTwo {\n");
        xml_.append(" $public $static $class InnerThree {\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $class InnerFour:..InnerThree {\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        failValidateInheritingClasses(files_);
    }
    

    @Test
    public void getAllOverridingMethods1Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex :pkg.ExTwo:pkg.ExThree{\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExTwo :pkg.ExFour{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExThree :pkg.ExFour{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExFour {\n");
        xml_.append(" $public $abstract $int absgetter():\n");
        xml_.append("}\n");
        files_.put("pkg/ExFour", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExFive :pkg.ExTwo:pkg.ExThree{}\n");
        files_.put("pkg/ExFive", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        InterfaceBlock i_ = (InterfaceBlock) context_.getClassBody("pkg.ExFour");
        ObjectMap<MethodId, StringList> sgn_ = toList(TypeUtil.getAllInstanceSignatures(i_, context_));
        sgn_ = toList(RootBlock.getAllOverridingMethods(toId(sgn_), context_));
        assertEq(1, sgn_.size());
        assertEq(new StringList("pkg.ExFour"),sgn_.getVal(new MethodId(false, "absgetter", new StringList())));
    }

    @Test
    public void getAllOverridingMethods2Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex :pkg.ExTwo:pkg.ExThree{\n");
        xml_.append(" $public $abstract $int absgetter():\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExTwo :pkg.ExFour{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExThree :pkg.ExFour{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExFour {\n");
        xml_.append(" $public $abstract $int absgetter():\n");
        xml_.append("}\n");
        files_.put("pkg/ExFour", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExFive :pkg.ExTwo:pkg.ExThree{}\n");
        files_.put("pkg/ExFive", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        InterfaceBlock i_ = (InterfaceBlock) context_.getClassBody("pkg.Ex");
        ObjectMap<MethodId, StringList> sgn_ = toList(TypeUtil.getAllInstanceSignatures(i_, context_));
        assertEq(1, sgn_.size());
        assertEq(new StringList("pkg.Ex","pkg.ExFour"),sgn_.getVal(new MethodId(false, "absgetter", new StringList())));
        sgn_ = toList(RootBlock.getAllOverridingMethods(toId(sgn_), context_));
        assertEq(1, sgn_.size());
        assertEq(new StringList("pkg.Ex"),sgn_.getVal(new MethodId(false, "absgetter", new StringList())));
    }

    @Test
    public void getAllOverridingMethods3Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex :pkg.ExTwo:pkg.ExThree{\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExTwo :pkg.ExFour{\n");
        xml_.append(" $public $abstract $int absgetter():\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExThree :pkg.ExFour{\n");
        xml_.append(" $public $abstract $int absgetter():\n");
        xml_.append("}\n");
        files_.put("pkg/ExThree", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExFour {\n");
        xml_.append("}\n");
        files_.put("pkg/ExFour", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExFive :pkg.ExTwo:pkg.ExThree{}\n");
        files_.put("pkg/ExFive", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        InterfaceBlock i_ = (InterfaceBlock) context_.getClassBody("pkg.Ex");
        ObjectMap<MethodId, StringList> sgn_ = toList(TypeUtil.getAllInstanceSignatures(i_, context_));
        assertEq(1, sgn_.size());
        assertEq(new StringList("pkg.ExTwo","pkg.ExThree"),sgn_.getVal(new MethodId(false,"absgetter", new StringList())));
        sgn_ = toList(RootBlock.getAllOverridingMethods(toId(sgn_), context_));
        assertEq(1, sgn_.size());
        assertEq(new StringList("pkg.ExTwo","pkg.ExThree"),sgn_.getVal(new MethodId(false,"absgetter", new StringList())));
    }
    @Test
    public void calculateStaticField1Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $static $final $int myf=2i:\n");
        xml_.append(" $public $static $final $int mys=myf;;;+3i:\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl ctx_ = validateStaticFields(files_);
        assertEq(2, ctx_.getClasses().staticFieldCount());
        Struct str_ = ctx_.getClasses().getStaticField(new ClassField("pkg.ExTwo", "myf"));
        assertEq(2, (((NumberStruct)str_).getInstance()).intValue());
        str_ = ctx_.getClasses().getStaticField(new ClassField("pkg.ExTwo", "mys"));
        assertEq(5, (((NumberStruct)str_).getInstance()).intValue());
    }
    @Test
    public void calculateStaticField2Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $static $final $int myf=$static(pkg.ExThree).myf;;;:\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree {\n");
        xml_.append(" $public $static $final $int myf=$static(pkg.ExTwo).myf;;;:\n");
        xml_.append("}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl ctx_ = validateStaticFields(files_);
        assertEq(0, ctx_.getClasses().staticFieldCount());
    }
    @Test
    public void calculateStaticField3Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $static $final $int mys=$static(pkg.ExThree).myf;;;:\n");
        xml_.append(" $public $static $final $int myf=$static(pkg.ExThree).myf;;;:\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree {\n");
        xml_.append(" $public $static $final $int myf=$static(pkg.ExTwo).myf;;;:\n");
        xml_.append("}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl ctx_ = validateStaticFields(files_);
        assertEq(0, ctx_.getClasses().staticFieldCount());
    }
    @Test
    public void calculateStaticField4Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $static $final $int mys=1i:\n");
        xml_.append(" $public $static $final $int myf=$static(pkg.ExThree).myf;;;:\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree {\n");
        xml_.append(" $public $static $final $int myf=$static(pkg.ExTwo).myf;;;:\n");
        xml_.append("}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl ctx_ = validateStaticFields(files_);
        assertEq(1, ctx_.getClasses().staticFieldCount());
        Struct str_ = ctx_.getClasses().getStaticField(new ClassField("pkg.ExTwo", "mys"));
        assertEq(1, (((NumberStruct)str_).getInstance()).intValue());
    }
    @Test
    public void calculateStaticField5Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $static $final $int mys=1i:\n");
        xml_.append(" $public $static $final $int myt=mys;;;+2i:\n");
        xml_.append(" $public $static $final $int myf=$static(pkg.ExThree).myf;;;:\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree {\n");
        xml_.append(" $public $static $final $int myf=$static(pkg.ExTwo).myf;;;:\n");
        xml_.append("}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl ctx_ = validateStaticFields(files_);
        assertEq(2, ctx_.getClasses().staticFieldCount());
        Struct str_ = ctx_.getClasses().getStaticField(new ClassField("pkg.ExTwo", "mys"));
        assertEq(1, (((NumberStruct)str_).getInstance()).intValue());
        str_ = ctx_.getClasses().getStaticField(new ClassField("pkg.ExTwo", "myt"));
        assertEq(3, (((NumberStruct)str_).getInstance()).intValue());
    }
    @Test
    public void calculateStaticField6Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $static $final $int myf=meth():\n");
        xml_.append(" $public $static $final $int mys=myf;;;+2i:\n");
        xml_.append(" $public $static $int meth(){\n");
        xml_.append("  $return 5i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl ctx_ = validateStaticFields(files_);
        assertEq(0, ctx_.getClasses().staticFieldCount());
    }
    @Test
    public void calculateStaticField7Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $static $final $int myf=$static(java.lang.Byte).MAX_VALUE:\n");
        xml_.append(" $public $static $final $int mys=myf;;;+2i:\n");
        xml_.append(" $public $static $int meth(){\n");
        xml_.append("  $return 5i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl ctx_ = validateStaticFields(files_);
        assertEq(2, ctx_.getClasses().staticFieldCount());
        Struct str_ = ctx_.getClasses().getStaticField(new ClassField("pkg.ExTwo", "myf"));
        assertEq(127, (((NumberStruct)str_).getInstance()).intValue());
        str_ = ctx_.getClasses().getStaticField(new ClassField("pkg.ExTwo", "mys"));
        assertEq(129, (((NumberStruct)str_).getInstance()).intValue());
    }
    @Test
    public void calculateStaticField8Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $static $final $int myf=2i,mys=myf;;;+3i:\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl ctx_ = validateStaticFields(files_);
        assertEq(2, ctx_.getClasses().staticFieldCount());
        Struct str_ = ctx_.getClasses().getStaticField(new ClassField("pkg.ExTwo", "myf"));
        assertEq(2, (((NumberStruct)str_).getInstance()).intValue());
        str_ = ctx_.getClasses().getStaticField(new ClassField("pkg.ExTwo", "mys"));
        assertEq(5, (((NumberStruct)str_).getInstance()).intValue());
    }
    @Test
    public void calculateStaticField9Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $static $final $byte myf=2i,mys=myf;;;+3i:\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl ctx_ = validateStaticFields(files_);
        assertEq(2, ctx_.getClasses().staticFieldCount());
        Struct str_ = ctx_.getClasses().getStaticField(new ClassField("pkg.ExTwo", "myf"));
        assertEq(2, (((NumberStruct)str_).getInstance()).intValue());
        str_ = ctx_.getClasses().getStaticField(new ClassField("pkg.ExTwo", "mys"));
        assertEq(5, (((NumberStruct)str_).getInstance()).intValue());
    }
    @Test
    public void calculateStaticField10Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $static $final $int myf=meth(),mys=5i:\n");
        xml_.append(" $public $static $int meth(){\n");
        xml_.append("  $return 5i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl ctx_ = validateStaticFields(files_);
        assertEq(1, ctx_.getClasses().staticFieldCount());
        Struct str_ = ctx_.getClasses().getStaticField(new ClassField("pkg.ExTwo", "mys"));
        assertEq(5, (((NumberStruct)str_).getInstance()).intValue());
    }
    @Test
    public void calculateStaticField11Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $static $final $int mys=myf;;;+3i:\n");
        xml_.append(" $public $static $final $int myf=2i:\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl ctx_ = validateStaticFields(files_);
        assertEq(2, ctx_.getClasses().staticFieldCount());
        Struct str_ = ctx_.getClasses().getStaticField(new ClassField("pkg.ExTwo", "myf"));
        assertEq(2, (((NumberStruct)str_).getInstance()).intValue());
        str_ = ctx_.getClasses().getStaticField(new ClassField("pkg.ExTwo", "mys"));
        assertEq(5, (((NumberStruct)str_).getInstance()).intValue());
    }
    @Test
    public void calculateStaticField12Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $static $final $int mys=\"\".toString().length()+myf+1:\n");
        xml_.append(" $public $static $final $int myf=1:\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl ctx_ = validateStaticFields(files_);
        assertEq(2, ctx_.getClasses().staticFieldCount());
        Struct str_ = ctx_.getClasses().getStaticField(new ClassField("pkg.ExTwo", "myf"));
        assertEq(1, (((NumberStruct)str_).getInstance()).intValue());
        str_ = ctx_.getClasses().getStaticField(new ClassField("pkg.ExTwo", "mys"));
        assertEq(2, (((NumberStruct)str_).getInstance()).intValue());
    }
    @Test
    public void calculateStaticField13Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $static $final $int field=($int)$math.random():\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl ctx_ = validateStaticFields(files_);
        assertEq(0, ctx_.getClasses().staticFieldCount());
    }
    private ContextEl validateStaticFields(StringMap<String> _files) {
        Options opt_ = new Options();
        opt_.setEndLineSemiColumn(false);
        opt_.setSuffixVar(VariableSuffix.DISTINCT);
        ContextEl cont_ = InitializationLgNames.buildStdOne(opt_);
        Classes classes_ = cont_.getClasses();
        Classes.buildPredefinedBracesBodies(cont_);
        Classes.tryBuildBracedClassesBodies(_files, cont_, false);
        assertTrue(classes_.displayErrors(), classes_.isEmptyErrors());
        classes_.validateInheritingClasses(cont_, false);
        assertTrue(classes_.displayErrors(), classes_.isEmptyErrors());
        classes_.validateIds(cont_,false);
        classes_.validateOverridingInherit(cont_, false);
        assertTrue(classes_.displayErrors(), classes_.isEmptyErrors());
        classes_.initStaticFields(cont_, false);
        assertTrue(classes_.displayErrors(), classes_.isEmptyErrors());
        return cont_;
    }
    private ContextEl unfullValidateOverridingMethods(StringMap<String> _files) {
        Options opt_ = new Options();
        opt_.setEndLineSemiColumn(false);
        opt_.setSuffixVar(VariableSuffix.DISTINCT);
        ContextEl cont_ = InitializationLgNames.buildStdOne(opt_);
        Classes classes_ = cont_.getClasses();
        Classes.buildPredefinedBracesBodies(cont_);
        Classes.tryBuildBracedClassesBodies(_files, cont_, false);
        assertTrue(classes_.displayErrors(), classes_.isEmptyErrors());
        assertTrue(classes_.displayErrors(), classes_.isEmptyErrors());
        classes_.validateInheritingClasses(cont_, false);
        assertTrue(classes_.displayErrors(), classes_.isEmptyErrors());
        classes_.validateIds(cont_,false);
        assertTrue(classes_.displayErrors(), classes_.isEmptyErrors());
        return cont_;
    }
    private ContextEl unfullValidateInheritingClasses(StringMap<String> _files) {
        Options opt_ = new Options();
        opt_.setEndLineSemiColumn(false);
        opt_.setSuffixVar(VariableSuffix.DISTINCT);
        ContextEl cont_ = InitializationLgNames.buildStdOne(opt_);
        Classes classes_ = cont_.getClasses();
        Classes.buildPredefinedBracesBodies(cont_);
        Classes.tryBuildBracedClassesBodies(_files, cont_, false);
        assertTrue(classes_.displayErrors(), classes_.isEmptyErrors());
        classes_.validateInheritingClasses(cont_, false);
        assertTrue(classes_.displayErrors(), classes_.isEmptyErrors());
        return cont_;
    }
    private void failValidateInheritingClasses(StringMap<String> _files) {
        Options opt_ = new Options();
        opt_.setEndLineSemiColumn(false);
        opt_.setSuffixVar(VariableSuffix.DISTINCT);
        ContextEl cont_ = InitializationLgNames.buildStdOne(opt_);
        Classes classes_ = cont_.getClasses();
        Classes.buildPredefinedBracesBodies(cont_);
        Classes.tryBuildBracedClassesBodies(_files, cont_, false);
        assertTrue(classes_.displayErrors(), classes_.isEmptyErrors());
        classes_.validateInheritingClasses(cont_, false);
        assertTrue(classes_.displayErrors(), !classes_.isEmptyErrors());
    }

    private ObjectMap<MethodId, StringList> toList(ObjectMap<MethodId, EqList<ClassMethodId>> _m) {
        ObjectMap<MethodId, StringList> m_ = new ObjectMap<MethodId, StringList>();
        for (EntryCust<MethodId, EqList<ClassMethodId>> e: _m.entryList()) {
            StringList l_ = new StringList();
            for (ClassMethodId c: e.getValue()) {
                l_.add(c.getClassName());
            }
            m_.put(e.getKey(), l_);
        }
        return m_;
    }

    private ObjectMap<MethodId, EqList<ClassMethodId>> toId(ObjectMap<MethodId, StringList> _m) {
        ObjectMap<MethodId, EqList<ClassMethodId>> m_ = new ObjectMap<MethodId, EqList<ClassMethodId>>();
        for (EntryCust<MethodId, StringList> e: _m.entryList()) {
            EqList<ClassMethodId> l_ = new EqList<ClassMethodId>();
            for (String c: e.getValue()) {
                l_.add(new ClassMethodId(c, e.getKey()));
            }
            m_.put(e.getKey(), l_);
        }
        return m_;
    }
}
