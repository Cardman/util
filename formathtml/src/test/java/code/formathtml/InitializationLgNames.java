package code.formathtml;

import org.junit.Assert;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.DefaultInitializer;
import code.expressionlanguage.DefaultLockingClass;
import code.expressionlanguage.options.ContextFactory;
import code.expressionlanguage.options.KeyWords;
import code.expressionlanguage.options.Options;
import code.expressionlanguage.stds.LgNames;
import code.formathtml.classes.CustBeanLgNames;
import code.formathtml.classes.CustLgNames;
import code.formathtml.util.BeanLgNames;
import code.util.CustList;

public final class InitializationLgNames {

    private InitializationLgNames(){
    }

    public static ContextEl buildStdOne(Options _opt) {
        BeanLgNames lgNames_ = new CustBeanLgNames();
        basicStandards(lgNames_);
        lgNames_.setAliasRate("java.lang.Long");
        lgNames_.setAliasDataBase("code.formathtml.classes.SimpleDataBase");
        ContextEl context_ = build(CustList.INDEX_NOT_FOUND_ELT,lgNames_, _opt);
        Assert.assertTrue(context_.getClasses().isEmptyStdError());
        return context_;
    }
    public static ContextEl buildStdTwo(Options _opt) {
        BeanLgNames lgNames_ = new CustLgNames();
        basicStandards(lgNames_);
        lgNames_.setAliasMath("java.lang.$math");
        ContextEl context_ = build(CustList.INDEX_NOT_FOUND_ELT,lgNames_, _opt);
        Assert.assertTrue(context_.getClasses().isEmptyStdError());
        return context_;
    }
    public static ContextEl build(int _stack,LgNames _lgNames, Options _opt) {
        DefaultLockingClass lk_ = new DefaultLockingClass();
        DefaultInitializer di_ = new DefaultInitializer();
        KeyWords kw_ = new KeyWords();
        ContextEl out_ = ContextFactory.build(_stack,lk_, di_, _opt, kw_, _lgNames,4);
        return out_;
    }
    public static BeanLgNames initStandards(ContextEl _cont) {
        BeanLgNames lgNames_ = new CustBeanLgNames();
        _cont.setStandards(lgNames_);
        lgNames_.setAliasRate("java.lang.Long");
        lgNames_.setAliasDataBase("code.formathtml.classes.SimpleDataBase");
        basicStandards(lgNames_);
        lgNames_.build();
        lgNames_.setupOverrides(_cont);
        return lgNames_;
    }
    private static void basicStandards(BeanLgNames _lgNames) {
        _lgNames.setDefaultPkg("java.lang");
        _lgNames.setAliasObject("java.lang.Object");
        _lgNames.setAliasVoid("$void");
        _lgNames.setAliasCharSequence("java.lang.CharSequence");
        _lgNames.setAliasDisplayable("code.util.ints.Displayable");
        _lgNames.setAliasDisplay("display");
        _lgNames.setAliasCompareTo("compareTo");
        _lgNames.setAliasCompare("compare");
        _lgNames.setAliasEquals("equals");
        _lgNames.setAliasToString("toString");
        _lgNames.setAliasValueOf("valueOf");
        _lgNames.setAliasMaxValueField("MAX_VALUE");
        _lgNames.setAliasMinValueField("MIN_VALUE");
        _lgNames.setAliasIteratorType("java.lang.$iterator");
        _lgNames.setAliasIterator("iterator");
        _lgNames.setAliasIterable("java.lang.$iterable");
        _lgNames.setAliasEnumParam("java.lang.$Enum");
        _lgNames.setAliasEnum("java.lang.$en");
        _lgNames.setAliasEnums("java.lang.$enums");
        _lgNames.setAliasReplacement("code.util.Replacement");
//        _lgNames.setAliasStore("$badStore");
        _lgNames.setAliasStore("code.expressionlanguage.exceptions.DynamicArrayStoreException");
//        _lgNames.setAliasNullPe("$npe");
        _lgNames.setAliasNullPe("code.util.exceptions.NullObjectException");
        _lgNames.setAliasBadEncode("java.lang.$enc");
//        _lgNames.setAliasBadIndex("$badIndex");
        _lgNames.setAliasBadIndex("code.expressionlanguage.exceptions.BadIndexException");
        _lgNames.setAliasIllegalArg("code.expressionlanguage.exceptions.IllegalArgument");
//        _lgNames.setAliasBadSize("$badSize");
        _lgNames.setAliasBadSize("code.expressionlanguage.exceptions.NegativeSizeException");
//        _lgNames.setAliasError("$error");
        _lgNames.setAliasError("java.lang.Exception");
        _lgNames.setAliasGetMessage("getMessage");
//        _lgNames.setAliasCast("$badCast");
        _lgNames.setAliasCast("code.expressionlanguage.exceptions.DynamicCastClassException");
        _lgNames.setAliasDivisionZero("code.expressionlanguage.exceptions.DivideZeroException");
        //_lgNames.setAliasSof("$sofe");
        //_lgNames.setAliasMath("$math");
        _lgNames.setAliasMath("java.lang.Math");
        _lgNames.setAliasAbs("abs");
        _lgNames.setAliasMod("mod");
        _lgNames.setAliasQuot("quot");
        _lgNames.setAliasSof("code.expressionlanguage.exceptions.StackOverFlow");
        _lgNames.setAliasNbFormat("java.lang.badFormat");
        _lgNames.setAliasPrimBoolean("$boolean");
        _lgNames.setAliasPrimByte("$byte");
        _lgNames.setAliasPrimShort("$short");
        _lgNames.setAliasPrimChar("$char");
        _lgNames.setAliasPrimInteger("$int");
        _lgNames.setAliasPrimLong("$long");
        _lgNames.setAliasPrimFloat("$float");
        _lgNames.setAliasPrimDouble("$double");
        _lgNames.setAliasBoolean("java.lang.Boolean");
        _lgNames.setAliasByte("java.lang.Byte");
        _lgNames.setAliasShort("java.lang.Short");
        _lgNames.setAliasCharacter("java.lang.Character");
        _lgNames.setAliasInteger("java.lang.Integer");
        _lgNames.setAliasLong("java.lang.Long");
        _lgNames.setAliasFloat("java.lang.Float");
        _lgNames.setAliasDouble("java.lang.Double");
        _lgNames.setAliasNumber("java.lang.Number");
        _lgNames.setAliasParseBoolean("parseBoolean");
        _lgNames.setAliasParseByte("parseByte");
        _lgNames.setAliasParseShort("parseShort");
        _lgNames.setAliasParseInt("parseInt");
        _lgNames.setAliasParseLong("parseLong");
        _lgNames.setAliasParseFloat("parseFloat");
        _lgNames.setAliasParseDouble("parseDouble");
        _lgNames.setAliasParseByteOrNull("parseByteOrNull");
        _lgNames.setAliasParseShortOrNull("parseShortOrNull");
        _lgNames.setAliasParseIntOrNull("parseIntOrNull");
        _lgNames.setAliasParseLongOrNull("parseLongOrNull");
        _lgNames.setAliasParseFloatOrNull("parseFloatOrNull");
        _lgNames.setAliasParseDoubleOrNull("parseDoubleOrNull");
        _lgNames.setAliasBooleanValue("booleanValue");
        _lgNames.setAliasByteValue("byteValue");
        _lgNames.setAliasShortValue("shortValue");
        _lgNames.setAliasCharValue("charValue");
        _lgNames.setAliasIntValue("intValue");
        _lgNames.setAliasLongValue("longValue");
        _lgNames.setAliasFloatValue("floatValue");
        _lgNames.setAliasDoubleValue("doubleValue");
        _lgNames.setAliasDigit("digit");
        _lgNames.setAliasIsDigit("isDigit");
        _lgNames.setAliasIsLetter("isLetter");
        _lgNames.setAliasIsLetterOrDigit("isLetterOrDigit");
        _lgNames.setAliasIsWordChar("isWordChar");
        _lgNames.setAliasIsLowerCase("isLowerCase");
        _lgNames.setAliasIsUpperCase("isUpperCase");
        _lgNames.setAliasIsWhitespace("isWhitespace");
        _lgNames.setAliasIsSpace("isSpace");
        _lgNames.setAliasIsInfinite("isInfinite");
        _lgNames.setAliasIsNan("isNan");
        _lgNames.setAliasForDigit("forDigit");
        _lgNames.setAliasGetDirectionality("getDirectionality");
        _lgNames.setAliasGetType("getType");
        _lgNames.setAliasGetCharType("getType");
        _lgNames.setAliasString("java.lang.String");
        _lgNames.setAliasLength("length");
        _lgNames.setAliasCharAt("charAt");
        _lgNames.setAliasToCharArray("toCharArray");
        _lgNames.setAliasSplit("split");
        _lgNames.setAliasSplitChars("splitChars");
        _lgNames.setAliasSplitStrings("splitStrings");
        _lgNames.setAliasReplace("replace");
        _lgNames.setAliasReplaceMultiple("replaceMultiple");
        _lgNames.setAliasEqualsIgnoreCase("equalsIgnoreCase");
        _lgNames.setAliasCompareToIgnoreCase("compareToIgnoreCase");
        _lgNames.setAliasContains("contains");
        _lgNames.setAliasEndsWith("endsWith");
        _lgNames.setAliasStartsWith("startsWith");
        _lgNames.setAliasIndexOf("indexOf");
        _lgNames.setAliasFormat("format");
        _lgNames.setAliasGetBytes("getBytes");
        _lgNames.setAliasIsEmpty("isEmpty");
        _lgNames.setAliasLastIndexOf("lastIndexOf");
        _lgNames.setAliasRegionMatches("regionMatches");
        _lgNames.setAliasSubstring("substring");
        _lgNames.setAliasSubSequence("subSequence");
        _lgNames.setAliasToLowerCase("toLowerCase");
        _lgNames.setAliasToUpperCase("toUpperCase");
        _lgNames.setAliasTrim("trim");
        _lgNames.setAliasStringBuilder("java.lang.StringBuilder");
        _lgNames.setAliasAppend("append");
        _lgNames.setAliasCapacity("capacity");
        _lgNames.setAliasClear("clear");
        _lgNames.setAliasDelete("delete");
        _lgNames.setAliasDeleteCharAt("deleteCharAt");
        _lgNames.setAliasEnsureCapacity("ensureCapacity");
        _lgNames.setAliasInsert("insert");
        _lgNames.setAliasReverse("reverse");
        _lgNames.setAliasSetCharAt("setCharAt");
        _lgNames.setAliasSetLength("setLength");
        _lgNames.setAliasSame("same");
        _lgNames.setAliasTrimToSize("trimToSize");
        _lgNames.setAliasGet("get");
        _lgNames.setAliasSize("size");
        _lgNames.setAliasSimpleIterator("iterator");
        _lgNames.setAliasErrorInitClass("java.lang.$defErrorClass");
        _lgNames.setAliasClone("clone");
        _lgNames.setAliasReadResources("readContent");
        _lgNames.setAliasReadResourcesNames("readNames");
        _lgNames.setAliasResources("java.lang.Resources");
        _lgNames.setAliasEnumValues("values");
        _lgNames.setAliasInvokeTarget("java.lang.$invokeTaget");
        _lgNames.setAliasClassNotFoundError("java.lang.$classNotFound");
        _lgNames.setAliasGetVariableOwner("getVariableOwner");
        _lgNames.setAliasGetGenericVariableOwner("getGenericVariableOwner");
        _lgNames.setAliasGetString("getString");
        _lgNames.setAliasClass("java.lang.$Class");
        _lgNames.setAliasStackTraceElement("java.lang.$stack");
        _lgNames.setAliasCurrentStack("current");
        _lgNames.setAliasFct("java.lang.$Fct");
        _lgNames.setAliasCall("call");
        _lgNames.setAliasAnnotation("java.lang.$Annotation");
        _lgNames.setAliasAnnotated("java.lang.$Annotated");
        _lgNames.setAliasGetAnnotations("getAnnotations");
        _lgNames.setAliasGetDefaultValue("getDefaultValue");
        _lgNames.setAliasGetAnnotationsParameters("getAnnotationsParameters");
        _lgNames.setAliasGetDeclaredConstructors("getDeclaredConstructors");
        _lgNames.setAliasGetDeclaredFields("getDeclaredFields");
        _lgNames.setAliasGetDeclaredMethods("getDeclaredMethods");
        _lgNames.setAliasMakeGeneric("makeGeneric");
        _lgNames.setAliasGetAllClasses("getAllClasses");
        _lgNames.setAliasGetOperators("getOperators");
        _lgNames.setAliasConstructor("java.lang.$Constructor");
        _lgNames.setAliasField("java.lang.$Field");
        _lgNames.setAliasMethod("java.lang.$Method");
        _lgNames.setAliasInvoke("invoke");
        _lgNames.setAliasNewInstance("newInstance");
        _lgNames.setAliasIsPolymorph("isPolymorph");
        _lgNames.setAliasSetPolymorph("setPolymorph");
        _lgNames.setAliasIsAbstract("isAbstract");
        _lgNames.setAliasGetName("getName");
        _lgNames.setAliasGetPrettyName("getPrettyName");
        _lgNames.setAliasGetField("get");
        _lgNames.setAliasSetField("set");
        _lgNames.setAliasGetClass("getClass");
        _lgNames.setAliasGetEnclosingType("getEnclosingType");
        _lgNames.setAliasGetDeclaredClasses("getDeclaredClasses");
        _lgNames.setAliasForName("forName");
        _lgNames.setAliasObjectsUtil("java.lang.$ObjectsUtil");
        _lgNames.setAliasSameRef("eq");
        _lgNames.setAliasGetParent("getParent");
        _lgNames.setAliasNext("next");
        _lgNames.setAliasHasNext("hasNext");
        _lgNames.setAliasIterableTable("java.lang.$iterableTable");
        _lgNames.setAliasIteratorTable("iteratorTable");
        _lgNames.setAliasIteratorTableType("java.lang.$iteratorTable");
        _lgNames.setAliasHasNextPair("hasNextPair");
        _lgNames.setAliasNextPair("nextPair");
        _lgNames.setAliasPairType("java.lang.$pair");
        _lgNames.setAliasGetFirst("getFirst");
        _lgNames.setAliasGetSecond("getSecond");
        _lgNames.setAliasName("name");
        _lgNames.setAliasOrdinal("ordinal");
        _lgNames.setAliasEnumName("$name");
        _lgNames.setAliasEnumOrdinal("$ordinal");
        _lgNames.setAliasEnumPredValueOf("valueOf");
        _lgNames.setAliasGetOldString("getOldString");
        _lgNames.setAliasGetNewString("getNewString");
        _lgNames.setAliasGetSuperClass("getSuperClass");
        _lgNames.setAliasGetGenericSuperClass("getGenericSuperClass");
        _lgNames.setAliasGetInterfaces("getInterfaces");
        _lgNames.setAliasGetGenericInterfaces("getGenericInterfaces");
        _lgNames.setAliasGetLowerBounds("getLowerBounds");
        _lgNames.setAliasGetUpperBounds("getUpperBounds");
        _lgNames.setAliasGetComponentType("getComponentType");
        _lgNames.setAliasMakeArray("makeArray");
        _lgNames.setAliasGetParameterTypes("getParameterTypes");
        _lgNames.setAliasGetTypeParameters("getTypeParameters");
        _lgNames.setAliasGetParameterNames("getGenericParameterTypes");
        _lgNames.setAliasGetGenericReturnType("getGenericReturnType");
        _lgNames.setAliasGetReturnType("getReturnType");
        _lgNames.setAliasGetActualTypeArguments("getActualTypeArguments");
        _lgNames.setAliasGetFieldType("getType");
        _lgNames.setAliasGetGenericType("getGenericType");
        _lgNames.setAliasIsFinal("isFinal");
        _lgNames.setAliasIsTypeVariable("isTypeVariable");
        _lgNames.setAliasIsVariable("isVariable");
        _lgNames.setAliasIsStatic("isStatic");
        _lgNames.setAliasIsVarargs("isVarargs");
        _lgNames.setAliasIsNormal("isNormal");
        _lgNames.setAliasIsPublic("isPublic");
        _lgNames.setAliasIsProtected("isProtected");
        _lgNames.setAliasIsPackage("isPackage");
        _lgNames.setAliasIsPrivate("isPrivate");
        _lgNames.setAliasIsClass("isClass");
        _lgNames.setAliasIsWildCard("isWildCard");
        _lgNames.setAliasIsInterface("isInterface");
        _lgNames.setAliasIsEnum("isEnum");
        _lgNames.setAliasIsPrimitive("isPrimitive");
        _lgNames.setAliasIsArray("isArray");
        _lgNames.setAliasIsAnnotation("isAnnotation");
        _lgNames.setAliasMakeWildCard("makeWildCard");
        _lgNames.setAliasIsInstance("isInstance");
        _lgNames.setAliasIsAssignableFrom("isAssignableFrom");
        _lgNames.setAliasInit("init");
        _lgNames.setAliasDefaultInstance("defaultInstance");
        _lgNames.setAliasEnumValueOf("enumValueOf");
        _lgNames.setAliasGetEnumConstants("getEnumConstants");
        _lgNames.setAliasGetGenericBounds("getGenericBounds");
        _lgNames.setAliasGetBounds("getBounds");
        _lgNames.setAliasArrayNewInstance("newArrayInstance");
        _lgNames.setAliasArrayGet("get");
        _lgNames.setAliasArraySet("set");
        _lgNames.setAliasArrayGetLength("getLength");
        _lgNames.setAliasGetDeclaringClass("getDeclaringClass");
        _lgNames.setAliasBinQuot("binQuot");
        _lgNames.setAliasBinMod("binMod");
        _lgNames.setAliasPlus("plus");
        _lgNames.setAliasMinus("minus");
        _lgNames.setAliasMult("mult");
        _lgNames.setAliasAnd("and");
        _lgNames.setAliasOr("or");
        _lgNames.setAliasXor("xor");
        _lgNames.setAliasNegBin("negBin");
        _lgNames.setAliasNeg("neg");
        _lgNames.setAliasLt("lt");
        _lgNames.setAliasGt("gt");
        _lgNames.setAliasLe("le");
        _lgNames.setAliasGe("ge");
        _lgNames.setAliasShiftLeft("shiftLeft");
        _lgNames.setAliasShiftRight("shiftRight");
        _lgNames.setAliasBitShiftLeft("bitShiftLeft");
        _lgNames.setAliasBitShiftRight("bitShiftRight");
        _lgNames.setAliasRotateLeft("rotateLeft");
        _lgNames.setAliasRotateRight("rotateRight");
        _lgNames.setAliasRandom("random");
        _lgNames.setFalseString("false");
        _lgNames.setTrueString("true");
        _lgNames.setNullString("");
    }
    public static void initAdvStandards(ContextEl _cont) {
        CustLgNames lgNames_ = new CustLgNames();
        basicStandards(lgNames_);
        lgNames_.setAliasMath("$math");
        lgNames_.build();
        _cont.setStandards(lgNames_);
        lgNames_.setupOverrides(_cont);
    }
}
