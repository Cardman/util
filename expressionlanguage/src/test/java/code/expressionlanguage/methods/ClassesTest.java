package code.expressionlanguage.methods;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static code.util.opers.EquallableUtil.assertEq;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.PrimitiveTypeUtil;
import code.expressionlanguage.opers.util.FctConstraints;
import code.util.EqList;
import code.util.ObjectMap;
import code.util.StringList;
import code.util.StringMap;

@SuppressWarnings("static-method")
public class ClassesTest {
    private static final String PUBLIC_ACCESS = "PUBLIC";

    @Ignore
    @Test
    public void initTest() {
        String xml_ = "<class access='"+PUBLIC_ACCESS+"' name='Ex' package='pkg'>\n";
        xml_ += "<method access='"+PUBLIC_ACCESS+"' name='exmeth' class='"+PrimitiveTypeUtil.PRIM_INT+"'>\n";
        xml_ += "<return expression='1i+1i'/>\n";
        xml_ += "</method>\n";
        xml_ += "</class>";
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = new ContextEl();
        files_.put("pkg/Ex."+Classes.EXT, xml_);
        Classes classes_ = new Classes(files_, cont_);
        cont_.setClasses(classes_);
        classes_.validateClassBodies(cont_);
        classes_.validateClassNames(cont_);
        classes_.validateMethodNames(cont_);
        classes_.validateMethodsId(cont_);
        classes_.validateLocalVariableNamesId(cont_);
        classes_.validateEl(cont_);
    }

    @Ignore
    @Test
    public void init2Test() {
        String xml_ = "<class access='"+PUBLIC_ACCESS+"' name='Ex' package='pkg'>\n";
        xml_ += "<method access='"+PUBLIC_ACCESS+"' name='exmeth' class='"+PrimitiveTypeUtil.PRIM_INT+"'>\n";
        xml_ += "<declare var='t' class='"+PrimitiveTypeUtil.PRIM_LONG+"'/>\n";
        xml_ += "<affect left='t;.' oper='=' right='8'/>\n";
//        xml_ += "<return expression='1i+class(&quot;"+PrimitiveTypeUtil.PRIM_INT+"&quot;,t;.)'/>\n";
        xml_ += "<return expression='1i+^class(&quot;"+PrimitiveTypeUtil.PRIM_INT+"&quot;,t;.)'/>\n";
        xml_ += "</method>\n";
        xml_ += "</class>";
        StringMap<String> files_ = new StringMap<String>();
        ContextEl cont_ = new ContextEl();
        files_.put("pkg/Ex."+Classes.EXT, xml_);
        Classes classes_ = new Classes(files_, cont_);
        cont_.setClasses(classes_);
        classes_.validateClassBodies(cont_);
        classes_.validateClassNames(cont_);
        classes_.validateMethodNames(cont_);
        classes_.validateMethodsId(cont_);
        classes_.validateLocalVariableNamesId(cont_);
        classes_.validateEl(cont_);
    }

    @Test
    public void getSortedSuperInterfaces1Test() {
        StringMap<String> files_ = new StringMap<String>();
        String xml_;
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='Ex' package='pkg'/>\n";
        files_.put("pkg/Ex."+Classes.EXT, xml_);
        ContextEl cont_ = new ContextEl();
        Classes classes_ = new Classes(files_, cont_);
        cont_.setClasses(classes_);
        StringList s_ = classes_.getSortedSuperInterfaces(new StringList("pkg.Ex"));
        assertEq(1, s_.size());
        assertEq("pkg.Ex", s_.first());
    }

