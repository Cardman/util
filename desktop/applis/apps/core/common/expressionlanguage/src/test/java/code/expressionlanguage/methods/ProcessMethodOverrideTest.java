package code.expressionlanguage.methods;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.ClassField;
import code.expressionlanguage.functionid.ConstructorId;
import code.expressionlanguage.structs.NumberStruct;
import code.expressionlanguage.structs.StringStruct;
import code.expressionlanguage.structs.Struct;
import code.util.CustList;
import code.util.StringMap;
import org.junit.Test;

import static code.expressionlanguage.EquallableElUtil.assertEq;
import static org.junit.Assert.assertTrue;

public final class ProcessMethodOverrideTest extends ProcessMethodCommon {
    @Test
    public void calculate1Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkg.ExTwo{\n");
        xml_.append(" $intern{getter():getter(Ex)|getter(ExTwo)};\n");
        xml_.append(" $public $int inst=2i;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  $super(16i);\n");
        xml_.append("  inst=i;\n");
        xml_.append("  inst+=getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  $return $new Ex(9).inst;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $int sec;\n");
        xml_.append(" $package ($int i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(18, getNumber(ret_));
    }
    @Test
    public void calculate2Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<S> :pkg.ExTwo<S>{\n");
        xml_.append(" $intern{getter(S):getter(Ex,S)|getter(ExTwo,T)};\n");
        xml_.append(" $public S inst=(S)2i;\n");
        xml_.append(" $public (S i){\n");
        xml_.append("  $super((S)16i);\n");
        xml_.append("  inst=i;\n");
        xml_.append("  inst=(S)(($int)inst+($int)getter((S)(($int)i+10)));\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  $return $new Ex<$int>(9).inst;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal S getter(S t){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<T> {\n");
        xml_.append(" $public T sec;\n");
        xml_.append(" $package (T i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal T getter(T t){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(18, getNumber(ret_));
    }
    @Test
    public void calculate3Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkg.ExTwo{\n");
        xml_.append(" $intern{getter():getter(Ex)};\n");
        xml_.append(" $public $int inst=2i;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  $super(16i);\n");
        xml_.append("  inst=i;\n");
        xml_.append("  inst+=getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  $return $new Ex(9).inst;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $int sec;\n");
        xml_.append(" $package ($int i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int getter(){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(18, getNumber(ret_));
    }
    @Test
    public void calculate4Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkg.ExTwo{\n");
        xml_.append(" $intern{getter():getter(Ex)};\n");
        xml_.append(" $public $int inst=2i;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  $super(16i);\n");
        xml_.append("  inst=i;\n");
        xml_.append("  inst+=getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  ExTwo e = $new Ex(9);\n");
        xml_.append("  $return e.getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $int sec;\n");
        xml_.append(" $package ($int i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int getter(){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(16, getNumber(ret_));
    }
    @Test
    public void calculate5Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkg.ExTwo{\n");
        xml_.append(" $intern{getter():getter(Ex)};\n");
        xml_.append(" $public $int inst=2i;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  $super(16i);\n");
        xml_.append("  inst=i;\n");
        xml_.append("  inst+=getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  ExTwo e = $new Ex(9);\n");
        xml_.append("  $return e.getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $int sec;\n");
        xml_.append(" $package ($int i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(16, getNumber(ret_));
    }
    @Test
    public void calculate6Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkg.ExTwo{\n");
        xml_.append(" $intern{getter():getter(Ex)};\n");
        xml_.append(" $public $int inst=2i;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  $super(16i);\n");
        xml_.append("  inst=i;\n");
        xml_.append("  inst+=getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  Ex e = $new Ex(9);\n");
        xml_.append("  $return e.getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $int sec;\n");
        xml_.append(" $package ($int i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(18, getNumber(ret_));
    }
    @Test
    public void calculate7Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree<U> :pkg.Ex<U>{\n");
        xml_.append(" $intern{getter(U):getter(Ex,S)|getter(ExTwo,T)};\n");
        xml_.append(" $public (U i){\n");
        xml_.append("  $super(i);\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  $return $new ExThree<$int>(9).inst;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.Ex<S> :pkg.ExTwo<S>{\n");
        xml_.append(" $intern{getter(S):getter(Ex,S)|getter(ExTwo,T)};\n");
        xml_.append(" $public S inst=(S)2i;\n");
        xml_.append(" $public (S i){\n");
        xml_.append("  $super((S)16i);\n");
        xml_.append("  inst=i;\n");
        xml_.append("  inst=(S)(($int)inst+($int)getter((S)(($int)i+10)));\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal S getter(S t){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<T> {\n");
        xml_.append(" $public T sec;\n");
        xml_.append(" $package (T i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal T getter(T t){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.ExThree",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(18, getNumber(ret_));
    }
    @Test
    public void calculate8Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkg.ExTwo{\n");
        xml_.append(" $intern{getter()};\n");
        xml_.append(" $public $int inst=2i;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  $super(16i);\n");
        xml_.append("  inst=i;\n");
        xml_.append("  inst+=getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  $return $new Ex(9).inst;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $int sec;\n");
        xml_.append(" $package ($int i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(18, getNumber(ret_));
    }
    @Test
    public void calculate9Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkg.ExTwo{\n");
        xml_.append(" $intern{getter():getter(Ex)|getter(ExTwo);getter2():getter2(Ex)};\n");
        xml_.append(" $public $int inst=2i;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  $super(20i);\n");
        xml_.append("  inst=i;\n");
        xml_.append("  inst+=getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  ExTwo ex = $new Ex(9);\n");
        xml_.append("  $return ex.getter2();\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter2(){\n");
        xml_.append("  $return inst*2;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $int sec;\n");
        xml_.append(" $package ($int i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter2(){\n");
        xml_.append("  $return sec+2;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(22, getNumber(ret_));
    }
    @Test
    public void calculate10Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkg.ExTwo{\n");
        xml_.append(" $intern{};\n");
        xml_.append(" $public $int inst=2i;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  $super(16i);\n");
        xml_.append("  inst=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  ExTwo ex = $new Ex(9);\n");
        xml_.append("  $return ex.getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $abstract $class pkg.ExTwo {\n");
        xml_.append(" $public $int sec;\n");
        xml_.append(" $package ($int i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $abstract $int getter();\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(9, getNumber(ret_));
    }
    @Test
    public void calculate11Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkg.ExTwo{\n");
        xml_.append(" $public $int inst=2i;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  $super(16i);\n");
        xml_.append("  inst=i;\n");
        xml_.append("  inst+=getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  ExTwo e = $new Ex(9);\n");
        xml_.append("  $return e.getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $int sec;\n");
        xml_.append(" $package ($int i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter()$intern(Ex:getter(ExTwo);ExTwo:getter(ExTwo)){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(16, getNumber(ret_));
    }
    @Test
    public void calculate12Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<V> :pkg.ExTwo<V,V>{\n");
        xml_.append(" $public V inst=(V)2i;\n");
        xml_.append(" $public (V i){\n");
        xml_.append("  $super((V)16i,(V)20i);\n");
        xml_.append("  inst=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  ExTwo<$int,$int> e = $new Ex<$int>(9);\n");
        xml_.append("  $return e.getter($id(ExTwo,T),25);\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal V getter2(V v){\n");
        xml_.append("  $return (V)(($int)inst+($int)v);\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal V getter3(V v){\n");
        xml_.append("  $return (V)(($int)inst*($int)v);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<T,U> {\n");
        xml_.append(" $public T sec;\n");
        xml_.append(" $public U th;\n");
        xml_.append(" $package (T i,U j){\n");
        xml_.append("  sec=i;\n");
        xml_.append("  th=j;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal T getter(T t)$intern(Ex:getter2(Ex,V);ExTwo:getter(ExTwo,T)){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal U getter(U u)$intern(Ex:getter3(Ex,V);ExTwo:getter(ExTwo,U)){\n");
        xml_.append("  $return th;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(34, getNumber(ret_));
    }
    @Test
    public void calculate13Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<V> :pkg.ExTwo<V,V>{\n");
        xml_.append(" $public V inst=(V)2i;\n");
        xml_.append(" $public (V i){\n");
        xml_.append("  $super((V)16i,(V)20i);\n");
        xml_.append("  inst=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  ExTwo<$int,$int> e = $new Ex<$int>(9);\n");
        xml_.append("  $return e.getter($id(ExTwo,U),25);\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal V getter2(V v){\n");
        xml_.append("  $return (V)(($int)inst+($int)v);\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal V getter3(V v){\n");
        xml_.append("  $return (V)(($int)inst*($int)v);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<T,U> {\n");
        xml_.append(" $public T sec;\n");
        xml_.append(" $public U th;\n");
        xml_.append(" $package (T i,U j){\n");
        xml_.append("  sec=i;\n");
        xml_.append("  th=j;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal T getter(T t)$intern(Ex:getter2(Ex,V);ExTwo:getter(ExTwo,T)){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal U getter(U u)$intern(Ex:getter3(Ex,V);ExTwo:getter(ExTwo,U)){\n");
        xml_.append("  $return th;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(225, getNumber(ret_));
    }
    @Test
    public void calculate14Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.Ex :pkg.Int{\n");
        xml_.append(" ONE(4i),\n");
        xml_.append(" TWO;\n");
        xml_.append(" $public $int first;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  first=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public (){\n");
        xml_.append("  first=5i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int doubleValue(){\n");
        xml_.append("  $return first;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExCont {\n");
        xml_.append(" $public Int pre=$static(pkg.Ex).TWO;\n");
        xml_.append(" $public java.lang.String inst=pre.name();\n");
        xml_.append("}\n");
        files_.put("pkg/ExCont", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Int {\n");
        xml_.append(" $public $abstract $int doubleValue();\n");
        xml_.append(" $public $normal java.lang.String name()$intern(Ex:name($en)){$return \"OTHER_\"+$enums.name((Ex)$this);}\n");
        xml_.append(" $public $normal $int ordinal(){$return -1;}\n");
        xml_.append("}\n");
        files_.put("pkg/Int", xml_.toString());
        ContextEl cont_ = contextElEnum(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.ExCont");

        Argument ret_;
        ret_ = instanceNormal("pkg.ExCont", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.ExCont", str_.getClassName(cont_));
        Struct field_;
        field_ = getField(str_, new ClassField("pkg.ExCont", "inst"));
        assertEq(STRING, field_.getClassName(cont_));
        assertEq("TWO",((StringStruct)field_).getInstance());
    }
    @Test
    public void calculate15Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<V> :pkg.ExTwo<V>:pkg.ExThree<V>{\n");
        xml_.append(" $public V inst=(V)2i;\n");
        xml_.append(" $public (V i){\n");
        xml_.append("  $super((V)16i);\n");
        xml_.append("  $interfaces(ExThree)((V)20i);\n");
        xml_.append("  inst=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  ExTwo<$int> e = $new Ex<$int>(9);\n");
        xml_.append("  $return e.getter(25);\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<T> {\n");
        xml_.append(" $public T sec;\n");
        xml_.append(" $package (T i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal T getter(T t)$intern(Ex:getter(ExThree,U)){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("$public $interface pkg.ExThree<U> {\n");
        xml_.append(" $public U sec;\n");
        xml_.append(" $package (U i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal U getter(U t){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(20, getNumber(ret_));
    }
    @Test
    public void calculate16Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.Ex :pkg.Int:pkg.Int2{\n");
        xml_.append(" ONE(4i),\n");
        xml_.append(" TWO;\n");
        xml_.append(" $public $int first;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  first=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public (){\n");
        xml_.append("  first=5i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExCont {\n");
        xml_.append(" $public Int2 pre=$static(pkg.Ex).TWO;\n");
        xml_.append(" $public java.lang.String inst=pre.name();\n");
        xml_.append("}\n");
        files_.put("pkg/ExCont", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Int {\n");
        xml_.append(" $public $abstract $int doubleValue()$intern(Ex:doubleValue(Int2));\n");
        xml_.append(" $public $normal $int ordinal(){$return -1;}\n");
        xml_.append(" $public $normal java.lang.String name(){$return \"OTHER_\"+$enums.name((Ex)$this);}\n");
        xml_.append("}\n");
        xml_.append("$public $interface pkg.Int2 {\n");
        xml_.append(" $public $normal $int doubleValue(){\n");
        xml_.append("  $return ((Ex)$this).first;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal java.lang.String name()$intern(Ex:name(Int)){$return \"OTHER_TWO_\"+$enums.name((Ex)$this);}\n");
        xml_.append("}\n");
        files_.put("pkg/Int", xml_.toString());
        ContextEl cont_ = contextElEnum(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.ExCont");

        Argument ret_;
        ret_ = instanceNormal("pkg.ExCont", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.ExCont", str_.getClassName(cont_));
        Struct field_;
        field_ = getField(str_, new ClassField("pkg.ExCont", "inst"));
        assertEq(STRING, field_.getClassName(cont_));
        assertEq("OTHER_TWO",((StringStruct)field_).getInstance());
    }
    @Test
    public void calculate17Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkg.ExTwo{\n");
        xml_.append(" $intern{getter():[](Ex)|getter(ExTwo);[]:getter(Ex);gett($int):gett2(Ex);gett5():gett5(ExThree)};\n");
        xml_.append(" $public $int inst=2i;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  $super(16i);\n");
        xml_.append("  inst=i;\n");
        xml_.append("  inst+=getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  $return $new Ex(9).inst;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter($int p){\n");
        xml_.append("  $return inst+p;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $int sec;\n");
        xml_.append(" $package ($int i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter():$intern(Ex:getter(Ex,$int);Ex:getter(ExThree);Ex:[]()){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        xml_.append("$public $class pkg.ExThree {\n");
        xml_.append(" $public $normal $int getter()$intern(String:length();Ex:getter()){\n");
        xml_.append("  $return 0;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(18, getNumber(ret_));
    }
    @Test
    public void calculate18Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.Ex :pkg.Int:pkg.Int2{\n");
        xml_.append(" ONE(4i),\n");
        xml_.append(" TWO;\n");
        xml_.append(" $public $int first;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  first=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public (){\n");
        xml_.append("  first=5i;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExCont {\n");
        xml_.append(" $public Int pre=$static(pkg.Ex).TWO;\n");
        xml_.append(" $public $int inst=pre.doubleValue();\n");
        xml_.append("}\n");
        files_.put("pkg/ExCont", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Int {\n");
        xml_.append(" $public $abstract $int doubleValue()$intern(Ex:doubleValue(Int2));\n");
        xml_.append(" $public $normal $int ordinal(){$return -1;}\n");
        xml_.append(" $public $normal java.lang.String name(){$return \"OTHER_\"+$enums.name((Ex)$this);}\n");
        xml_.append("}\n");
        xml_.append("$public $interface pkg.Int2 {\n");
        xml_.append(" $public $normal $int doubleValue(){\n");
        xml_.append("  $return ((Ex)$this).first;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal java.lang.String name()$intern(Ex:name(Int)){$return \"OTHER_TWO_\"+$enums.name((Ex)$this);}\n");
        xml_.append("}\n");
        files_.put("pkg/Int", xml_.toString());
        ContextEl cont_ = contextElEnum(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.ExCont");

        Argument ret_;
        ret_ = instanceNormal("pkg.ExCont", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.ExCont", str_.getClassName(cont_));
        Struct field_;
        field_ = getField(str_, new ClassField("pkg.ExCont", "inst"));
        assertEq(INTEGER, field_.getClassName(cont_));
        assertEq(5,((NumberStruct)field_).intStruct());
    }
    @Test
    public void calculate19Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.Ex :pkg.Int{\n");
        xml_.append(" $intern{doubleValue():doubleValue(Ex)|doubleValue(Int)},\n");
        xml_.append(" ONE(4i),\n");
        xml_.append(" TWO;\n");
        xml_.append(" $public $int first;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  first=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public (){\n");
        xml_.append("  first=5i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $final $int doubleValue(){\n");
        xml_.append("  $return first;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExCont {\n");
        xml_.append(" $public Int pre=$static(pkg.Ex).TWO;\n");
        xml_.append(" $public java.lang.String inst=pre.name();\n");
        xml_.append("}\n");
        files_.put("pkg/ExCont", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Int {\n");
        xml_.append(" $public $abstract $int doubleValue();\n");
        xml_.append(" $public $normal java.lang.String name()$intern(Ex:name($en)){$return \"OTHER_\"+$enums.name((Ex)$this);}\n");
        xml_.append(" $public $normal $int ordinal(){$return -1;}\n");
        xml_.append("}\n");
        files_.put("pkg/Int", xml_.toString());
        ContextEl cont_ = contextElEnum(files_);
        CustList<Argument> args_ = new CustList<Argument>();
        ConstructorId id_ = getConstructorId("pkg.ExCont");

        Argument ret_;
        ret_ = instanceNormal("pkg.ExCont", null, id_, args_, cont_);
        Struct str_ = ret_.getStruct();
        assertEq("pkg.ExCont", str_.getClassName(cont_));
        Struct field_;
        field_ = getField(str_, new ClassField("pkg.ExCont", "inst"));
        assertEq(STRING, field_.getClassName(cont_));
        assertEq("TWO",((StringStruct)field_).getInstance());
    }
    @Test
    public void calculate20Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkg.ExTwo{\n");
        xml_.append(" $intern{getter():getter(Ex)|gette(ExTwo)};\n");
        xml_.append(" $public $int inst=2i;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  $super(16i);\n");
        xml_.append("  inst=i;\n");
        xml_.append("  inst+=getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  $return $new Ex(9).inst;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $int sec;\n");
        xml_.append(" $package ($int i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(18, getNumber(ret_));
    }
    @Test
    public void calculate21Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkg.ExTwo{\n");
        xml_.append(" $public $int inst=2i;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  $super(16i);\n");
        xml_.append("  inst=i;\n");
        xml_.append("  inst+=getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  ExTwo e = $new Ex(9);\n");
        xml_.append("  $return e.getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $int sec;\n");
        xml_.append(" $package ($int i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter()$intern(Ex:getter(ExTwo);ExTwo:gette(ExTwo)){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(16, getNumber(ret_));
    }
    @Test
    public void calculate22Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkg.ExTwo{\n");
        xml_.append(" $public $int inst=2i;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  $super(16i);\n");
        xml_.append("  inst=i;\n");
        xml_.append("  inst+=getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  ExTwo e = $new Ex(9);\n");
        xml_.append("  $return ($int)e.getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $int sec;\n");
        xml_.append(" $package ($int i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal Object getter()$intern(Ex:getter(Ex);ExTwo:getter(ExTwo)){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(18, getNumber(ret_));
    }
    @Test
    public void calculate23Test() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkg.ExTwo{\n");
        xml_.append(" $public $int inst=2i;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  $super(16i);\n");
        xml_.append("  inst=i;\n");
        xml_.append("  inst+=getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  ExTwo e = $new Ex(9);\n");
        xml_.append("  $return e.getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal Object getter2(){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {\n");
        xml_.append(" $public $int sec;\n");
        xml_.append(" $package ($int i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter()$intern(Ex:getter2(Ex);ExTwo:getter(ExTwo)){\n");
        xml_.append("  $return sec;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = ctxOk(files_);
        Argument ret_;
        ret_ = calculateNormal("pkg.Ex",getMethodId("getter"),new CustList<Argument>(),cont_);
        assertEq(18, getNumber(ret_));
    }
    @Test
    public void calculate1FailTest() {
        StringMap<String> files_ = new StringMap<String>();
        
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex :pkg.ExTwo{\n");
        xml_.append(" $intern{getter()};\n");
        xml_.append(" $public $int inst=2i;\n");
        xml_.append(" $public ($int i){\n");
        xml_.append("  $super(16i);\n");
        xml_.append("  inst=i;\n");
        xml_.append("  inst+=getter();\n");
        xml_.append(" }\n");
        xml_.append(" $public $static $int getter(){\n");
        xml_.append("  $return $new Ex(9).inst;\n");
        xml_.append(" }\n");
        xml_.append(" $public $normal $int getter(){\n");
        xml_.append("  $return inst;\n");
        xml_.append(" }\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $abstract $class pkg.ExTwo {\n");
        xml_.append(" $public $int sec;\n");
        xml_.append(" $package ($int i){\n");
        xml_.append("  sec=i;\n");
        xml_.append(" }\n");
        xml_.append(" $public $abstract $int getter();\n");
        xml_.append("}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        assertTrue(hasErr(files_));
    }

}
