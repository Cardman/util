package code.expressionlanguage;

import static code.expressionlanguage.EquallableElUtil.assertEq;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.expressionlanguage.methods.Classes;
import code.util.StringList;
import code.util.StringMap;

@SuppressWarnings("static-method")
public class TemplatesTest {

    private static final String ARR_OBJECT = "[java.lang.Object";
    private static final String ARR_STRING = "[java.lang.String";















    private static final String ARR_VAR_S = "[#S";
    private static final String ARR_VAR_T = "[#T";

    @Test
    public void getAllTypes1Test(){
        assertEq(new StringList("String"), Templates.getAllTypes("String"));
    }
    @Test
    public void getAllTypes2Test(){
        assertEq(new StringList("Map","String","Rate"), Templates.getAllTypes("Map<String,Rate>"));
    }
    @Test
    public void getAllTypes3Test(){
        assertEq(new StringList("Map","String","Map<String,Rate>"), Templates.getAllTypes("Map<String,Map<String,Rate>>"));
    }
    @Test
    public void getAllTypes4Test(){
        assertEq(new StringList("List","Boolean"), Templates.getAllTypes("List<Boolean>"));
    }
    @Test
    public void getAllTypes5Test(){
        assertEq(new StringList("CustList","BooleanList"), Templates.getAllTypes("CustList<BooleanList>"));
    }
    @Test
    public void getAllTypes6Test(){
        assertEq(new StringList("Outer..Map"), Templates.getAllTypes("Outer..Map"));
    }
    @Test
    public void getAllTypes7Test(){
        assertEq(new StringList("..Map"), Templates.getAllTypes("..Map"));
    }
    @Test
    public void getAllTypes8Test(){
        assertEq(new StringList("Map..Inner","String","Rate"), Templates.getAllTypes("Map<String,Rate>..Inner"));
    }
    @Test
    public void getAllTypes9Test(){
        assertEq(new StringList("Map..Inner","String","Rate","Boolean","Number"), Templates.getAllTypes("Map<String,Rate>..Inner<Boolean,Number>"));
    }
    @Test
    public void getAllTypes10Test(){
        assertEq(new StringList("Map..Inner","String","Rate..Denominator","Boolean","Number"), Templates.getAllTypes("Map<String,Rate..Denominator>..Inner<Boolean,Number>"));
    }
    @Test
    public void getAllTypes11Test(){
        assertEq(new StringList("Map..Inner","String..Character","Rate","Boolean","Number"), Templates.getAllTypes("Map<String..Character,Rate>..Inner<Boolean,Number>"));
    }
    @Test
    public void getAllTypes12Test(){
        assertEq(new StringList("Map..Inner","String"), Templates.getAllTypes("Map<String>..Inner"));
    }
    @Test
    public void getAllTypes13Test(){
        assertEq(new StringList("[String"), Templates.getAllTypes("[String"));
    }
    @Test
    public void getAllTypes14Test(){
        assertEq(new StringList("Map","[String","Rate"), Templates.getAllTypes("Map<[String,Rate>"));
    }
    @Test
    public void getAllTypes15Test(){
        assertEq(new StringList("[Map","String","Rate"), Templates.getAllTypes("[Map<String,Rate>"));
    }
    @Test
    public void getAllTypes16Test(){
        assertEq(new StringList("[Map..Inner","String"), Templates.getAllTypes("[Map<String>..Inner"));
    }
    @Test
    public void getAllTypes17Test(){
        assertEq(new StringList("Map..Inner","String","[Rate..Denominator","Boolean","Number"), Templates.getAllTypes("Map<String,[Rate..Denominator>..Inner<Boolean,Number>"));
    }
    @Test
    public void getAllTypes18Test(){
        assertEq(new StringList("Map..Inner","[String..Character","Rate","Boolean","Number"), Templates.getAllTypes("Map<[String..Character,Rate>..Inner<Boolean,Number>"));
    }
    @Test
    public void getAllInnerTypes1Test(){
        assertEq(new StringList("String"), Templates.getAllInnerTypes("String"));
    }
    @Test
    public void getAllInnerTypes2Test(){
        assertEq(new StringList("Map<String,Rate>"), Templates.getAllInnerTypes("Map<String,Rate>"));
    }
    @Test
    public void getAllInnerTypes3Test(){
        assertEq(new StringList("Map<String,Map<String,Rate>>"), Templates.getAllInnerTypes("Map<String,Map<String,Rate>>"));
    }
    @Test
    public void getAllInnerTypes4Test(){
        assertEq(new StringList("List<Boolean>"), Templates.getAllInnerTypes("List<Boolean>"));
    }
    @Test
    public void getAllInnerTypes5Test(){
        assertEq(new StringList("CustList<BooleanList>"), Templates.getAllInnerTypes("CustList<BooleanList>"));
    }
    @Test
    public void getAllInnerTypes6Test(){
        assertEq(new StringList("Outer","Map"), Templates.getAllInnerTypes("Outer..Map"));
    }
    @Test
    public void getAllInnerTypes7Test(){
        assertEq(new StringList("","Map"), Templates.getAllInnerTypes("..Map"));
    }
    @Test
    public void getAllInnerTypes8Test(){
        assertEq(new StringList("Map<String,Rate>","Inner"), Templates.getAllInnerTypes("Map<String,Rate>..Inner"));
    }
    @Test
    public void getAllInnerTypes9Test(){
        assertEq(new StringList("Map<String,Rate>","Inner<Boolean,Number>"), Templates.getAllInnerTypes("Map<String,Rate>..Inner<Boolean,Number>"));
    }
    @Test
    public void getAllInnerTypes10Test(){
        assertEq(new StringList("Map<String,Rate..Denominator>","Inner<Boolean,Number>"), Templates.getAllInnerTypes("Map<String,Rate..Denominator>..Inner<Boolean,Number>"));
    }
    @Test
    public void getAllInnerTypes11Test(){
        assertEq(new StringList("Map<String..Character,Rate>","Inner<Boolean,Number>"), Templates.getAllInnerTypes("Map<String..Character,Rate>..Inner<Boolean,Number>"));
    }
    @Test
    public void getAllInnerTypes12Test(){
        assertEq(new StringList("Map<String>","Inner"), Templates.getAllInnerTypes("Map<String>..Inner"));
    }
    @Test
    public void getAllInnerTypes13Test(){
        assertEq(new StringList("[String"), Templates.getAllInnerTypes("[String"));
    }
    @Test
    public void getAllInnerTypes14Test(){
        assertEq(new StringList("Map<[String,Rate>"), Templates.getAllInnerTypes("Map<[String,Rate>"));
    }
    @Test
    public void getAllInnerTypes15Test(){
        assertEq(new StringList("[Map<String,Rate>"), Templates.getAllInnerTypes("[Map<String,Rate>"));
    }
    @Test
    public void getAllInnerTypes16Test(){
        assertEq(new StringList("[Map<String>","Inner"), Templates.getAllInnerTypes("[Map<String>..Inner"));
    }
    @Test
    public void getAllInnerTypes17Test(){
        assertEq(new StringList("Map<String,[Rate..Denominator>","Inner<Boolean,Number>"), Templates.getAllInnerTypes("Map<String,[Rate..Denominator>..Inner<Boolean,Number>"));
    }
    @Test
    public void getAllInnerTypes18Test(){
        assertEq(new StringList("Map<[String..Character,Rate>","Inner<Boolean,Number>"), Templates.getAllInnerTypes("Map<[String..Character,Rate>..Inner<Boolean,Number>"));
    }
//    @Test
//    public void getAllTypes1Test(){
//        assertEq(new StringList("String"), Templates.getAllTypes("String"));
//    }
//    @Test
//    public void getAllTypes2Test(){
//        assertEq(new StringList("Map","String","Rate"), Templates.getAllTypes("Map<String,Rate>"));
//    }
//    @Test
//    public void getAllTypes3Test(){
//        assertEq(new StringList("Map","String","Map<String,Rate>"), Templates.getAllTypes("Map<String,Map<String,Rate>>"));
//    }
//    @Test
//    public void getAllTypes4Test(){
//        assertEq(new StringList("List","Boolean"), Templates.getAllTypes("List<Boolean>"));
//    }
//    @Test
//    public void getAllTypes5Test(){
//        assertEq(new StringList("CustList","BooleanList"), Templates.getAllTypes("CustList<BooleanList>"));
//    }
//    @Test
//    public void getAllTypes6Test(){
//        assertNull(Templates.getAllTypes("Map<String,Rate>>"));
//    }
//    @Test
//    public void getAllTypes7Test(){
//        assertNull(Templates.getAllTypes("String,Rate"));
//    }
//    @Test
//    public void getAllTypes8Test(){
//        assertNull(Templates.getAllTypes("Map<String,Rate>>,StrMap<String,Rate>>"));
//    }
//    @Test
//    public void getAllTypes9Test(){
//        assertNull(Templates.getAllTypes("Map<String,Rate"));
//    }
//


