package code.maths.litteral;
import static code.maths.EquallableMathUtil.assertEq;
import static junitparams.JUnitParamsRunner.$;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import code.maths.Rate;
import code.maths.exceptions.BadDivisionException;
import code.maths.litteral.exceptions.BadNumberArgumentException;
import code.maths.litteral.exceptions.UndefinedFunctionException;
import code.maths.litteral.exceptions.UndefinedVariableException;
import code.util.StringMap;

@RunWith(JUnitParamsRunner.class)
@SuppressWarnings("static-method")
public class MathUtilTest {
    Object[] numericStrings() {
        return $($("1","1"),
                $(".0","0"),
                $("-.0","0"),
                $("-.5","-1/2"),
                $(".5","1/2"),
                $("1.","1"),
                $("1.5","3/2"),
                $("-1.","-1"),
                $("-1.5","-3/2"),
                $("0.0","0"),
                $("0.2","1/5"),
                $("15/7","15/7"),
                $("0.1","1/10"),
                $("0.2","1/5"),
                $("15/5","3"),
                $("-3","-3"),
                $("1+2*5*7","71"),
                $("1-2*5*7","-69"),
                $("1+3*5","16"),
                $("1+3+5","9"),
                $("1-3+5","3"),
                $("1-3*5","-14"),
                $("-3*5","-15"),
                $("-3-5","-8"),
                $("3-5","-2"),
                $("1+4","5"),
                $("1+4/5","9/5"),
                $("5*3","15"),
                $("5*3/7","15/7"),
                $("5:3/7","35/3"),
                $("div(5,3/7)","35/3"),
                $("5:-3/7","-35/3"),
                $("div(5,-3/7)","-35/3"),
                $("8-6","2"),
                $("--5","5"),
                $("---5","-5"),
                $("(--5+8)","13"),
//                $("(--5","(5"),
//                $("--5)","5)"),
//                $("(5","(5"),
//                $("5)","5)"),
                $("(---5+8)","3"),
                $("1*--5+8","13"),
                $("1*---5+8","3"),
                $("9--5","14"),
                $("13---4","9"),
                $("3*8+6","30"),
                $("(3*8+6)","30"),
//                $("(3*8+6)(3*8+6)","900"),
                $("1-2*-3","7"),
                $("1-2*-3*5","31"),
                $("1-2*3*-5","31"),
                $("1-2*3*5","-29"),
                $("2*(1-2*-3)","14"),
                $("2*(1-2*-3*5)","62"),
                $("2*(1-2*3*-5)","62"),
                $("2*(1-2*3*5)","-58"),
                $("((3))","3"),
//                $("(3+9","(12"),
//                $("3+9)","12)"),
//                $("(3+9+6","(18"),
//                $("3+9+6)","18)"),
//                $("(12","(12"),
//                $("12)","12)"),
//                $("((","(("),
//                $("))","))"),
//                $("((3+9)","(12"),
//                $("(3+9))","12)"),
//                $("((3+9","((12"),
//                $("3+9))","12))"),
//                $("4(3*8+6)","120"),
                $("3*(8+6)","42"),
                $("quot(5,6)","0"),
                $("-1*quot(5,6)","0"),
                $("-quot(5,6)","0"),
                $("quot(15,6)","2"),
                $("quot(-15,6)","-3"),
//                $("quot(15,6)quot(-15,6)","-6"),
                $("mod(15,6)","3"),
                $("mod(15,5)","0"),
                $("mod(-15,5)","0"),
                $("mod(-5,6)","1"),
                $("modtaux(15,6)","3"),
                $("modtaux(15,5)","0"),
                $("modtaux(-15,5)","0"),
                $("modtaux(-5,6)","1"),
                $("puis(3,4)","81"),
                $("puis(-3,3)","-27"),
                $("puis(-2,-3)","-1/8"),
                $("puis(2,-3)","1/8"),
                $("puis(0,0)","1"),
                $("puis(0,1/2)","0"),
//                $("3puis(0,0)","3"),
                $("puis(9,1/2)","3"),
                $("puis(9,-1/2)","1/3"),
                $("puis(-9,1/2)","3"),
                $("puis(-9,-1/2)","1/3"),
                $("puis(-27,1/3)","-3"),
                $("puis(-27,2/3)","9"),
                $("puis(-27,-1/3)","-1/3"),
                $("puis(-27,-2/3)","1/9"),
                $("3*puis(1+2,2)--9","36"),
                $("3*puis(1+2,2):3--9","18"),
                $("3*div(puis(1+2,2),3)--9","18"),
                $("3*puis(1+2,5-3):3--9","18"),
                $("3*div(puis(1+2,5-3),3)--9","18"),
                $("abs(9)","9"),
                $("abs(-5)","5"),
                $("abs(0)","0"),
                $("sgn(9)","1"),
                $("sgn(-5)","-1"),
                $("sgn(0)","0"),
                $("ent(-5)","-5"),
                $("ent(-5/2)","-3"),
                $("ent(0)","0"),
                $("ent(4)","4"),
                $("ent(6/5)","1"),
                $("troncature(-5)","-5"),
                $("troncature(-5/2)","-2"),
                $("troncature(0)","0"),
                $("troncature(4)","4"),
                $("troncature(6/5)","1"),
                $("num(0)","0"),
                $("num(4)","4"),
                $("num(6/5)","6"),
                $("den(0)","1"),
                $("den(4)","1"),
                $("den(6/5)","5"),
                $("num(-5)","-5"),
                $("num(-5/2)","-5"),
                $("den(-5)","1"),
                $("den(-5/2)","2"),
                $("caracferme(2,1,3)","1"),
                $("caracferme(1,1,3)","1"),
                $("caracferme(3,1,3)","1"),
                $("caracferme(0,1,3)","0"),
                $("caracferme(4,1,3)","0"),
                $("caracferme(2,3,1)","0"),
                $("caracferme(1,3,1)","0"),
                $("caracferme(3,3,1)","0"),
                $("caracferme(0,3,1)","0"),
                $("caracferme(4,3,1)","0"),
                $("caracouvert(2,1,3)","1"),
                $("caracouvert(1,1,3)","0"),
                $("caracouvert(3,1,3)","0"),
                $("caracouvert(0,1,3)","0"),
                $("caracouvert(4,1,3)","0"),
                $("caracouvert(2,3,1)","0"),
                $("caracouvert(1,3,1)","0"),
                $("caracouvert(3,3,1)","0"),
                $("caracouvert(0,3,1)","0"),
                $("caracouvert(4,3,1)","0"),
                $("caracsemiouvertg(2,1,3)","1"),
                $("caracsemiouvertg(1,1,3)","0"),
                $("caracsemiouvertg(3,1,3)","1"),
                $("caracsemiouvertg(0,1,3)","0"),
                $("caracsemiouvertg(4,1,3)","0"),
                $("caracsemiouvertg(2,3,1)","0"),
                $("caracsemiouvertg(1,3,1)","0"),
                $("caracsemiouvertg(3,3,1)","0"),
                $("caracsemiouvertg(0,3,1)","0"),
                $("caracsemiouvertg(4,3,1)","0"),
                $("caracsemiouvertd(2,1,3)","1"),
                $("caracsemiouvertd(1,1,3)","1"),
                $("caracsemiouvertd(3,1,3)","0"),
                $("caracsemiouvertd(0,1,3)","0"),
                $("caracsemiouvertd(4,1,3)","0"),
                $("caracsemiouvertd(2,3,1)","0"),
                $("caracsemiouvertd(1,3,1)","0"),
                $("caracsemiouvertd(3,3,1)","0"),
                $("caracsemiouvertd(0,3,1)","0"),
                $("caracsemiouvertd(4,3,1)","0"),
                $("caracdroiteouvert(2,1)","1"),
                $("caracdroiteouvert(1,1)","0"),
                $("caracdroiteouvert(0,1)","0"),
                $("caracdroiteferme(2,1)","1"),
                $("caracdroiteferme(1,1)","1"),
                $("caracdroiteferme(0,1)","0"),
                $("caracgaucheouvert(2,1)","0"),
                $("caracgaucheouvert(1,1)","0"),
                $("caracgaucheouvert(0,1)","1"),
                $("caracgaucheferme(2,1)","0"),
                $("caracgaucheferme(1,1)","1"),
                $("caracgaucheferme(0,1)","1"),
                $("puis(1+5,1+2)","216"),
                $("puis(1+5,1*2)","36"),
                $("abs(4+6)","10"),
                $("abs((4+6))","10"),
                $("min(1+5,1+7)","6"),
                $("max(1+5,1+7)","8"),
                $("min(1+7,1+5)","6"),
                $("max(1+7,1+5)","8"),
                $("moy(1+5,1+7)","7"),
                $("var(1+5,1+7)","1"),
                $("min(4+19,max(12,15))","15"),
                $("2*min(4+19,max(12,15))","30"),
                $("(4+12)*2","32"),
                $("2:-min(4+19,max(12,15))","-2/15"),
                $("3:-min(4+19,max(12,15))","-1/5"),
                $("puis(4*div(1,(3060)),1/4)","29/153"),
                $("puis(3,(1+1)*2)","81"),
                $("puis(3,2*(1+1))","81"),
                $("puis(2*1+1,2+1*2)","81"),
                $("puis(3,2+1*2)","81"),
                $("puis(2+1*(1+0),2+1*2)","81"),
                $("min(2,2+1*1,2+1*2)","2"),
                $("2*min(2,2+1*1,2+1*2)","4"),
                $("1+2+3","6"),
                $("1+(2+3)","6"),
                $("1+2+(3+0)","6"),
                $("1+2*-3+3","-2"),
                $("2*-3+1+2+3","0"),
                $("2*-3+1+2+3+4","4"),
                $("abs(2*-3+1)+(1+2+3+4)","15"),
                $("abs(2*-3+1)+(1+2-3+4)","9"),
                $("abs(2*-3+1)+(1+2-3+4+00)","9"),
                $("abs(2*-3+1)+(1+2-3+4+001)","10"),
                $("12-7*2*3","-30"),
                $("2*6-7*2*3","-30"),
                $("2*(2*6-7*2*3)","-60"),
                $("2*-3+1+2-3","-6"),
                $("2+2*(5+0*1)","12"),
                $("2+2*(5+(0*1+0))","12"),
                $("cardinal({})","0"),
                $("cardinal({MY_STRING_1})","1"),
                $("cardinal(union({MY_STRING_1;MY_STRING_3},{MY_STRING_2;MY_STRING_3}))","3"),
                $("cardinal({my_string;MY_STRING})","2"),
                $("cardinal({MY_STRING_cardinal;MY_STRING_CARDINAL})","2"),
                $("cardinal({MY_STRING_ONE})","1"),
                $("cardinal({MY_STRING_ONE;MY_STRING_TWO})","2"),
                $("cardinal({MY_STRING_ONE;MY_STRING_TWO;MY_STRING_ONE})","2"),
                $("cardinal(inter({MY_STRING_ONE},{MY_STRING_ONE}))","1"),
                $("cardinal(inter({MY_STRING_ONE},{MY_STRING_TWO}))","0"),
                $("cardinal(inter({MY_STRING_ONE},{}))","0"),
                $("cardinal(inter({},{MY_STRING_TWO}))","0"),
                $("cardinal(inter({MY_STRING_ONE;MY_STRING_TWO},{MY_STRING_TWO;MY_STRING_THREE}))","1"),
                $("cardinal(union({MY_STRING_ONE},{}))","1"),
                $("cardinal(union({},{MY_STRING_ONE}))","1"),
                $("cardinal(union({},{}))","0"),
                $("cardinal(union({MY_STRING_ONE},{MY_STRING_ONE}))","1"),
                $("cardinal(union({MY_STRING_ONE},{MY_STRING_TWO}))","2"),
                $("cardinal(union({MY_STRING_ONE;MY_STRING_TWO},{MY_STRING_TWO;MY_STRING_THREE}))","3"),
                $("cardinal(complementaire({},{}))","0"),
                $("cardinal(complementaire({},{MY_STRING_ONE}))","1"),
                $("cardinal(complementaire({},{MY_STRING_ONE;MY_STRING_TWO}))","2"),
                $("cardinal(complementaire({MY_STRING_ONE},{}))","0"),
                $("cardinal(complementaire({MY_STRING_ONE},{MY_STRING_ONE}))","0"),
                $("cardinal(complementaire({MY_STRING_ONE},{MY_STRING_TWO}))","1"),
                $("cardinal(complementaire({MY_STRING_ONE},{MY_STRING_ONE;MY_STRING_TWO}))","1"),
                $("cardinal(complementaire({MY_STRING_THREE},{MY_STRING_ONE;MY_STRING_TWO}))","2"),
                $("cardinal(complementaire({MY_STRING_THREE;MY_STRING_ONE},{MY_STRING_ONE;MY_STRING_TWO}))","1"),
                $("cardinal(complementaire({MY_STRING_THREE;MY_STRING_ONE;MY_STRING_FOUR},{MY_STRING_ONE;MY_STRING_TWO}))","1"),
                $("cardinal(complementaire({MY_STRING_THREE;MY_STRING_ONE;MY_STRING_FIVE},{MY_STRING_ONE;MY_STRING_TWO;MY_STRING_FOUR}))","2"),
                $("egalnum({MY_STRING_ONE;MY_STRING_TWO},{MY_STRING_TWO})","0"),
                $("egalnum({MY_STRING_ONE;MY_STRING_TWO},{MY_STRING_TWO;MY_STRING_ONE})","1"),
                $("inclusnum(vide,vide)","1"),
                $("inclusnum(vide,{MY_STRING_ONE;MY_STRING_TWO})","1"),
                $("inclusnum({MY_STRING_ONE},{MY_STRING_TWO})","0"),
                $("inclusnum({MY_STRING_ONE;MY_STRING_TWO},vide)","0"),
                $("inclusnum({MY_STRING_ONE;MY_STRING_TWO},{})","0"),
                $("inclusnum({MY_STRING_ONE;MY_STRING_TWO},{MY_STRING_TWO})","0"),
                $("inclusnum({MY_STRING_ONE;MY_STRING_TWO},{MY_STRING_TWO;MY_STRING_ONE})","1"),
                $("noninclusnum(vide,vide)","0"),
                $("noninclusnum(vide,{MY_STRING_ONE;MY_STRING_TWO})","0"),
                $("noninclusnum({MY_STRING_ONE},{MY_STRING_TWO})","1"),
                $("noninclusnum({MY_STRING_ONE;MY_STRING_TWO},vide)","1"),
                $("noninclusnum({MY_STRING_ONE;MY_STRING_TWO},{})","1"),
                $("noninclusnum({MY_STRING_ONE;MY_STRING_TWO},{MY_STRING_TWO})","1"),
                $("noninclusnum({MY_STRING_ONE;MY_STRING_TWO},{MY_STRING_TWO;MY_STRING_ONE})","0"),
                $("egalnum({MY_STRING_ONE},vide)","0"),
                $("egalnum(vide,{MY_STRING_ONE})","0"),
                $("egalnum(inter({MY_STRING_ONE},{MY_STRING_ONE}),{MY_STRING_ONE})","1"),
                $("egalnum(inter({MY_STRING_ONE},{MY_STRING_TWO}),{})","1"),
                $("egalnum(inter({MY_STRING_ONE;MY_STRING_TWO},{MY_STRING_TWO;MY_STRING_THREE}),{MY_STRING_TWO})","1"),
                $("egalnum(union({MY_STRING_ONE},{MY_STRING_ONE}),{MY_STRING_ONE})","1"),
                $("egalnum(union({MY_STRING_ONE},{MY_STRING_TWO}),{MY_STRING_ONE;MY_STRING_TWO})","1"),
                $("egalnum(union({MY_STRING_ONE;MY_STRING_TWO},{MY_STRING_TWO;MY_STRING_THREE}),{MY_STRING_ONE;MY_STRING_TWO;MY_STRING_THREE})","1"),
                $("differentnum({MY_STRING_ONE},vide)","1"),
                $("differentnum(vide,{MY_STRING_ONE})","1"),
                $("differentnum({MY_STRING_TWO},{MY_STRING_ONE})","1"),
                $("differentnum(inter({MY_STRING_ONE},{MY_STRING_ONE}),{MY_STRING_ONE})","0"),
                $("differentnum(inter({MY_STRING_ONE},{MY_STRING_TWO}),{})","0"),
                $("differentnum(inter({MY_STRING_ONE;MY_STRING_TWO},{MY_STRING_TWO;MY_STRING_THREE}),{MY_STRING_TWO})","0"),
                $("differentnum(union({MY_STRING_ONE},{MY_STRING_ONE}),{MY_STRING_ONE})","0"),
                $("differentnum(union({MY_STRING_ONE},{MY_STRING_TWO}),{MY_STRING_ONE;MY_STRING_TWO})","0"),
                $("differentnum(union({12my_string},{MY_STRING_TWO}),{12my_string;MY_STRING_TWO})","0"),
                $("differentnum(union({MY_STRING_ONE;MY_STRING_TWO},{MY_STRING_TWO;MY_STRING_THREE}),{MY_STRING_ONE;MY_STRING_TWO;MY_STRING_THREE})","0"));
    }

