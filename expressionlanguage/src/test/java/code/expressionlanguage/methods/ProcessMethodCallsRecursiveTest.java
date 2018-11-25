package code.expressionlanguage.methods;

import static code.expressionlanguage.EquallableElUtil.assertEq;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.classes.CustLgNames;
import code.expressionlanguage.classes.Ints;
import code.expressionlanguage.opers.util.MethodId;
import code.expressionlanguage.classes.StdStruct;
import code.util.CustList;
import code.util.StringMap;

@SuppressWarnings("static-method")
public final class ProcessMethodCallsRecursiveTest extends ProcessMethodCommon {

    @Test
    public void calculateArgument1013Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  $return exmethsec()+1i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int exmethsec(){\n");
        xml_.append("  $long t:\n");
        xml_.append("  t;.=8:\n");
        xml_.append("  code.expressionlanguage.classes.PickableList p=$new code.expressionlanguage.classes.PickableList():\n");
        xml_.append("  adding(p;.,0):\n");
        xml_.append("  adding(p;.,2):\n");
        xml_.append("  $do{\n");
        xml_.append("   t;.++:\n");
        xml_.append("  }\n");
        xml_.append("  $while(exmethparam(p;.)):\n");
        xml_.append("  $foreach($int i:exmethlist()){\n");
        xml_.append("   $if(i;%2==0i){\n");
        xml_.append("    t;.+=i;:\n");
        xml_.append("   }\n");
        xml_.append("   $else{\n");
        xml_.append("    t;.+=i;+1:\n");
        xml_.append("   }\n");
        xml_.append("  }\n");
        xml_.append("  $return 1i+$($int)t;.+p;.getList().size():\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $void adding(code.expressionlanguage.classes.PickableList l,java.lang.Object o){\n");
        xml_.append("  l;.;getList().add(o;.;):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static code.expressionlanguage.classes.Ints exmethlist(){\n");
        xml_.append("  code.expressionlanguage.classes.Ints t:\n");
        xml_.append("  t;.=$new code.expressionlanguage.classes.Ints():\n");
        xml_.append("  t;.add(8i):\n");
        xml_.append("  t;.add(2i):\n");
        xml_.append("  t;.add(1i):\n");
        xml_.append("  $return t;.:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $boolean exmethparam(code.expressionlanguage.classes.PickableList l){\n");
        xml_.append("  $return l;.;removeAndExistAfter(1i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int factrec($int l){\n");
        xml_.append("  $if(l;.;<=0){\n");
        xml_.append("   $return 1i:\n");
        xml_.append("  }\n");
        xml_.append("  $return l;.;*factrec(l;.;-1i):\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl(true,false);
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("factrec", cont_.getStandards().getAliasPrimInteger());
        Argument v_ = new Argument();
        v_.setObject(5);
        args_.add(v_);
        Argument ret_ = new Argument();
        ret_ = calculateArgument("pkg.Ex", id_, args_, cont_);
        assertEq(120, (Number)ret_.getObject());
    }

    @Test
    public void calculateArgument1014Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  $return exmethsec()+1i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int exmethsec(){\n");
        xml_.append("  $long t:\n");
        xml_.append("  t;.=8:\n");
        xml_.append("  code.expressionlanguage.classes.PickableList p=$new code.expressionlanguage.classes.PickableList():\n");
        xml_.append("  adding(p;.,0):\n");
        xml_.append("  adding(p;.,2):\n");
        xml_.append("  $do{\n");
        xml_.append("   t;.++:\n");
        xml_.append("  }\n");
        xml_.append("  $while(exmethparam(p;.)):\n");
        xml_.append("  $foreach($int i:exmethlist()){\n");
        xml_.append("   $if(i;%2==0i){\n");
        xml_.append("    t;.+=i;:\n");
        xml_.append("   }\n");
        xml_.append("   $else{\n");
        xml_.append("    t;.+=i;+1:\n");
        xml_.append("   }\n");
        xml_.append("  }\n");
        xml_.append("  $return 1i+$($int)t;.+p;.getList().size():\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $void adding(code.expressionlanguage.classes.PickableList l,java.lang.Object o){\n");
        xml_.append("  l;.;getList().add(o;.;):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static code.expressionlanguage.classes.Ints exmethlist(){\n");
        xml_.append("  code.expressionlanguage.classes.Ints t:\n");
        xml_.append("  t;.=$new code.expressionlanguage.classes.Ints():\n");
        xml_.append("  t;.add(8i):\n");
        xml_.append("  t;.add(2i):\n");
        xml_.append("  t;.add(1i):\n");
        xml_.append("  $return t;.:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $boolean exmethparam(code.expressionlanguage.classes.PickableList l){\n");
        xml_.append("  $return l;.;removeAndExistAfter(1i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int factrec($int l){\n");
        xml_.append("  $if(l;.;<=0){\n");
        xml_.append("   $return 1i:\n");
        xml_.append("  }\n");
        xml_.append("  $return l;.;*factrec(l;.;-1i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $void addelt(code.expressionlanguage.classes.Ints l,$int e){\n");
        xml_.append("  l;.;add(e;.;):\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl(true,false);
        CustLgNames custLgNames_ = (CustLgNames) cont_.getStandards();
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("addelt", CUST, cont_.getStandards().getAliasPrimInteger());
        Argument v_ = new Argument();
        Ints l_ = new Ints();
        v_.setStruct(new StdStruct(l_, custLgNames_.getAliasInts()));
        args_.add(v_);
        v_ = new Argument();
        v_.setObject(5);
        args_.add(v_);
        Argument ret_ = new Argument();
        assertEq(0, l_.size());
        ret_ = calculateArgument("pkg.Ex", id_, args_, cont_);
        assertNull(ret_.getObject());
        assertEq(1, l_.size());
        assertEq(5, l_.first());
    }

    @Test
    public void calculateArgument1015Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  $return exmethsec()+1i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int exmethsec(){\n");
        xml_.append("  $long t:\n");
        xml_.append("  t;.=8:\n");
        xml_.append("  code.expressionlanguage.classes.PickableList p=$new code.expressionlanguage.classes.PickableList():\n");
        xml_.append("  adding(p;.,0):\n");
        xml_.append("  adding(p;.,2):\n");
        xml_.append("  $do{\n");
        xml_.append("   t;.++:\n");
        xml_.append("  }\n");
        xml_.append("  $while(exmethparam(p;.)):\n");
        xml_.append("  $foreach($int i:exmethlist()){\n");
        xml_.append("   $if(i;%2==0i){\n");
        xml_.append("    t;.+=i;:\n");
        xml_.append("   }\n");
        xml_.append("   $else{\n");
        xml_.append("    t;.+=i;+1:\n");
        xml_.append("   }\n");
        xml_.append("  }\n");
        xml_.append("  $return 1i+$($int)t;.+p;.getList().size():\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $void adding(code.expressionlanguage.classes.PickableList l,java.lang.Object o){\n");
        xml_.append("  l;.;getList().add(o;.;):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static code.expressionlanguage.classes.Ints exmethlist(){\n");
        xml_.append("  code.expressionlanguage.classes.Ints t:\n");
        xml_.append("  t;.=$new code.expressionlanguage.classes.Ints():\n");
        xml_.append("  t;.add(8i):\n");
        xml_.append("  t;.add(2i):\n");
        xml_.append("  t;.add(1i):\n");
        xml_.append("  $return t;.:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $boolean exmethparam(code.expressionlanguage.classes.PickableList l){\n");
        xml_.append("  $return l;.;removeAndExistAfter(1i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int factrec($int l){\n");
        xml_.append("  $if(l;.;<=0){\n");
        xml_.append("   $return 1i:\n");
        xml_.append("  }\n");
        xml_.append("  $return l;.;*factrec(l;.;-1i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $void addelt(code.expressionlanguage.classes.Ints l,$int e){\n");
        xml_.append("  l;.;add(e;.;):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $try{\n");
        xml_.append("   $return badMethod():\n");
        xml_.append("  }\n");
        xml_.append("  $catch(java.lang.Exception e){\n");
        xml_.append("   $return 1i:\n");
        xml_.append("  }\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int badMethod(){\n");
        xml_.append("  $return 1i/0i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl(true,false);
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        Argument ret_ = new Argument();
        ret_ = calculateArgument("pkg.Ex", id_, args_, cont_);
        assertEq(1, (Number)ret_.getObject());
    }

    @Test
    public void calculateArgument1016Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  $return exmethsec()+1i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int exmethsec(){\n");
        xml_.append("  $long t:\n");
        xml_.append("  t;.=8:\n");
        xml_.append("  code.expressionlanguage.classes.PickableList p=$new code.expressionlanguage.classes.PickableList():\n");
        xml_.append("  adding(p;.,0):\n");
        xml_.append("  adding(p;.,2):\n");
        xml_.append("  $do{\n");
        xml_.append("   t;.++:\n");
        xml_.append("  }\n");
        xml_.append("  $while(exmethparam(p;.)):\n");
        xml_.append("  $foreach($int i:exmethlist()){\n");
        xml_.append("   $if(i;%2==0i){\n");
        xml_.append("    t;.+=i;:\n");
        xml_.append("   }\n");
        xml_.append("   $else{\n");
        xml_.append("    t;.+=i;+1:\n");
        xml_.append("   }\n");
        xml_.append("  }\n");
        xml_.append("  $return 1i+$($int)t;.+p;.getList().size():\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $void adding(code.expressionlanguage.classes.PickableList l,java.lang.Object o){\n");
        xml_.append("  l;.;getList().add(o;.;):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static code.expressionlanguage.classes.Ints exmethlist(){\n");
        xml_.append("  code.expressionlanguage.classes.Ints t:\n");
        xml_.append("  t;.=$new code.expressionlanguage.classes.Ints():\n");
        xml_.append("  t;.add(8i):\n");
        xml_.append("  t;.add(2i):\n");
        xml_.append("  t;.add(1i):\n");
        xml_.append("  $return t;.:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $boolean exmethparam(code.expressionlanguage.classes.PickableList l){\n");
        xml_.append("  $return l;.;removeAndExistAfter(1i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int factrec($int l){\n");
        xml_.append("  $if(l;.;<=0){\n");
        xml_.append("   $return 1i:\n");
        xml_.append("  }\n");
        xml_.append("  $return l;.;*factrec(l;.;-1i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $void addelt(code.expressionlanguage.classes.Ints l,$int e){\n");
        xml_.append("  l;.;add(e;.;):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $try{\n");
        xml_.append("   $return 0i:\n");
        xml_.append("  }\n");
        xml_.append("  $finally{\n");
        xml_.append("   $return 1i:\n");
        xml_.append("  }\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int badMethod(){\n");
        xml_.append("  $return 1i/0i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int simpleMethod(){\n");
        xml_.append("  $return 1i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl(true,false);
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        Argument ret_ = new Argument();
        ret_ = calculateArgument("pkg.Ex", id_, args_, cont_);
        assertEq(1, (Number)ret_.getObject());
    }


    @Test
    public void calculateArgument1017Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  $return exmethsec()+1i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int exmethsec(){\n");
        xml_.append("  $long t:\n");
        xml_.append("  t;.=8:\n");
        xml_.append("  code.expressionlanguage.classes.PickableList p=$new code.expressionlanguage.classes.PickableList():\n");
        xml_.append("  adding(p;.,0):\n");
        xml_.append("  adding(p;.,2):\n");
        xml_.append("  $do{\n");
        xml_.append("   t;.++:\n");
        xml_.append("  }\n");
        xml_.append("  $while(exmethparam(p;.)):\n");
        xml_.append("  $foreach($int i:exmethlist()){\n");
        xml_.append("   $if(i;%2==0i){\n");
        xml_.append("    t;.+=i;:\n");
        xml_.append("   }\n");
        xml_.append("   $else{\n");
        xml_.append("    t;.+=i;+1:\n");
        xml_.append("   }\n");
        xml_.append("  }\n");
        xml_.append("  $return 1i+$($int)t;.+p;.getList().size():\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $void adding(code.expressionlanguage.classes.PickableList l,java.lang.Object o){\n");
        xml_.append("  l;.;getList().add(o;.;):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static code.expressionlanguage.classes.Ints exmethlist(){\n");
        xml_.append("  code.expressionlanguage.classes.Ints t:\n");
        xml_.append("  t;.=$new code.expressionlanguage.classes.Ints():\n");
        xml_.append("  t;.add(8i):\n");
        xml_.append("  t;.add(2i):\n");
        xml_.append("  t;.add(1i):\n");
        xml_.append("  $return t;.:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $boolean exmethparam(code.expressionlanguage.classes.PickableList l){\n");
        xml_.append("  $return l;.;removeAndExistAfter(1i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int factrec($int l){\n");
        xml_.append("  $if(l;.;<=0){\n");
        xml_.append("   $return 1i:\n");
        xml_.append("  }\n");
        xml_.append("  $return l;.;*factrec(l;.;-1i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $void addelt(code.expressionlanguage.classes.Ints l,$int e){\n");
        xml_.append("  l;.;add(e;.;):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $try{\n");
        xml_.append("   $return 0i:\n");
        xml_.append("  }\n");
        xml_.append("  $finally{\n");
        xml_.append("   simpleMethod():\n");
        xml_.append("  }\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int badMethod(){\n");
        xml_.append("  $return 1i/0i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int simpleMethod(){\n");
        xml_.append("  $return 1i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl(true,false);
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
    public void calculateArgument1018Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  $return exmethsec()+1i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int exmethsec(){\n");
        xml_.append("  $long t:\n");
        xml_.append("  t;.=8:\n");
        xml_.append("  code.expressionlanguage.classes.PickableList p=$new code.expressionlanguage.classes.PickableList():\n");
        xml_.append("  adding(p;.,0):\n");
        xml_.append("  adding(p;.,2):\n");
        xml_.append("  $do{\n");
        xml_.append("   t;.++:\n");
        xml_.append("  }\n");
        xml_.append("  $while(exmethparam(p;.)):\n");
        xml_.append("  $foreach($int i:exmethlist()){\n");
        xml_.append("   $if(i;%2==0i){\n");
        xml_.append("    t;.+=i;:\n");
        xml_.append("   }\n");
        xml_.append("   $else{\n");
        xml_.append("    t;.+=i;+1:\n");
        xml_.append("   }\n");
        xml_.append("  }\n");
        xml_.append("  $return 1i+$($int)t;.+p;.getList().size():\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $void adding(code.expressionlanguage.classes.PickableList l,java.lang.Object o){\n");
        xml_.append("  l;.;getList().add(o;.;):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static code.expressionlanguage.classes.Ints exmethlist(){\n");
        xml_.append("  code.expressionlanguage.classes.Ints t:\n");
        xml_.append("  t;.=$new code.expressionlanguage.classes.Ints():\n");
        xml_.append("  t;.add(8i):\n");
        xml_.append("  t;.add(2i):\n");
        xml_.append("  t;.add(1i):\n");
        xml_.append("  $return t;.:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $boolean exmethparam(code.expressionlanguage.classes.PickableList l){\n");
        xml_.append("  $return l;.;removeAndExistAfter(1i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int factrec($int l){\n");
        xml_.append("  $if(l;.;<=0){\n");
        xml_.append("   $return 1i:\n");
        xml_.append("  }\n");
        xml_.append("  $return l;.;*factrec(l;.;-1i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $void addelt(code.expressionlanguage.classes.Ints l,$int e){\n");
        xml_.append("  l;.;add(e;.;):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $try{\n");
        xml_.append("   $return betterMethod():\n");
        xml_.append("  }\n");
        xml_.append("  $catch(java.lang.Exception e){\n");
        xml_.append("   $return 1i:\n");
        xml_.append("  }\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int betterMethod(){\n");
        xml_.append("  $return 2i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl(true,false);
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        Argument ret_ = new Argument();
        ret_ = calculateArgument("pkg.Ex", id_, args_, cont_);
        assertEq(2, (Number)ret_.getObject());
    }

    @Test
    public void calculateArgument1019Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  $return exmethsec()+1i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int exmethsec(){\n");
        xml_.append("  $long t:\n");
        xml_.append("  t;.=8:\n");
        xml_.append("  code.expressionlanguage.classes.PickableList p=$new code.expressionlanguage.classes.PickableList():\n");
        xml_.append("  adding(p;.,0):\n");
        xml_.append("  adding(p;.,2):\n");
        xml_.append("  $do{\n");
        xml_.append("   t;.++:\n");
        xml_.append("  }\n");
        xml_.append("  $while(exmethparam(p;.)):\n");
        xml_.append("  $foreach($int i:exmethlist()){\n");
        xml_.append("   $if(i;%2==0i){\n");
        xml_.append("    t;.+=i;:\n");
        xml_.append("   }\n");
        xml_.append("   $else{\n");
        xml_.append("    t;.+=i;+1:\n");
        xml_.append("   }\n");
        xml_.append("  }\n");
        xml_.append("  $return 1i+$($int)t;.+p;.getList().size():\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $void adding(code.expressionlanguage.classes.PickableList l,java.lang.Object o){\n");
        xml_.append("  l;.;getList().add(o;.;):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static code.expressionlanguage.classes.Ints exmethlist(){\n");
        xml_.append("  code.expressionlanguage.classes.Ints t:\n");
        xml_.append("  t;.=$new code.expressionlanguage.classes.Ints():\n");
        xml_.append("  t;.add(8i):\n");
        xml_.append("  t;.add(2i):\n");
        xml_.append("  t;.add(1i):\n");
        xml_.append("  $return t;.:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $boolean exmethparam(code.expressionlanguage.classes.PickableList l){\n");
        xml_.append("  $return l;.;removeAndExistAfter(1i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int factrec($int l){\n");
        xml_.append("  $if(l;.;<=0){\n");
        xml_.append("   $return 1i:\n");
        xml_.append("  }\n");
        xml_.append("  $return l;.;*factrec(l;.;-1i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $void addelt(code.expressionlanguage.classes.Ints l,$int e){\n");
        xml_.append("  l;.;add(e;.;):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $try{\n");
        xml_.append("   $return badMethod():\n");
        xml_.append("  }\n");
        xml_.append("  $catch(java.lang.Exception e){\n");
        xml_.append("   $return 1i:\n");
        xml_.append("  }\n");
        xml_.append("  $finally{\n");
        xml_.append("   $return 2i:\n");
        xml_.append("  }\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int badMethod(){\n");
        xml_.append("  $return 1i/0i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl(true,false);
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        Argument ret_ = new Argument();
        ret_ = calculateArgument("pkg.Ex", id_, args_, cont_);
        assertEq(2,(Number) ret_.getObject());
    }

    @Test
    public void calculateArgument1020Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  $return exmethsec()+1i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int exmethsec(){\n");
        xml_.append("  $long t:\n");
        xml_.append("  t;.=8:\n");
        xml_.append("  code.expressionlanguage.classes.PickableList p=$new code.expressionlanguage.classes.PickableList():\n");
        xml_.append("  adding(p;.,0):\n");
        xml_.append("  adding(p;.,2):\n");
        xml_.append("  $do{\n");
        xml_.append("   t;.++:\n");
        xml_.append("  }\n");
        xml_.append("  $while(exmethparam(p;.)):\n");
        xml_.append("  $foreach($int i:exmethlist()){\n");
        xml_.append("   $if(i;%2==0i){\n");
        xml_.append("    t;.+=i;:\n");
        xml_.append("   }\n");
        xml_.append("   $else{\n");
        xml_.append("    t;.+=i;+1:\n");
        xml_.append("   }\n");
        xml_.append("  }\n");
        xml_.append("  $return 1i+$($int)t;.+p;.getList().size():\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $void adding(code.expressionlanguage.classes.PickableList l,java.lang.Object o){\n");
        xml_.append("  l;.;getList().add(o;.;):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static code.expressionlanguage.classes.Ints exmethlist(){\n");
        xml_.append("  code.expressionlanguage.classes.Ints t:\n");
        xml_.append("  t;.=$new code.expressionlanguage.classes.Ints():\n");
        xml_.append("  t;.add(8i):\n");
        xml_.append("  t;.add(2i):\n");
        xml_.append("  t;.add(1i):\n");
        xml_.append("  $return t;.:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $boolean exmethparam(code.expressionlanguage.classes.PickableList l){\n");
        xml_.append("  $return l;.;removeAndExistAfter(1i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int factrec($int l){\n");
        xml_.append("  $if(l;.;<=0){\n");
        xml_.append("   $return 1i:\n");
        xml_.append("  }\n");
        xml_.append("  $return l;.;*factrec(l;.;-1i):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $void addelt(code.expressionlanguage.classes.Ints l,$int e){\n");
        xml_.append("  l;.;add(e;.;):\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int catching(){\n");
        xml_.append("  $try{\n");
        xml_.append("   $return badMethod():\n");
        xml_.append("  }\n");
        xml_.append("  $catch(java.lang.Exception e):\n");
        xml_.append("  $return 2i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int badMethod(){\n");
        xml_.append("  $return 1i/0i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl(true,false);
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("catching");
        Argument ret_ = new Argument();
        ret_ = calculateArgument("pkg.Ex", id_, args_, cont_);
        assertEq(2,(Number) ret_.getObject());
    }
    @Test
    public void calculateArgument1021Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  $return exmethsec()+1i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int exmethsec(){\n");
        xml_.append("  $long t:\n");
        xml_.append("  t;.=8:\n");
        xml_.append("  $foreach($int i:exmethlist()){\n");
        xml_.append("   $foreach($int j:exmethlist()){\n");
        xml_.append("    t;.+=j;:\n");
        xml_.append("   }\n");
        xml_.append("  }\n");
        xml_.append("  $return 1i+$($int)t;.:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static code.expressionlanguage.classes.Ints exmethlist(){\n");
        xml_.append("  code.expressionlanguage.classes.Ints t:\n");
        xml_.append("  t;.=$new code.expressionlanguage.classes.Ints():\n");
        xml_.append("  t;.add(8i):\n");
        xml_.append("  t;.add(2i):\n");
        xml_.append("  $return t;.:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl();
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("exmeth");
        Argument ret_ = new Argument();
        ret_ = calculateArgument("pkg.Ex", id_, args_, cont_);
        assertEq(30,(Number) ret_.getObject());
    }
    @Test
    public void calculateArgument1022Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  $return exmethsec()+1i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int exmethsec(){\n");
        xml_.append("  $long t:\n");
        xml_.append("  t;.=8:\n");
        xml_.append("  $foreach($int i:exmethlist()){\n");
        xml_.append("   $foreach($int j:exmethlist()){\n");
        xml_.append("    t;.+=j;:\n");
        xml_.append("   }\n");
        xml_.append("  }\n");
        xml_.append("  $return 1i+$($int)t;.:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static code.expressionlanguage.classes.Ints exmethlist(){\n");
        xml_.append("  code.expressionlanguage.classes.Ints t:\n");
        xml_.append("  t;.=$new code.expressionlanguage.classes.Ints():\n");
        xml_.append("  t;.add(8i):\n");
        xml_.append("  t;.add(2i):\n");
        xml_.append("  $return t;.:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int calling(){\n");
        xml_.append("  emptymeth():\n");
        xml_.append("  $return 18i:\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $void emptymeth(){}\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl();
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("calling");
        Argument ret_ = new Argument();
        ret_ = calculateArgument("pkg.Ex", id_, args_, cont_);
        assertEq(18, (Number)ret_.getObject());
    }
    @Test
    public void calculateArgument1023Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  $long t:\n");
        xml_.append("  t;.=8:\n");
        xml_.append("  $if(t;.%2==0){\n");
        xml_.append("   t;.+=8:\n");
        xml_.append("   $return 1i+$($int)t;.:\n");
        xml_.append("  }\n");
        xml_.append("  $elseif(t;.<0){\n");
        xml_.append("   t;.+=2:\n");
        xml_.append("   $return 1i+$($int)t;.:\n");
        xml_.append("  }\n");
        xml_.append("  $if(t;.%2==0){\n");
        xml_.append("   t;.+=8:\n");
        xml_.append("   $return 1i+$($int)t;.:\n");
        xml_.append("  }\n");
        xml_.append("  $else{\n");
        xml_.append("   t;.+=2:\n");
        xml_.append("   $return 1i+$($int)t;.:\n");
        xml_.append("  }\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl(true,false);
        files_.put("pkg/Ex", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("exmeth");
        Argument ret_ = new Argument();
        ret_ = calculateArgument("pkg.Ex", id_, args_, cont_);
        assertEq(17, (Number)ret_.getObject());
    }

    @Test
    public void calculateArgument1024Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  $return $static(pkg.ExTwo).exmeth()+8i:\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = contextEl(true,false);
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $static $int exmeth(){\n");
        xml_.append("  $long t:\n");
        xml_.append("  t;.=8:\n");
        xml_.append("  $if(t;.%2==0){\n");
        xml_.append("   t;.+=8:\n");
        xml_.append("   $return 1i+$($int)t;.:\n");
        xml_.append("  }\n");
        xml_.append("  $elseif(t;.<0){\n");
        xml_.append("   t;.+=2:\n");
        xml_.append("   $return 1i+$($int)t;.:\n");
        xml_.append("  }\n");
        xml_.append("  $if(t;.%2==0){\n");
        xml_.append("   t;.+=8:\n");
        xml_.append("   $return 1i+$($int)t;.:\n");
        xml_.append("  }\n");
        xml_.append("  $else{\n");
        xml_.append("   t;.+=2:\n");
        xml_.append("   $return 1i+$($int)t;.:\n");
        xml_.append("  }\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        Classes.validateAll(files_, cont_);
        assertTrue(cont_.getClasses().isEmptyErrors());
        CustList<Argument> args_ = new CustList<Argument>();
        MethodId id_ = getMethodId("exmeth");
        Argument ret_ = new Argument();
        ret_ = calculateArgument("pkg.Ex", id_, args_, cont_);
        assertEq(25, (Number)ret_.getObject());
    }

}
