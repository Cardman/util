package code.expressionlanguage.methods;

import static code.expressionlanguage.EquallableElUtil.assertEq;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.opers.util.MethodId;
import code.util.CustList;
import code.util.StringMap;

public final class ProcessMethodCompoundOperatorTest extends ProcessMethodCommon {

    @Test
    public void calculateArgument1Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("operator+ pkg.Ex (pkg.Ex a, pkg.Ex b){\n");
        xml_.append(" pkg.Ex out = new pkg.Ex();\n");
        xml_.append(" out.a=a.a+b.a;\n");
        xml_.append(" return out;\n");
        xml_.append("}\n");
        xml_.append("public class pkg.Ex {\n");
        xml_.append(" public int a;\n");
        xml_.append(" public static int catching(){\n");
        xml_.append("  Ex one = new Ex();\n");
        xml_.append("  one.a=5i;\n");
        xml_.append("  Ex two = new Ex();\n");
        xml_.append("  two.a=3i;\n");
        xml_.append("  one += two;\n");
        xml_.append("  if (one.a != 8i){\n");
        xml_.append("   return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEnElDefault();
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        Argument ret_ = new Argument();
        ret_ = calculateArgument("pkg.Ex", id_, args_, cont_);
        assertEq(0, (Number)ret_.getObject());
    }
    @Test
    public void calculateArgument2Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("operator+ pkg.Ex (pkg.Ex a, pkg.Ex b){\n");
        xml_.append(" pkg.Ex out = new pkg.Ex();\n");
        xml_.append(" out.a=a.a+b.a;\n");
        xml_.append(" return out;\n");
        xml_.append("}\n");
        xml_.append("public class pkg.Ex {\n");
        xml_.append(" public int a;\n");
        xml_.append(" public static int catching(){\n");
        xml_.append("  Ex[] one = {new Ex()};\n");
        xml_.append("  one[0].a=5i;\n");
        xml_.append("  Ex two = new Ex();\n");
        xml_.append("  two.a=3i;\n");
        xml_.append("  one[0] += two;\n");
        xml_.append("  if (one[0].a != 8i){\n");
        xml_.append("   return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEnElDefault();
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        Argument ret_ = new Argument();
        ret_ = calculateArgument("pkg.Ex", id_, args_, cont_);
        assertEq(0, (Number)ret_.getObject());
    }
    @Test
    public void calculateArgument3Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("operator+ pkg.Ex (pkg.Ex a, pkg.Ex b){\n");
        xml_.append(" pkg.Ex out = new pkg.Ex();\n");
        xml_.append(" out.a=a.a+b.a;\n");
        xml_.append(" return out;\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExCont {\n");
        xml_.append(" public Ex field = new Ex();\n");
        xml_.append("}\n");
        xml_.append("public class pkg.Ex {\n");
        xml_.append(" public int a;\n");
        xml_.append(" public static int catching(){\n");
        xml_.append("  ExCont one = new ExCont();\n");
        xml_.append("  one.field.a=5i;\n");
        xml_.append("  ExCont two = new ExCont();\n");
        xml_.append("  two.field.a=3i;\n");
        xml_.append("  one.field += two.field;\n");
        xml_.append("  if (one.field.a != 8i){\n");
        xml_.append("   return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEnElDefault();
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        Argument ret_ = new Argument();
        ret_ = calculateArgument("pkg.Ex", id_, args_, cont_);
        assertEq(0, (Number)ret_.getObject());
    }
}
