package code.expressionlanguage.methods;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.analyze.blocks.Block;
import code.expressionlanguage.analyze.blocks.NamedFunctionBlock;
import code.expressionlanguage.analyze.blocks.RootBlock;
import code.expressionlanguage.analyze.opers.OperationNode;
import code.expressionlanguage.exec.coverage.*;
import code.expressionlanguage.functionid.MethodId;
import code.util.CustList;
import code.util.EntryCust;
import code.util.IdMap;
import code.util.StringMap;
import code.util.core.BoolVal;
import org.junit.Test;

import static code.expressionlanguage.EquallableElUtil.assertEq;
import static org.junit.Assert.*;

public final class CoverageBisTest extends ProcessMethodCommon {
    @Test
    public void coverage1Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  $long t;\n");
        xml_.append("  t=8;\n");
        xml_.append("  $return 1i+$($int)t;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = covVal2(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("exmeth");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        assertEq(5, getCovers(cont_).size());
        assertEq(0, getCovers(cont_).getValue(0).size());
        assertEq(0, getCovers(cont_).getValue(1).size());
        assertEq(1, getCovers(cont_).getValue(2).size());
        assertEq(3, getCovers(cont_).getValue(3).size());
        assertTrue(getCovers(cont_).getValue(3).firstValue().isFullCovered());
        assertEq(4, getCovers(cont_).getValue(4).size());
    }