    @Test(timeout=1000)
    @Parameters(method="numericStrings")
    public void evaluateExp1Test(String _numericString,String _res) {
        Argument a_ = MathUtil.processEl(_numericString, 0, false, new StringMap<String>());
        assertEq(new Rate(_res),(Rate)a_.getObject());
    }


    @Test
    public void evaluateTest() {
        StringMap<String> variables_ = new StringMap<String>();
        variables_.put("VARIABLE", "2");
        String numericString_ = "8:VARIABLE";
        assertEq(new Rate("4"), (Rate)MathUtil.processEl(numericString_, 0, false, variables_).getObject());
    }

    @Test(timeout=1000)
    public void check1Test() {
        Argument a_ = MathUtil.processEl("1:0", 0, true, new StringMap<String>());
        assertEq(MathType.RATE.name(), a_.getArgClass().name());

    }

    Object[] evaluateExpressionFail() {
        return $($("(2*1+1):0"),
                $("(2*1+1):0+1"),
                $("1+(2*1+1):0"),
                $("1+(2*1+1):0+8"),
                $("1+((2*1+1):0)+8"),
                $("(1:0)+4"),
                $("div(1,0)+4"),
                $("1:0+1"),
                $("1:-0+1"),
                $("div(1,-0)+1"),
                $("1+2*5:0"),
                $("1-2*5:0"),
                $("1+3:0"),
                $("1+3+5:0"),
                $("1-3+5:0"),
                $("1-3:0"),
                $("-3*5:0"),
                $("-3-5:0"),
                $("3-5:0"),
                $("1:0"),
                $("1:-0"),
                $("quot(1,0)"),
                $("quot(1,-0)"),
                $("mod(1,0)"),
                $("mod(1,-0)"),
                $("modtaux(1,0)"),
                $("modtaux(1,-0)"),
//                $("string(0,-1)"),
                $("abs(1:0)"),
                $("quot(1,1:0)"),
                $("quot(1,1:0+1)"),
                $("2*quot(1:0,1)"),
                $("2*quot(1,1:0)"),
                $("2*quot(1,0)"),
                $("quot(1:0,1)"),
//                $("(1:0"),
//                $("1:0)"),
                $("quot(1:0+1,1)"),
                $("-1:0+1"),
                $("puis(0,-1)"),
                $("-1:0"),
                $("(-1:0+1)+1"));
    }

