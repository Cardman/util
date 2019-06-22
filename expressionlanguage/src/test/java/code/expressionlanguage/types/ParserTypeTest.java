package code.expressionlanguage.types;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.InitializationLgNames;
import code.expressionlanguage.methods.Classes;
import code.expressionlanguage.options.Options;
import code.util.Ints;
import code.util.StringMap;
import org.junit.Test;

import static code.expressionlanguage.EquallableElUtil.assertEq;
import static org.junit.Assert.assertTrue;

public class ParserTypeTest {

    @Test
    public void getIndexes1Test(){
        ContextEl cont_ = unfullValidateInheritingClassesDeps(new StringMap<String>());
        Ints indexes_ = ParserType.getIndexes("int", cont_);
        assertEq(0, indexes_.size());
    }

    @Test
    public void getIndexes2Test(){
        ContextEl cont_ = unfullValidateInheritingClassesDeps(new StringMap<String>());
        Ints indexes_ = ParserType.getIndexes("String", cont_);
        assertEq(0, indexes_.size());
    }

    @Test
    public void getIndexes3Test(){
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex", cont_);
        assertEq(0, indexes_.size());
    }
    @Test
    public void getIndexes4Test(){
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("Ex", cont_);
        assertEq(0, indexes_.size());
    }

    @Test
    public void getIndexes5Test(){
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex", cont_);
        assertEq(0, indexes_.size());
    }
    @Test
    public void getIndexes6Test(){
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("Ex", cont_);
        assertEq(0, indexes_.size());
    }
    @Test
    public void getIndexes7Test(){
        ContextEl cont_ = unfullValidateInheritingClassesDeps(new StringMap<String>());
        Ints indexes_ = ParserType.getIndexes("int[]", cont_);
        assertEq(0, indexes_.size());
    }

    @Test
    public void getIndexes8Test(){
        ContextEl cont_ = unfullValidateInheritingClassesDeps(new StringMap<String>());
        Ints indexes_ = ParserType.getIndexes("String[]", cont_);
        assertEq(0, indexes_.size());
    }

    @Test
    public void getIndexes9Test(){
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex[]", cont_);
        assertEq(0, indexes_.size());
    }
    @Test
    public void getIndexes10Test(){
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("Ex[]", cont_);
        assertEq(0, indexes_.size());
    }

