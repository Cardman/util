package code.expressionlanguage.methods;

import static code.expressionlanguage.EquallableElUtil.assertEq;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.InitializationLgNames;
import code.expressionlanguage.common.TypeUtil;
import code.expressionlanguage.opers.util.ClassMethodId;
import code.expressionlanguage.opers.util.ClassName;
import code.expressionlanguage.opers.util.MethodId;
import code.util.EntryCust;
import code.util.EqList;
import code.util.ObjectMap;
import code.util.StringList;
import code.util.StringMap;

@SuppressWarnings("static-method")
public class RootBlockTest {

    @Test
    public void getAllGenericSuperTypes1Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T> :pkg.Ex<#T>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        StringList superTypes_ = classes_.getClassBody("pkg.ExTwo").getAllGenericSuperTypes(cont_);
        assertEq(1, superTypes_.size());
        assertEq("pkg.Ex<#T>", superTypes_.first());
    }

    @Test
    public void getAllGenericSuperTypes2Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T> :pkg.Ex<#T>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree<#S> :pkg.ExTwo<#S>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        StringList superTypes_ = classes_.getClassBody("pkg.ExThree").getAllGenericSuperTypes(cont_);
        assertEq(2, superTypes_.size());
        assertEq("pkg.ExTwo<#S>", superTypes_.first());
        assertEq("pkg.Ex<#S>", superTypes_.get(1));
    }

    @Test
    public void getAllGenericSuperTypes3Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T> :pkg.Ex<#T>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree :pkg.ExTwo<java.lang.String>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        StringList superTypes_ = classes_.getClassBody("pkg.ExThree").getAllGenericSuperTypes(cont_);
        assertEq(2, superTypes_.size());
        assertEq("pkg.ExTwo<java.lang.String>", superTypes_.first());
        assertEq("pkg.Ex<java.lang.String>", superTypes_.get(1));
    }

    @Test
    public void getAllGenericSuperTypes4Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#E> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExTwo<#T> :pkg.Ex<#T>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExThree<#S> :pkg.Ex<#S>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExFour :pkg.ExTwo<java.lang.String>:pkg.ExThree<java.lang.String>{}\n");
        files_.put("pkg/ExFour", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        StringList superTypes_ = classes_.getClassBody("pkg.ExFour").getAllGenericSuperTypes(cont_);
        assertEq(4, superTypes_.size());
        assertEq("pkg.ExTwo<java.lang.String>", superTypes_.first());
        assertEq("pkg.ExThree<java.lang.String>", superTypes_.get(1));
        assertEq("pkg.Ex<java.lang.String>", superTypes_.get(2));
        assertEq("pkg.Ex<java.lang.String>", superTypes_.last());
    }

    @Test
    public void getAllGenericSuperTypes5Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#E> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExTwo<#T> :pkg.Ex<#T>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExThree<#S> :pkg.Ex<#S>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExFour :pkg.ExTwo<java.lang.Number>:pkg.ExThree<java.lang.String>{}\n");
        files_.put("pkg/ExFour", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        StringList superTypes_ = classes_.getClassBody("pkg.ExFour").getAllGenericSuperTypes(cont_);
        assertEq(4, superTypes_.size());
        assertEq("pkg.ExTwo<java.lang.Number>", superTypes_.first());
        assertEq("pkg.ExThree<java.lang.String>", superTypes_.get(1));
        assertEq("pkg.Ex<java.lang.Number>", superTypes_.get(2));
        assertEq("pkg.Ex<java.lang.String>", superTypes_.last());
    }


    @Test
    public void test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T> :pkg.Ex<#T>{\n");
        xml_.append(" $public $normal $void instancemethod(#T i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        ObjectMap<MethodId, StringList> map_ = toList(classes_.getClassBody("pkg.ExTwo").getAllOverridingMethods());
        assertEq(1, map_.size());
        StringList superTypes_ = map_.getVal(new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#T", false))));
        assertEq(2, superTypes_.size());
        assertEq("pkg.ExTwo<#T>", superTypes_.first());
        assertEq("pkg.Ex<#T>", superTypes_.last());
        map_ = toList(classes_.getClassBody("pkg.Ex").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#E", false))));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Ex<#E>", superTypes_.first());
        MethodId id_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#E", false)));
        MethodId resId_;
        ClassMethodId res_;
        RootBlock r_ = classes_.getClassBody("pkg.Ex");
        StringMap<ClassMethodId> concrete_ = TypeUtil.getConcreteMethodsToCall(r_, id_, cont_);
        assertEq(2, concrete_.size());
        assertTrue(concrete_.contains("pkg.ExTwo"));
        res_ = concrete_.getVal("pkg.ExTwo");
        resId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#T", false)));
        assertEq("pkg.ExTwo", res_.getClassName());
        assertEq(resId_, res_.getConstraints());
        assertTrue(concrete_.contains("pkg.Ex"));
        res_ = concrete_.getVal("pkg.Ex");
        resId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#E", false)));
        assertEq("pkg.Ex", res_.getClassName());
        assertEq(resId_, res_.getConstraints());
    }

    @Test
    public void test2() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex<java.lang.String>{\n");
        xml_.append(" $public $normal $void instancemethod(java.lang.String i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        ObjectMap<MethodId, StringList> map_ = toList(classes_.getClassBody("pkg.ExTwo").getAllOverridingMethods());
        assertEq(1, map_.size());
        StringList superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>(new ClassName("java.lang.String", false))));
        assertEq(2, superTypes_.size());
        assertEq("pkg.ExTwo", superTypes_.first());
        assertEq("pkg.Ex<java.lang.String>", superTypes_.last());
        map_ = toList(classes_.getClassBody("pkg.Ex").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>(new ClassName("#E", false))));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Ex<#E>", superTypes_.first());
    }

    @Test
    public void test3() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {\n");
        xml_.append(" $public $normal #E instancemethod(){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T> :pkg.Ex<#T>{\n");
        xml_.append(" $public $normal #T instancemethod(){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        ObjectMap<MethodId, StringList> map_ = toList(classes_.getClassBody("pkg.ExTwo").getAllOverridingMethods());
        assertEq(1, map_.size());
        StringList superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>()));
        assertEq(2, superTypes_.size());
        assertEq("pkg.ExTwo<#T>", superTypes_.first());
        assertEq("pkg.Ex<#T>", superTypes_.last());
        map_ = toList(classes_.getClassBody("pkg.Ex").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>()));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Ex<#E>", superTypes_.first());
    }
    @Test
    public void test4() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {\n");
        xml_.append(" $public $normal #E instancemethod(){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex<java.lang.String>{\n");
        xml_.append(" $public $normal java.lang.String instancemethod(){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        ObjectMap<MethodId, StringList> map_ = toList(classes_.getClassBody("pkg.ExTwo").getAllOverridingMethods());
        assertEq(1, map_.size());
        StringList superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>()));
        assertEq(2, superTypes_.size());
        assertEq("pkg.ExTwo", superTypes_.first());
        assertEq("pkg.Ex<java.lang.String>", superTypes_.last());
        map_ = toList(classes_.getClassBody("pkg.Ex").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>()));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Ex<#E>", superTypes_.first());
    }
    @Test
    public void test5() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $normal java.lang.Object instancemethod(){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#E> :pkg.Ex{\n");
        xml_.append(" $public $normal #E instancemethod(){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        ObjectMap<MethodId, StringList> map_ = toList(classes_.getClassBody("pkg.ExTwo").getAllOverridingMethods());
        assertEq(1, map_.size());
        StringList superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>()));
        assertEq(2, superTypes_.size());
        assertEq("pkg.ExTwo<#E>", superTypes_.first());
        assertEq("pkg.Ex", superTypes_.last());
        map_ = toList(classes_.getClassBody("pkg.Ex").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>()));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Ex", superTypes_.first());
    }
    @Test
    public void test6() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T> :pkg.Ex<#T>:pkg.Int<#T>{\n");
        xml_.append(" $public $normal $void instancemethod(#T i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Int<#F> {\n");
        xml_.append(" $public $normal $void instancemethod(#F i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Int", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        ObjectMap<MethodId, StringList> map_ = toList(classes_.getClassBody("pkg.ExTwo").getAllOverridingMethods());
        assertEq(1, map_.size());
        StringList superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>(new ClassName("#T", false))));
        assertEq(3, superTypes_.size());
        assertEq("pkg.ExTwo<#T>", superTypes_.first());
        assertEq("pkg.Ex<#T>", superTypes_.get(1));
        assertEq("pkg.Int<#T>", superTypes_.last());
        map_ = toList(classes_.getClassBody("pkg.Ex").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>(new ClassName("#E", false))));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Ex<#E>", superTypes_.first());
        map_ = toList(classes_.getClassBody("pkg.Int").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>(new ClassName("#F", false))));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Int<#F>", superTypes_.first());
        MethodId id_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#F", false)));
        MethodId resId_;
        ClassMethodId res_;
        RootBlock r_ = classes_.getClassBody("pkg.Int");
        StringMap<ClassMethodId> concrete_ = TypeUtil.getConcreteMethodsToCall(r_, id_, cont_);
        assertEq(1, concrete_.size());
        assertTrue(concrete_.contains("pkg.ExTwo"));
        res_ = concrete_.getVal("pkg.ExTwo");
        resId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#T", false)));
        assertEq("pkg.ExTwo", res_.getClassName());
        assertEq(resId_, res_.getConstraints());
    }
    @Test
    public void test7() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T> :pkg.Ex<#T>:pkg.Int<#T>{\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Int<#F> {\n");
        xml_.append(" $public $normal $void instancemethod(#F i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Int", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        ObjectMap<MethodId, StringList> map_ = toList(classes_.getClassBody("pkg.ExTwo").getAllOverridingMethods());
        assertEq(1, map_.size());
        StringList superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>(new ClassName("#T", false))));
        assertEq(2, superTypes_.size());
        assertEq("pkg.Ex<#T>", superTypes_.first());
        assertEq("pkg.Int<#T>", superTypes_.last());
        map_ = toList(classes_.getClassBody("pkg.Ex").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>(new ClassName("#E", false))));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Ex<#E>", superTypes_.first());
        map_ = toList(classes_.getClassBody("pkg.Int").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>(new ClassName("#F", false))));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Int<#F>", superTypes_.first());
    }
    @Test
    public void test8() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T:pkg.Int<java.lang.String>&pkg.Int2<java.lang.String>> {\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Int<#F> {\n");
        xml_.append(" $public $normal $void instancemethod(#F i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Int", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Int2<#U> {\n");
        xml_.append(" $public $normal $void instancemethod(#U i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Int2", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
    }
    @Test
    public void test9() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $abstract $class pkg.Ex<#E> {\n");
        xml_.append(" $public $abstract $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T> :pkg.Ex<#T>{\n");
        xml_.append(" $public $normal $void instancemethod(#T i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        ObjectMap<MethodId, StringList> map_ = toList(classes_.getClassBody("pkg.ExTwo").getAllOverridingMethods());
        assertEq(1, map_.size());
        StringList superTypes_ = map_.getVal(new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#T", false))));
        assertEq(2, superTypes_.size());
        assertEq("pkg.ExTwo<#T>", superTypes_.first());
        assertEq("pkg.Ex<#T>", superTypes_.last());
        map_ = toList(classes_.getClassBody("pkg.Ex").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#E", false))));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Ex<#E>", superTypes_.first());
    }
    @Test
    public void test10() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $abstract $interface pkg.Ex<#E> {\n");
        xml_.append(" $public $abstract $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T> :pkg.Ex<#T>{\n");
        xml_.append(" $public $normal $void instancemethod(#T i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        ObjectMap<MethodId, StringList> map_ = toList(classes_.getClassBody("pkg.ExTwo").getAllOverridingMethods());
        assertEq(1, map_.size());
        StringList superTypes_ = map_.getVal(new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#T", false))));
        assertEq(2, superTypes_.size());
        assertEq("pkg.ExTwo<#T>", superTypes_.first());
        assertEq("pkg.Ex<#T>", superTypes_.last());
        map_ = toList(classes_.getClassBody("pkg.Ex").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#E", false))));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Ex<#E>", superTypes_.first());
    }
    @Test
    public void test11() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $abstract $interface pkg.Ex<#E> {\n");
        xml_.append(" $public $abstract $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExTwo<#F> :pkg.Ex<#F>{\n");
        xml_.append(" $public $normal $void instancemethod(#F i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree<#T> :pkg.ExTwo<#T>{\n");
        xml_.append("}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        ObjectMap<MethodId, StringList> map_ = toList(classes_.getClassBody("pkg.ExTwo").getAllOverridingMethods());
        assertEq(1, map_.size());
        StringList superTypes_ = map_.getVal(new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#F", false))));
        assertEq(2, superTypes_.size());
        assertEq("pkg.ExTwo<#F>", superTypes_.first());
        assertEq("pkg.Ex<#F>", superTypes_.last());
        map_ = toList(classes_.getClassBody("pkg.Ex").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#E", false))));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Ex<#E>", superTypes_.first());
        map_ = toList(classes_.getClassBody("pkg.ExThree").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#T", false))));
        assertEq(2, superTypes_.size());
        assertEq("pkg.ExTwo<#T>", superTypes_.first());
        assertEq("pkg.Ex<#T>", superTypes_.last());
        MethodId id_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#F", false)));
        MethodId resId_;
        ClassMethodId res_;
        RootBlock r_ = classes_.getClassBody("pkg.ExTwo");
        StringMap<ClassMethodId> concrete_ = TypeUtil.getConcreteMethodsToCall(r_, id_, cont_);
        assertEq(1, concrete_.size());
        assertTrue(concrete_.contains("pkg.ExThree"));
        res_ = concrete_.getVal("pkg.ExThree");
        resId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#F", false)));
        assertEq("pkg.ExTwo", res_.getClassName());
        assertEq(resId_, res_.getConstraints());
        id_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#E", false)));
        r_ = classes_.getClassBody("pkg.Ex");
        concrete_ = TypeUtil.getConcreteMethodsToCall(r_, id_, cont_);
        assertEq(1, concrete_.size());
        assertTrue(concrete_.contains("pkg.ExThree"));
        res_ = concrete_.getVal("pkg.ExThree");
        resId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#F", false)));
        assertEq("pkg.ExTwo", res_.getClassName());
        assertEq(resId_, res_.getConstraints());
    }
    @Test
    public void test12() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $abstract $class pkg.Ex<#E> {\n");
        xml_.append(" $public $abstract $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T> :pkg.Ex<#T>{\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(!classes_.getErrorsDet().isEmpty());
    }
    @Test
    public void test13() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $abstract $interface pkg.Ex<#E> {\n");
        xml_.append(" $public $abstract $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T> :pkg.Ex<#T>{\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), !classes_.getErrorsDet().isEmpty());
    }
    @Test
    public void test14() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $normal java.lang.Number instancemethod(){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#E:java.lang.Number> :pkg.Ex{\n");
        xml_.append(" $public $normal #E instancemethod(){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        ObjectMap<MethodId, StringList> map_ = toList(classes_.getClassBody("pkg.ExTwo").getAllOverridingMethods());
        assertEq(1, map_.size());
        StringList superTypes_ = map_.getVal(new MethodId(false,"instancemethod", new EqList<ClassName>()));
        assertEq(2, superTypes_.size());
        assertEq("pkg.ExTwo<#E>", superTypes_.first());
        assertEq("pkg.Ex", superTypes_.last());
        map_ = toList(classes_.getClassBody("pkg.Ex").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false,"instancemethod", new EqList<ClassName>()));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Ex", superTypes_.first());
    }
    @Test
    public void test15() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $normal java.lang.Number instancemethod($int i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#E> :pkg.Ex{\n");
        xml_.append(" $public $normal #E instancemethod($int i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), !classes_.getErrorsDet().isEmpty());
    }
    @Test
    public void test16() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $package $normal $void instancemethod(){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{\n");
        xml_.append(" $protected $normal $void instancemethod(){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.ExThree :pkg.ExTwo{\n");
        xml_.append(" $public $normal $void instancemethod(){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkgtwo/ExThree", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        ObjectMap<MethodId, StringList> map_ = toList(classes_.getClassBody("pkgtwo.ExThree").getAllOverridingMethods());
        assertEq(1, map_.size());
        StringList superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>()));
        assertEq(3, superTypes_.size());
        assertEq("pkgtwo.ExThree", superTypes_.first());
        assertEq("pkg.ExTwo", superTypes_.get(1));
        assertEq("pkg.Ex", superTypes_.last());
        map_ = toList(classes_.getClassBody("pkg.ExTwo").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>()));
        assertEq(2, superTypes_.size());
        assertEq("pkg.ExTwo", superTypes_.first());
        assertEq("pkg.Ex", superTypes_.last());
        map_ = toList(classes_.getClassBody("pkg.Ex").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>()));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Ex", superTypes_.first());
    }
    @Test
    public void test17() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $private $normal $void instancemethod(){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{\n");
        xml_.append(" $package $normal $void instancemethod(){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        ObjectMap<MethodId, StringList> map_ = toList(classes_.getClassBody("pkg.ExTwo").getAllOverridingMethods());
        StringList superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>()));
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>()));
        assertEq(1, superTypes_.size());
        assertEq("pkg.ExTwo", superTypes_.first());
        map_ = toList(classes_.getClassBody("pkg.Ex").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>()));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Ex", superTypes_.first());
    }
    @Test
    public void test18() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T> :pkg.Ex<#T>:pkg.Int<#T>{\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Int<#F> {\n");
        xml_.append(" $public $normal $void instancemethod(#F i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Int", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        ObjectMap<MethodId, StringList> map_ = toList(classes_.getClassBody("pkg.ExTwo").getAllOverridingMethods());
        assertEq(1, map_.size());
        StringList superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>(new ClassName("#T", false))));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Int<#T>", superTypes_.first());
        map_ = toList(classes_.getClassBody("pkg.Ex").getAllOverridingMethods());
        assertEq(0, map_.size());
        map_ = toList(classes_.getClassBody("pkg.Int").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>(new ClassName("#F", false))));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Int<#F>", superTypes_.first());
        MethodId id_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#F", false)));
        MethodId resId_;
        ClassMethodId res_;
        RootBlock r_ = classes_.getClassBody("pkg.Int");
        StringMap<ClassMethodId> concrete_ = TypeUtil.getConcreteMethodsToCall(r_, id_, cont_);
        assertEq(1, concrete_.size());
        assertTrue(concrete_.contains("pkg.ExTwo"));
        res_ = concrete_.getVal("pkg.ExTwo");
        resId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#F", false)));
        assertEq("pkg.Int", res_.getClassName());
        assertEq(resId_, res_.getConstraints());
    }
    @Test
    public void test19() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> :pkg.Int<#E>{\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T> :pkg.Ex<#T>{\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Int<#F> {\n");
        xml_.append(" $public $normal $void instancemethod(#F i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Int", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateOverridingInherit(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        ObjectMap<MethodId, StringList> map_ = toList(classes_.getClassBody("pkg.ExTwo").getAllOverridingMethods());
        assertEq(1, map_.size());
        StringList superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>(new ClassName("#T", false))));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Int<#T>", superTypes_.first());
        map_ = toList(classes_.getClassBody("pkg.Ex").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>(new ClassName("#E", false))));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Int<#E>", superTypes_.first());
        map_ = toList(classes_.getClassBody("pkg.Int").getAllOverridingMethods());
        assertEq(1, map_.size());
        superTypes_ = map_.getVal(new MethodId(false, "instancemethod", new EqList<ClassName>(new ClassName("#F", false))));
        assertEq(1, superTypes_.size());
        assertEq("pkg.Int<#F>", superTypes_.first());
    }
    @Test
    public void testMockOverrides() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {\n");
        xml_.append(" $public $normal $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T> :pkg.Ex<#T>{\n");
        xml_.append(" $public $normal $void instancemethod(#T i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        ObjectMap<MethodId, EqList<ClassMethodId>> map_;
        MethodId geneId_;
        ClassMethodId geneClassId_;
        MethodId realId_;
        map_ = classes_.getClassBody("pkg.ExTwo").getAllOverridingMethods();
        map_.clear();
        geneId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#T", false)));
        realId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#T", false)));
        geneClassId_ = new ClassMethodId("pkg.ExTwo<#T>",realId_);
        map_.put(geneId_, new EqList<ClassMethodId>(geneClassId_));
        map_ = classes_.getClassBody("pkg.Ex").getAllOverridingMethods();
        map_.clear();
        geneId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#E", false)));
        realId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#E", false)));
        geneClassId_ = new ClassMethodId("pkg.Ex<#E>",realId_);
        map_.put(geneId_, new EqList<ClassMethodId>(geneClassId_));
        MethodId id_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#E", false)));
        MethodId resId_;
        ClassMethodId res_;
        RootBlock r_ = classes_.getClassBody("pkg.Ex");
        StringMap<ClassMethodId> concrete_ = TypeUtil.getConcreteMethodsToCall(r_, id_, cont_);
        assertEq(2, concrete_.size());
        res_ = concrete_.getVal("pkg.Ex");
        resId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#E", false)));
        assertEq("pkg.Ex", res_.getClassName());
        assertEq(resId_, res_.getConstraints());
        res_ = concrete_.getVal("pkg.ExTwo");
        resId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#E", false)));
        assertEq("pkg.Ex", res_.getClassName());
        assertEq(resId_, res_.getConstraints());
    }
    @Test
    public void testMockOverrides2() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $abstract $interface pkg.Ex<#E> {\n");
        xml_.append(" $public $abstract $void instancemethod(#E i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExTwo<#F> :pkg.Ex<#F>{\n");
        xml_.append(" $public $normal $void instancemethod(#F i){\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree<#T> :pkg.ExTwo<#T>{\n");
        xml_.append("}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = cont_.getClasses();
        classes_.validateSingleParameterizedClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateIds(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        ObjectMap<MethodId, EqList<ClassMethodId>> map_;
        MethodId geneId_;
        ClassMethodId geneClassId_;
        ClassMethodId geneClassIdTwo_;
        MethodId realId_;
        map_ = classes_.getClassBody("pkg.ExTwo").getAllOverridingMethods();
        map_.clear();
        geneId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#F", false)));
        realId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#F", false)));
        geneClassId_ = new ClassMethodId("pkg.ExTwo<#F>",realId_);
        realId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#E", false)));
        geneClassIdTwo_ = new ClassMethodId("pkg.Ex<#F>",realId_);
        map_.put(geneId_, new EqList<ClassMethodId>(geneClassId_,geneClassIdTwo_));
        map_ = classes_.getClassBody("pkg.Ex").getAllOverridingMethods();
        map_.clear();
        geneId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#E", false)));
        realId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#E", false)));
        geneClassId_ = new ClassMethodId("pkg.Ex<#E>",realId_);
        map_.put(geneId_, new EqList<ClassMethodId>(geneClassId_));
        MethodId id_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#F", false)));
        RootBlock r_ = classes_.getClassBody("pkg.ExTwo");
        StringMap<ClassMethodId> concrete_ = TypeUtil.getConcreteMethodsToCall(r_, id_, cont_);
        assertEq(1, concrete_.size());
        MethodId resId_;
        ClassMethodId res_;
        res_ = concrete_.getVal("pkg.ExThree");
        resId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#F", false)));
        assertEq("pkg.ExTwo", res_.getClassName());
        assertEq(resId_, res_.getConstraints());
        id_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#E", false)));
        r_ = classes_.getClassBody("pkg.Ex");
        concrete_ = TypeUtil.getConcreteMethodsToCall(r_, id_, cont_);
        assertEq(1, concrete_.size());
        res_ = concrete_.getVal("pkg.ExThree");
        resId_ = new MethodId(false,"instancemethod", new EqList<ClassName>(new ClassName("#F", false)));
        assertEq("pkg.ExTwo", res_.getClassName());
        assertEq(resId_, res_.getConstraints());
    }
    private static ContextEl unfullValidateOverridingMethods(StringMap<String> _files) {
        ContextEl cont_ = new ContextEl();
        Classes classes_ = new Classes();
        cont_.setClasses(classes_);
        InitializationLgNames.initAdvStandards(cont_);
        cont_.initError();
        Classes.tryBuildBracedClassesBodies(_files, cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        classes_.validateInheritingClasses(cont_);
        assertTrue(classes_.getErrorsDet().display(), classes_.getErrorsDet().isEmpty());
        return cont_;
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
}