    @Test(expected=BadDivisionException.class)
    @Parameters(method="evaluateExpressionFail")
    public void evaluateExp_2FailTest(String _input) {
        MathUtil.processEl(_input, 0, false, new StringMap<String>());
    }
    Object[] evaluateExpressionFailDivZero() {
        return $($("(2*1+1):0"),
                $("(2*1+1):0+1"),
                $("1+(2*1+1):0"),
                $("1+(2*1+1):0+8"),
                $("1+((2*1+1):0)+8"),
                $("(1:0)+4"),
                $("div(1,0)+4"),
                $("1:0+1"),
                $("1:-0+1"),
                $("div(1,-0)+1"),
                $("1+2*5:0"),
                $("1-2*5:0"),
                $("1+3:0"),
                $("1+3+5:0"),
                $("1-3+5:0"),
                $("1-3:0"),
                $("-3*5:0"),
                $("-3-5:0"),
                $("3-5:0"),
                $("1:0"),
                $("1:-0"),
                $("quot(1,0)"),
                $("quot(1,-0)"),
                $("mod(1,0)"),
                $("mod(1,-0)"),
                $("modtaux(1,0)"),
                $("modtaux(1,-0)"),
//                $("string(0,-1)"),
                $("abs(1:0)"),
                $("quot(1,1:0)"),
                $("quot(1,1:0+1)"),
                $("2*quot(1:0,1)"),
                $("2*quot(1,1:0)"),
                $("2*quot(1,0)"),
                $("quot(1:0,1)"),
//                $("(1:0"),
//                $("1:0)"),
                $("quot(1:0+1,1)"),
                $("-1:0+1"),
                $("puis(0,-1)"),
                $("-1:0"),
                $("(-1:0+1)+1"));
    }