    @Test
    public void getIndexes11Test(){
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex[]", cont_);
        assertEq(0, indexes_.size());
    }
    @Test
    public void getIndexes12Test(){
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("Ex[]", cont_);
        assertEq(0, indexes_.size());
    }
    @Test
    public void getIndexes13Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex<String>", cont_);
        assertEq(2, indexes_.size());
        assertEq(6, indexes_.first());
        assertEq(13, indexes_.last());
    }
    @Test
    public void getIndexes14Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex<String>[]", cont_);
        assertEq(2, indexes_.size());
        assertEq(6, indexes_.first());
        assertEq(13, indexes_.last());
    }
    @Test
    public void getIndexes15Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("public class pkg.ExTwo {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex<pkg.ExTwo>", cont_);
        assertEq(2, indexes_.size());
        assertEq(6, indexes_.first());
        assertEq(16, indexes_.last());
    }
    @Test
    public void getIndexes16Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("public class pkg.ExTwo {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex<pkg.ExTwo>[]", cont_);
        assertEq(2, indexes_.size());
        assertEq(6, indexes_.first());
        assertEq(16, indexes_.last());
    }
    @Test
    public void getIndexes17Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T,U> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("public class pkg.ExTwo {}\n");
        xml_.append("public class pkg.ExThree {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex<pkg.ExTwo,pkg.ExThree>", cont_);
        assertEq(3, indexes_.size());
        assertEq(6, indexes_.first());
        assertEq(16, indexes_.get(1));
        assertEq(28, indexes_.last());
    }
    @Test
    public void getIndexes18Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T,U> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("public class pkg.ExTwo {}\n");
        xml_.append("public class pkg.ExThree {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex<pkg.ExTwo,pkg.ExThree>[]", cont_);
        assertEq(3, indexes_.size());
        assertEq(6, indexes_.first());
        assertEq(16, indexes_.get(1));
        assertEq(28, indexes_.last());
    }
    @Test
    public void getIndexes19Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("public class pkg.ExTwo {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex<ExTwo>", cont_);
        assertEq(2, indexes_.size());
        assertEq(6, indexes_.first());
        assertEq(12, indexes_.last());
    }
    @Test
    public void getIndexes20Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("public class pkg.ExTwo {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex<ExTwo>[]", cont_);
        assertEq(2, indexes_.size());
        assertEq(6, indexes_.first());
        assertEq(12, indexes_.last());
    }
    @Test
    public void getIndexes21Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("public class pkg.ExTwo {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("Ex<pkg.ExTwo>", cont_);
        assertEq(2, indexes_.size());
        assertEq(2, indexes_.first());
        assertEq(12, indexes_.last());
    }
    @Test
    public void getIndexes22Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("public class pkg.ExTwo {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("Ex<pkg.ExTwo>[]", cont_);
        assertEq(2, indexes_.size());
        assertEq(2, indexes_.first());
        assertEq(12, indexes_.last());
    }
    @Test
    public void getIndexes23Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T,U> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("public class pkg.ExTwo {}\n");
        xml_.append("public class pkg.ExThree {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex<ExTwo,pkg.ExThree>", cont_);
        assertEq(3, indexes_.size());
        assertEq(6, indexes_.first());
        assertEq(12, indexes_.get(1));
        assertEq(24, indexes_.last());
    }
    @Test
    public void getIndexes24Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T,U> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("public class pkg.ExTwo {}\n");
        xml_.append("public class pkg.ExThree {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex<ExTwo,pkg.ExThree>[]", cont_);
        assertEq(3, indexes_.size());
        assertEq(6, indexes_.first());
        assertEq(12, indexes_.get(1));
        assertEq(24, indexes_.last());
    }
    @Test
    public void getIndexes25Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T,U> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("public class pkg.ExTwo {}\n");
        xml_.append("public class pkg.ExThree {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex<pkg.ExTwo,ExThree>", cont_);
        assertEq(3, indexes_.size());
        assertEq(6, indexes_.first());
        assertEq(16, indexes_.get(1));
        assertEq(24, indexes_.last());
    }
    @Test
    public void getIndexes26Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T,U> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("public class pkg.ExTwo {}\n");
        xml_.append("public class pkg.ExThree {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex<pkg.ExTwo,ExThree>[]", cont_);
        assertEq(3, indexes_.size());
        assertEq(6, indexes_.first());
        assertEq(16, indexes_.get(1));
        assertEq(24, indexes_.last());
    }
    @Test
    public void getIndexes27Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T,U> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("public class pkg.ExTwo {}\n");
        xml_.append("public class pkg.ExThree {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex<ExTwo,ExThree>", cont_);
        assertEq(3, indexes_.size());
        assertEq(6, indexes_.first());
        assertEq(12, indexes_.get(1));
        assertEq(20, indexes_.last());
    }
    @Test
    public void getIndexes28Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T,U> {}\n");
        files_.put("pkg/Ex", xml_.toString());
        xml_ = new StringBuilder();
        xml_.append("public class pkg.ExTwo {}\n");
        xml_.append("public class pkg.ExThree {}\n");
        files_.put("pkg/ExTwo", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex<ExTwo,ExThree>[]", cont_);
        assertEq(3, indexes_.size());
        assertEq(6, indexes_.first());
        assertEq(12, indexes_.get(1));
        assertEq(20, indexes_.last());
    }
    @Test
    public void getIndexes29Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex {\n");
        xml_.append(" public class Inner {}\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex.Inner", cont_);
        assertEq(1, indexes_.size());
        assertEq(6, indexes_.first());
    }
    @Test
    public void getIndexes30Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex {\n");
        xml_.append(" public class Inner {}\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex.Inner[]", cont_);
        assertEq(1, indexes_.size());
        assertEq(6, indexes_.first());
    }
    @Test
    public void getIndexes31Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex {\n");
        xml_.append(" public class Inner {}\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("Ex.Inner", cont_);
        assertEq(1, indexes_.size());
        assertEq(2, indexes_.first());
    }
    @Test
    public void getIndexes32Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex {\n");
        xml_.append(" public class Inner {}\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("Ex.Inner[]", cont_);
        assertEq(1, indexes_.size());
        assertEq(2, indexes_.first());
    }
    @Test
    public void getIndexes33Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex {\n");
        xml_.append(" public class Inner<T> {}\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.Ex.Inner<pkg.Ex>", cont_);
        assertEq(3, indexes_.size());
        assertEq(6, indexes_.first());
        assertEq(12, indexes_.get(1));
        assertEq(19, indexes_.last());
    }
    @Test
    public void getIndexes34Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex {\n");
        xml_.append(" public class Inner {}\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExTwo<U,V> {\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.ExTwo<pkg.Ex.Inner,pkg.Ex>[]", cont_);
        assertEq(4, indexes_.size());
        assertEq(9, indexes_.first());
        assertEq(16, indexes_.get(1));
        assertEq(22, indexes_.get(2));
        assertEq(29, indexes_.last());
    }
    @Test
    public void getIndexes35Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex {\n");
        xml_.append(" public class Inner<T> {}\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExTwo<U,V> {\n");
        xml_.append("}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("pkg.ExTwo<pkg.Ex.Inner<pkg.Ex>,pkg.Ex>[]", cont_);
        assertEq(6, indexes_.size());
        assertEq(9, indexes_.first());
        assertEq(16, indexes_.get(1));
        assertEq(22, indexes_.get(2));
        assertEq(29, indexes_.get(3));
        assertEq(30, indexes_.get(4));
        assertEq(37, indexes_.last());
    }
    @Test
    public void getIndexes36Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T,U> {\n");
        xml_.append(" public class Inner {}\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExTwo {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("Ex<String,ExTwo>.Inner", cont_);
        assertEq(4, indexes_.size());
        assertEq(2, indexes_.first());
        assertEq(9, indexes_.get(1));
        assertEq(15, indexes_.get(2));
        assertEq(16, indexes_.last());
    }
    @Test
    public void getIndexes37Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T,U> {\n");
        xml_.append(" public class Inner<V> {}\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExTwo {}\n");
        xml_.append("public class pkg.ExThree {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("Ex<String,ExTwo>.Inner<pkg.ExThree>", cont_);
        assertEq(6, indexes_.size());
        assertEq(2, indexes_.first());
        assertEq(9, indexes_.get(1));
        assertEq(15, indexes_.get(2));
        assertEq(16, indexes_.get(3));
        assertEq(22, indexes_.get(4));
        assertEq(34, indexes_.last());
    }
    @Test
    public void getIndexes38Test() {
        StringMap<String> files_ = new StringMap<String>();
        StringBuilder xml_;
        xml_ = new StringBuilder();
        xml_.append("public class pkg.Ex<T,U> {\n");
        xml_.append(" public class Inner<V> {}\n");
        xml_.append("}\n");
        xml_.append("public class pkg.ExTwo {}\n");
        xml_.append("public class pkg.ExThree {}\n");
        files_.put("pkg/Ex", xml_.toString());
        ContextEl cont_ = unfullValidateInheritingClassesDeps(files_);
        Ints indexes_ = ParserType.getIndexes("Ex<String,ExTwo>.Inner<String>", cont_);
        assertEq(6, indexes_.size());
        assertEq(2, indexes_.first());
        assertEq(9, indexes_.get(1));
        assertEq(15, indexes_.get(2));
        assertEq(16, indexes_.get(3));
        assertEq(22, indexes_.get(4));
        assertEq(29, indexes_.last());
    }
    private ContextEl unfullValidateInheritingClassesDeps(StringMap<String> _files) {
        ContextEl cont_ = contextEnElDefault();
        Classes classes_ = cont_.getClasses();
        Classes.buildPredefinedBracesBodies(cont_);
        Classes.tryBuildBracedClassesBodies(_files, cont_, false);
        assertTrue(classes_.displayErrors(), classes_.isEmptyErrors());
        assertTrue(classes_.displayErrors(), classes_.isEmptyErrors());
        classes_.validateInheritingClassesId(cont_, false);
        assertTrue(classes_.displayErrors(), classes_.isEmptyErrors());
        return cont_;
    }
    private static ContextEl contextEnElDefault() {
        Options opt_ = new Options();
        opt_.setSingleInnerParts(true);
        return InitializationLgNames.buildStdOne("en", opt_);
    }
}
