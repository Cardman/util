package code.expressionlanguage.methods;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.blocks.ExecFileBlock;
import code.expressionlanguage.functionid.MethodId;
import code.util.CustList;
import code.util.StringMap;
import org.junit.Test;

import static code.expressionlanguage.EquallableElUtil.assertEq;
import static org.junit.Assert.assertTrue;

public final class CoverageReportZTest extends ProcessMethodCommon {

    @Test
    public void coverage399Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("interface pkg.Int {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int extField;\n");
        xml_.append(" static int m(){\n");
        xml_.append("  Int l = new Int(){\n");
        xml_.append("   public int field=++extField;\n");
        xml_.append("   public int field(){\n");
        xml_.append("    return field;\n");
        xml_.append("   }\n");
        xml_.append("  };\n");
        xml_.append("  return l.field();\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">interface <a name=\"m10\">pkg.Int</a> {\n" +
                " int <a name=\"m25\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m42\">pkg.Ext</a> {\n" +
                " static int <span class=\"g\"><a name=\"m64\">extField</a></span>;\n" +
                " static int <a name=\"m86\">m</a>(){\n" +
                "  <a title=\"pkg.Int\" href=\"#m10\">Int</a> <span class=\"f\"><span class=\"f\"><a name=\"m97\">l</a> </span>=<span class=\"f\"> new <a title=\"pkg.Int\" href=\"#m10\">Int</a>()<span class=\"t\"><a name=\"m110\">{</a>\n" +
                "   public int <span class=\"f\"><span class=\"f\"><a name=\"m126\">field</a></span>=<span class=\"f\">++<span class=\"f\"><a title=\"pkg.Ext.extField\" href=\"#m64\">extField</a></span></span></span>;\n" +
                "   public int <a name=\"m158\">field</a>(){\n" +
                "    return <span class=\"f\"><a title=\"pkg.Ext..Int*1.field\" href=\"#m126\">field</a></span>;\n" +
                "   }\n" +
                "  }</span></span></span>;\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m97\">l</a></span>.<span class=\"f\"><a title=\"pkg.Int.field()\" href=\"#m25\">field</a>()</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage400Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static int extField;\n");
        xml_.append(" public int field=++extField;\n");
        xml_.append(" public int field(){\n");
        xml_.append("  return field;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  Int l = new Int(){\n");
        xml_.append("  };\n");
        xml_.append("  return l.field();\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static int <span class=\"g\"><a name=\"m28\">extField</a></span>;\n" +
                " public int <span class=\"f\"><span class=\"f\"><a name=\"m50\">field</a></span>=<span class=\"f\">++<span class=\"f\"><a title=\"pkg.Int.extField\" href=\"#m28\">extField</a></span></span></span>;\n" +
                " public int <a name=\"m80\">field</a>(){\n" +
                "  return <span class=\"f\"><a title=\"pkg.Int.field\" href=\"#m50\">field</a></span>;\n" +
                " }\n" +
                "}\n" +
                "class <a name=\"m116\">pkg.Ext</a> {\n" +
                " static int <a name=\"m138\">m</a>(){\n" +
                "  <a title=\"pkg.Int\" href=\"#m6\">Int</a> <span class=\"f\"><span class=\"f\"><a name=\"m149\">l</a> </span>=<span class=\"f\"> new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m162\">{</a>\n" +
                "  }</span></span></span>;\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m149\">l</a></span>.<span class=\"f\"><a title=\"pkg.Int.field()\" href=\"#m80\">field</a>()</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage401Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("interface pkg.Int {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  Int l = new Int(1){\n");
        xml_.append("   public int field;\n");
        xml_.append("   Int(int p){\n");
        xml_.append("    field=p;\n");
        xml_.append("   }\n");
        xml_.append("   public int field(){\n");
        xml_.append("    return field;\n");
        xml_.append("   }\n");
        xml_.append("  };\n");
        xml_.append("  return l.field();\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">interface <a name=\"m10\">pkg.Int</a> {\n" +
                " int <a name=\"m25\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m42\">pkg.Ext</a> {\n" +
                " static int <a name=\"m64\">m</a>(){\n" +
                "  <a title=\"pkg.Int\" href=\"#m10\">Int</a> <span class=\"f\"><span class=\"f\"><a name=\"m75\">l</a> </span>=<span class=\"f\"> <a title=\"pkg.Ext..Int*1.pkg.Ext..Int*1(int)\" href=\"#m115\">new</a> <a title=\"pkg.Int\" href=\"#m10\">Int</a>(<span class=\"f\">1</span>)<span class=\"t\"><a name=\"m89\">{</a>\n" +
                "   public int <span class=\"f\"><a name=\"m105\">field</a></span>;\n" +
                "   <a name=\"m115\">Int(</a>int <a name=\"m123\">p</a>){\n" +
                "    <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..Int*1.field\" href=\"#m105\">field</a></span>=<span class=\"f\"><a href=\"#m123\">p</a></span></span>;\n" +
                "   }\n" +
                "   public int <a name=\"m159\">field</a>(){\n" +
                "    return <span class=\"f\"><a title=\"pkg.Ext..Int*1.field\" href=\"#m105\">field</a></span>;\n" +
                "   }\n" +
                "  }</span></span></span>;\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m75\">l</a></span>.<span class=\"f\"><a title=\"pkg.Int.field()\" href=\"#m25\">field</a>()</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage402Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" int field = 15;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int extField;\n");
        xml_.append(" static int m(){\n");
        xml_.append("  if (new Int(){\n");
        xml_.append("   public int subfield;\n");
        xml_.append("  }.field == 15)lab{\n");
        xml_.append("   return 1;\n");
        xml_.append("  }\n");
        xml_.append("  return 2;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m21\">field</a> </span>=<span class=\"f\"> 15</span></span>;\n" +
                "}\n" +
                "class <a name=\"m41\">pkg.Ext</a> {\n" +
                " static int <span class=\"g\"><a name=\"m63\">extField</a></span>;\n" +
                " static int <a name=\"m85\">m</a>(){\n" +
                "  <span class=\"p\">if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m105\">{</a>\n" +
                "   public int <span class=\"f\"><a name=\"m121\">subfield</a></span>;\n" +
                "  }</span></span>.<span class=\"f\"><a title=\"pkg.Int.field\" href=\"#m21\">field</a> </span></span><a title=\"true\">==</a><span class=\"f\"> 15</span></span>)<a name=\"m147\">lab</a>{\n" +
                "   return <span class=\"f\">1</span>;\n" +
                "  }\n" +
                "  return <span class=\"n\">2</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage403Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" int field = 15;\n");
        xml_.append(" static boolean true(Int i){\n");
        xml_.append("  return i.field == 15;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int extField;\n");
        xml_.append(" static int m(){\n");
        xml_.append("  if (new Int(){\n");
        xml_.append("   public int subfield;\n");
        xml_.append("  }){\n");
        xml_.append("   return 1;\n");
        xml_.append("  }\n");
        xml_.append("  return 2;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m21\">field</a> </span>=<span class=\"f\"> 15</span></span>;\n" +
                " static boolean <a name=\"m49\">true</a>(<a title=\"pkg.Int\" href=\"#m6\">Int</a> <a name=\"m58\">i</a>){\n" +
                "  return <span class=\"p\"><span class=\"f\"><span class=\"f\"><a href=\"#m58\">i</a></span>.<span class=\"f\"><a title=\"pkg.Int.field\" href=\"#m21\">field</a> </span></span><a title=\"true\">==</a><span class=\"f\"> 15</span></span>;\n" +
                " }\n" +
                "}\n" +
                "class <a name=\"m97\">pkg.Ext</a> {\n" +
                " static int <span class=\"g\"><a name=\"m119\">extField</a></span>;\n" +
                " static int <a name=\"m141\">m</a>(){\n" +
                "  <span class=\"p\">if</span> (<span class=\"p\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m161\">{</a>\n" +
                "   public int <span class=\"f\"><a name=\"m177\">subfield</a></span>;\n" +
                "  }</span></span>)<a title=\"pkg.Int.static true(boolean,pkg.Int)\" href=\"#m49\">{</a>\n" +
                "   return <span class=\"f\">1</span>;\n" +
                "  }\n" +
                "  return <span class=\"n\">2</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage404Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" int field = 15;\n");
        xml_.append(" static boolean true(Int i){\n");
        xml_.append("  return i.field == 15;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int extField;\n");
        xml_.append(" static int m(){\n");
        xml_.append("  if (new Int(){\n");
        xml_.append("   public int subfield;\n");
        xml_.append("  })lab{\n");
        xml_.append("   return 1;\n");
        xml_.append("  }\n");
        xml_.append("  return 2;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m21\">field</a> </span>=<span class=\"f\"> 15</span></span>;\n" +
                " static boolean <a name=\"m49\">true</a>(<a title=\"pkg.Int\" href=\"#m6\">Int</a> <a name=\"m58\">i</a>){\n" +
                "  return <span class=\"p\"><span class=\"f\"><span class=\"f\"><a href=\"#m58\">i</a></span>.<span class=\"f\"><a title=\"pkg.Int.field\" href=\"#m21\">field</a> </span></span><a title=\"true\">==</a><span class=\"f\"> 15</span></span>;\n" +
                " }\n" +
                "}\n" +
                "class <a name=\"m97\">pkg.Ext</a> {\n" +
                " static int <span class=\"g\"><a name=\"m119\">extField</a></span>;\n" +
                " static int <a name=\"m141\">m</a>(){\n" +
                "  <span class=\"p\">if</span> (<span class=\"p\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m161\">{</a>\n" +
                "   public int <span class=\"f\"><a name=\"m177\">subfield</a></span>;\n" +
                "  }</span></span>)<a name=\"m191\">lab</a><a title=\"pkg.Int.static true(boolean,pkg.Int)\" href=\"#m49\">{</a>\n" +
                "   return <span class=\"f\">1</span>;\n" +
                "  }\n" +
                "  return <span class=\"n\">2</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage405Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Init {\n");
        xml_.append(" int field = 2;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Until {\n");
        xml_.append(" int field = 15;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Step {\n");
        xml_.append(" int field = 3;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int extField;\n");
        xml_.append(" static int m(){\n");
        xml_.append("  int sum = 0;\n");
        xml_.append("  iter (int v = new Init(){\n");
        xml_.append("   public int subfield;\n");
        xml_.append("  }.field;new Until(){\n");
        xml_.append("   public int subfield;\n");
        xml_.append("  }.field;new Step(){\n");
        xml_.append("   public int subfield;\n");
        xml_.append("  }.field){\n");
        xml_.append("   sum += v;\n");
        xml_.append("  }\n");
        xml_.append("  return sum;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Init</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m22\">field</a> </span>=<span class=\"f\"> 2</span></span>;\n" +
                "}\n" +
                "class <a name=\"m41\">pkg.Until</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m58\">field</a> </span>=<span class=\"f\"> 15</span></span>;\n" +
                "}\n" +
                "class <a name=\"m78\">pkg.Step</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m94\">field</a> </span>=<span class=\"f\"> 3</span></span>;\n" +
                "}\n" +
                "class <a name=\"m113\">pkg.Ext</a> {\n" +
                " static int <span class=\"g\"><a name=\"m135\">extField</a></span>;\n" +
                " static int <a name=\"m157\">m</a>(){\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m168\">sum</a> </span>=<span class=\"f\"> 0</span></span>;\n" +
                "  <span class=\"f\">iter</span> (int <a name=\"m189\">v</a> = <span class=\"f\"><span class=\"f\">new <a title=\"pkg.Init\" href=\"#m6\">Init</a>()<span class=\"t\"><a name=\"m203\">{</a>\n" +
                "   public int <span class=\"f\"><a name=\"m219\">subfield</a></span>;\n" +
                "  }</span></span>.<span class=\"f\"><a title=\"pkg.Init.field\" href=\"#m22\">field</a></span></span>;<span class=\"f\"><span class=\"f\">new <a title=\"pkg.Until\" href=\"#m41\">Until</a>()<span class=\"t\"><a name=\"m250\">{</a>\n" +
                "   public int <span class=\"f\"><a name=\"m266\">subfield</a></span>;\n" +
                "  }</span></span>.<span class=\"f\"><a title=\"pkg.Until.field\" href=\"#m58\">field</a></span></span>;<span class=\"f\"><span class=\"f\">new <a title=\"pkg.Step\" href=\"#m78\">Step</a>()<span class=\"t\"><a name=\"m296\">{</a>\n" +
                "   public int <span class=\"f\"><a name=\"m312\">subfield</a></span>;\n" +
                "  }</span></span>.<span class=\"f\"><a title=\"pkg.Step.field\" href=\"#m94\">field</a></span></span>){\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m168\">sum</a> </span>+=<span class=\"f\"> <a href=\"#m189\">v</a></span></span>;\n" +
                "  }\n" +
                "  return <span class=\"f\"><a href=\"#m168\">sum</a></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage406Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Init {\n");
        xml_.append(" int field = 2;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Until {\n");
        xml_.append(" int field = 15;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Step {\n");
        xml_.append(" int field = 3;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int extField;\n");
        xml_.append(" static int m(){\n");
        xml_.append("  int sum = 0;\n");
        xml_.append("  iter (int v = new Init(){\n");
        xml_.append("   public int subfield;\n");
        xml_.append("  }.field;new Until(){\n");
        xml_.append("   public int subfield;\n");
        xml_.append("  }.field;new Step(){\n");
        xml_.append("   public int subfield;\n");
        xml_.append("  }.field){\n");
        xml_.append("   sum += v;\n");
        xml_.append("  }\n");
        xml_.append("  iter (int v = new Init(){\n");
        xml_.append("   public int subfield;\n");
        xml_.append("  }.field;new Until(){\n");
        xml_.append("   public int subfield;\n");
        xml_.append("  }.field;new Step(){\n");
        xml_.append("   public int subfield;\n");
        xml_.append("  }.field){\n");
        xml_.append("   sum += v;\n");
        xml_.append("  }\n");
        xml_.append("  return sum;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Init</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m22\">field</a> </span>=<span class=\"f\"> 2</span></span>;\n" +
                "}\n" +
                "class <a name=\"m41\">pkg.Until</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m58\">field</a> </span>=<span class=\"f\"> 15</span></span>;\n" +
                "}\n" +
                "class <a name=\"m78\">pkg.Step</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m94\">field</a> </span>=<span class=\"f\"> 3</span></span>;\n" +
                "}\n" +
                "class <a name=\"m113\">pkg.Ext</a> {\n" +
                " static int <span class=\"g\"><a name=\"m135\">extField</a></span>;\n" +
                " static int <a name=\"m157\">m</a>(){\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m168\">sum</a> </span>=<span class=\"f\"> 0</span></span>;\n" +
                "  <span class=\"f\">iter</span> (int <a name=\"m189\">v</a> = <span class=\"f\"><span class=\"f\">new <a title=\"pkg.Init\" href=\"#m6\">Init</a>()<span class=\"t\"><a name=\"m203\">{</a>\n" +
                "   public int <span class=\"f\"><a name=\"m219\">subfield</a></span>;\n" +
                "  }</span></span>.<span class=\"f\"><a title=\"pkg.Init.field\" href=\"#m22\">field</a></span></span>;<span class=\"f\"><span class=\"f\">new <a title=\"pkg.Until\" href=\"#m41\">Until</a>()<span class=\"t\"><a name=\"m250\">{</a>\n" +
                "   public int <span class=\"f\"><a name=\"m266\">subfield</a></span>;\n" +
                "  }</span></span>.<span class=\"f\"><a title=\"pkg.Until.field\" href=\"#m58\">field</a></span></span>;<span class=\"f\"><span class=\"f\">new <a title=\"pkg.Step\" href=\"#m78\">Step</a>()<span class=\"t\"><a name=\"m296\">{</a>\n" +
                "   public int <span class=\"f\"><a name=\"m312\">subfield</a></span>;\n" +
                "  }</span></span>.<span class=\"f\"><a title=\"pkg.Step.field\" href=\"#m94\">field</a></span></span>){\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m168\">sum</a> </span>+=<span class=\"f\"> <a href=\"#m189\">v</a></span></span>;\n" +
                "  }\n" +
                "  <span class=\"f\">iter</span> (int <a name=\"m363\">v</a> = <span class=\"f\"><span class=\"f\">new <a title=\"pkg.Init\" href=\"#m6\">Init</a>()<span class=\"t\"><a name=\"m377\">{</a>\n" +
                "   public int <span class=\"f\"><a name=\"m393\">subfield</a></span>;\n" +
                "  }</span></span>.<span class=\"f\"><a title=\"pkg.Init.field\" href=\"#m22\">field</a></span></span>;<span class=\"f\"><span class=\"f\">new <a title=\"pkg.Until\" href=\"#m41\">Until</a>()<span class=\"t\"><a name=\"m424\">{</a>\n" +
                "   public int <span class=\"f\"><a name=\"m440\">subfield</a></span>;\n" +
                "  }</span></span>.<span class=\"f\"><a title=\"pkg.Until.field\" href=\"#m58\">field</a></span></span>;<span class=\"f\"><span class=\"f\">new <a title=\"pkg.Step\" href=\"#m78\">Step</a>()<span class=\"t\"><a name=\"m470\">{</a>\n" +
                "   public int <span class=\"f\"><a name=\"m486\">subfield</a></span>;\n" +
                "  }</span></span>.<span class=\"f\"><a title=\"pkg.Step.field\" href=\"#m94\">field</a></span></span>){\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m168\">sum</a> </span>+=<span class=\"f\"> <a href=\"#m363\">v</a></span></span>;\n" +
                "  }\n" +
                "  return <span class=\"f\"><a href=\"#m168\">sum</a></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage407Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Init {\n");
        xml_.append(" int field = 2;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Until {\n");
        xml_.append(" int field = 15;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Step {\n");
        xml_.append(" int field = 3;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int extField;\n");
        xml_.append(" static int m(){\n");
        xml_.append("  int sum = 0;\n");
        xml_.append("  for (int v = new Init(){\n");
        xml_.append("   public int subfield;\n");
        xml_.append("  }.field;v < new Until(){\n");
        xml_.append("   public int subfield;\n");
        xml_.append("  }.field;v += new Step(){\n");
        xml_.append("   public int subfield;\n");
        xml_.append("  }.field){\n");
        xml_.append("   sum += v;\n");
        xml_.append("  }\n");
        xml_.append("  return sum;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Init</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m22\">field</a> </span>=<span class=\"f\"> 2</span></span>;\n" +
                "}\n" +
                "class <a name=\"m41\">pkg.Until</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m58\">field</a> </span>=<span class=\"f\"> 15</span></span>;\n" +
                "}\n" +
                "class <a name=\"m78\">pkg.Step</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m94\">field</a> </span>=<span class=\"f\"> 3</span></span>;\n" +
                "}\n" +
                "class <a name=\"m113\">pkg.Ext</a> {\n" +
                " static int <span class=\"g\"><a name=\"m135\">extField</a></span>;\n" +
                " static int <a name=\"m157\">m</a>(){\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m168\">sum</a> </span>=<span class=\"f\"> 0</span></span>;\n" +
                "  <span class=\"f\">for</span> (int <span class=\"f\"><span class=\"f\"><a name=\"m188\">v</a> </span>=<span class=\"f\"><span class=\"f\"> new <a title=\"pkg.Init\" href=\"#m6\">Init</a>()<span class=\"t\"><a name=\"m202\">{</a>\n" +
                "   public int <span class=\"f\"><a name=\"m218\">subfield</a></span>;\n" +
                "  }</span></span>.<span class=\"f\"><a title=\"pkg.Init.field\" href=\"#m22\">field</a></span></span></span>;<span class=\"f\"><span class=\"f\"><a href=\"#m188\">v</a> </span>&lt;<span class=\"f\"><span class=\"f\"> new <a title=\"pkg.Until\" href=\"#m41\">Until</a>()<span class=\"t\"><a name=\"m253\">{</a>\n" +
                "   public int <span class=\"f\"><a name=\"m269\">subfield</a></span>;\n" +
                "  }</span></span>.<span class=\"f\"><a title=\"pkg.Until.field\" href=\"#m58\">field</a></span></span></span>;<span class=\"f\"><span class=\"f\"><a href=\"#m188\">v</a> </span>+=<span class=\"f\"><span class=\"f\"> new <a title=\"pkg.Step\" href=\"#m78\">Step</a>()<span class=\"t\"><a name=\"m304\">{</a>\n" +
                "   public int <span class=\"f\"><a name=\"m320\">subfield</a></span>;\n" +
                "  }</span></span>.<span class=\"f\"><a title=\"pkg.Step.field\" href=\"#m94\">field</a></span></span></span>){\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m168\">sum</a> </span>+=<span class=\"f\"> <a href=\"#m188\">v</a></span></span>;\n" +
                "  }\n" +
                "  return <span class=\"f\"><a href=\"#m168\">sum</a></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage408Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("interface pkg.Int {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("enum pkg.Ext {\n");
        xml_.append(" ONE(new Int(1){\n");
        xml_.append("  static int extField;\n");
        xml_.append("  public int field;\n");
        xml_.append("  public Int(int p){\n");
        xml_.append("   field = p;\n");
        xml_.append("  }\n");
        xml_.append("  public int field(){\n");
        xml_.append("   return field;\n");
        xml_.append("  }\n");
        xml_.append(" }){\n");
        xml_.append("  ONE(Int p){\n");
        xml_.append("   super(p);\n");
        xml_.append("  }\n");
        xml_.append(" };\n");
        xml_.append(" Int inner;\n");
        xml_.append(" Ext(Int p){\n");
        xml_.append("  inner = p;\n");
        xml_.append(" }\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return ONE.inner.field();\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">interface <a name=\"m10\">pkg.Int</a> {\n" +
                " int <a name=\"m25\">field</a>();\n" +
                "}\n" +
                "enum <a name=\"m41\">pkg.Ext</a> {\n" +
                " <a name=\"m52\" title=\"pkg.Ext-ONE.pkg.Ext-ONE(pkg.Int)\" href=\"#m200\">ONE</a>(<span class=\"g\"><a title=\"pkg.Ext..Int*1.pkg.Ext..Int*1(int)\" href=\"#m113\">new</a> <a title=\"pkg.Int\" href=\"#m10\">Int</a>(<span class=\"g\">1</span>)<span class=\"t\"><a name=\"m66\">{</a>\n" +
                "  static int <span class=\"g\"><a name=\"m81\">extField</a></span>;\n" +
                "  public int <span class=\"g\"><a name=\"m104\">field</a></span>;\n" +
                "  <a name=\"m113\">public Int(</a>int <a name=\"m128\">p</a>){\n" +
                "   <span class=\"g\"><span class=\"g\"><a title=\"pkg.Ext..Int*1.field\" href=\"#m104\">field</a> </span>=<span class=\"g\"> <a href=\"#m128\">p</a></span></span>;\n" +
                "  }\n" +
                "  public int <a name=\"m163\">field</a>(){\n" +
                "   return <span class=\"f\"><a title=\"pkg.Ext..Int*1.field\" href=\"#m104\">field</a></span>;\n" +
                "  }\n" +
                " }</span></span>){\n" +
                "  <a name=\"m200\">ONE(</a><a title=\"pkg.Int\" href=\"#m10\">Int</a> <a name=\"m208\">p</a>){\n" +
                "   <span class=\"g\"><a title=\"pkg.Ext.pkg.Ext(pkg.Int)\" href=\"#m246\">super</a>(<span class=\"g\"><a href=\"#m208\">p</a></span>)</span>;\n" +
                "  }\n" +
                " };\n" +
                " <a title=\"pkg.Int\" href=\"#m10\">Int</a> <span class=\"g\"><a name=\"m238\">inner</a></span>;\n" +
                " <a name=\"m246\">Ext(</a><a title=\"pkg.Int\" href=\"#m10\">Int</a> <a name=\"m254\">p</a>){\n" +
                "  <span class=\"g\"><span class=\"g\"><a title=\"pkg.Ext.inner\" href=\"#m238\">inner</a> </span>=<span class=\"g\"> <a href=\"#m254\">p</a></span></span>;\n" +
                " }\n" +
                " static int <a name=\"m286\">m</a>(){\n" +
                "  return <span class=\"f\"><span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext.ONE\" href=\"#m52\">ONE</a></span>.<span class=\"f\"><a title=\"pkg.Ext.inner\" href=\"#m238\">inner</a></span></span>.<span class=\"f\"><a title=\"pkg.Int.field()\" href=\"#m25\">field</a>()</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage409Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("interface pkg.Int {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("enum pkg.Ext {\n");
        xml_.append(" ONE(new Int(1){\n");
        xml_.append("  static int extField;\n");
        xml_.append("  public int field;\n");
        xml_.append("  public Int(int p){\n");
        xml_.append("   field = p;\n");
        xml_.append("  }\n");
        xml_.append("  public int field(){\n");
        xml_.append("   return field;\n");
        xml_.append("  }\n");
        xml_.append(" });\n");
        xml_.append(" Int inner;\n");
        xml_.append(" Ext(Int p){\n");
        xml_.append("  inner = p;\n");
        xml_.append(" }\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return ONE.inner.field();\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">interface <a name=\"m10\">pkg.Int</a> {\n" +
                " int <a name=\"m25\">field</a>();\n" +
                "}\n" +
                "enum <a name=\"m41\">pkg.Ext</a> {\n" +
                " <a name=\"m52\" title=\"pkg.Ext.pkg.Ext(pkg.Int)\" href=\"#m211\">ONE</a>(<span class=\"g\"><a title=\"pkg.Ext..Int*1.pkg.Ext..Int*1(int)\" href=\"#m113\">new</a> <a title=\"pkg.Int\" href=\"#m10\">Int</a>(<span class=\"g\">1</span>)<span class=\"t\"><a name=\"m66\">{</a>\n" +
                "  static int <span class=\"g\"><a name=\"m81\">extField</a></span>;\n" +
                "  public int <span class=\"g\"><a name=\"m104\">field</a></span>;\n" +
                "  <a name=\"m113\">public Int(</a>int <a name=\"m128\">p</a>){\n" +
                "   <span class=\"g\"><span class=\"g\"><a title=\"pkg.Ext..Int*1.field\" href=\"#m104\">field</a> </span>=<span class=\"g\"> <a href=\"#m128\">p</a></span></span>;\n" +
                "  }\n" +
                "  public int <a name=\"m163\">field</a>(){\n" +
                "   return <span class=\"f\"><a title=\"pkg.Ext..Int*1.field\" href=\"#m104\">field</a></span>;\n" +
                "  }\n" +
                " }</span></span>);\n" +
                " <a title=\"pkg.Int\" href=\"#m10\">Int</a> <span class=\"g\"><a name=\"m203\">inner</a></span>;\n" +
                " <a name=\"m211\">Ext(</a><a title=\"pkg.Int\" href=\"#m10\">Int</a> <a name=\"m219\">p</a>){\n" +
                "  <span class=\"g\"><span class=\"g\"><a title=\"pkg.Ext.inner\" href=\"#m203\">inner</a> </span>=<span class=\"g\"> <a href=\"#m219\">p</a></span></span>;\n" +
                " }\n" +
                " static int <a name=\"m251\">m</a>(){\n" +
                "  return <span class=\"f\"><span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext.ONE\" href=\"#m52\">ONE</a></span>.<span class=\"f\"><a title=\"pkg.Ext.inner\" href=\"#m203\">inner</a></span></span>.<span class=\"f\"><a title=\"pkg.Int.field()\" href=\"#m25\">field</a>()</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage410Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("interface pkg.Int {\n");
        xml_.append(" static int stfield;\n");
        xml_.append(" int field();\n");
        xml_.append(" static {\n");
        xml_.append("  stfield++;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  Int l = new interfaces(Int) Int(){\n");
        xml_.append("   public int field=++stfield;\n");
        xml_.append("   public int field(){\n");
        xml_.append("    return field;\n");
        xml_.append("   }\n");
        xml_.append("  };\n");
        xml_.append("  return l.field();\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">interface <a name=\"m10\">pkg.Int</a> {\n" +
                " static int <span class=\"g\"><a name=\"m32\">stfield</a></span>;\n" +
                " int <a name=\"m46\">field</a>();\n" +
                " static {\n" +
                "  <span class=\"g\"><span class=\"g\"><a title=\"pkg.Int.stfield\" href=\"#m32\">stfield</a></span>++</span>;\n" +
                " }\n" +
                "}\n" +
                "class <a name=\"m89\">pkg.Ext</a> {\n" +
                " static int <a name=\"m111\">m</a>(){\n" +
                "  <a title=\"pkg.Int\" href=\"#m10\">Int</a> <span class=\"f\"><span class=\"f\"><a name=\"m122\">l</a> </span>=<span class=\"f\"> new interfaces(<a title=\"pkg.Int\" href=\"#m10\">Int</a>) <a title=\"pkg.Int\" href=\"#m10\">Int</a>()<span class=\"t\"><a name=\"m151\">{</a>\n" +
                "   public int <span class=\"f\"><span class=\"f\"><a name=\"m167\">field</a></span>=<span class=\"f\">++<span class=\"f\"><a title=\"pkg.Int.stfield\" href=\"#m32\">stfield</a></span></span></span>;\n" +
                "   public int <a name=\"m198\">field</a>(){\n" +
                "    return <span class=\"f\"><a title=\"pkg.Ext..Int*1.field\" href=\"#m167\">field</a></span>;\n" +
                "   }\n" +
                "  }</span></span></span>;\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m122\">l</a></span>.<span class=\"f\"><a title=\"pkg.Int.field()\" href=\"#m46\">field</a>()</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage411Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("interface pkg.Int {\n");
        xml_.append(" static int stfield;\n");
        xml_.append(" int field();\n");
        xml_.append(" static {\n");
        xml_.append("  stfield++;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  Int l = new {} interfaces(Int) Int(){\n");
        xml_.append("   public int field=++stfield;\n");
        xml_.append("   public int field(){\n");
        xml_.append("    return field;\n");
        xml_.append("   }\n");
        xml_.append("  };\n");
        xml_.append("  return l.field();\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">interface <a name=\"m10\">pkg.Int</a> {\n" +
                " static int <span class=\"g\"><a name=\"m32\">stfield</a></span>;\n" +
                " int <a name=\"m46\">field</a>();\n" +
                " static {\n" +
                "  <span class=\"g\"><span class=\"g\"><a title=\"pkg.Int.stfield\" href=\"#m32\">stfield</a></span>++</span>;\n" +
                " }\n" +
                "}\n" +
                "class <a name=\"m89\">pkg.Ext</a> {\n" +
                " static int <a name=\"m111\">m</a>(){\n" +
                "  <a title=\"pkg.Int\" href=\"#m10\">Int</a> <span class=\"f\"><span class=\"f\"><a name=\"m122\">l</a> </span>=<span class=\"f\"> new {} interfaces(<a title=\"pkg.Int\" href=\"#m10\">Int</a>) <a title=\"pkg.Int\" href=\"#m10\">Int</a>()<span class=\"t\"><a name=\"m154\">{</a>\n" +
                "   public int <span class=\"f\"><span class=\"f\"><a name=\"m170\">field</a></span>=<span class=\"f\">++<span class=\"f\"><a title=\"pkg.Int.stfield\" href=\"#m32\">stfield</a></span></span></span>;\n" +
                "   public int <a name=\"m201\">field</a>(){\n" +
                "    return <span class=\"f\"><a title=\"pkg.Ext..Int*1.field\" href=\"#m170\">field</a></span>;\n" +
                "   }\n" +
                "  }</span></span></span>;\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m122\">l</a></span>.<span class=\"f\"><a title=\"pkg.Int.field()\" href=\"#m46\">field</a>()</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage412Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" int field=1;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static String m(){\n");
        xml_.append("  String res = \"\";\n");
        xml_.append("  int i = 0;\n");
        xml_.append("  while (i < new Int(){}.field){\n");
        xml_.append("   res+=',';\n");
        xml_.append("   i++;\n");
        xml_.append("  }\n");
        xml_.append("  return res;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m21\">field</a></span>=<span class=\"f\">1</span></span>;\n" +
                "}\n" +
                "class <a name=\"m38\">pkg.Ext</a> {\n" +
                " static String <a name=\"m63\">m</a>(){\n" +
                "  String <span class=\"f\"><span class=\"f\"><a name=\"m77\">res</a> </span>=<span class=\"f\"> <span class=\"s\">\"\"</span></span></span>;\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m93\">i</a> </span>=<span class=\"f\"> 0</span></span>;\n" +
                "  <span class=\"f\">while</span> (<span class=\"f\"><span class=\"f\"><a href=\"#m93\">i</a> </span>&lt;<span class=\"f\"><span class=\"f\"> new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m122\">{</a>}</span></span>.<span class=\"f\"><a title=\"pkg.Int.field\" href=\"#m21\">field</a></span></span></span>){\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m77\">res</a></span>+=<span class=\"f\"><span class=\"s\">','</span></span></span>;\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m93\">i</a></span>++</span>;\n" +
                "  }\n" +
                "  return <span class=\"f\"><a href=\"#m77\">res</a></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage413Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" int field=1;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static String m(){\n");
        xml_.append("  String res = \"\";\n");
        xml_.append("  int i = 0;\n");
        xml_.append("  if (i < 0){\n");
        xml_.append("  } else if (i < new Int(){}.field){\n");
        xml_.append("   res+=',';\n");
        xml_.append("   i++;\n");
        xml_.append("  }\n");
        xml_.append("  return res;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m21\">field</a></span>=<span class=\"f\">1</span></span>;\n" +
                "}\n" +
                "class <a name=\"m38\">pkg.Ext</a> {\n" +
                " static String <a name=\"m63\">m</a>(){\n" +
                "  String <span class=\"f\"><span class=\"f\"><a name=\"m77\">res</a> </span>=<span class=\"f\"> <span class=\"s\">\"\"</span></span></span>;\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m93\">i</a> </span>=<span class=\"f\"> 0</span></span>;\n" +
                "  <span class=\"p\">if</span> (<span class=\"p\"><span class=\"f\"><a href=\"#m93\">i</a> </span><a title=\"false\">&lt;</a><span class=\"f\"> 0</span></span>){\n" +
                "  } <span class=\"p\">else if</span> (<span class=\"p\"><span class=\"f\"><a href=\"#m93\">i</a> </span><a title=\"true\">&lt;</a><span class=\"f\"><span class=\"f\"> new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m140\">{</a>}</span></span>.<span class=\"f\"><a title=\"pkg.Int.field\" href=\"#m21\">field</a></span></span></span>){\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m77\">res</a></span>+=<span class=\"f\"><span class=\"s\">','</span></span></span>;\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m93\">i</a></span>++</span>;\n" +
                "  }\n" +
                "  return <span class=\"f\"><a href=\"#m77\">res</a></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage414Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("interface pkg.Int {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static Int inner = new Int(){\n");
        xml_.append("  static int extField;\n");
        xml_.append("  public int field=1;\n");
        xml_.append("  public int field(){\n");
        xml_.append("   return field;\n");
        xml_.append("  }\n");
        xml_.append(" };\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return inner.field();\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">interface <a name=\"m10\">pkg.Int</a> {\n" +
                " int <a name=\"m25\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m42\">pkg.Ext</a> {\n" +
                " static <a title=\"pkg.Int\" href=\"#m10\">Int</a> <span class=\"g\"><span class=\"g\"><a name=\"m64\">inner</a> </span>=<span class=\"g\"> new <a title=\"pkg.Int\" href=\"#m10\">Int</a>()<span class=\"t\"><a name=\"m81\">{</a>\n" +
                "  static int <span class=\"g\"><a name=\"m96\">extField</a></span>;\n" +
                "  public int <span class=\"g\"><span class=\"g\"><a name=\"m119\">field</a></span>=<span class=\"g\">1</span></span>;\n" +
                "  public int <a name=\"m141\">field</a>(){\n" +
                "   return <span class=\"f\"><a title=\"pkg.Ext..Int*1.field\" href=\"#m119\">field</a></span>;\n" +
                "  }\n" +
                " }</span></span></span>;\n" +
                " static int <a name=\"m187\">m</a>(){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext.inner\" href=\"#m64\">inner</a></span>.<span class=\"f\"><a title=\"pkg.Int.field()\" href=\"#m25\">field</a>()</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage415Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" int field=1;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static String m(){\n");
        xml_.append("  String res = \"\";\n");
        xml_.append("  int i = 0;\n");
        xml_.append("  switch (new Int(){}.field){\n");
        xml_.append("   case 1:\n");
        xml_.append("   res+=',';\n");
        xml_.append("   i++;\n");
        xml_.append("  }\n");
        xml_.append("  return res;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m21\">field</a></span>=<span class=\"f\">1</span></span>;\n" +
                "}\n" +
                "class <a name=\"m38\">pkg.Ext</a> {\n" +
                " static String <a name=\"m63\">m</a>(){\n" +
                "  String <span class=\"f\"><span class=\"f\"><a name=\"m77\">res</a> </span>=<span class=\"f\"> <span class=\"s\">\"\"</span></span></span>;\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m93\">i</a> </span>=<span class=\"f\"> 0</span></span>;\n" +
                "  <span class=\"p\"><a title=\"1/2\">switch</a></span> (<span class=\"f\"><span class=\"f\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m119\">{</a>}</span></span>.<span class=\"f\"><a title=\"pkg.Int.field\" href=\"#m21\">field</a></span></span>){\n" +
                "   case <span class=\"f\">1</span>:\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m77\">res</a></span>+=<span class=\"f\"><span class=\"s\">','</span></span></span>;\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m93\">i</a></span>++</span>;\n" +
                "  }\n" +
                "  return <span class=\"f\"><a href=\"#m77\">res</a></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage416Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" int field=1;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static String m(){\n");
        xml_.append("  String res = \"\";\n");
        xml_.append("  int i = 0;\n");
        xml_.append("  do {\n");
        xml_.append("   res+=',';\n");
        xml_.append("   i++;\n");
        xml_.append("  }\n");
        xml_.append("  while (i < new Int(){}.field);\n");
        xml_.append("  return res;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m21\">field</a></span>=<span class=\"f\">1</span></span>;\n" +
                "}\n" +
                "class <a name=\"m38\">pkg.Ext</a> {\n" +
                " static String <a name=\"m63\">m</a>(){\n" +
                "  String <span class=\"f\"><span class=\"f\"><a name=\"m77\">res</a> </span>=<span class=\"f\"> <span class=\"s\">\"\"</span></span></span>;\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m93\">i</a> </span>=<span class=\"f\"> 0</span></span>;\n" +
                "  do {\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m77\">res</a></span>+=<span class=\"f\"><span class=\"s\">','</span></span></span>;\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m93\">i</a></span>++</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">while</span> (<span class=\"p\"><span class=\"f\"><a href=\"#m93\">i</a> </span><a title=\"false\">&lt;</a><span class=\"f\"><span class=\"f\"> new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m154\">{</a>}</span></span>.<span class=\"f\"><a title=\"pkg.Int.field\" href=\"#m21\">field</a></span></span></span>);\n" +
                "  return <span class=\"f\"><a href=\"#m77\">res</a></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage417Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" int field=1;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static String m(){\n");
        xml_.append("  String res = \"\";\n");
        xml_.append("  for (int i: new Iterable<int>(1,2){\n");
        xml_.append("   int[] f;\n");
        xml_.append("   Iterable(int... a){\n");
        xml_.append("    f = a;\n");
        xml_.append("   }\n");
        xml_.append("   public Iterator<int> iterator(){\n");
        xml_.append("    return new Iterator<int>(f){\n");
        xml_.append("     int[] g;\n");
        xml_.append("     int j;\n");
        xml_.append("     Iterator(int... a){\n");
        xml_.append("      g = a;\n");
        xml_.append("     }\n");
        xml_.append("     public boolean hasNext(){\n");
        xml_.append("      return j < g.length;\n");
        xml_.append("     }\n");
        xml_.append("     public int next(){\n");
        xml_.append("      return g[j++];\n");
        xml_.append("     }\n");
        xml_.append("    };\n");
        xml_.append("   }\n");
        xml_.append("  }) {\n");
        xml_.append("   res+=i+\",\";\n");
        xml_.append("  }\n");
        xml_.append("  return res;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " int <span class=\"n\"><span class=\"n\"><a name=\"m21\">field</a></span>=<span class=\"n\">1</span></span>;\n" +
                "}\n" +
                "class <a name=\"m38\">pkg.Ext</a> {\n" +
                " static String <a name=\"m63\">m</a>(){\n" +
                "  String <span class=\"f\"><span class=\"f\"><a name=\"m77\">res</a> </span>=<span class=\"f\"> <span class=\"s\">\"\"</span></span></span>;\n" +
                "  <span class=\"f\">for (int <a name=\"m98\">i</a></span>: <span class=\"f\"><a title=\"pkg.Ext..Iterable*1.pkg.Ext..Iterable*1(int...)\" href=\"#m140\">new</a> Iterable&lt;int&gt;(<span class=\"f\">1</span>,<span class=\"f\">2</span>)<span class=\"t\"><a name=\"m123\">{</a>\n" +
                "   int[] <span class=\"f\"><a name=\"m134\">f</a></span>;\n" +
                "   <a name=\"m140\">Iterable(</a>int... <a name=\"m156\">a</a>){\n" +
                "    <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..Iterable*1.f\" href=\"#m134\">f</a> </span>=<span class=\"f\"> <a href=\"#m156\">a</a></span></span>;\n" +
                "   }\n" +
                "   public Iterator&lt;int&gt; <a name=\"m200\">iterator</a>(){\n" +
                "    return <span class=\"f\"><a title=\"pkg.Ext..Iterable*1..Iterator*1.pkg.Ext..Iterable*1..Iterator*1(int...)\" href=\"#m276\">new</a> Iterator&lt;int&gt;(<span class=\"f\"><a title=\"pkg.Ext..Iterable*1.f\" href=\"#m134\">f</a></span>)<span class=\"t\"><a name=\"m243\">{</a>\n" +
                "     int[] <span class=\"f\"><a name=\"m256\">g</a></span>;\n" +
                "     int <span class=\"f\"><a name=\"m268\">j</a></span>;\n" +
                "     <a name=\"m276\">Iterator(</a>int... <a name=\"m292\">a</a>){\n" +
                "      <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..Iterable*1..Iterator*1.g\" href=\"#m256\">g</a> </span>=<span class=\"f\"> <a href=\"#m292\">a</a></span></span>;\n" +
                "     }\n" +
                "     public boolean <a name=\"m336\">hasNext</a>(){\n" +
                "      return <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..Iterable*1..Iterator*1.j\" href=\"#m268\">j</a> </span>&lt;<span class=\"f\"><span class=\"f\"> <a title=\"pkg.Ext..Iterable*1..Iterator*1.g\" href=\"#m256\">g</a></span>.<span class=\"f\"><b>length</b></span></span></span>;\n" +
                "     }\n" +
                "     public int <a name=\"m397\">next</a>(){\n" +
                "      return <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..Iterable*1..Iterator*1.g\" href=\"#m256\">g</a></span><span class=\"f\">[<span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..Iterable*1..Iterator*1.j\" href=\"#m268\">j</a></span>++</span>]</span></span>;\n" +
                "     }\n" +
                "    }</span></span>;\n" +
                "   }\n" +
                "  }</span></span>) {\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m77\">res</a></span>+=<span class=\"f\"><span class=\"f\"><a href=\"#m98\">i</a></span>+<span class=\"f\"><span class=\"s\">\",\"</span></span></span></span>;\n" +
                "  }\n" +
                "  return <span class=\"f\"><a href=\"#m77\">res</a></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage418Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" int field=1;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static String m(){\n");
        xml_.append("  String res = \"\";\n");
        xml_.append("  for (int i, int y: new IterableTable<int,int>(new int[]{1,2},new int[]{3,4}){\n");
        xml_.append("   int[] e;\n");
        xml_.append("   int[] f;\n");
        xml_.append("   IterableTable(int[] b,int[] a){\n");
        xml_.append("    e = b;\n");
        xml_.append("    f = a;\n");
        xml_.append("   }\n");
        xml_.append("   public IteratorTable<int,int> iteratorTable(){\n");
        xml_.append("    return new IteratorTable<int,int>(e,f){\n");
        xml_.append("     int[] g;\n");
        xml_.append("     int[] h;\n");
        xml_.append("     int j;\n");
        xml_.append("     IteratorTable(int[] b,int[] a){\n");
        xml_.append("      g = b;\n");
        xml_.append("      h = a;\n");
        xml_.append("     }\n");
        xml_.append("     public boolean hasNextPair(){\n");
        xml_.append("      return j < g.length;\n");
        xml_.append("     }\n");
        xml_.append("     public Pair<int,int> nextPair(){\n");
        xml_.append("      return new Pair<int,int>(g,h,j++){\n");
        xml_.append("       int[] k;\n");
        xml_.append("       int[] l;\n");
        xml_.append("       int m;\n");
        xml_.append("       Pair(int[] b,int[] a, int z){\n");
        xml_.append("        k = b;\n");
        xml_.append("        l = a;\n");
        xml_.append("        m = z;\n");
        xml_.append("       }\n");
        xml_.append("       public int getFirst(){\n");
        xml_.append("        return k[m];\n");
        xml_.append("       }\n");
        xml_.append("       public int getSecond(){\n");
        xml_.append("        return l[m];\n");
        xml_.append("       }\n");
        xml_.append("      };\n");
        xml_.append("     }\n");
        xml_.append("    };\n");
        xml_.append("   }\n");
        xml_.append("  }) {\n");
        xml_.append("   res+=y+\",\"+i+\";\";\n");
        xml_.append("  }\n");
        xml_.append("  return res;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " int <span class=\"n\"><span class=\"n\"><a name=\"m21\">field</a></span>=<span class=\"n\">1</span></span>;\n" +
                "}\n" +
                "class <a name=\"m38\">pkg.Ext</a> {\n" +
                " static String <a name=\"m63\">m</a>(){\n" +
                "  String <span class=\"f\"><span class=\"f\"><a name=\"m77\">res</a> </span>=<span class=\"f\"> <span class=\"s\">\"\"</span></span></span>;\n" +
                "  <span class=\"f\">for (int <a name=\"m98\">i</a>, int <a name=\"m105\">y</a></span>: <span class=\"f\"><a title=\"pkg.Ext..IterableTable*1.pkg.Ext..IterableTable*1([int,[int)\" href=\"#m194\">new</a> IterableTable&lt;int,int&gt;(<span class=\"f\">new int[]{<span class=\"f\">1</span>,<span class=\"f\">2</span>}</span>,<span class=\"f\">new int[]{<span class=\"f\">3</span>,<span class=\"f\">4</span>}</span>)<span class=\"t\"><a name=\"m165\">{</a>\n" +
                "   int[] <span class=\"f\"><a name=\"m176\">e</a></span>;\n" +
                "   int[] <span class=\"f\"><a name=\"m188\">f</a></span>;\n" +
                "   <a name=\"m194\">IterableTable(</a>int[] <a name=\"m214\">b</a>,int[] <a name=\"m222\">a</a>){\n" +
                "    <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..IterableTable*1.e\" href=\"#m176\">e</a> </span>=<span class=\"f\"> <a href=\"#m214\">b</a></span></span>;\n" +
                "    <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..IterableTable*1.f\" href=\"#m188\">f</a> </span>=<span class=\"f\"> <a href=\"#m222\">a</a></span></span>;\n" +
                "   }\n" +
                "   public IteratorTable&lt;int,int&gt; <a name=\"m286\">iteratorTable</a>(){\n" +
                "    return <span class=\"f\"><a title=\"pkg.Ext..IterableTable*1..IteratorTable*1.pkg.Ext..IterableTable*1..IteratorTable*1([int,[int)\" href=\"#m392\">new</a> IteratorTable&lt;int,int&gt;(<span class=\"f\"><a title=\"pkg.Ext..IterableTable*1.e\" href=\"#m176\">e</a></span>,<span class=\"f\"><a title=\"pkg.Ext..IterableTable*1.f\" href=\"#m188\">f</a></span>)<span class=\"t\"><a name=\"m345\">{</a>\n" +
                "     int[] <span class=\"f\"><a name=\"m358\">g</a></span>;\n" +
                "     int[] <span class=\"f\"><a name=\"m372\">h</a></span>;\n" +
                "     int <span class=\"f\"><a name=\"m384\">j</a></span>;\n" +
                "     <a name=\"m392\">IteratorTable(</a>int[] <a name=\"m412\">b</a>,int[] <a name=\"m420\">a</a>){\n" +
                "      <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..IterableTable*1..IteratorTable*1.g\" href=\"#m358\">g</a> </span>=<span class=\"f\"> <a href=\"#m412\">b</a></span></span>;\n" +
                "      <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..IterableTable*1..IteratorTable*1.h\" href=\"#m372\">h</a> </span>=<span class=\"f\"> <a href=\"#m420\">a</a></span></span>;\n" +
                "     }\n" +
                "     public boolean <a name=\"m477\">hasNextPair</a>(){\n" +
                "      return <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..IterableTable*1..IteratorTable*1.j\" href=\"#m384\">j</a> </span>&lt;<span class=\"f\"><span class=\"f\"> <a title=\"pkg.Ext..IterableTable*1..IteratorTable*1.g\" href=\"#m358\">g</a></span>.<span class=\"f\"><b>length</b></span></span></span>;\n" +
                "     }\n" +
                "     public Pair&lt;int,int&gt; <a name=\"m552\">nextPair</a>(){\n" +
                "      return <span class=\"f\"><a title=\"pkg.Ext..IterableTable*1..IteratorTable*1..Pair*1.pkg.Ext..IterableTable*1..IteratorTable*1..Pair*1([int,[int,int)\" href=\"#m658\">new</a> Pair&lt;int,int&gt;(<span class=\"f\"><a title=\"pkg.Ext..IterableTable*1..IteratorTable*1.g\" href=\"#m358\">g</a></span>,<span class=\"f\"><a title=\"pkg.Ext..IterableTable*1..IteratorTable*1.h\" href=\"#m372\">h</a></span>,<span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..IterableTable*1..IteratorTable*1.j\" href=\"#m384\">j</a></span>++</span>)<span class=\"t\"><a name=\"m603\">{</a>\n" +
                "       int[] <span class=\"f\"><a name=\"m618\">k</a></span>;\n" +
                "       int[] <span class=\"f\"><a name=\"m634\">l</a></span>;\n" +
                "       int <span class=\"f\"><a name=\"m648\">m</a></span>;\n" +
                "       <a name=\"m658\">Pair(</a>int[] <a name=\"m669\">b</a>,int[] <a name=\"m677\">a</a>, int <a name=\"m684\">z</a>){\n" +
                "        <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..IterableTable*1..IteratorTable*1..Pair*1.k\" href=\"#m618\">k</a> </span>=<span class=\"f\"> <a href=\"#m669\">b</a></span></span>;\n" +
                "        <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..IterableTable*1..IteratorTable*1..Pair*1.l\" href=\"#m634\">l</a> </span>=<span class=\"f\"> <a href=\"#m677\">a</a></span></span>;\n" +
                "        <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..IterableTable*1..IteratorTable*1..Pair*1.m\" href=\"#m648\">m</a> </span>=<span class=\"f\"> <a href=\"#m684\">z</a></span></span>;\n" +
                "       }\n" +
                "       public int <a name=\"m760\">getFirst</a>(){\n" +
                "        return <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..IterableTable*1..IteratorTable*1..Pair*1.k\" href=\"#m618\">k</a></span><span class=\"f\">[<span class=\"f\"><a title=\"pkg.Ext..IterableTable*1..IteratorTable*1..Pair*1.m\" href=\"#m648\">m</a></span>]</span></span>;\n" +
                "       }\n" +
                "       public int <a name=\"m820\">getSecond</a>(){\n" +
                "        return <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..IterableTable*1..IteratorTable*1..Pair*1.l\" href=\"#m634\">l</a></span><span class=\"f\">[<span class=\"f\"><a title=\"pkg.Ext..IterableTable*1..IteratorTable*1..Pair*1.m\" href=\"#m648\">m</a></span>]</span></span>;\n" +
                "       }\n" +
                "      }</span></span>;\n" +
                "     }\n" +
                "    }</span></span>;\n" +
                "   }\n" +
                "  }</span></span>) {\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m77\">res</a></span>+=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m105\">y</a></span>+<span class=\"f\"><span class=\"s\">\",\"</span></span></span>+<span class=\"f\"><a href=\"#m98\">i</a></span></span>+<span class=\"f\"><span class=\"s\">\";\"</span></span></span></span>;\n" +
                "  }\n" +
                "  return <span class=\"f\"><a href=\"#m77\">res</a></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage419Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("interface pkg.Int {\n");
        xml_.append(" String field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static String m(){\n");
        xml_.append("  Int[] a = {new Int(1){\n");
        xml_.append("   public String field;\n");
        xml_.append("   Int(int p){\n");
        xml_.append("    field=\"one\\\"escape\"+p;\n");
        xml_.append("   }\n");
        xml_.append("   public String field(){\n");
        xml_.append("    return field+\"endone\";\n");
        xml_.append("   }\n");
        xml_.append("  },new Int(2,3){\n");
        xml_.append("   public String field;\n");
        xml_.append("   Int(int p, int q){\n");
        xml_.append("    field=\"two\\\"escape\"+p+';'+q;\n");
        xml_.append("   }\n");
        xml_.append("   public String field(){\n");
        xml_.append("    return field+\"endtwo\";\n");
        xml_.append("   }\n");
        xml_.append("  }};\n");
        xml_.append("  String res = \"\";\n");
        xml_.append("  int i = 0;\n");
        xml_.append("  while (i < 2){\n");
        xml_.append("   res+=a[i].field()+',';\n");
        xml_.append("   i++;\n");
        xml_.append("  }\n");
        xml_.append("  return res;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">interface <a name=\"m10\">pkg.Int</a> {\n" +
                " String <a name=\"m28\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m45\">pkg.Ext</a> {\n" +
                " static String <a name=\"m70\">m</a>(){\n" +
                "  <a title=\"pkg.Int\" href=\"#m10\">Int</a>[] <span class=\"f\"><span class=\"f\"><a name=\"m83\">a</a> </span>=<span class=\"f\"> {<span class=\"f\"><a title=\"pkg.Ext..Int*1.pkg.Ext..Int*1(int)\" href=\"#m127\">new</a> <a title=\"pkg.Int\" href=\"#m10\">Int</a>(<span class=\"f\">1</span>)<span class=\"t\"><a name=\"m98\">{</a>\n" +
                "   public String <span class=\"f\"><a name=\"m117\">field</a></span>;\n" +
                "   <a name=\"m127\">Int(</a>int <a name=\"m135\">p</a>){\n" +
                "    <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..Int*1.field\" href=\"#m117\">field</a></span>=<span class=\"f\"><span class=\"f\"><span class=\"s\">\"one\\\"escape\"</span></span>+<span class=\"f\"><a href=\"#m135\">p</a></span></span></span>;\n" +
                "   }\n" +
                "   public String <a name=\"m188\">field</a>(){\n" +
                "    return <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..Int*1.field\" href=\"#m117\">field</a></span>+<span class=\"f\"><span class=\"s\">\"endone\"</span></span></span>;\n" +
                "   }\n" +
                "  }</span></span>,<span class=\"f\"><a title=\"pkg.Ext..Int*2.pkg.Ext..Int*2(int,int)\" href=\"#m274\">new</a> <a title=\"pkg.Int\" href=\"#m10\">Int</a>(<span class=\"f\">2</span>,<span class=\"f\">3</span>)<span class=\"t\"><a name=\"m245\">{</a>\n" +
                "   public String <span class=\"f\"><a name=\"m264\">field</a></span>;\n" +
                "   <a name=\"m274\">Int(</a>int <a name=\"m282\">p</a>, int <a name=\"m289\">q</a>){\n" +
                "    <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..Int*2.field\" href=\"#m264\">field</a></span>=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"s\">\"two\\\"escape\"</span></span>+<span class=\"f\"><a href=\"#m282\">p</a></span></span>+<span class=\"f\"><span class=\"s\">';'</span></span></span>+<span class=\"f\"><a href=\"#m289\">q</a></span></span></span>;\n" +
                "   }\n" +
                "   public String <a name=\"m348\">field</a>(){\n" +
                "    return <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..Int*2.field\" href=\"#m264\">field</a></span>+<span class=\"f\"><span class=\"s\">\"endtwo\"</span></span></span>;\n" +
                "   }\n" +
                "  }</span></span>}</span></span>;\n" +
                "  String <span class=\"f\"><span class=\"f\"><a name=\"m404\">res</a> </span>=<span class=\"f\"> <span class=\"s\">\"\"</span></span></span>;\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m420\">i</a> </span>=<span class=\"f\"> 0</span></span>;\n" +
                "  <span class=\"f\">while</span> (<span class=\"f\"><span class=\"f\"><a href=\"#m420\">i</a> </span>&lt;<span class=\"f\"> 2</span></span>){\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m404\">res</a></span>+=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m83\">a</a></span><span class=\"f\">[<span class=\"f\"><a href=\"#m420\">i</a></span>]</span></span>.<span class=\"f\"><a title=\"pkg.Int.field()\" href=\"#m28\">field</a>()</span></span>+<span class=\"f\"><span class=\"s\">','</span></span></span></span>;\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m420\">i</a></span>++</span>;\n" +
                "  }\n" +
                "  return <span class=\"f\"><a href=\"#m404\">res</a></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage420Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int CST = 1;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static String m(){\n");
        xml_.append("  String res = \"\";\n");
        xml_.append("  switch(1){\n");
        xml_.append("   case new Int(){}.CST:\n");
        xml_.append("    res += 1;\n");
        xml_.append("  }\n");
        xml_.append("  return res;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">CST</a> </span>=<span class=\"g\"> 1</span></span>;\n" +
                "}\n" +
                "class <a name=\"m51\">pkg.Ext</a> {\n" +
                " static String <a name=\"m76\">m</a>(){\n" +
                "  String <span class=\"f\"><span class=\"f\"><a name=\"m90\">res</a> </span>=<span class=\"f\"> <span class=\"s\">\"\"</span></span></span>;\n" +
                "  <span class=\"p\"><a title=\"1/2\">switch</a></span>(<span class=\"f\">1</span>){\n" +
                "   case <span class=\"f\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m130\">{</a>}</span>.<a title=\"pkg.Int.CST\" href=\"#m34\">CST</a></span>:\n" +
                "    <span class=\"f\"><span class=\"f\"><a href=\"#m90\">res</a> </span>+=<span class=\"f\"> 1</span></span>;\n" +
                "  }\n" +
                "  return <span class=\"f\"><a href=\"#m90\">res</a></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage421Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static final int CST = 1;\n");
        xml_.append(" static String m(){\n");
        xml_.append("  String res = \"\";\n");
        xml_.append("  switch(2){\n");
        xml_.append("   case CST+CST:\n");
        xml_.append("    res += 1;\n");
        xml_.append("  }\n");
        xml_.append("  return res;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">CST</a> </span>=<span class=\"g\"> 1</span></span>;\n" +
                " static String <a name=\"m58\">m</a>(){\n" +
                "  String <span class=\"f\"><span class=\"f\"><a name=\"m72\">res</a> </span>=<span class=\"f\"> <span class=\"s\">\"\"</span></span></span>;\n" +
                "  <span class=\"p\"><a title=\"1/2\">switch</a></span>(<span class=\"f\">2</span>){\n" +
                "   case <span class=\"f\"><a title=\"pkg.Ext.CST\" href=\"#m34\">CST</a>+<a title=\"pkg.Ext.CST\" href=\"#m34\">CST</a></span>:\n" +
                "    <span class=\"f\"><span class=\"f\"><a href=\"#m72\">res</a> </span>+=<span class=\"f\"> 1</span></span>;\n" +
                "  }\n" +
                "  return <span class=\"f\"><a href=\"#m72\">res</a></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage422Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" int field = 1;\n");
        xml_.append("}\n");
        xml_.append("class pkg.Int2 {\n");
        xml_.append(" Int f;\n");
        xml_.append(" Int2(Int i) {\n");
        xml_.append("  f = i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return new Int2(new Int(){}){\n");
        xml_.append("   Int2(Int p){super(p);}\n");
        xml_.append("  }.f.field;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m21\">field</a> </span>=<span class=\"f\"> 1</span></span>;\n" +
                "}\n" +
                "class <a name=\"m40\">pkg.Int2</a> {\n" +
                " <a title=\"pkg.Int\" href=\"#m6\">Int</a> <span class=\"f\"><a name=\"m56\">f</a></span>;\n" +
                " <a name=\"m60\">Int2(</a><a title=\"pkg.Int\" href=\"#m6\">Int</a> <a name=\"m69\">i</a>) {\n" +
                "  <span class=\"f\"><span class=\"f\"><a title=\"pkg.Int2.f\" href=\"#m56\">f</a> </span>=<span class=\"f\"> <a href=\"#m69\">i</a></span></span>;\n" +
                " }\n" +
                "}\n" +
                "class <a name=\"m94\">pkg.Ext</a> {\n" +
                " static int <a name=\"m116\">m</a>(){\n" +
                "  return <span class=\"f\"><span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext..Int2*1.pkg.Ext..Int2*1(pkg.Int)\" href=\"#m156\">new</a> <a title=\"pkg.Int2\" href=\"#m40\">Int2</a>(<span class=\"f\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m148\">{</a>}</span></span>)<span class=\"t\"><a name=\"m151\">{</a>\n" +
                "   <a name=\"m156\">Int2(</a><a title=\"pkg.Int\" href=\"#m6\">Int</a> <a name=\"m165\">p</a>){<span class=\"f\"><a title=\"pkg.Int2.pkg.Int2(pkg.Int)\" href=\"#m60\">super</a>(<span class=\"f\"><a href=\"#m165\">p</a></span>)</span>;}\n" +
                "  }</span></span>.<span class=\"f\"><a title=\"pkg.Int2.f\" href=\"#m56\">f</a></span></span>.<span class=\"f\"><a title=\"pkg.Int.field\" href=\"#m21\">field</a></span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage423Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return (int a:int)->{return 2 * a;}.call(3);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><span class=\"f\"><span class=\"t\">(int <a name=\"m47\">a</a>:int)<a name=\"m53\">-&gt;</a>{return <span class=\"f\"><span class=\"f\">2 </span>*<span class=\"f\"> <a href=\"#m47\">a</a></span></span>;}</span></span>.<span class=\"f\"><b>call</b>(<span class=\"f\">3</span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage424Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return m((int a:int)->{return 2 * a;},3);\n");
        xml_.append(" }\n");
        xml_.append(" static int m(Fct<int,int> fct,int a){\n");
        xml_.append("  return fct.call(a);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><a title=\"pkg.Ext.static m($core.Fct&lt;int,int&gt;,int)\" href=\"#m92\">m</a>(<span class=\"f\"><span class=\"t\">(int <a name=\"m49\">a</a>:int)<a name=\"m55\">-&gt;</a>{return <span class=\"f\"><span class=\"f\">2 </span>*<span class=\"f\"> <a href=\"#m49\">a</a></span></span>;}</span></span>,<span class=\"f\">3</span>)</span>;\n" +
                " }\n" +
                " static int <a name=\"m92\">m</a>(Fct&lt;int,int&gt; <a name=\"m107\">fct</a>,int <a name=\"m115\">a</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m107\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m115\">a</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage425Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext<T> {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return staticCall(Ext<int>).m();\n");
        xml_.append(" }\n");
        xml_.append(" staticCall T m(){\n");
        xml_.append("  return staticCall(ExtOther<T>).m((T a:T)->{return (T)(2 * (int)a);},(T)3);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("class pkg.ExtOther<S> {\n");
        xml_.append(" staticCall S m(Fct<S,S> fct,S a){\n");
        xml_.append("  return fct.call(a);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a>&lt;<a name=\"m14\">T</a>&gt; {\n" +
                " static int <a name=\"m31\">m</a>(){\n" +
                "  return <span class=\"f\"><span class=\"f\">staticCall(<a title=\"pkg.Ext\" href=\"#m6\">Ext</a>&lt;int&gt;)</span>.<span class=\"f\"><a title=\"pkg.Ext.staticCall m()\" href=\"#m88\">m</a>()</span></span>;\n" +
                " }\n" +
                " staticCall <a href=\"#m14\">T</a> <a name=\"m88\">m</a>(){\n" +
                "  return <span class=\"f\"><span class=\"f\">staticCall(<a title=\"pkg.ExtOther\" href=\"#m181\">ExtOther</a>&lt;<a href=\"#m14\">T</a>&gt;)</span>.<span class=\"f\"><a title=\"pkg.ExtOther.staticCall m($core.Fct&lt;#S,#S&gt;,#S)\" href=\"#m213\">m</a>(<span class=\"f\"><span class=\"t\">(<a href=\"#m14\">T</a> <a name=\"m131\">a</a>:<a href=\"#m14\">T</a>)<a name=\"m135\">-&gt;</a>{return <span class=\"f\">(<a href=\"#m14\">T</a>)<span class=\"f\">(<span class=\"f\"><span class=\"f\">2 </span>*<span class=\"f\"> (int)<span class=\"f\"><a href=\"#m131\">a</a></span></span></span>)</span></span>;}</span></span>,<span class=\"f\">(<a href=\"#m14\">T</a>)<span class=\"f\">3</span></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "class <a name=\"m181\">pkg.ExtOther</a>&lt;<a name=\"m194\">S</a>&gt; {\n" +
                " staticCall <a href=\"#m194\">S</a> <a name=\"m213\">m</a>(Fct&lt;<a href=\"#m194\">S</a>,<a href=\"#m194\">S</a>&gt; <a name=\"m224\">fct</a>,<a href=\"#m194\">S</a> <a name=\"m230\">a</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m224\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m230\">a</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage426Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  Content content = new Content();\n");
        xml_.append("  m((Content a:void)->{a.incr();},content);\n");
        xml_.append("  return content.value;\n");
        xml_.append(" }\n");
        xml_.append(" static void m(Fct<Content,void> fct,Content a){\n");
        xml_.append("  fct.call(a);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("class pkg.Content {\n");
        xml_.append(" int value = 5;\n");
        xml_.append(" void incr(){\n");
        xml_.append("  value++;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  <a title=\"pkg.Content\" href=\"#m214\">Content</a> <span class=\"f\"><span class=\"f\"><a name=\"m43\">content</a> </span>=<span class=\"f\"> new <a title=\"pkg.Content\" href=\"#m214\">Content</a>()</span></span>;\n" +
                "  <span class=\"f\"><a title=\"pkg.Ext.static m($core.Fct&lt;pkg.Content,void&gt;,pkg.Content)\" href=\"#m152\">m</a>(<span class=\"f\"><span class=\"t\">(<a title=\"pkg.Content\" href=\"#m214\">Content</a> <a name=\"m81\">a</a>:void)<a name=\"m88\">-&gt;</a>{<span class=\"f\"><span class=\"f\"><a href=\"#m81\">a</a></span>.<span class=\"f\"><a title=\"pkg.Content.incr()\" href=\"#m250\">incr</a>()</span></span>;}</span></span>,<span class=\"f\"><a href=\"#m43\">content</a></span>)</span>;\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m43\">content</a></span>.<span class=\"f\"><a title=\"pkg.Content.value\" href=\"#m233\">value</a></span></span>;\n" +
                " }\n" +
                " static void <a name=\"m152\">m</a>(Fct&lt;<a title=\"pkg.Content\" href=\"#m214\">Content</a>,void&gt; <a name=\"m172\">fct</a>,<a title=\"pkg.Content\" href=\"#m214\">Content</a> <a name=\"m184\">a</a>){\n" +
                "  <span class=\"f\"><span class=\"f\"><a href=\"#m172\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m184\">a</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "class <a name=\"m214\">pkg.Content</a> {\n" +
                " int <span class=\"f\"><span class=\"f\"><a name=\"m233\">value</a> </span>=<span class=\"f\"> 5</span></span>;\n" +
                " void <a name=\"m250\">incr</a>(){\n" +
                "  <span class=\"f\"><span class=\"f\"><a title=\"pkg.Content.value\" href=\"#m233\">value</a></span>++</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage427Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return m((a:int)->{return 2 * a;},3);\n");
        xml_.append(" }\n");
        xml_.append(" static int m(Fct<int,int> fct,int a){\n");
        xml_.append("  return fct.call(a);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><a title=\"pkg.Ext.static m($core.Fct&lt;int,int&gt;,int)\" href=\"#m88\">m</a>(<span class=\"f\"><span class=\"t\">(<a name=\"m45\">a</a>:int)<a name=\"m51\">-&gt;</a>{return <span class=\"f\"><span class=\"f\">2 </span>*<span class=\"f\"> <a href=\"#m45\">a</a></span></span>;}</span></span>,<span class=\"f\">3</span>)</span>;\n" +
                " }\n" +
                " static int <a name=\"m88\">m</a>(Fct&lt;int,int&gt; <a name=\"m103\">fct</a>,int <a name=\"m111\">a</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m103\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m111\">a</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage428Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return m((a:)->{return 2 * a;},3);\n");
        xml_.append(" }\n");
        xml_.append(" static int m(Fct<int,int> fct,int a){\n");
        xml_.append("  return fct.call(a);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><a title=\"pkg.Ext.static m($core.Fct&lt;int,int&gt;,int)\" href=\"#m85\">m</a>(<span class=\"f\"><span class=\"t\">(<a name=\"m45\">a</a>:)<a name=\"m48\">-&gt;</a>{return <span class=\"f\"><span class=\"f\">2 </span>*<span class=\"f\"> <a href=\"#m45\">a</a></span></span>;}</span></span>,<span class=\"f\">3</span>)</span>;\n" +
                " }\n" +
                " static int <a name=\"m85\">m</a>(Fct&lt;int,int&gt; <a name=\"m100\">fct</a>,int <a name=\"m108\">a</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m100\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m108\">a</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage429Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return m((int a)->{return 2 * a;},3);\n");
        xml_.append(" }\n");
        xml_.append(" static int m(Fct<int,int> fct,int a){\n");
        xml_.append("  return fct.call(a);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><a title=\"pkg.Ext.static m($core.Fct&lt;int,int&gt;,int)\" href=\"#m88\">m</a>(<span class=\"f\"><span class=\"t\">(int <a name=\"m49\">a</a>)<a name=\"m51\">-&gt;</a>{return <span class=\"f\"><span class=\"f\">2 </span>*<span class=\"f\"> <a href=\"#m49\">a</a></span></span>;}</span></span>,<span class=\"f\">3</span>)</span>;\n" +
                " }\n" +
                " static int <a name=\"m88\">m</a>(Fct&lt;int,int&gt; <a name=\"m103\">fct</a>,int <a name=\"m111\">a</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m103\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m111\">a</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage430Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return m((a)->{return 2 * a;},3);\n");
        xml_.append(" }\n");
        xml_.append(" static int m(Fct<int,int> fct,int a){\n");
        xml_.append("  return fct.call(a);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><a title=\"pkg.Ext.static m($core.Fct&lt;int,int&gt;,int)\" href=\"#m84\">m</a>(<span class=\"f\"><span class=\"t\">(<a name=\"m45\">a</a>)<a name=\"m47\">-&gt;</a>{return <span class=\"f\"><span class=\"f\">2 </span>*<span class=\"f\"> <a href=\"#m45\">a</a></span></span>;}</span></span>,<span class=\"f\">3</span>)</span>;\n" +
                " }\n" +
                " static int <a name=\"m84\">m</a>(Fct&lt;int,int&gt; <a name=\"m99\">fct</a>,int <a name=\"m107\">a</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m99\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m107\">a</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage431Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext<T> {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return staticCall(Ext<int>).m();\n");
        xml_.append(" }\n");
        xml_.append(" staticCall T m(){\n");
        xml_.append("  return staticCall(ExtOther<T>).m((T a)->{return (T)(2 * (int)a);},(T)3);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("class pkg.ExtOther<S> {\n");
        xml_.append(" staticCall S m(Fct<S,S> fct,S a){\n");
        xml_.append("  return fct.call(a);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a>&lt;<a name=\"m14\">T</a>&gt; {\n" +
                " static int <a name=\"m31\">m</a>(){\n" +
                "  return <span class=\"f\"><span class=\"f\">staticCall(<a title=\"pkg.Ext\" href=\"#m6\">Ext</a>&lt;int&gt;)</span>.<span class=\"f\"><a title=\"pkg.Ext.staticCall m()\" href=\"#m88\">m</a>()</span></span>;\n" +
                " }\n" +
                " staticCall <a href=\"#m14\">T</a> <a name=\"m88\">m</a>(){\n" +
                "  return <span class=\"f\"><span class=\"f\">staticCall(<a title=\"pkg.ExtOther\" href=\"#m179\">ExtOther</a>&lt;<a href=\"#m14\">T</a>&gt;)</span>.<span class=\"f\"><a title=\"pkg.ExtOther.staticCall m($core.Fct&lt;#S,#S&gt;,#S)\" href=\"#m211\">m</a>(<span class=\"f\"><span class=\"t\">(<a href=\"#m14\">T</a> <a name=\"m131\">a</a>)<a name=\"m133\">-&gt;</a>{return <span class=\"f\">(<a href=\"#m14\">T</a>)<span class=\"f\">(<span class=\"f\"><span class=\"f\">2 </span>*<span class=\"f\"> (int)<span class=\"f\"><a href=\"#m131\">a</a></span></span></span>)</span></span>;}</span></span>,<span class=\"f\">(<a href=\"#m14\">T</a>)<span class=\"f\">3</span></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "class <a name=\"m179\">pkg.ExtOther</a>&lt;<a name=\"m192\">S</a>&gt; {\n" +
                " staticCall <a href=\"#m192\">S</a> <a name=\"m211\">m</a>(Fct&lt;<a href=\"#m192\">S</a>,<a href=\"#m192\">S</a>&gt; <a name=\"m222\">fct</a>,<a href=\"#m192\">S</a> <a name=\"m228\">a</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m222\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m228\">a</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage432Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return m((int a:int) -> {return 2 * a;},3);\n");
        xml_.append(" }\n");
        xml_.append(" static int m(Fct<int,int> fct,int a){\n");
        xml_.append("  return fct.call(a);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><a title=\"pkg.Ext.static m($core.Fct&lt;int,int&gt;,int)\" href=\"#m94\">m</a>(<span class=\"f\"><span class=\"t\">(int <a name=\"m49\">a</a>:int) <a name=\"m56\">-&gt;</a> {return <span class=\"f\"><span class=\"f\">2 </span>*<span class=\"f\"> <a href=\"#m49\">a</a></span></span>;}</span></span>,<span class=\"f\">3</span>)</span>;\n" +
                " }\n" +
                " static int <a name=\"m94\">m</a>(Fct&lt;int,int&gt; <a name=\"m109\">fct</a>,int <a name=\"m117\">a</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m109\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m117\">a</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage433Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return m(a->{return 2 * a;},3);\n");
        xml_.append(" }\n");
        xml_.append(" static int m(Fct<int,int> fct,int a){\n");
        xml_.append("  return fct.call(a);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><a title=\"pkg.Ext.static m($core.Fct&lt;int,int&gt;,int)\" href=\"#m82\">m</a>(<span class=\"f\"><span class=\"t\"><a name=\"m44\">a</a><a name=\"m45\">-&gt;</a>{return <span class=\"f\"><span class=\"f\">2 </span>*<span class=\"f\"> <a href=\"#m44\">a</a></span></span>;}</span></span>,<span class=\"f\">3</span>)</span>;\n" +
                " }\n" +
                " static int <a name=\"m82\">m</a>(Fct&lt;int,int&gt; <a name=\"m97\">fct</a>,int <a name=\"m105\">a</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m97\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m105\">a</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage434Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return m(a -> {return 2 * a;},3);\n");
        xml_.append(" }\n");
        xml_.append(" static int m(Fct<int,int> fct,int a){\n");
        xml_.append("  return fct.call(a);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><a title=\"pkg.Ext.static m($core.Fct&lt;int,int&gt;,int)\" href=\"#m84\">m</a>(<span class=\"f\"><span class=\"t\"><a name=\"m44\">a</a> <a name=\"m46\">-&gt;</a> {return <span class=\"f\"><span class=\"f\">2 </span>*<span class=\"f\"> <a href=\"#m44\">a</a></span></span>;}</span></span>,<span class=\"f\">3</span>)</span>;\n" +
                " }\n" +
                " static int <a name=\"m84\">m</a>(Fct&lt;int,int&gt; <a name=\"m99\">fct</a>,int <a name=\"m107\">a</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m99\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m107\">a</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage435Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return m((a) -> 2 * a ,3);\n");
        xml_.append(" }\n");
        xml_.append(" static int m(Fct<int,int> fct,int a){\n");
        xml_.append("  return fct.call(a);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><a title=\"pkg.Ext.static m($core.Fct&lt;int,int&gt;,int)\" href=\"#m77\">m</a>(<span class=\"f\"><span class=\"t\">(<a name=\"m45\">a</a>) <a name=\"m48\">-&gt;</a> <span class=\"f\"><span class=\"f\">2 </span>*<span class=\"f\"> <a href=\"#m45\">a</a></span></span></span> </span>,<span class=\"f\">3</span>)</span>;\n" +
                " }\n" +
                " static int <a name=\"m77\">m</a>(Fct&lt;int,int&gt; <a name=\"m92\">fct</a>,int <a name=\"m100\">a</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m92\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m100\">a</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage436Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return m(a -> 2 * a ,3);\n");
        xml_.append(" }\n");
        xml_.append(" static int m(Fct<int,int> fct,int a){\n");
        xml_.append("  return fct.call(a);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><a title=\"pkg.Ext.static m($core.Fct&lt;int,int&gt;,int)\" href=\"#m75\">m</a>(<span class=\"f\"><span class=\"t\"><a name=\"m44\">a</a> <a name=\"m46\">-&gt;</a> <span class=\"f\"><span class=\"f\">2 </span>*<span class=\"f\"> <a href=\"#m44\">a</a></span></span></span> </span>,<span class=\"f\">3</span>)</span>;\n" +
                " }\n" +
                " static int <a name=\"m75\">m</a>(Fct&lt;int,int&gt; <a name=\"m90\">fct</a>,int <a name=\"m98\">a</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m90\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m98\">a</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage437Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return m(a -> b -> a * b,2,3);\n");
        xml_.append(" }\n");
        xml_.append(" static int m(Fct<int,Fct<int,int>> fct,int a,int b){\n");
        xml_.append("  return fct.call(a).call(b);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><a title=\"pkg.Ext.static m($core.Fct&lt;int,$core.Fct&lt;int,int&gt;&gt;,int,int)\" href=\"#m81\">m</a>(<span class=\"f\"><span class=\"t\"><a name=\"m44\">a</a> <a name=\"m46\">-&gt;</a> <span class=\"f\"><span class=\"t\"><a name=\"m49\">b</a> <a name=\"m51\">-&gt;</a> <span class=\"f\"><span class=\"f\"><a href=\"#m44\">a</a> </span>*<span class=\"f\"> <a href=\"#m49\">b</a></span></span></span></span></span></span>,<span class=\"f\">2</span>,<span class=\"f\">3</span>)</span>;\n" +
                " }\n" +
                " static int <a name=\"m81\">m</a>(Fct&lt;int,Fct&lt;int,int&gt;&gt; <a name=\"m105\">fct</a>,int <a name=\"m113\">a</a>,int <a name=\"m119\">b</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m105\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m113\">a</a></span>)</span></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m119\">b</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage438Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" static int extField;\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m90\">pkg.Ext</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m123\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " static int <span class=\"g\"><a name=\"m145\">extField</a></span>;\n" +
                " static int <a name=\"m167\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage439Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("enum pkg.Ext {\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" ONE;\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "enum <a name=\"m89\">pkg.Ext</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m122\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " <a name=\"m133\">ONE</a>;\n" +
                " static int <a name=\"m150\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage440Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("enum pkg.Ext {\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" ONE{};\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "enum <a name=\"m89\">pkg.Ext</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m122\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " <a name=\"m133\">ONE</a>{};\n" +
                " static int <a name=\"m152\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage441Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("enum pkg.Ext {\n");
        xml_.append(" ONE{};\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m106\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m91\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " int <a name=\"m106\">field</a>();\n" +
                "}\n" +
                "enum <a name=\"m122\">pkg.Ext</a> {\n" +
                " <a name=\"m133\">ONE</a>{};\n" +
                " static int <a name=\"m152\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage442Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" @Annot\n");
        xml_.append(" int field()new Int(){}.FIELD;\n");
        xml_.append("}\n");
        xml_.append("enum pkg.Ext {\n");
        xml_.append(" ONE{};\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a></span>\n" +
                " int <a name=\"m81\">field</a>()<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m97\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span>;\n" +
                "}\n" +
                "enum <a name=\"m114\">pkg.Ext</a> {\n" +
                " <a name=\"m125\">ONE</a>{};\n" +
                " static int <a name=\"m144\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage443Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" int field()new Int(){}.FIELD;\n");
        xml_.append("}\n");
        xml_.append("enum pkg.Ext {\n");
        xml_.append(" ONE{};\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m106\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m91\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " int <a name=\"m106\">field</a>()<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m122\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span>;\n" +
                "}\n" +
                "enum <a name=\"m139\">pkg.Ext</a> {\n" +
                " <a name=\"m150\">ONE</a>{};\n" +
                " static int <a name=\"m169\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage444Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" @Annot(field=Int.FIELD)\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" static int extField;\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m90\">pkg.Ext</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\"><a title=\"pkg.Int\" href=\"#m6\">Int</a></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m148\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " static int <span class=\"g\"><a name=\"m170\">extField</a></span>;\n" +
                " static int <a name=\"m192\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage445Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" @Annot(field=Int.FIELD)\n");
        xml_.append(" static int extField;\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m90\">pkg.Ext</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m123\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\"><a title=\"pkg.Int\" href=\"#m6\">Int</a></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " static int <span class=\"g\"><a name=\"m170\">extField</a></span>;\n" +
                " static int <a name=\"m192\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage446Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" static int extField;\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m90\">pkg.Ext</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m123\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m156\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " static int <span class=\"g\"><a name=\"m178\">extField</a></span>;\n" +
                " static int <a name=\"m200\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage447Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" Ext(){};\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m90\">pkg.Ext</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m123\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " <a name=\"m134\">Ext(</a>){};\n" +
                " static int <a name=\"m155\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage448Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" Ext(@Annot(field=new Int(){}.FIELD) int p){};\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m90\">pkg.Ext</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m123\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " <a name=\"m134\">Ext(</a><span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m160\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span> int <a name=\"m174\">p</a>){};\n" +
                " static int <a name=\"m192\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage449Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" Ext(@Annot(field=new Int(){}.FIELD)@Annot(field=new Int(){}.FIELD) int p){};\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m90\">pkg.Ext</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m123\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " <a name=\"m134\">Ext(</a><span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m160\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span><span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m191\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span> int <a name=\"m205\">p</a>){};\n" +
                " static int <a name=\"m223\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage450Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" Ext(@Annot(field=new Int(){}.FIELD) int p, @Annot(field=new Int(){}.FIELD) int q){};\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m90\">pkg.Ext</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m123\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " <a name=\"m134\">Ext(</a><span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m160\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span> int <a name=\"m174\">p</a>, <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m199\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span> int <a name=\"m213\">q</a>){};\n" +
                " static int <a name=\"m231\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage451Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" static void l(){};\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m90\">pkg.Ext</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m123\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " static void <a name=\"m146\">l</a>(){};\n" +
                " static int <a name=\"m165\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage452Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" static void l(@Annot(field=new Int(){}.FIELD) int p){};\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m90\">pkg.Ext</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m123\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " static void <a name=\"m146\">l</a>(<span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m170\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span> int <a name=\"m184\">p</a>){};\n" +
                " static int <a name=\"m202\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage453Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" static void l(@Annot(field=new Int(){}.FIELD)@Annot(field=new Int(){}.FIELD) int p){};\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m90\">pkg.Ext</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m123\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " static void <a name=\"m146\">l</a>(<span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m170\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span><span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m201\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span> int <a name=\"m215\">p</a>){};\n" +
                " static int <a name=\"m233\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage454Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" static void l(@Annot(field=new Int(){}.FIELD) int p, @Annot(field=new Int(){}.FIELD) int q){};\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m90\">pkg.Ext</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m123\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " static void <a name=\"m146\">l</a>(<span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m170\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span> int <a name=\"m184\">p</a>, <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m209\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span> int <a name=\"m223\">q</a>){};\n" +
                " static int <a name=\"m241\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage455Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" void this(@Annot(field=new Int(){}.FIELD) int p){};\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" int this(@Annot(field=new Int(){}.FIELD) int p){return 0;};\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m90\">pkg.Ext</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m123\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " void <a name=\"m139\">this</a>(<span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m166\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span> int <a name=\"m180\">p</a>){};\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m209\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " int <a name=\"m224\">this</a>(<span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m251\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span> int <a name=\"m265\">p</a>){return <span class=\"n\">0</span>;};\n" +
                " static int <a name=\"m292\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage456Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" @Annot(field=new Int(){}.FIELD)\n");
        xml_.append(" operator+ int(@Annot(field=new Int(){}.FIELD) int p){return 0;};\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m90\">pkg.Ext</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m123\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                " operator<a name=\"m142\">+</a> int(<span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m170\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span> int <a name=\"m184\">p</a>){return <span class=\"n\">0</span>;};\n" +
                " static int <a name=\"m211\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage457Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("@Annot(field=new Int(){}.FIELD)\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "<span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\">new <a title=\"pkg.Int\" href=\"#m6\">Int</a>()<span class=\"t\"><a name=\"m106\">{</a>}</span></span>.<span class=\"n2\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span></span>)</span>\n" +
                "class <a name=\"m122\">pkg.Ext</a> {\n" +
                " static int <a name=\"m144\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage458Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("@Annot(field=(:int)->{return Int.FIELD;}.call())\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "<span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\"><span class=\"t\">(:int)<a name=\"m103\">-&gt;</a>{return <span class=\"n\"><span class=\"n\"><a title=\"pkg.Int\" href=\"#m6\">Int</a></span>.<span class=\"n\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span>;}</span></span>.<span class=\"n2\"><b>call</b>()</span></span></span>)</span>\n" +
                "class <a name=\"m139\">pkg.Ext</a> {\n" +
                " static int <a name=\"m161\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage459Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Int {\n");
        xml_.append(" static final int FIELD=1;\n");
        xml_.append("}\n");
        xml_.append("annotation pkg.Annot {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append("@Annot(field=(:int)->{return Int.FIELD;}.call())\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Int</a> {\n" +
                " static final int <span class=\"g\"><span class=\"g\"><a name=\"m34\">FIELD</a></span>=<span class=\"g\">1</span></span>;\n" +
                "}\n" +
                "annotation <a name=\"m56\">pkg.Annot</a> {\n" +
                " int <a name=\"m73\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m90\">pkg.Ext</a> {\n" +
                "<span class=\"n2\">@<a title=\"pkg.Annot\" href=\"#m56\">Annot</a>(<span class=\"n2\"><a title=\"pkg.Annot.field()\" href=\"#m73\">field</a>=<span class=\"n2\"><span class=\"n2\"><span class=\"t\">(:int)<a name=\"m119\">-&gt;</a>{return <span class=\"n\"><span class=\"n\"><a title=\"pkg.Int\" href=\"#m6\">Int</a></span>.<span class=\"n\"><a title=\"pkg.Int.FIELD\" href=\"#m34\">FIELD</a></span></span>;}</span></span>.<span class=\"n2\"><b>call</b>()</span></span></span>)</span>\n" +
                " static int <a name=\"m161\">m</a>(){\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage460Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return staticCall(Ext).m();\n");
        xml_.append(" }\n");
        xml_.append(" staticCall int m(){\n");
        xml_.append("  return m(a -> a -> a * #a,2,3);\n");
        xml_.append(" }\n");
        xml_.append(" staticCall int m(Fct<int,Fct<int,int>> fct,int a,int b){\n");
        xml_.append("  return fct.call(a).call(b);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><span class=\"f\">staticCall(<a title=\"pkg.Ext\" href=\"#m6\">Ext</a>)</span>.<span class=\"f\"><a title=\"pkg.Ext.staticCall m()\" href=\"#m82\">m</a>()</span></span>;\n" +
                " }\n" +
                " staticCall int <a name=\"m82\">m</a>(){\n" +
                "  return <span class=\"f\"><a title=\"pkg.Ext.staticCall m($core.Fct&lt;int,$core.Fct&lt;int,int&gt;&gt;,int,int)\" href=\"#m140\">m</a>(<span class=\"f\"><span class=\"t\"><a name=\"m98\">a</a> <a name=\"m100\">-&gt;</a> <span class=\"f\"><span class=\"t\"><a name=\"m103\">a</a> <a name=\"m105\">-&gt;</a> <span class=\"f\"><span class=\"f\"><a href=\"#m103\">a</a> </span>*<span class=\"f\"> <a href=\"#m98\">#a</a></span></span></span></span></span></span>,<span class=\"f\">2</span>,<span class=\"f\">3</span>)</span>;\n" +
                " }\n" +
                " staticCall int <a name=\"m140\">m</a>(Fct&lt;int,Fct&lt;int,int&gt;&gt; <a name=\"m164\">fct</a>,int <a name=\"m172\">a</a>,int <a name=\"m178\">b</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m164\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m172\">a</a></span>)</span></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m178\">b</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage461Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  int sum = 0;\n");
        xml_.append("  int sum2 = 3;\n");
        xml_.append("  for (int i = 1; i <= 9; i+= 2){\n");
        xml_.append("   sum += m(a -> i -> sum2 + i + a + ([i]) + #i,2,7);\n");
        xml_.append("  }\n");
        xml_.append("  return sum;\n");
        xml_.append(" }\n");
        xml_.append(" static int m(Fct<int,Fct<int,int>> fct,int a,int b){\n");
        xml_.append("  return fct.call(a).call(b);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m39\">sum</a> </span>=<span class=\"f\"> 0</span></span>;\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m54\">sum2</a> </span>=<span class=\"f\"> 3</span></span>;\n" +
                "  <span class=\"f\">for</span> (int <span class=\"f\"><span class=\"f\"><a name=\"m75\">i</a> </span>=<span class=\"f\"> 1</span></span>; <span class=\"f\"><span class=\"f\"><a href=\"#m75\">i</a> </span>&lt;=<span class=\"f\"> 9</span></span>; <span class=\"f\"><span class=\"f\"><a href=\"#m75\">i</a></span>+=<span class=\"f\"> 2</span></span>){\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m39\">sum</a> </span>+=<span class=\"f\"> <a title=\"pkg.Ext.static m($core.Fct&lt;int,$core.Fct&lt;int,int&gt;&gt;,int,int)\" href=\"#m185\">m</a>(<span class=\"f\"><span class=\"t\"><a name=\"m110\">a</a> <a name=\"m112\">-&gt;</a> <span class=\"f\"><span class=\"t\"><a name=\"m115\">i</a> <a name=\"m117\">-&gt;</a> <span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m54\">sum2</a> </span>+<span class=\"f\"> <a href=\"#m115\">i</a> </span></span>+<span class=\"f\"> <a href=\"#m110\">a</a> </span></span>+<span class=\"f\"> ([<a href=\"#m75\">i</a>]) </span></span>+<span class=\"f\"> <a href=\"#m75\">#i</a></span></span></span></span></span></span>,<span class=\"f\">2</span>,<span class=\"f\">7</span>)</span></span>;\n" +
                "  }\n" +
                "  return <span class=\"f\"><a href=\"#m39\">sum</a></span>;\n" +
                " }\n" +
                " static int <a name=\"m185\">m</a>(Fct&lt;int,Fct&lt;int,int&gt;&gt; <a name=\"m209\">fct</a>,int <a name=\"m217\">a</a>,int <a name=\"m223\">b</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m209\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m217\">a</a></span>)</span></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m223\">b</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage462Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return staticCall(Ext).m();\n");
        xml_.append(" }\n");
        xml_.append(" staticCall int m(){\n");
        xml_.append("  int a = 3;\n");
        xml_.append("  return m(a -> a * #a,2);\n");
        xml_.append(" }\n");
        xml_.append(" staticCall int m(Fct<int,int> fct,int a){\n");
        xml_.append("  return fct.call(a);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><span class=\"f\">staticCall(<a title=\"pkg.Ext\" href=\"#m6\">Ext</a>)</span>.<span class=\"f\"><a title=\"pkg.Ext.staticCall m()\" href=\"#m82\">m</a>()</span></span>;\n" +
                " }\n" +
                " staticCall int <a name=\"m82\">m</a>(){\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m93\">a</a> </span>=<span class=\"f\"> 3</span></span>;\n" +
                "  return <span class=\"f\"><a title=\"pkg.Ext.staticCall m($core.Fct&lt;int,int&gt;,int)\" href=\"#m146\">m</a>(<span class=\"f\"><span class=\"t\"><a name=\"m111\">a</a> <a name=\"m113\">-&gt;</a> <span class=\"f\"><span class=\"f\"><a href=\"#m111\">a</a> </span>*<span class=\"f\"> <a href=\"#m93\">#a</a></span></span></span></span>,<span class=\"f\">2</span>)</span>;\n" +
                " }\n" +
                " staticCall int <a name=\"m146\">m</a>(Fct&lt;int,int&gt; <a name=\"m161\">fct</a>,int <a name=\"m169\">a</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m161\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m169\">a</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage463Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  int sum3 = 0;\n");
        xml_.append("  for (int i = 2; i <= 10; i+= 2){\n");
        xml_.append("  sum3+=(:int) -> {int sum = 0;\n");
        xml_.append("  int sum2 = 3;\n");
        xml_.append("  for (int i = 1; i <= 9; i+= 2){\n");
        xml_.append("   sum += m(i -> i -> sum2 + #i + i + ([#i]) + ##i,2,7);\n");
        xml_.append("  }\n");
        xml_.append("  return sum;}.call();\n");
        xml_.append("  }\n");
        xml_.append("  return sum3;\n");
        xml_.append(" }\n");
        xml_.append(" static int m(Fct<int,Fct<int,int>> fct,int a,int b){\n");
        xml_.append("  return fct.call(a).call(b);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m39\">sum3</a> </span>=<span class=\"f\"> 0</span></span>;\n" +
                "  <span class=\"f\">for</span> (int <span class=\"f\"><span class=\"f\"><a name=\"m60\">i</a> </span>=<span class=\"f\"> 2</span></span>; <span class=\"f\"><span class=\"f\"><a href=\"#m60\">i</a> </span>&lt;=<span class=\"f\"> 10</span></span>; <span class=\"f\"><span class=\"f\"><a href=\"#m60\">i</a></span>+=<span class=\"f\"> 2</span></span>){\n" +
                "  <span class=\"f\"><span class=\"f\"><a href=\"#m39\">sum3</a></span>+=<span class=\"f\"><span class=\"f\"><span class=\"t\">(:int) <a name=\"m99\">-&gt;</a> {int <span class=\"f\"><span class=\"f\"><a name=\"m107\">sum</a> </span>=<span class=\"f\"> 0</span></span>;\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m122\">sum2</a> </span>=<span class=\"f\"> 3</span></span>;\n" +
                "  <span class=\"f\">for</span> (int <span class=\"f\"><span class=\"f\"><a name=\"m143\">i</a> </span>=<span class=\"f\"> 1</span></span>; <span class=\"f\"><span class=\"f\"><a href=\"#m143\">i</a> </span>&lt;=<span class=\"f\"> 9</span></span>; <span class=\"f\"><span class=\"f\"><a href=\"#m143\">i</a></span>+=<span class=\"f\"> 2</span></span>){\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m107\">sum</a> </span>+=<span class=\"f\"> <a title=\"pkg.Ext.static m($core.Fct&lt;int,$core.Fct&lt;int,int&gt;&gt;,int,int)\" href=\"#m284\">m</a>(<span class=\"f\"><span class=\"t\"><a name=\"m178\">i</a> <a name=\"m180\">-&gt;</a> <span class=\"f\"><span class=\"t\"><a name=\"m183\">i</a> <a name=\"m185\">-&gt;</a> <span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m122\">sum2</a> </span>+<span class=\"f\"> <a href=\"#m178\">#i</a> </span></span>+<span class=\"f\"> <a href=\"#m183\">i</a> </span></span>+<span class=\"f\"> ([<a href=\"#m60\">#i</a>]) </span></span>+<span class=\"f\"> <a href=\"#m143\">##i</a></span></span></span></span></span></span>,<span class=\"f\">2</span>,<span class=\"f\">7</span>)</span></span>;\n" +
                "  }\n" +
                "  return <span class=\"f\"><a href=\"#m107\">sum</a></span>;}</span></span>.<span class=\"f\"><b>call</b>()</span></span></span>;\n" +
                "  }\n" +
                "  return <span class=\"f\"><a href=\"#m39\">sum3</a></span>;\n" +
                " }\n" +
                " static int <a name=\"m284\">m</a>(Fct&lt;int,Fct&lt;int,int&gt;&gt; <a name=\"m308\">fct</a>,int <a name=\"m316\">a</a>,int <a name=\"m322\">b</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m308\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m316\">a</a></span>)</span></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m322\">b</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage464Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return staticCall(Ext).m();\n");
        xml_.append(" }\n");
        xml_.append(" staticCall int m(){\n");
        xml_.append("  int a = 4;\n");
        xml_.append("  return m(a -> a -> a * #a * ##a,2,3);\n");
        xml_.append(" }\n");
        xml_.append(" staticCall int m(Fct<int,Fct<int,int>> fct,int a,int b){\n");
        xml_.append("  return fct.call(a).call(b);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><span class=\"f\">staticCall(<a title=\"pkg.Ext\" href=\"#m6\">Ext</a>)</span>.<span class=\"f\"><a title=\"pkg.Ext.staticCall m()\" href=\"#m82\">m</a>()</span></span>;\n" +
                " }\n" +
                " staticCall int <a name=\"m82\">m</a>(){\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m93\">a</a> </span>=<span class=\"f\"> 4</span></span>;\n" +
                "  return <span class=\"f\"><a title=\"pkg.Ext.staticCall m($core.Fct&lt;int,$core.Fct&lt;int,int&gt;&gt;,int,int)\" href=\"#m159\">m</a>(<span class=\"f\"><span class=\"t\"><a name=\"m111\">a</a> <a name=\"m113\">-&gt;</a> <span class=\"f\"><span class=\"t\"><a name=\"m116\">a</a> <a name=\"m118\">-&gt;</a> <span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m116\">a</a> </span>*<span class=\"f\"> <a href=\"#m111\">#a</a> </span></span>*<span class=\"f\"> <a href=\"#m93\">##a</a></span></span></span></span></span></span>,<span class=\"f\">2</span>,<span class=\"f\">3</span>)</span>;\n" +
                " }\n" +
                " staticCall int <a name=\"m159\">m</a>(Fct&lt;int,Fct&lt;int,int&gt;&gt; <a name=\"m183\">fct</a>,int <a name=\"m191\">a</a>,int <a name=\"m197\">b</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m183\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m191\">a</a></span>)</span></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m197\">b</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage465Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  int sum3 = 0;\n");
        xml_.append("  for (int i = 2; i <= 10; i+= 2){\n");
        xml_.append("  sum3+=(:int) -> {int sum = 0;\n");
        xml_.append("  int sum2 = 3;\n");
        xml_.append("  for (int i = 1; i <= 9; i+= 2){\n");
        xml_.append("   sum += ([#i])+m(i -> i -> sum2 + #i + i + ([#i]) + ##i,2,7);\n");
        xml_.append("  }\n");
        xml_.append("  return sum;}.call();\n");
        xml_.append("  }\n");
        xml_.append("  return sum3;\n");
        xml_.append(" }\n");
        xml_.append(" static int m(Fct<int,Fct<int,int>> fct,int a,int b){\n");
        xml_.append("  return fct.call(a).call(b);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m39\">sum3</a> </span>=<span class=\"f\"> 0</span></span>;\n" +
                "  <span class=\"f\">for</span> (int <span class=\"f\"><span class=\"f\"><a name=\"m60\">i</a> </span>=<span class=\"f\"> 2</span></span>; <span class=\"f\"><span class=\"f\"><a href=\"#m60\">i</a> </span>&lt;=<span class=\"f\"> 10</span></span>; <span class=\"f\"><span class=\"f\"><a href=\"#m60\">i</a></span>+=<span class=\"f\"> 2</span></span>){\n" +
                "  <span class=\"f\"><span class=\"f\"><a href=\"#m39\">sum3</a></span>+=<span class=\"f\"><span class=\"f\"><span class=\"t\">(:int) <a name=\"m99\">-&gt;</a> {int <span class=\"f\"><span class=\"f\"><a name=\"m107\">sum</a> </span>=<span class=\"f\"> 0</span></span>;\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m122\">sum2</a> </span>=<span class=\"f\"> 3</span></span>;\n" +
                "  <span class=\"f\">for</span> (int <span class=\"f\"><span class=\"f\"><a name=\"m143\">i</a> </span>=<span class=\"f\"> 1</span></span>; <span class=\"f\"><span class=\"f\"><a href=\"#m143\">i</a> </span>&lt;=<span class=\"f\"> 9</span></span>; <span class=\"f\"><span class=\"f\"><a href=\"#m143\">i</a></span>+=<span class=\"f\"> 2</span></span>){\n" +
                "   <span class=\"f\"><span class=\"f\"><a href=\"#m107\">sum</a> </span>+=<span class=\"f\"><span class=\"f\"> ([<a href=\"#m60\">#i</a>])</span>+<span class=\"f\"><a title=\"pkg.Ext.static m($core.Fct&lt;int,$core.Fct&lt;int,int&gt;&gt;,int,int)\" href=\"#m291\">m</a>(<span class=\"f\"><span class=\"t\"><a name=\"m185\">i</a> <a name=\"m187\">-&gt;</a> <span class=\"f\"><span class=\"t\"><a name=\"m190\">i</a> <a name=\"m192\">-&gt;</a> <span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m122\">sum2</a> </span>+<span class=\"f\"> <a href=\"#m185\">#i</a> </span></span>+<span class=\"f\"> <a href=\"#m190\">i</a> </span></span>+<span class=\"f\"> ([<a href=\"#m60\">#i</a>]) </span></span>+<span class=\"f\"> <a href=\"#m143\">##i</a></span></span></span></span></span></span>,<span class=\"f\">2</span>,<span class=\"f\">7</span>)</span></span></span>;\n" +
                "  }\n" +
                "  return <span class=\"f\"><a href=\"#m107\">sum</a></span>;}</span></span>.<span class=\"f\"><b>call</b>()</span></span></span>;\n" +
                "  }\n" +
                "  return <span class=\"f\"><a href=\"#m39\">sum3</a></span>;\n" +
                " }\n" +
                " static int <a name=\"m291\">m</a>(Fct&lt;int,Fct&lt;int,int&gt;&gt; <a name=\"m315\">fct</a>,int <a name=\"m323\">a</a>,int <a name=\"m329\">b</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m315\">fct</a></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m323\">a</a></span>)</span></span>.<span class=\"f\"><b>call</b>(<span class=\"f\"><a href=\"#m329\">b</a></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage466Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return m(2,c:5,b:3);\n");
        xml_.append(" }\n");
        xml_.append(" static int m(int a,int b,int c){\n");
        xml_.append("  return a*b+c;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><a title=\"pkg.Ext.static m(int,int,int)\" href=\"#m71\">m</a>(<span class=\"f\">2</span>,<span class=\"f\"><a href=\"#m89\">c</a>:<span class=\"f\">5</span></span>,<span class=\"f\"><a href=\"#m83\">b</a>:<span class=\"f\">3</span></span>)</span>;\n" +
                " }\n" +
                " static int <a name=\"m71\">m</a>(int <a name=\"m77\">a</a>,int <a name=\"m83\">b</a>,int <a name=\"m89\">c</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m77\">a</a></span>*<span class=\"f\"><a href=\"#m83\">b</a></span></span>+<span class=\"f\"><a href=\"#m89\">c</a></span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage467Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return m(2, c :5, b :3);\n");
        xml_.append(" }\n");
        xml_.append(" static int m(int a,int b,int c){\n");
        xml_.append("  return a*b+c;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><a title=\"pkg.Ext.static m(int,int,int)\" href=\"#m75\">m</a>(<span class=\"f\">2</span>,<span class=\"f\"> <a href=\"#m93\">c</a> :<span class=\"f\">5</span></span>,<span class=\"f\"> <a href=\"#m87\">b</a> :<span class=\"f\">3</span></span>)</span>;\n" +
                " }\n" +
                " static int <a name=\"m75\">m</a>(int <a name=\"m81\">a</a>,int <a name=\"m87\">b</a>,int <a name=\"m93\">c</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m81\">a</a></span>*<span class=\"f\"><a href=\"#m87\">b</a></span></span>+<span class=\"f\"><a href=\"#m93\">c</a></span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage468Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  return ExtTwo.m(2,c:5,b:3);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("class pkg.ExtTwo {\n");
        xml_.append(" static int m(int a,int b,int c){\n");
        xml_.append("  return a*b+c;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/ExTwo", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a title=\"pkg.ExtTwo\" href=\"ExTwo.html#m6\">ExtTwo</a></span>.<span class=\"f\"><a title=\"pkg.ExtTwo.static m(int,int,int)\" href=\"ExTwo.html#m31\">m</a>(<span class=\"f\">2</span>,<span class=\"f\"><a href=\"ExTwo.html#m49\">c</a>:<span class=\"f\">5</span></span>,<span class=\"f\"><a href=\"ExTwo.html#m43\">b</a>:<span class=\"f\">3</span></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.ExtTwo</a> {\n" +
                " static int <a name=\"m31\">m</a>(int <a name=\"m37\">a</a>,int <a name=\"m43\">b</a>,int <a name=\"m49\">c</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m37\">a</a></span>*<span class=\"f\"><a href=\"#m43\">b</a></span></span>+<span class=\"f\"><a href=\"#m49\">c</a></span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.getValue(1));
    }
    @Test
    public void coverage469Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" int field;\n");
        xml_.append(" Ext(int a,int b,int c,int d){\n");
        xml_.append("  field = a*b+c-d;\n");
        xml_.append(" }\n");
        xml_.append(" static int m(){\n");
        xml_.append("  Ext e = new Ext(d:10,b:13,c:4,a:7){\n");
        xml_.append("   Ext(int a,int b,int c,int d){\n");
        xml_.append("    super(a,b,c,d);\n");
        xml_.append("   }\n");
        xml_.append("  };\n");
        xml_.append("  return e.field;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " int <span class=\"f\"><a name=\"m21\">field</a></span>;\n" +
                " <a name=\"m29\">Ext(</a>int <a name=\"m37\">a</a>,int <a name=\"m43\">b</a>,int <a name=\"m49\">c</a>,int <a name=\"m55\">d</a>){\n" +
                "  <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext.field\" href=\"#m21\">field</a> </span>=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"> <a href=\"#m37\">a</a></span>*<span class=\"f\"><a href=\"#m43\">b</a></span></span>+<span class=\"f\"><a href=\"#m49\">c</a></span></span>-<span class=\"f\"><a href=\"#m55\">d</a></span></span></span>;\n" +
                " }\n" +
                " static int <a name=\"m93\">m</a>(){\n" +
                "  <a title=\"pkg.Ext\" href=\"#m6\">Ext</a> <span class=\"f\"><span class=\"f\"><a name=\"m104\">e</a> </span>=<span class=\"f\"> <a title=\"pkg.Ext..Ext*1.pkg.Ext..Ext*1(int,int,int,int)\" href=\"#m139\">new</a> <a title=\"pkg.Ext\" href=\"#m6\">Ext</a>(<span class=\"f\"><a href=\"#m165\">d</a>:<span class=\"f\">10</span></span>,<span class=\"f\"><a href=\"#m153\">b</a>:<span class=\"f\">13</span></span>,<span class=\"f\"><a href=\"#m159\">c</a>:<span class=\"f\">4</span></span>,<span class=\"f\"><a href=\"#m147\">a</a>:<span class=\"f\">7</span></span>)<span class=\"t\"><a name=\"m134\">{</a>\n" +
                "   <a name=\"m139\">Ext(</a>int <a name=\"m147\">a</a>,int <a name=\"m153\">b</a>,int <a name=\"m159\">c</a>,int <a name=\"m165\">d</a>){\n" +
                "    <span class=\"f\"><a title=\"pkg.Ext.pkg.Ext(int,int,int,int)\" href=\"#m29\">super</a>(<span class=\"f\"><a href=\"#m147\">a</a></span>,<span class=\"f\"><a href=\"#m153\">b</a></span>,<span class=\"f\"><a href=\"#m159\">c</a></span>,<span class=\"f\"><a href=\"#m165\">d</a></span>)</span>;\n" +
                "   }\n" +
                "  }</span></span></span>;\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m104\">e</a></span>.<span class=\"f\"><a title=\"pkg.Ext.field\" href=\"#m21\">field</a></span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage470Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static long m(){\n");
        xml_.append("  Generator g = (Generator)(Fct<long,long>)l -> l * 2;\n");
        xml_.append("  return g.get(a:10);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static long <a name=\"m29\">m</a>(){\n" +
                "  Generator <span class=\"f\"><span class=\"f\"><a name=\"m46\">g</a> </span>=<span class=\"f\"> (Generator)<span class=\"f\">(Fct&lt;long,long&gt;)<span class=\"f\"><span class=\"t\"><a name=\"m77\">l</a> <a name=\"m79\">-&gt;</a> <span class=\"f\"><span class=\"f\"><a href=\"#m77\">l</a> </span>*<span class=\"f\"> 2</span></span></span></span></span></span></span>;\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m46\">g</a></span>.<span class=\"f\">get(<span class=\"f\">a:<span class=\"f\">10</span></span>)</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage471Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" int[] field={0,1};\n");
        xml_.append(" int this(int i){\n");
        xml_.append("  return field[i];\n");
        xml_.append(" }\n");
        xml_.append(" void this(int i){\n");
        xml_.append("  field[i]=value;\n");
        xml_.append(" }\n");
        xml_.append(" static int m(){\n");
        xml_.append("  Ext e = new Ext();\n");
        xml_.append("  return e[i:0];\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " int[] <span class=\"f\"><span class=\"f\"><a name=\"m23\">field</a></span>=<span class=\"f\">{<span class=\"f\">0</span>,<span class=\"f\">1</span>}</span></span>;\n" +
                " int <a name=\"m41\">this</a>(int <a name=\"m50\">i</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext.field\" href=\"#m23\">field</a></span><span class=\"f\">[<span class=\"f\"><a href=\"#m50\">i</a></span>]</span></span>;\n" +
                " }\n" +
                " void <a name=\"m82\">this</a>(int <a name=\"m91\">i</a>){\n" +
                "  <span class=\"n\"><span class=\"n\"><span class=\"n\"><a title=\"pkg.Ext.field\" href=\"#m23\">field</a></span><span class=\"n\">[<span class=\"n\"><a href=\"#m91\">i</a></span>]</span></span>=<span class=\"n\"><b>value</b></span></span>;\n" +
                " }\n" +
                " static int <a name=\"m128\">m</a>(){\n" +
                "  <a title=\"pkg.Ext\" href=\"#m6\">Ext</a> <span class=\"f\"><span class=\"f\"><a name=\"m139\">e</a> </span>=<span class=\"f\"> new <a title=\"pkg.Ext\" href=\"#m6\">Ext</a>()</span></span>;\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m139\">e</a></span><span class=\"f\"><a title=\"pkg.Ext.[](int)\" href=\"#m41\">[</a><span class=\"f\"><a href=\"#m50\">i</a>:<span class=\"f\">0</span></span><a title=\"pkg.Ext.[](int)\" href=\"#m41\">]</a></span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage472Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" int[] field={0,1};\n");
        xml_.append(" int this(int i){\n");
        xml_.append("  return field[i];\n");
        xml_.append(" }\n");
        xml_.append(" void this(int i){\n");
        xml_.append("  field[i]=value;\n");
        xml_.append(" }\n");
        xml_.append(" static int m(){\n");
        xml_.append("  Ext e = new Ext();\n");
        xml_.append("  e[i:0]=15;\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " int[] <span class=\"f\"><span class=\"f\"><a name=\"m23\">field</a></span>=<span class=\"f\">{<span class=\"f\">0</span>,<span class=\"f\">1</span>}</span></span>;\n" +
                " int <a name=\"m41\">this</a>(int <a name=\"m50\">i</a>){\n" +
                "  return <span class=\"n\"><span class=\"n\"><a title=\"pkg.Ext.field\" href=\"#m23\">field</a></span><span class=\"n\">[<span class=\"n\"><a href=\"#m50\">i</a></span>]</span></span>;\n" +
                " }\n" +
                " void <a name=\"m82\">this</a>(int <a name=\"m91\">i</a>){\n" +
                "  <span class=\"f\"><span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext.field\" href=\"#m23\">field</a></span><span class=\"f\">[<span class=\"f\"><a href=\"#m91\">i</a></span>]</span></span>=<span class=\"f\"><b>value</b></span></span>;\n" +
                " }\n" +
                " static int <a name=\"m128\">m</a>(){\n" +
                "  <a title=\"pkg.Ext\" href=\"#m6\">Ext</a> <span class=\"f\"><span class=\"f\"><a name=\"m139\">e</a> </span>=<span class=\"f\"> new <a title=\"pkg.Ext\" href=\"#m6\">Ext</a>()</span></span>;\n" +
                "  <span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m139\">e</a></span><span class=\"f\"><a title=\"pkg.Ext.[]=(int)\" href=\"#m82\">[</a><span class=\"f\"><a href=\"#m91\">i</a>:<span class=\"f\">0</span></span><a title=\"pkg.Ext.[]=(int)\" href=\"#m82\">]</a></span></span>=<span class=\"f\">15</span></span>;\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage473Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" int[] field={0,1};\n");
        xml_.append(" int this(int i){\n");
        xml_.append("  return field[i];\n");
        xml_.append(" }\n");
        xml_.append(" void this(int i){\n");
        xml_.append("  field[i]=value;\n");
        xml_.append(" }\n");
        xml_.append(" static int m(){\n");
        xml_.append("  Ext e = new Ext();\n");
        xml_.append("  e[i:0]++;\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " int[] <span class=\"f\"><span class=\"f\"><a name=\"m23\">field</a></span>=<span class=\"f\">{<span class=\"f\">0</span>,<span class=\"f\">1</span>}</span></span>;\n" +
                " int <a name=\"m41\">this</a>(int <a name=\"m50\">i</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext.field\" href=\"#m23\">field</a></span><span class=\"f\">[<span class=\"f\"><a href=\"#m50\">i</a></span>]</span></span>;\n" +
                " }\n" +
                " void <a name=\"m82\">this</a>(int <a name=\"m91\">i</a>){\n" +
                "  <span class=\"f\"><span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext.field\" href=\"#m23\">field</a></span><span class=\"f\">[<span class=\"f\"><a href=\"#m91\">i</a></span>]</span></span>=<span class=\"f\"><b>value</b></span></span>;\n" +
                " }\n" +
                " static int <a name=\"m128\">m</a>(){\n" +
                "  <a title=\"pkg.Ext\" href=\"#m6\">Ext</a> <span class=\"f\"><span class=\"f\"><a name=\"m139\">e</a> </span>=<span class=\"f\"> new <a title=\"pkg.Ext\" href=\"#m6\">Ext</a>()</span></span>;\n" +
                "  <span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m139\">e</a></span><span class=\"f\"><a title=\"pkg.Ext.[](int)\" href=\"#m41\">[</a><span class=\"f\"><a href=\"#m50\">i</a><a href=\"#m91\">:</a><span class=\"f\">0</span></span><a title=\"pkg.Ext.[](int)\" href=\"#m41\">]</a></span></span>+<a title=\"pkg.Ext.[]=(int)\" href=\"#m82\">+</a></span>;\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage474Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" int[] field={0,1};\n");
        xml_.append(" int this(int i){\n");
        xml_.append("  return field[i];\n");
        xml_.append(" }\n");
        xml_.append(" void this(int i){\n");
        xml_.append("  field[i]=value;\n");
        xml_.append(" }\n");
        xml_.append(" static int m(){\n");
        xml_.append("  Ext e = new Ext();\n");
        xml_.append("  e[i:0]+=15;\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " int[] <span class=\"f\"><span class=\"f\"><a name=\"m23\">field</a></span>=<span class=\"f\">{<span class=\"f\">0</span>,<span class=\"f\">1</span>}</span></span>;\n" +
                " int <a name=\"m41\">this</a>(int <a name=\"m50\">i</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext.field\" href=\"#m23\">field</a></span><span class=\"f\">[<span class=\"f\"><a href=\"#m50\">i</a></span>]</span></span>;\n" +
                " }\n" +
                " void <a name=\"m82\">this</a>(int <a name=\"m91\">i</a>){\n" +
                "  <span class=\"f\"><span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext.field\" href=\"#m23\">field</a></span><span class=\"f\">[<span class=\"f\"><a href=\"#m91\">i</a></span>]</span></span>=<span class=\"f\"><b>value</b></span></span>;\n" +
                " }\n" +
                " static int <a name=\"m128\">m</a>(){\n" +
                "  <a title=\"pkg.Ext\" href=\"#m6\">Ext</a> <span class=\"f\"><span class=\"f\"><a name=\"m139\">e</a> </span>=<span class=\"f\"> new <a title=\"pkg.Ext\" href=\"#m6\">Ext</a>()</span></span>;\n" +
                "  <span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m139\">e</a></span><span class=\"f\"><a title=\"pkg.Ext.[](int)\" href=\"#m41\">[</a><span class=\"f\"><a href=\"#m50\">i</a><a href=\"#m91\">:</a><span class=\"f\">0</span></span><a title=\"pkg.Ext.[](int)\" href=\"#m41\">]</a></span></span>+<a title=\"pkg.Ext.[]=(int)\" href=\"#m82\">=</a><span class=\"f\">15</span></span>;\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage475Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" int[] field={0,1};\n");
        xml_.append(" int this(int i){\n");
        xml_.append("  return field[i];\n");
        xml_.append(" }\n");
        xml_.append(" void this(int i){\n");
        xml_.append("  field[i]=value;\n");
        xml_.append(" }\n");
        xml_.append(" static int m(){\n");
        xml_.append("  Ext e = new Ext();\n");
        xml_.append("  e[i :0]++;\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " int[] <span class=\"f\"><span class=\"f\"><a name=\"m23\">field</a></span>=<span class=\"f\">{<span class=\"f\">0</span>,<span class=\"f\">1</span>}</span></span>;\n" +
                " int <a name=\"m41\">this</a>(int <a name=\"m50\">i</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext.field\" href=\"#m23\">field</a></span><span class=\"f\">[<span class=\"f\"><a href=\"#m50\">i</a></span>]</span></span>;\n" +
                " }\n" +
                " void <a name=\"m82\">this</a>(int <a name=\"m91\">i</a>){\n" +
                "  <span class=\"f\"><span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext.field\" href=\"#m23\">field</a></span><span class=\"f\">[<span class=\"f\"><a href=\"#m91\">i</a></span>]</span></span>=<span class=\"f\"><b>value</b></span></span>;\n" +
                " }\n" +
                " static int <a name=\"m128\">m</a>(){\n" +
                "  <a title=\"pkg.Ext\" href=\"#m6\">Ext</a> <span class=\"f\"><span class=\"f\"><a name=\"m139\">e</a> </span>=<span class=\"f\"> new <a title=\"pkg.Ext\" href=\"#m6\">Ext</a>()</span></span>;\n" +
                "  <span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m139\">e</a></span><span class=\"f\"><a title=\"pkg.Ext.[](int)\" href=\"#m41\">[</a><span class=\"f\"><a href=\"#m50\">i</a> <a href=\"#m91\">:</a><span class=\"f\">0</span></span><a title=\"pkg.Ext.[](int)\" href=\"#m41\">]</a></span></span>+<a title=\"pkg.Ext.[]=(int)\" href=\"#m82\">+</a></span>;\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage476Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" int[] field={0,1};\n");
        xml_.append(" int this(int i){\n");
        xml_.append("  return field[i];\n");
        xml_.append(" }\n");
        xml_.append(" void this(int i){\n");
        xml_.append("  field[i]=value;\n");
        xml_.append(" }\n");
        xml_.append(" static int m(){\n");
        xml_.append("  Ext e = new Ext();\n");
        xml_.append("  e[i :0]+=15;\n");
        xml_.append("  return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " int[] <span class=\"f\"><span class=\"f\"><a name=\"m23\">field</a></span>=<span class=\"f\">{<span class=\"f\">0</span>,<span class=\"f\">1</span>}</span></span>;\n" +
                " int <a name=\"m41\">this</a>(int <a name=\"m50\">i</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext.field\" href=\"#m23\">field</a></span><span class=\"f\">[<span class=\"f\"><a href=\"#m50\">i</a></span>]</span></span>;\n" +
                " }\n" +
                " void <a name=\"m82\">this</a>(int <a name=\"m91\">i</a>){\n" +
                "  <span class=\"f\"><span class=\"f\"><span class=\"f\"><a title=\"pkg.Ext.field\" href=\"#m23\">field</a></span><span class=\"f\">[<span class=\"f\"><a href=\"#m91\">i</a></span>]</span></span>=<span class=\"f\"><b>value</b></span></span>;\n" +
                " }\n" +
                " static int <a name=\"m128\">m</a>(){\n" +
                "  <a title=\"pkg.Ext\" href=\"#m6\">Ext</a> <span class=\"f\"><span class=\"f\"><a name=\"m139\">e</a> </span>=<span class=\"f\"> new <a title=\"pkg.Ext\" href=\"#m6\">Ext</a>()</span></span>;\n" +
                "  <span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m139\">e</a></span><span class=\"f\"><a title=\"pkg.Ext.[](int)\" href=\"#m41\">[</a><span class=\"f\"><a href=\"#m50\">i</a> <a href=\"#m91\">:</a><span class=\"f\">0</span></span><a title=\"pkg.Ext.[](int)\" href=\"#m41\">]</a></span></span>+<a title=\"pkg.Ext.[]=(int)\" href=\"#m82\">=</a><span class=\"f\">15</span></span>;\n" +
                "  return <span class=\"f\">0</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

    @Test
    public void coverage477Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int m(){\n");
        xml_.append("  int c = 5;\n");
        xml_.append("  return m(2,c:c,b:3);\n");
        xml_.append(" }\n");
        xml_.append(" static int m(int a,int b,int c){\n");
        xml_.append("  return a*b+c;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">class <a name=\"m6\">pkg.Ext</a> {\n" +
                " static int <a name=\"m28\">m</a>(){\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m39\">c</a> </span>=<span class=\"f\"> 5</span></span>;\n" +
                "  return <span class=\"f\"><a title=\"pkg.Ext.static m(int,int,int)\" href=\"#m84\">m</a>(<span class=\"f\">2</span>,<span class=\"f\"><a href=\"#m102\">c</a>:<span class=\"f\"><a href=\"#m39\">c</a></span></span>,<span class=\"f\"><a href=\"#m96\">b</a>:<span class=\"f\">3</span></span>)</span>;\n" +
                " }\n" +
                " static int <a name=\"m84\">m</a>(int <a name=\"m90\">a</a>,int <a name=\"m96\">b</a>,int <a name=\"m102\">c</a>){\n" +
                "  return <span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m90\">a</a></span>*<span class=\"f\"><a href=\"#m96\">b</a></span></span>+<span class=\"f\"><a href=\"#m102\">c</a></span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage478Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $annotation pkg.MyAnnot {\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" @MyAnnot\n");
        xml_.append(" $public $static $int field;\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $Annotation[] arr = $class(Ex).getDeclaredFields()[0].getAnnotations();\n");
        xml_.append("  $if (arr.length != 1i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0i]) != $class(MyAnnot)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = cov(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">$public $annotation <a name=\"m20\">pkg.MyAnnot</a> {\n" +
                "}\n" +
                "$public $class <a name=\"m51\">pkg.Ex</a> {\n" +
                " <span class=\"f2\">@<a title=\"pkg.MyAnnot\" href=\"#m20\">MyAnnot</a></span>\n" +
                " $public $static $int <span class=\"g\"><a name=\"m92\">field</a></span>;\n" +
                " $public $static $int <a name=\"m121\">catching</a>(){\n" +
                "  $Annotation[] <span class=\"f\"><span class=\"f\"><a name=\"m149\">arr</a> </span>=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"> $class(<a title=\"pkg.Ex\" href=\"#m51\">Ex</a>)</span>.<span class=\"f\">getDeclaredFields()</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\">getAnnotations()</span></span></span>;\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><a href=\"#m149\">arr</a></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 1i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><a href=\"#m149\">arr</a></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m149\">arr</a></span><span class=\"f\">[<span class=\"f\">0i</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnot\" href=\"#m20\">MyAnnot</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  $return <span class=\"f\">0i</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage479Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $annotation pkg.MyAnnot {\n");
        xml_.append(" @MyAnnot\n");
        xml_.append(" $int field()1;\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $Annotation[] arr = $class(MyAnnot).getDeclaredMethods()[0].getAnnotations();\n");
        xml_.append("  $if (arr.length != 1i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0i]) != $class(MyAnnot)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = cov(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">$public $annotation <a name=\"m20\">pkg.MyAnnot</a> {\n" +
                " <span class=\"f2\">@<a title=\"pkg.MyAnnot\" href=\"#m20\">MyAnnot</a></span>\n" +
                " $int <a name=\"m50\">field</a>()<span class=\"f2\">1</span>;\n" +
                "}\n" +
                "$public $class <a name=\"m77\">pkg.Ex</a> {\n" +
                " $public $static $int <a name=\"m108\">catching</a>(){\n" +
                "  $Annotation[] <span class=\"f\"><span class=\"f\"><a name=\"m136\">arr</a> </span>=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"> $class(<a title=\"pkg.MyAnnot\" href=\"#m20\">MyAnnot</a>)</span>.<span class=\"f\">getDeclaredMethods()</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\">getAnnotations()</span></span></span>;\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><a href=\"#m136\">arr</a></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 1i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><a href=\"#m136\">arr</a></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m136\">arr</a></span><span class=\"f\">[<span class=\"f\">0i</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnot\" href=\"#m20\">MyAnnot</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  $return <span class=\"f\">0i</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage480Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $annotation pkg.MyAnnot {\n");
        xml_.append(" $int field()1;\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" @MyAnnot\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $Annotation[] arr = $class(Ex).getDeclaredMethods()[0].getAnnotations();\n");
        xml_.append("  $if (arr.length != 1i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0i]) != $class(MyAnnot)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = cov(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">$public $annotation <a name=\"m20\">pkg.MyAnnot</a> {\n" +
                " $int <a name=\"m40\">field</a>()<span class=\"f2\">1</span>;\n" +
                "}\n" +
                "$public $class <a name=\"m67\">pkg.Ex</a> {\n" +
                " <span class=\"f2\">@<a title=\"pkg.MyAnnot\" href=\"#m20\">MyAnnot</a></span>\n" +
                " $public $static $int <a name=\"m108\">catching</a>(){\n" +
                "  $Annotation[] <span class=\"f\"><span class=\"f\"><a name=\"m136\">arr</a> </span>=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"> $class(<a title=\"pkg.Ex\" href=\"#m67\">Ex</a>)</span>.<span class=\"f\">getDeclaredMethods()</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\">getAnnotations()</span></span></span>;\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><a href=\"#m136\">arr</a></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 1i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><a href=\"#m136\">arr</a></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m136\">arr</a></span><span class=\"f\">[<span class=\"f\">0i</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnot\" href=\"#m20\">MyAnnot</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  $return <span class=\"f\">0i</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage481Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $annotation pkg.MyAnnotOne {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotTwo {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotThree {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotFour {\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $void catching(@MyAnnotOne@MyAnnotTwo $int a,@MyAnnotThree@MyAnnotFour $int b){\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $Annotation[][] arr = $class(Ex).getDeclaredMethods()[0].getAnnotationsParameters();\n");
        xml_.append("  $if (arr.length != 2i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if (arr[0].length != 2i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if (arr[1].length != 2i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0]) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0][0]) != $class(MyAnnotOne)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0][1]) != $class(MyAnnotTwo)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[1]) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[1][0]) != $class(MyAnnotThree)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[1][1]) != $class(MyAnnotFour)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = cov(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">$public $annotation <a name=\"m20\">pkg.MyAnnotOne</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m59\">pkg.MyAnnotTwo</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m98\">pkg.MyAnnotThree</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m139\">pkg.MyAnnotFour</a> {\n" +
                "}\n" +
                "$public $class <a name=\"m174\">pkg.Ex</a> {\n" +
                " $public $static $void <a name=\"m206\">catching</a>(<span class=\"f2\">@<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a></span><span class=\"f2\">@<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a></span> $int <a name=\"m243\">a</a>,<span class=\"f2\">@<a title=\"pkg.MyAnnotThree\" href=\"#m98\">MyAnnotThree</a></span><span class=\"f2\">@<a title=\"pkg.MyAnnotFour\" href=\"#m139\">MyAnnotFour</a></span> $int <a name=\"m276\">b</a>){\n" +
                " }\n" +
                " $public $static $int <a name=\"m305\">catching</a>(){\n" +
                "  $Annotation[][] <span class=\"f\"><span class=\"f\"><a name=\"m335\">arr</a> </span>=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"> $class(<a title=\"pkg.Ex\" href=\"#m174\">Ex</a>)</span>.<span class=\"f\">getDeclaredMethods()</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\">getAnnotationsParameters()</span></span></span>;\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 2i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 2i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 2i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnotThree\" href=\"#m98\">MyAnnotThree</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnotFour\" href=\"#m139\">MyAnnotFour</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  $return <span class=\"f\">0i</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage482Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $annotation pkg.MyAnnotOne {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotTwo {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotThree {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotFour {\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $void catching(@MyAnnotOne@MyAnnotTwo $int a,@MyAnnotThree@MyAnnotFour $int b){\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $Annotation[][] arr = $class(Ex).getDeclaredMethods()[0].getAnnotationsParameters($class(MyAnnotOne));\n");
        xml_.append("  $if (arr.length != 2i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if (arr[0].length != 1i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if (arr[1].length != 0i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0]) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0][0]) != $class(MyAnnotOne)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[1]) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = cov(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">$public $annotation <a name=\"m20\">pkg.MyAnnotOne</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m59\">pkg.MyAnnotTwo</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m98\">pkg.MyAnnotThree</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m139\">pkg.MyAnnotFour</a> {\n" +
                "}\n" +
                "$public $class <a name=\"m174\">pkg.Ex</a> {\n" +
                " $public $static $void <a name=\"m206\">catching</a>(<span class=\"f2\">@<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a></span><span class=\"n2\">@<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a></span> $int <a name=\"m243\">a</a>,<span class=\"n2\">@<a title=\"pkg.MyAnnotThree\" href=\"#m98\">MyAnnotThree</a></span><span class=\"n2\">@<a title=\"pkg.MyAnnotFour\" href=\"#m139\">MyAnnotFour</a></span> $int <a name=\"m276\">b</a>){\n" +
                " }\n" +
                " $public $static $int <a name=\"m305\">catching</a>(){\n" +
                "  $Annotation[][] <span class=\"f\"><span class=\"f\"><a name=\"m335\">arr</a> </span>=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"> $class(<a title=\"pkg.Ex\" href=\"#m174\">Ex</a>)</span>.<span class=\"f\">getDeclaredMethods()</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\">getAnnotationsParameters(<span class=\"f\">$class(<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a>)</span>)</span></span></span>;\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 2i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 1i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 0i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  $return <span class=\"f\">0i</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage483Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $annotation pkg.MyAnnotOne {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotTwo {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotThree {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotFour {\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $void catching(@MyAnnotOne@MyAnnotTwo $int a,@MyAnnotThree@MyAnnotFour $int b){\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $Annotation[][] arr = $class(Ex).getDeclaredMethods()[0].getAnnotationsParameters($class(MyAnnotTwo));\n");
        xml_.append("  $if (arr.length != 2i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if (arr[0].length != 1i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if (arr[1].length != 0i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0]) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0][0]) != $class(MyAnnotTwo)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[1]) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = cov(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">$public $annotation <a name=\"m20\">pkg.MyAnnotOne</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m59\">pkg.MyAnnotTwo</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m98\">pkg.MyAnnotThree</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m139\">pkg.MyAnnotFour</a> {\n" +
                "}\n" +
                "$public $class <a name=\"m174\">pkg.Ex</a> {\n" +
                " $public $static $void <a name=\"m206\">catching</a>(<span class=\"n2\">@<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a></span><span class=\"f2\">@<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a></span> $int <a name=\"m243\">a</a>,<span class=\"n2\">@<a title=\"pkg.MyAnnotThree\" href=\"#m98\">MyAnnotThree</a></span><span class=\"n2\">@<a title=\"pkg.MyAnnotFour\" href=\"#m139\">MyAnnotFour</a></span> $int <a name=\"m276\">b</a>){\n" +
                " }\n" +
                " $public $static $int <a name=\"m305\">catching</a>(){\n" +
                "  $Annotation[][] <span class=\"f\"><span class=\"f\"><a name=\"m335\">arr</a> </span>=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"> $class(<a title=\"pkg.Ex\" href=\"#m174\">Ex</a>)</span>.<span class=\"f\">getDeclaredMethods()</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\">getAnnotationsParameters(<span class=\"f\">$class(<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a>)</span>)</span></span></span>;\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 2i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 1i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 0i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  $return <span class=\"f\">0i</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage484Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $annotation pkg.MyAnnotOne {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotTwo {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotThree {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotFour {\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $void catching(@MyAnnotOne@MyAnnotTwo $int a,@MyAnnotThree@MyAnnotFour $int b){\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $Annotation[][] arr = $class(Ex).getDeclaredMethods()[0].getAnnotationsParameters($class(MyAnnotThree));\n");
        xml_.append("  $if (arr.length != 2i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if (arr[0].length != 0){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if (arr[1].length != 1){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0]) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[1]) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[1][0]) != $class(MyAnnotThree)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = cov(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">$public $annotation <a name=\"m20\">pkg.MyAnnotOne</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m59\">pkg.MyAnnotTwo</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m98\">pkg.MyAnnotThree</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m139\">pkg.MyAnnotFour</a> {\n" +
                "}\n" +
                "$public $class <a name=\"m174\">pkg.Ex</a> {\n" +
                " $public $static $void <a name=\"m206\">catching</a>(<span class=\"n2\">@<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a></span><span class=\"n2\">@<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a></span> $int <a name=\"m243\">a</a>,<span class=\"f2\">@<a title=\"pkg.MyAnnotThree\" href=\"#m98\">MyAnnotThree</a></span><span class=\"n2\">@<a title=\"pkg.MyAnnotFour\" href=\"#m139\">MyAnnotFour</a></span> $int <a name=\"m276\">b</a>){\n" +
                " }\n" +
                " $public $static $int <a name=\"m305\">catching</a>(){\n" +
                "  $Annotation[][] <span class=\"f\"><span class=\"f\"><a name=\"m335\">arr</a> </span>=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"> $class(<a title=\"pkg.Ex\" href=\"#m174\">Ex</a>)</span>.<span class=\"f\">getDeclaredMethods()</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\">getAnnotationsParameters(<span class=\"f\">$class(<a title=\"pkg.MyAnnotThree\" href=\"#m98\">MyAnnotThree</a>)</span>)</span></span></span>;\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 2i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 0</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 1</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnotThree\" href=\"#m98\">MyAnnotThree</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  $return <span class=\"f\">0i</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage485Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $annotation pkg.MyAnnotOne {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotTwo {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotThree {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotFour {\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $void catching(@MyAnnotOne@MyAnnotTwo $int a,@MyAnnotThree@MyAnnotFour $int b){\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $Annotation[][] arr = $class(Ex).getDeclaredMethods()[0].getAnnotationsParameters($class(MyAnnotFour));\n");
        xml_.append("  $if (arr.length != 2i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if (arr[0].length != 0){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if (arr[1].length != 1){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0]) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[1]) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[1][0]) != $class(MyAnnotFour)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = cov(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">$public $annotation <a name=\"m20\">pkg.MyAnnotOne</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m59\">pkg.MyAnnotTwo</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m98\">pkg.MyAnnotThree</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m139\">pkg.MyAnnotFour</a> {\n" +
                "}\n" +
                "$public $class <a name=\"m174\">pkg.Ex</a> {\n" +
                " $public $static $void <a name=\"m206\">catching</a>(<span class=\"n2\">@<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a></span><span class=\"n2\">@<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a></span> $int <a name=\"m243\">a</a>,<span class=\"n2\">@<a title=\"pkg.MyAnnotThree\" href=\"#m98\">MyAnnotThree</a></span><span class=\"f2\">@<a title=\"pkg.MyAnnotFour\" href=\"#m139\">MyAnnotFour</a></span> $int <a name=\"m276\">b</a>){\n" +
                " }\n" +
                " $public $static $int <a name=\"m305\">catching</a>(){\n" +
                "  $Annotation[][] <span class=\"f\"><span class=\"f\"><a name=\"m335\">arr</a> </span>=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"> $class(<a title=\"pkg.Ex\" href=\"#m174\">Ex</a>)</span>.<span class=\"f\">getDeclaredMethods()</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\">getAnnotationsParameters(<span class=\"f\">$class(<a title=\"pkg.MyAnnotFour\" href=\"#m139\">MyAnnotFour</a>)</span>)</span></span></span>;\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 2i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 0</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 1</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m335\">arr</a></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnotFour\" href=\"#m139\">MyAnnotFour</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  $return <span class=\"f\">0i</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage486Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $annotation pkg.MyAnnotOne {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotTwo {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotThree {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotFour {\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" @MyAnnotOne\n");
        xml_.append(" @MyAnnotTwo\n");
        xml_.append(" @MyAnnotThree\n");
        xml_.append(" @MyAnnotFour\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $Annotation[] arr = $class(Ex).getDeclaredMethods()[0].getAnnotations($class(MyAnnotOne));\n");
        xml_.append("  $if (arr.length != 1i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0]) != $class(MyAnnotOne)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = cov(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">$public $annotation <a name=\"m20\">pkg.MyAnnotOne</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m59\">pkg.MyAnnotTwo</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m98\">pkg.MyAnnotThree</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m139\">pkg.MyAnnotFour</a> {\n" +
                "}\n" +
                "$public $class <a name=\"m174\">pkg.Ex</a> {\n" +
                " <span class=\"f2\">@<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a></span>\n" +
                " <span class=\"n2\">@<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a></span>\n" +
                " <span class=\"n2\">@<a title=\"pkg.MyAnnotThree\" href=\"#m98\">MyAnnotThree</a></span>\n" +
                " <span class=\"n2\">@<a title=\"pkg.MyAnnotFour\" href=\"#m139\">MyAnnotFour</a></span>\n" +
                " $public $static $int <a name=\"m260\">catching</a>(){\n" +
                "  $Annotation[] <span class=\"f\"><span class=\"f\"><a name=\"m288\">arr</a> </span>=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"> $class(<a title=\"pkg.Ex\" href=\"#m174\">Ex</a>)</span>.<span class=\"f\">getDeclaredMethods()</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\">getAnnotations(<span class=\"f\">$class(<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a>)</span>)</span></span></span>;\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><a href=\"#m288\">arr</a></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 1i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><a href=\"#m288\">arr</a></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m288\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  $return <span class=\"f\">0i</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage487Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $annotation pkg.MyAnnotOne {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotTwo {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotThree {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotFour {\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" @MyAnnotOne\n");
        xml_.append(" @MyAnnotTwo\n");
        xml_.append(" @MyAnnotThree\n");
        xml_.append(" @MyAnnotFour\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $Annotation[] arr = $class(Ex).getDeclaredMethods()[0].getAnnotations($class(MyAnnotTwo));\n");
        xml_.append("  $if (arr.length != 1i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0]) != $class(MyAnnotTwo)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = cov(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">$public $annotation <a name=\"m20\">pkg.MyAnnotOne</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m59\">pkg.MyAnnotTwo</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m98\">pkg.MyAnnotThree</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m139\">pkg.MyAnnotFour</a> {\n" +
                "}\n" +
                "$public $class <a name=\"m174\">pkg.Ex</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a></span>\n" +
                " <span class=\"f2\">@<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a></span>\n" +
                " <span class=\"n2\">@<a title=\"pkg.MyAnnotThree\" href=\"#m98\">MyAnnotThree</a></span>\n" +
                " <span class=\"n2\">@<a title=\"pkg.MyAnnotFour\" href=\"#m139\">MyAnnotFour</a></span>\n" +
                " $public $static $int <a name=\"m260\">catching</a>(){\n" +
                "  $Annotation[] <span class=\"f\"><span class=\"f\"><a name=\"m288\">arr</a> </span>=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"> $class(<a title=\"pkg.Ex\" href=\"#m174\">Ex</a>)</span>.<span class=\"f\">getDeclaredMethods()</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\">getAnnotations(<span class=\"f\">$class(<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a>)</span>)</span></span></span>;\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><a href=\"#m288\">arr</a></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 1i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><a href=\"#m288\">arr</a></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m288\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  $return <span class=\"f\">0i</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage488Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $annotation pkg.MyAnnotOne {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotTwo {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotThree {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotFour {\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" @MyAnnotOne\n");
        xml_.append(" @MyAnnotTwo\n");
        xml_.append(" @MyAnnotThree\n");
        xml_.append(" @MyAnnotFour\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $Annotation[] arr = $class(Ex).getDeclaredMethods()[0].getAnnotations($class(MyAnnotThree));\n");
        xml_.append("  $if (arr.length != 1i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0]) != $class(MyAnnotThree)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = cov(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">$public $annotation <a name=\"m20\">pkg.MyAnnotOne</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m59\">pkg.MyAnnotTwo</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m98\">pkg.MyAnnotThree</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m139\">pkg.MyAnnotFour</a> {\n" +
                "}\n" +
                "$public $class <a name=\"m174\">pkg.Ex</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a></span>\n" +
                " <span class=\"n2\">@<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a></span>\n" +
                " <span class=\"f2\">@<a title=\"pkg.MyAnnotThree\" href=\"#m98\">MyAnnotThree</a></span>\n" +
                " <span class=\"n2\">@<a title=\"pkg.MyAnnotFour\" href=\"#m139\">MyAnnotFour</a></span>\n" +
                " $public $static $int <a name=\"m260\">catching</a>(){\n" +
                "  $Annotation[] <span class=\"f\"><span class=\"f\"><a name=\"m288\">arr</a> </span>=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"> $class(<a title=\"pkg.Ex\" href=\"#m174\">Ex</a>)</span>.<span class=\"f\">getDeclaredMethods()</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\">getAnnotations(<span class=\"f\">$class(<a title=\"pkg.MyAnnotThree\" href=\"#m98\">MyAnnotThree</a>)</span>)</span></span></span>;\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><a href=\"#m288\">arr</a></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 1i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><a href=\"#m288\">arr</a></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m288\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnotThree\" href=\"#m98\">MyAnnotThree</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  $return <span class=\"f\">0i</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage489Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $annotation pkg.MyAnnotOne {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotTwo {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotThree {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotFour {\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" @MyAnnotOne\n");
        xml_.append(" @MyAnnotTwo\n");
        xml_.append(" @MyAnnotThree\n");
        xml_.append(" @MyAnnotFour\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $Annotation[] arr = $class(Ex).getDeclaredMethods()[0].getAnnotations($class(MyAnnotFour));\n");
        xml_.append("  $if (arr.length != 1i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0]) != $class(MyAnnotFour)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = cov(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">$public $annotation <a name=\"m20\">pkg.MyAnnotOne</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m59\">pkg.MyAnnotTwo</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m98\">pkg.MyAnnotThree</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m139\">pkg.MyAnnotFour</a> {\n" +
                "}\n" +
                "$public $class <a name=\"m174\">pkg.Ex</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a></span>\n" +
                " <span class=\"n2\">@<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a></span>\n" +
                " <span class=\"n2\">@<a title=\"pkg.MyAnnotThree\" href=\"#m98\">MyAnnotThree</a></span>\n" +
                " <span class=\"f2\">@<a title=\"pkg.MyAnnotFour\" href=\"#m139\">MyAnnotFour</a></span>\n" +
                " $public $static $int <a name=\"m260\">catching</a>(){\n" +
                "  $Annotation[] <span class=\"f\"><span class=\"f\"><a name=\"m288\">arr</a> </span>=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"> $class(<a title=\"pkg.Ex\" href=\"#m174\">Ex</a>)</span>.<span class=\"f\">getDeclaredMethods()</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\">getAnnotations(<span class=\"f\">$class(<a title=\"pkg.MyAnnotFour\" href=\"#m139\">MyAnnotFour</a>)</span>)</span></span></span>;\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><a href=\"#m288\">arr</a></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 1i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><a href=\"#m288\">arr</a></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m288\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnotFour\" href=\"#m139\">MyAnnotFour</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  $return <span class=\"f\">0i</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage490Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $annotation pkg.MyAnnotOne {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotTwo {\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" @MyAnnotOne\n");
        xml_.append(" @MyAnnotTwo\n");
        xml_.append(" @MyAnnotOne\n");
        xml_.append(" @MyAnnotTwo\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $Annotation[] arr = $class(Ex).getDeclaredMethods()[0].getAnnotations($class(MyAnnotOne));\n");
        xml_.append("  $if (arr.length != 2i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0]) != $class(MyAnnotOne)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[1]) != $class(MyAnnotOne)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = cov(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">$public $annotation <a name=\"m20\">pkg.MyAnnotOne</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m59\">pkg.MyAnnotTwo</a> {\n" +
                "}\n" +
                "$public $class <a name=\"m93\">pkg.Ex</a> {\n" +
                " <span class=\"f2\">@<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a></span>\n" +
                " <span class=\"n2\">@<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a></span>\n" +
                " <span class=\"f2\">@<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a></span>\n" +
                " <span class=\"n2\">@<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a></span>\n" +
                " $public $static $int <a name=\"m176\">catching</a>(){\n" +
                "  $Annotation[] <span class=\"f\"><span class=\"f\"><a name=\"m204\">arr</a> </span>=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"> $class(<a title=\"pkg.Ex\" href=\"#m93\">Ex</a>)</span>.<span class=\"f\">getDeclaredMethods()</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\">getAnnotations(<span class=\"f\">$class(<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a>)</span>)</span></span></span>;\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><a href=\"#m204\">arr</a></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 2i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><a href=\"#m204\">arr</a></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m204\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m204\">arr</a></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  $return <span class=\"f\">0i</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage491Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $annotation pkg.MyAnnotOne {\n");
        xml_.append("}\n");
        xml_.append("$public $annotation pkg.MyAnnotTwo {\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" @MyAnnotOne\n");
        xml_.append(" @MyAnnotTwo\n");
        xml_.append(" @MyAnnotOne\n");
        xml_.append(" @MyAnnotTwo\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $Annotation[] arr = $class(Ex).getDeclaredMethods()[0].getAnnotations($class(MyAnnotTwo));\n");
        xml_.append("  $if (arr.length != 2i){\n");
        xml_.append("   $return 3i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr) != $class($Annotation[])){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[0]) != $class(MyAnnotTwo)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $if ($static($Class).getClass(arr[1]) != $class(MyAnnotTwo)){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = cov(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">$public $annotation <a name=\"m20\">pkg.MyAnnotOne</a> {\n" +
                "}\n" +
                "$public $annotation <a name=\"m59\">pkg.MyAnnotTwo</a> {\n" +
                "}\n" +
                "$public $class <a name=\"m93\">pkg.Ex</a> {\n" +
                " <span class=\"n2\">@<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a></span>\n" +
                " <span class=\"f2\">@<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a></span>\n" +
                " <span class=\"n2\">@<a title=\"pkg.MyAnnotOne\" href=\"#m20\">MyAnnotOne</a></span>\n" +
                " <span class=\"f2\">@<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a></span>\n" +
                " $public $static $int <a name=\"m176\">catching</a>(){\n" +
                "  $Annotation[] <span class=\"f\"><span class=\"f\"><a name=\"m204\">arr</a> </span>=<span class=\"f\"><span class=\"f\"><span class=\"f\"><span class=\"f\"> $class(<a title=\"pkg.Ex\" href=\"#m93\">Ex</a>)</span>.<span class=\"f\">getDeclaredMethods()</span></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>.<span class=\"f\">getAnnotations(<span class=\"f\">$class(<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a>)</span>)</span></span></span>;\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\"><a href=\"#m204\">arr</a></span>.<span class=\"f\"><b>length</b> </span></span><a title=\"false\">!=</a><span class=\"f\"> 2i</span></span>){\n" +
                "   $return <span class=\"n\">3i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><a href=\"#m204\">arr</a></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class($Annotation[])</span></span>){\n" +
                "   $return <span class=\"n\">2i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m204\">arr</a></span><span class=\"f\">[<span class=\"f\">0</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  <span class=\"p\">$if</span> (<span class=\"p\"><span class=\"f\"><span class=\"f\">$static($Class)</span>.<span class=\"f\">getClass(<span class=\"f\"><span class=\"f\"><a href=\"#m204\">arr</a></span><span class=\"f\">[<span class=\"f\">1</span>]</span></span>) </span></span><a title=\"false\">!=</a><span class=\"f\"> $class(<a title=\"pkg.MyAnnotTwo\" href=\"#m59\">MyAnnotTwo</a>)</span></span>){\n" +
                "   $return <span class=\"n\">1i</span>;\n" +
                "  }\n" +
                "  $return <span class=\"f\">0i</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage492Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex {\n");
        xml_.append(" public static int exmeth(){\n");
        xml_.append("  for (int i = 0; true ; i++){\n");
        xml_.append("   if (!(i % 2 == 0)){\n");
        xml_.append("    break;\n");
        xml_.append("   }\n");
        xml_.append("  }\n");
        xml_.append("  int s = 1;\n");
        xml_.append("  var toStr = \"\";\n");
        xml_.append("  toStr = toStr + new ExTwo();\n");
        xml_.append("  toStr = new ExTwo() + toStr;\n");
        xml_.append("  return 6;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExTwo {\n");
        xml_.append(" public String $toString() {\n");
        xml_.append("  return null;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("exmeth");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">public class <a name=\"m13\">pkg.Ex</a> {\n" +
                " public static int <a name=\"m41\">exmeth</a>(){\n" +
                "  <span class=\"f\">for</span> (int <span class=\"f\"><span class=\"f\"><a name=\"m62\">i</a> </span>=<span class=\"f\"> 0</span></span>; <span class=\"f\">true</span> ; <span class=\"f\"><span class=\"f\"><a href=\"#m62\">i</a></span>++</span>){\n" +
                "   <span class=\"f\">if</span> (<span class=\"f\">!<span class=\"f\">(<span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m62\">i</a> </span>%<span class=\"f\"> 2 </span></span>==<span class=\"f\"> 0</span></span>)</span></span>){\n" +
                "    break;\n" +
                "   }\n" +
                "  }\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m131\">s</a> </span>=<span class=\"f\"> 1</span></span>;\n" +
                "  <b title=\"$core.String\">var</b> <span class=\"f\"><span class=\"f\"><a name=\"m144\">toStr</a> </span>=<span class=\"f\"> <span class=\"s\">\"\"</span></span></span>;\n" +
                "  <span class=\"f\"><span class=\"f\"><a href=\"#m144\">toStr</a> </span>=<span class=\"f\"><span class=\"f\"> <a href=\"#m144\">toStr</a> </span><i>+</i><span class=\"f\"> new <a title=\"pkg.ExTwo\" href=\"#m248\">ExTwo</a>()</span></span></span>;\n" +
                "  <span class=\"f\"><span class=\"f\"><a href=\"#m144\">toStr</a> </span>=<span class=\"f\"><span class=\"f\"> new <a title=\"pkg.ExTwo\" href=\"#m248\">ExTwo</a>() </span><i>+</i><span class=\"f\"> <a href=\"#m144\">toStr</a></span></span></span>;\n" +
                "  return <span class=\"f\">6</span>;\n" +
                " }\n" +
                "}\n" +
                "public class <a name=\"m248\">pkg.ExTwo</a> {\n" +
                " public String <a name=\"m275\">$toString</a>() {\n" +
                "  return <span class=\"f\">null</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage493Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex {\n");
        xml_.append(" public static int exmeth(){\n");
        xml_.append("  for (int i = 0; ; i++){\n");
        xml_.append("   if (!(i % 2 == 0)){\n");
        xml_.append("    break;\n");
        xml_.append("   }\n");
        xml_.append("  }\n");
        xml_.append("  int s = 1;\n");
        xml_.append("  var toStr = \"\";\n");
        xml_.append("  toStr = toStr + new ExTwo();\n");
        xml_.append("  toStr = new ExTwo() + toStr;\n");
        xml_.append("  return 6;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExTwo {\n");
        xml_.append(" public String $toString() {\n");
        xml_.append("  return null;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEn(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("exmeth");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">public class <a name=\"m13\">pkg.Ex</a> {\n" +
                " public static int <a name=\"m41\">exmeth</a>(){\n" +
                "  for (int <span class=\"f\"><span class=\"f\"><a name=\"m62\">i</a> </span>=<span class=\"f\"> 0</span></span>; ; <span class=\"f\"><span class=\"f\"><a href=\"#m62\">i</a></span>++</span>){\n" +
                "   <span class=\"f\">if</span> (<span class=\"f\">!<span class=\"f\">(<span class=\"f\"><span class=\"f\"><span class=\"f\"><a href=\"#m62\">i</a> </span>%<span class=\"f\"> 2 </span></span>==<span class=\"f\"> 0</span></span>)</span></span>){\n" +
                "    break;\n" +
                "   }\n" +
                "  }\n" +
                "  int <span class=\"f\"><span class=\"f\"><a name=\"m126\">s</a> </span>=<span class=\"f\"> 1</span></span>;\n" +
                "  <b title=\"$core.String\">var</b> <span class=\"f\"><span class=\"f\"><a name=\"m139\">toStr</a> </span>=<span class=\"f\"> <span class=\"s\">\"\"</span></span></span>;\n" +
                "  <span class=\"f\"><span class=\"f\"><a href=\"#m139\">toStr</a> </span>=<span class=\"f\"><span class=\"f\"> <a href=\"#m139\">toStr</a> </span><i>+</i><span class=\"f\"> new <a title=\"pkg.ExTwo\" href=\"#m243\">ExTwo</a>()</span></span></span>;\n" +
                "  <span class=\"f\"><span class=\"f\"><a href=\"#m139\">toStr</a> </span>=<span class=\"f\"><span class=\"f\"> new <a title=\"pkg.ExTwo\" href=\"#m243\">ExTwo</a>() </span><i>+</i><span class=\"f\"> <a href=\"#m139\">toStr</a></span></span></span>;\n" +
                "  return <span class=\"f\">6</span>;\n" +
                " }\n" +
                "}\n" +
                "public class <a name=\"m243\">pkg.ExTwo</a> {\n" +
                " public String <a name=\"m270\">$toString</a>() {\n" +
                "  return <span class=\"f\">null</span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverage494Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static StringBuilder exmeth(){\n");
        xml_.append("  $return $lambda(StringBuilder,$new,$id).call();\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = cov(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("exmeth");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">$public $class <a name=\"m15\">pkg.Ex</a> {\n" +
                " $public $static StringBuilder <a name=\"m55\">exmeth</a>(){\n" +
                "  $return <span class=\"f\"><span class=\"f\">$lambda(StringBuilder,$new,$id)</span>.<span class=\"f\"><b>call</b>()</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }
    @Test
    public void coverageComment17Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("interface pkg.Int {\n");
        xml_.append(" int field();\n");
        xml_.append("}\n");
        xml_.append("class pkg.Ext {\n");
        xml_.append(" static int extField;\n");
        xml_.append(" static int m(){\n");
        xml_.append("  Int l = new Int(){\n");
        xml_.append("   public int field=++extField;\n");
        xml_.append("   public int field(){\n");
        xml_.append("    return\\*comment*\\field;\n");
        xml_.append("   }\n");
        xml_.append("  };\n");
        xml_.append("  return l.field();\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("src/pkg/Ex", xml_.toString());
        ContextEl cont_ = covEnCom(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("m");
        calculateNormal("pkg.Ext", id_, args_, cont_);
        StringMap<String> filesExp_ = ExecFileBlock.export(cont_);
        assertEq("<html><head><link href=\"../../css/style.css\" rel=\"stylesheet\" type=\"text/css\"/></head><body><pre><span class=\"t\">interface <a name=\"m10\">pkg.Int</a> {\n" +
                " int <a name=\"m25\">field</a>();\n" +
                "}\n" +
                "class <a name=\"m42\">pkg.Ext</a> {\n" +
                " static int <span class=\"g\"><a name=\"m64\">extField</a></span>;\n" +
                " static int <a name=\"m86\">m</a>(){\n" +
                "  <a title=\"pkg.Int\" href=\"#m10\">Int</a> <span class=\"f\"><span class=\"f\"><a name=\"m97\">l</a> </span>=<span class=\"f\"> new <a title=\"pkg.Int\" href=\"#m10\">Int</a>()<span class=\"t\"><a name=\"m110\">{</a>\n" +
                "   public int <span class=\"f\"><span class=\"f\"><a name=\"m126\">field</a></span>=<span class=\"f\">++<span class=\"f\"><a title=\"pkg.Ext.extField\" href=\"#m64\">extField</a></span></span></span>;\n" +
                "   public int <a name=\"m158\">field</a>(){\n" +
                "    return<span class=\"c\">\\*comment*\\</span><span class=\"f\"><a title=\"pkg.Ext..Int*1.field\" href=\"#m126\">field</a></span>;\n" +
                "   }\n" +
                "  }</span></span></span>;\n" +
                "  return <span class=\"f\"><span class=\"f\"><a href=\"#m97\">l</a></span>.<span class=\"f\"><a title=\"pkg.Int.field()\" href=\"#m25\">field</a>()</span></span>;\n" +
                " }\n" +
                "}\n" +
                "</span></pre></body></html>", filesExp_.firstValue());
    }

}