    @Test
    public void coverage10Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $annotation pkg.MyAnnot {\n");
        xml_.append("}\n");
        xml_.append("@MyAnnot\n");
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $Annotation[] arr = $class(Ex).getAnnotations();\n");
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
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = covVal2(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        assertEq(10, getSecCovers(cont_).size());
    }
    @Test
    public void coverage11Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $annotation pkg.MyAnnot {\n");
        xml_.append(" $int infoInt()1i;\n");
        xml_.append("}\n");
        xml_.append("@MyAnnot(2i)\n");
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  java.lang.Object arr = $class(MyAnnot).getDeclaredMethods()[0i].getDefaultValue();\n");
        xml_.append("  $if ($static($Class).getClass(arr) != $class(java.lang.Integer)){\n");
        xml_.append("   $return 2i;\n");
        xml_.append("  }\n");
        xml_.append("  java.lang.Integer a = $(java.lang.Integer)arr;\n");
        xml_.append("  $if (a != 1i){\n");
        xml_.append("   $return 1i;\n");
        xml_.append("  }\n");
        xml_.append("  $return 0i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = covVal2(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        assertEq(10, getSecCovers(cont_).size());
    }
    @Test
    public void coverage12Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int st = 0;\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  $long t;\n");
        xml_.append("  t=8;\n");
        xml_.append("  $return 1i+$($int)t;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = covVal2(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("exmeth");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        Coverage coverage_ = cont_.getCoverage();
        Block firstChild_ = coverage_.getFiles().last().getFirstChild().getFirstChild();
        assertEq(1, getFieldCovers(cont_).size());
        assertEq(3, getFieldCovers(cont_).getVal(firstChild_).size());
        assertTrue(getFieldCovers(cont_).getVal(firstChild_).firstValue().isFullCovered());
    }
    @Test
    public void coverage13Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $int t;\n");
        xml_.append("  t=0i;\n");
        xml_.append("  $if(t<0){\n");
        xml_.append("   $return 1i/0i;\n");
        xml_.append("  }\n");
        xml_.append("  $else{\n");
        xml_.append("   t=1i;\n");
        xml_.append("  }\n");
        xml_.append("  $return t;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = covVal2(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        assertEq(9, getCovers(cont_).size());
        assertEq(3, getCovers(cont_).getValue(4).size());
        AbstractCoverageResult value_ = getCovers(cont_).getValue(4).firstValue();
        assertTrue(value_ instanceof BooleanCoverageResult);
        assertTrue(!value_.isFullCovered());
        assertTrue(!((BooleanCoverageResult)value_).isCoverTrue());
        assertTrue(((BooleanCoverageResult)value_).isCoverFalse());
    }
    @Test
    public void coverage14Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $int t;\n");
        xml_.append("  t=0i;\n");
        xml_.append("  $if(t>=0){\n");
        xml_.append("   t=1i;\n");
        xml_.append("   $return t;\n");
        xml_.append("  }\n");
        xml_.append("  $else{\n");
        xml_.append("   $return 1i/0i;\n");
        xml_.append("  }\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = covVal2(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        assertEq(9, getCovers(cont_).size());
        assertEq(3, getCovers(cont_).getValue(4).size());
        AbstractCoverageResult value_ = getCovers(cont_).getValue(4).firstValue();
        assertTrue(value_ instanceof BooleanCoverageResult);
        assertTrue(!value_.isFullCovered());
        assertTrue(((BooleanCoverageResult)value_).isCoverTrue());
        assertTrue(!((BooleanCoverageResult)value_).isCoverFalse());
    }
    @Test
    public void coverage20Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  $return call();\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int call(){\n");
        xml_.append("  $return 1i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = covVal2(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("exmeth");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        assertEq(2, getCovers(cont_).size());
        assertEq(1, getCovers(cont_).getValue(1).size());
        assertTrue(getCovers(cont_).getValue(1).firstValue().isFullCovered());
    }
    @Test
    public void coverage21Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  $return 1i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int call(){\n");
        xml_.append("  $return 1i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = covVal2(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("exmeth");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        assertEq(2, getCalls(cont_).getVal("pkg.Ex").size());
        assertTrue(getCalls(cont_).getVal("pkg.Ex").firstValue());
        assertTrue(!getCalls(cont_).getVal("pkg.Ex").lastValue());
    }
    @Test
    public void coverage22Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$operator+ pkg.Ex (pkg.Ex p, pkg.Ex q){\n");
        xml_.append(" pkg.Ex out = $new pkg.Ex();\n");
        xml_.append(" out.field = p.field+q.field;\n");
        xml_.append(" $return out;\n");
        xml_.append("}\n");
        xml_.append("$operator- pkg.Ex (pkg.Ex p, pkg.Ex q){\n");
        xml_.append(" pkg.Ex out = $new pkg.Ex();\n");
        xml_.append(" out.field = p.field-q.field;\n");
        xml_.append(" $return out;\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $int field;\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  pkg.Ex one = $new pkg.Ex();\n");
        xml_.append("  pkg.Ex two = $new pkg.Ex();\n");
        xml_.append("  one.field = 1;\n");
        xml_.append("  two.field = 2;\n");
        xml_.append("  $return (one+two).field;\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int call(){\n");
        xml_.append("  $return 1i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = covVal2(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("exmeth");
        Argument out_ = calculateNormal("pkg.Ex", id_, args_, cont_);
        assertEq(3, getNumber(out_));
        assertEq(2, getCallsTwo(cont_).getVal("").size());
        assertTrue(getCallsTwo(cont_).getVal("").firstValue());
        assertTrue(!getCallsTwo(cont_).getVal("").lastValue());
    }
    @Test
    public void coverage23Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $int t;\n");
        xml_.append("  t=0i;\n");
        xml_.append("  $try{\n");
        xml_.append("   $if(t>=0){\n");
        xml_.append("    $return 1i/0i;\n");
        xml_.append("   }\n");
        xml_.append("   $return 2;\n");
        xml_.append("  } $catch(Object o){\n");
        xml_.append("   t=1i;\n");
        xml_.append("   $return t;\n");
        xml_.append("  }\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = covVal2(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        assertEq(1, getCatches(cont_).size());
        assertSame(BoolVal.TRUE, getCatches(cont_).firstValue());
    }
    @Test
    public void coverage24Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $int t;\n");
        xml_.append("  t=-1i;\n");
        xml_.append("  $try{\n");
        xml_.append("   $if(t>=0){\n");
        xml_.append("    $return 1i/0i;\n");
        xml_.append("   }\n");
        xml_.append("   $return 2;\n");
        xml_.append("  } $catch(Object o){\n");
        xml_.append("   t=1i;\n");
        xml_.append("   $return t;\n");
        xml_.append("  }\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = covVal2(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        calculateNormal("pkg.Ex", id_, args_, cont_);
        assertEq(1, getCatches(cont_).size());
        assertSame(BoolVal.FALSE, getCatches(cont_).firstValue());
    }

    private static StringMap<IdMap<NamedFunctionBlock, Boolean>> getCalls(ContextEl _cont) {
        StringMap<IdMap<NamedFunctionBlock, Boolean>> ret_ = new StringMap<IdMap<NamedFunctionBlock, Boolean>>();
        for (EntryCust<Block, TypeCoverageResult> e: _cont.getCoverage().getTypes().entryList()) {
            IdMap<NamedFunctionBlock, Boolean> v_ = new IdMap<NamedFunctionBlock, Boolean>();
            for (EntryCust<Block, FunctionCoverageResult> f: e.getValue().getFunctions().entryList()) {
                v_.addEntry((NamedFunctionBlock) f.getKey(),f.getValue().isCalled());
            }
            ret_.addEntry(((RootBlock)e.getKey()).getFullName(), v_);
        }
        return ret_;
    }


    private static StringMap<IdMap<NamedFunctionBlock, Boolean>> getCallsTwo(ContextEl _cont) {
        StringMap<IdMap<NamedFunctionBlock, Boolean>> ret_ = new StringMap<IdMap<NamedFunctionBlock, Boolean>>();
        IdMap<NamedFunctionBlock, Boolean> v_ = new IdMap<NamedFunctionBlock, Boolean>();
        for (EntryCust<Block, FunctionCoverageResult> e: _cont.getCoverage().getOperators().entryList()) {
            v_.addEntry((NamedFunctionBlock) e.getKey(),e.getValue().isCalled());
        }
        ret_.addEntry("", v_);
        return ret_;
    }

    private static IdMap<Block, BoolVal> getCatches(ContextEl _cont) {
        IdMap<Block, TypeCoverageResult> types_ = _cont.getCoverage().getTypes();
        IdMap<Block, TypeCoverageResult> c_ = new IdMap<Block, TypeCoverageResult>();
        for (EntryCust<Block, TypeCoverageResult> e: types_.entryList()) {
            if (e.getKey().getFile().isPredefined()) {
                continue;
            }
            c_.addEntry(e.getKey(),e.getValue());
        }
        return c_.firstValue().getFunctions().firstValue().getCatches();
    }

    private static IdMap<Block, IdMap<OperationNode, AbstractCoverageResult>> getCovers(ContextEl _cont) {
        IdMap<Block, TypeCoverageResult> types_ = _cont.getCoverage().getTypes();
        IdMap<Block, TypeCoverageResult> c_ = new IdMap<Block, TypeCoverageResult>();
        for (EntryCust<Block, TypeCoverageResult> e: types_.entryList()) {
            if (e.getKey().getFile().isPredefined()) {
                continue;
            }
            c_.addEntry(e.getKey(),e.getValue());
        }
        IdMap<Block, BlockCoverageResult> blocks_ = c_.firstValue().getFunctions().firstValue().getBlocks();
        IdMap<Block, IdMap<OperationNode, AbstractCoverageResult>> m_ = new IdMap<Block, IdMap<OperationNode, AbstractCoverageResult>>();
        for (EntryCust<Block, BlockCoverageResult> e: blocks_.entryList()) {
            m_.addEntry(e.getKey(),e.getValue().getCovers());
        }
        return m_;
    }

    private static IdMap<Block, IdMap<OperationNode, AbstractCoverageResult>> getFieldCovers(ContextEl _cont) {
        IdMap<Block, TypeCoverageResult> types_ = _cont.getCoverage().getTypes();
        IdMap<Block, TypeCoverageResult> c_ = new IdMap<Block, TypeCoverageResult>();
        for (EntryCust<Block, TypeCoverageResult> e: types_.entryList()) {
            if (e.getKey().getFile().isPredefined()) {
                continue;
            }
            c_.addEntry(e.getKey(),e.getValue());
        }
        IdMap<Block, BlockCoverageResult> blocks_ = c_.firstValue().getFields();
        IdMap<Block, IdMap<OperationNode, AbstractCoverageResult>> m_ = new IdMap<Block, IdMap<OperationNode, AbstractCoverageResult>>();
        for (EntryCust<Block, BlockCoverageResult> e: blocks_.entryList()) {
            m_.addEntry(e.getKey(),e.getValue().getCovers());
        }
        return m_;
    }
    private static IdMap<Block, IdMap<OperationNode, AbstractCoverageResult>> getSecCovers(ContextEl _cont) {
        IdMap<Block, TypeCoverageResult> types_ = _cont.getCoverage().getTypes();
        IdMap<Block, TypeCoverageResult> c_ = new IdMap<Block, TypeCoverageResult>();
        for (EntryCust<Block, TypeCoverageResult> e: types_.entryList()) {
            if (e.getKey().getFile().isPredefined()) {
                continue;
            }
            c_.addEntry(e.getKey(),e.getValue());
        }
        IdMap<Block, BlockCoverageResult> blocks_ = c_.getValue(1).getFunctions().firstValue().getBlocks();
        IdMap<Block, IdMap<OperationNode, AbstractCoverageResult>> m_ = new IdMap<Block, IdMap<OperationNode, AbstractCoverageResult>>();
        for (EntryCust<Block, BlockCoverageResult> e: blocks_.entryList()) {
            m_.addEntry(e.getKey(),e.getValue().getCovers());
        }
        return m_;
    }

}