    @Test(expected=BadDivisionException.class)
    @Parameters(method="evaluateExpressionFailDivZero")
    public void evaluateExp_DivZero_FailTest(String _input) {
        MathUtil.processEl(_input, 0, false, new StringMap<String>());
    }

    @Test(expected=BadNumberArgumentException.class)
    public void evaluateExp_unknownNbArgFct_FailTest() {
        MathUtil.processEl("min()", 0, false, new StringMap<String>());
    }

    @Test(expected=UndefinedFunctionException.class)
    public void evaluateExp_unknownFct_FailTest() {
        MathUtil.processEl("string(0,-1)", 0, false, new StringMap<String>());
    }

    @Test(expected=UndefinedVariableException.class)
    public void evaluateExp_unknownVar_FailTest() {
        MathUtil.processEl("VAR__UNDEFINED+1", 0, false, new StringMap<String>());
    }

    Object[] booleanStrings() {
        return $($("V",true),
                $("F",false),
                $("1=1",true),
                $("1=abs(1)",true),
                $("1=2",false),
                $("1!=1",false),
                $("1!=abs(1)",false),
                $("1!=2",true),
                $("1<0",false),
                $("1<1",false),
                $("1<2",true),
                $("1<=0",false),
                $("1<=1",true),
                $("1<=2",true),
                $("1>0",true),
                $("1>1",false),
                $("1>2",false),
                $("1>=0",true),
                $("1>=1",true),
                $("1>=2",false),
                $("1=1&2<3",true),
                $("1=1&2<puis(3,2)",true),
                $("1=1&2>3",false),
                $("1!=1&2>3",false),
                $("1=1|2<3",true),
                $("1=1|2<puis(2,3)",true),
                $("1=1|2>3&4<8&1>0",true),
                $("(1!=1|2>3|5<6)&4<8&1>0",true),
                $("1=1|2>3",true),
                $("1!=1|2>3",false),
                $("1!=1|2<3",true),
                $("2<3|1!=1&4<3",true),
                $("2<3|1!=abs(1)&4<3",true),
                $("(2<3|1!=1)&4<3",false),
                $("(2<3|1!=abs(1))&4<3",false),
                $("(2<3|abs(1)!=1)&4<3",false),
                $("4<3&(2<3|abs(1)!=1)",false),
                $("4<3&(abs(1)!=1|2<3)",false),
                $("(2<3|1!=abs(1))&puis(2,2)<3",false),
                $("2<3&(((2<3|1!=abs(1))&puis(2,2)<3)|puis(2,2)<3)",false),
                $("2<3&(((2<3|1!=abs(1))&puis(2,2)<3)|((2<3|1!=abs(1))&puis(2,2)<3))",false),
                $("(2<3&1=1|2<4)=V",true),
                $("2<3&1=1|2<4",true),
                $("2<3&((1=1))|2<4",true),
                $("2<3&((1)=(1))|2<4",true),
                $("2<3&((1))=((1))|2<4",true),
                $("V&1=1|2<4",true),
                $("F|1=1|2<4",true),
                $("2<3&!(((2<3|1!=abs(1))&puis(2,2)<3)|((2<3|1!=abs(1))&puis(2,2)<3))",true),
                $("2<3&!!(((2<3|1!=abs(1))&puis(2,2)<3)|((2<3|1!=abs(1))&puis(2,2)<3))",false),
                $("((2<3)|1!=(abs(1)))&puis(2,2)<3",false),
                $("2<3&((1)=(1))|(2<4&6>=5)",true),
                $("1=1|(2<4&6>=5)",true),
                $("cardinal({V;F})=2",true),
                $("cardinal({1a;2b})=2",true),
                $("cardinal({V;F})=2&(V)",true),
                $("cardinal({1a;2b})=2&(F)",false),
                $("1<=1&(1+1=2&2+3=5|5<6)",true),
                $("1<=1&(1+1=2&(2+3=5|5<6))",true),
                $("1<=1|(1+1=2&1+1=2&(2+3=5|5<6))",true),
                $("1<=1|(1+1=2|1+1=2&1+1=2&(2+3=5|5<6&5<6))",true),
                $("1<=1&(1+1=2&1+1=2&(2+3=5|5<6))",true),
                $("1>=1&(1<=1|(1+1=2|(2+3=5|5<6)))",true),
                $("1<=1|(1+1=2|(2+3=5|5<6))",true),
                $("(2<3)=(1=1)",true),
                $("(-2<3)=(1=1)",true),
                $("(2<3)=(1!=1)",false),
                $("(2>3)=(1!=1)",true),
                $("(2>3)=(1=1)",false),
                $("(2<3)!=(1=1)",false),
                $("(2>3)!=(1=1)",true),
                $("(2<3)!=(1!=1)",true),
                $("(2>3)!=(1!=1)",false),
                $("(-2>3)!=(1!=1)",false),
                $("-1<4",true),
                $("-1>4",false),
                $("-1<=4",true),
                $("-1>=4",false),
                $("-1=4",false),
                $("-1!=4",true),
                $("1<-4",false),
                $("1>-4",true),
                $("1<=-4",false),
                $("1>=-4",true),
                $("1=-4",false),
                $("1!=-4",true),
                $("((1!=-4))",true),
                $("1+1=2|2+2=4|3+3=6",true),
                $("(V)",true),
                $("((V))",true),
                $("(F)",false),
                $("((F))",false),
                $("6+7>10&(1<2|4>3)",true),
                $("6+7>10&(V|4>3)",true),
                $("6+7>10&(1<2|V)",true),
                $("6+7<=10&(1<2|4>3)",false),
                $("6+7<=10&(V|4>3)",false),
                $("6+7<=10&(1<2|V)",false),
                $("1+1=2|1:0=8",true),
                $("1+1!=2&1:0=8",false));
    }

    @Test(timeout=1000)
    @Parameters(method="booleanStrings")
    public void evaluateExp2Test(String _numericString,boolean _res) {
        Argument a_ = MathUtil.processEl(_numericString, 0, false, new StringMap<String>());
        assertEq(_res,a_.getObject());
    }
}
