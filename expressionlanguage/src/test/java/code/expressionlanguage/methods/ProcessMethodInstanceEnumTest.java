package code.expressionlanguage.methods;

import static code.expressionlanguage.EquallableElUtil.assertEq;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.opers.util.ClassField;
import code.expressionlanguage.opers.util.Struct;
import code.util.StringMap;

@SuppressWarnings("static-method")
public final class ProcessMethodInstanceEnumTest extends ProcessMethodCommon {

    @Test
    public void initializeClass1Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl();
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().getErrorsDet().isEmpty());
        ProcessMethod.initializeClass("pkg.Ex", cont_);
        assertTrue(cont_.getClasses().isInitialized("pkg.Ex"));
    }

    @Test
    public void initializeClass2Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.Ex {\n");
        xml_.append(" ONE:\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl();
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().getErrorsDet().isEmpty());
        ProcessMethod.initializeClass("pkg.Ex", cont_);
        assertTrue(cont_.getClasses().isInitialized("pkg.Ex"));
    }

    @Test
    public void initializeClass3Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.Ex {\n");
        xml_.append(" ONE:\n");
        xml_.append(" $public $int first=4i:\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl();
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().getErrorsDet().isEmpty());
        ProcessMethod.initializeClass("pkg.Ex", cont_);
        assertTrue(cont_.getClasses().isInitialized("pkg.Ex"));
        Struct str_ = cont_.getClasses().getStaticField(new ClassField("pkg.Ex", "ONE"));
        assertEq("pkg.Ex", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.Ex", "first"));
        assertEq(INTEGER, field_.getClassName(cont_));
        assertEq(4, (Number)field_.getInstance());
    }

    @Test
    public void initializeClass4Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.Ex {\n");
        xml_.append(" ONE(4i):\n");
        xml_.append(" $public $int first:\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  first;;;=i;.;:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl();
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().getErrorsDet().isEmpty());
        ProcessMethod.initializeClass("pkg.Ex", cont_);
        assertTrue(cont_.getClasses().isInitialized("pkg.Ex"));
        Struct str_ = cont_.getClasses().getStaticField(new ClassField("pkg.Ex", "ONE"));
        assertEq("pkg.Ex", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.Ex", "first"));
        assertEq(INTEGER, field_.getClassName(cont_));
        assertEq(4, (Number)field_.getInstance());
    }

    @Test
    public void initializeClass5Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.Ex {\n");
        xml_.append(" ONE(4i),\n");
        xml_.append(" TWO:\n");
        xml_.append(" $public $int first:\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  first;;;=i;.;:\n");
        xml_.append(" }\n");
        xml_.append(" $public (){\n");
        xml_.append("  first;;;=5i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl();
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().getErrorsDet().isEmpty());
        ProcessMethod.initializeClass("pkg.Ex", cont_);
        assertTrue(cont_.getClasses().isInitialized("pkg.Ex"));
        Struct str_ = cont_.getClasses().getStaticField(new ClassField("pkg.Ex", "ONE"));
        assertEq("pkg.Ex", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.Ex", "first"));
        assertEq(INTEGER, field_.getClassName(cont_));
        assertEq(4, (Number)field_.getInstance());
        str_ = cont_.getClasses().getStaticField(new ClassField("pkg.Ex", "TWO"));
        assertEq("pkg.Ex", str_.getClassName(cont_));
        field_ = str_.getFields().getVal(new ClassField("pkg.Ex", "first"));
        assertEq(INTEGER, field_.getClassName(cont_));
        assertEq(5, (Number)field_.getInstance());
    }

    @Test
    public void initializeClass6Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.Ex {\n");
        xml_.append(" ONE(4i),\n");
        xml_.append(" TWO:\n");
        xml_.append(" $public $int first:\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  first;;;=i;.;:\n");
        xml_.append(" }\n");
        xml_.append(" $public (){\n");
        xml_.append("  $this(5i):\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl();
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().getErrorsDet().isEmpty());
        ProcessMethod.initializeClass("pkg.Ex", cont_);
        assertTrue(cont_.getClasses().isInitialized("pkg.Ex"));
        Struct str_ = cont_.getClasses().getStaticField(new ClassField("pkg.Ex", "ONE"));
        assertEq("pkg.Ex", str_.getClassName(cont_));
        Struct field_;
        field_ = str_.getFields().getVal(new ClassField("pkg.Ex", "first"));
        assertEq(INTEGER, field_.getClassName(cont_));
        assertEq(4, (Number)field_.getInstance());
        str_ = cont_.getClasses().getStaticField(new ClassField("pkg.Ex", "TWO"));
        assertEq("pkg.Ex", str_.getClassName(cont_));
        field_ = str_.getFields().getVal(new ClassField("pkg.Ex", "first"));
        assertEq(INTEGER, field_.getClassName(cont_));
        assertEq(5, (Number)field_.getInstance());
    }

}