    @Test
    public void format1Test() {
        ContextEl context_ = simpleContextEl();
        String first_ = context_.getStandards().getAliasString();
        String second_ = context_.getStandards().getAliasInteger();
        assertEq(second_,Templates.format(first_, second_, context_));
    }
































































    @Test
    public void format9Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T> {}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<#E>";
        String second_ = "#T";
        assertEq("#E",Templates.format(first_, second_, cont_));
    }

    @Test
    public void format10Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T> {}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<java.lang.String>";
        String second_ = "#T";
        assertEq("java.lang.String",Templates.format(first_, second_, cont_));
    }

    @Test
    public void format11Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T,#U> {}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<java.lang.String,java.lang.Object>";
        String second_ = "#U";
        assertEq("java.lang.Object",Templates.format(first_, second_, cont_));
    }

    @Test
    public void format12Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T,#U> {}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<java.lang.String,java.lang.Object>";
        String second_ = "#T";
        assertEq("java.lang.String",Templates.format(first_, second_, cont_));
    }

    @Test
    public void format13Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T,#U> {}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<java.lang.String,java.lang.Object>";
        String second_ = "#T";
        assertEq("java.lang.String",Templates.format(first_, second_, cont_));
    }

    @Test
    public void format14Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T,#U> {}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<java.lang.String,java.lang.Object>";
        String second_ = "#V";
        assertEq("#V",Templates.format(first_, second_, cont_));
    }

    @Test
    public void format15Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T,#U> {}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<java.lang.String,java.lang.Object>";
        String second_ = "code.util.CustList<#V>";
        assertEq("code.util.CustList<#V>",Templates.format(first_, second_, cont_));
    }

    @Test
    public void generalFormat1Test() {
        ContextEl context_ = simpleContextEl();
        String first_ = context_.getStandards().getAliasString();
        String second_ = context_.getStandards().getAliasInteger();
        assertEq(second_,Templates.wildCardFormat(first_, second_, context_,true));
    }
































































    @Test
    public void generalFormat9Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T> {}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<#E>";
        String second_ = "#T";
        assertEq("#E",Templates.wildCardFormat(first_, second_, cont_, true));
    }

    @Test
    public void generalFormat10Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T> {}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<java.lang.String>";
        String second_ = "#T";
        assertEq("java.lang.String",Templates.wildCardFormat(first_, second_, cont_,true));
    }

    @Test
    public void generalFormat11Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T,#U> {}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<java.lang.String,java.lang.Object>";
        String second_ = "#U";
        assertEq("java.lang.Object",Templates.wildCardFormat(first_, second_, cont_,true));
    }

    @Test
    public void generalFormat12Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T,#U> {}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<java.lang.String,java.lang.Object>";
        String second_ = "#T";
        assertEq("java.lang.String",Templates.wildCardFormat(first_, second_, cont_,true));
    }

    @Test
    public void generalFormat13Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T,#U> {}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<java.lang.String,java.lang.Object>";
        String second_ = "#T";
        assertEq("java.lang.String",Templates.wildCardFormat(first_, second_, cont_,true));
    }

    @Test
    public void generalFormat14Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T,#U> {}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<java.lang.String,java.lang.Object>";
        String second_ = "#V";
        assertEq("#V",Templates.wildCardFormat(first_, second_, cont_,true));
    }

    @Test
    public void generalFormat15Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T,#U> {}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<java.lang.String,java.lang.Object>";
        String second_ = "code.util.CustList<#V>";
        assertEq("code.util.CustList<#V>",Templates.wildCardFormat(first_, second_, cont_,true));
    }

    @Test
    public void generalFormat16Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T:java.lang.Number,#U> {}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<java.lang.Number,java.lang.Number>";
        String second_ = "code.util.CustList<#T>";
        assertEq("code.util.CustList<java.lang.Number>",Templates.wildCardFormat(first_, second_, cont_,true));
    }
    @Test
    public void generalFormat17Test() {
        StringBuilder xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E:java.lang.Number,#F> {}\n");
        StringMap<String> files_ = new StringMap<String>();
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T:java.lang.Number,#U> {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.ExTwo<java.lang.Number,java.lang.Number>";
        String second_ = "pkg.Ex<#T,#T>";
        assertEq("pkg.Ex<java.lang.Number,java.lang.Number>",Templates.wildCardFormat(first_, second_, cont_,true));
    }

    @Test
    public void getGenericTypeByBases1Test() {
        ContextEl context_ = simpleContextEl();
        String t_ = Templates.getFullTypeByBases("java.lang.String", "java.lang.Object", context_);
        assertEq("java.lang.Object", t_);
    }

    @Test
    public void getGenericTypeByBases2Test() {
        ContextEl context_ = simpleContextEl();
        String t_ = Templates.getFullTypeByBases("java.lang.Object", "java.lang.String", context_);
        assertNull(t_);
    }

























    @Test
    public void getGenericTypeByBases6Test() {
        ContextEl context_ = simpleContextEl();
        String t_ = Templates.getFullTypeByBases("java.lang.String", "java.lang.String", context_);
        assertEq("java.lang.String", t_);
    }









    @Test
    public void getGenericTypeByBases8Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String t_ = Templates.getFullTypeByBases("pkg.Ex", "pkg.Ex", cont_);
        assertEq("pkg.Ex", t_);
    }

    @Test
    public void getGenericTypeByBases9Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String t_ = Templates.getFullTypeByBases("pkg.ExTwo", "pkg.Ex", cont_);
        assertEq("pkg.Ex", t_);
    }

    @Test
    public void getGenericTypeByBases10Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String t_ = Templates.getFullTypeByBases("pkg.Ex", "pkg.ExTwo", cont_);
        assertNull(t_);
    }









    @Test
    public void getGenericTypeByBases12Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#U> :pkg.Ex<#U>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String t_ = Templates.getFullTypeByBases("pkg.ExTwo<#V>", "pkg.Ex", cont_);
        assertEq("pkg.Ex<#V>", t_);
    }

    @Test
    public void getGenericTypeByBases13Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex<java.lang.Number>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String t_ = Templates.getFullTypeByBases("pkg.ExTwo", "pkg.Ex", cont_);
        assertEq("pkg.Ex<java.lang.Number>", t_);
    }
    @Test
    public void getGenericTypeByBases14Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#U> :pkg.Ex<#U>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String t_ = Templates.getFullTypeByBases("pkg.Ex<#V>", "pkg.Ex", cont_);
        assertEq("pkg.Ex<#V>", t_);
    }
    @Test
    public void getGenericTypeByBases15Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExThree<#X> {}\n");
        files_.put("pkg/ExThree", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T>:pkg.ExThree<#T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#U> :pkg.Ex<#U>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String t_ = Templates.getFullTypeByBases("pkg.Ex<#V>", "pkg.ExThree", cont_);
        assertEq("pkg.ExThree<#V>", t_);
    }
    @Test
    public void isCorrect1Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg("java.lang.Object");
        m_.setParam("java.lang.Object");
        assertTrue(Templates.isCorrect(m_, context_));
    }

    @Test
    public void isCorrect2Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg("java.lang.String");
        m_.setParam("java.lang.Object");
        assertTrue(Templates.isCorrect(m_, context_));
    }

    @Test
    public void isCorrect3Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg("java.lang.Object");
        m_.setParam("java.lang.String");
        assertTrue(!Templates.isCorrect(m_, context_));
    }

    @Test
    public void isCorrect4Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg("java.util.List<java.lang.Object>");
        m_.setParam("java.util.List<java.lang.Object>");
        assertTrue(Templates.isCorrect(m_, context_));
    }

    @Test
    public void isCorrect5Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg("java.util.List<java.lang.String>");
        m_.setParam("java.util.List<java.lang.Object>");
        assertTrue(!Templates.isCorrect(m_, context_));
    }

    @Test
    public void isCorrect6Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg("java.util.List<java.lang.Object>");
        m_.setParam("java.util.List<java.lang.String>");
        assertTrue(!Templates.isCorrect(m_, context_));
    }

    @Test
    public void isCorrect7Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg("#E");
        m_.setParam("java.lang.Object");
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("E", new StringList("java.lang.Enum<#E>"));
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect8Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg("#E");
        m_.setParam("java.lang.Number");
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("E", new StringList("java.lang.Integer"));
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect9Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg("#E");
        m_.setParam("[java.lang.Number");
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("E", new StringList("[java.lang.Integer"));
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect10Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg("#E");
        m_.setParam("[java.lang.Number");
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("E", new StringList("java.lang.Integer"));
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }











































































    @Test
    public void isCorrect11Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T>:ExTwo<#T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#S> {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("$Fct<pkg.Ex<java.lang.Number>>");
        m_.setParam("$Fct<pkg.ExTwo<java.lang.Number>>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect12Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T>:ExTwo<#T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#S> {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("$Fct<pkg.ExTwo<java.lang.Number>,$int>");
        m_.setParam("$Fct<pkg.Ex<java.lang.Number>,$int>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect13Test() {
        StringMap<String> files_ = new StringMap<String>();
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("$Fct<$void>");
        m_.setParam("$Fct<java.lang.Object>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect14Test() {
        StringMap<String> files_ = new StringMap<String>();
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("$Fct<java.lang.Object>");
        m_.setParam("$Fct<$void>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect15Test() {
        StringMap<String> files_ = new StringMap<String>();
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("$Fct<$void>");
        m_.setParam("$Fct<$void>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect16Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T>:ExTwo<#T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#S> {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("$Fct<pkg.Ex<java.lang.Number>,pkg.Ex<java.lang.Number>>");
        m_.setParam("$Fct<pkg.ExTwo<java.lang.Number>>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }
    @Test
    public void isCorrect17Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg("#E");
        m_.setParam("java.lang.Number");
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("E", new StringList("[java.lang.Integer"));
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect18Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T>:ExTwo<#T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#S> {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("$Fct<pkg.Ex<java.lang.Number>,pkg.Ex<java.lang.Number>>");
        m_.setParam("pkg.ExTwo<java.lang.Object>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect19Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T>:ExTwo<#T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#S> {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("$Fct<pkg.Ex<java.lang.Number>,pkg.Ex<java.lang.Number>>");
        m_.setParam("java.lang.Object");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }
    @Test
    public void isCorrect20Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg("#T");
        m_.setParam("#S");
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("T", new StringList("#S"));
        t_.put("S", new StringList("java.lang.Object"));
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }
    @Test
    public void isCorrect21Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg("#S");
        m_.setParam("#T");
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("T", new StringList("#S"));
        t_.put("S", new StringList("java.lang.Object"));
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect22Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T>:ExTwo<#T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#S> {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("java.lang.Object");
        m_.setParam("$Fct<pkg.Ex<java.lang.Number>,pkg.Ex<java.lang.Number>>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect23Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg(ARR_STRING);
        m_.setParam(ARR_OBJECT);
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect24Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Param<?pkg.ExTwo>");
        m_.setParam("pkg.Param<?pkg.Ex>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect25Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Param<!pkg.Ex>");
        m_.setParam("pkg.Param<!pkg.ExTwo>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }


    @Test
    public void isCorrect26Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Param<pkg.ExTwo>");
        m_.setParam("pkg.Param<?pkg.Ex>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect27Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Param<pkg.Ex>");
        m_.setParam("pkg.Param<!pkg.ExTwo>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect28Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Param<pkg.Ex>");
        m_.setParam("pkg.Param<?>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect29Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Param<!pkg.Ex>");
        m_.setParam("pkg.Param<?>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect30Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Param<?pkg.Ex>");
        m_.setParam("pkg.Param<?>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect31Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Param<?>");
        m_.setParam("pkg.Param<?>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect32Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Param<?>");
        m_.setParam("pkg.Param<?pkg.Ex>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect33Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Param<!pkg.Ex>");
        m_.setParam("pkg.Param<?pkg.Ex>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect34Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Param<?pkg.Ex>");
        m_.setParam("pkg.Param<!pkg.Ex>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect35Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Param<?pkg.Ex>");
        m_.setParam("pkg.Param<pkg.Ex>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect36Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg(ARR_VAR_T);
        m_.setParam(ARR_VAR_S);
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("T", new StringList("#S"));
        t_.put("S", new StringList("java.lang.Object"));
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect37Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg(ARR_VAR_S);
        m_.setParam(ARR_VAR_T);
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("T", new StringList("#S"));
        t_.put("S", new StringList("java.lang.Object"));
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect38Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg(ARR_VAR_S);
        m_.setParam(ARR_VAR_T);
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("T", new StringList("#S"));
        t_.put("S", new StringList("java.lang.Object"));
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect39Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg(ARR_VAR_T);
        m_.setParam(ARR_VAR_S);
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("T", new StringList("#S"));
        t_.put("S", new StringList("java.lang.Object"));
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }
    

    @Test
    public void isCorrect40Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Param<!pkg.Ex>");
        m_.setParam("pkg.Param<pkg.Ex>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }


    @Test
    public void isCorrect41Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        xml_.append("$public $class pkg.ParamTwo<#S>:Param<#S>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ParamTwo<?pkg.ExTwo>");
        m_.setParam("pkg.Param<?pkg.Ex>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect42Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        xml_.append("$public $class pkg.ParamTwo<#S>:Param<#S>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ParamTwo<!pkg.Ex>");
        m_.setParam("pkg.Param<!pkg.ExTwo>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect43Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        xml_.append("$public $class pkg.ParamTwo<#S>:Param<#S>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ParamTwo<pkg.ExTwo>");
        m_.setParam("pkg.Param<?pkg.Ex>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect44Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        xml_.append("$public $class pkg.ParamTwo<#S>:Param<#S>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ParamTwo<pkg.Ex>");
        m_.setParam("pkg.Param<!pkg.ExTwo>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect45Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        xml_.append("$public $class pkg.ParamTwo<#S>:Param<#S>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ParamTwo<pkg.Ex>");
        m_.setParam("pkg.Param<?>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect46Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        xml_.append("$public $class pkg.ParamTwo<#S>:Param<#S>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ParamTwo<!pkg.Ex>");
        m_.setParam("pkg.Param<?>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect47Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        xml_.append("$public $class pkg.ParamTwo<#S>:Param<#S>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ParamTwo<?pkg.Ex>");
        m_.setParam("pkg.Param<?>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect48Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        xml_.append("$public $class pkg.ParamTwo<#S>:Param<#S>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ParamTwo<?>");
        m_.setParam("pkg.Param<?>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect49Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        xml_.append("$public $class pkg.ParamTwo<#S>:Param<#S>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ParamTwo<?>");
        m_.setParam("pkg.Param<?pkg.Ex>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }



















































    @Test
    public void isCorrect50Test() {
        ContextEl context_ = simpleContextEl();
        Mapping m_ = new Mapping();
        m_.setArg("#H");
        m_.setParam(context_.getStandards().getAliasObject());
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("H", new StringList());
        m_.setMapping(t_);
        assertTrue(Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect51Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        xml_.append("$public $class pkg.ParamTwo<#S>:Param<#S>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ParamTwo<!pkg.Ex>");
        m_.setParam("pkg.Param<?pkg.Ex>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }
    @Test
    public void isCorrect52Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Ex");
        m_.setParam("pkg.Ex");
        assertTrue(Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect53Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        xml_.append("$public $class pkg.ParamTwo<#S>:Param<#S>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ParamTwo<?pkg.Ex>");
        m_.setParam("pkg.Param<!pkg.Ex>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }
    @Test
    public void isCorrect54Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("java.lang.Number");
        m_.setParam("pkg.Ex");
        assertTrue(!Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect55Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ExTwo");
        m_.setParam("pkg.Ex");
        assertTrue(Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect56Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Ex");
        m_.setParam("pkg.ExTwo");
        assertTrue(!Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect57Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ExTwo");
        m_.setParam("pkg.Ex");
        assertTrue(Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect58Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Ex");
        m_.setParam("pkg.ExTwo");
        assertTrue(!Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect59Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ExTwo");
        m_.setParam("pkg.Ex");
        assertTrue(Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect60Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Ex");
        m_.setParam("pkg.ExTwo");
        assertTrue(!Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect61Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ExTwo");
        m_.setParam("pkg.Ex");
        assertTrue(Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect62Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.Ex");
        m_.setParam("pkg.ExTwo");
        assertTrue(!Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect63Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#F> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#E> :pkg.Ex<#E>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.getMapping().put("T", new StringList("java.lang.Object"));
        m_.setArg("pkg.ExTwo<#T>");
        m_.setParam("pkg.Ex<#T>");
        assertTrue(Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect64Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#F> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#E> :pkg.Ex<#E>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.getMapping().put("T", new StringList("java.lang.Object"));
        m_.setArg("pkg.Ex<#T>");
        m_.setParam("pkg.ExTwo<#T>");
        assertTrue(!Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect65Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#F> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExTwo<#E> :pkg.Ex<#E>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.getMapping().put("T", new StringList("java.lang.Object"));
        m_.setArg("pkg.ExTwo<#T>");
        m_.setParam("pkg.Ex<#T>");
        assertTrue(Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect66Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#F> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExTwo<#E> :pkg.Ex<#E>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.getMapping().put("T", new StringList("java.lang.Object"));
        m_.setArg("pkg.Ex<#T>");
        m_.setParam("pkg.ExTwo<#T>");
        assertTrue(!Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect67Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#F> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExTwo<#E> :pkg.Ex<#E>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.getMapping().put("S", new StringList("java.lang.Object"));
        m_.setArg("pkg.ExTwo<#S>");
        m_.setParam("pkg.Ex<#S>");
        assertTrue(Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect68Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        xml_.append("$public $class pkg.ParamTwo<#S>:Param<#S>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ParamTwo<?pkg.Ex>");
        m_.setParam("pkg.Param<pkg.Ex>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }

    @Test
    public void isCorrect71Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Param<#T>{}\n");
        xml_.append("$public $class pkg.ParamTwo<#S>:Param<#S>{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ParamTwo<!pkg.Ex>");
        m_.setParam("pkg.Param<pkg.Ex>");
        StringMap<StringList> t_ = new StringMap<StringList>();
        m_.setMapping(t_);
        assertTrue(!Templates.isCorrect(m_,context_));
    }
    @Test
    public void isCorrect73Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#F> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExTwo<#E> :pkg.Ex<#E>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.getMapping().put("T", new StringList("[java.lang.Object"));
        m_.setArg("[[pkg.ExTwo<#T>");
        m_.setParam("[pkg.Ex<#T>");
        assertTrue(!Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect74Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#F> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExTwo<#E> :pkg.Ex<#E>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.getMapping().put("T", new StringList("[java.lang.Object"));
        m_.setArg("[pkg.Ex<#T>");
        m_.setParam("[[pkg.ExTwo<#T>");
        assertTrue(!Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect75Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#F> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex<java.lang.Object>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.setArg("pkg.ExTwo");
        m_.setParam("pkg.Ex<java.lang.Object>");
        assertTrue(Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect76Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#F> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.Ex<java.lang.Object>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        Mapping m_ = new Mapping();
        m_.getMapping().put("T", new StringList("java.lang.Object"));
        m_.setArg("pkg.Ex<java.lang.Object>");
        m_.setParam("pkg.ExTwo");
        assertTrue(!Templates.isCorrect(m_, cont_));
    }

    @Test
    public void isCorrect77Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#E> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T> :pkg.Ex<#T>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        cont_.getClasses();
    }

    @Test
    public void isCorrectTemplate51Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(Templates.isCorrectTemplate("pkg.Ex", t_,cont_));
    }

    @Test
    public void isCorrectTemplate52Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(Templates.isCorrectTemplate("pkg.Ex", t_,cont_));
    }

    @Test
    public void isCorrectTemplate53Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(Templates.isCorrectTemplate("pkg.Ex", t_,cont_));
    }

    @Test
    public void isCorrectTemplate54Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(Templates.isCorrectTemplate("[pkg.Ex", t_,cont_));
    }

    @Test
    public void isCorrectTemplate55Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $enum pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(Templates.isCorrectTemplate("[pkg.Ex", t_,cont_));
    }

    @Test
    public void isCorrectTemplate56Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(Templates.isCorrectTemplate("[pkg.Ex", t_,cont_));
    }

    @Test
    public void isCorrectTemplate57Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(Templates.isCorrectTemplate("pkg.Ex<java.lang.Object>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate58Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(Templates.isCorrectTemplate("pkg.Ex<java.lang.Object>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate59Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(Templates.isCorrectTemplate("[pkg.Ex<java.lang.Object>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate60Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(Templates.isCorrectTemplate("[pkg.Ex<java.lang.Object>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate61Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("E", new StringList("java.lang.Object"));
        assertTrue(Templates.isCorrectTemplate("[pkg.Ex<#E>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate62Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("E", new StringList("java.lang.Object"));
        assertTrue(Templates.isCorrectTemplate("[pkg.Ex<#E>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate63Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T:java.lang.Number> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("E", new StringList("java.lang.Integer"));
        assertTrue(Templates.isCorrectTemplate("[pkg.Ex<#E>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate64Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T:java.lang.Number> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("E", new StringList("java.lang.Integer"));
        assertTrue(Templates.isCorrectTemplate("[pkg.Ex<#E>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate65Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T:java.lang.Number> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("E", new StringList("java.lang.Object"));
        assertTrue(!Templates.isCorrectTemplate("[pkg.Ex<#E>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate66Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T:java.lang.Number> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("E", new StringList("java.lang.Object"));
        assertTrue(!Templates.isCorrectTemplate("[pkg.Ex<#E>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate67Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T:java.lang.Number> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(!Templates.isCorrectTemplate("[pkg.Ex<java.lang.Object>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate68Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T:java.lang.Number> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(!Templates.isCorrectTemplate("[pkg.Ex<java.lang.Object>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate69Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T>:pkg.ExTwo<#T[]> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExTwo<#S>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<?java.lang.Number>";
        String second_ = "pkg.ExTwo<?[#T>";
        String res_ = Templates.getFullTypeByBases(first_, second_, cont_);
        assertEq("pkg.ExTwo<?[java.lang.Number>", res_);
    }

    @Test
    public void isCorrectTemplate70Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T:java.lang.Number> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(Templates.isCorrectTemplate("[pkg.Ex<java.lang.Integer>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate71Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T:java.lang.Number> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(!Templates.isCorrectTemplate("[pkg.Ex<java.lang.Object>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate72Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T:java.lang.Number> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(!Templates.isCorrectTemplate("[pkg.Ex<java.lang.Object>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate73Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T:pkg.ExThree> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.ExThree{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree {}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("E", new StringList("pkg.ExTwo"));
        assertTrue(Templates.isCorrectTemplate("[pkg.Ex<#E>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate74Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T:pkg.ExThree> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.ExThree{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree {}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("E", new StringList("pkg.ExTwo"));
        assertTrue(Templates.isCorrectTemplate("[pkg.Ex<#E>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate75Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T:pkg.ExThree> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree :pkg.ExTwo{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("E", new StringList("pkg.ExTwo"));
        assertTrue(!Templates.isCorrectTemplate("[pkg.Ex<#E>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate76Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T:pkg.ExThree> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree :pkg.ExTwo{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("E", new StringList("pkg.ExTwo"));
        assertTrue(!Templates.isCorrectTemplate("[pkg.Ex<#E>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate77Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T:pkg.ExThree> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree :pkg.ExTwo{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(!Templates.isCorrectTemplate("[pkg.Ex<pkg.ExTwo>", t_,cont_));
    }

    @Test
    public void isCorrectTemplate78Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T:pkg.ExThree> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree :pkg.ExTwo{}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(!Templates.isCorrectTemplate("[pkg.Ex<pkg.ExTwo>", t_,cont_));
    }

    @Test
    public void isCorrectTemplateAll1Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T:pkg.ExTwo> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.ExThree{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree {}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(Templates.isCorrectTemplateAll("java.lang.Object", t_,cont_));
    }

    @Test
    public void isCorrectTemplateAll2Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T:pkg.ExTwo> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.ExThree{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree {}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(Templates.isCorrectTemplateAll(cont_.getStandards().getAliasPrimInteger(), t_,cont_));
    }

    @Test
    public void isCorrectTemplateAll3Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T:pkg.ExTwo> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.ExThree{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree {}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(Templates.isCorrectTemplateAll("[java.lang.Object", t_,cont_));
    }

    @Test
    public void isCorrectTemplateAll4Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T:pkg.ExTwo> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.ExThree{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree {}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(Templates.isCorrectTemplateAll("[$int", t_,cont_));
    }

    @Test
    public void getFullTypeByBases7Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T>:pkg.ExTwo<#T[]> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExTwo<#S>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<?java.lang.Number>";
        String second_ = "pkg.ExTwo<[#T>";
        String res_ = Templates.getFullTypeByBases(first_, second_, cont_);
        assertEq("pkg.ExTwo<?[java.lang.Number>", res_);
    }


    @Test
    public void getFullTypeByBases8Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T>:pkg.ExTwo<#T[]> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.ExTwo<#S>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        String first_ = "pkg.Ex<?java.lang.Number>";
        String second_ = "pkg.ExTwo<?[#T>";
        String res_ = Templates.format(first_, second_, cont_);
        assertNull(res_);
    }















































    @Test
    public void isCorrectTemplateAll11Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T:pkg.ExTwo> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.ExThree{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree {}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("E", new StringList("pkg.ExTwo"));
        assertTrue(Templates.isCorrectTemplateAll("pkg.Ex<#E>", t_,cont_));
    }

















    @Test
    public void isCorrectTemplateAll13Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T:pkg.ExThree> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo :pkg.ExThree{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExThree {}\n");
        files_.put("pkg/ExThree", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        t_.put("E", new StringList("pkg.ExTwo"));
        assertTrue(Templates.isCorrectTemplateAll("pkg.Ex<#E>", t_,cont_));
    }


    @Test
    public void isCorrectTemplateAll14Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $interface pkg.Ex<#T:pkg.ExTwo<?#T>> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#U>{}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringMap<StringList> t_ = new StringMap<StringList>();
        assertTrue(!Templates.isCorrectTemplateAll("pkg.Ex<pkg.ExTwo<?pkg.ExTwo<?java.lang.Number>>>", t_,cont_));
    }
















    @Test
    public void getAllGenericSuperTypes6Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.Ex<#T:java.lang.Number> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringList superTypes_ = Templates.getAllGenericSuperTypes("pkg.Ex<#E>", cont_);
        assertEq(0, superTypes_.size());
    }

    @Test
    public void getAllGenericSuperTypes7Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("$public $class pkg.ExTwo<#T:java.lang.Number> {}\n");
        xml_.append("$public $class pkg.Ex<#E:java.lang.Number>:pkg.ExTwo<#E> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateOverridingMethods(files_);
        StringList superTypes_ = Templates.getAllGenericSuperTypes("pkg.Ex", cont_);
        assertEq(1, superTypes_.size());
        assertEq("pkg.ExTwo<#E>", superTypes_.get(0));
    }
    private ContextEl unfullValidateOverridingMethods(StringMap<String> _files) {
        ContextEl cont_ = new ContextEl();
        cont_.getOptions().setSuffixVar(VariableSuffix.DISTINCT);
        cont_.getOptions().setMultipleAffectations(false);
        Classes classes_ = cont_.getClasses();
        InitializationLgNames.initAdvStandards(cont_);
        cont_.initError();
        Classes.buildPredefinedBracesBodies(cont_);
        Classes.tryBuildBracedClassesBodies(_files, cont_);
        assertTrue(classes_.displayErrors(), classes_.isEmptyErrors());
        assertTrue(classes_.displayErrors(), classes_.isEmptyErrors());
        classes_.validateInheritingClasses(cont_, false);
        assertTrue(classes_.displayErrors(), classes_.isEmptyErrors());
        return cont_;
    }
    private ContextEl simpleContextEl() {
        ContextEl cont_ = new ContextEl();
        cont_.getOptions().setSuffixVar(VariableSuffix.DISTINCT);
        cont_.getOptions().setMultipleAffectations(false);
        InitializationLgNames.initAdvStandards(cont_);
        cont_.initError();
        return cont_;
    }
}