    @Test
    public void getSortedSuperInterfaces2Test() {
        StringMap<String> files_ = new StringMap<String>();
        String xml_;
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='Ex' package='pkg' class0='pkg.ExTwo'/>\n";
        files_.put("pkg/Ex."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExTwo' package='pkg'/>\n";
        files_.put("pkg/ExTwo."+Classes.EXT, xml_);
        ContextEl cont_ = new ContextEl();
        Classes classes_ = new Classes(files_, cont_);
        cont_.setClasses(classes_);
        StringList s_ = classes_.getSortedSuperInterfaces(new StringList("pkg.Ex"));
        assertEq(2, s_.size());
        assertEq("pkg.ExTwo", s_.first());
        assertEq("pkg.Ex", s_.last());
    }

    @Test
    public void getSortedSuperInterfaces3Test() {
        StringMap<String> files_ = new StringMap<String>();
        String xml_;
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='Ex' package='pkg' class0='pkg.ExTwo' class1='pkg.ExThree'/>\n";
        files_.put("pkg/Ex."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExTwo' package='pkg'/>\n";
        files_.put("pkg/ExTwo."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExThree' package='pkg'/>\n";
        files_.put("pkg/ExThree."+Classes.EXT, xml_);
        ContextEl cont_ = new ContextEl();
        Classes classes_ = new Classes(files_, cont_);
        cont_.setClasses(classes_);
        StringList s_ = classes_.getSortedSuperInterfaces(new StringList("pkg.Ex"));
        assertEq(3, s_.size());
        assertEq("pkg.ExTwo", s_.first());
        assertEq("pkg.ExThree", s_.get(1));
        assertEq("pkg.Ex", s_.last());
    }

    @Test
    public void getSortedSuperInterfaces4Test() {
        StringMap<String> files_ = new StringMap<String>();
        String xml_;
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='Ex' package='pkg' class0='pkg.ExTwo' class1='pkg.ExThree' class2='pkg.ExFour'/>\n";
        files_.put("pkg/Ex."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExTwo' package='pkg'/>\n";
        files_.put("pkg/ExTwo."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExThree' package='pkg'/>\n";
        files_.put("pkg/ExThree."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExFour' package='pkg'/>\n";
        files_.put("pkg/ExFour."+Classes.EXT, xml_);
        ContextEl cont_ = new ContextEl();
        Classes classes_ = new Classes(files_, cont_);
        cont_.setClasses(classes_);
        StringList s_ = classes_.getSortedSuperInterfaces(new StringList("pkg.Ex"));
        assertEq(4, s_.size());
        assertEq("pkg.ExTwo", s_.first());
        assertEq("pkg.ExThree", s_.get(1));
        assertEq("pkg.ExFour", s_.get(2));
        assertEq("pkg.Ex", s_.last());
    }

    @Test
    public void getSortedSuperInterfaces5Test() {
        StringMap<String> files_ = new StringMap<String>();
        String xml_;
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='Ex' package='pkg' class0='pkg.ExTwo' class1='pkg.ExThree'/>\n";
        files_.put("pkg/Ex."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExTwo' package='pkg' class0='pkg.ExFour'/>\n";
        files_.put("pkg/ExTwo."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExThree' package='pkg' class0='pkg.ExFour'/>\n";
        files_.put("pkg/ExThree."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExFour' package='pkg'/>\n";
        files_.put("pkg/ExFour."+Classes.EXT, xml_);
        ContextEl cont_ = new ContextEl();
        Classes classes_ = new Classes(files_, cont_);
        cont_.setClasses(classes_);
        StringList s_ = classes_.getSortedSuperInterfaces(new StringList("pkg.Ex"));
        assertEq(4, s_.size());
        assertEq("pkg.ExFour", s_.first());
        assertEq("pkg.ExTwo", s_.get(1));
        assertEq("pkg.ExThree", s_.get(2));
        assertEq("pkg.Ex", s_.last());
    }

    @Test
    public void getSortedSuperInterfaces6Test() {
        StringMap<String> files_ = new StringMap<String>();
        String xml_;
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='Ex' package='pkg' class0='pkg.ExTwo' class1='pkg.ExThree'/>\n";
        files_.put("pkg/Ex."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExTwo' package='pkg' class0='pkg.ExFour'/>\n";
        files_.put("pkg/ExTwo."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExThree' package='pkg' class0='pkg.ExFour'/>\n";
        files_.put("pkg/ExThree."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExFour' package='pkg'/>\n";
        files_.put("pkg/ExFour."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExFive' package='pkg' class0='pkg.ExTwo' class1='pkg.ExThree'/>\n";
        files_.put("pkg/ExFive."+Classes.EXT, xml_);
        ContextEl cont_ = new ContextEl();
        Classes classes_ = new Classes(files_, cont_);
        cont_.setClasses(classes_);
        StringList s_ = classes_.getSortedSuperInterfaces(new StringList("pkg.Ex","pkg.ExFive"));
        assertEq(5, s_.size());
        assertEq("pkg.ExFour", s_.first());
        assertEq("pkg.ExTwo", s_.get(1));
        assertEq("pkg.ExThree", s_.get(2));
        assertEq("pkg.Ex", s_.get(3));
        assertEq("pkg.ExFive", s_.last());
    }

    @Test
    public void getAllOverridingMethods1Test() {
        StringMap<String> files_ = new StringMap<String>();
        String xml_;
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='Ex' package='pkg' class0='pkg.ExTwo' class1='pkg.ExThree'>\n";
        xml_ += "</interface>\n";
        files_.put("pkg/Ex."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExTwo' package='pkg' class0='pkg.ExFour'/>\n";
        files_.put("pkg/ExTwo."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExThree' package='pkg' class0='pkg.ExFour'/>\n";
        files_.put("pkg/ExThree."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExFour' package='pkg'>\n";
        xml_ += "<method access='"+PUBLIC_ACCESS+"' modifier='abstract' name='absgetter' class='"+PrimitiveTypeUtil.PRIM_INT+"'/>\n";
        xml_ += "</interface>\n";
        files_.put("pkg/ExFour."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExFive' package='pkg' class0='pkg.ExTwo' class1='pkg.ExThree'/>\n";
        files_.put("pkg/ExFive."+Classes.EXT, xml_);
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = context_.getClasses();
        InterfaceBlock i_ = (InterfaceBlock) classes_.getClassBody("pkg.ExFour");
        ObjectMap<FctConstraints, StringList> sgn_ = i_.getAllSignatures(classes_);
        sgn_ = InterfaceBlock.getAllOverridingMethods(sgn_, classes_);
        assertEq(1, sgn_.size());
        assertEq(new StringList("pkg.ExFour"),sgn_.getVal(new FctConstraints("absgetter", new EqList<StringList>())));
    }

    @Test
    public void getAllOverridingMethods2Test() {
        StringMap<String> files_ = new StringMap<String>();
        String xml_;
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='Ex' package='pkg' class0='pkg.ExTwo' class1='pkg.ExThree'>\n";
        xml_ += "<method access='"+PUBLIC_ACCESS+"' modifier='abstract' name='absgetter' class='"+PrimitiveTypeUtil.PRIM_INT+"'/>\n";
        xml_ += "</interface>\n";
        files_.put("pkg/Ex."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExTwo' package='pkg' class0='pkg.ExFour'/>\n";
        files_.put("pkg/ExTwo."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExThree' package='pkg' class0='pkg.ExFour'/>\n";
        files_.put("pkg/ExThree."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExFour' package='pkg'>\n";
        xml_ += "<method access='"+PUBLIC_ACCESS+"' modifier='abstract' name='absgetter' class='"+PrimitiveTypeUtil.PRIM_INT+"'/>\n";
        xml_ += "</interface>\n";
        files_.put("pkg/ExFour."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExFive' package='pkg' class0='pkg.ExTwo' class1='pkg.ExThree'/>\n";
        files_.put("pkg/ExFive."+Classes.EXT, xml_);
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = context_.getClasses();
        InterfaceBlock i_ = (InterfaceBlock) classes_.getClassBody("pkg.Ex");
        ObjectMap<FctConstraints, StringList> sgn_ = i_.getAllSignatures(classes_);
        assertEq(1, sgn_.size());
        assertEq(new StringList("pkg.Ex","pkg.ExFour"),sgn_.getVal(new FctConstraints("absgetter", new EqList<StringList>())));
        sgn_ = InterfaceBlock.getAllOverridingMethods(sgn_, classes_);
        assertEq(1, sgn_.size());
        assertEq(new StringList("pkg.Ex"),sgn_.getVal(new FctConstraints("absgetter", new EqList<StringList>())));
    }

    @Test
    public void getAllOverridingMethods3Test() {
        StringMap<String> files_ = new StringMap<String>();
        String xml_;
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='Ex' package='pkg' class0='pkg.ExTwo' class1='pkg.ExThree'>\n";
        xml_ += "</interface>\n";
        files_.put("pkg/Ex."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExTwo' package='pkg' class0='pkg.ExFour'>\n";
        xml_ += "<method access='"+PUBLIC_ACCESS+"' modifier='abstract' name='absgetter' class='"+PrimitiveTypeUtil.PRIM_INT+"'/>\n";
        xml_ += "</interface>\n";
        files_.put("pkg/ExTwo."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExThree' package='pkg' class0='pkg.ExFour'>\n";
        xml_ += "<method access='"+PUBLIC_ACCESS+"' modifier='abstract' name='absgetter' class='"+PrimitiveTypeUtil.PRIM_INT+"'/>\n";
        xml_ += "</interface>\n";
        files_.put("pkg/ExThree."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExFour' package='pkg'>\n";
        xml_ += "</interface>\n";
        files_.put("pkg/ExFour."+Classes.EXT, xml_);
        xml_ = "<interface access='"+PUBLIC_ACCESS+"' name='ExFive' package='pkg' class0='pkg.ExTwo' class1='pkg.ExThree'/>\n";
        files_.put("pkg/ExFive."+Classes.EXT, xml_);
        ContextEl context_ = unfullValidateOverridingMethods(files_);
        Classes classes_ = context_.getClasses();
        InterfaceBlock i_ = (InterfaceBlock) classes_.getClassBody("pkg.Ex");
        ObjectMap<FctConstraints, StringList> sgn_ = i_.getAllSignatures(classes_);
        assertEq(1, sgn_.size());
        assertEq(new StringList("pkg.ExTwo","pkg.ExThree"),sgn_.getVal(new FctConstraints("absgetter", new EqList<StringList>())));
        sgn_ = InterfaceBlock.getAllOverridingMethods(sgn_, classes_);
        assertEq(1, sgn_.size());
        assertEq(new StringList("pkg.ExTwo","pkg.ExThree"),sgn_.getVal(new FctConstraints("absgetter", new EqList<StringList>())));
    }
    private ContextEl unfullValidateOverridingMethods(StringMap<String> _files) {
        ContextEl cont_ = new ContextEl();
        Classes classes_ = new Classes(_files, cont_);
        cont_.setClasses(classes_);
        assertTrue(classes_.getErrorsDet().toString(), classes_.getErrorsDet().isEmpty());
        classes_.validateInheritingClasses(cont_);
        assertTrue(classes_.getErrorsDet().toString(), classes_.getErrorsDet().isEmpty());
        classes_.validateClassBodies(cont_);
        assertTrue(classes_.getErrorsDet().toString(), classes_.getErrorsDet().isEmpty());
        classes_.validateClassNames(cont_);
        assertTrue(classes_.getErrorsDet().toString(), classes_.getErrorsDet().isEmpty());
        classes_.validateFieldNames(cont_);
        assertTrue(classes_.getErrorsDet().toString(), classes_.getErrorsDet().isEmpty());
        classes_.validateFieldsId(cont_);
        assertTrue(classes_.getErrorsDet().toString(), classes_.getErrorsDet().isEmpty());
        classes_.validateMethodNames(cont_);
        assertTrue(classes_.getErrorsDet().toString(), classes_.getErrorsDet().isEmpty());
        classes_.validateMethodsId(cont_);
        assertTrue(classes_.getErrorsDet().toString(), classes_.getErrorsDet().isEmpty());
        return cont_;
    }
}
