package code.expressionlanguage.methods;

import static code.expressionlanguage.EquallableElUtil.assertEq;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.opers.util.ClassField;
import code.expressionlanguage.opers.util.ConstructorId;
import code.expressionlanguage.opers.util.Struct;
import code.util.CustList;
import code.util.StringMap;

public final class ProcessMethodInstanceVarArgTest extends ProcessMethodCommon {
    @Test
    public void instanceArgument96Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public [java.lang.Integer array=$new [java.lang.Integer[](1i):\n");
        xml_.append(" $public $int elt=2i:\n");
        xml_.append(" {\n");
        xml_.append("  ref()[0i]=getter($vararg(\"$int\"),$firstopt(8i),5i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $final [java.lang.Object ref(){\n");
        xml_.append("  $return array;;;:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int getter($int... i){\n");
        xml_.append("  $int t:\n");
        xml_.append("  $foreach(java.lang.Number e:i;.;){\n");
        xml_.append("   t;.+=e;intValue():\n");
        xml_.append("  }\n");
        xml_.append("  $return elt;;;+t;.:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextEl();
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.Ex");
        ProcessMethod.initializeClass("pkg.Ex", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.Ex", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.Ex", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.Ex", "array"));
        assertEq(ARR_INTEGER, field_.getClassName(cont_));
        Struct[] array_ = (Struct[]) field_.getInstance();
        assertEq(15, (Number) array_[0].getInstance());
    }
    @Test
    public void instanceArgument97Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public [java.lang.Integer array=$new [java.lang.Integer[](1i):\n");
        xml_.append(" $public $int elt=2i:\n");
        xml_.append(" {\n");
        xml_.append("  ref()[0i]=getter(8i,5i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $final [java.lang.Object ref(){\n");
        xml_.append("  $return array;;;:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int getter($int... i){\n");
        xml_.append("  $int t:\n");
        xml_.append("  $foreach(java.lang.Number e:i;.;){\n");
        xml_.append("   t;.+=e;intValue():\n");
        xml_.append("  }\n");
        xml_.append("  $return elt;;;+t;.:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextEl();
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.Ex");
        ProcessMethod.initializeClass("pkg.Ex", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.Ex", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.Ex", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.Ex", "array"));
        assertEq(ARR_INTEGER, field_.getClassName(cont_));
        Struct[] array_ = (Struct[]) field_.getInstance();
        assertEq(15, (Number) array_[0].getInstance());
    }
    @Test
    public void instanceArgument98Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public [java.lang.Integer array=$new [java.lang.Integer[](1i):\n");
        xml_.append(" $public $int elt=2i:\n");
        xml_.append(" {\n");
        xml_.append("  ref()[0i]=getter(8i,5i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $final [java.lang.Object ref(){\n");
        xml_.append("  $return array;;;:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int getter($int... i){\n");
        xml_.append("  $int t:\n");
        xml_.append("  $foreach(java.lang.Number e:i;.;){\n");
        xml_.append("   t;.+=e;intValue():\n");
        xml_.append("  }\n");
        xml_.append("  $return elt;;;+t;.:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int getter($long... i){\n");
        xml_.append("  $int t:\n");
        xml_.append("  $foreach(java.lang.Number e:i;.;){\n");
        xml_.append("   t;.+=e;intValue()*2i:\n");
        xml_.append("  }\n");
        xml_.append("  $return elt;;;+t;.:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextEl();
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.Ex");
        ProcessMethod.initializeClass("pkg.Ex", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.Ex", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.Ex", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.Ex", "array"));
        assertEq(ARR_INTEGER, field_.getClassName(cont_));
        Struct[] array_ = (Struct[]) field_.getInstance();
        assertEq(15, (Number) array_[0].getInstance());
    }
    @Test
    public void instanceArgument99Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public [java.lang.Integer array=$new [java.lang.Integer[](1i):\n");
        xml_.append(" $public $int elt=2i:\n");
        xml_.append(" {\n");
        xml_.append("  ref()[0i]=getter(8i,5i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $final [java.lang.Object ref(){\n");
        xml_.append("  $return array;;;:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int getter($int i,$int... j){\n");
        xml_.append("  $int t:\n");
        xml_.append("  t;.=i;.;:\n");
        xml_.append("  $foreach(java.lang.Number e:j;.;){\n");
        xml_.append("   t;.+=e;intValue():\n");
        xml_.append("  }\n");
        xml_.append("  $return elt;;;+t;.:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int getter($long... i){\n");
        xml_.append("  $int t:\n");
        xml_.append("  $foreach(java.lang.Number e:i;.;){\n");
        xml_.append("   t;.+=e;intValue()*2i:\n");
        xml_.append("  }\n");
        xml_.append("  $return elt;;;+t;.:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextEl();
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.Ex");
        ProcessMethod.initializeClass("pkg.Ex", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.Ex", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.Ex", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.Ex", "array"));
        assertEq(ARR_INTEGER, field_.getClassName(cont_));
        Struct[] array_ = (Struct[]) field_.getInstance();
        assertEq(15, (Number) array_[0].getInstance());
    }
    @Test
    public void instanceArgument100Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public [java.lang.Integer array=$new [java.lang.Integer[](1i):\n");
        xml_.append(" $public $int elt=2i:\n");
        xml_.append(" {\n");
        xml_.append("  ref()[0i]=getter(8i,5i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $final [java.lang.Object ref(){\n");
        xml_.append("  $return array;;;:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int getter($int i,$int... j){\n");
        xml_.append("  $int t:\n");
        xml_.append("  t;.=i;.;:\n");
        xml_.append("  $foreach(java.lang.Number e:j;.;){\n");
        xml_.append("   t;.+=e;intValue():\n");
        xml_.append("  }\n");
        xml_.append("  $return elt;;;+t;.:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int getter($int i,$long... j){\n");
        xml_.append("  $int t:\n");
        xml_.append("  t;.=i;.;*2i:\n");
        xml_.append("  $foreach(java.lang.Number e:j;.;){\n");
        xml_.append("   t;.+=e;intValue()*2i:\n");
        xml_.append("  }\n");
        xml_.append("  $return elt;;;+t;.:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextEl();
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.Ex");
        ProcessMethod.initializeClass("pkg.Ex", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.Ex", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.Ex", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.Ex", "array"));
        assertEq(ARR_INTEGER, field_.getClassName(cont_));
        Struct[] array_ = (Struct[]) field_.getInstance();
        assertEq(15, (Number) array_[0].getInstance());
    }
    @Test
    public void instanceArgument101Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public [java.lang.Integer array=$new [java.lang.Integer[](1i):\n");
        xml_.append(" $public $int elt=2i:\n");
        xml_.append(" {\n");
        xml_.append("  ref()[0i]=getter($new [$int[](8i,5i)):\n");
        xml_.append(" }\n");
        xml_.append(" $public $final [java.lang.Object ref(){\n");
        xml_.append("  $return array;;;:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int getter($int... j){\n");
        xml_.append("  $int t:\n");
        xml_.append("  $foreach(java.lang.Number e:j;.;){\n");
        xml_.append("   t;.+=e;intValue():\n");
        xml_.append("  }\n");
        xml_.append("  $return elt;;;+t;.:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int getter($int i,$long... j){\n");
        xml_.append("  $int t:\n");
        xml_.append("  t;.=i;.;*2i:\n");
        xml_.append("  $foreach(java.lang.Number e:j;.;){\n");
        xml_.append("   t;.+=e;intValue()*2i:\n");
        xml_.append("  }\n");
        xml_.append("  $return elt;;;+t;.:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextEl();
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.Ex");
        ProcessMethod.initializeClass("pkg.Ex", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.Ex", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.Ex", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.Ex", "array"));
        assertEq(ARR_INTEGER, field_.getClassName(cont_));
        Struct[] array_ = (Struct[]) field_.getInstance();
        assertEq(15, (Number) array_[0].getInstance());
    }
    @Test
    public void instanceArgument102Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.Ex :pkg.Int{\n");
        xml_.append(" ONE(4i),\n");
        xml_.append(" TWO:\n");
        xml_.append(" $public $int first:\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  first;;;=i;.;:\n");
        xml_.append(" }\n");
        xml_.append(" $public (){\n");
        xml_.append("  first;;;=5i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int doubleValue(){\n");
        xml_.append("  $return first;;;:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExCont {\n");
        xml_.append(" $public $enum pre=$static$pkg$Ex.TWO;;;:\n");
        xml_.append(" $public java.lang.String inst=pre;;;name():\n");
        xml_.append("}\n");
        files_.put("pkg/ExCont", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Int {\n");
        xml_.append(" $public $abstract java.lang.String name():\n");
        xml_.append(" $public $abstract $int ordinal():\n");
        xml_.append("}\n");
        files_.put("pkg/Int", xml_.toString());
        ContextEl cont_ = contextEl();
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.ExCont");
        ProcessMethod.initializeClass("pkg.ExCont", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.ExCont", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.ExCont", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.ExCont", "inst"));
        assertEq(STRING, field_.getClassName(cont_));
        assertEq("TWO",(String) field_.getInstance());
    }
    @Test
    public void instanceArgument103Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.Ex :pkg.Int{\n");
        xml_.append(" ONE(4i),\n");
        xml_.append(" TWO:\n");
        xml_.append(" $public $int first:\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  first;;;=i;.;:\n");
        xml_.append(" }\n");
        xml_.append(" $public (){\n");
        xml_.append("  first;;;=5i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int doubleValue(){\n");
        xml_.append("  $return first;;;:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExCont {\n");
        xml_.append(" $public $enum pre=$static$pkg$Ex.TWO;;;:\n");
        xml_.append(" $public $int inst=pre;;;ordinal():\n");
        xml_.append("}\n");
        files_.put("pkg/ExCont", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Int {\n");
        xml_.append(" $public $abstract java.lang.String name():\n");
        xml_.append(" $public $abstract $int ordinal():\n");
        xml_.append("}\n");
        files_.put("pkg/Int", xml_.toString());
        ContextEl cont_ = contextEl();
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.ExCont");
        ProcessMethod.initializeClass("pkg.ExCont", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.ExCont", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.ExCont", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.ExCont", "inst"));
        assertEq(INTEGER, field_.getClassName(cont_));
        assertEq(1, (Number)field_.getInstance());
    }
    @Test
    public void instanceArgument104Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.Ex :pkg.Int{\n");
        xml_.append(" ONE(4i),\n");
        xml_.append(" TWO:\n");
        xml_.append(" $public $int first:\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  first;;;=i;.;:\n");
        xml_.append(" }\n");
        xml_.append(" $public (){\n");
        xml_.append("  first;;;=5i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int doubleValue(){\n");
        xml_.append("  $return first;;;:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExCont {\n");
        xml_.append(" $public pkg.Int pre=$static$pkg$Ex.TWO;;;:\n");
        xml_.append(" $public java.lang.String inst=pre;;;name():\n");
        xml_.append("}\n");
        files_.put("pkg/ExCont", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Int {\n");
        xml_.append(" $public $abstract java.lang.String name():\n");
        xml_.append(" $public $abstract $int ordinal():\n");
        xml_.append("}\n");
        files_.put("pkg/Int", xml_.toString());
        ContextEl cont_ = contextEl();
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.ExCont");
        ProcessMethod.initializeClass("pkg.ExCont", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.ExCont", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.ExCont", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.ExCont", "inst"));
        assertEq(STRING, field_.getClassName(cont_));
        assertEq("TWO", (String)field_.getInstance());
    }
    @Test
    public void instanceArgument105Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.Ex :pkg.Int{\n");
        xml_.append(" ONE(4i),\n");
        xml_.append(" TWO:\n");
        xml_.append(" $public $int first:\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  first;;;=i;.;:\n");
        xml_.append(" }\n");
        xml_.append(" $public (){\n");
        xml_.append("  first;;;=5i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int doubleValue(){\n");
        xml_.append("  $return first;;;:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExCont {\n");
        xml_.append(" $public pkg.Int pre=$static$pkg$Ex.TWO;;;:\n");
        xml_.append(" $public $int inst=pre;;;ordinal():\n");
        xml_.append("}\n");
        files_.put("pkg/ExCont", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Int {\n");
        xml_.append(" $public $abstract java.lang.String name():\n");
        xml_.append(" $public $abstract $int ordinal():\n");
        xml_.append("}\n");
        files_.put("pkg/Int", xml_.toString());
        ContextEl cont_ = contextEl();
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.ExCont");
        ProcessMethod.initializeClass("pkg.ExCont", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.ExCont", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.ExCont", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.ExCont", "inst"));
        assertEq(INTEGER, field_.getClassName(cont_));
        assertEq(1, (Number)field_.getInstance());
    }
    @Test
    public void instanceArgument106Test() {
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkgtwo.ExTwo{\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return 2i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.ExTwo {\n");
        xml_.append(" $package $normal $int getter(){\n");
        xml_.append("  $return 5i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkgtwo/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.ExThree {\n");
        xml_.append(" $public pkgtwo.ExTwo inst=$new pkg.Ex():\n");
        xml_.append(" $public $int ance=inst;;;$this$getter():\n");
        xml_.append("}\n");
        files_.put("pkgtwo/ExThree", xml_.toString());
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkgtwo.ExThree");
        ProcessMethod.initializeClass("pkgtwo.ExThree", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkgtwo.ExThree", null, id_, args_, cont_);
        assertTrue(cont_.getClasses().isInitialized("pkgtwo.ExThree"));
        Struct str_ = ret_.getStruct();
        assertEq("pkgtwo.ExThree", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkgtwo.ExThree", "ance"));
        assertEq(INTEGER, field_.getClassName(cont_));
        assertEq(5, (Number)field_.getInstance());
    }
    @Test
    public void instanceArgument107Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public [java.lang.Integer array=$new [java.lang.Integer[](1i):\n");
        xml_.append(" $public $int elt=2i:\n");
        xml_.append(" {\n");
        xml_.append("  ref()[0i]=getter($vararg(\"$int\"),$firstopt(8i)):\n");
        xml_.append(" }\n");
        xml_.append(" $public $final [java.lang.Object ref(){\n");
        xml_.append("  $return array;;;:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int getter($int... i){\n");
        xml_.append("  $int t:\n");
        xml_.append("  $foreach(java.lang.Number e:i;.;){\n");
        xml_.append("   t;.+=e;intValue():\n");
        xml_.append("  }\n");
        xml_.append("  $return elt;;;+t;.:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextEl();
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.Ex");
        ProcessMethod.initializeClass("pkg.Ex", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.Ex", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.Ex", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.Ex", "array"));
        assertEq(ARR_INTEGER, field_.getClassName(cont_));
        Struct[] array_ = (Struct[]) field_.getInstance();
        assertEq(10, (Number) array_[0].getInstance());
    }
    @Test
    public void instanceArgument108Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public [java.lang.Integer array=$new [java.lang.Integer[](1i):\n");
        xml_.append(" $public $int elt=2i:\n");
        xml_.append(" {\n");
        xml_.append("  ref()[0i]=getter():\n");
        xml_.append(" }\n");
        xml_.append(" $public $final [java.lang.Object ref(){\n");
        xml_.append("  $return array;;;:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int getter($int... i){\n");
        xml_.append("  $int t:\n");
        xml_.append("  $foreach(java.lang.Number e:i;.;){\n");
        xml_.append("   t;.+=e;intValue():\n");
        xml_.append("  }\n");
        xml_.append("  $return elt;;;+t;.:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextEl();
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.Ex");
        ProcessMethod.initializeClass("pkg.Ex", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.Ex", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.Ex", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.Ex", "array"));
        assertEq(ARR_INTEGER, field_.getClassName(cont_));
        Struct[] array_ = (Struct[]) field_.getInstance();
        assertEq(2, (Number) array_[0].getInstance());
    }
    @Test
    public void instanceArgument109Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public [java.lang.Integer array=$new [java.lang.Integer[](1i):\n");
        xml_.append(" $public $int elt=2i:\n");
        xml_.append(" {\n");
        xml_.append("  ref()[0i]=getter($vararg(\"$int\")):\n");
        xml_.append(" }\n");
        xml_.append(" $public $final [java.lang.Object ref(){\n");
        xml_.append("  $return array;;;:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int getter($int... i){\n");
        xml_.append("  $int t:\n");
        xml_.append("  $foreach(java.lang.Number e:i;.;){\n");
        xml_.append("   t;.+=e;intValue():\n");
        xml_.append("  }\n");
        xml_.append("  $return elt;;;+t;.:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextEl();
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.Ex");
        ProcessMethod.initializeClass("pkg.Ex", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.Ex", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.Ex", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.Ex", "array"));
        assertEq(ARR_INTEGER, field_.getClassName(cont_));
        Struct[] array_ = (Struct[]) field_.getInstance();
        assertEq(2, (Number) array_[0].getInstance());
    }
    @Test
    public void instanceArgument110Test() {
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkgtwo.ExTwo{\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.ExTwo {\n");
        xml_.append(" $package $normal $int getter(){\n");
        xml_.append("  $return 5i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkgtwo/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkgtwo.ExThree {\n");
        xml_.append(" $public pkgtwo.ExTwo inst=$new pkg.Ex():\n");
        xml_.append(" $public $int ance=inst;;;getter():\n");
        xml_.append("}\n");
        files_.put("pkgtwo/ExThree", xml_.toString());
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkgtwo.ExThree");
        ProcessMethod.initializeClass("pkgtwo.ExThree", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkgtwo.ExThree", null, id_, args_, cont_);
        assertTrue(cont_.getClasses().isInitialized("pkgtwo.ExThree"));
        Struct str_ = ret_.getStruct();
        assertEq("pkgtwo.ExThree", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkgtwo.ExThree", "ance"));
        assertEq(INTEGER, field_.getClassName(cont_));
        assertEq(5, (Number)field_.getInstance());
    }
    @Test
    public void instanceArgument111Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.Ex :pkg.Int{\n");
        xml_.append(" ONE(4i),\n");
        xml_.append(" TWO:\n");
        xml_.append(" $public $int first:\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  first;;;=i;.;:\n");
        xml_.append(" }\n");
        xml_.append(" $public (){\n");
        xml_.append("  first;;;=5i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int doubleValue(){\n");
        xml_.append("  $return first;;;:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExCont {\n");
        xml_.append(" $public $Enum<pkg.Ex> pre=$static$pkg$Ex.TWO;;;:\n");
        xml_.append(" $public java.lang.String inst=pre;;;name():\n");
        xml_.append("}\n");
        files_.put("pkg/ExCont", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Int {\n");
        xml_.append(" $public $abstract java.lang.String name():\n");
        xml_.append(" $public $abstract $int ordinal():\n");
        xml_.append("}\n");
        files_.put("pkg/Int", xml_.toString());
        ContextEl cont_ = contextEl();
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.ExCont");
        ProcessMethod.initializeClass("pkg.ExCont", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.ExCont", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.ExCont", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.ExCont", "inst"));
        assertEq(STRING, field_.getClassName(cont_));
        assertEq("TWO", (String)field_.getInstance());
    }
    @Test
    public void instanceArgument112Test() {
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkg.ExTwo{\n");
        xml_.append(" $public $int inst=8i:\n");
        xml_.append(" $public $normal $int superaccess(){\n");
        xml_.append("  $return $super$inst;;;.intValue():\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return 2i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public java.lang.Integer inst=16i:\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return 5i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree {\n");
        xml_.append(" $public pkg.Ex inst=$new pkg.Ex():\n");
        xml_.append(" $public $int ance=inst;;;superaccess():\n");
        xml_.append("}\n");
        files_.put("pkg/ExThree", xml_.toString());
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.ExThree");
        ProcessMethod.initializeClass("pkg.ExThree", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.ExThree", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.ExThree", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.ExThree", "ance"));
        assertEq(INTEGER, field_.getClassName(cont_));
        assertEq(16, (Number)field_.getInstance());
    }
    @Test
    public void instanceArgument113Test() {
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkg.ExTwo{\n");
        xml_.append(" $public $int inst=8i:\n");
        xml_.append(" $public $normal $int superaccess(){\n");
        xml_.append("  $return $super$inst;;;+0i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return 2i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $int inst=16i:\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return 5i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree {\n");
        xml_.append(" $public pkg.Ex inst=$new pkg.Ex():\n");
        xml_.append(" $public $int ance=inst;;;superaccess():\n");
        xml_.append("}\n");
        files_.put("pkg/ExThree", xml_.toString());
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.ExThree");
        ProcessMethod.initializeClass("pkg.ExThree", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.ExThree", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.ExThree", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.ExThree", "ance"));
        assertEq(INTEGER, field_.getClassName(cont_));
        assertEq(16, (Number)field_.getInstance());
    }
    @Test
    public void instanceArgument114Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int inst=2i:\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $int ance=$classchoice$pkg$Ex$$inst;;;+0i:\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = contextEl();
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.ExTwo");
        ProcessMethod.initializeClass("pkg.ExTwo", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.ExTwo", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.ExTwo", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.ExTwo", "ance"));
        assertEq(INTEGER, field_.getClassName(cont_));
        assertEq(2, (Number)field_.getInstance());
    }
    @Test
    public void instanceArgument115Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static java.lang.Integer inst=2i:\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $int ance=$classchoice$pkg$Ex$$inst;;;.intValue():\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = contextEl();
        Classes.validateAll(files_, cont_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.ExTwo");
        ProcessMethod.initializeClass("pkg.ExTwo", cont_);
        Argument ret_;
        ret_ = instanceArgument("pkg.ExTwo", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.ExTwo", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.ExTwo", "ance"));
        assertEq(INTEGER, field_.getClassName(cont_));
        assertEq(2, (Number)field_.getInstance());
    }
}
