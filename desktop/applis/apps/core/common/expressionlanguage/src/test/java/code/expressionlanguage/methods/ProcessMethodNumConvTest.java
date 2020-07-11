package code.expressionlanguage.methods;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.ClassField;
import code.expressionlanguage.exec.Classes;
import code.expressionlanguage.functionid.MethodId;
import code.expressionlanguage.structs.IntStruct;
import code.expressionlanguage.structs.Struct;
import code.util.CustList;
import code.util.StringMap;
import org.junit.Test;

import static code.expressionlanguage.EquallableElUtil.assertEq;
import static org.junit.Assert.assertTrue;

public final class ProcessMethodNumConvTest extends ProcessMethodCommon {
    @Test
    public void calculate1Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  String[] arr = new String[1];\n");
        xml_.append("  arr[new ExClass()]=\"Element\";\n");
        xml_.append("  return arr[0];\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field;\n");
        xml_.append(" public static int $(ExClass i){\n");
        xml_.append("  return i.field;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("Element", getString(ret_));
    }
    @Test
    public void calculate2Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  String[] arr = new String[new ExClass()];\n");
        xml_.append("  arr[0]=\"Element\";\n");
        xml_.append("  return arr[0];\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field=1;\n");
        xml_.append(" public static int $(ExClass i){\n");
        xml_.append("  return i.field;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("Element", getString(ret_));
    }
    @Test
    public void calculate3Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  return new ExClass()?\"Vrai\":\"Faux\";\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field=1;\n");
        xml_.append(" public static boolean $(ExClass i){\n");
        xml_.append("  return i.field==1;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("Vrai", getString(ret_));
    }
    @Test
    public void calculate4Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  int i = 5;\n");
        xml_.append("  return new ExClass()&&i == 5?\"Vrai\":\"Faux\";\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field=1;\n");
        xml_.append(" public static boolean $(ExClass i){\n");
        xml_.append("  return i.field==1;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("Vrai", getString(ret_));
    }
    @Test
    public void calculate5Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  int i = 5;\n");
        xml_.append("  return new ExClass()&&i == 5?\"Vrai\":\"Faux\";\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field=2;\n");
        xml_.append(" public static boolean $(ExClass i){\n");
        xml_.append("  return i.field==1;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("Faux", getString(ret_));
    }
    @Test
    public void calculate6Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  int i = 5;\n");
        xml_.append("  return new ExClass()||i == 5?\"Vrai\":\"Faux\";\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field=2;\n");
        xml_.append(" public static boolean $(ExClass i){\n");
        xml_.append("  return i.field==1;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("Vrai", getString(ret_));
    }
    @Test
    public void calculate7Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  int i = 5;\n");
        xml_.append("  return new ExClass()||i == 7?\"Vrai\":\"Faux\";\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field=2;\n");
        xml_.append(" public static boolean $(ExClass i){\n");
        xml_.append("  return i.field==1;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("Faux", getString(ret_));
    }
    @Test
    public void calculate8Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  return !new ExClass()?\"Vrai\":\"Faux\";\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field=2;\n");
        xml_.append(" public static boolean $(ExClass i){\n");
        xml_.append("  return i.field==1;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("Vrai", getString(ret_));
    }
    @Test
    public void calculate9Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  return !new ExClass()?\"Vrai\":\"Faux\";\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field=1;\n");
        xml_.append(" public static boolean $(ExClass i){\n");
        xml_.append("  return i.field==1;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("Faux", getString(ret_));
    }
    @Test
    public void calculate10Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  String str = \"\";\n");
        xml_.append("  iter(int i=new ExClass(1);new ExClass(9);new ExClass(2)){\n");
        xml_.append("   str+=i+\",\";\n");
        xml_.append("  }\n");
        xml_.append("  return str;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field;\n");
        xml_.append(" public ExClass(int p){\n");
        xml_.append("  field=p;\n");
        xml_.append(" }\n");
        xml_.append(" public static int $(ExClass i){\n");
        xml_.append("  return i.field;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("1,3,5,7,", getString(ret_));
    }
    @Test
    public void calculate11Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  String str = \"\";\n");
        xml_.append("  iter(int i=new ExClass(1);new ExClass1(9);new ExClass2(2)){\n");
        xml_.append("   str+=i+\",\";\n");
        xml_.append("  }\n");
        xml_.append("  return str;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field;\n");
        xml_.append(" public ExClass(int p){\n");
        xml_.append("  field=p;\n");
        xml_.append(" }\n");
        xml_.append(" public static int $(ExClass i){\n");
        xml_.append("  return i.field;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass1 {\n");
        xml_.append(" public int field;\n");
        xml_.append(" public ExClass1(int p){\n");
        xml_.append("  field=p;\n");
        xml_.append(" }\n");
        xml_.append(" public static int $(ExClass1 i){\n");
        xml_.append("  return i.field;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass2 {\n");
        xml_.append(" public int field;\n");
        xml_.append(" public ExClass2(int p){\n");
        xml_.append("  field=p;\n");
        xml_.append(" }\n");
        xml_.append(" public static int $(ExClass2 i){\n");
        xml_.append("  return i.field;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("1,3,5,7,", getString(ret_));
    }
    @Test
    public void calculate12Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  if (new ExClass()){\n");
        xml_.append("   return \"Vrai\";\n");
        xml_.append("  }\n");
        xml_.append("  return \"Faux\";\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field=1;\n");
        xml_.append(" public static boolean $(ExClass i){\n");
        xml_.append("  return i.field==1;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("Vrai", getString(ret_));
    }
    @Test
    public void calculate13Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  if (new ExClass()){\n");
        xml_.append("   return \"Vrai\";\n");
        xml_.append("  }\n");
        xml_.append("  return \"Faux\";\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field=2;\n");
        xml_.append(" public static boolean $(ExClass i){\n");
        xml_.append("  return i.field==1;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("Faux", getString(ret_));
    }
    @Test
    public void calculate14Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  String str = \"\";\n");
        xml_.append("  for (ExClass e=new ExClass(1);e;e.field+=2){\n");
        xml_.append("   str += e.field + \",\";\n");
        xml_.append("  }\n");
        xml_.append("  return str;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field;\n");
        xml_.append(" public ExClass(int p){\n");
        xml_.append("  field=p;\n");
        xml_.append(" }\n");
        xml_.append(" public static boolean $(ExClass i){\n");
        xml_.append("  return i.field<9;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("1,3,5,7,", getString(ret_));
    }
    @Test
    public void calculate15Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  String str = \"\";\n");
        xml_.append("  ExClass e=new ExClass(1);\n");
        xml_.append("  while (e){\n");
        xml_.append("   str += e.field + \",\";\n");
        xml_.append("   e.field+=2;\n");
        xml_.append("  }\n");
        xml_.append("  return str;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field;\n");
        xml_.append(" public ExClass(int p){\n");
        xml_.append("  field=p;\n");
        xml_.append(" }\n");
        xml_.append(" public static boolean $(ExClass i){\n");
        xml_.append("  return i.field<9;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("1,3,5,7,", getString(ret_));
    }
    @Test
    public void calculate16Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  String str = \"\";\n");
        xml_.append("  ExClass e=new ExClass(1);\n");
        xml_.append("  do {\n");
        xml_.append("   str += e.field + \",\";\n");
        xml_.append("   e.field+=2;\n");
        xml_.append("  } while (e);\n");
        xml_.append("  return str;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field;\n");
        xml_.append(" public ExClass(int p){\n");
        xml_.append("  field=p;\n");
        xml_.append(" }\n");
        xml_.append(" public static boolean $(ExClass i){\n");
        xml_.append("  return i.field<9;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("1,3,5,7,", getString(ret_));
    }
    @Test
    public void calculate17Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  int e = 1;\n");
        xml_.append("  if (e == 2){\n");
        xml_.append("   return \"\";\n");
        xml_.append("  } else if (new ExClass()){\n");
        xml_.append("   return \"Vrai\";\n");
        xml_.append("  }\n");
        xml_.append("  return \"Faux\";\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field=1;\n");
        xml_.append(" public static boolean $(ExClass i){\n");
        xml_.append("  return i.field==1;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("Vrai", getString(ret_));
    }
    @Test
    public void calculate18Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Apply {\n");
        xml_.append(" public static String method(){\n");
        xml_.append("  int e = 1;\n");
        xml_.append("  if (e == 2){\n");
        xml_.append("   return \"\";\n");
        xml_.append("  } else if (new ExClass()){\n");
        xml_.append("   return \"Vrai\";\n");
        xml_.append("  }\n");
        xml_.append("  return \"Faux\";\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExClass {\n");
        xml_.append(" public int field=2;\n");
        xml_.append(" public static boolean $(ExClass i){\n");
        xml_.append("  return i.field==1;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = contextElReadOnlyDefault();
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("method");
        Argument ret_ = calculateNormal("pkg.Apply", id_, args_, cont_);
        assertEq("Faux", getString(ret_));
    }
}
